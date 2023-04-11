package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.ModTabRegistry;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class ModEnUsLanguageProvider extends LanguageProvider{
  public ModEnUsLanguageProvider(DataGenerator gen, String locale){
    super(gen, Homespun.MOD_ID, locale);
  }

  @Override
  protected void addTranslations(){
    //------------------------------------- items -------------------------------------//
    /*     industry     */
    add(ModItems.TINY_IRON_DUST.get(), "Tiny Pile Of Iron Dust");
    add(ModItems.COPPER_NUGGET.get(), "Copper Nugget");
    add(ModItems.SALT.get(), "Salt Rocks");
    add(ModItems.REDSTONE_ACID.get(), "Redstone Acid");
    /*     crops     */
    add(ModItems.IRONBERRIES.get(), "Ironberries");
    add(ModItems.OLIVES.get(), "Olives");
    /*     fluids     */
    add(ModItems.IRONBERRY_JUICE_BOTTLE.get(), "Ironberry Juice Bottle");
    add(ModItems.IRONBERRY_JUICE_BUCKET.get(), "Ironberry Juice Bucket");
    //------------------------------------- blocks -------------------------------------//
    /*     ironwood     */
    add(ModBlocks.IRONWOOD_SAPLING.get(), "Ironwood Sapling");
    add(ModBlocks.POTTED_IRONWOOD_SAPLING.get(), "Potted Ironwood Sapling");
    add(ModBlocks.IRONWOOD_LEAVES.get(), "Ironwood Leaves");
    add(ModBlocks.IRONWOOD_LOG.get(), "Ironwood Log");
    add(ModBlocks.STRIPPED_IRONWOOD_LOG.get(), "Stripped Ironwood Log");
    add(ModBlocks.IRONWOOD_WOOD.get(), "Ironwood Wood");
    add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get(), "Stripped Ironwood Wood");
    add(ModBlocks.IRONWOOD_PLANKS.get(), "Ironwood Planks");
    add(ModBlocks.IRONWOOD_STAIRS.get(), "Ironwood Stairs");
    add(ModBlocks.IRONWOOD_SLAB.get(), "Ironwood Slab");
    add(ModBlocks.IRONWOOD_FENCE.get(), "Ironwood Fence");
    add(ModBlocks.IRONWOOD_FENCE_GATE.get(), "Ironwood Fence Gate");
    add(ModBlocks.IRONWOOD_BUTTON.get(), "Ironwood Button");
    add(ModBlocks.IRONWOOD_PRESSURE_PLATE.get(), "Ironwood Pressure Plate");
    add(ModBlocks.IRONWOOD_DOOR.get(), "Ironwood Door");
    add(ModBlocks.IRONWOOD_TRAPDOOR.get(), "Ironwood Trapdoor");
    add(ModBlocks.IRONWOOD_MOSAIC.get(), "Ironwood Mosaic");
    add(ModBlocks.IRONWOOD_MOSAIC_STAIRS.get(), "Ironwood Mosaic Stairs");
    add(ModBlocks.IRONWOOD_MOSAIC_SLAB.get(), "Ironwood Mosaic Slab");

    /*     olive     */
    add(ModBlocks.OLIVE_SAPLING.get(), "Olive Sapling");
    add(ModBlocks.POTTED_OLIVE_SAPLING.get(), "Potted Olive Sapling");
    add(ModBlocks.OLIVE_LEAVES.get(), "Olive Leaves");
    add(ModBlocks.OLIVE_LOG.get(), "Olive Log");
    add(ModBlocks.STRIPPED_OLIVE_LOG.get(), "Stripped Olive Log");
    add(ModBlocks.OLIVE_WOOD.get(), "Olive Wood");
    add(ModBlocks.STRIPPED_OLIVE_WOOD.get(), "Stripped Olive Wood");
    add(ModBlocks.OLIVE_PLANKS.get(), "Olive Planks");
    add(ModBlocks.OLIVE_STAIRS.get(), "Olive Stairs");
    add(ModBlocks.OLIVE_SLAB.get(), "Olive Slab");
    add(ModBlocks.OLIVE_FENCE.get(), "Olive Fence");
    add(ModBlocks.OLIVE_FENCE_GATE.get(), "Olive Fence Gate");
    add(ModBlocks.OLIVE_BUTTON.get(), "Olive Button");
    add(ModBlocks.OLIVE_PRESSURE_PLATE.get(), "Olive Pressure Plate");
    add(ModBlocks.OLIVE_DOOR.get(), "Olive Door");
    add(ModBlocks.OLIVE_TRAPDOOR.get(), "Olive Trapdoor");
    add(ModBlocks.OLIVE_MOSAIC.get(), "Olive Mosaic");
    add(ModBlocks.OLIVE_MOSAIC_STAIRS.get(), "Olive Mosaic Stairs");
    add(ModBlocks.OLIVE_MOSAIC_SLAB.get(), "Olive Mosaic Slab");

    /*     deco stone     */
    add(ModBlocks.SMOOTH_STONE_PILLAR.get(), "Smooth Stone Pillar");
    add(ModBlocks.CALCITE_BRICKS.get(), "Calcite Bricks");
    add(ModBlocks.CALCITE_BRICK_STAIRS.get(), "Calcite Brick Stairs");
    add(ModBlocks.CALCITE_BRICK_SLAB.get(), "Calcite Brick Slab");
    add(ModBlocks.CALCITE_BRICK_WALL.get(), "Calcite Brick Wall");
    add(ModBlocks.TUFF_TILES.get(), "Tuff Tiles");
    add(ModBlocks.TUFF_TILE_STAIRS.get(), "Tuff Tile Stairs");
    add(ModBlocks.TUFF_TILE_SLAB.get(), "Tuff Tile Slab");
    add(ModBlocks.TUFF_TILE_WALL.get(), "Tuff Tile Wall");
    /*     industry     */
    add(ModBlocks.CRUSHING_TUB.get(), "Crushing Tub");
    add(ModBlocks.EVAPORATING_BASIN.get(), "Evaporating Basin");
    /*     metallurgy     */
    add(ModBlocks.GOLD_CHAIN.get(), "Gold Chain");
    add(ModBlocks.COPPER_CHAIN.get(), "Copper Chain");
    add(ModBlocks.IRON_POST.get(), "Iron Post");
    add(ModBlocks.WOODEN_POST.get(), "Wooden Post");
    add(ModBlocks.CAST_IRON_BLOCK.get(), "Wrought Tile");
    add(ModBlocks.CAST_IRON_TILES.get(), "Cut Wrought Tile");
    add(ModBlocks.CAST_IRON_TILE_STAIRS.get(), "Cut Wrought Tile Stairs");
    add(ModBlocks.CAST_IRON_TILE_SLAB.get(), "Cut Wrought Tile Slab");
    /*     ceramic vessels     */

    add(ModBlocks.CERAMIC_VESSEL.get(), "Ceramic Vessel");
    for(String name: ModBlocks.vessels){
      add(ModBlocks.VESSEL_MAP.get(name).get(), capitalizeFully(name) + " Ceramic Vessel");
    }


    //------------------------------- creative mode tabs -------------------------------//
    add("itemGroup." + ModTabRegistry.AGRICULTURE.getRecipeFolderName(), "Homespun Agriculture");
    add("itemGroup." + ModTabRegistry.DECORATION.getRecipeFolderName(), "Homespun Decoration");
    add("itemGroup." + ModTabRegistry.INDUSTRY.getRecipeFolderName(), "Homespun Industry");

    //------------------------------------ tooltips ------------------------------------//
    add("tooltip.homespun.crushing_tub", "§8§oPut stuff in then jump on it");
    add("tooltip.homespun.evaporating_basin", "§8§oA Magma Block underneath speeds it up");

    //------------------------------------- fluids -------------------------------------//
    add("fluid_type.homespun.ironberry_juice", "Ironberry Juice");

    //-------------------------------------- jei --------------------------------------//
    add("jei.homespun.crushing_tub_recipe", "Tub Crushing");
    add("jei.homespun.evaporating_recipe", "Evaporating");
  }
}
