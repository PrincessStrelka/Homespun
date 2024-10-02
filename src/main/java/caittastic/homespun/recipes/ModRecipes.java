package caittastic.homespun.recipes;

import caittastic.homespun.Homespun;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes{
  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister
          .create(BuiltInRegistries.RECIPE_SERIALIZER, Homespun.MOD_ID);

  public static final Supplier<RecipeSerializer<TakeFluidUsingItemRecipe>> TAKE_USING_SERIALIZER =
          SERIALIZERS.register("take_using_item", ()-> TakeFluidUsingItemRecipe.Serializer.INSTANCE);

  public static final Supplier<RecipeSerializer<InsertFluidUsingItemRecipe>> INSERT_USING_SERIALIZER =
          SERIALIZERS.register("fill_using_item", ()-> InsertFluidUsingItemRecipe.Serializer.INSTANCE);

  public static final Supplier<RecipeSerializer<CrushingTubRecipe>> CRUSHING_TUB_RECIPE =
          SERIALIZERS.register("tub_crushing", ()-> CrushingTubRecipe.Serializer.INSTANCE);
  public static final Supplier<RecipeSerializer<EvaporatingBasinRecipe>> EVAPORATING_BASIN_RECIPE =
          SERIALIZERS.register("evaporating", ()-> EvaporatingBasinRecipe.Serializer.INSTANCE);
}
