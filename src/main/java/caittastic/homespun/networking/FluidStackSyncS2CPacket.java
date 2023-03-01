package caittastic.homespun.networking;

import caittastic.homespun.blockentity.CrushingTubBE;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.network.NetworkEvent;

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
    });
    return true;
  }

}
