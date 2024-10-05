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
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.fluid.ModFluidTypes;
import caittastic.homespun.fluid.ModFluids;
import caittastic.homespun.gui.ModMenuRegistry;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.recipes.ModRecipes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Homespun.MOD_ID)
public class Homespun{
  public static final String MOD_ID = "homespun";

  public Homespun(IEventBus bus, ModContainer container) {
    ModItems.ITEMS.register(bus);
    ModBlocks.BLOCKS.register(bus);
    ModBlockEntities.BLOCK_ENTITIES.register(bus);
    ModTabs.CREATIVE_MODE_TABS.register(bus);

    ModMenuRegistry.MENUS.register(bus);

    ModRecipes.SERIALIZERS.register(bus);

    ModFluids.FLUIDS.register(bus);
    ModFluidTypes.FLUID_TYPES.register(bus);
  }
}
