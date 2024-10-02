package caittastic.homespun.recipes;

import caittastic.homespun.recipes.inputs.StackAndTankInput;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public record CrushingTubRecipe(SizedIngredient inputItemStack, FluidStack outputFluidStack,
                                ItemStack outputItemStack) implements Recipe<StackAndTankInput> {
    @Override
    public boolean matches(StackAndTankInput container, Level level) {
        FluidTank outputTank = container.tank();
        ItemStack craftInputStack = container.inputStack();

        if (level.isClientSide)
            return false;


        //craft input stack is input stack
        //tank fluid is empty or recipe fluid
        //tank space left is less tan recipe fluid amount
        return inputItemStack.test(craftInputStack) &&
                (outputTank.isEmpty() || outputTank.getFluid().getFluid() == outputFluidStack.getFluid()) &&
                (outputTank.getCapacity() - outputTank.getFluidAmount()) >= this.outputFluidStack.getAmount();

    }

    @Override
    public ItemStack assemble(StackAndTankInput p_345149_, HolderLookup.Provider p_346030_) {
        return outputItemStack.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputItemStack.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public static class Type implements RecipeType<CrushingTubRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "tub_crushing";

        private Type() {
        }

        @Override
        public String toString() {
            return ID;
        }
    }

    public static class Serializer implements RecipeSerializer<CrushingTubRecipe> {
        public static final CrushingTubRecipe.Serializer INSTANCE = new CrushingTubRecipe.Serializer();
        private static final MapCodec<CrushingTubRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                SizedIngredient.FLAT_CODEC.fieldOf("input_item").forGetter(CrushingTubRecipe::inputItemStack),
                FluidStack.OPTIONAL_CODEC.fieldOf("fluid_stack").forGetter(CrushingTubRecipe::outputFluidStack),
                ItemStack.OPTIONAL_CODEC.fieldOf("output_item").forGetter(CrushingTubRecipe::outputItemStack)
        ).apply(builder, CrushingTubRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, CrushingTubRecipe> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC,
                CrushingTubRecipe::inputItemStack,
                FluidStack.OPTIONAL_STREAM_CODEC,
                CrushingTubRecipe::outputFluidStack,
                ItemStack.OPTIONAL_STREAM_CODEC,
                CrushingTubRecipe::outputItemStack,
                CrushingTubRecipe::new
        );

        @Override
        public MapCodec<CrushingTubRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrushingTubRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
