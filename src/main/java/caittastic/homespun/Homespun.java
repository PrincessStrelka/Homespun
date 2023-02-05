package caittastic.homespun;

/*
TODO: mosaic plank blocks
TODO: pots of every dye type, with different patterns
TODO: pride flag pots with artsy names
todo: tinted leaves
todo: In world tree generation
TODO: make tomato throwable
*/

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.world.feature.ModConfiguredFeatures;
import net.minecraft.world.level.block.Blocks;
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
    ModConfiguredFeatures.CONFIGURED_FEATURES.register(bus);

    bus.addListener(this::setup);
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void setup(final FMLCommonSetupEvent event){
    event.enqueueWork(() -> ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(
            ModBlocks.IRONWOOD_SAPLING.getId(),
            ModBlocks.POTTED_IRONWOOD_SAPLING));
  }
}
