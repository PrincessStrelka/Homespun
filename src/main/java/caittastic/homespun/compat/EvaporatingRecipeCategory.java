package caittastic.homespun.compat;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.blockentity.EvaporatingBasinBE;
import caittastic.homespun.recipes.EvaporatingBasinRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EvaporatingRecipeCategory implements IRecipeCategory<EvaporatingBasinRecipe>{
  public static final ResourceLocation UID = new ResourceLocation(Homespun.MOD_ID, "evaporating");
  public static final ResourceLocation TEXTURE = new ResourceLocation(Homespun.MOD_ID, "textures/gui/evaporating_basin_jei.png");

  public final IDrawable background;
  public final IDrawable icon;

  public EvaporatingRecipeCategory(IGuiHelper helper){
    this.background = helper.createDrawable(TEXTURE, 0, 0, 108, 68);
    this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EVAPORATING_BASIN.get()));

  }

  @Override
  public RecipeType<EvaporatingBasinRecipe> getRecipeType(){
    return JEIHomespunPlugin.EVAPORATING_TYPE;
  }

  @Override
  public Component getTitle(){
    return Component.translatable("jei.homespun.evaporating_recipe");
  }

  @Override
  public IDrawable getBackground(){
    return this.background;
  }

  @Override
  public IDrawable getIcon(){
    return this.icon;
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, EvaporatingBasinRecipe recipe, IFocusGroup focuses){

    int scaledAmount = (int)(recipe.inputFluidStack().getAmount() * (34f / EvaporatingBasinBE.TANK_CAPACITY));

    builder.addSlot(RecipeIngredientRole.INPUT, 21, 15 + (34 - scaledAmount))
            .addFluidStack(recipe.inputFluidStack().getFluid(), recipe.inputFluidStack().getAmount())
            .setFluidRenderer(recipe.inputFluidStack().getAmount(), true, 16, scaledAmount);

    builder.addSlot(RecipeIngredientRole.OUTPUT, 72, 29).addItemStack(recipe.getResultItem());

  }
}
