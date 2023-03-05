package caittastic.homespun.block;

import caittastic.homespun.blockentity.CrushingTubBE;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.recipes.InsertFluidUsingItemRecipe;
import caittastic.homespun.recipes.SimpleContainerWithTank;
import caittastic.homespun.recipes.TakeFluidUsingItemRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static caittastic.homespun.blockentity.CrushingTubBE.CRAFT_SLOT;

public class CrushingTubBlock extends BaseEntityBlock{
  public CrushingTubBlock(Properties pProperties){
    super(pProperties);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag){
    pTooltip.add(Component.translatable("tooltip.homespun.crushing_tub"));
    super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
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
  public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity fallEntity, float pFallDistance){
    if(!pLevel.isClientSide && !(fallEntity instanceof ItemEntity) && pLevel.getBlockEntity(pPos) instanceof CrushingTubBE entity){
      entity.doCraft();
    }
    super.fallOn(pLevel, pState, pPos, fallEntity, pFallDistance);
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
          Optional<TakeFluidUsingItemRecipe> takeOutRecipe = level.getRecipeManager().getRecipeFor(
                  TakeFluidUsingItemRecipe.Type.INSTANCE,
                  new SimpleContainerWithTank(fluidTank, stackInHand),
                  level);

          Optional<InsertFluidUsingItemRecipe> insertFluidUsingRecipe = level.getRecipeManager().getRecipeFor(
                  InsertFluidUsingItemRecipe.Type.INSTANCE,
                  new SimpleContainerWithTank(fluidTank, stackInHand),
                  level);

          if(takeOutRecipe.isPresent()){ //try to take out using item
            player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.drain(takeOutRecipe.get().fluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, takeOutRecipe.get().filledItem().copy());
          }
          else if(insertFluidUsingRecipe.isPresent()){ // try put in fluid using item
            player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.fill(insertFluidUsingRecipe.get().fluid(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, insertFluidUsingRecipe.get().emptyItem().copy());
          }
          else{ //try to insert/remove items
            if(internalStack.isEmpty() || (internalItem == itemInHand && (internalStack.getCount() < internalStack.getMaxStackSize()))){
              System.out.println("insert");
              player.level.playSound(null, player.blockPosition(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
              int amountToInsert = Math.min(stackInHand.getCount(), internalStack.getMaxStackSize() - internalStack.getCount());
              itemHandler.insertItem(CRAFT_SLOT, new ItemStack(itemInHand, amountToInsert), false);
              entity.setChanged();
              entity.getLevel().sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 3);
              if(!player.isCreative())
                stackInHand.shrink(amountToInsert);
            }
            else{
              System.out.println("try extract");
              player.level.playSound(null, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
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
      if(player.getItemInHand(hand).isEmpty()){
        player.setItemInHand(hand, stackToGive);
        System.out.println("player given:" + stackToGive);
      }
      else if(!player.getInventory().add(stackToGive))
        player.drop(stackToGive, false);
    }
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
    return ModBlockEntities.CRUSHING_TUB.get().create(pos, state);
  }

  @Override
  public RenderShape getRenderShape(BlockState pState){
    return RenderShape.MODEL;
  }
}
