package caittastic.homespun.block;

import caittastic.homespun.blockentity.FluidStorageBE;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.recipes.InsertFluidUsingItemRecipe;
import caittastic.homespun.recipes.TakeFluidUsingItemRecipe;
import caittastic.homespun.recipes.inputs.StackAndTankInput;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
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
  public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext pLevel, List<Component> tooltip, TooltipFlag pFlag){
    //todo fluid stored in item
    CustomData nbt = stack.get(DataComponents.BLOCK_ENTITY_DATA);
    if(nbt != null){
      FluidStack fluid = FluidStack.parseOptional(pLevel.registries(), nbt.copyTag());
      tooltip.add(Component
              .translatable("tooltip.fluid_storage.count", fluid.getAmount())
              .append(fluid.getHoverName().copy())
              .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
    super.appendHoverText(stack, pLevel, tooltip, pFlag);
  }

  @Override
  public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player){
    BlockEntity blockEntity = level.getBlockEntity(pos);
    if(blockEntity instanceof FluidStorageBE storageBE){
      if(!level.isClientSide && !player.isCreative()){
        ItemStack dropStack = new ItemStack(ModBlocks.FLUID_STORAGE.get());
        if(!storageBE.isEmpty())
          blockEntity.saveToItem(dropStack, level.registryAccess());
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
    return super.playerWillDestroy(level, pos, state, player);
  }

  @Override
  public ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult){
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

        if(stackInHand.getCapability(Capabilities.FluidHandler.ITEM) == null){
          Optional<RecipeHolder<TakeFluidUsingItemRecipe>> takeOutRecipe = level.getRecipeManager().getRecipeFor(
                  TakeFluidUsingItemRecipe.Type.INSTANCE,
                  new StackAndTankInput(stackInHand, fluidTank),
                  level);

          Optional<RecipeHolder<InsertFluidUsingItemRecipe>> insertFluidUsingRecipe = level.getRecipeManager().getRecipeFor(
                  InsertFluidUsingItemRecipe.Type.INSTANCE,
                  new StackAndTankInput(stackInHand, fluidTank),
                  level);

          if(takeOutRecipe.isPresent()){ //try to extract fluid using item
            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.drain(takeOutRecipe.get().value().fluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, takeOutRecipe.get().value().filledItem().copy());
          }
          else if(insertFluidUsingRecipe.isPresent()){ // try insert fluid using item
            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            fluidTank.fill(insertFluidUsingRecipe.get().value().inputFluid(), IFluidHandler.FluidAction.EXECUTE);
            removeStackAndReplaceWith(player, hand, stackInHand, insertFluidUsingRecipe.get().value().emptyItem().copy());
          }
          return ItemInteractionResult.SUCCESS;
        }
      }
    return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
  }

  
}
