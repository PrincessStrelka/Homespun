package caittastic.homespun.datagen.models;

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModItemModels extends ItemModelProvider{

  public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper){
    super(generator, MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels(){
    //------------------------------------- items -------------------------------------//
    /*     industry     */
    registerFlatItemModel(ModItems.TINY_IRON_DUST);
    registerFlatItemModel(ModItems.COPPER_NUGGET);
    registerFlatItemModel(ModItems.SALT);
    registerFlatItemModel(ModItems.REDSTONE_ACID);

    /*     crops     */
    registerFlatItemModel(ModItems.IRONBERRIES);
    registerFlatItemModel(ModItems.OLIVES);


    /*     fluids     */
    registerFlatItemModel(ModItems.IRONBERRY_JUICE_BOTTLE);
    registerFlatItemModel(ModItems.IRONBERRY_JUICE_BUCKET);

    //------------------------------------- blocks -------------------------------------//
    /*     ironwood     */
    registerFlatBlockWithBlockTexture(ModBlocks.IRONWOOD_SAPLING);
    registerWithExistingParent(ModBlocks.IRONWOOD_LEAVES);
    registerWithExistingParent(ModBlocks.IRONWOOD_LOG);
    registerWithExistingParent(ModBlocks.STRIPPED_IRONWOOD_LOG);
    registerWithExistingParent(ModBlocks.IRONWOOD_WOOD);
    registerWithExistingParent(ModBlocks.STRIPPED_IRONWOOD_WOOD);
    registerWithExistingParent(ModBlocks.IRONWOOD_PLANKS);
    registerWithExistingParent(ModBlocks.IRONWOOD_STAIRS);
    registerWithExistingParent(ModBlocks.IRONWOOD_SLAB);
    registerFlatBlockModelWithItemTexture(ModBlocks.IRONWOOD_DOOR);
    regiserTrapdoor(ModBlocks.IRONWOOD_TRAPDOOR);
    registerWithExistingInventoryModel(ModBlocks.IRONWOOD_FENCE);
    registerWithExistingParent(ModBlocks.IRONWOOD_FENCE_GATE);
    registerWithExistingInventoryModel(ModBlocks.IRONWOOD_BUTTON);
    registerWithExistingParent(ModBlocks.IRONWOOD_PRESSURE_PLATE);
    registerWithExistingParent(ModBlocks.IRONWOOD_MOSAIC);
    registerWithExistingParent(ModBlocks.IRONWOOD_MOSAIC_STAIRS);
    registerWithExistingParent(ModBlocks.IRONWOOD_MOSAIC_SLAB);

    /*     olive     */
    registerFlatBlockWithBlockTexture(ModBlocks.OLIVE_SAPLING);
    registerWithExistingParent(ModBlocks.OLIVE_LEAVES);
    registerWithExistingParent(ModBlocks.OLIVE_LOG);
    registerWithExistingParent(ModBlocks.STRIPPED_OLIVE_LOG);
    registerWithExistingParent(ModBlocks.OLIVE_WOOD);
    registerWithExistingParent(ModBlocks.STRIPPED_OLIVE_WOOD);
    registerWithExistingParent(ModBlocks.OLIVE_PLANKS);
    registerWithExistingParent(ModBlocks.OLIVE_STAIRS);
    registerWithExistingParent(ModBlocks.OLIVE_SLAB);
    registerFlatBlockModelWithItemTexture(ModBlocks.OLIVE_DOOR);
    regiserTrapdoor(ModBlocks.OLIVE_TRAPDOOR);
    registerWithExistingInventoryModel(ModBlocks.OLIVE_FENCE);
    registerWithExistingParent(ModBlocks.OLIVE_FENCE_GATE);
    registerWithExistingInventoryModel(ModBlocks.OLIVE_BUTTON);
    registerWithExistingParent(ModBlocks.OLIVE_PRESSURE_PLATE);
    registerWithExistingParent(ModBlocks.OLIVE_MOSAIC);
    registerWithExistingParent(ModBlocks.OLIVE_MOSAIC_STAIRS);
    registerWithExistingParent(ModBlocks.OLIVE_MOSAIC_SLAB);

    /*     deco stone     */
    registerWithExistingParent(ModBlocks.SMOOTH_STONE_PILLAR);

    registerWithExistingParent(ModBlocks.CALCITE_BRICKS);
    registerWithExistingParent(ModBlocks.CALCITE_BRICK_SLAB);
    registerWithExistingParent(ModBlocks.CALCITE_BRICK_STAIRS);
    registerWithExistingInventoryModel(ModBlocks.CALCITE_BRICK_WALL);

    registerWithExistingParent(ModBlocks.TUFF_TILES);
    registerWithExistingParent(ModBlocks.TUFF_TILE_SLAB);
    registerWithExistingParent(ModBlocks.TUFF_TILE_STAIRS);
    registerWithExistingInventoryModel(ModBlocks.TUFF_TILE_WALL);

    /*     industry     */
    registerWithExistingParent(ModBlocks.CRUSHING_TUB);
    registerWithExistingParent(ModBlocks.EVAPORATING_BASIN);

    /*     metallurgy     */
    registerFlatBlockModelWithItemTexture(ModBlocks.GOLD_CHAIN);
    registerFlatBlockModelWithItemTexture(ModBlocks.COPPER_CHAIN);
    registerWithExistingParent(ModBlocks.IRON_POST);
    registerWithExistingParent(ModBlocks.WOODEN_POST);
    registerWithExistingParent(ModBlocks.CAST_IRON_BLOCK);
    registerWithExistingParent(ModBlocks.CAST_IRON_TILES);
    registerWithExistingParent(ModBlocks.CAST_IRON_TILE_STAIRS);
    registerWithExistingParent(ModBlocks.CAST_IRON_TILE_SLAB);
    registerWithExistingInventoryModel(ModBlocks.CAST_IRON_TILE_WALL);

    /*     storage     */
    registerWithExistingParent(ModBlocks.CERAMIC_VESSEL);
    for(String[] patternKeyword: ModBlocks.vessel_patterns){
      String name = patternKeyword[0];
      registerWithExistingParent(ModBlocks.VESSEL_MAP.get(name));
    }
    String id = ModBlocks.FLUID_STORAGE.getId().toString();
    registerWithExistingParentAtRegistryLocation(
            ModBlocks.FLUID_STORAGE,
            "block/" + id.substring(id.indexOf(":") + 1) + "_inventory");
    //registerWithExistingParent(ModBlocks.CABINET);


  }

  //------------------------------------- methods -------------------------------------//
  private void regiserTrapdoor(RegistryObject<Block> trapdoor){
    String id = trapdoor.getId().toString();
    registerWithExistingParentAtRegistryLocation(
            trapdoor,
            "block/" + id.substring(id.indexOf(":") + 1) + "_bottom"
    );
  }

  private void registerWithExistingInventoryModel(RegistryObject<Block> block){
    String id = block.getId().toString();
    registerWithExistingParentAtRegistryLocation(
            block,
            "item/" + id.substring(id.indexOf(":") + 1) + "_inventory"
    );
  }

  private void registerFlatBlockModelWithItemTexture(RegistryObject<Block> block){
    ResourceLocation resourceLoc = new ResourceLocation(
            MOD_ID,
            "item/" + block.getId().getPath());
    registerFlatBlockModel(block, resourceLoc.getPath());
  }

  private void registerFlatBlockWithBlockTexture(RegistryObject<Block> block){
    String id = block.getId().toString();
    registerFlatBlockModel(
            block,
            "block/" + id.substring(id.indexOf(":") + 1));
  }

  private void registerFlatBlockModel(RegistryObject<Block> block, String registryLoc){
    singleTexture(
            block.getId().getPath(),
            mcLoc("item/generated"),
            "layer0",
            modLoc(registryLoc));
  }

  private void registerFlatItemModel(RegistryObject<Item> item){
    ResourceLocation resourceLocation = new ResourceLocation(
            MOD_ID,
            "item/" + item.getId().getPath());
    singleTexture(
            item.getId().getPath(),
            mcLoc("item/generated"),
            "layer0", resourceLocation);
  }

  private void registerWithExistingParent(RegistryObject<Block> block){
    registerWithExistingParentAtRegistryLocation(
            block,
            "block/" + block.getId().getPath());
  }

  private void registerWithExistingParentAtRegistryLocation(
          RegistryObject<Block> block,
          String registryLoc){
    withExistingParent(block.getId().getPath(), modLoc(registryLoc));
  }
}

