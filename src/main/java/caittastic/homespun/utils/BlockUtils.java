package caittastic.homespun.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

public final class BlockUtils {
    public static <T> T getCapability(BlockCapability<T, @Nullable Direction> capability, BlockEntity be) {
        return getCapability(capability, be, null);
    }

    public static <T> T getCapability(BlockCapability<T, @Nullable Direction> capability, BlockEntity be, @Nullable Direction direction) {
        return be.getLevel().getCapability(capability, be.getBlockPos(), be.getBlockState(), be, direction);
    }
}
