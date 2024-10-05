package caittastic.homespun.blockentity;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities{
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Homespun.MOD_ID);

  public static final Supplier<BlockEntityType<CrushingTubBE>> CRUSHING_TUB =
          BLOCK_ENTITIES.register("crushing_tub_block_entity", () ->
                  BlockEntityType.Builder
                          .of(CrushingTubBE::new, ModBlocks.CRUSHING_TUB.get())
                          .build(null));

  public static final Supplier<BlockEntityType<EvaporatingBasinBE>> EVAPORATING_BASIN =
          BLOCK_ENTITIES.register("evaporating_basin_block_entity", () ->
                  BlockEntityType.Builder
                          .of(EvaporatingBasinBE::new, ModBlocks.EVAPORATING_BASIN.get())
                          .build(null));

  public static final Supplier<BlockEntityType<FluidStorageBE>> FLUID_STORAGE =
          BLOCK_ENTITIES.register("fluid_storage_block_entity", () ->
                  BlockEntityType.Builder
                          .of(FluidStorageBE::new, ModBlocks.FLUID_STORAGE.get())
                          .build(null));

  public static final Supplier<BlockEntityType<VesselBE>> VESSEL =
          BLOCK_ENTITIES.register("vessel_block_entity", () ->
                  BlockEntityType.Builder
                          .of(VesselBE::new,
                                  ModBlocks.CERAMIC_VESSEL.get(),
                                  ModBlocks.VESSEL_MAP.get("ashen").get(),
                                  ModBlocks.VESSEL_MAP.get("cerulean").get(),
                                  ModBlocks.VESSEL_MAP.get("ivory").get(),
                                  ModBlocks.VESSEL_MAP.get("roseate").get(),
                                  ModBlocks.VESSEL_MAP.get("verdant").get(),
                                  ModBlocks.VESSEL_MAP.get("violaceous").get())
                          .build(null));

}
