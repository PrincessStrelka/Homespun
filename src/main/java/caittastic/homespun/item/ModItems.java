package caittastic.homespun.item;

import caittastic.homespun.Homespun;
import caittastic.homespun.fluid.ModFluids;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static caittastic.homespun.ModTabs.AGRICULTURE;
import static caittastic.homespun.ModTabs.INDUSTRY;

//need to add fluid buckets, elyxers, and alcahol

public class ModItems{
  // Create a Deferred Register to hold Items which will all be registered under the MOD_ID
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Homespun.MOD_ID);

  //------------------------------------- food data -------------------------------------//
  public static final FoodProperties OLIVES_FOOD = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.4F)
          .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1, false, false), 0.95F)
          .build();
  public static final FoodProperties IRONBERRIES_FOOD = (new FoodProperties.Builder()).alwaysEat().nutrition(2).saturationMod(0.4F)
          .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 15, false, false), 1)
          .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 15, false, false), 1)
          .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 15, false, false), 1)
          .effect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 15, false, false), 1)
          .effect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 15, false, false), 1)
          .effect(new MobEffectInstance(MobEffects.JUMP, 200, 250, false, false), 1).build();
  public static final FoodProperties TOMATO_FOOD = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.4F).build();
  public static final FoodProperties CHILI_PEPPER_FOOD = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.4F)
          .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0, false, false), 1)
          .build();
  public static final FoodProperties GRAPES_FOOD = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).build();

  //------------------------------------- items -------------------------------------//
  /*     crops     */
  public static final RegistryObject<Item> IRONBERRIES = ITEMS.register("ironberries", () -> new Item(new Item.Properties().tab(AGRICULTURE).food(IRONBERRIES_FOOD)){
    @Override
    public int getUseDuration(@NotNull ItemStack i){
      return 16;
    }
  });
  public static final RegistryObject<Item> OLIVES = ITEMS.register("olives", () -> new Item(new Item.Properties().tab(AGRICULTURE).food(OLIVES_FOOD)){
    @Override
    public int getUseDuration(@NotNull ItemStack i){
      return 40;
    }
  });
  /*     industry     */
  public static final RegistryObject<Item> TINY_IRON_DUST = ITEMS.register("tiny_iron_dust", () -> new Item(new Item.Properties().tab(INDUSTRY)));
  public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(INDUSTRY)));
  public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties().tab(INDUSTRY)));
  public static final RegistryObject<Item> REDSTONE_ACID = ITEMS.register("redstone_acid", () -> new RedstoneAcidItem(new Item.Properties().tab(INDUSTRY).stacksTo(16)));

  /*     fluid     */
  public static final RegistryObject<Item> IRONBERRY_JUICE_BOTTLE = ITEMS.register("ironberry_juice_bottle", () -> new Item(new Item.Properties().tab(AGRICULTURE).stacksTo(16)));
  public static final RegistryObject<Item> IRONBERRY_JUICE_BUCKET = ITEMS.register("ironberry_juice_bucket",
          () -> new BucketItem(ModFluids.IRONBERRY_JUICE, new Item.Properties().tab(AGRICULTURE).stacksTo(1).craftRemainder(Items.BUCKET)));
}
