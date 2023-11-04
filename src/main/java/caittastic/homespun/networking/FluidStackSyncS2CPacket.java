package caittastic.homespun.networking;

import caittastic.homespun.blockentity.CrushingTubBE;
import caittastic.homespun.blockentity.EvaporatingBasinBE;
import caittastic.homespun.blockentity.FluidStorageBE;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FluidStackSyncS2CPacket{
  private final FluidStack fluidStack;
  private final BlockPos pos;


  public FluidStackSyncS2CPacket(FluidStack fluidStack, BlockPos pos){
    this.fluidStack = fluidStack;
    this.pos = pos;
  }

  public FluidStackSyncS2CPacket(FriendlyByteBuf buf){
    this.fluidStack = buf.readFluidStack();
    this.pos = buf.readBlockPos();
  }

  public void toBytes(FriendlyByteBuf buf){
    buf.writeFluidStack(fluidStack);
    buf.writeBlockPos(pos);
  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier){
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof CrushingTubBE blockEntity){
        blockEntity.setFluid(this.fluidStack);
      }
      if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof EvaporatingBasinBE blockEntity){
        blockEntity.setFluid(this.fluidStack);
      }
      if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof FluidStorageBE blockEntity){
        blockEntity.setFluid(this.fluidStack);
      }
    });
    return true;
  }

}
