package caittastic.homespun.networking;

import caittastic.homespun.blockentity.CrushingTubBE;
import caittastic.homespun.blockentity.EvaporatingBasinBE;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemStackSyncS2CPacket{
  private final ItemStackHandler itemStackHandler;
  private final BlockPos pos;

  public ItemStackSyncS2CPacket(ItemStackHandler itemStackHandler, BlockPos pos){
    this.itemStackHandler = itemStackHandler;
    this.pos = pos;
  }

  public ItemStackSyncS2CPacket(FriendlyByteBuf buf){
    List<ItemStack> collection = buf.readCollection(ArrayList::new, FriendlyByteBuf::readItem);
    itemStackHandler = new ItemStackHandler(collection.size());
    for (int i =0; i< collection.size(); i++){
      itemStackHandler.insertItem(i, collection.get(i), false);
    }

    this.pos = buf.readBlockPos();
  }

  public void toBytes(FriendlyByteBuf buf){
    List<ItemStack> collection = new ArrayList<>();
    for(int i = 0; i < itemStackHandler.getSlots(); i++){
      collection.add(itemStackHandler.getStackInSlot(i));
    }
    buf.writeCollection(collection, FriendlyByteBuf::writeItem);
    buf.writeBlockPos(pos);
  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier){
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof CrushingTubBE blockEntity){
        blockEntity.setHandler(this.itemStackHandler);
      }
      if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof EvaporatingBasinBE blockEntity){
        blockEntity.setHandler(this.itemStackHandler);
      }
    });
    return true;
  }

}
