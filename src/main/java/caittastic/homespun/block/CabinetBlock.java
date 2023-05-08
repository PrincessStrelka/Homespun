package caittastic.homespun.block;

import caittastic.homespun.block.Properties.CabinetType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CabinetBlock extends Block implements SimpleWaterloggedBlock{
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
  public static final EnumProperty<CabinetType> TYPE = EnumProperty.create("type", CabinetType.class);

  public CabinetBlock(Properties pProperties){
    super(pProperties);
    this.registerDefaultState(this.stateDefinition.any()
            .setValue(WATERLOGGED, Boolean.FALSE)
            .setValue(TYPE, CabinetType.SINGLE)
            .setValue(FACING, Direction.NORTH));
  }

  @Override
  public @NotNull VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pos, CollisionContext context){
    return Shapes.or(Block.box(1, 1, 1, 15, 15, 15));
  }

  @Override
  public @NotNull BlockState updateShape(BlockState ourState, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pCurrentPos, BlockPos pNeighborPos){
    if(ourState.getValue(WATERLOGGED))
      level.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

    System.out.println("--------------------------------------");
    //ony update if the neighbor block is a cabinet
    if(neighborState.is(this)){
      System.out.println("neighbor block is cabinet");
      if(dir == Direction.UP){
        if(ourState.getValue(TYPE) != CabinetType.TOP){
          System.out.println("change ourselves to bottom");
        }
      } else if(dir == Direction.DOWN){
        System.out.println("change ourselves to top");
      }
    }

    return super.updateShape(ourState, dir, neighborState, level, pCurrentPos, pNeighborPos);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context){
    Level level = context.getLevel();
    BlockPos clickedPos = context.getClickedPos();
    BlockState blockStateAbove = level.getBlockState(clickedPos.above());
    BlockState blockStateBelow = level.getBlockState(clickedPos.below());
    Direction blockFacingDirection = context.getHorizontalDirection().getOpposite();

    CabinetType type = CabinetType.SINGLE;
    if(!Objects.requireNonNull(context.getPlayer()).isShiftKeyDown()){
      if(blockStateBelow.is(this) &&
              blockStateBelow.getValue(FACING) == blockFacingDirection &&
              !(blockStateBelow.getValue(TYPE) == CabinetType.TOP))
        type = CabinetType.TOP;
      else if(blockStateAbove.is(this) &&
              blockStateAbove.getValue(FACING) == blockFacingDirection &&
              !(blockStateAbove.getValue(TYPE) == CabinetType.BOTTOM))
        type = CabinetType.BOTTOM;
    }

    return this.defaultBlockState()
            .setValue(WATERLOGGED, level.getFluidState(clickedPos).getType() == Fluids.WATER)
            .setValue(FACING, blockFacingDirection)
            .setValue(TYPE, type);
  }

  @Override
  public @NotNull FluidState getFluidState(BlockState pState){
    return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
    pBuilder.add(WATERLOGGED, FACING, TYPE);
  }
}
