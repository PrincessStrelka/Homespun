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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static caittastic.homespun.blockentity.CrushingTubBE.CRAFT_SLOT;

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
      FluidActionResult fluidResult;
      ItemStack stackInHand = player.getItemInHand(hand);
      Item itemInHand = stackInHand.getItem();
      if(level.getBlockEntity(pos) instanceof CrushingTubBE entity){
        FluidTank fluidTank = entity.getFluidTank();
        IItemHandler itemHandler = entity.getItemHandler();
        ItemStack internalStack = itemHandler.getStackInSlot(CRAFT_SLOT);
        Item internalItem = internalStack.getItem();


        //try to bucket in to/out of
        fluidResult = FluidUtil.tryEmptyContainerAndStow(stackInHand, fluidTank, new InvWrapper(player.getInventory()), 1000, player, true);
        if(fluidResult.isSuccess())
          player.setItemInHand(hand, fluidResult.getResult());

        fluidResult = FluidUtil.tryFillContainerAndStow(stackInHand, fluidTank, new InvWrapper(player.getInventory()), 1000, player, true);
        if(fluidResult.isSuccess())
          player.setItemInHand(hand, fluidResult.getResult());

        if(!stackInHand.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()){
          //try extract fluid with bottle
          //TODO: make this into recipe

          if(itemInHand.equals(Items.GLASS_BOTTLE)){
            if(entity.getStoredFluidStack().getFluid() == Fluids.WATER){
              fluidTank.drain(250, IFluidHandler.FluidAction.EXECUTE);
              level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
              removeStackAndReplaceWith(player, hand, stackInHand, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
            }
            if(entity.getStoredFluidStack().getFluid() == Fluids.LAVA){
              fluidTank.drain(250, IFluidHandler.FluidAction.EXECUTE);
              level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
              removeStackAndReplaceWith(player, hand, stackInHand, Items.HONEY_BOTTLE.getDefaultInstance());
            }
          }
          else{
            //try insert fluid with bottle
            if(itemInHand.equals(Items.POTION)){
              int leftovers = fluidTank.fill(new FluidStack(Fluids.WATER, 250), IFluidHandler.FluidAction.SIMULATE);
              if(leftovers > 0){
                fluidTank.fill(new FluidStack(Fluids.WATER, 250), IFluidHandler.FluidAction.EXECUTE);
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
                removeStackAndReplaceWith(player, hand, stackInHand, Items.GLASS_BOTTLE.getDefaultInstance());
              }
            }
            if(itemInHand.equals(Items.HONEY_BOTTLE)){
              int leftovers = fluidTank.fill(new FluidStack(Fluids.LAVA, 250), IFluidHandler.FluidAction.SIMULATE);
              if(leftovers > 0){
                fluidTank.fill(new FluidStack(Fluids.LAVA, 250), IFluidHandler.FluidAction.EXECUTE);
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
                removeStackAndReplaceWith(player, hand, stackInHand, Items.GLASS_BOTTLE.getDefaultInstance());
              }
            }


            //try to place items in/take items
            int internalMaxStackSize = internalStack.getMaxStackSize();

            //if we should be able to place into the container
            if(internalStack.isEmpty() || ((internalItem == itemInHand) && (internalStack.getCount() < internalMaxStackSize))){
              //find the smallest between the amount of items in hand and the remaining items untill stack stored is full
              int amountToInsert = Math.min(stackInHand.getCount(), internalMaxStackSize - internalStack.getCount());
              //insert amount to insert of held item into container
              itemHandler.insertItem(CRAFT_SLOT, new ItemStack(itemInHand, amountToInsert), false);
              //extract amount to insert from held item
              if(!player.isCreative())
                stackInHand.shrink(amountToInsert);
              level.playSound(player, player.blockPosition(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1.0F, 1.0F);
              level.gameEvent(GameEvent.BLOCK_CHANGE, entity.getBlockPos(), GameEvent.Context.of(player, entity.getBlockState()));
              entity.setChanged();
              entity.getLevel().sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 3);
            }
            else{
              //drop the internal stack
              if(!player.isCreative())
                popResourceFromFace(player.getLevel(), entity.getBlockPos(), player.getDirection().getOpposite(), internalStack);
              itemHandler.extractItem(CRAFT_SLOT, internalStack.getCount(), false);
            }
          }
        }
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide);

  }

  private void removeStackAndReplaceWith(Player player, InteractionHand hand, ItemStack stackToRemove, ItemStack stackToGive){
    if(!player.isCreative()){
      stackToRemove.shrink(1);
      if(player.getItemInHand(hand).isEmpty())
        player.setItemInHand(hand, stackToGive);
      else if(!player.getInventory().add(stackToGive))
        player.drop(stackToGive, false);
    }
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
