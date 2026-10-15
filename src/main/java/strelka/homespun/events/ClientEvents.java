package strelka.homespun.events;

import strelka.homespun.Homespun;
import strelka.homespun.blockentity.ModBlockEntities;
import strelka.homespun.gui.ModMenuRegistry;
import strelka.homespun.gui.VesselScreen;
import strelka.homespun.renderer.CrushingTubBER;
import strelka.homespun.renderer.EvaporatingBasinBER;
import strelka.homespun.renderer.FluidStorageBER;
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
