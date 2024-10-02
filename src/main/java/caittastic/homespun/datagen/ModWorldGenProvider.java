package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldGenProvider {
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBBER_TREE_KEY = registerConfigKey("rubber_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfigKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Homespun.MOD_ID, name));
    }

    public static ResourceKey<PlacedFeature> registerPlaceKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Homespun.MOD_ID, name));
    }
}
