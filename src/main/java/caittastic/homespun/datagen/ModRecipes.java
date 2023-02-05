package caittastic.homespun.datagen;

import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider{
  public ModRecipes(DataGenerator generator){
    super(generator);
  }

  @Override
  protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer){
    /*     ironwood     */
    woodFromLogs(consumer, ModBlocks.IRONWOOD_WOOD.get(), ModBlocks.IRONWOOD_LOG.get());
    woodFromLogs(consumer, ModBlocks.STRIPPED_IRONWOOD_LOG.get(), ModBlocks.STRIPPED_IRONWOOD_LOG.get());
    planksFromLog(consumer, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_LOGS);
    simpleStairBuilder(consumer,
            ModBlocks.IRONWOOD_STAIRS,
            ModBlocks.IRONWOOD_PLANKS,
            "has_planks",
            has(ModBlocks.IRONWOOD_PLANKS.get()));
    simpleSlabBuilder(consumer,
            ModBlocks.IRONWOOD_SLAB,
            ModBlocks.IRONWOOD_PLANKS,
            "has_planks",
            has(ModBlocks.IRONWOOD_PLANKS.get()));
    simpleFenceBuilder(consumer,
            ModBlocks.IRONWOOD_FENCE,
            ModBlocks.IRONWOOD_PLANKS,
            "has_planks",
            has(ModBlocks.IRONWOOD_PLANKS.get()));
    simpleFenceGateBuilder(consumer,
            ModBlocks.IRONWOOD_FENCE_GATE,
            ModBlocks.IRONWOOD_PLANKS,
            has(ModBlocks.IRONWOOD_PLANKS.get()),
            "has_planks");
    simpleButtonBuilder(consumer,
            ModBlocks.IRONWOOD_BUTTON,
            ModBlocks.IRONWOOD_PLANKS,
            "has_planks",
            has(ModBlocks.IRONWOOD_PLANKS.get()));
    simplePressurePlateBuilder(consumer,
            ModBlocks.IRONWOOD_PRESSURE_PLATE,
            ModBlocks.IRONWOOD_PLANKS,
            "has_planks",
            has(ModBlocks.IRONWOOD_PLANKS.get()));
    simpleDoorBuilder(consumer,
            ModBlocks.IRONWOOD_DOOR,
            ModBlocks.IRONWOOD_PLANKS,
            "has_planks",
            has(ModBlocks.IRONWOOD_PLANKS.get()));
    simpleTrapdoorBuilder(consumer,
            ModBlocks.IRONWOOD_TRAPDOOR,
            ModBlocks.IRONWOOD_PLANKS,
            "has_planks",
            has(ModBlocks.IRONWOOD_PLANKS.get()));
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
  }

  //------------------------------------- methods -------------------------------------//
  private void simpleTrapdoorBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> trapdoorBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger){
    trapdoorBuilder(trapdoorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }

  private void simpleDoorBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> doorBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger){
    doorBuilder(doorBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }

  private void simplePressurePlateBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> pressurePlateBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger){
    pressurePlateBuilder(pressurePlateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }

  private void simpleButtonBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> buttonBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger){
    buttonBuilder(buttonBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }

  private void simpleFenceGateBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> fenceGateBlock, RegistryObject<Block> materialBlock, InventoryChangeTrigger.TriggerInstance criterionTrigger, String criterionName){
    fenceGateBuilder(fenceGateBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }

  private void simpleFenceBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> fenceBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger){
    fenceBuilder(fenceBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }

  private void simpleSlabBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> slabBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger){
    slabBuilder(slabBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }

  private void simpleStairBuilder(Consumer<FinishedRecipe> consumer, RegistryObject<Block> stairsBlock, RegistryObject<Block> materialBlock, String criterionName, InventoryChangeTrigger.TriggerInstance criterionTrigger){
    stairBuilder(stairsBlock.get(), Ingredient.of(materialBlock.get())).unlockedBy(criterionName, criterionTrigger).save(consumer);
  }
}

