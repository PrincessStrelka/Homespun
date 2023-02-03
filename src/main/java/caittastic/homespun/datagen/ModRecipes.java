package caittastic.homespun.datagen;

import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        /*     ironwood     */
        woodFromLogs(consumer, ModBlocks.IRONWOOD_WOOD.get(), ModBlocks.IRONWOOD_LOG.get());//IRONWOOD_WOOD
        woodFromLogs(consumer, ModBlocks.STRIPPED_IRONWOOD_LOG.get(), ModBlocks.STRIPPED_IRONWOOD_LOG.get());//STRIPPED_IRONWOOD_WOOD
        planksFromLog(consumer, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_LOGS);//IRONWOOD_PLANKS
        simpleStairBuilder(consumer, ModBlocks.IRONWOOD_STAIRS, ModBlocks.IRONWOOD_WOOD, "has_planks", has(ModBlocks.IRONWOOD_PLANKS.get()));//IRONWOOD_STAIRS
        simpleSlabBuilder(consumer, ModBlocks.IRONWOOD_SLAB, ModBlocks.IRONWOOD_WOOD, "has_planks", has(ModBlocks.IRONWOOD_PLANKS.get()));//IRONWOOD_SLAB
        simpleFenceBuilder(consumer, ModBlocks.IRONWOOD_FENCE, ModBlocks.IRONWOOD_WOOD, "has_planks", has(ModBlocks.IRONWOOD_PLANKS.get()));//IRONWOOD_FENCE
        simpleFenceGateBuilder(consumer, ModBlocks.IRONWOOD_FENCE_GATE, ModBlocks.IRONWOOD_WOOD, has(ModBlocks.IRONWOOD_PLANKS.get()), "has_planks");//IRONWOOD_FENCE_GATE
        simpleButtonBuilder(consumer, ModBlocks.IRONWOOD_BUTTON, ModBlocks.IRONWOOD_WOOD, "has_planks", has(ModBlocks.IRONWOOD_PLANKS.get()));//IRONWOOD_BUTTON
        simplePressurePlateBuilder(consumer, ModBlocks.IRONWOOD_PRESSURE_PLATE, ModBlocks.IRONWOOD_WOOD, "has_planks", has(ModBlocks.IRONWOOD_PLANKS.get()));//IRONWOOD_PRESSURE_PLATE
        simpleDoorBuiler(consumer, ModBlocks.IRONWOOD_DOOR, ModBlocks.IRONWOOD_WOOD, "has_planks", has(ModBlocks.IRONWOOD_PLANKS.get()));//IRONWOOD_DOOR
        simpleTrapdoorBUiler(consumer, ModBlocks.IRONWOOD_TRAPDOOR, ModBlocks.IRONWOOD_WOOD, "has_planks", has(ModBlocks.IRONWOOD_PLANKS.get()));//IRONWOOD_TRAPDOOR
        /*     olive     */
        /*
        woodFromLogs(consumer, ModBlocks.OLIVE_WOOD.get(), ModBlocks.OLIVE_LOG.get());//OLIVE_WOOD
        woodFromLogs(consumer, ModBlocks.STRIPPED_OLIVE_LOG.get(), ModBlocks.STRIPPED_OLIVE_LOG.get());//STRIPPED_OLIVE_WOOD
        planksFromLog(consumer, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_LOGS);//OLIVE_PLANKS
        simpleStairBuilder(consumer, ModBlocks.OLIVE_STAIRS, ModBlocks.OLIVE_WOOD, "has_planks", has(ModBlocks.OLIVE_PLANKS.get()));//OLIVE_STAIRS
        simpleSlabBuilder(consumer, ModBlocks.OLIVE_SLAB, ModBlocks.OLIVE_WOOD, "has_planks", has(ModBlocks.OLIVE_PLANKS.get()));//OLIVE_SLAB
        simpleFenceBuilder(consumer, ModBlocks.OLIVE_FENCE, ModBlocks.OLIVE_WOOD, "has_planks", has(ModBlocks.OLIVE_PLANKS.get()));//OLIVE_FENCE
        simpleFenceGateBuilder(consumer, ModBlocks.OLIVE_FENCE_GATE, ModBlocks.OLIVE_WOOD, has(ModBlocks.OLIVE_PLANKS.get()), "has_planks");//OLIVE_FENCE_GATE
        simpleButtonBuilder(consumer, ModBlocks.OLIVE_BUTTON, ModBlocks.OLIVE_WOOD, "has_planks", has(ModBlocks.OLIVE_PLANKS.get()));//OLIVE_BUTTON
        simplePressurePlateBuilder(consumer, ModBlocks.OLIVE_PRESSURE_PLATE, ModBlocks.OLIVE_WOOD, "has_planks", has(ModBlocks.OLIVE_PLANKS.get()));//OLIVE_PRESSURE_PLATE
        simpleDoorBuiler(consumer, ModBlocks.OLIVE_DOOR, ModBlocks.OLIVE_WOOD, "has_planks", has(ModBlocks.OLIVE_PLANKS.get()));//OLIVE_DOOR
        simpleTrapdoorBUiler(consumer, ModBlocks.OLIVE_TRAPDOOR, ModBlocks.OLIVE_WOOD, "has_planks", has(ModBlocks.OLIVE_PLANKS.get()));//OLIVE_TRAPDOOR
         */

        /*     seeds     */
        /*
        ShapelessRecipeBuilder.shapeless(ModItemRegistry.TOMATO_SEEDS.get(), 1).requires(ModItemRegistry.TOMATO.get()).group("seeds").unlockedBy("has_crop", has(ModItemRegistry.TOMATO.get())).save(consumer);//TOMATO_SEEDS
        ShapelessRecipeBuilder.shapeless(ModItemRegistry.CHILI_PEPPER_SEEDS.get(), 1).requires(ModItemRegistry.CHILI_PEPPER.get()).group("seeds").unlockedBy("has_crop", has(ModItemRegistry.CHILI_PEPPER.get())).save(consumer);//CHILI_PEPPER_SEEDS
         */

        ShapelessRecipeBuilder.shapeless(ModItems.TINY_IRON_DUST.get(), 1).requires(ModItems.IRONBERRIES.get()).unlockedBy("has_ironberries", has(ModItems.IRONBERRIES.get())).save(consumer); //todo change this to crushing tub
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TINY_IRON_DUST.get()),
                        Items.IRON_NUGGET, 0.1f, 200)
                .unlockedBy("has_tiny_iron_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.TINY_IRON_DUST.get()).build()))
                .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
                .save(consumer, "iron_nugget_from_smelting_tiny_iron_dust");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TINY_IRON_DUST.get()),
                        Items.IRON_NUGGET, 0.1f, 100)
                .unlockedBy("has_tiny_iron_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.TINY_IRON_DUST.get()).build()))
                .unlockedBy("has_ironberries", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.IRONBERRIES.get()).build()))
                .save(consumer, "iron_nugget_from_blasting_tiny_iron_dust");

        ShapedRecipeBuilder.shaped(ModBlocks.IRONWOOD_SAPLING.get())
                .pattern("nnn")
                .pattern("nsn")
                .pattern("nnn")
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('s', ItemTags.SAPLINGS)
                .group("homespun")
                .unlockedBy("has_ironberries", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRONBERRIES.get()))
                .unlockedBy("has_tiny_iron_dust", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TINY_IRON_DUST.get()))
                .save(consumer);




    }

    //------------------------------------- methods -------------------------------------//

    private void simpleTrapdoorBUiler(Consumer<FinishedRecipe> consumer, RegistryObject<Block> trapdoorBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger) {
        trapdoorBuilder(trapdoorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }

    private void simpleDoorBuiler(Consumer<FinishedRecipe> consumer, RegistryObject<Block> doorBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger) {
        doorBuilder(doorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }

    private void simplePressurePlateBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> pressurePlateBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger) {
        pressurePlateBuilder(pressurePlateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }

    private void simpleButtonBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> buttonBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger) {
        buttonBuilder(buttonBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }

    private void simpleFenceGateBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> fenceGateBlock, RegistryObject<Block> materialBlock, InventoryChangeTrigger.TriggerInstance criterionTrigger, String criterionName) {
        fenceGateBuilder(fenceGateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }

    private void simpleFenceBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> fenceBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger) {
        fenceBuilder(fenceBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }

    private void simpleSlabBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> slabBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger) {
        slabBuilder(slabBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }

    private void simpleStairBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> stairsBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger) {
        stairBuilder(stairsBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
    }
}

