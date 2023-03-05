package caittastic.homespun.recipes;

import caittastic.homespun.Homespun;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes{
  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister
          .create(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), Homespun.MOD_ID);

  public static final RegistryObject<RecipeSerializer<TakeFluidUsingItemRecipe>> TAKE_USING_SERIALIZER =
          SERIALIZERS.register("take_using_item", ()-> TakeFluidUsingItemRecipe.Serializer.INSTANCE);

  public static final RegistryObject<RecipeSerializer<InsertFluidUsingItemRecipe>> INSERT_USING_SERIALIZER =
          SERIALIZERS.register("fill_using_item", ()-> InsertFluidUsingItemRecipe.Serializer.INSTANCE);

  public static final RegistryObject<RecipeSerializer<CrushingTubRecipe>> CRUSHING_TUB_RECIPE =
          SERIALIZERS.register("tub_crushing", ()-> CrushingTubRecipe.Serializer.INSTANCE);
}
