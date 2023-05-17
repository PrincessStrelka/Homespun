package caittastic.homespun.events;

import caittastic.homespun.Homespun;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Homespun.MOD_ID)
public class CoreClientEvents{

  public static final Style INVISIBLE_STYLE = Style.EMPTY.withFont(new ResourceLocation(Homespun.MOD_ID, "invis"));

  private CoreClientEvents() {}

  @SubscribeEvent
  public static void handleItemTooltipEvent(ItemTooltipEvent event) {
    List<Component> tooltip = event.getToolTip();
    ItemStack stack = event.getItemStack();

      String keywordKey = stack.getDescriptionId() + ".keyword";
      if (canLocalize(keywordKey)) {
        if (tooltip.get(0) instanceof MutableComponent mutable) {
          mutable.append(getKeywordTextComponent(keywordKey));
        }
      }

  }

  public static MutableComponent getKeywordTextComponent(String key) {
    return getTextComponent(key).withStyle(INVISIBLE_STYLE);
    //return getTextComponent(key);
  }
  public static MutableComponent getTextComponent(String key) {
    //if can localize then return component.translatabble else return component.literal
    return canLocalize(key) ? Component.translatable(key) : Component.literal(key);
  }
  public static boolean canLocalize(String key) {
    return I18n.exists(key);
  }
}
