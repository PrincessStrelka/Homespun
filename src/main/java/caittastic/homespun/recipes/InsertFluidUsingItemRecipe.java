package caittastic.homespun.recipes;

import caittastic.homespun.Homespun;
import caittastic.homespun.recipes.inputs.StackAndTankInput;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

public record InsertFluidUsingItemRecipe(ItemStack emptyItem, FluidStack inputFluid, ItemStack outputStack) implements Recipe<StackAndTankInput>{
  @Override
  public boolean matches(StackAndTankInput container, Level level){
    FluidTank tank = container.tank();
    ItemStack stackInHand = container.inputStack();

    if(level.isClientSide || stackInHand.isEmpty())
      return false;

    return stackInHand.getItem() == this.outputStack.getItem() &&
            (tank.getFluid().isEmpty() || tank.getFluid().getFluid() == this.inputFluid.getFluid()) &&
            (tank.getCapacity() - tank.getFluidAmount()) >= this.inputFluid.getAmount();
  }

  @Override
  public ItemStack assemble(StackAndTankInput p_345149_, HolderLookup.Provider p_346030_) {
    return outputStack.copy();
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight){
    return true;
  }

  @Override
  public ItemStack getResultItem(HolderLookup.Provider registries) {
    return outputStack.copy();
  }

  @Override
  public RecipeSerializer<?> getSerializer(){
    return Serializer.INSTANCE;
  }

  @Override
  public RecipeType<?> getType(){
    return Type.INSTANCE;
  }

  public static class Type implements RecipeType<InsertFluidUsingItemRecipe>{
    public static final Type INSTANCE = new Type();
    public static final String ID = "fill_using_item";

    private Type(){}
  }

  public static class Serializer implements RecipeSerializer<InsertFluidUsingItemRecipe>{
    public static final Serializer INSTANCE = new Serializer();
    private static final MapCodec<InsertFluidUsingItemRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            ItemStack.CODEC.fieldOf("empty_item").forGetter(InsertFluidUsingItemRecipe::emptyItem),
            FluidStack.CODEC.fieldOf("fluid_stack").forGetter(InsertFluidUsingItemRecipe::inputFluid),
            ItemStack.CODEC.fieldOf("filled_item").forGetter(InsertFluidUsingItemRecipe::outputStack)
    ).apply(builder, InsertFluidUsingItemRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, InsertFluidUsingItemRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            InsertFluidUsingItemRecipe::emptyItem,
            FluidStack.STREAM_CODEC,
            InsertFluidUsingItemRecipe::inputFluid,
            ItemStack.STREAM_CODEC,
            InsertFluidUsingItemRecipe::outputStack,
            InsertFluidUsingItemRecipe::new
    );

    @Override
    public MapCodec<InsertFluidUsingItemRecipe> codec() {
      return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, InsertFluidUsingItemRecipe> streamCodec() {
      return STREAM_CODEC;
    }
  }
}
