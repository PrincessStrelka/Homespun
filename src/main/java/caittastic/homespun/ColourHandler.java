package caittastic.homespun;

import caittastic.homespun.block.ModBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Homespun.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ColourHandler {
  @SubscribeEvent
  static void registerBlockColours(RegisterColorHandlersEvent.Block event){
    event.register((blockState, getter, blockPos, tintIndex) -> getter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(getter, blockPos) : FoliageColor.getDefaultColor(), ModBlocks.OLIVE_LEAVES.get());
  }

  @SubscribeEvent
  public static void registerItemColors(RegisterColorHandlersEvent.Item event){
    BlockColors blockColors = event.getBlockColors();
    event.register((stack, tintIndex) -> {if(stack.getItem() instanceof BlockItem blockItem) return blockColors.getColor(blockItem.getBlock().defaultBlockState(), null, null, tintIndex);return 0xFFFFFF;}, ModBlocks.OLIVE_LEAVES.get());
  }
}
