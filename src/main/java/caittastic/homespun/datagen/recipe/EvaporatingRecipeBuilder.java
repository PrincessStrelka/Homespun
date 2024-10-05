package caittastic.homespun.datagen.recipe;

import caittastic.homespun.recipes.EvaporatingBasinRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class EvaporatingRecipeBuilder implements RecipeBuilder {
    private ItemStack itemStack;
    private FluidStack fluidStack;
    private int craftTime;

    public static EvaporatingRecipeBuilder of(ItemStack outputStack) {
        EvaporatingRecipeBuilder builder = new EvaporatingRecipeBuilder();
        builder.itemStack = outputStack;
        return builder;
    }

    public EvaporatingRecipeBuilder fluidStack(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
        return this;
    }

    public EvaporatingRecipeBuilder craftTime(int time) {
        this.craftTime = time;
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
        return itemStack.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        recipeOutput.accept(id, new EvaporatingBasinRecipe(fluidStack, itemStack, craftTime), null);
    }
}
