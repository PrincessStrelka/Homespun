package caittastic.homespun.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CrushingTub extends Block{
  public CrushingTub(Properties pProperties){
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
  public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
    //return Block.box(0, 0, 0, 16, 9, 16); //a hitbox that conforms the outline of the crushing tub
    return Shapes.or(
            Block.box(0, 0, 0, 16, 9, 1),
            Block.box(1, 0, 1, 15, 1, 15),
            Block.box(15, 0, 1, 16, 9, 15),
            Block.box(0, 0, 1, 1, 9, 15),
            Block.box(0, 0, 15, 16, 9, 16)
    );
  }
}
