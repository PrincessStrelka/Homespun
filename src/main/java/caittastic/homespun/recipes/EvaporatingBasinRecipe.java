package caittastic.homespun.recipes;

import caittastic.homespun.Homespun;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EvaporatingBasinRecipe implements Recipe<SimpleContainerWithTank>{
  private final ResourceLocation id;
  private final FluidStack inputFluidStack;
  private final ItemStack outputItemStack;
  private final int craftTime;

  public EvaporatingBasinRecipe(ResourceLocation id, FluidStack inputFluid, ItemStack outputItem, int craftTime){
    this.id = id;
    this.inputFluidStack = inputFluid;
    this.outputItemStack = outputItem;
    this.craftTime = craftTime;
  }

  @Override
  public boolean matches(SimpleContainerWithTank container, Level level){
    FluidTank tank = container.getTank();
    ItemStack stack = container.getStack();

    if(level.isClientSide)
      return false;

    FluidStack storedFluidStack = tank.getFluid();
    FluidStack inputFluidStack = this.inputFluidStack;
    ItemStack storedItemStack = stack;
    ItemStack inputItemStack = this.outputItemStack;

    return storedFluidStack.getFluid() == inputFluidStack.getFluid() &&
            storedFluidStack.getAmount() >= inputFluidStack.getAmount() &&
            (storedItemStack.isEmpty() || (storedItemStack.getItem() == inputItemStack.getItem()
                    && storedItemStack.getCount() + inputItemStack.getCount() <= storedItemStack.getMaxStackSize()));



  }

  @Override
  public ItemStack assemble(SimpleContainerWithTank pContainer){
    return outputItemStack;
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight){
    return true;
  }

  @Override
  public ItemStack getResultItem(){
    return outputItemStack.copy();
  }

  @Override
  public ResourceLocation getId(){
    return id;
  }

  @Override
  public RecipeSerializer<?> getSerializer(){
    return Serializer.INSTANCE;
  }

  @Override
  public RecipeType<?> getType(){
    return Type.INSTANCE;
  }

  public int getTime(){
    return this.craftTime;
  }

  public FluidStack inputFluidStack(){
    return this.inputFluidStack;
  }

  public static class Type implements RecipeType<EvaporatingBasinRecipe>{
    public static final Type INSTANCE = new Type();
    public static final String ID = "evaporating";

    private Type(){}
  }

  public static class Serializer implements RecipeSerializer<EvaporatingBasinRecipe>{
    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID = new ResourceLocation(Homespun.MOD_ID, "evaporating");

    @Override
    public EvaporatingBasinRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe){
      ItemStack outputItem = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "output_item"), true, false);
      FluidStack fluidStack = fluidStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "fluid_stack"));
      int craftTime = GsonHelper.getAsInt(pSerializedRecipe, "time");

      return new EvaporatingBasinRecipe(pRecipeId, fluidStack, outputItem, craftTime);
    }

    private FluidStack fluidStackFromJson(JsonObject json){
      String fluidName = GsonHelper.getAsString(json, "fluid");
      ResourceLocation fluidKey = new ResourceLocation(fluidName);

      if(!ForgeRegistries.FLUIDS.containsKey(fluidKey))
        throw new JsonSyntaxException("Uh oh! Unknown fluid '" + fluidName + "'!");
      Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidKey);

      int amount = GsonHelper.getAsInt(json, "amount", 250);

      return new FluidStack(fluid, amount);
    }

    @Override
    public @Nullable EvaporatingBasinRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf){
      FluidStack fluidStack = buf.readFluidStack();
      ItemStack outputItem = buf.readItem();
      int time = buf.readInt();

      return new EvaporatingBasinRecipe(id, fluidStack, outputItem, time);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, EvaporatingBasinRecipe recipe){
      buf.writeFluidStack(recipe.inputFluidStack);
      buf.writeItemStack(recipe.outputItemStack, false);
      buf.writeInt(recipe.craftTime);
    }
  }
}
