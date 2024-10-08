package caittastic.homespun.events;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.blockentity.*;
import caittastic.homespun.item.ModItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Homespun.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            //needs to be the first thing in the enqueuework else it wont work
            /*     potted     */
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.IRONWOOD_SAPLING.getId(), ModBlocks.POTTED_IRONWOOD_SAPLING);
            ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.OLIVE_SAPLING.getId(), ModBlocks.POTTED_OLIVE_SAPLING);
            /*     compostable     */
            //30% / 0.3f | seeds, saplings, leaves
            ComposterBlock.COMPOSTABLES.put(ModBlocks.IRONWOOD_LEAVES.get().asItem(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.OLIVE_LEAVES.get().asItem(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.IRONWOOD_SAPLING.get().asItem(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.OLIVE_SAPLING.get().asItem(), 0.3f);
            //65% / 0.65f | fruit, vegetables, grains, roots, mushrooms, flowers
            ComposterBlock.COMPOSTABLES.put(ModItems.OLIVES.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModItems.IRONBERRIES.get(), 0.65f);
            //85% / 0.85f | processed foods, compressed crop blocks
        });
    }

    @SubscribeEvent
    public static void attachCaps(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.CRUSHING_TUB.get(), CrushingTubBE::getItemCap);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.CRUSHING_TUB.get(), CrushingTubBE::getFluidCap);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.EVAPORATING_BASIN.get(), EvaporatingBasinBE::getItemCap);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.EVAPORATING_BASIN.get(), EvaporatingBasinBE::getFluidCap);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.FLUID_STORAGE.get(), FluidStorageBE::getFluidCap);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.VESSEL.get(), VesselBE::getItemCap);
    }
}
