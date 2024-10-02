package caittastic.homespun.blockentity;

import caittastic.homespun.block.EvaporatingBasinBlock;
import caittastic.homespun.recipes.EvaporatingBasinRecipe;
import caittastic.homespun.recipes.inputs.StackAndTankInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
      }
    }
  };
  private final FluidTank fluidTank = new FluidTank(TANK_CAPACITY){
    @Override
    protected void onContentsChanged(){
      setChanged();
      if(!level.isClientSide){
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
      }
    }
  };
  public boolean isCrafting;
  private int progress = 0;

  //constructor
  public EvaporatingBasinBE(BlockPos pos, BlockState state){
    super(ModBlockEntities.EVAPORATING_BASIN.get(), pos, state);
  }

  //crafting stuff
  public static void tick(Level level, BlockPos blockPos, BlockState state, EvaporatingBasinBE entity){
    Optional<EvaporatingBasinRecipe> recipe = level.getRecipeManager().getRecipeFor(
            EvaporatingBasinRecipe.Type.INSTANCE,
            new StackAndTankInput(entity.itemHandler.getStackInSlot(OUTPUT_SLOT), entity.fluidTank),
            level).map(RecipeHolder::value);

    if(recipe.isPresent()){
      if(level.getBlockState(blockPos.offset(0, -1, 0)).is(Blocks.MAGMA_BLOCK))
        entity.progress += 2;
      else
        entity.progress++;
      if(entity.progress >= recipe.get().craftTime()){
        level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.1F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        entity.itemHandler.insertItem(OUTPUT_SLOT, recipe.get().getResultItem(null), false);
        entity.fluidTank.drain(recipe.get().fluidIngredient().getAmount(), IFluidHandler.FluidAction.EXECUTE);
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
  public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookup){
    return saveWithoutMetadata(lookup);
  }

  @Override
  public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup){
    itemHandler.deserializeNBT(lookup, nbt.getCompound("inventory"));
    fluidTank.readFromNBT(lookup, nbt);
    progress = nbt.getInt("evaporating_basin.progress");
    super.loadAdditional(nbt, lookup);

  }

  @Override
  protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup){
    nbt.put("inventory", itemHandler.serializeNBT(lookup));
    nbt = fluidTank.writeToNBT(lookup, nbt);
    nbt.putInt("evaporating_basin.progress", progress);
    super.saveAdditional(nbt, lookup);
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
}
