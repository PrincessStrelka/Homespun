package caittastic.homespun.renderer;

import caittastic.homespun.blockentity.CrushingTubBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

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
    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    ItemStack itemStack = entity.getOutputRenderStack();
    poseStack.pushPose();
    poseStack.translate(0.5f, 0.65f, 0.5f);
    poseStack.scale(0.25f, 0.25f, 0.25f);
    poseStack.mulPose(Vector3f.XP.rotationDegrees(90));



  }
}
