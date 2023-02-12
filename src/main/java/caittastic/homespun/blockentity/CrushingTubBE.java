package caittastic.homespun.blockentity;

import caittastic.homespun.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrushingTubBE extends BlockEntity{
  public CrushingTubBE(BlockPos pos, BlockState state){
    super(BlockEntities.CRUSHING_TUB.get(), pos, state);
  }

  public static void tick(Level level, BlockPos blockPos, BlockState state, CrushingTubBE entity){

  }

  public ItemStack getOutputRenderStack(){
    return ModItems.TINY_IRON_DUST.get().getDefaultInstance();
  }
}
