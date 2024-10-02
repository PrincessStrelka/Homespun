package caittastic.homespun.blockentity;

import caittastic.homespun.recipes.CrushingTubRecipe;
import caittastic.homespun.recipes.inputs.StackAndTankInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class CrushingTubBE extends BlockEntity{
  public static final int CRAFT_SLOT = 0;
  private static final int CAPACITY = 4000;
  private final ItemStackHandler itemHandler = new ItemStackHandler(1){
    @Override
    protected void onContentsChanged(int slot){
      setChanged();
      if(!level.isClientSide){
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
      }
    }
  };
  private final FluidTank fluidTank = new FluidTank(CAPACITY){
    @Override
    protected void onContentsChanged(){
      setChanged();
      if(!level.isClientSide){
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
      }
    }
  };

  public CrushingTubBE(BlockPos pos, BlockState state){
    super(ModBlockEntities.CRUSHING_TUB.get(), pos, state);
  }

  public static int getCapacity(){
    return CAPACITY;
  }

  public void setFluid(FluidStack stack){
    this.fluidTank.setFluid(stack);
  }

  @Override
  public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup){
    itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
    fluidTank.readFromNBT(lookup, nbt);

    super.loadAdditional(nbt, lookup);
  }

  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket(){
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookup){
    return saveWithoutMetadata(lookup);
  }

  @Override
  protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup){
    nbt.put("inventory", itemHandler.serializeNBT(lookup));
    nbt = fluidTank.writeToNBT(lookup, nbt);
    super.saveAdditional(nbt, lookup);
  }


  public ItemStack getInputRenderStack(){
    return itemHandler.getStackInSlot(CRAFT_SLOT);
  }

  public int getInputRenderStackSize(){
    return itemHandler.getStackInSlot(CRAFT_SLOT).getCount();
  }

  public FluidStack getStoredFluidStack(){
    return fluidTank.getFluid();
  }

  public IItemHandler getItemCap(Direction side) {
    if (side == Direction.DOWN) return getItemHandler();
    return null;
  }

  public IFluidHandler getFluidCap(Direction side) {
    return getFluidTank();
  }

  public int getFluidCapacity(){
    return fluidTank.getTankCapacity(0);
  }

  //just done on the client to keep things in sync?
  public void setHandler(ItemStackHandler itemStackHandler){
    for(int i = 0; i < itemStackHandler.getSlots(); i++){
      itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
    }
  }

  public void drops(){
    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
    for(int i = 0; i < itemHandler.getSlots(); i++){ //loops through every item in the itemhandler
      inventory.setItem(i, itemHandler.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
    }

    Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
  }


  void dropItems(BlockPos dropPos, ItemStack stack){
    Level level = this.level;
    float midItemHeight = EntityType.ITEM.getHeight() / 2.0F;
    double x = (double)((float)dropPos.getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
    double y = (double)((float)dropPos.getY() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D) - (double)midItemHeight;
    double z = (double)((float)dropPos.getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
    if(!level.isClientSide && !stack.isEmpty() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !level.restoringBlockSnapshots){
      ItemEntity itementity = new ItemEntity(level, x, y, z, stack);
      itementity.setDefaultPickUpDelay();
      level.addFreshEntity(itementity);
    }
  }

  public void doCraft(){
    Optional<CrushingTubRecipe> crushingRecipe = level.getRecipeManager().getRecipeFor(
            CrushingTubRecipe.Type.INSTANCE,
            new StackAndTankInput(itemHandler.getStackInSlot(CRAFT_SLOT), fluidTank),
            level).map(RecipeHolder::value);

    if(crushingRecipe.isPresent()){
      CrushingTubRecipe recipe = crushingRecipe.get();
      SoundEvent crushSound;
      SizedIngredient inputItemStack = recipe.inputItemStack();
      if(itemHandler.getStackInSlot(CRAFT_SLOT).getItem() instanceof BlockItem blockitem)
        crushSound = blockitem.getBlock().defaultBlockState().getSoundType().getBreakSound();
      else
        crushSound = SoundEvents.SLIME_BLOCK_FALL;
      this.level.playSound(null, this.getBlockPos(), crushSound, SoundSource.BLOCKS, 0.5F, new Random().nextFloat() * 0.1F + 0.9F);
      this.itemHandler.extractItem(CRAFT_SLOT, inputItemStack.count(), false);
      dropItems(this.getBlockPos(), recipe.getResultItem(null));
      this.fluidTank.fill(recipe.outputFluidStack(), IFluidHandler.FluidAction.EXECUTE);
    }
  }

  public FluidTank getFluidTank(){
    return fluidTank;
  }

  public IItemHandler getItemHandler(){
    return itemHandler;
  }
}
