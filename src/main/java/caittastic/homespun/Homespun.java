package caittastic.homespun;

/*
TODO: make tomato throwable
todo: implement crushing tub recipes for iron dust

idea: crushing tup recipe for crushed acorns
crushed acorns used to make bread and speed potion (coffee)

idea: forestry like charcoal firepits to get high yeilds of charcoal and ash

idea: using charcoal to make fertile soil

idea: japanese water hammer, monjolo, uses the weight of flowing water to make the hammer end rise up, and then the water pours out and the hammer smacks back down, could be used to automate crushing tub recipes
look up videos of these in action to see how they could be added
these are similar to shishi-odochi / deer-scarer

idea: tallow as an alternate recipe for candles

idea: tallow used to make pemmican, energy giving but not verry stomach sating food

idea: leaves in crushing tub gives you their drops at a higher chance (simulate fortune?)

idea: calcite and tuff walls

idea: pride flag pots with artsy names

idea: mosaic blocks for ironwood and olive

idea: drying basin should probably be able to be sped up by having lava/fire/magma blocks underneath it
*/

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.world.feature.ModConfiguredFeatures;
import caittastic.homespun.world.feature.ModPlacedFeatures;
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
    ModPlacedFeatures.PLACED_FEATURES.register(bus);

    bus.addListener(this::setup);
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void setup(final FMLCommonSetupEvent event){
    event.enqueueWork(() -> ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(
            ModBlocks.IRONWOOD_SAPLING.getId(),
            ModBlocks.POTTED_IRONWOOD_SAPLING));
  }
}
