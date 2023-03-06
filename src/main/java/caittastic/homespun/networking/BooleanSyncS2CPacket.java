package caittastic.homespun.networking;

import caittastic.homespun.blockentity.CrushingTubBE;
import caittastic.homespun.blockentity.EvaporatingBasinBE;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BooleanSyncS2CPacket{
  private final boolean isCrafting;
  private final BlockPos pos;

  public BooleanSyncS2CPacket(boolean isCrafting, BlockPos pos){
    this.isCrafting = isCrafting;
    this.pos = pos;
  }

  public BooleanSyncS2CPacket(FriendlyByteBuf buf){
    this.isCrafting = buf.readBoolean();
    this.pos = buf.readBlockPos();
  }


  public void toBytes(FriendlyByteBuf buf){

    buf.writeBoolean(isCrafting);
    buf.writeBlockPos(pos);
  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier){
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof EvaporatingBasinBE blockEntity){
        blockEntity.isCrafting = this.isCrafting;
      }
    });
    return true;
  }

}
