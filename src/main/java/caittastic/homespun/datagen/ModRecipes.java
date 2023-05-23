package caittastic.homespun.datagen;

import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
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
    SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TINY_IRON_DUST.get()),
                    Items.IRON_NUGGET, 1.0f, 100)
            .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
            .save(consumer, "tiny_iron_dust_smelting");
    SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TINY_IRON_DUST.get()),
                    Items.IRON_NUGGET, 1.0f, 50)
            .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
            .save(consumer, "tiny_iron_dust_blasting");
    ShapedRecipeBuilder.shaped(ModBlocks.CRUSHING_TUB.get(), 1)
            .pattern("w w")
            .pattern("i i")
            .pattern("sss")
            .define('w', ItemTags.PLANKS)
            .define('i', Tags.Items.INGOTS_IRON)
            .define('s', ItemTags.WOODEN_SLABS)
            .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
            .save(consumer);
    ShapedRecipeBuilder.shaped(ModBlocks.EVAPORATING_BASIN.get(), 1)
            .pattern("t t")
            .pattern(" t ")
            .define('t', ItemTags.TERRACOTTA)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
            .save(consumer);

    /*     IRONWOOD     */

    woodFromLogs(consumer, ModBlocks.IRONWOOD_WOOD.get(), ModBlocks.IRONWOOD_LOG.get());
    woodFromLogs(consumer, ModBlocks.STRIPPED_IRONWOOD_WOOD.get(), ModBlocks.STRIPPED_IRONWOOD_LOG.get());
    planksFromLog(consumer, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_LOGS);
    planksFromWood(consumer, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_WOOD, "ironwood_planks_from_wood");
    stairRecipe(consumer, ModBlocks.IRONWOOD_STAIRS, ModBlocks.IRONWOOD_PLANKS, false);
    slabRecipe(consumer, ModBlocks.IRONWOOD_SLAB, ModBlocks.IRONWOOD_PLANKS, false);
    fenceRecipe(consumer, ModBlocks.IRONWOOD_FENCE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    fenceGateRecipe(consumer, ModBlocks.IRONWOOD_FENCE_GATE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    buttonRecipe(consumer, ModBlocks.IRONWOOD_BUTTON, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    pressurePlateRecipe(consumer, ModBlocks.IRONWOOD_PRESSURE_PLATE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    doorRecipe(consumer, ModBlocks.IRONWOOD_DOOR, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
    trapdoorRecipe(consumer, ModBlocks.IRONWOOD_TRAPDOOR, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);

    registerMosaics(consumer, "ironwood", ModBlocks.IRONWOOD_MOSAIC.get(), ModBlocks.IRONWOOD_SLAB.get(), ModBlocks.IRONWOOD_PLANKS.get(), ModBlocks.IRONWOOD_MOSAIC_SLAB.get(), ModBlocks.IRONWOOD_MOSAIC_STAIRS.get());





    /*     OLIVE     */
    woodFromLogs(consumer, ModBlocks.OLIVE_WOOD.get(), ModBlocks.OLIVE_LOG.get());
    woodFromLogs(consumer, ModBlocks.STRIPPED_OLIVE_WOOD.get(), ModBlocks.STRIPPED_OLIVE_LOG.get());
    planksFromLog(consumer, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_LOGS);
    planksFromWood(consumer, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_WOOD, "olive_planks_from_wood");
    stairRecipe(consumer, ModBlocks.OLIVE_STAIRS, ModBlocks.OLIVE_PLANKS, false);
    slabRecipe(consumer, ModBlocks.OLIVE_SLAB, ModBlocks.OLIVE_PLANKS, false);
    fenceRecipe(consumer, ModBlocks.OLIVE_FENCE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    fenceGateRecipe(consumer, ModBlocks.OLIVE_FENCE_GATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    buttonRecipe(consumer, ModBlocks.OLIVE_BUTTON, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    pressurePlateRecipe(consumer, ModBlocks.OLIVE_PRESSURE_PLATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    doorRecipe(consumer, ModBlocks.OLIVE_DOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    trapdoorRecipe(consumer, ModBlocks.OLIVE_TRAPDOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    registerMosaics(consumer, "olive", ModBlocks.OLIVE_MOSAIC.get(), ModBlocks.OLIVE_SLAB.get(), ModBlocks.OLIVE_PLANKS.get(), ModBlocks.OLIVE_MOSAIC_SLAB.get(), ModBlocks.OLIVE_MOSAIC_STAIRS.get());


    /*     deco stone     */
    //calcite
    registerBrickSet(consumer,
            Blocks.CALCITE,
            ModBlocks.CALCITE_BRICKS,
            ModBlocks.CALCITE_BRICK_SLAB,
            ModBlocks.CALCITE_BRICK_STAIRS,
            ModBlocks.CALCITE_BRICK_WALL
    );
    //tuff
    registerBrickSet(consumer,
            Blocks.TUFF,
            ModBlocks.TUFF_TILES,
            ModBlocks.TUFF_TILE_SLAB,
            ModBlocks.TUFF_TILE_STAIRS,
            ModBlocks.TUFF_TILE_WALL
    );
    //smooth stone pillar
    ShapedRecipeBuilder.shaped(ModBlocks.SMOOTH_STONE_PILLAR.get(), 3)
            .pattern("x")
            .pattern("x")
            .pattern("x")
            .define('x', Blocks.SMOOTH_STONE)
            .unlockedBy("has_stone", has(Blocks.STONE))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(Blocks.SMOOTH_STONE), ModBlocks.SMOOTH_STONE_PILLAR.get())
            .unlockedBy("has_stone", has(Blocks.STONE))
            .save(consumer, "smooth_stone_pillar_from_stonecutting");

    /*     metallurgy     */
    //ingots and nuggets
    ShapelessRecipeBuilder.shapeless(ModItems.COPPER_NUGGET.get(), 9)
            .requires(Tags.Items.INGOTS_COPPER)
            .unlockedBy("copper_ingot", has(Items.COPPER_INGOT))
            .save(consumer);
    ShapedRecipeBuilder.shaped(Items.COPPER_INGOT)
            .pattern("xxx")
            .pattern("xxx")
            .pattern("xxx")
            .define('x', TagInit.Items.FORGE_NUGGETS_COPPER)
            .unlockedBy("copper_ingot", has(Items.COPPER_INGOT))
            .save(consumer);

    //chains
    chainRecipe(ModBlocks.GOLD_CHAIN, Tags.Items.NUGGETS_GOLD, Tags.Items.INGOTS_GOLD, "gold_ingot", Items.GOLD_INGOT, consumer);
    chainRecipe(ModBlocks.COPPER_CHAIN, TagInit.Items.FORGE_NUGGETS_COPPER, Tags.Items.INGOTS_COPPER, "copper_ingot", Items.COPPER_INGOT, consumer);
    //posts
    pillarRecipe(consumer, ModBlocks.IRON_POST, Tags.Items.INGOTS_IRON, "has_iron");
    pillarRecipe(consumer, ModBlocks.WOODEN_POST, ItemTags.PLANKS, "has_planks");
    //cast iron
    ShapedRecipeBuilder.shaped(ModBlocks.CAST_IRON_BLOCK.get(), 4)
            .pattern("in")
            .pattern("ni")
            .define('i', Tags.Items.INGOTS_IRON)
            .define('n', Tags.Items.NUGGETS_IRON)
            .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(Tags.Items.INGOTS_IRON), ModBlocks.CAST_IRON_BLOCK.get(), 2)
            .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
            .save(consumer, getItemName(ModBlocks.CAST_IRON_BLOCK.get()) + "_from_stonecutting");
    brickLikeRecipe(consumer, ModBlocks.CAST_IRON_BLOCK.get(), ModBlocks.CAST_IRON_TILES, true);
    stairRecipe(consumer, ModBlocks.CAST_IRON_TILE_STAIRS, ModBlocks.CAST_IRON_TILES, true);
    slabRecipe(consumer, ModBlocks.CAST_IRON_TILE_SLAB, ModBlocks.CAST_IRON_TILES, true);
    wallRecipe(consumer, ModBlocks.CAST_IRON_TILES, ModBlocks.CAST_IRON_TILE_WALL.get());

    /*     ceramic vessels     */
    //default
    ShapedRecipeBuilder.shaped(ModBlocks.CERAMIC_VESSEL.get(), 1)
            .pattern(" t ")
            .pattern("t t")
            .pattern("ttt")
            .define('t', ItemTags.TERRACOTTA)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
            .save(consumer);

    //ashen
    ShapelessRecipeBuilder.shapeless(ModBlocks.VESSEL_MAP.get("ashen").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
            .requires(Tags.Items.DYES_BLACK)
            .requires(Tags.Items.DYES_PURPLE)
            .requires(Tags.Items.DYES_WHITE)
            .requires(Tags.Items.DYES_YELLOW)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(consumer);

    //cerulean
    ShapelessRecipeBuilder.shapeless(ModBlocks.VESSEL_MAP.get("cerulean").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
            .requires(Tags.Items.DYES_LIGHT_BLUE)
            .requires(Tags.Items.DYES_MAGENTA)
            .requires(Tags.Items.DYES_YELLOW)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(consumer);

    //ivory
    ShapelessRecipeBuilder.shapeless(ModBlocks.VESSEL_MAP.get("ivory").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
            .requires(Tags.Items.DYES_WHITE)
            .requires(Tags.Items.DYES_LIGHT_BLUE)
            .requires(Tags.Items.DYES_PINK)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(consumer);

    //roseate
    ShapelessRecipeBuilder.shapeless(ModBlocks.VESSEL_MAP.get("roseate").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
            .requires(Tags.Items.DYES_MAGENTA)
            .requires(Tags.Items.DYES_ORANGE)
            .requires(Tags.Items.DYES_WHITE)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(consumer);

    //verdant
    ShapelessRecipeBuilder.shapeless(ModBlocks.VESSEL_MAP.get("verdant").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
            .requires(Tags.Items.DYES_LIME)
            .requires(Tags.Items.DYES_BLACK)
            .requires(Tags.Items.DYES_WHITE)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(consumer);

    //violaceous
    ShapelessRecipeBuilder.shapeless(ModBlocks.VESSEL_MAP.get("violaceous").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
            .requires(Tags.Items.DYES_PURPLE)
            .requires(Tags.Items.DYES_RED)
            .requires(Tags.Items.DYES_ORANGE)
            .requires(Tags.Items.DYES_YELLOW)
            .requires(Tags.Items.DYES_GREEN)
            .requires(Tags.Items.DYES_BLUE)
            .requires(Tags.Items.DYES_BROWN)
            .requires(Tags.Items.DYES_BLACK)
            .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(consumer);

    /*     storage     */
    ShapedRecipeBuilder.shaped(ModBlocks.FLUID_STORAGE.get())
            .pattern("w w")
            .pattern("i i")
            .pattern("www")
            .define('w', ModBlocks.IRONWOOD_PLANKS.get())
            .define('i', Tags.Items.INGOTS_IRON)
            .unlockedBy("ironwood_log", has(ModBlocks.IRONWOOD_LOG.get()))
            .save(consumer);
  }

  private void wallRecipe(@NotNull Consumer<FinishedRecipe> consumer, RegistryObject<Block> parentBlock, Block wallBlock){
    String id = parentBlock.getId().toString();
    String materialID = parentBlock.toString();
    String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);
    ShapedRecipeBuilder.shaped(wallBlock)
            .pattern("xxx")
            .pattern("xxx")
            .define('x', parentBlock.get())
            .unlockedBy(criterionName, has(parentBlock.get()))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(parentBlock.get()), wallBlock)
            .unlockedBy(criterionName, has(parentBlock.get()))
            .save(consumer, id.substring(id.indexOf(":") + 1) + "_wall_from_stonecutting");
  }

  private void registerMosaics(@NotNull Consumer<FinishedRecipe> consumer, String woodType, Block mosaicBlock, Block slabBlock, Block plankBlock, Block mosaicSlabBlock, Block mosaicStairsBlock){
    //mosaic
    ShapedRecipeBuilder.shaped(mosaicBlock)
            .define('#', Ingredient.of(slabBlock))
            .pattern("#")
            .pattern("#")
            .unlockedBy("has_" + woodType + "_mosaic", has(mosaicBlock))
            .unlockedBy("has_" + woodType + "_plank", has(plankBlock))
            .unlockedBy("has_" + woodType + "_slab", has(slabBlock))
            .save(consumer);
    //slabs
    ShapedRecipeBuilder.shaped(mosaicSlabBlock, 6)
            .define('#', Ingredient.of(mosaicBlock))
            .pattern("###")
            .unlockedBy("has_" + woodType + "_mosaic", has(mosaicBlock))
            .save(consumer);
    //stairs
    ShapedRecipeBuilder.shaped(mosaicStairsBlock, 4)
            .define('#', Ingredient.of(mosaicBlock))
            .pattern("#  ")
            .pattern("## ")
            .pattern("###")
            .unlockedBy("has_" + woodType + "_mosaic", has(mosaicBlock))
            .save(consumer);
  }


  //------------------------------------- methods -------------------------------------//
  private void brickLikeRecipe(@NotNull Consumer<FinishedRecipe> consumer, Block materialBlock, RegistryObject<Block> resultBlock, Boolean canBeCut){
    String id = resultBlock.getId().toString();
    String materialID = materialBlock.toString();
    String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);

    ShapedRecipeBuilder.shaped(resultBlock.get(), 4)
            .pattern("xx")
            .pattern("xx")
            .define('x', materialBlock)
            .unlockedBy(criterionName, has(materialBlock))
            .save(consumer);
    if(canBeCut){
      SingleItemRecipeBuilder
              .stonecutting(Ingredient.of(materialBlock), resultBlock.get())
              .unlockedBy(criterionName, has(materialBlock))
              .save(consumer, id.substring(id.indexOf(":") + 1) + "_from_stonecutting");
    }
  }

  private void stairRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> stairsBlock, RegistryObject<Block> materialBlock, Boolean canBeCut){
    String id = stairsBlock.getId().toString();
    String materialID = materialBlock.getId().toString();
    String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);

    stairBuilder(stairsBlock.get(), Ingredient.of(materialBlock.get()))
            .unlockedBy(criterionName, has(materialBlock.get()))
            .save(consumer);

    if(canBeCut){
      SingleItemRecipeBuilder
              .stonecutting(Ingredient.of(materialBlock.get()), stairsBlock.get())
              .unlockedBy(criterionName, has(materialBlock.get()))
              .save(consumer, id.substring(id.indexOf(":") + 1) + "_stairs_from_stonecutting");
    }
  }

  private void slabRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<Block> slabBlock, RegistryObject<Block> materialBlock, Boolean canBeCut){
    String id = slabBlock.getId().toString();
    String materialID = materialBlock.getId().toString();
    String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);

    slabBuilder(slabBlock.get(), Ingredient.of(materialBlock.get()))
            .unlockedBy(criterionName, has(materialBlock.get()))
            .save(consumer);

    if(canBeCut){
      SingleItemRecipeBuilder
              .stonecutting(Ingredient.of(materialBlock.get()), slabBlock.get(), 2)
              .unlockedBy(criterionName, has(materialBlock.get()))
              .save(consumer, id.substring(id.indexOf(":") + 1) + "_slabs_from_stonecutting");
    }
  }

  private void pillarRecipe(@NotNull Consumer<FinishedRecipe> consumer, RegistryObject<Block> post, TagKey<Item> materialTag, String criterion){
    ShapedRecipeBuilder.shaped(post.get(), 3)
            .pattern("x")
            .pattern("x")
            .pattern("x")
            .define('x', materialTag)
            .unlockedBy(criterion, has(materialTag))
            .save(consumer);
  }

  private void planksFromWood(Consumer<FinishedRecipe> consumer, Block plankBlock, TagKey<Item> woodTag, String name){
    ShapelessRecipeBuilder.shapeless(plankBlock, 4).requires(woodTag).group("planks").unlockedBy("has_wood", has(woodTag)).save(consumer, name);
  }

  private void registerBrickSet(
          @NotNull Consumer<FinishedRecipe> consumer,
          Block parent,
          RegistryObject<Block> bricks,
          RegistryObject<Block> brickSlab,
          RegistryObject<Block> brickStairs
          , RegistryObject<Block> brickWall
  ){


    brickLikeRecipe(consumer, parent, bricks, true); //bricks
    slabRecipe(consumer, brickSlab, bricks, true); //slabs
    stairRecipe(consumer, brickStairs, bricks, true); //stairs
    //walls

    String id = bricks.getId().toString();
    String materialID = parent.toString();
    String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);
    ShapedRecipeBuilder.shaped(brickWall.get())
            .pattern("xxx")
            .pattern("xxx")
            .define('x', bricks.get())
            .unlockedBy(criterionName, has(parent))
            .save(consumer);
    SingleItemRecipeBuilder
            .stonecutting(Ingredient.of(bricks.get()), brickWall.get())
            .unlockedBy(criterionName, has(parent))
            .save(consumer, id.substring(id.indexOf(":") + 1) + "_wall_from_stonecutting");
  }

  private void chainRecipe(RegistryObject<Block> chain, TagKey<Item> nuggetTag, TagKey<Item> ingotTag, String criterionName, Item criterionItem, Consumer<FinishedRecipe> consumer){
    ShapedRecipeBuilder.shaped(chain.get())
            .pattern("o")
            .pattern("#")
            .pattern("o")
            .define('o', nuggetTag)
            .define('#', ingotTag)

            .unlockedBy(criterionName, has(criterionItem))
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


}

