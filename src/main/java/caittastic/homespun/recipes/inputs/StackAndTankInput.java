package caittastic.homespun.recipes.inputs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public record StackAndTankInput(ItemStack inputStack, FluidTank tank) implements RecipeInput {
    @Override
    public ItemStack getItem(int p_346128_) {
        return inputStack;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return RecipeInput.super.isEmpty() && tank.isEmpty();
    }
}
