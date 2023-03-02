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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
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
    //two thick walls, means flallOn doesnt work but aesthetically feels better
    /*
    return Shapes.or(
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 2, 0, 16, 9, 2),
            Block.box(0, 2, 14, 16, 9, 16),
            Block.box(0, 2, 2, 2, 9, 14),
            Block.box(14, 2, 2, 16, 9, 14)
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
  public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult){
    if(!level.isClientSide){
      BlockEntity blockentity = level.getBlockEntity(pos);
      ItemStack stackInHand = player.getItemInHand(hand);
      if(blockentity instanceof CrushingTubBE entity){
        FluidActionResult fluidResult;

        //try to bucket in to/out of
        fluidResult = tryInsertFluidFromItemToEntity(player, stackInHand, entity);
        if(fluidResult.isSuccess())
          player.setItemInHand(hand, fluidResult.getResult());

        fluidResult = tryTakeFluidFromEntityToItem(player, stackInHand, entity);
        if(fluidResult.isSuccess())
          player.setItemInHand(hand, fluidResult.getResult());

        if(!stackInHand.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()){
          //try insert fluid with bottle
          //TODO: make this into recipe
          if(stackInHand.getItem() == Items.GLASS_BOTTLE){
            if(entity.getStoredFluidStack().getFluid() == Fluids.WATER){
              entity.getFluidTank().drain(250, IFluidHandler.FluidAction.EXECUTE);
              level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
              ItemUtils.createFilledResult(stackInHand, player, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
            }
            if(entity.getStoredFluidStack().getFluid() == Fluids.LAVA){
              entity.getFluidTank().drain(250, IFluidHandler.FluidAction.EXECUTE);
              level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
              ItemUtils.createFilledResult(stackInHand, player, Items.HONEY_BOTTLE.getDefaultInstance());
            }
          }
          //try extract fluid with bottle
        }


        //try to place items in/take items
        /*
        if(entity.tryPlaceOrTakeOrBucket(player, player.getAbilities().instabuild ? stackInHand.copy() : stackInHand)){
          return InteractionResult.SUCCESS;
        }
         */

      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide);

  }

  @NotNull
  private FluidActionResult tryTakeFluidFromEntityToItem(Player player, ItemStack stackInHand, CrushingTubBE entity){
    return FluidUtil.tryFillContainerAndStow(
            stackInHand,
            entity.getFluidTank(),
            new InvWrapper(player.getInventory()),
            1000,
            player,
            true);
  }

  @NotNull
  private FluidActionResult tryInsertFluidFromItemToEntity(Player player, ItemStack stackInHand, CrushingTubBE entity){
    return FluidUtil.tryEmptyContainerAndStow(
            stackInHand,
            entity.getFluidTank(),
            new InvWrapper(player.getInventory()),
            1000,
            player,
            true);
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
}
