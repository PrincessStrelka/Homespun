package caittastic.homespun.datagen;

import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
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
    /*     IRONWOOD     */
    woodFromLogs(consumer, ModBlocks.IRONWOOD_WOOD.get(), ModBlocks.IRONWOOD_LOG.get());
    woodFromLogs(consumer, ModBlocks.STRIPPED_IRONWOOD_LOG.get(), ModBlocks.STRIPPED_IRONWOOD_LOG.get());
    planksFromLog(consumer, ModBlocks.IRONWOOD_PLANKS.get(), TagInit.Items.IRONWOOD_LOGS);
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
    woodFromLogs(consumer, ModBlocks.STRIPPED_OLIVE_LOG.get(), ModBlocks.STRIPPED_OLIVE_LOG.get());
    planksFromLog(consumer, ModBlocks.OLIVE_PLANKS.get(), TagInit.Items.OLIVE_LOGS);
    stairRecipe(consumer, ModBlocks.OLIVE_STAIRS, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    slabRecipe(consumer, ModBlocks.OLIVE_SLAB, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    fenceRecipe(consumer, ModBlocks.OLIVE_FENCE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    fenceGateRecipe(consumer, ModBlocks.OLIVE_FENCE_GATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    buttonRecipe(consumer, ModBlocks.OLIVE_BUTTON, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    pressurePlateRecipe(consumer, ModBlocks.OLIVE_PRESSURE_PLATE, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    doorRecipe(consumer, ModBlocks.OLIVE_DOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
    trapdoorRecipe(consumer, ModBlocks.OLIVE_TRAPDOOR, ModBlocks.OLIVE_PLANKS, hasPlanksCriterion);
  }

  //------------------------------------- methods -------------------------------------//
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

