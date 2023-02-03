package caittastic.homespun;

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.world.feature.ModConfiguredFeatures;
import caittastic.homespun.world.feature.ModPlacedFeatures;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/*
TODO: mosaic plank blocks
TODO: pots of every dye type, with different patterns
*/

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Homespun.MOD_ID)
public class Homespun {
    public static final String MOD_ID = "homespun"; // Define mod id in a common place for everything to reference

    public Homespun() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEM.register(bus); // register the register for the items so the items get registered
        ModBlocks.BLOCK.register(bus); // Register the Deferred Register to the mod event bus so blocks get registered
        ModPlacedFeatures.PLACED_FEATURES.register(bus); //register the deffered register for features placed in worldgen
        ModConfiguredFeatures.CONFIGURED_FEATURES.register(bus); //register the register for feature configuration


        bus.addListener(this::clientSetup);
        bus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.IRONWOOD_SAPLING.getId(), ModBlocks.POTTED_IRONWOOD_SAPLING);
        });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }

    private void clientSetup(final FMLCommonSetupEvent event){
        //ironwood
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRONWOOD_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRONWOOD_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_IRONWOOD_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRONWOOD_TRAPDOOR.get(), RenderType.cutout());

        //olive
        /*
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_OLIVE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_TRAPDOOR.get(), RenderType.cutout());
         */





    }
}
//todo pride flag pots with artsy names
