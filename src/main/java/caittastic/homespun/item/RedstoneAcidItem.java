package caittastic.homespun.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RedstoneAcidItem extends Item{
  public RedstoneAcidItem(Properties pProperties){
    super(pProperties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context){
    Level level = context.getLevel();
    BlockPos clickedPos = context.getClickedPos();
    Player player = context.getPlayer();
    ItemStack itemInHand = context.getItemInHand();

    Block[][] recipes = {
            {Blocks.COBBLESTONE, Blocks.GRAVEL},

            {Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER},
            {Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER},
            {Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB},
            {Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS},

            {Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER},
            {Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER},
            {Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB},
            {Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS},

            {Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER},
            {Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER},
            {Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB},
            {Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS}
    };


    for(Block[] blocks: recipes){
      Block inputBlock = blocks[0];
      Block outputBlock = blocks[1];

      if(!level.isClientSide() && level.getBlockState(clickedPos).getBlock() == inputBlock){
        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, clickedPos, itemInHand);

        level.setBlock(clickedPos, outputBlock.defaultBlockState(), 3);
        level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        removeStackAndReplaceWith(player, context.getHand(), itemInHand, new ItemStack(Items.GLASS_BOTTLE));
        return InteractionResult.SUCCESS;
      }
    }

    return super.useOn(context);
  }

  private void removeStackAndReplaceWith(Player player, InteractionHand hand, ItemStack stackToRemove, ItemStack stackToGive){
    ItemStack givenStack = stackToGive.copy();
    if(!player.isCreative()){
      stackToRemove.shrink(1);
      if(player.getItemInHand(hand).isEmpty()){
        player.setItemInHand(hand, givenStack);
      }
      else if(!player.getInventory().add(givenStack))
        player.drop(givenStack, false);
    }
  }
}
