package caittastic.homespun.renderer;

import caittastic.homespun.blockentity.EvaporatingBasinBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Objects;
import java.util.Random;

public class EvaporatingBasinBER implements BlockEntityRenderer<EvaporatingBasinBE>{
  public EvaporatingBasinBER(BlockEntityRendererProvider.Context context){}

  @Override
  public void render(
          @NotNull EvaporatingBasinBE entity,
          float partialTick,
          @NotNull PoseStack poseStack,
          @NotNull MultiBufferSource bufferSource,
          int packedLight,
          int packedOverlay){
    Minecraft minecraft = Minecraft.getInstance();
    ItemRenderer itemRenderer = minecraft.getItemRenderer();
    ItemStack stackToRender = entity.getRenderStack();
    float pixHeight = 0.0625f;
    float startHeight = 0.03125f;
    //float scaleFactor = 14f / 16f;
    float scaleFactor =0.5f;
    int lightLevel = getLightLevel(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos());
    Random rand = new Random();
    rand.setSeed((long)entity.getBlockPos().getX() * entity.getBlockPos().getZ() * entity.getBlockPos().getY());
    poseStack.pushPose();

    // item rendering starts here //
    poseStack.translate(0.5, pixHeight*(2 + ((scaleFactor)/2)), 0.5);
    poseStack.mulPose(Axis.XP.rotationDegrees(90));
    poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
    poseStack.mulPose(Axis.ZN.rotationDegrees((float)(rand.nextFloat() * 360.0)));
    itemRenderer.renderStatic(
            stackToRender,
            ItemDisplayContext.NONE,
            lightLevel,
            OverlayTexture.NO_OVERLAY,
            poseStack,
            bufferSource,
            entity.getLevel(),
            1);
    poseStack.popPose();

    // fluid rendering starts here //
    FluidStack fluidStack = entity.getRenderFluid();
    Fluid fluid = fluidStack.getFluid();

    if(fluidStack.isEmpty())
      return;

    int fluidBrightness = Math.max(lightLevel, fluidStack.getFluid().getFluidType().getLightLevel(fluidStack));
    int l2 = fluidBrightness >> 0x10 & 0xFFFF;
    int i3 = fluidBrightness & 0xFFFF;

    IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
    int fluidTintColour = renderProperties.getTintColor(fluidStack);
    float red = (fluidTintColour >> 16 & 0xFF) / 255.0F;
    float green = (fluidTintColour >> 8 & 0xFF) / 255.0F;
    float blue = (fluidTintColour & 0xFF) / 255.0F;
    float alpha = ((fluidTintColour >> 24) & 0xFF) / 255F;

    TextureAtlasSprite stillFluidSprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(renderProperties.getStillTexture(fluidStack));
    VertexConsumer vertexBuffer = bufferSource.getBuffer(RenderType.translucent());

    PoseStack.Pose last = poseStack.last();
    Matrix4f lastPose = last.pose();
    Matrix3f matrix = last.normal();
    float fluidY = 0.001f + median(pixHeight * 2, (pixHeight * 2) + fluidStack.getAmount() * ((pixHeight * 3) / EvaporatingBasinBE.TANK_CAPACITY),  pixHeight * 5) ;
    float fluidStartDrawPixel = pixHeight * 3;
    float fluidEndDrawPixel = pixHeight * 13;
    float fluidSpriteU0 = stillFluidSprite.getU0() + (stillFluidSprite.getU1() - stillFluidSprite.getU0()) / 8;
    float fluidSpriteU1 = stillFluidSprite.getU1() - (stillFluidSprite.getU1() - stillFluidSprite.getU0()) / 8;
    float fluidSpriteV0 = stillFluidSprite.getV0() + (stillFluidSprite.getV1() - stillFluidSprite.getV0()) / 8;
    float fluidSpriteV1 = stillFluidSprite.getV1() - (stillFluidSprite.getV1() - stillFluidSprite.getV0()) / 8;
    //north-west
    vertexBuffer.addVertex(lastPose, fluidStartDrawPixel, fluidY, fluidStartDrawPixel).setColor(red, green, blue, alpha).setUv(fluidSpriteU0, fluidSpriteV1).setUv2(l2, i3).setNormal(last, 0, 1, 0);
    //south-west
    vertexBuffer.addVertex(lastPose, fluidStartDrawPixel, fluidY, fluidEndDrawPixel).setColor(red, green, blue, alpha).setUv(fluidSpriteU0, fluidSpriteV0).setUv2(l2, i3).setNormal(last, 0, 1, 0);
    //south-east
    vertexBuffer.addVertex(lastPose, fluidEndDrawPixel, fluidY, fluidEndDrawPixel).setColor(red, green, blue, alpha).setUv(fluidSpriteU1, fluidSpriteV0).setUv2(l2, i3).setNormal(last, 0, 1, 0);
    //north-east
    vertexBuffer.addVertex(lastPose, fluidEndDrawPixel, fluidY, fluidStartDrawPixel).setColor(red, green, blue, alpha).setUv(fluidSpriteU1, fluidSpriteV1).setUv2(l2, i3).setNormal(last, 0, 1, 0);




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
