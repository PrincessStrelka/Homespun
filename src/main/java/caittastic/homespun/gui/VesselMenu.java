package caittastic.homespun.gui;

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.blockentity.VesselBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class VesselMenu extends AbstractContainerMenu{
  //container slot stuff
  private static final int HOTBAR_WIDTH = 9; //how many slots wide is the hotbar
  private static final int PLAYER_INVENTORY_HEIGHT = 3; //how many slots high is the inventory
  private static final int PLAYER_INVENTORY_WIDTH = 9; //how many slots wide is the inventory
  private static final int VANILLA_FIRST_SLOT_INDEX = 0; //what index does the vanilla inventory container start
  private static final int VANILLA_SLOT_COUNT = HOTBAR_WIDTH + (PLAYER_INVENTORY_WIDTH * PLAYER_INVENTORY_HEIGHT); // the total amount of slots in the vanilla container
  private static final int BE_FIRST_SLOT = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT; //the index of the first slot of our block entity
  private static final int BE_SLOT_COUNT = VesselBE.SLOT_COUNT;  // the number of slots in our block entity
  /* variables */
  private final VesselBE blockEntity;
  private final Level level;
  private BlockPos worldPosition;

  /* constructor */
  //im not really sure what this stuff means
  protected VesselMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData){
    this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(VesselBE.DATA_COUNT)); //psize has to match the count in blockentity
  }

  public VesselMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data){
    super(ModMenuRegistry.VESSEL_MENU.get(), pContainerId); //the first one needs to be the matching menu type to the block entity type
    checkContainerSize(inv, VesselBE.SLOT_COUNT);//pMinSize is how many slots your block entity has
    blockEntity = ((VesselBE)entity);
    this.level = inv.player.level;

    addPlayerInventory(inv);
    addPlayerHotbar(inv);

    //indexes are the same as the ones in the block entities itemhandler, the x and y positions match the sprites of the slots in the texture
    this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
      for(int slot = 0; slot < VesselBE.SLOT_COUNT; slot++){
        int xStar = 53;
        int yStar = 17;
        int rowWidth = 4;
        int xOffs = (slot % rowWidth) * 18;
        int yOffs = slot / rowWidth * 18;
        this.addSlot(new VesselSlot(handler, slot, xStar + xOffs, yStar + yOffs));
      }
    });

    addDataSlots(data);
  }

  /* implemented methods */
  @Override
  public ItemStack quickMoveStack(Player player, int index){
    Slot sourceSlot = slots.get(index);                                         //gets the slot at the index that is shift clicked
    if(sourceSlot == null || !sourceSlot.hasItem())
      return ItemStack.EMPTY;    //if the slot that gets clicked on is null or does not contain an item, return an empty item, exit the method
    ItemStack sourceStack = sourceSlot.getItem();                               //gets the itemstack in the clicked slot
    ItemStack copyOfSourceStack = sourceStack.copy();                           //creates a copy of the clicked itemstack to modify

    // Check if the slot clicked is one of the vanilla container slots
    if(index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT){
      // This is a vanilla container slot so merge the stack into the tile inventory
      if(!moveItemStackTo(sourceStack, BE_FIRST_SLOT, BE_FIRST_SLOT
              + BE_SLOT_COUNT, false)){
        return ItemStack.EMPTY;  // EMPTY_ITEM
      }
    }
    else if(index < BE_FIRST_SLOT + BE_SLOT_COUNT){
      // This is a TE slot so merge the stack into the players inventory
      if(!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)){
        return ItemStack.EMPTY;
      }
    }
    else{
      System.out.println("Invalid slotIndex:" + index);
      return ItemStack.EMPTY;
    }
    // If stack size == 0 (the entire stack was moved) set slot contents to null
    if(sourceStack.getCount() == 0){
      sourceSlot.set(ItemStack.EMPTY);
    }
    else{
      sourceSlot.setChanged();
    }
    sourceSlot.onTake(player, sourceStack);
    return copyOfSourceStack;
  }

  @Override
  public boolean stillValid(Player player){
    return blockEntity.stillValid(player);
  }

  /* custom methods */
  //helper method to add the player inventory to the menu
  private void addPlayerInventory(Inventory playerInventory){
    for(int i = 0; i < PLAYER_INVENTORY_HEIGHT; ++i){ //loop the height of the player inventory
      for(int l = 0; l < PLAYER_INVENTORY_WIDTH; ++l){ //loop the width of the player inventory
        int slotIndex = l + i * 9 + 9; //the index of the inventory slots, counting from the top left incrementing rightwards
        int firstInventoryX = 8;
        int firstInventoryY = 84;
        //index of inventory slot, x coordinate of top left pixel of slot, y coordinate of top left pixel of slot
        this.addSlot(new Slot(playerInventory, slotIndex, firstInventoryX + l * 18, firstInventoryY + i * 18));
      }
    }
  }

  //helper method to add the player hotbar to the menu
  private void addPlayerHotbar(Inventory playerInventory){
    for(int i = 0; i < HOTBAR_WIDTH; ++i){ //loop the width of the player hotbar
      int firstHotbarX = 8; //the x coordinate of the top left pixel of the first slot
      int firstHotbarY = 142; //the y coordinate of the top left pixel
      //adds a slot at the index i with px and py being the coordinates of the top left pixel of the slots texture
      this.addSlot(new Slot(playerInventory, i, firstHotbarX + i * 18, firstHotbarY));
    }
  }
}
