package caittastic.homespun.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PostBlock extends Block{
  public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
  public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class);

  public PostBlock(Properties pProperties){
    super(pProperties);
    this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(CONNECTED, Boolean.FALSE));
  }

  @Override
  public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction){
    return switch(direction){
      case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch(state.getValue(AXIS)){
        case X -> state.setValue(AXIS, Direction.Axis.Z);
        case Z -> state.setValue(AXIS, Direction.Axis.X);
        default -> state;
      };
      default -> super.rotate(state, level, pos, direction);
    };
  }

  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
    return switch(pState.getValue(AXIS)){
      case X -> Shapes.or(Block.box(0, 6, 6, 16, 10, 10));
      case Z -> Shapes.or(Block.box(6, 6, 0, 10, 10, 16));
      default -> Shapes.or(Block.box(6, 0, 6, 10, 16, 10));
    };
  }

  @Override
  public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos){
    if(pDirection == Direction.UP){
      if(
              pNeighborState.is(ModBlocks.IRON_POST.get()) || pNeighborState.is(ModBlocks.WOODEN_POST.get())
                      || pNeighborState.isAir()
      ){
        pLevel.setBlock(pCurrentPos, pState.setValue(CONNECTED, Boolean.FALSE), 3);
      } else{
        pLevel.setBlock(pCurrentPos, pState.setValue(CONNECTED, Boolean.TRUE), 3);
      }

    }
    return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext){
    Direction.Axis axis = pContext.getClickedFace().getAxis();
    return this.defaultBlockState().setValue(CONNECTED, Boolean.FALSE).setValue(AXIS, axis);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
    builder.add(CONNECTED).add(AXIS);

  }
}
