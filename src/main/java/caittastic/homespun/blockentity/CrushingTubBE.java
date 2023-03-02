package caittastic.homespun.blockentity;

import caittastic.homespun.item.ModItems;
import caittastic.homespun.networking.FluidStackSyncS2CPacket;
import caittastic.homespun.networking.ItemStackSyncS2CPacket;
import caittastic.homespun.networking.ModPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class CrushingTubBE extends BlockEntity{
  private final ItemStackHandler itemHandler = new ItemStackHandler(1){
    @Override
    protected void onContentsChanged(int slot){
      setChanged();
      if(!level.isClientSide){
        ModPackets.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
      }
    }
  };
  private final FluidTank FLUID_TANK = new FluidTank(4000){
    @Override
    protected void onContentsChanged(){
      if(!level.isClientSide){
        ModPackets.sendToClients(new FluidStackSyncS2CPacket(this.fluid, worldPosition));
      }
      setChanged();
    }
  };
  public static final int CRAFT_SLOT = 0;
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
  private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

  public CrushingTubBE(BlockPos pos, BlockState state){
    super(BlockEntities.CRUSHING_TUB.get(), pos, state);
  }

  public void setFluid(FluidStack stack){
    this.FLUID_TANK.setFluid(stack);
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
    return FLUID_TANK.getFluid();
    //return new FluidStack(Fluids.WATER, 2000);
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side){
    if(cap == ForgeCapabilities.FLUID_HANDLER)
      lazyFluidHandler.cast();
    return super.getCapability(cap, side);
  }

  public int getFluidCapacity(){
    return FLUID_TANK.getTankCapacity(0);
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
    //TODO: make this recipe based
    Random rand = new Random();
    Item craftItem = ModItems.IRONBERRIES.get();
    int craftItemCount = 1;
    Item resultItem = ModItems.TINY_IRON_DUST.get();
    int resultItemCount = 1;
    Fluid resultFluid = Fluids.WATER;
    int resultFluidCount = 250;

    if(itemHandler.getStackInSlot(CRAFT_SLOT).getItem() == craftItem &&
            itemHandler.getStackInSlot(CRAFT_SLOT).getCount() >= craftItemCount &&
            (FLUID_TANK.getFluid().getFluid() == resultFluid || FLUID_TANK.getFluid().getFluid() == Fluids.EMPTY) &&
            FLUID_TANK.getSpace() >= resultFluidCount
    ){
      this.level.playSound(null, this.getBlockPos(), SoundEvents.SLIME_BLOCK_FALL, SoundSource.BLOCKS, 0.5F, rand.nextFloat() * 0.1F + 0.9F);
      this.itemHandler.extractItem(CRAFT_SLOT, craftItemCount, false);
      dropItems(this.getBlockPos(), new ItemStack(resultItem, resultItemCount));
      this.FLUID_TANK.fill(new FluidStack(resultFluid, resultFluidCount), IFluidHandler.FluidAction.EXECUTE);
    }
  }


  public FluidTank getFluidTank(){
    return FLUID_TANK;
  }

  public IItemHandler getItemHandler(){
    return itemHandler;
  }
}
