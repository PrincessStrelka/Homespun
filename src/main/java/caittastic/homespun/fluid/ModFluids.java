package caittastic.homespun.fluid;

import caittastic.homespun.Homespun;
import caittastic.homespun.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids{
  public static final DeferredRegister<Fluid> FLUIDS =
          DeferredRegister.create(BuiltInRegistries.FLUID, Homespun.MOD_ID);

  public static final Supplier<FlowingFluid> IRONBERRY_JUICE = FLUIDS.register("ironberry_juice",
          () -> new BaseFlowingFluid.Source(ModFluids.IRONBERRY_JUICE_PROPERTIES));
  public static final Supplier<FlowingFluid> FlOWING_IRONBERRY_JUICE = FLUIDS.register("ironberry_juice_flowing",
          () -> new BaseFlowingFluid.Flowing(ModFluids.IRONBERRY_JUICE_PROPERTIES));

  public static final BaseFlowingFluid.Properties IRONBERRY_JUICE_PROPERTIES = new BaseFlowingFluid.Properties(
          ModFluidTypes.IRONBERRY_JUICE_FLUID_TYPE, IRONBERRY_JUICE, FlOWING_IRONBERRY_JUICE).bucket(ModItems.IRONBERRY_JUICE_BUCKET);
}
