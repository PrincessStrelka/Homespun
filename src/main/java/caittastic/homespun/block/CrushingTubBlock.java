package caittastic.homespun.block;

import caittastic.homespun.blockentity.CrushingTubBE;
import caittastic.homespun.blockentity.ModBlockEntities;
import caittastic.homespun.recipes.InsertFluidUsingItemRecipe;
import caittastic.homespun.recipes.TakeFluidUsingItemRecipe;
import caittastic.homespun.recipes.inputs.StackAndTankInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static caittastic.homespun.blockentity.CrushingTubBE.CRAFT_SLOT;

public class CrushingTubBlock extends FluidInteractingBase {
    public CrushingTubBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.homespun.crushing_tub"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pos, CollisionContext context) {
        return Shapes.or(Block.box(0, 0, 0, 16, 9, 16));
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity fallEntity, float pFallDistance) {
        if (!pLevel.isClientSide && !(fallEntity instanceof ItemEntity) && pLevel.getBlockEntity(pPos) instanceof CrushingTubBE entity) {
            entity.doCraft();
        }
        super.fallOn(pLevel, pState, pPos, fallEntity, pFallDistance);
    }

    //makes sure when the block is broken the inventory drops with it
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrushingTubBE) {
                ((CrushingTubBE) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        FluidActionResult fluidResult;
        ItemStack stackInHand = player.getItemInHand(hand);
        Item itemInHand = stackInHand.getItem();
        if (level.getBlockEntity(pos) instanceof CrushingTubBE entity) {
            FluidTank fluidTank = entity.getFluidTank();
            IItemHandler itemHandler = entity.getItemHandler();
            ItemStack internalStack = itemHandler.getStackInSlot(CRAFT_SLOT);
            Item internalItem = internalStack.getItem();


            //try to bucket in to/out of
            fluidResult = FluidUtil.tryEmptyContainerAndStow(stackInHand, fluidTank, new InvWrapper(player.getInventory()), 1000, player, true);
            if (fluidResult.isSuccess())
                player.setItemInHand(hand, fluidResult.getResult());

            fluidResult = FluidUtil.tryFillContainerAndStow(stackInHand, fluidTank, new InvWrapper(player.getInventory()), 1000, player, true);
            if (fluidResult.isSuccess())
                player.setItemInHand(hand, fluidResult.getResult());

            IFluidHandlerItem capability = stackInHand.getCapability(Capabilities.FluidHandler.ITEM);
            if (capability == null) {
                Optional<TakeFluidUsingItemRecipe> takeOutRecipe = level.getRecipeManager().getRecipeFor(
                        TakeFluidUsingItemRecipe.Type.INSTANCE,
                        new StackAndTankInput(stackInHand, fluidTank),
                        level).map(RecipeHolder::value);

                Optional<InsertFluidUsingItemRecipe> insertFluidUsingRecipe = level.getRecipeManager().getRecipeFor(
                        InsertFluidUsingItemRecipe.Type.INSTANCE,
                        new StackAndTankInput(stackInHand, fluidTank),
                        level).map(RecipeHolder::value);

                if (takeOutRecipe.isPresent()) { //try to take out using item
                    level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    fluidTank.drain(takeOutRecipe.get().fluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
                    removeStackAndReplaceWith(player, hand, stackInHand, takeOutRecipe.get().filledItem().copy());
                } else if (insertFluidUsingRecipe.isPresent()) { // try put in fluid using item
                    level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    fluidTank.fill(insertFluidUsingRecipe.get().inputFluid(), IFluidHandler.FluidAction.EXECUTE);
                    removeStackAndReplaceWith(player, hand, stackInHand, insertFluidUsingRecipe.get().emptyItem().copy());
                } else { //try to insert/remove items
                    if (internalStack.isEmpty() || (internalItem == itemInHand && (internalStack.getCount() < internalStack.getMaxStackSize()))) {

                        level.playSound(null, player.blockPosition(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                        int amountToInsert = Math.min(stackInHand.getCount(), internalStack.getMaxStackSize() - internalStack.getCount());
                        itemHandler.insertItem(CRAFT_SLOT, new ItemStack(itemInHand, amountToInsert), false);
                        entity.setChanged();
                        entity.getLevel().sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 3);
                        if (!player.isCreative())
                            stackInHand.shrink(amountToInsert);
                    } else {

                        level.playSound(null, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                        if (!player.isCreative())
                            popResourceFromFace(level, entity.getBlockPos(), player.getDirection().getOpposite(), internalStack);
                        itemHandler.extractItem(CRAFT_SLOT, internalStack.getCount(), false);
                    }
                }
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.CRUSHING_TUB.get().create(pos, state);
    }


}
