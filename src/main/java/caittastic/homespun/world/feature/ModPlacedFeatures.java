package caittastic.homespun.world.feature;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModPlacedFeatures{
  public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
          DeferredRegister.create(Registries.PLACED_FEATURE, Homespun.MOD_ID);

  /*     ironwood     */
  //makes it so the tree can only spawn where a sapling would survive
  public static final DeferredHolder<PlacedFeature, PlacedFeature> IRONWOOD_CHECKED = PLACED_FEATURES.register("ironwood_checked",
          () -> new PlacedFeature(ModConfiguredFeatures.IRONWOOD.getDelegate(),
                  List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.IRONWOOD_SAPLING.get()))));

  public static final DeferredHolder<PlacedFeature, PlacedFeature> IRONWOOD_PLACED =
          PLACED_FEATURES.register("ironwood_placed",
                  () -> new PlacedFeature(
                          ModConfiguredFeatures.IRONWOOD_SPAWN.getDelegate(),
                          VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05F, 3))));
  //spawns 0 trees, has 1% chance to spawn 3 extra trees. 1/chance has to be an integer

  /*     olive     */
  public static final DeferredHolder<PlacedFeature, PlacedFeature> OLIVE_CHECKED = PLACED_FEATURES.register("olive_checked",
          () -> new PlacedFeature(ModConfiguredFeatures.OLIVE.getDelegate(),
                  List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.OLIVE_SAPLING.get()))));

  public static final DeferredHolder<PlacedFeature, PlacedFeature> OLIVE_PLACED =
          PLACED_FEATURES.register("olive_placed",
                  () -> new PlacedFeature(
                          ModConfiguredFeatures.OLIVE_SPAWN.getDelegate(),
                          VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05F, 5))));
  

}
