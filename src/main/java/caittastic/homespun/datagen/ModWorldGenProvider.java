package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.extensions.IBlockAndTintGetterExtension;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OLIVE_KEY = registerConfigKey("olive");
    public static final ResourceKey<PlacedFeature> OLIVE_PLACE_KEY = registerPlaceKey("olive_placed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRONWOOD_KEY = registerConfigKey("ironwood");
    public static final ResourceKey<PlacedFeature> IRONWOOD_PLACE_KEY = registerPlaceKey("ironwood_placed");

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(Homespun.MOD_ID));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfigKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Homespun.MOD_ID, name));
    }

    public static ResourceKey<PlacedFeature> registerPlaceKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Homespun.MOD_ID, name));
    }

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> {
                context.register(IRONWOOD_KEY, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.IRONWOOD_LOG.get()),
                        new StraightTrunkPlacer(5, 8, 0),
                        BlockStateProvider.simple(ModBlocks.IRONWOOD_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines()
                        .build()));
                context.register(OLIVE_KEY, new ConfiguredFeature<>(
                        Feature.TREE,
                        new TreeConfiguration.TreeConfigurationBuilder(
                                BlockStateProvider.simple(ModBlocks.OLIVE_LOG.get()), //trunk block
                                new ForkingTrunkPlacer(2, 2, 2), //how the trunk block is placed | height, height?, unknown
                                //BlockStateProvider.simple(ModBlocks.OLIVE_LEAVES.get()), //leaves block
                                BlockStateProvider.simple(ModBlocks.OLIVE_LEAVES.get()), //leaves block
                                new BlobFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 2), //how the foliage will be placed
                                new TwoLayersFeatureSize(1, 0, 0)) //frick knows
                                .build()
                ));
            })
            .add(Registries.PLACED_FEATURE, context -> {
                HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
                context.register(OLIVE_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(OLIVE_KEY),
                        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05f, 5), ModBlocks.OLIVE_SAPLING.get())));
                context.register(IRONWOOD_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(IRONWOOD_KEY),
                        VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05f, 3), ModBlocks.IRONWOOD_SAPLING.get())));
            });
}
