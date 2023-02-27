package caittastic.homespun.renderer;

import caittastic.homespun.blockentity.CrushingTubBE;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class CrushingTubBER implements BlockEntityRenderer<CrushingTubBE>{
  public CrushingTubBER(BlockEntityRendererProvider.Context context){

  }

  private static final float MIN_WH =  5F/16F; //Width/height of cutout
  private static final float MAX_WH = 11F/16F; //Width/height of cutout
  private static final float MIN_D = -0.01F/16F; //Depth
  private static final float MAX_D = 16.01F/16F; //Depth

  @Override
  public void render(
          @NotNull CrushingTubBE entity,
          float partialTick,
          @NotNull PoseStack poseStack,
          @NotNull MultiBufferSource bufferSource,
          int packedLight,
          int packedOverlay){
    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    ItemStack stackToRender = entity.getInputRenderStack();
    float startHeight = 0.03125f;
    float pixHeight = 0.0625f;
    Random rand = new Random();
    poseStack.pushPose();
    rand.setSeed((long)entity.getBlockPos().getX() * entity.getBlockPos().getZ() * entity.getBlockPos().getY());

    //initialises the start of where the stacks should be rendered from
    poseStack.translate(0.5f, startHeight + pixHeight, 0.5f);
    poseStack.scale(1f, 1f, 1f);
    poseStack.mulPose(Vector3f.XP.rotationDegrees(90));

    //for every item in the stack to be rendered, move up one pixel and render item
    for(int i = 0; i < entity.getInputRenderStackSize(); i++){
      poseStack.translate(0, 0, -pixHeight);
      poseStack.mulPose(Vector3f.ZN.rotationDegrees((float)(rand.nextFloat() * 360.0)));
      itemRenderer.renderStatic(
              stackToRender,
              ItemTransforms.TransformType.GUI,
              getLightLevel(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos()),
              OverlayTexture.NO_OVERLAY,
              poseStack,
              bufferSource,
              1);
    }
    poseStack.popPose();
  }

  private int getLightLevel(Level level, BlockPos pos){
    int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
    int skyLight = level.getBrightness(LightLayer.SKY, pos);
    return LightTexture.pack(blockLight, skyLight);
  }
}
