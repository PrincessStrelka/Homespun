package caittastic.homespun.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PostBlock extends Block{
  public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");

  public PostBlock(Properties pProperties){
    super(pProperties);
  }

  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
    return Shapes.or(Block.box(6, 0, 6, 10, 16, 10));

  }

  @Override
  public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos){
    if(pDirection == Direction.UP){
      if(pNeighborState.is(ModBlocks.IRON_POST.get()) || pNeighborState.isAir() || pNeighborState.is(ModBlocks.WOODEN_POST.get())){
        pLevel.setBlock(pCurrentPos, pState.setValue(CONNECTED, Boolean.FALSE), 3);
      }
      else{
        pLevel.setBlock(pCurrentPos, pState.setValue(CONNECTED, Boolean.TRUE), 3);
      }

    }


    return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext){
    return this.defaultBlockState().setValue(CONNECTED, Boolean.FALSE);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
    builder.add(CONNECTED);
  }
}
