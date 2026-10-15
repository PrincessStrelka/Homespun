package strelka.homespun.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class FallingLogBlock extends FallingBlock {

	public FallingLogBlock(Properties properties){
		super(properties);
		this.registerDefaultState(
			this.stateDefinition
				.any()
				.setValue(FLOATING, Boolean.FALSE)
				.setValue(AXIS, Direction.Axis.Y)
		);
	}

	public static final MapCodec<RotatedPillarBlock> CODEC = simpleCodec(RotatedPillarBlock::new);
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	protected BlockState rotate(BlockState state, Rotation rot) {
		switch (rot) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(AXIS)) {
					case X:
						return state.setValue(AXIS, Direction.Axis.Z);
					case Z:
						return state.setValue(AXIS, Direction.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}

	/* Blockstate management */
	public static final BooleanProperty FLOATING = BooleanProperty.create("floating");

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FLOATING).add(AXIS);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context){
		return this.defaultBlockState().setValue(FLOATING, Boolean.TRUE).setValue(AXIS, context.getClickedFace().getAxis());
	}

	@Override
	protected MapCodec<? extends FallingBlock> codec(){
		return null;
	}

	/* falling block management */
	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		BlockState blockbelow = level.getBlockState(pos.below());
		boolean below_is_free =
			blockbelow.isAir() ||
			blockbelow.is(BlockTags.FIRE) ||
			blockbelow.liquid() ||
			blockbelow.canBeReplaced();

		if (below_is_free &&
			pos.getY() >= level.getMinBuildHeight() &&
			!state.getValue(FLOATING)) {
			FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
			this.falling(fallingblockentity);
		}
	}

	@Override
	protected int getDelayAfterPlace(){
		return 0;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

	}
}
