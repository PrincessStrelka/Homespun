package caittastic.homespun.datagen.models;

import caittastic.homespun.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModBlockStatesAndModels extends BlockStateProvider{

  public ModBlockStatesAndModels(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, MOD_ID, exFileHelper);
  }

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
    simpleBlock(ModBlocks.IRONWOOD_MOSAIC.get());
    registerStairWithBaseBlock(ModBlocks.IRONWOOD_MOSAIC_STAIRS, ModBlocks.IRONWOOD_MOSAIC);
    registerSlabWithBaseBlock(ModBlocks.IRONWOOD_MOSAIC_SLAB, ModBlocks.IRONWOOD_MOSAIC);

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
    simpleBlock(ModBlocks.OLIVE_MOSAIC.get());
    registerStairWithBaseBlock(ModBlocks.OLIVE_MOSAIC_STAIRS, ModBlocks.OLIVE_MOSAIC);
    registerSlabWithBaseBlock(ModBlocks.OLIVE_MOSAIC_SLAB, ModBlocks.OLIVE_MOSAIC);

    /*     deco stone     */
    axisBlock((RotatedPillarBlock)ModBlocks.SMOOTH_STONE_PILLAR.get(), blockTexture(ModBlocks.SMOOTH_STONE_PILLAR.get()), blockTexture(Blocks.SMOOTH_STONE));

    simpleBlock(ModBlocks.CALCITE_BRICKS.get());
    registerStairWithBaseBlock(ModBlocks.CALCITE_BRICK_STAIRS, ModBlocks.CALCITE_BRICKS);
    registerSlabWithBaseBlock(ModBlocks.CALCITE_BRICK_SLAB, ModBlocks.CALCITE_BRICKS);
    registerWall(ModBlocks.CALCITE_BRICK_WALL, ModBlocks.CALCITE_BRICKS);

    simpleBlock(ModBlocks.TUFF_TILES.get());
    registerStairWithBaseBlock(ModBlocks.TUFF_TILE_STAIRS, ModBlocks.TUFF_TILES);
    registerSlabWithBaseBlock(ModBlocks.TUFF_TILE_SLAB, ModBlocks.TUFF_TILES);
    registerWall(ModBlocks.TUFF_TILE_WALL, ModBlocks.TUFF_TILES);

    /*     metallurgy     */
    simpleBlock(ModBlocks.CAST_IRON_BLOCK.get());
    simpleBlock(ModBlocks.CAST_IRON_TILES.get());
    registerStairWithBaseBlock(ModBlocks.CAST_IRON_TILE_STAIRS, ModBlocks.CAST_IRON_TILES);
    registerSlabWithBaseBlock(ModBlocks.CAST_IRON_TILE_SLAB, ModBlocks.CAST_IRON_TILES);
    registerWall(ModBlocks.CAST_IRON_TILE_WALL, ModBlocks.CAST_IRON_TILES);

    /*     storage     */
    registerVesselModel(ModBlocks.CERAMIC_VESSEL);
    for(String[] patternKeyword: ModBlocks.vessel_patterns){
      String name = patternKeyword[0];
      registerVesselModel(ModBlocks.VESSEL_MAP.get(name));
    }

  }

  //------------------------------------- methods -------------------------------------//
  private void registerVesselModel(DeferredBlock<?> vessel){
    simpleBlock(
            vessel.get(),
            models().withExistingParent(vessel.getId().getPath(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "block/base_ceramic_vessel"))
                    .texture("top", vesselTexture(vessel.get()) + "_top")
                    .texture("bottom", vesselTexture(vessel.get()) + "_bottom")
                    .texture("side", vesselTexture(vessel.get()) + "_side")
    );
  }

  public ResourceLocation vesselTexture(Block block){
    ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
    return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + "vessels/" + name.getPath());
  }

  private void registerLeavesBlock(DeferredBlock<?> leavesBlock){
    simpleBlock(
            leavesBlock.get(),
            models()
                    .singleTexture(leavesBlock.getId().getPath(), mcLoc("block/leaves"), "all", blockTexture(leavesBlock.get()))
                    .renderType(ResourceLocation.tryParse("cutout"))
    );
  }

  private void registerPottedPlant(DeferredBlock<?> pottedPlantBlock, DeferredBlock<?> plantBlock){
    simpleBlock(
            pottedPlantBlock.get(),
            models()
                    .singleTexture(pottedPlantBlock.getId().getPath(),
                            mcLoc("block/flower_pot_cross"), "plant", blockTexture(plantBlock.get()))
                    .renderType(ResourceLocation.tryParse("cutout"))
    );
  }

  private void registerPressurePlate(DeferredBlock<?> pressurePlateBlock, DeferredBlock<?> baseBlock){
    pressurePlateBlock(
            (PressurePlateBlock)pressurePlateBlock.get(),
            blockTexture(baseBlock.get())
    );
  }

  private void registerDoor(DeferredBlock<?> doorBlock){
    doorBlock(
            (DoorBlock)doorBlock.get(),
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(doorBlock.get()).getPath() + "_bottom"),
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(doorBlock.get()).getPath() + "_top")
    );
  }

  private void registerFenceGate(DeferredBlock<?> fenceGateBlock, DeferredBlock<?> baseBlock){
    fenceGateBlock((FenceGateBlock)fenceGateBlock.get(),
            blockTexture(baseBlock.get()));
  }

  private void registerTrapdoor(DeferredBlock<?> trapdoorBlock){
    trapdoorBlockWithRenderType((TrapDoorBlock)trapdoorBlock.get(),
            blockTexture(trapdoorBlock.get()), true, "cutout");
  }

  private void registerSlabWithBaseBlock(DeferredBlock<?> slabBlock, DeferredBlock<?> doubleSlabBlock){
    slabBlock((SlabBlock)slabBlock.get(),
            blockTexture(doubleSlabBlock.get()),
            blockTexture(doubleSlabBlock.get()));
  }

  private void registerStairWithBaseBlock(DeferredBlock<?> stairBlock, DeferredBlock<?> baseBlock){
    stairsBlock((StairBlock)stairBlock.get(),
            blockTexture(baseBlock.get()));
  }

  private void registerAxisBlockWithBaseBlock(DeferredBlock<?> axisBlock, DeferredBlock<?> baseBlock){
    axisBlock((RotatedPillarBlock)axisBlock.get(),
            blockTexture(baseBlock.get()),
            blockTexture(baseBlock.get())
    );
  }

  private void registerCrossBlock(DeferredBlock<?> crossBlock){
    simpleBlock(
            crossBlock.get(),
            models()
                    .cross(BuiltInRegistries.BLOCK.getKey(crossBlock.get()).getPath(), blockTexture(crossBlock.get()))
                    .renderType(ResourceLocation.tryParse("cutout")));
  }

  private void registerButton(DeferredBlock<?> buttonBlock, DeferredBlock<?> baseBlock){
    buttonBlock((ButtonBlock)buttonBlock.get(),
            blockTexture(baseBlock.get()));
    itemModels().buttonInventory(
            BuiltInRegistries.BLOCK.getKey(buttonBlock.get()).getPath() + "_inventory",
            blockTexture(baseBlock.get())
    );
  }

  private void registerFence(DeferredBlock<?> fenceBlock, DeferredBlock<?> fenceBaseBlock){
    fenceBlock(
            (FenceBlock)fenceBlock.get(),
            blockTexture(fenceBaseBlock.get())
    );
    itemModels().fenceInventory(
            BuiltInRegistries.BLOCK.getKey(fenceBlock.get()).toString() + "_inventory",
            blockTexture(fenceBaseBlock.get())
    );
  }

  private void registerWall(DeferredBlock<?> wallBlock, DeferredBlock<?> ParentBlock){
    wallBlock(
            (WallBlock)wallBlock.get(),
            blockTexture(ParentBlock.get())
    );
    itemModels().wallInventory(
            BuiltInRegistries.BLOCK.getKey(wallBlock.get()).toString() + "_inventory",
            blockTexture(ParentBlock.get())
    );
  }
}