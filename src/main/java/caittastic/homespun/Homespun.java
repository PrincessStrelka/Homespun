package caittastic.homespun;

/*
TODO: make tomato throwable
idea: japanese water hammer, monjolo, uses the weight of flowing water to make the hammer end rise up, and then the water pours out and the hammer smacks back down, could be used to automate crushing tub recipes
look up videos of these in action to see how they could be added
these are similar to shishi-odochi / deer-scarer
GameRegistry.registerTileEntity(TileEntityApiary.class, new ResourceLocation(Rustic.MODID , "tileEntityApiary"))
GameRegistry.registerTileEntity(TileEntityCondenser.class, new ResourceLocation(Rustic.MODID, "tileEntityCondenser"))
GameRegistry.registerTileEntity(TileEntityCondenserAdvancedBottom.class, new ResourceLocation(Rustic.MODID, "tileEntityCondenserAdvanced"))
GameRegistry.registerTileEntity(TileEntityCondenserAdvancedTop.class, new ResourceLocation(Rustic.MODID, "tileEntityCondenserAdvancedProxy"))
GameRegistry.registerTileEntity(TileEntityBrewingBarrel.class, new ResourceLocation(Rustic.MODID, "tileEntityBrewingBarrel"))
*/

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.blockentity.BlockEntities;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.networking.ModPackets;
import caittastic.homespun.world.feature.ModConfiguredFeatures;
import caittastic.homespun.world.feature.ModPlacedFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Homespun.MOD_ID)
public class Homespun{
  public static final String MOD_ID = "homespun";

  public Homespun(){
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    ModItems.ITEM.register(bus);
    ModBlocks.BLOCKS.register(bus);
    BlockEntities.BLOCK_ENTITIES.register(bus);

    ModConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
    ModPlacedFeatures.PLACED_FEATURES.register(bus);

    bus.addListener(this::setup);
    MinecraftForge.EVENT_BUS.register(this);

  }

  private void setup(final FMLCommonSetupEvent event){
    event.enqueueWork(() -> {
      //needs to be the first thing in the enqueuework else it wont work
      ModPackets.register();
      /*     potted     */
      ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.IRONWOOD_SAPLING.getId(), ModBlocks.POTTED_IRONWOOD_SAPLING);
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
}
