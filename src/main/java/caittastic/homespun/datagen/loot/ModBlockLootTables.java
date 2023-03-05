package caittastic.homespun.datagen.loot;

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot{
  private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
  private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
  private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
  private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

  //------------------------------------- ===== -------------------------------------//
  @Override
  protected void addTables(){
    /*     ironwood     */
    leafWithExtra(
            ModBlocks.IRONWOOD_LEAVES,
            ModBlocks.IRONWOOD_SAPLING,
            ModItems.IRONBERRIES,
            new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F},
            new float[]{0.15F, 0.20F, 0.34F, 0.38F, 0.32F});
    simplePottedBlock(ModBlocks.POTTED_IRONWOOD_SAPLING);
    simpleDoorTable(ModBlocks.IRONWOOD_DOOR);
    simpleDropSelf(ModBlocks.IRONWOOD_SAPLING);
    simpleDropSelf(ModBlocks.IRONWOOD_LOG);
    simpleDropSelf(ModBlocks.STRIPPED_IRONWOOD_LOG);
    simpleDropSelf(ModBlocks.IRONWOOD_WOOD);
    simpleDropSelf(ModBlocks.STRIPPED_IRONWOOD_WOOD);
    simpleDropSelf(ModBlocks.IRONWOOD_PLANKS);
    simpleDropSelf(ModBlocks.IRONWOOD_STAIRS);
    simpleDropSelf(ModBlocks.IRONWOOD_SLAB);
    simpleDropSelf(ModBlocks.IRONWOOD_FENCE);
    simpleDropSelf(ModBlocks.IRONWOOD_FENCE_GATE);
    simpleDropSelf(ModBlocks.IRONWOOD_BUTTON);
    simpleDropSelf(ModBlocks.IRONWOOD_PRESSURE_PLATE);
    simpleDropSelf(ModBlocks.IRONWOOD_TRAPDOOR);
    /*     olive     */
    leafWithExtra(
            ModBlocks.OLIVE_LEAVES,
            ModBlocks.OLIVE_SAPLING,
            ModItems.OLIVES,
            new float[]{0.08F, 0.106F, 0.132F, 0.16F},
            new float[]{0.15F, 0.20F, 0.34F, 0.38F, 0.32F});
    simplePottedBlock(ModBlocks.POTTED_OLIVE_SAPLING);
    simpleDoorTable(ModBlocks.OLIVE_DOOR);
    simpleDropSelf(ModBlocks.OLIVE_SAPLING);
    simpleDropSelf(ModBlocks.OLIVE_LOG);
    simpleDropSelf(ModBlocks.STRIPPED_OLIVE_LOG);
    simpleDropSelf(ModBlocks.OLIVE_WOOD);
    simpleDropSelf(ModBlocks.STRIPPED_OLIVE_WOOD);
    simpleDropSelf(ModBlocks.OLIVE_PLANKS);
    simpleDropSelf(ModBlocks.OLIVE_STAIRS);
    simpleDropSelf(ModBlocks.OLIVE_SLAB);
    simpleDropSelf(ModBlocks.OLIVE_FENCE);
    simpleDropSelf(ModBlocks.OLIVE_FENCE_GATE);
    simpleDropSelf(ModBlocks.OLIVE_BUTTON);
    simpleDropSelf(ModBlocks.OLIVE_PRESSURE_PLATE);
    simpleDropSelf(ModBlocks.OLIVE_TRAPDOOR);
    /*     chain     */
    simpleDropSelf(ModBlocks.GOLD_CHAIN);
    simpleDropSelf(ModBlocks.COPPER_CHAIN);
    /*     deco stone     */
    simpleDropSelf(ModBlocks.SMOOTH_STONE_PILLAR);
    simpleDropSelf(ModBlocks.CALCITE_BRICKS);
    simpleDropSelf(ModBlocks.CALCITE_BRICK_SLAB);
    simpleDropSelf(ModBlocks.CALCITE_BRICK_STAIRS);
    simpleDropSelf(ModBlocks.CALCITE_BRICK_WALL);
    simpleDropSelf(ModBlocks.TUFF_TILES);
    simpleDropSelf(ModBlocks.TUFF_TILE_SLAB);
    simpleDropSelf(ModBlocks.TUFF_TILE_STAIRS);
    simpleDropSelf(ModBlocks.TUFF_TILE_WALL);
    /*     industry     */
    simpleDropSelf(ModBlocks.CRUSHING_TUB);
    simpleDropSelf(ModBlocks.EVAPORATING_BASIN);
  }

  //------------------------------------- methods -------------------------------------//
  private void simpleDropSelf(RegistryObject<Block> self){
    this.dropSelf(self.get());
  }

  private void simplePottedBlock(RegistryObject<Block> pottedBlock){
    this.dropPottedContents(pottedBlock.get());
  }

  private void simpleDoorTable(RegistryObject<Block> simpleDoor){
    this.add(simpleDoor.get(), BlockLoot::createDoorTable);
  }

  @Override
  protected Iterable<Block> getKnownBlocks(){
    return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
  }

  private void leafWithExtra(RegistryObject<Block> leafBlock, RegistryObject<Block> saplingBlock, RegistryObject<Item> extraItem, float[] saplingFortuneChances, float[] extraFortuneChances){
    this.add(leafBlock.get(),
            (block) -> createLeavesDrops(
                    block,
                    saplingBlock.get(),
                    saplingFortuneChances)
                    .withPool(LootPool
                            .lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                            .add(applyExplosionCondition(block, LootItem.lootTableItem(extraItem.get()))
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                            Enchantments.BLOCK_FORTUNE,
                                            extraFortuneChances)))));
  }
}
