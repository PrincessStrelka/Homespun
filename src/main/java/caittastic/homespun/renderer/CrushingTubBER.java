package caittastic.homespun.renderer;

import caittastic.homespun.blockentity.CrushingTubBE;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class CrushingTubBER implements BlockEntityRenderer<CrushingTubBE>{
  private static final float MIN_WH = 5F / 16F; //Width/height of cutout
  private static final float MAX_WH = 11F / 16F; //Width/height of cutout
  private static final float MIN_D = -0.01F / 16F; //Depth
  private static final float MAX_D = 16.01F / 16F; //Depth

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
    poseStack.pushPose();
    rand.setSeed((long)entity.getBlockPos().getX() * entity.getBlockPos().getZ() * entity.getBlockPos().getY());
    int lightLevel = getLightLevel(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos());
    float itemScaleFactor = 1;

    //initialises the start of where the stacks should be rendered from
    poseStack.translate(0.5f, startHeight + pixHeight, 0.5f);
    poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
    poseStack.scale(itemScaleFactor, itemScaleFactor, itemScaleFactor);


    //for every item in the stack to be rendered, move up one pixel and render item
    for(int i = 0; i < entity.getInputRenderStackSize(); i++){
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
    poseStack.popPose();

    // fluid rendering starts here //
    //get info on type of fluid
    FluidStack fluidStack = entity.getStoredFluidStack();
    Fluid fluid = fluidStack.getFluid();

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
    float fluidY = 0.0001f + pixHeight * 8;
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

}
