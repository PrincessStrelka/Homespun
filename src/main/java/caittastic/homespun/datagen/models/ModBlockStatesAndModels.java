package caittastic.homespun.datagen.models;

import caittastic.homespun.Homespun;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStatesAndModels extends BlockStateProvider{
  public static final String BLOCK_FOLDER = "block";

  public ModBlockStatesAndModels(DataGenerator gen, ExistingFileHelper helper){
    super(gen, Homespun.MOD_ID, helper);
  }

  //------------------------------------- ===== -------------------------------------//
  @Override
  protected void registerStatesAndModels(){
    /*     ironwood     */
    registerCrossBlock(ModBlocks.IRONWOOD_SAPLING); //IRONWOOD_SAPLING
    registerPottedPlant(ModBlocks.POTTED_IRONWOOD_SAPLING, ModBlocks.IRONWOOD_SAPLING); //POTTED_IRONWOOD_SAPLING
    registerLeavesBlock(ModBlocks.IRONWOOD_LEAVES); //IRONWOOD_LEAVES
    logBlock((RotatedPillarBlock)ModBlocks.IRONWOOD_LOG.get()); //IRONWOOD_LOG
    logBlock((RotatedPillarBlock)ModBlocks.STRIPPED_IRONWOOD_LOG.get()); //STRIPPED_IRONWOOD_LOG
    registerAxisBlockWithBaseBlock(ModBlocks.IRONWOOD_WOOD, ModBlocks.IRONWOOD_LOG);
    registerAxisBlockWithBaseBlock(ModBlocks.STRIPPED_IRONWOOD_WOOD, ModBlocks.STRIPPED_IRONWOOD_LOG);
    simpleBlock(ModBlocks.IRONWOOD_PLANKS.get());
    registerStairWithBaseBlock(ModBlocks.IRONWOOD_STAIRS, ModBlocks.IRONWOOD_PLANKS);
    registerSlabWithBaseBlock(ModBlocks.IRONWOOD_SLAB, ModBlocks.IRONWOOD_PLANKS);
    registerFence(ModBlocks.IRONWOOD_FENCE, ModBlocks.IRONWOOD_PLANKS);
    registerFenceGate(ModBlocks.IRONWOOD_FENCE_GATE, ModBlocks.IRONWOOD_PLANKS);
    registerButton(ModBlocks.IRONWOOD_BUTTON, ModBlocks.IRONWOOD_PLANKS);
    registerPressurePlate(ModBlocks.IRONWOOD_PRESSURE_PLATE, ModBlocks.IRONWOOD_PLANKS);
    registerDoor(ModBlocks.IRONWOOD_DOOR);
    registerTrapdoor(ModBlocks.IRONWOOD_TRAPDOOR);
    /*     olive     */
    registerCrossBlock(ModBlocks.OLIVE_SAPLING);
    registerPottedPlant(ModBlocks.POTTED_OLIVE_SAPLING, ModBlocks.OLIVE_SAPLING);
    registerLeavesBlock(ModBlocks.OLIVE_LEAVES);
    logBlock((RotatedPillarBlock)ModBlocks.OLIVE_LOG.get());
    logBlock((RotatedPillarBlock)ModBlocks.STRIPPED_OLIVE_LOG.get());
    registerAxisBlockWithBaseBlock(ModBlocks.OLIVE_WOOD, ModBlocks.OLIVE_LOG);
    registerAxisBlockWithBaseBlock(ModBlocks.STRIPPED_OLIVE_WOOD, ModBlocks.STRIPPED_OLIVE_LOG);
    simpleBlock(ModBlocks.OLIVE_PLANKS.get());
    registerStairWithBaseBlock(ModBlocks.OLIVE_STAIRS, ModBlocks.OLIVE_PLANKS);
    registerSlabWithBaseBlock(ModBlocks.OLIVE_SLAB, ModBlocks.OLIVE_PLANKS);
    registerFence(ModBlocks.OLIVE_FENCE, ModBlocks.OLIVE_PLANKS);
    registerFenceGate(ModBlocks.OLIVE_FENCE_GATE, ModBlocks.OLIVE_PLANKS);
    registerButton(ModBlocks.OLIVE_BUTTON, ModBlocks.OLIVE_PLANKS);
    registerPressurePlate(ModBlocks.OLIVE_PRESSURE_PLATE, ModBlocks.OLIVE_PLANKS);
    registerDoor(ModBlocks.OLIVE_DOOR);
    registerTrapdoor(ModBlocks.OLIVE_TRAPDOOR);
    /*     deco stone     */
    axisBlock((RotatedPillarBlock)ModBlocks.SMOOTH_STONE_PILLAR.get(), blockTexture(ModBlocks.SMOOTH_STONE_PILLAR.get()), blockTexture(Blocks.SMOOTH_STONE));
    simpleBlock(ModBlocks.CALCITE_BRICKS.get());
    registerStairWithBaseBlock(ModBlocks.CALCITE_BRICK_STAIRS, ModBlocks.CALCITE_BRICKS);
    registerSlabWithBaseBlock(ModBlocks.CALCITE_BRICK_SLAB, ModBlocks.CALCITE_BRICKS);
    simpleBlock(ModBlocks.TUFF_TILES.get());
    registerStairWithBaseBlock(ModBlocks.TUFF_TILE_STAIRS, ModBlocks.TUFF_TILES);
    registerSlabWithBaseBlock(ModBlocks.TUFF_TILE_SLAB, ModBlocks.TUFF_TILES);

  }
  //------------------------------------- methods -------------------------------------//

  private void registerLeavesBlock(RegistryObject<Block> leavesBlock){
    simpleBlock(
            leavesBlock.get(),
            models()
                    .singleTexture(leavesBlock.getId().getPath(), mcLoc("block/leaves"), "all", blockTexture(leavesBlock.get()))
                    .renderType(ResourceLocation.tryParse("cutout"))
    );
  }

  private void registerPottedPlant(RegistryObject<Block> pottedPlantBlock, RegistryObject<Block> plantBlock){
    simpleBlock(
            pottedPlantBlock.get(),
            models()
                    .singleTexture(pottedPlantBlock.getId().getPath(),
                            mcLoc("block/flower_pot_cross"), "plant", blockTexture(plantBlock.get()))
                    .renderType(ResourceLocation.tryParse("cutout"))
    );
  }

  private void registerPressurePlate(RegistryObject<Block> pressurePlateBlock, RegistryObject<Block> baseBlock){
    pressurePlateBlock(
            (PressurePlateBlock)pressurePlateBlock.get(),
            blockTexture(baseBlock.get())
    );
  }

  private void registerDoor(RegistryObject<Block> doorBlock){
    doorBlock(
            (DoorBlock)doorBlock.get(),
            new ResourceLocation(Homespun.MOD_ID, "block/" + doorBlock.getId().getPath() + "_bottom"),
            new ResourceLocation(Homespun.MOD_ID, "block/" + doorBlock.getId().getPath() + "_top")
    );
  }

  private void registerFenceGate(RegistryObject<Block> fenceGateBlock, RegistryObject<Block> baseBlock){
    fenceGateBlock((FenceGateBlock)fenceGateBlock.get(),
            blockTexture(baseBlock.get()));
  }

  private void registerTrapdoor(RegistryObject<Block> trapdoorBlock){
    trapdoorBlockWithRenderType((TrapDoorBlock)trapdoorBlock.get(),
            blockTexture(trapdoorBlock.get()), true, "cutout");
  }

  private void registerSlabWithBaseBlock(RegistryObject<Block> slabBlock, RegistryObject<Block> doubleSlabBlock){
    slabBlock((SlabBlock)slabBlock.get(),
            blockTexture(doubleSlabBlock.get()),
            blockTexture(doubleSlabBlock.get()));
  }

  private void registerStairWithBaseBlock(RegistryObject<Block> stairBlock, RegistryObject<Block> baseBlock){
    stairsBlock((StairBlock)stairBlock.get(),
            blockTexture(baseBlock.get()));
  }

  private void registerAxisBlockWithBaseBlock(RegistryObject<Block> axisBlock, RegistryObject<Block> baseBlock){
    axisBlock((RotatedPillarBlock)axisBlock.get(),
            blockTexture(baseBlock.get()),
            blockTexture(baseBlock.get())
    );
  }

  private void registerCrossBlock(RegistryObject<Block> crossBlock){
    simpleBlock(
            crossBlock.get(),
            models()
                    .cross(crossBlock.getId().getPath(), blockTexture(crossBlock.get()))
                    .renderType(ResourceLocation.tryParse("cutout")));
  }

  private void registerButton(RegistryObject<Block> buttonBlock, RegistryObject<Block> baseBlock){
    buttonBlock((ButtonBlock)buttonBlock.get(),
            blockTexture(baseBlock.get()));
    itemModels().buttonInventory(
            ForgeRegistries.BLOCKS.getKey(buttonBlock.get()).getPath() + "_inventory",
            blockTexture(baseBlock.get())
    );
  }

  private void registerFence(RegistryObject<Block> fenceBlock, RegistryObject<Block> fenceBaseBlock){
    fenceBlock(
            (FenceBlock)fenceBlock.get(),
            blockTexture(fenceBaseBlock.get())
    );
    itemModels().fenceInventory(
            ForgeRegistries.BLOCKS.getKey(fenceBlock.get()).toString() + "_inventory",
            blockTexture(fenceBaseBlock.get())
    );
  }
}