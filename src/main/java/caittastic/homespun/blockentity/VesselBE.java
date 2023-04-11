package caittastic.homespun.blockentity;

import caittastic.homespun.gui.VesselMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class VesselBE extends BlockEntity implements MenuProvider{
  public static int SLOT_COUNT = 12; //the amount of slots in our inventory
  public static int DATA_COUNT = 2; //the amount in the container data
  protected final ContainerData data; //something to do with transfering NBT data between block entity, menu, and screen
  private Component name;
  //-------------------------------------------------------variables-------------------------------------------------------//
  //"is important" but i dont know why or what for
  //the size is for every number of slots in the inventory
  private final ItemStackHandler inventory = new ItemStackHandler(SLOT_COUNT){
    @Override
    protected void onContentsChanged(int slot){
      setChanged();
    }
  };
  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty(); //no clue what this does

  //-------------------------------------------------------constructor-------------------------------------------------------//
  public VesselBE(BlockPos blockPos, BlockState blockState){
    super(ModBlockEntities.VESSEL.get(), blockPos, blockState);
    //something to do with transfering NBT data between block entity, menu, and screen, but im really not sure
    //creates a new containerData
    //passes in progress in index 0
    //passes in max progress in index 1
    //count is how many variables are being saved
    this.data = new ContainerData(){
      public int get(int index){
        return 0;
      }

      @Override
      public void set(int pIndex, int pValue){

      }

      public int getCount(){
        return DATA_COUNT; //has to match the simple container size in menu
      }
    }

    ;
  }

  //-------------------------------------------------------base methods-------------------------------------------------------//
  @Override //this is the name that shows up in the block gui
  public Component getDisplayName(){
    //todo make this return the name of the block if renamed
    return this.name != null ? this.name : Component.translatable("container.vessel");
  }


  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int containerID, Inventory inventory, Player player){
    return new VesselMenu(containerID, inventory, this, this.data);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
          @Nonnull Capability<T> cap,
          @javax.annotation.Nullable Direction side
  ){
    if(cap == ForgeCapabilities.ITEM_HANDLER){
      return lazyItemHandler.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void onLoad(){
    super.onLoad();
    lazyItemHandler = LazyOptional.of(() -> inventory);
  }

  @Override
  public void invalidateCaps(){
    super.invalidateCaps();
    lazyItemHandler.invalidate();
  }

  @Override //saves stuff to NBT on saving the game
  protected void saveAdditional(@NotNull CompoundTag tag){
    tag.put("inventory", inventory.serializeNBT());
    if (this.name != null) {
      tag.putString("CustomName", Component.Serializer.toJson(this.name));
    }
    super.saveAdditional(tag);
  }

  //loads stuff from NBT on saving the game
  @Override
  public void load(CompoundTag nbt){
    super.load(nbt);
    inventory.deserializeNBT(nbt.getCompound("inventory"));
    if (nbt.contains("CustomName", 8)) {
      this.name = Component.Serializer.fromJson(nbt.getString("CustomName"));
    }
  }



  //-------------------------------------------------------custom methods-------------------------------------------------------//
  //handles dropping the block and its inventory on breaking
  public void drops(){
    SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots()); //creates a simplecontainer containing the amount of slots in our itemhandler
    for(int i = 0; i < this.inventory.getSlots(); i++){ //loops through every item in the itemhandler
      inventory.setItem(i, this.inventory.getStackInSlot(i)); //puts every item in the itemhandler into the simplecontainer
    }

    Containers.dropContents(this.level, this.worldPosition, inventory); //drops the contents of the simplecontainer
  }
}
