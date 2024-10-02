package caittastic.homespun.gui;

import caittastic.homespun.Homespun;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class VesselScreen extends AbstractContainerScreen<VesselMenu>{
  /* variable */
  private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
          Homespun.MOD_ID,
          "textures/gui/vessel_gui.png"); //the gui texture of the block entity

  /* constructor */
  public VesselScreen(VesselMenu pMenu, Inventory pPlayerInventory, Component pTitle){
    super(pMenu, pPlayerInventory, pTitle);
  }

  /* implemented methods */
  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY){
    int x = (width - imageWidth) / 2;
    int y = (height - imageHeight) / 2;
    guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
  }


  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta){
    renderBackground(guiGraphics, mouseX, mouseY, delta);
    super.render(guiGraphics, mouseX, mouseY, delta);
    renderTooltip(guiGraphics, mouseX, mouseY);
  }
}
