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
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.crafting.CraftingHelper;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class CrushingTubRecipe implements Recipe<SimpleContainerWithTank>{
  private final ResourceLocation id;
  private final ItemStack inputItemStack;
  private final FluidStack outputFluidStack;
  private final ItemStack outputItemStack;

  public CrushingTubRecipe(ResourceLocation id, ItemStack inputItem, FluidStack inputFluid, ItemStack outputItem){
    this.id = id;
    this.inputItemStack = inputItem;
    this.outputFluidStack = inputFluid;
    this.outputItemStack = outputItem;
  }

  @Override
  public boolean matches(SimpleContainerWithTank container, Level level){
    FluidTank outputTank = container.getTank();
    ItemStack craftInputStack = container.getStack();

    if(level.isClientSide)
      return false;


    //craft input stack is input stack
    //tank fluid is empty or recipe fluid
    //tank space left is less tan recipe fluid amount
    return craftInputStack.getItem() == inputItemStack.getItem() &&
            craftInputStack.getCount() >= inputItemStack.getCount() &&
            (outputTank.isEmpty() || outputTank.getFluid().getFluid() == outputFluidStack.getFluid()) &&
            (outputTank.getCapacity() - outputTank.getFluidAmount()) >= this.outputFluidStack.getAmount();

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

   public ItemStack getInputItemStack(){
    return inputItemStack.copy();
  }

  public FluidStack getResultFluidStack(){
    return outputFluidStack.copy();
  }


  public static class Type implements RecipeType<CrushingTubRecipe>{
    public static final Type INSTANCE = new Type();
    public static final String ID = "tub_crushing";

    private Type(){}
  }

  public static class Serializer implements RecipeSerializer<CrushingTubRecipe>{
    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID = new ResourceLocation(Homespun.MOD_ID, "tub_crushing");

    @Override
    public CrushingTubRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe){
      ItemStack inputItem = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input_item"));
      ItemStack outputItem = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "output_item"), true, false);
      FluidStack fluidStack = fluidStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "fluid_stack"));

      return new CrushingTubRecipe(pRecipeId, inputItem, fluidStack, outputItem);
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
    public @Nullable CrushingTubRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf){
      ItemStack inputItem = buf.readItem();
      FluidStack fluidStack = buf.readFluidStack();
      ItemStack outputItem = buf.readItem();

      return new CrushingTubRecipe(id, inputItem, fluidStack, outputItem);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, CrushingTubRecipe recipe){
      buf.writeItemStack(recipe.inputItemStack, false);
      buf.writeFluidStack(recipe.outputFluidStack);
      buf.writeItemStack(recipe.outputItemStack, false);
    }
  }
}
