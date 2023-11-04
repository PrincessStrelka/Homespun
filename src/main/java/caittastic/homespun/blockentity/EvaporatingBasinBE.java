package caittastic.homespun.blockentity;

import caittastic.homespun.block.EvaporatingBasinBlock;
import caittastic.homespun.networking.FluidStackSyncS2CPacket;
import caittastic.homespun.networking.ItemStackSyncS2CPacket;
import caittastic.homespun.networking.ModPackets;
import caittastic.homespun.recipes.EvaporatingBasinRecipe;
import caittastic.homespun.recipes.SimpleContainerWithTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EvaporatingBasinBE extends BlockEntity{

  public static final int TANK_CAPACITY = 2000;
  public static final int OUTPUT_SLOT = 0;
  private final ItemStackHandler itemHandler = new ItemStackHandler(1){
    @Override
    protected void onContentsChanged(int slot){
      setChanged();
      if(!level.isClientSide){
        ModPackets.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
      }
    }
  };
  private final FluidTank fluidTank = new FluidTank(TANK_CAPACITY){
    @Override
    protected void onContentsChanged(){
      if(!level.isClientSide){
        ModPackets.sendToClients(new FluidStackSyncS2CPacket(this.fluid, worldPosition));
      }
      setChanged();
    }
  };
  public boolean isCrafting;
  private int progress = 0;
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
  private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

  //constructor
  public EvaporatingBasinBE(BlockPos pos, BlockState state){
    super(ModBlockEntities.EVAPORATING_BASIN.get(), pos, state);
  }

  //crafting stuff
  public static void tick(Level level, BlockPos blockPos, BlockState state, EvaporatingBasinBE entity){
    Optional<EvaporatingBasinRecipe> recipe = level.getRecipeManager().getRecipeFor(
            EvaporatingBasinRecipe.Type.INSTANCE,
            new SimpleContainerWithTank(entity.fluidTank, entity.itemHandler.getStackInSlot(OUTPUT_SLOT)),
            level);

    if(recipe.isPresent()){
      if(level.getBlockState(blockPos.offset(0, -1, 0)).is(Blocks.MAGMA_BLOCK))
        entity.progress += 2;
      else
        entity.progress++;
      if(entity.progress >= recipe.get().getTime()){
        level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.1F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        entity.itemHandler.insertItem(OUTPUT_SLOT, recipe.get().getResultItem(), false);
        entity.fluidTank.drain(recipe.get().inputFluidStack().getAmount(), IFluidHandler.FluidAction.EXECUTE);
        setChanged(level, blockPos, state);
        entity.progress = 0;
      } else
        level.setBlock(blockPos, state.setValue(EvaporatingBasinBlock.EVAPORATING, true), 3);
    } else{
      entity.progress = 0;
      level.setBlock(blockPos, state.setValue(EvaporatingBasinBlock.EVAPORATING, false), 3);
    }
  }

  //keeps items and fluids in sync with client and server
  public void setHandler(ItemStackHandler itemStackHandler){
    for(int i = 0; i < itemStackHandler.getSlots(); i++){
      itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
    }
  }

  public void setFluid(FluidStack stack){
    this.fluidTank.setFluid(stack);
  }

  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket(){
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public @NotNull CompoundTag getUpdateTag(){
    return saveWithoutMetadata();
  }

  @Override
  public void invalidateCaps(){
    super.invalidateCaps();
    lazyItemHandler.invalidate();
    lazyFluidHandler.invalidate();
  }

  //save and load
  @Override
  public void onLoad(){
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> itemHandler);
    lazyFluidHandler = LazyOptional.of(() -> fluidTank);

  }

  @Override
  public void load(CompoundTag nbt){
    itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    fluidTank.readFromNBT(nbt);
    progress = nbt.getInt("evaporating_basin.progress");
    super.load(nbt);

  }

  @Override
  protected void saveAdditional(CompoundTag nbt){
    nbt.put("inventory", itemHandler.serializeNBT());
    nbt = fluidTank.writeToNBT(nbt);
    nbt.putInt("evaporating_basin.progress", progress);
    super.saveAdditional(nbt);
  }

  //for getting data about the things stored
  public ItemStack getRenderStack(){
    return itemHandler.getStackInSlot(OUTPUT_SLOT);
  }

  public FluidStack getRenderFluid(){
    return fluidTank.getFluid();
  }

  public FluidTank getFluidTank(){
    return fluidTank;
  }

  public IItemHandler getItemHandler(){
    return itemHandler;
  }

  public boolean isCrafting(){
    return isCrafting;
  }

  //for making sure it drops all it items
  public void drops(){
    SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
    for(int i = 0; i < itemHandler.getSlots(); i++){ //loops through every item in the itemhandler
      inventory.setItem(i, itemHandler.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
    }

    Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
  }

  //for making our block interact with other mods blocks
  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side){
    if(cap == ForgeCapabilities.FLUID_HANDLER)
      return lazyFluidHandler.cast();
    if(cap == ForgeCapabilities.ITEM_HANDLER){
      return lazyItemHandler.cast();
    }
    return super.getCapability(cap, side);
  }
}
