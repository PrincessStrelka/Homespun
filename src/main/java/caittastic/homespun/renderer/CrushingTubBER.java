package caittastic.homespun.renderer;

import caittastic.homespun.blockentity.CrushingTubBE;
import caittastic.homespun.blockentity.EvaporatingBasinBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class CrushingTubBER implements BlockEntityRenderer<CrushingTubBE>{
  public CrushingTubBER(BlockEntityRendererProvider.Context context){

  }

  @Override
  public void render(
          @NotNull CrushingTubBE entity,
          float partialTick,
          @NotNull PoseStack poseStack,
          @NotNull MultiBufferSource bufferSource,
          int packedLight,
          int packedOverlay){
    Minecraft minecraft = Minecraft.getInstance();
    ItemRenderer itemRenderer = minecraft.getItemRenderer();
    ItemStack stackToRender = entity.getInputRenderStack();
    float startHeight = 0.03125f;
    float pixHeight = 0.0625f;
    Random rand = new Random();
    rand.setSeed((long)entity.getBlockPos().getX() * entity.getBlockPos().getZ() * entity.getBlockPos().getY());
    poseStack.pushPose();

    int lightLevel = getLightLevel(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos());
    float itemScaleFactor = 0.53f;

    //initialises the start of where the stacks should be rendered from
    //if the model is a block, do something different to a flat item model
    if(stackToRender.getItem() instanceof BlockItem){
      poseStack.translate(0.5f, pixHeight*(2 + ((itemScaleFactor)/2)), 0.5f);
      poseStack.translate((rand.nextFloat()-0.5)/4, 0, (rand.nextFloat()-0.5)/4);
      poseStack.scale(1, 1, 1);
    }
    else{
      poseStack.translate(0.5f, startHeight + pixHeight, 0.5f);
      poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
      poseStack.scale(itemScaleFactor, itemScaleFactor, itemScaleFactor);
    }


    //for every item in the stack to be rendered, move up one pixel and render item
    for(int i = 0; i < entity.getInputRenderStackSize(); i++){
      if(stackToRender.getItem() instanceof BlockItem){
        if((i) % 16 == 0){
          poseStack.translate((rand.nextFloat()-0.5)/4, pixHeight * 2, (rand.nextFloat()-0.5)/4);
          //poseStack.mulPose(Vector3f.YN.rotationDegrees((float)(rand.nextFloat() * 360.0)));
          itemRenderer.renderStatic(
                  stackToRender,
                  ItemTransforms.TransformType.FIXED,
                  lightLevel,
                  OverlayTexture.NO_OVERLAY,
                  poseStack,
                  bufferSource,
                  1);
        }
      }
      else{
        if(i % ((int)Math.ceil(stackToRender.getMaxStackSize()/ 32f)) == 0){
          poseStack.translate(0, 0, -pixHeight * itemScaleFactor);
          poseStack.mulPose(Vector3f.ZN.rotationDegrees((float)(rand.nextFloat() * 360.0)));
          itemRenderer.renderStatic(
                  stackToRender,
                  ItemTransforms.TransformType.GUI,
                  lightLevel,
                  OverlayTexture.NO_OVERLAY,
                  poseStack,
                  bufferSource,
                  1);
        }
      }

    }
    poseStack.popPose();

    // fluid rendering starts here //
    //get info on type of fluid
    FluidStack fluidStack = entity.getStoredFluidStack();
    Fluid fluid = fluidStack.getFluid();

    //skip over fluid rendering if the fluidstack is empty
    if(fluidStack.isEmpty())
      return;

    //fluid brightness info
    int fluidBrightness = Math.max(lightLevel, fluidStack.getFluid().getFluidType().getLightLevel(fluidStack));
    int l2 = fluidBrightness >> 0x10 & 0xFFFF;
    int i3 = fluidBrightness & 0xFFFF;

    //fluid colour tint info
    IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
    int fluidTintColour = renderProperties.getTintColor(fluidStack);
    float red = (fluidTintColour >> 16 & 0xFF) / 255.0F;
    float green = (fluidTintColour >> 8 & 0xFF) / 255.0F;
    float blue = (fluidTintColour & 0xFF) / 255.0F;
    float alpha = ((fluidTintColour >> 24) & 0xFF) / 255F;

    //fluid texture info
    TextureAtlasSprite stillFluidSprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(renderProperties.getStillTexture(fluidStack));
    VertexConsumer vertexBuffer = bufferSource.getBuffer(RenderType.translucentNoCrumbling());

    //fluid render info
    Matrix4f lastPose = poseStack.last().pose();
    Matrix3f matrix = poseStack.last().normal();
    float fluidY = 0.001f + median(pixHeight * 2, (pixHeight * 2) + fluidStack.getAmount() * ((pixHeight * 6) / CrushingTubBE.getCapacity()),  pixHeight * 8) ;
    float fluidStartDrawPixel = pixHeight * 2;
    float fluidEndDrawPixel = pixHeight * 14;
    float fluidSpriteU0 = stillFluidSprite.getU0() + (stillFluidSprite.getU1() - stillFluidSprite.getU0()) / 8;
    float fluidSpriteU1 = stillFluidSprite.getU1() - (stillFluidSprite.getU1() - stillFluidSprite.getU0()) / 8;
    float fluidSpriteV0 = stillFluidSprite.getV0() + (stillFluidSprite.getV1() - stillFluidSprite.getV0()) / 8;
    float fluidSpriteV1 = stillFluidSprite.getV1() - (stillFluidSprite.getV1() - stillFluidSprite.getV0()) / 8;
    //north-west
    vertexBuffer.vertex(lastPose, fluidStartDrawPixel, fluidY, fluidStartDrawPixel).color(red, green, blue, alpha).uv(fluidSpriteU0, fluidSpriteV1).uv2(l2, i3).normal(matrix, 0, 1, 0).endVertex();
    //south-west
    vertexBuffer.vertex(lastPose, fluidStartDrawPixel, fluidY, fluidEndDrawPixel).color(red, green, blue, alpha).uv(fluidSpriteU0, fluidSpriteV0).uv2(l2, i3).normal(matrix, 0, 1, 0).endVertex();
    //south-east
    vertexBuffer.vertex(lastPose, fluidEndDrawPixel, fluidY, fluidEndDrawPixel).color(red, green, blue, alpha).uv(fluidSpriteU1, fluidSpriteV0).uv2(l2, i3).normal(matrix, 0, 1, 0).endVertex();
    //north-east
    vertexBuffer.vertex(lastPose, fluidEndDrawPixel, fluidY, fluidStartDrawPixel).color(red, green, blue, alpha).uv(fluidSpriteU1, fluidSpriteV1).uv2(l2, i3).normal(matrix, 0, 1, 0).endVertex();
  }

  private int getLightLevel(Level level, BlockPos pos){
    int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
    int skyLight = level.getBrightness(LightLayer.SKY, pos);
    return LightTexture.pack(blockLight, skyLight);
  }
  float median(float a, float b, float c){
    return Math.max(Math.min(a,b), Math.min(Math.max(a,b),c));
  }
}
