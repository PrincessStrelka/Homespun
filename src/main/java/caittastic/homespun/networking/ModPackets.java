package caittastic.homespun.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModPackets{
  private static SimpleChannel INSTANCE;
  private static int packetID = 0;
  private static int id(){return packetID++;}

  public static void register(){
    SimpleChannel net = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MOD_ID ,"packets"))
            .networkProtocolVersion(()-> "1.0")
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .simpleChannel();
    INSTANCE = net;

    net.messageBuilder(ItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
            .decoder(ItemStackSyncS2CPacket::new)
            .encoder(ItemStackSyncS2CPacket::toBytes)
            .consumerMainThread(ItemStackSyncS2CPacket::handle)
            .add();
    
    net.messageBuilder(FluidStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
            .decoder(FluidStackSyncS2CPacket::new)
            .encoder(FluidStackSyncS2CPacket::toBytes)
            .consumerMainThread(FluidStackSyncS2CPacket::handle)
            .add();
    
  }
  public static <MSG> void sendToServer(MSG message){
    INSTANCE.sendToServer(message);
  }

  public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
    INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
  }

  public static <MSG> void sendToClients(MSG message) {
    INSTANCE.send(PacketDistributor.ALL.noArg(), message);
  }

}
