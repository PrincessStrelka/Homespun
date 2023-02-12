package caittastic.homespun.block;

import caittastic.homespun.blockentity.BlockEntities;
import caittastic.homespun.blockentity.CrushingTubBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrushingTubBlock extends BaseEntityBlock{
  public CrushingTubBlock(Properties pProperties){
    super(pProperties);
  }

  @Override
  public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face){
    return true;
  }

  @Override
  public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face){
    return 5;
  }

  @Override
  public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face){
    return 5;
  }

  @Override
  public @NotNull VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pos, CollisionContext context){
    return Shapes.or(
            Block.box(0, 0, 0, 16, 9, 1),
            Block.box(1, 0, 1, 15, 1, 15),
            Block.box(15, 0, 1, 16, 9, 15),
            Block.box(0, 0, 1, 1, 9, 15),
            Block.box(0, 0, 15, 16, 9, 16)
    );
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
    return BlockEntities.CRUSHING_TUB.get().create(pos, state);
  }

  @Override
  public RenderShape getRenderShape(BlockState pState){
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType){
    return createTickerHelper(entityType, BlockEntities.CRUSHING_TUB.get(), CrushingTubBE::tick);
  }
}
