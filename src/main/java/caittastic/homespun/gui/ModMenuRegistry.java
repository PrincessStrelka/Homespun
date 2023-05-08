package caittastic.homespun.gui;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModMenuRegistry{
  public static final DeferredRegister<MenuType<?>> MENUS =
          DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

  private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(
          IContainerFactory<T> factory, String name){
    return MENUS.register(name, () -> IForgeMenuType.create(factory));
  }

  /* ---------- menus ---------- */
  public static final RegistryObject<MenuType<VesselMenu>> VESSEL_MENU
          = registerMenuType(VesselMenu::new, "vessel_menu");

}
