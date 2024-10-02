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
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public record TakeFluidUsingItemRecipe(ItemStack emptyItem, FluidStack fluidStack, ItemStack filledItem) implements Recipe<StackAndTankInput> {
  @Override
  public boolean matches(StackAndTankInput container, Level level){
    FluidTank tank = container.tank();
    ItemStack stackInHand = container.inputStack();

    if(level.isClientSide || stackInHand.isEmpty())
      return false;

    return stackInHand.getItem() == this.emptyItem.getItem() &&
            tank.getFluid().getFluid() == this.fluidStack.getFluid() &&
            tank.getFluidAmount() >= this.fluidStack.getAmount();
  }

  @Override
  public ItemStack assemble(StackAndTankInput p_345149_, HolderLookup.Provider p_346030_) {
    return filledItem.copy();
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight){
    return true;
  }

  @Override
  public ItemStack getResultItem(HolderLookup.Provider registries) {
    return filledItem.copy();
  }

  @Override
  public RecipeSerializer<?> getSerializer(){
    return Serializer.INSTANCE;
  }

  @Override
  public RecipeType<?> getType(){
    return Type.INSTANCE;
  }

  public ItemStack emptyItem(){
    return this.emptyItem;
  }

  public FluidStack fluid(){
    return fluidStack;
  }

  public ItemStack filledItem(){
    return filledItem;
  }

  public static class Type implements RecipeType<TakeFluidUsingItemRecipe>{
    public static final Type INSTANCE = new Type();
    public static final String ID = "take_using_item";

    private Type(){}
  }

  public static class Serializer implements RecipeSerializer<TakeFluidUsingItemRecipe>{
    public static final Serializer INSTANCE = new Serializer();

    private static final MapCodec<TakeFluidUsingItemRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            ItemStack.CODEC.fieldOf("empty_item").forGetter(TakeFluidUsingItemRecipe::emptyItem),
            FluidStack.CODEC.fieldOf("fluid_stack").forGetter(TakeFluidUsingItemRecipe::fluidStack),
            ItemStack.CODEC.fieldOf("filled_item").forGetter(TakeFluidUsingItemRecipe::filledItem)
    ).apply(builder, TakeFluidUsingItemRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, TakeFluidUsingItemRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            TakeFluidUsingItemRecipe::emptyItem,
            FluidStack.STREAM_CODEC,
            TakeFluidUsingItemRecipe::fluidStack,
            ItemStack.STREAM_CODEC,
            TakeFluidUsingItemRecipe::filledItem,
            TakeFluidUsingItemRecipe::new
    );

    @Override
    public MapCodec<TakeFluidUsingItemRecipe> codec() {
      return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, TakeFluidUsingItemRecipe> streamCodec() {
      return STREAM_CODEC;
    }
  }
}
