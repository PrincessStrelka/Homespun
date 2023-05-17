package caittastic.homespun.block;

import caittastic.homespun.blockentity.EvaporatingBasinBE;
import caittastic.homespun.blockentity.EvaporatingBasinBE;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.recipes.InsertFluidUsingItemRecipe;
import caittastic.homespun.recipes.SimpleContainerWithTank;
import caittastic.homespun.recipes.TakeFluidUsingItemRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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

public class EvaporatingBasinBlock extends FluidInteractingBase{
  public EvaporatingBasinBlock(Properties pProperties){
    super(pProperties);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag){
    pTooltip.add(Component.translatable("tooltip.homespun.evaporating_basin"));
    super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
  }

  @Override
  public @NotNull VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pos, CollisionContext context){
    return Shapes.or(Block.box(1, 0, 1, 15, 5, 15));
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
    return ModBlockEntities.EVAPORATING_BASIN.get().create(pos, state);
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult){
    if(!level.isClientSide){
      FluidActionResult fluidResult;
      ItemStack stackInHand = player.getItemInHand(hand);
      if(level.getBlockEntity(pos) instanceof EvaporatingBasinBE entity){
        FluidTank fluidTank = entity.getFluidTank();
        IItemHandler itemHandler = entity.getItemHandler();
        ItemStack internalStack = itemHandler.getStackInSlot(EvaporatingBasinBE.OUTPUT_SLOT);

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

          if(takeOutRecipe.isPresent()){ //try to extract fluid using item
            player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.drain(takeOutRecipe.get().fluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, takeOutRecipe.get().filledItem().copy());
          }
          else if(insertFluidUsingRecipe.isPresent()){ // try insert fluid using item
            player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.fill(insertFluidUsingRecipe.get().fluid(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, insertFluidUsingRecipe.get().emptyItem().copy());
          }
          else if(!internalStack.isEmpty()){
            player.level.playSound(null, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
            if(!player.isCreative())
              popResourceFromFace(player.getLevel(), entity.getBlockPos(), player.getDirection().getOpposite(), internalStack);
            itemHandler.extractItem(EvaporatingBasinBE.OUTPUT_SLOT, internalStack.getCount(), false);
          }
        }
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType){
    return createTickerHelper(pBlockEntityType, ModBlockEntities.EVAPORATING_BASIN.get(), EvaporatingBasinBE::tick);
  }

  @Override
  public void animateTick(BlockState pState, Level level, BlockPos pos, RandomSource pRandom){
    if(level.getBlockEntity(pos) instanceof EvaporatingBasinBE entity){
      if(entity.isCrafting()){
        level.addParticle(ParticleTypes.EFFECT,
                pos.getX() + level.random.nextDouble(), pos.getY() + 0.3125, pos.getZ() + level.random.nextDouble(),
                0, 0, 0);
      }
    }
    super.animateTick(pState, level, pos, pRandom);
  }

  //makes sure when the block is broken the inventory drops with it
  @Override
  public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving){
    if(pState.getBlock() != pNewState.getBlock()){
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if(blockEntity instanceof EvaporatingBasinBE){
        ((EvaporatingBasinBE)blockEntity).drops();
      }
    }
    super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
  }
}
