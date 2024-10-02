package caittastic.homespun.world.feature;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModConfiguredFeatures{
  public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
          DeferredRegister.create(Registries.CONFIGURED_FEATURE, Homespun.MOD_ID);
  /*     ironwood     */
  public static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> IRONWOOD =
          CONFIGURED_FEATURES.register(
                  "ironwood",
                  () -> new ConfiguredFeature<>(
                          Feature.TREE,
                          new TreeConfiguration.TreeConfigurationBuilder(
                                  BlockStateProvider.simple(ModBlocks.IRONWOOD_LOG.get()),
                                  new StraightTrunkPlacer(5, 8, 0),
                                  BlockStateProvider.simple(ModBlocks.IRONWOOD_LEAVES.get()),
                                  new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                                  new TwoLayersFeatureSize(1, 0, 1))
                                  .ignoreVines()
                                  .build()
                  )
          );
  //does some magic nonsense that mr tutorial man doesnt understand
  public static final DeferredHolder<ConfiguredFeature<?,?>, ConfiguredFeature<?, ?>> IRONWOOD_SPAWN =
          CONFIGURED_FEATURES.register("ironwood_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                  new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                          ModPlacedFeatures.IRONWOOD_CHECKED.getDelegate(),
                          0.5f)), ModPlacedFeatures.IRONWOOD_CHECKED.getDelegate())));

  /*     olive     */
  public static final DeferredHolder<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> OLIVE =
          CONFIGURED_FEATURES.register(
                  "olive",
                  () -> new ConfiguredFeature<>(
                          Feature.TREE,
                          new TreeConfiguration.TreeConfigurationBuilder(
                                  BlockStateProvider.simple(ModBlocks.OLIVE_LOG.get()), //trunk block
                                  new ForkingTrunkPlacer(2, 2, 2), //how the trunk block is placed | height, height?, unknown
                                  //BlockStateProvider.simple(ModBlocks.OLIVE_LEAVES.get()), //leaves block
                                  BlockStateProvider.simple(ModBlocks.OLIVE_LEAVES.get()), //leaves block
                                  new BlobFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 2), //how the foliage will be placed
                                  new TwoLayersFeatureSize(1, 0, 0)) //frick knows
                                  .build()
                  )
          );
  //does some magic nonsense that mr tutorial man doesnt understand
  public static final DeferredHolder<ConfiguredFeature<?,?>, ConfiguredFeature<?, ?>> OLIVE_SPAWN =
          CONFIGURED_FEATURES.register("olive_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                  new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                          ModPlacedFeatures.OLIVE_CHECKED.getDelegate(),
                          0.5f)), ModPlacedFeatures.OLIVE_CHECKED.getDelegate())));

}
