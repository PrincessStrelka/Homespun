package caittastic.homespun.events;

import caittastic.homespun.Homespun;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.renderer.CrushingTubBER;
import caittastic.homespun.renderer.EvaporatingBasinBER;
import caittastic.homespun.renderer.FluidStorageBER;
import net.neoforged.neoforge.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.eventbus.api.SubscribeEvent;
import net.neoforged.neoforge.fml.common.Mod;

public class ClientEvents{
  @Mod.EventBusSubscriber(modid = Homespun.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents{
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event){
      event.registerBlockEntityRenderer(ModBlockEntities.CRUSHING_TUB.get(), CrushingTubBER::new);
      event.registerBlockEntityRenderer(ModBlockEntities.EVAPORATING_BASIN.get(), EvaporatingBasinBER::new);
      event.registerBlockEntityRenderer(ModBlockEntities.FLUID_STORAGE.get(), FluidStorageBER::new);
    }
  }
}
