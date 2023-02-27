package caittastic.homespun.block;

import caittastic.homespun.blockentity.BlockEntities;
import caittastic.homespun.blockentity.CrushingTubBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
    //single cuboid
    return Shapes.or(Block.box(0, 0, 0, 16, 9, 16));

    //two thick walls
    /*
    return Shapes.or(
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 2, 0, 16, 9, 2),
            Block.box(0, 2, 14, 16, 9, 16),
            Block.box(0, 2, 2, 2, 9, 14),
            Block.box(14, 2, 2, 16, 9, 14)
    );
     */

    //one thick wall
    /*
    return Shapes.or(
            Block.box(0, 0, 0, 16, 9, 1),
            Block.box(1, 0, 1, 15, 1, 15),
            Block.box(15, 0, 1, 16, 9, 15),
            Block.box(0, 0, 1, 1, 9, 15),
            Block.box(0, 0, 15, 16, 9, 16)
    );
     */
  }

  @Override
  public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance){
    if(!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof CrushingTubBE entity){
      entity.doCraft();
    }
    super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
  }

  //makes sure when the block is broken the inventory drops with it
  @Override
  public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving){
    if(pState.getBlock() != pNewState.getBlock()){
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if(blockEntity instanceof CrushingTubBE){
        ((CrushingTubBE)blockEntity).drops();
      }
    }
    super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
  }

  @Override
  public InteractionResult use(BlockState pState, Level pLevel, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHit){
    BlockEntity blockentity = pLevel.getBlockEntity(pos);
    if(blockentity instanceof CrushingTubBE entity){
      ItemStack stackInHand = player.getItemInHand(hand);
      //if the inventory has a stack currently, put move stack into players inventory then return success
      //shift right click should empty fluid in the inventory
      //using with a bucket should fill the bucket with fluid in fluid tank


      //this checks if an itemstack is in a recipe, when crushing tub recipes are implemented do this
      //Optional<CampfireCookingRecipe> optional = campfireblockentity.getCookableRecipe(itemstack);
      if(!pLevel.isClientSide && entity.placeItem(player, player.getAbilities().instabuild ? stackInHand.copy() : stackInHand)){
        pLevel.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.NEUTRAL, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
      }
      return InteractionResult.CONSUME;
    }
    return InteractionResult.PASS;
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
