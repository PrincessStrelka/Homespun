package caittastic.homespun.datagen.recipe;

import caittastic.homespun.Homespun;
import caittastic.homespun.recipes.CrushingTubRecipe;
import caittastic.homespun.recipes.EvaporatingBasinRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class CrushingRecipeBuilder implements RecipeBuilder {
    private SizedIngredient inputStack;
    private FluidStack fluidStack;
    private ItemStack outputStack;

    public static CrushingRecipeBuilder of(ItemStack outputStack) {
        CrushingRecipeBuilder builder = new CrushingRecipeBuilder();
        builder.outputStack = outputStack;
        return builder;
    }

    public CrushingRecipeBuilder fluidStack(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
        return this;
    }

    public CrushingRecipeBuilder inputStack(SizedIngredient input) {
        this.inputStack = input;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return outputStack.getItem();
    }

    private ResourceLocation inputsToString() {
        StringBuilder builder = new StringBuilder();
        String namespace = Homespun.MOD_ID;
        for (Ingredient.Value value : inputStack.ingredient().getValues()) {
            if (value instanceof Ingredient.ItemValue(ItemStack item)) {
                ResourceLocation itemLocation = BuiltInRegistries.ITEM.getKey(item.getItem());
                namespace = itemLocation.getNamespace();
                builder.append(itemLocation.getPath()).append("_");
            } else if (value instanceof Ingredient.TagValue(TagKey<Item> tag)) {
                ResourceLocation location = tag.location();
                namespace = location.getNamespace();
                builder.append(location.getPath()).append("_");
            }
        }
        builder.append("_to_").append(BuiltInRegistries.ITEM.getKey(getResult()).getPath());
        return ResourceLocation.fromNamespaceAndPath(namespace, builder.toString());
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        recipeOutput.accept(inputsToString(), new CrushingTubRecipe(inputStack, fluidStack, outputStack), null);
    }
}

