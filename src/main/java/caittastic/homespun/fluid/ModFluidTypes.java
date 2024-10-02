package caittastic.homespun.fluid;

import caittastic.homespun.Homespun;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModFluidTypes{
  public static final ResourceLocation FLUID_STILL = ResourceLocation.fromNamespaceAndPath(Homespun.MOD_ID, "block/ironberry_juice_still");
  public static final ResourceLocation FLUID_FLOWING = ResourceLocation.fromNamespaceAndPath(Homespun.MOD_ID, "block/ironberry_juice_still");

  public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Homespun.MOD_ID);
  public static final Supplier<FluidType> IRONBERRY_JUICE_FLUID_TYPE = register("ironberry_juice",
          FluidType.Properties.create());

  private static Supplier<FluidType> register(String name, FluidType.Properties properties){
    return FLUID_TYPES.register(name, () -> new BaseFluidType(properties, FLUID_STILL, FLUID_FLOWING));
  }

}
