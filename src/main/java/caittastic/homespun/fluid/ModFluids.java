package caittastic.homespun.fluid;

import caittastic.homespun.Homespun;
import caittastic.homespun.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.ForgeFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModFluids{
  public static final DeferredRegister<Fluid> FLUIDS =
          DeferredRegister.create(ForgeRegistries.FLUIDS, Homespun.MOD_ID);

  public static final RegistryObject<FlowingFluid> IRONBERRY_JUICE = FLUIDS.register("ironberry_juice",
          () -> new ForgeFlowingFluid.Source(ModFluids.IRONBERRY_JUICE_PROPERTIES));
  public static final RegistryObject<FlowingFluid> FlOWING_IRONBERRY_JUICE = FLUIDS.register("ironberry_juice_flowing",
          () -> new ForgeFlowingFluid.Flowing(ModFluids.IRONBERRY_JUICE_PROPERTIES));

  public static final ForgeFlowingFluid.Properties IRONBERRY_JUICE_PROPERTIES = new ForgeFlowingFluid.Properties(
          ModFluidTypes.IRONBERRY_JUICE_FLUID_TYPE, IRONBERRY_JUICE, FlOWING_IRONBERRY_JUICE).bucket(ModItems.IRONBERRY_JUICE_BUCKET);
}
