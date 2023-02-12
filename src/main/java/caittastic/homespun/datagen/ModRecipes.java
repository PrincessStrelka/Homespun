package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider{
  String hasPlanksCriterion = "has_planks";

  public ModRecipes(DataGenerator generator){
    super(generator);
  }

  @Override
  protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer){
    /*     industry     */
    ShapelessRecipeBuilder.shapeless(ModItems.TINY_IRON_DUST.get()).requires(ModItems.IRONBERRIES.get()).unlockedBy("has_ironberries", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRONBERRIES.get())).save(consumer);
    SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TINY_IRON_DUST.get()),
                    Items.IRON_NUGGET, 1.0f, 100)
            .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
            .save(consumer, "tiny_iron_dust_smelting");
    SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TINY_IRON_DUST.get()),
                    Items.IRON_NUGGET, 1.0f, 50)
            .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
            .save(consumer, "tiny_iron_dust_blasting");

    /*     IRONWOOD     */
    woodFromLogs(consumer, ModBlocks.IRONWOOD_WOOD.get(), ModBlocks.IRONWOOD_LOG.get());
    woodFromLogs(consumer, ModBlocks.STRIPPED_IRONWOOD_WOOD.get(), ModBlocks.STRIPPED_IRONWOOD_LOG.get());
    planksFromLog(consumer, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_LOGS);
    planksFromWood(consumer, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_WOOD, "ironwood_planks_from_wood");
    stairRecipe(consumer, ModBlocks.IRONWOOD_STAIRS, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    slabRecipe(consumer, ModBlocks.IRONWOOD_SLAB, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    fenceRecipe(consumer, ModBlocks.IRONWOOD_FENCE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    fenceGateRecipe(consumer, ModBlocks.IRONWOOD_FENCE_GATE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    buttonRecipe(consumer, ModBlocks.IRONWOOD_BUTTON, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    pressurePlateRecipe(consumer, ModBlocks.IRONWOOD_PRESSURE_PLATE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    doorRecipe(consumer, ModBlocks.IRONWOOD_DOOR, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    trapdoorRecipe(consumer, ModBlocks.IRONWOOD_TRAPDOOR, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    /*     OLIVE     */
    woodFromLogs(consumer, ModBlocks.OLIVE_WOOD.get(), ModBlocks.OLIVE_LOG.get());
    woodFromLogs(consumer, ModBlocks.STRIPPED_OLIVE_WOOD.get(), ModBlocks.STRIPPED_OLIVE_LOG.get());
    planksFromLog(consumer, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_LOGS);
    planksFromWood(consumer, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_WOOD, "olive_planks_from_wood");
    stairRecipe(consumer, ModBlocks.OLIVE_STAIRS, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    slabRecipe(consumer, ModBlocks.OLIVE_SLAB, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    fenceRecipe(consumer, ModBlocks.OLIVE_FENCE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    fenceGateRecipe(consumer, ModBlocks.OLIVE_FENCE_GATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    buttonRecipe(consumer, ModBlocks.OLIVE_BUTTON, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    pressurePlateRecipe(consumer, ModBlocks.OLIVE_PRESSURE_PLATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    doorRecipe(consumer, ModBlocks.OLIVE_DOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    trapdoorRecipe(consumer, ModBlocks.OLIVE_TRAPDOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    /*     chain     */
    chainRecipe(ModBlocks.GOLD_CHAIN, Tags.Items.NUGGETS_GOLD, Tags.Items.INGOTS_GOLD, "gold_ingot", Items.GOLD_INGOT, consumer);
    chainRecipe(ModBlocks.COPPER_CHAIN, TagInit.Items.COPPER_NUGGETS, Tags.Items.INGOTS_COPPER, "copper_ingot", Items.COPPER_INGOT, consumer);
    /*     metallurgy     */
    ShapelessRecipeBuilder.shapeless(ModItems.COPPER_NUGGET.get(), 9).requires(Tags.Items.INGOTS_COPPER).unlockedBy("copper_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT)).save(consumer);
    ShapedRecipeBuilder.shaped(Items.COPPER_INGOT)
            .pattern("xxx")
            .pattern("xxx")
            .pattern("xxx")
            .define('x', TagInit.Items.COPPER_NUGGETS)
            .group(Homespun.MOD_ID)
            .unlockedBy("copper_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
            .save(consumer);
    /*     deco stone     */
    //calcite
    registerBrickSet(consumer,
            "calcite_brick",
            "has_calcite",
            Blocks.CALCITE,
            ModBlocks.CALCITE_BRICKS,
            ModBlocks.CALCITE_BRICK_SLAB,
            ModBlocks.CALCITE_BRICK_STAIRS
            //,ModBlocks.CALCITE_BRICK_WALL
    );
    //tuff
    registerBrickSet(consumer,
            "tuff_tile",
            "has_tuff",
            Blocks.TUFF,
            ModBlocks.TUFF_TILES,
            ModBlocks.TUFF_TILE_SLAB,
            ModBlocks.TUFF_TILE_STAIRS
            //,ModBlocks.TUFF_TILE_WALL
    );
    //smooth stone pillar
    ShapedRecipeBuilder.shaped(ModBlocks.SMOOTH_STONE_PILLAR.get(), 3)
            .pattern("x")
            .pattern("x")
            .pattern("x")
            .define('x', Blocks.SMOOTH_STONE)
            .group(Homespun.MOD_ID)
            .unlockedBy("has_stone", has(Blocks.STONE))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(Blocks.SMOOTH_STONE), ModBlocks.SMOOTH_STONE_PILLAR.get())
            .unlockedBy("has_stone", has(Blocks.STONE))
            .save(consumer, "smooth_stone_pillar_from_stonecutting");


  }

  //------------------------------------- methods -------------------------------------//
  private void planksFromWood(Consumer<FinishedRecipe> consumer, Block plankBlock, TagKey<Item> woodTag, String name){
    ShapelessRecipeBuilder.shapeless(plankBlock, 4).requires(woodTag).group("planks").unlockedBy("has_wood", has(woodTag)).save(consumer, name);
  }

  private void registerBrickSet(
          @NotNull Consumer<FinishedRecipe> consumer,
          String brickName,
          String criterionName,
          Block parent,
          RegistryObject<Block> bricks,
          RegistryObject<Block> brickSlab,
          RegistryObject<Block> brickStairs
          //,RegistryObject<Block> brickWall
  ){
    ShapedRecipeBuilder.shaped(bricks.get(), 4)
            .pattern("xx")
            .pattern("xx")
            .define('x', parent)
            .group(Homespun.MOD_ID)
            .unlockedBy(criterionName, has(parent))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(parent), bricks.get())
            .unlockedBy(criterionName, has(parent))
            .save(consumer, brickName + "s_from_stonecutting");
    //slabs
    ShapedRecipeBuilder.shaped(brickSlab.get(), 2)
            .pattern("xxx")
            .define('x', bricks.get())
            .group(Homespun.MOD_ID)
            .unlockedBy(criterionName, has(parent))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(bricks.get()), brickSlab.get(), 2)
            .unlockedBy(criterionName, has(parent))
            .save(consumer, brickName + "_slabs_from_stonecutting");
    //stairs
    ShapedRecipeBuilder.shaped(brickStairs.get(), 6)
            .pattern("x  ")
            .pattern("xx ")
            .pattern("xxx")
            .define('x', bricks.get())
            .group(Homespun.MOD_ID)
            .unlockedBy(criterionName, has(parent))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(bricks.get()), brickStairs.get())
            .unlockedBy(criterionName, has(parent))
            .save(consumer, brickName + "_stairs_from_stonecutting");
    //walls
    /*

    ShapedRecipeBuilder.shaped(brickWall.get())
            .pattern("xxx")
            .pattern("xxx")
            .define('x', bricks.get())
            .group(Homespun.MOD_ID)
            .unlockedBy(criterionName, has(parent))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(bricks.get()), brickWall.get())
            .unlockedBy(criterionName, has(parent))
            .save(consumer, brickName + "_wall_from_stonecutting");

     */
  }

  private void chainRecipe(RegistryObject<Block> chain, TagKey<Item> nuggetTag, TagKey<Item> ingotTag, String criterionName, Item criterionItem, Consumer<FinishedRecipe> consumer){
    ShapedRecipeBuilder.shaped(chain.get())
            .pattern("o")
            .pattern("#")
            .pattern("o")
            .define('o', nuggetTag)
            .define('#', ingotTag)
            .group(Homespun.MOD_ID)
            .unlockedBy(criterionName, InventoryChangeTrigger.TriggerInstance.hasItems(criterionItem))
            .save(consumer);
  }

  private void trapdoorRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> trapdoorBlock, RegistryObject<Block> materialBlock, String criterionName){
    trapdoorBuilder(trapdoorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }

  private void doorRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> doorBlock, RegistryObject<Block> materialBlock, String criterionName){
    doorBuilder(doorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }

  private void pressurePlateRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> pressurePlateBlock, RegistryObject<Block> materialBlock, String criterionName){
    pressurePlateBuilder(pressurePlateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }

  private void buttonRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> buttonBlock, RegistryObject<Block> materialBlock, String criterionName){
    buttonBuilder(buttonBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }

  private void fenceGateRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> fenceGateBlock, RegistryObject<Block> materialBlock, String criterionName){
    fenceGateBuilder(fenceGateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }

  private void fenceRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> fenceBlock, RegistryObject<Block> materialBlock, String criterionName){
    fenceBuilder(fenceBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }

  private void slabRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> slabBlock, RegistryObject<Block> materialBlock, String criterionName){
    slabBuilder(slabBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }

  private void stairRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> stairsBlock, RegistryObject<Block> materialBlock, String criterionName){
    stairBuilder(stairsBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(consumer);
  }
}

