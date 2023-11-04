package caittastic.homespun.gui;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class VesselSlot extends SlotItemHandler{
  public VesselSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition){
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public boolean mayPlace(ItemStack pStack) {
    return pStack.getItem().canFitInsideContainerItems();
  }
}