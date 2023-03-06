package caittastic.homespun.compat;


import caittastic.homespun.Homespun;
import caittastic.homespun.recipes.CrushingTubRecipe;
import caittastic.homespun.recipes.EvaporatingBasinRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIHomespunPlugin implements IModPlugin{
  public static RecipeType<CrushingTubRecipe> CRUSHING_TUB_TYPE =
          new RecipeType<>(CrushingTubRecipeCategory.UID, CrushingTubRecipe.class);
  public static RecipeType<EvaporatingBasinRecipe> EVAPORATING_TYPE =
          new RecipeType<>(EvaporatingRecipeCategory.UID, EvaporatingBasinRecipe.class);

  @Override
  public ResourceLocation getPluginUid(){
    return new ResourceLocation(Homespun.MOD_ID, "jei_plugin");
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration){
    registration.addRecipeCategories(new CrushingTubRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    registration.addRecipeCategories(new EvaporatingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
    registration.addRecipes(CRUSHING_TUB_TYPE, rm.getAllRecipesFor(CrushingTubRecipe.Type.INSTANCE));
    registration.addRecipes(EVAPORATING_TYPE, rm.getAllRecipesFor(EvaporatingBasinRecipe.Type.INSTANCE));
  }
}
