package caittastic.homespun.datagen;

import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.datagen.recipe.CrushingRecipeBuilder;
import caittastic.homespun.datagen.recipe.EvaporatingRecipeBuilder;
import caittastic.homespun.datagen.recipe.IORecipeBuilder;
import caittastic.homespun.fluid.ModFluids;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.recipes.CrushingTubRecipe;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModRecipes extends RecipeProvider {
    String hasPlanksCriterion = "has_planks";

    public ModRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput p_recipeOutput, HolderLookup.Provider holderLookup) {
        /*     industry     */
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TINY_IRON_DUST.get()), RecipeCategory.MISC,
                        Items.IRON_NUGGET, 1.0f, 100)
                .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
                .save(p_recipeOutput, "tiny_iron_dust_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TINY_IRON_DUST.get()), RecipeCategory.MISC,
                        Items.IRON_NUGGET, 1.0f, 50)
                .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
                .save(p_recipeOutput, "tiny_iron_dust_blasting");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.CRUSHING_TUB.get(), 1)
                .pattern("w w")
                .pattern("i i")
                .pattern("sss")
                .define('w', ItemTags.PLANKS)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(p_recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.EVAPORATING_BASIN.get(), 1)
                .pattern("t t")
                .pattern(" t ")
                .define('t', ItemTags.TERRACOTTA)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .save(p_recipeOutput);

        /*     IRONWOOD     */

        woodFromLogs(p_recipeOutput, ModBlocks.IRONWOOD_WOOD.get(), ModBlocks.IRONWOOD_LOG.get());
        woodFromLogs(p_recipeOutput, ModBlocks.STRIPPED_IRONWOOD_WOOD.get(), ModBlocks.STRIPPED_IRONWOOD_LOG.get());
        planksFromLog(p_recipeOutput, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_LOGS, 4);
        planksFromWood(p_recipeOutput, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_WOOD, "ironwood_planks_from_wood");
        stairRecipe(p_recipeOutput, ModBlocks.IRONWOOD_STAIRS, ModBlocks.IRONWOOD_PLANKS, false);
        slabRecipe(p_recipeOutput, ModBlocks.IRONWOOD_SLAB, ModBlocks.IRONWOOD_PLANKS, false);
        fenceRecipe(p_recipeOutput, ModBlocks.IRONWOOD_FENCE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
        fenceGateRecipe(p_recipeOutput, ModBlocks.IRONWOOD_FENCE_GATE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
        buttonRecipe(p_recipeOutput, ModBlocks.IRONWOOD_BUTTON, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
        pressurePlateRecipe(p_recipeOutput, ModBlocks.IRONWOOD_PRESSURE_PLATE, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
        doorRecipe(p_recipeOutput, ModBlocks.IRONWOOD_DOOR, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);
        trapdoorRecipe(p_recipeOutput, ModBlocks.IRONWOOD_TRAPDOOR, ModBlocks.IRONWOOD_PLANKS, hasPlanksCriterion);

        registerMosaics(p_recipeOutput, "ironwood", ModBlocks.IRONWOOD_MOSAIC.get(), ModBlocks.IRONWOOD_SLAB.get(), ModBlocks.IRONWOOD_PLANKS.get(), ModBlocks.IRONWOOD_MOSAIC_SLAB.get(), ModBlocks.IRONWOOD_MOSAIC_STAIRS.get());





        /*     OLIVE     */
        woodFromLogs(p_recipeOutput, ModBlocks.OLIVE_WOOD.get(), ModBlocks.OLIVE_LOG.get());
        woodFromLogs(p_recipeOutput, ModBlocks.STRIPPED_OLIVE_WOOD.get(), ModBlocks.STRIPPED_OLIVE_LOG.get());
        planksFromLog(p_recipeOutput, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_LOGS, 4);
        planksFromWood(p_recipeOutput, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_WOOD, "olive_planks_from_wood");
        stairRecipe(p_recipeOutput, ModBlocks.OLIVE_STAIRS, ModBlocks.OLIVE_PLANKS, false);
        slabRecipe(p_recipeOutput, ModBlocks.OLIVE_SLAB, ModBlocks.OLIVE_PLANKS, false);
        fenceRecipe(p_recipeOutput, ModBlocks.OLIVE_FENCE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
        fenceGateRecipe(p_recipeOutput, ModBlocks.OLIVE_FENCE_GATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
        buttonRecipe(p_recipeOutput, ModBlocks.OLIVE_BUTTON, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
        pressurePlateRecipe(p_recipeOutput, ModBlocks.OLIVE_PRESSURE_PLATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
        doorRecipe(p_recipeOutput, ModBlocks.OLIVE_DOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
        trapdoorRecipe(p_recipeOutput, ModBlocks.OLIVE_TRAPDOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
        registerMosaics(p_recipeOutput, "olive", ModBlocks.OLIVE_MOSAIC.get(), ModBlocks.OLIVE_SLAB.get(), ModBlocks.OLIVE_PLANKS.get(), ModBlocks.OLIVE_MOSAIC_SLAB.get(), ModBlocks.OLIVE_MOSAIC_STAIRS.get());


        /*     deco stone     */
        //calcite
        registerBrickSet(p_recipeOutput,
                Blocks.CALCITE,
                ModBlocks.CALCITE_BRICKS,
                ModBlocks.CALCITE_BRICK_SLAB,
                ModBlocks.CALCITE_BRICK_STAIRS,
                ModBlocks.CALCITE_BRICK_WALL
        );
        //tuff
        registerBrickSet(p_recipeOutput,
                Blocks.TUFF,
                ModBlocks.TUFF_TILES,
                ModBlocks.TUFF_TILE_SLAB,
                ModBlocks.TUFF_TILE_STAIRS,
                ModBlocks.TUFF_TILE_WALL
        );
        //smooth stone pillar
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_STONE_PILLAR.get(), 3)
                .pattern("x")
                .pattern("x")
                .pattern("x")
                .define('x', Blocks.SMOOTH_STONE)
                .unlockedBy("has_stone", has(Blocks.STONE))
                .save(p_recipeOutput);
        SingleItemRecipeBuilder
                .stonecutting(Ingredient.of(Blocks.SMOOTH_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_STONE_PILLAR.get())
                .unlockedBy("has_stone", has(Blocks.STONE))
                .save(p_recipeOutput, "smooth_stone_pillar_from_stonecutting");

        /*     metallurgy     */
        //ingots and nuggets
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_NUGGET.get(), 9)
                .requires(Tags.Items.INGOTS_COPPER)
                .unlockedBy("copper_ingot", has(Items.COPPER_INGOT))
                .save(p_recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COPPER_INGOT)
                .pattern("xxx")
                .pattern("xxx")
                .pattern("xxx")
                .define('x', TagInit.Items.FORGE_NUGGETS_COPPER)
                .unlockedBy("copper_ingot", has(Items.COPPER_INGOT))
                .save(p_recipeOutput);
        ItemStack waterPotion = Items.POTION.getDefaultInstance();
        waterPotion.set(DataComponents.POTION_CONTENTS, new PotionContents(holderLookup.holderOrThrow(Potions.WATER.getKey())));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.REDSTONE_ACID)
                .requires(Ingredient.of(waterPotion))
                .requires(Items.REDSTONE)
                .unlockedBy("has_item", has(Items.REDSTONE))
                .save(p_recipeOutput);

        //chains
        chainRecipe(ModBlocks.GOLD_CHAIN, Tags.Items.NUGGETS_GOLD, Tags.Items.INGOTS_GOLD, "gold_ingot", Items.GOLD_INGOT, p_recipeOutput);
        chainRecipe(ModBlocks.COPPER_CHAIN, TagInit.Items.FORGE_NUGGETS_COPPER, Tags.Items.INGOTS_COPPER, "copper_ingot", Items.COPPER_INGOT, p_recipeOutput);
        //posts
        pillarRecipe(p_recipeOutput, ModBlocks.IRON_POST, Tags.Items.INGOTS_IRON, "has_iron");
        pillarRecipe(p_recipeOutput, ModBlocks.WOODEN_POST, ItemTags.PLANKS, "has_planks");
        //cast iron
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CAST_IRON_BLOCK.get(), 4)
                .pattern("in")
                .pattern("ni")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('n', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(p_recipeOutput);
        SingleItemRecipeBuilder
                .stonecutting(Ingredient.of(Tags.Items.INGOTS_IRON), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CAST_IRON_BLOCK.get(), 2)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(p_recipeOutput, getItemName(ModBlocks.CAST_IRON_BLOCK.get()) + "_from_stonecutting");
        brickLikeRecipe(p_recipeOutput, ModBlocks.CAST_IRON_BLOCK.get(), ModBlocks.CAST_IRON_TILES, true);
        stairRecipe(p_recipeOutput, ModBlocks.CAST_IRON_TILE_STAIRS, ModBlocks.CAST_IRON_TILES, true);
        slabRecipe(p_recipeOutput, ModBlocks.CAST_IRON_TILE_SLAB, ModBlocks.CAST_IRON_TILES, true);
        wallRecipe(p_recipeOutput, ModBlocks.CAST_IRON_TILES, ModBlocks.CAST_IRON_TILE_WALL.get());

        /*     ceramic vessels     */
        //default
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CERAMIC_VESSEL.get(), 1)
                .pattern(" t ")
                .pattern("t t")
                .pattern("ttt")
                .define('t', ItemTags.TERRACOTTA)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .save(p_recipeOutput);

        //ashen
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VESSEL_MAP.get("ashen").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
                .requires(Tags.Items.DYES_BLACK)
                .requires(Tags.Items.DYES_PURPLE)
                .requires(Tags.Items.DYES_WHITE)
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(p_recipeOutput);

        //cerulean
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VESSEL_MAP.get("cerulean").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .requires(Tags.Items.DYES_MAGENTA)
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(p_recipeOutput);

        //ivory
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VESSEL_MAP.get("ivory").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
                .requires(Tags.Items.DYES_WHITE)
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .requires(Tags.Items.DYES_PINK)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(p_recipeOutput);

        //roseate
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VESSEL_MAP.get("roseate").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
                .requires(Tags.Items.DYES_MAGENTA)
                .requires(Tags.Items.DYES_ORANGE)
                .requires(Tags.Items.DYES_WHITE)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(p_recipeOutput);

        //verdant
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VESSEL_MAP.get("verdant").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
                .requires(Tags.Items.DYES_LIME)
                .requires(Tags.Items.DYES_BLACK)
                .requires(Tags.Items.DYES_WHITE)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(p_recipeOutput);

        //violaceous
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.VESSEL_MAP.get("violaceous").get(), 1).requires(ModBlocks.CERAMIC_VESSEL.get())
                .requires(Tags.Items.DYES_PURPLE)
                .requires(Tags.Items.DYES_RED)
                .requires(Tags.Items.DYES_ORANGE)
                .requires(Tags.Items.DYES_YELLOW)
                .requires(Tags.Items.DYES_GREEN)
                .requires(Tags.Items.DYES_BLUE)
                .requires(Tags.Items.DYES_BROWN)
                .requires(Tags.Items.DYES_BLACK)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA)).save(p_recipeOutput);

        /*     storage     */
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.FLUID_STORAGE.get())
                .pattern("w w")
                .pattern("i i")
                .pattern("www")
                .define('w', ModBlocks.IRONWOOD_PLANKS.get())
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("ironwood_log", has(ModBlocks.IRONWOOD_LOG.get()))
                .save(p_recipeOutput);

        /* IO Recipes */
        IORecipeBuilder.of(ModItems.IRONBERRY_JUICE_BOTTLE.toStack())
                .emptyItem(Items.GLASS_BOTTLE.getDefaultInstance())
                .fluid(new FluidStack(ModFluids.IRONBERRY_JUICE.get(), 250))
                .save(p_recipeOutput);

        IORecipeBuilder.of(waterPotion)
                .emptyItem(Items.GLASS_BOTTLE.getDefaultInstance())
                .fluid(new FluidStack(Fluids.WATER, 250))
                .save(p_recipeOutput);

        /* Evaporating */
        EvaporatingRecipeBuilder.of(ModItems.SALT.toStack())
                .fluidStack(new FluidStack(Fluids.WATER, 250))
                .craftTime(100)
                .save(p_recipeOutput);

        EvaporatingRecipeBuilder.of(ModItems.TINY_IRON_DUST.toStack())
                .fluidStack(new FluidStack(ModFluids.IRONBERRY_JUICE.get(), 250))
                .craftTime(100)
                .save(p_recipeOutput);

        /* Crushing */

        CrushingRecipeBuilder.of(Items.AIR.getDefaultInstance())
                .fluidStack(new FluidStack(ModFluids.IRONBERRY_JUICE.get(), 250))
                .inputStack(SizedIngredient.of(ModItems.IRONBERRIES.get(), 1))
                .save(p_recipeOutput);

        CrushingRecipeBuilder.of(Items.FLINT.getDefaultInstance())
                .fluidStack(FluidStack.EMPTY)
                .inputStack(SizedIngredient.of(Tags.Items.GRAVELS, 1))
                .save(p_recipeOutput);

        CrushingRecipeBuilder.of(Items.SUGAR.getDefaultInstance())
                .fluidStack(new FluidStack(Fluids.WATER, 250))
                .inputStack(SizedIngredient.of(Items.BEETROOT, 1))
                .save(p_recipeOutput);

        CrushingRecipeBuilder.of(ModItems.TINY_IRON_DUST.toStack())
                .fluidStack(FluidStack.EMPTY)
                .inputStack(SizedIngredient.of(Items.RAW_IRON, 1))
                .save(p_recipeOutput);
    }

    private void wallRecipe(@NotNull RecipeOutput p_recipeOutput, DeferredBlock<?> parentBlock, Block wallBlock) {
        String id = parentBlock.getId().getPath();
        String materialID = parentBlock.toString();
        String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, wallBlock)
                .pattern("xxx")
                .pattern("xxx")
                .define('x', parentBlock.get())
                .unlockedBy(criterionName, has(parentBlock.get()))
                .save(p_recipeOutput);
        SingleItemRecipeBuilder
                .stonecutting(Ingredient.of(parentBlock.get()), RecipeCategory.DECORATIONS, wallBlock)
                .unlockedBy(criterionName, has(parentBlock.get()))
                .save(p_recipeOutput, id.substring(id.indexOf(":") + 1) + "_wall_from_stonecutting");
    }

    private void registerMosaics(@NotNull RecipeOutput p_recipeOutput, String woodType, Block mosaicBlock, Block slabBlock, Block plankBlock, Block mosaicSlabBlock, Block mosaicStairsBlock) {
        //mosaic
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, mosaicBlock)
                .define('#', Ingredient.of(slabBlock))
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + woodType + "_mosaic", has(mosaicBlock))
                .unlockedBy("has_" + woodType + "_plank", has(plankBlock))
                .unlockedBy("has_" + woodType + "_slab", has(slabBlock))
                .save(p_recipeOutput);
        //slabs
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, mosaicSlabBlock, 6)
                .define('#', Ingredient.of(mosaicBlock))
                .pattern("###")
                .unlockedBy("has_" + woodType + "_mosaic", has(mosaicBlock))
                .save(p_recipeOutput);
        //stairs
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, mosaicStairsBlock, 4)
                .define('#', Ingredient.of(mosaicBlock))
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_" + woodType + "_mosaic", has(mosaicBlock))
                .save(p_recipeOutput);
    }


    //------------------------------------- methods -------------------------------------//
    private void brickLikeRecipe(@NotNull RecipeOutput p_recipeOutput, Block materialBlock, DeferredBlock<?> resultBlock, Boolean canBeCut) {
        String id = resultBlock.getId().getPath();
        String materialID = materialBlock.toString();
        String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, resultBlock.get(), 4)
                .pattern("xx")
                .pattern("xx")
                .define('x', materialBlock)
                .unlockedBy(criterionName, has(materialBlock))
                .save(p_recipeOutput);
        if (canBeCut) {
            SingleItemRecipeBuilder
                    .stonecutting(Ingredient.of(materialBlock), RecipeCategory.DECORATIONS, resultBlock.get())
                    .unlockedBy(criterionName, has(materialBlock))
                    .save(p_recipeOutput, id.substring(id.indexOf(":") + 1) + "_from_stonecutting");
        }
    }

    private void stairRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> stairsBlock, DeferredBlock<?> materialBlock, Boolean canBeCut) {
        String id = stairsBlock.getId().getPath();
        String materialID = materialBlock.getId().getPath();
        String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);

        stairBuilder(stairsBlock.get(), Ingredient.of(materialBlock.get()))
                .unlockedBy(criterionName, has(materialBlock.get()))
                .save(p_recipeOutput);

        if (canBeCut) {
            SingleItemRecipeBuilder
                    .stonecutting(Ingredient.of(materialBlock.get()), RecipeCategory.DECORATIONS, stairsBlock.get())
                    .unlockedBy(criterionName, has(materialBlock.get()))
                    .save(p_recipeOutput, id.substring(id.indexOf(":") + 1) + "_stairs_from_stonecutting");
        }
    }

    private void slabRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> slabBlock, DeferredBlock<?> materialBlock, Boolean canBeCut) {
        String id = slabBlock.getId().getPath();
        String materialID = materialBlock.getId().getPath();
        String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);

        slabBuilder(RecipeCategory.DECORATIONS, slabBlock.get(), Ingredient.of(materialBlock.get()))
                .unlockedBy(criterionName, has(materialBlock.get()))
                .save(p_recipeOutput);

        if (canBeCut) {
            SingleItemRecipeBuilder
                    .stonecutting(Ingredient.of(materialBlock.get()), RecipeCategory.DECORATIONS, slabBlock.get(), 2)
                    .unlockedBy(criterionName, has(materialBlock.get()))
                    .save(p_recipeOutput, id.substring(id.indexOf(":") + 1) + "_slabs_from_stonecutting");
        }
    }

    private void pillarRecipe(@NotNull RecipeOutput p_recipeOutput, DeferredBlock<?> post, TagKey<Item> materialTag, String criterion) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, post.get(), 3)
                .pattern("x")
                .pattern("x")
                .pattern("x")
                .define('x', materialTag)
                .unlockedBy(criterion, has(materialTag))
                .save(p_recipeOutput);
    }

    private void planksFromWood(RecipeOutput p_recipeOutput, Block plankBlock, TagKey<Item> woodTag, String name) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, plankBlock, 4).requires(woodTag).group("planks").unlockedBy("has_wood", has(woodTag)).save(p_recipeOutput, name);
    }

    private void registerBrickSet(
            @NotNull RecipeOutput p_recipeOutput,
            Block parent,
            DeferredBlock<?> bricks,
            DeferredBlock<?> brickSlab,
            DeferredBlock<?> brickStairs
            , DeferredBlock<?> brickWall
    ) {


        brickLikeRecipe(p_recipeOutput, parent, bricks, true); //bricks
        slabRecipe(p_recipeOutput, brickSlab, bricks, true); //slabs
        stairRecipe(p_recipeOutput, brickStairs, bricks, true); //stairs
        //walls

        String id = BuiltInRegistries.BLOCK.getKey(parent).getPath();
        String materialID = parent.toString();
        String criterionName = "has_" + materialID.substring(materialID.indexOf(":") + 1);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, brickWall.get())
                .pattern("xxx")
                .pattern("xxx")
                .define('x', bricks.get())
                .unlockedBy(criterionName, has(parent))
                .save(p_recipeOutput);
        SingleItemRecipeBuilder
                .stonecutting(Ingredient.of(bricks.get()), RecipeCategory.DECORATIONS, brickWall.get())
                .unlockedBy(criterionName, has(parent))
                .save(p_recipeOutput, id.substring(id.indexOf(":") + 1) + "_wall_from_stonecutting");
    }

    private void chainRecipe(DeferredBlock<?> chain, TagKey<Item> nuggetTag, TagKey<Item> ingotTag, String criterionName, Item criterionItem, RecipeOutput p_recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, chain.get())
                .pattern("o")
                .pattern("#")
                .pattern("o")
                .define('o', nuggetTag)
                .define('#', ingotTag)

                .unlockedBy(criterionName, has(criterionItem))
                .save(p_recipeOutput);
    }

    private void trapdoorRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> trapdoorBlock, DeferredBlock<?> materialBlock, String criterionName) {
        trapdoorBuilder(trapdoorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(p_recipeOutput);
    }

    private void doorRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> doorBlock, DeferredBlock<?> materialBlock, String criterionName) {
        doorBuilder(doorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(p_recipeOutput);
    }

    private void pressurePlateRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> pressurePlateBlock, DeferredBlock<?> materialBlock, String criterionName) {
        pressurePlateBuilder(RecipeCategory.REDSTONE, pressurePlateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(p_recipeOutput);
    }

    private void buttonRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> buttonBlock, DeferredBlock<?> materialBlock, String criterionName) {
        buttonBuilder(buttonBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(p_recipeOutput);
    }

    private void fenceGateRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> fenceGateBlock, DeferredBlock<?> materialBlock, String criterionName) {
        fenceGateBuilder(fenceGateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(p_recipeOutput);
    }

    private void fenceRecipe(RecipeOutput p_recipeOutput, DeferredBlock<?> fenceBlock, DeferredBlock<?> materialBlock, String criterionName) {
        fenceBuilder(fenceBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, has(materialBlock.get())).save(p_recipeOutput);
    }


}

