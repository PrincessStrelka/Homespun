package caittastic.homespun.block;

import caittastic.homespun.blockentity.VesselBE;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VesselBlock extends BaseEntityBlock{
  public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
  private final String pattern;


  public VesselBlock(String pattern, Properties properties){
    super(properties);
    this.pattern = pattern;
  }

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

  @Override
  public void playerWillDestroy(Level level, BlockPos pPos, BlockState pState, Player pPlayer){
    BlockEntity blockEntity = level.getBlockEntity(pPos);
    if(blockEntity instanceof VesselBE vesselEntity){
      if(!level.isClientSide && !pPlayer.isCreative()){
        ItemStack itemstack = getVesselFromPattern(this.pattern);
        if(!vesselEntity.isEmpty())
          blockEntity.saveToItem(itemstack);

        if(vesselEntity.hasCustomName()){
          itemstack.setHoverName(vesselEntity.getCustomName());
        }

        ItemEntity itementity = new ItemEntity(level, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, itemstack);
        itementity.setDefaultPickUpDelay();
        level.addFreshEntity(itementity);
      }
    }
    super.playerWillDestroy(level, pPos, pState, pPlayer);
  }

  @Override
  public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack){
    if(pStack.hasCustomHoverName()){
      BlockEntity entity = pLevel.getBlockEntity(pPos);
      if(entity instanceof VesselBE){
        ((VesselBE)entity).setCustomName(pStack.getHoverName());
      }
    }
  }

  @Override
  public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving){
    if(!pState.is(pNewState.getBlock())){
      BlockEntity entity = pLevel.getBlockEntity(pPos);
      if(entity instanceof VesselBE){
        pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
      }

      super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable BlockGetter pLevel, List<Component> tooltip, TooltipFlag pFlag){
    CompoundTag NBT = BlockItem.getBlockEntityData(stack);
    int shownStacks = 0;
    int maxShown = 4;

    if(NBT != null && NBT.contains(VesselBE.ITEMS_KEY)){
      ItemStackHandler inventory = new ItemStackHandler();
      inventory.deserializeNBT(NBT.getCompound(VesselBE.ITEMS_KEY));
      for(int i = 0; i < VesselBE.SLOT_COUNT; i++){
        ItemStack iteratedStack = inventory.getStackInSlot(i);
        if(!iteratedStack.isEmpty()){
          if(shownStacks < maxShown || Screen.hasShiftDown()){
            MutableComponent mutablecomponent = iteratedStack.getHoverName().copy();
            mutablecomponent.append(" x").append(String.valueOf(iteratedStack.getCount()));
            tooltip.add(mutablecomponent.withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
          }
          shownStacks++;
        }
      }

      if(!Screen.hasShiftDown() && shownStacks > maxShown)
        tooltip.add(Component.translatable("tooltip.ceramic_vessel.more", shownStacks - maxShown).withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));

    }
    super.appendHoverText(stack, pLevel, tooltip, pFlag);
  }



  private ItemStack getVesselFromPattern(@Nullable String pattern){
    Block vesselBlock;
    if(pattern == null)
      vesselBlock = ModBlocks.CERAMIC_VESSEL.get();
    else
      vesselBlock = ModBlocks.VESSEL_MAP.get(pattern).get();
    return new ItemStack(vesselBlock);
  }


}
