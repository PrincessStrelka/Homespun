package caittastic.homespun.datagen.loot;

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Collections;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

  public ModBlockLootTables(HolderLookup.Provider provider) {
    super(Collections.emptySet(), FeatureFlags.VANILLA_SET, provider);
  }

  //------------------------------------- ===== -------------------------------------//
  @Override
  protected void generate(){
    /*     ironwood     */
    leafWithExtra(
            ModBlocks.IRONWOOD_LEAVES,
            ModBlocks.IRONWOOD_SAPLING,
            ModItems.IRONBERRIES,
            new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F},
            new float[]{0.30F, 0.40F, 0.68F, 0.76F, 0.64F}, registries);
    simplePottedBlock(ModBlocks.POTTED_IRONWOOD_SAPLING);
    simpleDoorTable(ModBlocks.IRONWOOD_DOOR);
    simpleDropSelf(ModBlocks.IRONWOOD_SAPLING);
    simpleDropSelf(ModBlocks.IRONWOOD_LOG);
    simpleDropSelf(ModBlocks.STRIPPED_IRONWOOD_LOG);
    simpleDropSelf(ModBlocks.IRONWOOD_WOOD);
    simpleDropSelf(ModBlocks.STRIPPED_IRONWOOD_WOOD);
    simpleDropSelf(ModBlocks.IRONWOOD_PLANKS);
    simpleDropSelf(ModBlocks.IRONWOOD_STAIRS);
    simpleSlabBlock(ModBlocks.IRONWOOD_SLAB);
    simpleDropSelf(ModBlocks.IRONWOOD_FENCE);
    simpleDropSelf(ModBlocks.IRONWOOD_FENCE_GATE);
    simpleDropSelf(ModBlocks.IRONWOOD_BUTTON);
    simpleDropSelf(ModBlocks.IRONWOOD_PRESSURE_PLATE);
    simpleDropSelf(ModBlocks.IRONWOOD_TRAPDOOR);
    simpleDropSelf(ModBlocks.IRONWOOD_MOSAIC);
    simpleDropSelf(ModBlocks.IRONWOOD_MOSAIC_STAIRS);
    simpleSlabBlock(ModBlocks.IRONWOOD_MOSAIC_SLAB);

    /*     olive     */
    leafWithExtra(
            ModBlocks.OLIVE_LEAVES,
            ModBlocks.OLIVE_SAPLING,
            ModItems.OLIVES,
            new float[]{0.08F, 0.106F, 0.132F, 0.16F},
            new float[]{0.15F, 0.20F, 0.34F, 0.38F, 0.32F},
            registries);
    simplePottedBlock(ModBlocks.POTTED_OLIVE_SAPLING);
    simpleDoorTable(ModBlocks.OLIVE_DOOR);
    simpleDropSelf(ModBlocks.OLIVE_SAPLING);
    simpleDropSelf(ModBlocks.OLIVE_LOG);
    simpleDropSelf(ModBlocks.STRIPPED_OLIVE_LOG);
    simpleDropSelf(ModBlocks.OLIVE_WOOD);
    simpleDropSelf(ModBlocks.STRIPPED_OLIVE_WOOD);
    simpleDropSelf(ModBlocks.OLIVE_PLANKS);
    simpleDropSelf(ModBlocks.OLIVE_STAIRS);
    simpleSlabBlock(ModBlocks.OLIVE_SLAB);
    simpleDropSelf(ModBlocks.OLIVE_FENCE);
    simpleDropSelf(ModBlocks.OLIVE_FENCE_GATE);
    simpleDropSelf(ModBlocks.OLIVE_BUTTON);
    simpleDropSelf(ModBlocks.OLIVE_PRESSURE_PLATE);
    simpleDropSelf(ModBlocks.OLIVE_TRAPDOOR);
    simpleDropSelf(ModBlocks.OLIVE_MOSAIC);
    simpleDropSelf(ModBlocks.OLIVE_MOSAIC_STAIRS);
    simpleSlabBlock(ModBlocks.OLIVE_MOSAIC_SLAB);

    /*     deco stone     */
    simpleDropSelf(ModBlocks.SMOOTH_STONE_PILLAR);
    simpleDropSelf(ModBlocks.CALCITE_BRICKS);
    simpleSlabBlock(ModBlocks.CALCITE_BRICK_SLAB);
    simpleDropSelf(ModBlocks.CALCITE_BRICK_STAIRS);
    simpleDropSelf(ModBlocks.CALCITE_BRICK_WALL);

    simpleDropSelf(ModBlocks.TUFF_TILES);
    simpleSlabBlock(ModBlocks.TUFF_TILE_SLAB);
    simpleDropSelf(ModBlocks.TUFF_TILE_STAIRS);
    simpleDropSelf(ModBlocks.TUFF_TILE_WALL);

    /*     industry     */
    simpleDropSelf(ModBlocks.CRUSHING_TUB);
    simpleDropSelf(ModBlocks.EVAPORATING_BASIN);

    /*     metallurgy     */
    simpleDropSelf(ModBlocks.GOLD_CHAIN);
    simpleDropSelf(ModBlocks.COPPER_CHAIN);
    simpleDropSelf(ModBlocks.IRON_POST);
    simpleDropSelf(ModBlocks.WOODEN_POST);
    simpleDropSelf(ModBlocks.CAST_IRON_BLOCK);
    simpleDropSelf(ModBlocks.CAST_IRON_TILES);
    simpleDropSelf(ModBlocks.CAST_IRON_TILE_WALL);
    simpleDropSelf(ModBlocks.CAST_IRON_TILE_STAIRS);
    simpleSlabBlock(ModBlocks.CAST_IRON_TILE_SLAB);

    /*     storage     */
    dropAir(ModBlocks.CERAMIC_VESSEL.get());
    for(String[] patternKeyword: ModBlocks.vessel_patterns){
      String name = patternKeyword[0];
      dropAir(ModBlocks.VESSEL_MAP.get(name).get());}
    dropAir(ModBlocks.FLUID_STORAGE.get());
    //simpleSlabBlock(ModBlocks.CABINET);

  }

  //------------------------------------- methods -------------------------------------//
  private void dropAir(Block block){
    this.dropOther(block, Items.AIR);
  }

  private void simpleSlabBlock(DeferredBlock<?> slabBlock){

    this.add(slabBlock.get(), createSlabItemTable(slabBlock.get()));
  }



  private void simpleDropSelf(DeferredBlock<?> self){
    this.dropSelf(self.get());
  }

  private void simplePottedBlock(DeferredBlock<?> pottedBlock){
    this.dropPottedContents(pottedBlock.get());
  }

  private void simpleDoorTable(DeferredBlock<?> simpleDoor){
    this.add(simpleDoor.get(), createDoorTable(simpleDoor.get()));
  }

  @Override
  protected Iterable<Block> getKnownBlocks(){
    return ModBlocks.BLOCKS.getEntries().stream().map(x -> x.getDelegate().value())::iterator;
  }

  private void leafWithExtra(DeferredBlock<?> leafBlock, DeferredBlock<?> saplingBlock, DeferredItem<?> extraItem, float[] saplingFortuneChances, float[] extraFortuneChances, HolderLookup.Provider lookup){
    this.add(leafBlock.get(),
            (block) -> createLeavesDrops(
                    block,
                    saplingBlock.get(),
                    saplingFortuneChances)
                    .withPool(LootPool
                            .lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).or(HAS_SHEARS.or(this.hasSilkTouch()).invert()).invert())
                            .add(applyExplosionCondition(block, LootItem.lootTableItem(extraItem.get()))
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                            lookup.holderOrThrow(Enchantments.FORTUNE),
                                            extraFortuneChances)))));
  }
}
