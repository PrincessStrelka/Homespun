package caittastic.homespun.blockentity;

import caittastic.homespun.item.ModItems;
import caittastic.homespun.networking.ItemstackSyncS2CPacket;
import caittastic.homespun.networking.ModPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class CrushingTubBE extends BlockEntity{
  int CRAFT_SLOT = 0;
  private final ItemStackHandler itemHandler = new ItemStackHandler(1){
    @Override
    protected void onContentsChanged(int slot){
      setChanged();
      if(!level.isClientSide){
        ModPackets.sendToClients(new ItemstackSyncS2CPacket(this, worldPosition));
      }
    }
  };
  private final FluidTank FLUID_TANK = new FluidTank(4000){
    @Override
    protected void onContentsChanged(){
      super.onContentsChanged();
    }
  };
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
  private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

  public CrushingTubBE(BlockPos pos, BlockState state){
    super(BlockEntities.CRUSHING_TUB.get(), pos, state);
  }

  public static void tick(Level level, BlockPos blockPos, BlockState state, CrushingTubBE entity){

  }

  public static void dropItems(Level pLevel, BlockPos pPos, Item itemToDrop, int count){
    ItemStack pStack = new ItemStack(itemToDrop, count);
    float f = EntityType.ITEM.getHeight() / 2.0F;
    double d0 = (double)((float)pPos.getX() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25D, 0.25D);
    double d1 = (double)((float)pPos.getY() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25D, 0.25D) - (double)f;
    double d2 = (double)((float)pPos.getZ() + 0.5F) + Mth.nextDouble(pLevel.random, -0.25D, 0.25D);
    if(!pLevel.isClientSide && !pStack.isEmpty() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !pLevel.restoringBlockSnapshots){
      ItemEntity itementity = new ItemEntity(pLevel, d0, d1, d2, pStack);
      itementity.setDefaultPickUpDelay();
      pLevel.addFreshEntity(itementity);
    }
  }

  public void setFluid(FluidStack stack){
    this.FLUID_TANK.setFluid(stack);
  }

  public FluidStack getFluidStack(){
    return this.FLUID_TANK.getFluid();
  }

  @Override
  public void onLoad(){
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> itemHandler);
    lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
  }

  @Override
  public void invalidateCaps(){
    super.invalidateCaps();
    lazyItemHandler.invalidate();
    lazyFluidHandler.invalidate();
  }

  @Override
  protected void saveAdditional(CompoundTag nbt){
    nbt.put("inventory", itemHandler.serializeNBT());
    nbt = FLUID_TANK.writeToNBT(nbt);
    super.saveAdditional(nbt);
  }

  @Override
  public void load(CompoundTag nbt){
    itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    FLUID_TANK.readFromNBT(nbt);
    super.load(nbt);
  }

  public ItemStack getInputRenderStack(){
    return itemHandler.getStackInSlot(CRAFT_SLOT);
  }

  public int getInputRenderStackSize(){
    return itemHandler.getStackInSlot(CRAFT_SLOT).getCount();
  }

  public FluidStack getStoredFluidStack(){
    //return FLUID_TANK.getFluid();
    return new FluidStack(Fluids.WATER, 4000);

  }

  //just done on the client to keep things in sync?
  public void setHandler(ItemStackHandler itemStackHandler){
    for(int i = 0; i < itemStackHandler.getSlots(); i++){
      itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
    }
  }

  public boolean placeItem(Player player, ItemStack stack){
    ItemStack stackInSlot = itemHandler.getStackInSlot(CRAFT_SLOT);
    Item internalItem = stackInSlot.getItem();
    if(stackInSlot.isEmpty() || (internalItem == stack.getItem())){
      if(stackInSlot.getCount() + stack.getCount() <= stackInSlot.getMaxStackSize()){
        itemHandler.insertItem(CRAFT_SLOT, stack, false);
        player.getItemInHand(InteractionHand.MAIN_HAND).setCount(0);
      }
      else{
        stack.setCount(stackInSlot.getMaxStackSize() - stackInSlot.getCount());
        player.getItemInHand(InteractionHand.MAIN_HAND).setCount(player.getItemInHand(InteractionHand.MAIN_HAND).getCount() - (stackInSlot.getMaxStackSize() - stackInSlot.getCount()));
        itemHandler.insertItem(CRAFT_SLOT, stack, false);
      }
      this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(player, this.getBlockState()));
      this.setChanged();
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
      return true;
    }
    return false;
  }

  public void drops(){
    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
    for(int i = 0; i < itemHandler.getSlots(); i++){ //loops through every item in the itemhandler
      inventory.setItem(i, itemHandler.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
    }

    Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
  }

  public void doCraft(){
    Random rand = new Random();
    Item craftItem = ModItems.IRONBERRIES.get();
    int craftItemCount = 1;
    Item resultItem = ModItems.TINY_IRON_DUST.get();
    int resultItemCount = 1;
    FlowingFluid resultFluid = Fluids.WATER;
    int resultFluidCount = 250;

    if(
            itemHandler.getStackInSlot(CRAFT_SLOT).getItem() == craftItem &&
            itemHandler.getStackInSlot(CRAFT_SLOT).getCount() >= craftItemCount &&
            FLUID_TANK.getSpace() >= resultFluidCount
    ){
      this.level.playSound(null, this.getBlockPos(), SoundEvents.SLIME_BLOCK_FALL, SoundSource.BLOCKS, 0.5F, rand.nextFloat() * 0.1F + 0.9F);
      this.itemHandler.extractItem(CRAFT_SLOT, craftItemCount, false);
      dropItems(this.level, this.getBlockPos(), resultItem, resultItemCount);
      this.FLUID_TANK.fill(new FluidStack(resultFluid, resultFluidCount), IFluidHandler.FluidAction.EXECUTE);
    }
  }


}
