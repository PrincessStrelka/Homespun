package caittastic.homespun.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class FlammableLogBlock extends RotatedPillarBlock{
  public FlammableLogBlock(Properties properties){
    super(properties);
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
  public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate){
    if(toolAction == ToolActions.AXE_STRIP){
      if(state.is(ModBlocks.IRONWOOD_LOG.get())){
        return ModBlocks.STRIPPED_IRONWOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
      }
      else if(state.is(ModBlocks.IRONWOOD_WOOD.get())){
        return ModBlocks.STRIPPED_IRONWOOD_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
      }
      if(state.is(ModBlocks.OLIVE_LOG.get())){
        return ModBlocks.STRIPPED_OLIVE_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
      }
      else if(state.is(ModBlocks.OLIVE_WOOD.get())){
        return ModBlocks.STRIPPED_OLIVE_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
      }
    }
    return super.getToolModifiedState(state, context, toolAction, simulate);
  }

}
