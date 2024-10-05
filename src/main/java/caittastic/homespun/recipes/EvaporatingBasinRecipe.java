package caittastic.homespun.recipes;

import caittastic.homespun.recipes.inputs.StackAndTankInput;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public record EvaporatingBasinRecipe(FluidStack fluidIngredient, ItemStack resultItem,
                                     int craftTime) implements Recipe<StackAndTankInput> {
    @Override
    public boolean matches(StackAndTankInput container, Level level) {
        FluidTank tank = container.tank();
        ItemStack stack = container.inputStack();

        FluidStack storedFluidStack = tank.getFluid();
        FluidStack inputFluidStack = this.fluidIngredient;
        ItemStack storedItemStack = stack;
        ItemStack inputItemStack = this.resultItem;

        return storedFluidStack.getFluid() == inputFluidStack.getFluid() &&
                storedFluidStack.getAmount() >= inputFluidStack.getAmount() &&
                (storedItemStack.isEmpty() || (storedItemStack.getItem() == inputItemStack.getItem()
                        && storedItemStack.getCount() + inputItemStack.getCount() <= storedItemStack.getMaxStackSize()));
    }

    @Override
    public ItemStack assemble(StackAndTankInput p_345149_, HolderLookup.Provider p_346030_) {
        return resultItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return resultItem.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<EvaporatingBasinRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "evaporating";

        private Type() {
        }

        @Override
        public String toString() {
            return ID;
        }
    }

    public static class Serializer implements RecipeSerializer<EvaporatingBasinRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<EvaporatingBasinRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                FluidStack.CODEC.fieldOf("fluid_stack").forGetter(EvaporatingBasinRecipe::fluidIngredient),
                ItemStack.CODEC.fieldOf("output_item").forGetter(EvaporatingBasinRecipe::resultItem),
                Codec.INT.fieldOf("time").forGetter(EvaporatingBasinRecipe::craftTime)
        ).apply(builder, EvaporatingBasinRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, EvaporatingBasinRecipe> STREAM_CODEC = StreamCodec.composite(
                FluidStack.STREAM_CODEC,
                EvaporatingBasinRecipe::fluidIngredient,
                ItemStack.STREAM_CODEC,
                EvaporatingBasinRecipe::resultItem,
                ByteBufCodecs.INT,
                EvaporatingBasinRecipe::craftTime,
                EvaporatingBasinRecipe::new
        );

        @Override
        public MapCodec<EvaporatingBasinRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EvaporatingBasinRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
