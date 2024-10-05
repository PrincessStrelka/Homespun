package caittastic.homespun.datagen.recipe;

import caittastic.homespun.recipes.InsertFluidUsingItemRecipe;
import caittastic.homespun.recipes.TakeFluidUsingItemRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class IORecipeBuilder implements RecipeBuilder {
    private ItemStack filledItem;
    private FluidStack fluidStack;
    private ItemStack emptyItem;

    public static IORecipeBuilder of(ItemStack filledItem) {
        IORecipeBuilder builder = new IORecipeBuilder();
        builder.filledItem = filledItem;
        return builder;
    }

    public IORecipeBuilder emptyItem(ItemStack emptyItem) {
        this.emptyItem = emptyItem;
        return this;
    }

    public IORecipeBuilder fluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
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
        return this.filledItem.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        recipeOutput.accept(extendPath(id, "_insert"), new InsertFluidUsingItemRecipe(emptyItem, fluidStack, filledItem), null);
        recipeOutput.accept(extendPath(id, "_extract"), new TakeFluidUsingItemRecipe(emptyItem, fluidStack, filledItem), null);
    }

    private static ResourceLocation extendPath(ResourceLocation loc, String extension) {
        return ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), loc.getPath()+extension);
    }
}
