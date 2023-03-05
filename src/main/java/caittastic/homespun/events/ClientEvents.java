package caittastic.homespun.events;

import caittastic.homespun.Homespun;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.renderer.CrushingTubBER;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents{
  @Mod.EventBusSubscriber(modid = Homespun.MOD_ID, value = Dist.CLIENT)
  public static class ClientForgeEvents{

  }

  @Mod.EventBusSubscriber(modid = Homespun.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents{
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
      event.registerBlockEntityRenderer(ModBlockEntities.CRUSHING_TUB.get(), CrushingTubBER::new);
    }
  }
}
