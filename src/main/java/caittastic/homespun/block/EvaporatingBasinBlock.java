package caittastic.homespun.block;

import caittastic.homespun.blockentity.EvaporatingBasinBE;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.recipes.InsertFluidUsingItemRecipe;
import caittastic.homespun.recipes.TakeFluidUsingItemRecipe;
import caittastic.homespun.recipes.inputs.StackAndTankInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class EvaporatingBasinBlock extends FluidInteractingBase{
  public static final BooleanProperty EVAPORATING = BooleanProperty.create("evaporating");

  public EvaporatingBasinBlock(Properties pProperties){
    super(pProperties);
    this.registerDefaultState(this.stateDefinition.any().setValue(EVAPORATING, Boolean.FALSE));
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Item.TooltipContext pLevel, List<Component> pTooltip, TooltipFlag pFlag){
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
  public ItemInteractionResult useItemOn(ItemStack stackInHand, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult){
      FluidActionResult fluidResult;
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

        if(stackInHand.getCapability(Capabilities.FluidHandler.ITEM) == null){
          Optional<TakeFluidUsingItemRecipe> takeOutRecipe = level.getRecipeManager().getRecipeFor(
                  TakeFluidUsingItemRecipe.Type.INSTANCE,
                  new StackAndTankInput(stackInHand, fluidTank),
                  level).map(RecipeHolder::value);

          Optional<InsertFluidUsingItemRecipe> insertFluidUsingRecipe = level.getRecipeManager().getRecipeFor(
                  InsertFluidUsingItemRecipe.Type.INSTANCE,
                  new StackAndTankInput(stackInHand, fluidTank),
                  level).map(RecipeHolder::value);

          if(takeOutRecipe.isPresent()){ //try to extract fluid using item
            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.drain(takeOutRecipe.get().fluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, takeOutRecipe.get().filledItem().copy());
          } else if(insertFluidUsingRecipe.isPresent()){ // try insert fluid using item
            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.fill(insertFluidUsingRecipe.get().inputFluid(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, insertFluidUsingRecipe.get().emptyItem().copy());
          } else if(!internalStack.isEmpty()){
            level.playSound(null, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
            if(!player.isCreative())
              popResourceFromFace(level, entity.getBlockPos(), player.getDirection().getOpposite(), internalStack);
            itemHandler.extractItem(EvaporatingBasinBE.OUTPUT_SLOT, internalStack.getCount(), false);
          }
          return ItemInteractionResult.SUCCESS;
        }
      }
    return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
  }


  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType){
    return createTickerHelper(pBlockEntityType, ModBlockEntities.EVAPORATING_BASIN.get(), EvaporatingBasinBE::tick);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
    pBuilder.add(EVAPORATING);
  }

  @Override
  public void animateTick(BlockState pState, Level level, BlockPos pos, RandomSource pRandom){
    if(pState.getValue(EVAPORATING)){
      level.addParticle(ParticleTypes.EFFECT,
              pos.getX() + level.random.nextDouble(), pos.getY() + 0.3125, pos.getZ() + level.random.nextDouble(),
              0, 0, 0);
    }
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
