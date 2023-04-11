package caittastic.homespun.block;

import caittastic.homespun.blockentity.VesselBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VesselBlock extends BaseEntityBlock{
  public VesselBlock(Properties properties){super(properties);}

  @Override
  public @NotNull VoxelShape getShape(
          BlockState state,
          BlockGetter pLevel,
          BlockPos pos,
          CollisionContext context){
    return Shapes.or(
            Block.box(2, 0, 2, 14, 12, 14),
            Block.box(5, 12, 5, 11, 14, 11),
            Block.box(4, 14, 4, 12, 16, 12)
    );
  }

  //stops our block from being invisable
  @Override
  public RenderShape getRenderShape(BlockState pState){
    return RenderShape.MODEL;
  }

  //makes sure when the block is broken the inventory drops with it
  @Override
  public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving){
    if(pState.getBlock() != pNewState.getBlock()){
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if(blockEntity instanceof VesselBE){
        ((VesselBE)blockEntity).drops();
      }
    }
    super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
  }

  //tries to open the container when the block is clicked
  @Override
  public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
    if(!pLevel.isClientSide()){
      BlockEntity entity = pLevel.getBlockEntity(pPos);
      if(entity instanceof VesselBE){
        NetworkHooks.openScreen(((ServerPlayer)pPlayer), (VesselBE)entity, pPos);
      }
      else{
        throw new IllegalStateException("Our Container provider is missing!");
      }
    }
    return InteractionResult.sidedSuccess(pLevel.isClientSide());
  }


  //creates the new block entity when a new blockstate is created
  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
    return new VesselBE(pPos, pState);
  }
}
