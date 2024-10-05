package caittastic.homespun.events;

import caittastic.homespun.Homespun;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.gui.ModMenuRegistry;
import caittastic.homespun.gui.VesselScreen;
import caittastic.homespun.renderer.CrushingTubBER;
import caittastic.homespun.renderer.EvaporatingBasinBER;
import caittastic.homespun.renderer.FluidStorageBER;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ClientEvents{
  @EventBusSubscriber(modid = Homespun.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents{
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event){
      event.registerBlockEntityRenderer(ModBlockEntities.CRUSHING_TUB.get(), CrushingTubBER::new);
      event.registerBlockEntityRenderer(ModBlockEntities.EVAPORATING_BASIN.get(), EvaporatingBasinBER::new);
      event.registerBlockEntityRenderer(ModBlockEntities.FLUID_STORAGE.get(), FluidStorageBER::new);
    }

    @SubscribeEvent
    public static void clientSetup(final RegisterMenuScreensEvent event){
      event.register(ModMenuRegistry.VESSEL_MENU.get(), VesselScreen::new);
    }
  }
}
