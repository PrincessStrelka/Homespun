package caittastic.homespun.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class FlammableLogBlock extends RotatedPillarBlock{
  public FlammableLogBlock(Properties p_55926_){
    super(p_55926_);
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
    if(context.getItemInHand().getItem() instanceof AxeItem){
      if(state.is(ModBlocks.IRONWOOD_LOG.get())){
        return returnBlockWithSameAxis(state, ModBlocks.STRIPPED_IRONWOOD_LOG);
      }
      else if(state.is(ModBlocks.IRONWOOD_WOOD.get())){
        return returnBlockWithSameAxis(state, ModBlocks.STRIPPED_IRONWOOD_WOOD);
      }
      if(state.is(ModBlocks.OLIVE_LOG.get())){
        return returnBlockWithSameAxis(state, ModBlocks.STRIPPED_OLIVE_LOG);
      }
      else if(state.is(ModBlocks.OLIVE_WOOD.get())){
        return returnBlockWithSameAxis(state, ModBlocks.STRIPPED_OLIVE_WOOD);
      }
    }
    return super.getToolModifiedState(state, context, toolAction, simulate);
  }

  private BlockState returnBlockWithSameAxis(BlockState state, RegistryObject<Block> blockToReturn){
    return blockToReturn.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
  }
}
