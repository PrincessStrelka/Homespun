package caittastic.homespun.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidStorageBE extends BlockEntity{
  /* ----------------------------------- variables ----------------------------------- */
  public static final int TANK_CAPACITY = 8000;
  private final FluidTank fluidTank = new FluidTank(TANK_CAPACITY){
    @Override
    protected void onContentsChanged(){
      setChanged();
      if(!level.isClientSide){
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
      }
    }
  };

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
  public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookup){
    return saveWithoutMetadata(lookup);
  }

  @Override
  public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup){
    fluidTank.readFromNBT(lookup, nbt);
    super.loadAdditional(nbt, lookup);

  }

  @Override
  protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup){
    nbt = fluidTank.writeToNBT(lookup, nbt);
    super.saveAdditional(nbt, lookup);
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

  public IFluidHandler getFluidCap(Direction side) {
    return getFluidTank();
  }
}
