package caittastic.homespun.gui;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModMenuRegistry{
  public static final DeferredRegister<MenuType<?>> MENUS =
          DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);

  private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(
          IContainerFactory<T> factory, String name){
    return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
  }

  /* ---------- menus ---------- */
  public static final Supplier<MenuType<VesselMenu>> VESSEL_MENU
          = registerMenuType(VesselMenu::new, "vessel_menu");

}
