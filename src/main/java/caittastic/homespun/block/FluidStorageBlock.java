package caittastic.homespun.block;

import caittastic.homespun.blockentity.FluidStorageBE;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.recipes.InsertFluidUsingItemRecipe;
import caittastic.homespun.recipes.SimpleContainerWithTank;
import caittastic.homespun.recipes.TakeFluidUsingItemRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FluidStorageBlock extends FluidInteractingBase{
  public FluidStorageBlock(Properties pProperties){
    super(pProperties);
  }



  @Override
  public @NotNull VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pos, CollisionContext context){
    return Shapes.or(Block.box(2, 0, 2, 14, 16, 14));
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
    return ModBlockEntities.FLUID_STORAGE.get().create(pos, state);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable BlockGetter pLevel, List<Component> tooltip, TooltipFlag pFlag){
    //todo fluid stored in item
    CompoundTag nbt = BlockItem.getBlockEntityData(stack);
    if(nbt != null){
      FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
      tooltip.add(Component
              .translatable("tooltip.fluid_storage.count", fluid.getAmount())
              .append(fluid.getDisplayName().copy())
              .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
    super.appendHoverText(stack, pLevel, tooltip, pFlag);
  }

  @Override
  public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player){
    BlockEntity blockEntity = level.getBlockEntity(pos);
    if(blockEntity instanceof FluidStorageBE storageBE){
      if(!level.isClientSide && !player.isCreative()){
        ItemStack dropStack = new ItemStack(ModBlocks.FLUID_STORAGE.get());
        if(!storageBE.isEmpty())
          blockEntity.saveToItem(dropStack);
        ItemEntity dropEntity = new ItemEntity(
                level,
                (double)pos.getX() + 0.5D,
                (double)pos.getY() + 0.5D,
                (double)pos.getZ() + 0.5D,
                dropStack);
        dropEntity.setDefaultPickUpDelay();
        level.addFreshEntity(dropEntity);
      }
    }
    super.playerWillDestroy(level, pos, state, player);
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult){
    if(!level.isClientSide){
      FluidActionResult fluidResult;
      ItemStack stackInHand = player.getItemInHand(hand);
      if(level.getBlockEntity(pos) instanceof FluidStorageBE entity){
        FluidTank fluidTank = entity.getFluidTank();

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
            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.drain(takeOutRecipe.get().fluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, takeOutRecipe.get().filledItem().copy());
          }
          else if(insertFluidUsingRecipe.isPresent()){ // try insert fluid using item
            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.fill(insertFluidUsingRecipe.get().fluid(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, insertFluidUsingRecipe.get().emptyItem().copy());
          }
        }
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  
}
