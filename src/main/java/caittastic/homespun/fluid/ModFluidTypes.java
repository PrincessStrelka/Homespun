package caittastic.homespun.fluid;

import caittastic.homespun.Homespun;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModFluidTypes{
  public static final ResourceLocation FLUID_STILL = new ResourceLocation(Homespun.MOD_ID, "block/ironberry_juice_still");
  public static final ResourceLocation FLUID_FLOWING = new ResourceLocation(Homespun.MOD_ID, "block/ironberry_juice_still");

  public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Homespun.MOD_ID);
  public static final RegistryObject<FluidType> IRONBERRY_JUICE_FLUID_TYPE = register("ironberry_juice",
          FluidType.Properties.create());

  private static RegistryObject<FluidType> register(String name, FluidType.Properties properties){
    return FLUID_TYPES.register(name, () -> new BaseFluidType(properties, FLUID_STILL, FLUID_FLOWING));
  }

}
