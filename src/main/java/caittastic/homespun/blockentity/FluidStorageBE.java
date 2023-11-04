package caittastic.homespun.blockentity;

import caittastic.homespun.networking.FluidStackSyncS2CPacket;
import caittastic.homespun.networking.ModPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidStorageBE extends BlockEntity{
  /* ----------------------------------- variables ----------------------------------- */
  public static final int TANK_CAPACITY = 8000;
  private final FluidTank fluidTank = new FluidTank(TANK_CAPACITY){
    @Override
    protected void onContentsChanged(){
      if(!level.isClientSide){
        ModPackets.sendToClients(new FluidStackSyncS2CPacket(this.fluid, worldPosition));
      }
      setChanged();
    }
  };
  private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

  /* ----------------------------------- constructor ----------------------------------- */
  public FluidStorageBE(BlockPos pos, BlockState state){
    super(ModBlockEntities.FLUID_STORAGE.get(), pos, state);
  }

  /* ----------------------------------- overrides ----------------------------------- */
  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket(){
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public @NotNull CompoundTag getUpdateTag(){
    return saveWithoutMetadata();
  }

  @Override
  public void onLoad(){
    super.onLoad();
    lazyFluidHandler = LazyOptional.of(() -> fluidTank);
  }

  @Override
  public void load(CompoundTag nbt){
    fluidTank.readFromNBT(nbt);
    super.load(nbt);

  }

  @Override
  public void invalidateCaps(){
    super.invalidateCaps();
    lazyFluidHandler.invalidate();
  }

  @Override
  protected void saveAdditional(CompoundTag nbt){
    nbt = fluidTank.writeToNBT(nbt);
    super.saveAdditional(nbt);
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side){
    if(cap == ForgeCapabilities.FLUID_HANDLER)
      return lazyFluidHandler.cast();
    return super.getCapability(cap, side);
  }

  /* --------------------------------- custom methods --------------------------------- */
  public FluidStack getRenderFluid(){
    return fluidTank.getFluid();
  }

  public FluidTank getFluidTank(){
    return fluidTank;
  }

  public void setFluid(FluidStack stack){
    this.fluidTank.setFluid(stack);
  }

  public boolean isEmpty(){
    return fluidTank.getFluid().isEmpty();
  }
}
