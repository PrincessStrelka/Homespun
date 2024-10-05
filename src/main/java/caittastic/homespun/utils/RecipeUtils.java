package caittastic.homespun.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;

public final class RecipeUtils {
    public static @NotNull Ingredient iWCToIngredientSaveCount(SizedIngredient sizedIngredient) {
        Ingredient ingredient = sizedIngredient.ingredient();
        for (ItemStack itemStack : ingredient.getItems()) {
            itemStack.setCount(sizedIngredient.count());
        }
        return ingredient;
    }
}
