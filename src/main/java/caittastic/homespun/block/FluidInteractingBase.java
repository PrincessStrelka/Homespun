package caittastic.homespun.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FluidInteractingBase extends BaseEntityBlock{
  protected FluidInteractingBase(Properties pProperties){
    super(pProperties);
  }
  @Override
  public RenderShape getRenderShape(BlockState pState){
    return RenderShape.MODEL;
  }
  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
    return null;
  }

  void removeStackAndReplaceWith(Player player, InteractionHand hand, ItemStack stackToRemove, ItemStack stackToGive){
    if(!player.isCreative()){
      stackToRemove.shrink(1);
      if(player.getItemInHand(hand).isEmpty()){
        player.setItemInHand(hand, stackToGive);
      }
      else if(!player.getInventory().add(stackToGive))
        player.drop(stackToGive, false);
    }
  }
}
