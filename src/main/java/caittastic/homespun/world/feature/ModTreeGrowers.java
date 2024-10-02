package caittastic.homespun.world.feature;

import caittastic.homespun.datagen.ModWorldGenProvider;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower OLIVE = new TreeGrower("olive", Optional.empty(), Optional.of(ModWorldGenProvider.OLIVE_KEY), Optional.empty());
    public static final TreeGrower IRONWOOD = new TreeGrower("ironwood", Optional.empty(), Optional.of(ModWorldGenProvider.IRONWOOD_KEY), Optional.empty());

}
