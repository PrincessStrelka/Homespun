package caittastic.homespun.block;

import caittastic.homespun.ModTabRegistry;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.world.feature.tree.IronwoodTreeGrower;
import caittastic.homespun.world.feature.tree.OliveTreeGrower;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModBlocks{
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID); // Create a Deferred Register to hold Blocks registered under the MOD_ID namespace
  //------------------------------------- blocks -------------------------------------//
  /*     ironwood     */
  public static final RegistryObject<Block> IRONWOOD_SAPLING = registerBlockAndItem("ironwood_sapling", () -> new IronwoodSaplingBlock(new IronwoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModTabRegistry.AGRICULTURE);
  public static final RegistryObject<Block> POTTED_IRONWOOD_SAPLING = BLOCKS.register("potted_ironwood_sapling", () -> new FlowerPotBlock(IRONWOOD_SAPLING.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
  public static final RegistryObject<Block> IRONWOOD_LEAVES = registerBlockAndItem("ironwood_leaves", () -> new FlamableLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModTabRegistry.AGRICULTURE);
  public static final RegistryObject<Block> IRONWOOD_LOG = registerBlockAndItem("ironwood_log", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModTabRegistry.AGRICULTURE);
  public static final RegistryObject<Block> STRIPPED_IRONWOOD_LOG = registerBlockAndItem("stripped_ironwood_log", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_WOOD = registerBlockAndItem("ironwood_wood", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> STRIPPED_IRONWOOD_WOOD = registerBlockAndItem("stripped_ironwood_wood", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_PLANKS = registerBlockAndItem("ironwood_planks", () -> new FlammableWoodBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_STAIRS = registerBlockAndItem("ironwood_stairs", () -> new StairBlock(() -> ModBlocks.IRONWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_SLAB = registerBlockAndItem("ironwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_FENCE = registerBlockAndItem("ironwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_FENCE_GATE = registerBlockAndItem("ironwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_BUTTON = registerBlockAndItem("ironwood_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_PRESSURE_PLATE = registerBlockAndItem("ironwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_DOOR = registerBlockAndItem("ironwood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> IRONWOOD_TRAPDOOR = registerBlockAndItem("ironwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), ModTabRegistry.DECORATION);

  /*     olive     */
  public static final RegistryObject<Block> OLIVE_SAPLING = registerBlockAndItem("olive_sapling", () -> new OliveSaplingBlock(new OliveTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModTabRegistry.AGRICULTURE);
  public static final RegistryObject<Block> POTTED_OLIVE_SAPLING = BLOCKS.register("potted_olive_sapling", () -> new FlowerPotBlock(OLIVE_SAPLING.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
  public static final RegistryObject<Block> OLIVE_LEAVES = registerBlockAndItem("olive_leaves", () -> new FlamableLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModTabRegistry.AGRICULTURE);
  public static final RegistryObject<Block> OLIVE_LOG = registerBlockAndItem("olive_log", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModTabRegistry.AGRICULTURE);
  public static final RegistryObject<Block> STRIPPED_OLIVE_LOG = registerBlockAndItem("stripped_olive_log", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_WOOD = registerBlockAndItem("olive_wood", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> STRIPPED_OLIVE_WOOD = registerBlockAndItem("stripped_olive_wood", () -> new FlammableLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_PLANKS = registerBlockAndItem("olive_planks", () -> new FlammableWoodBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_STAIRS = registerBlockAndItem("olive_stairs", () -> new StairBlock(() -> ModBlocks.OLIVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_SLAB = registerBlockAndItem("olive_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_FENCE = registerBlockAndItem("olive_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_FENCE_GATE = registerBlockAndItem("olive_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_BUTTON = registerBlockAndItem("olive_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_PRESSURE_PLATE = registerBlockAndItem("olive_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_DOOR = registerBlockAndItem("olive_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> OLIVE_TRAPDOOR = registerBlockAndItem("olive_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), ModTabRegistry.DECORATION);

  /*     chain     */
  public static final RegistryObject<Block> GOLD_CHAIN = registerBlockAndItem("gold_chain", () ->
          new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> COPPER_CHAIN = registerBlockAndItem("copper_chain", () ->
          new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)), ModTabRegistry.DECORATION);

  /*     clay wall     */
  // public static BlockBase CLAY_WALL;
  // public static BlockBase CLAY_WALL_CROSS;
  // public static BlockClayWallDiag CLAY_WALL_DIAG;
  //  CLAY_WALL = ((BlockBase) new BlockBase(Material.CLAY, "clay_wall").setHardness(1F)).setBlockSoundType(SoundType.GROUND);
  //  CLAY_WALL_CROSS = ((BlockBase) new BlockBase(Material.CLAY, "clay_wall_cross").setHardness(1F)).setBlockSoundType(SoundType.GROUND);
  //  CLAY_WALL_DIAG = (BlockClayWallDiag) new BlockClayWallDiag().setHardness(1F);

  /*     lanterns     */
  // public static BlockWoodLantern LANTERN_WOOD;
  //LANTERN_WOOD = new BlockWoodLantern();
  // public static BlockLantern IRON_LANTERN;
  // public static BlockLantern GOLDEN_LANTERN;
  // public static BlockLantern SILVER_LANTERN;
  //IRON_LANTERN = new BlockLantern(Material.IRON, "iron_lantern");
  //GOLDEN_LANTERN = new BlockLantern(Material.IRON, "golden_lantern");
  //SILVER_LANTERN = new BlockLantern(Material.IRON, "silver_lantern");

  /*     decoration     */
  // public static BlockLattice IRON_LATTICE;
  //IRON_LATTICE = new BlockLattice(Material.IRON, "iron_lattice");
  // public static BlockGargoyle GARGOYLE;
  //GARGOYLE = new BlockGargoyle();

  /*     farming     */
  // public static BlockFertileSoil FERTILE_SOIL;
  //FERTILE_SOIL = new BlockFertileSoil();
  // public static BlockCropStake CROP_STAKE;
  //CROP_STAKE = new BlockCropStake();
  // public static BlockRope ROPE;
  // public static BlockStakeTied STAKE_TIED;
  //ROPE = new BlockRope("rope");
  //    STAKE_TIED = new BlockStakeTied();

  /*     storage     */
  // public static BlockCabinet CABINET;
  //CABINET = new BlockCabinet();
  // public static BlockLiquidBarrel LIQUID_BARREL;
  // public static BlockBarrel BARREL;
  // public static BlockVase VASE;
  //  VASE = new BlockVase();
  //  BARREL = new BlockBarrel();
  //LIQUID_BARREL = new BlockLiquidBarrel();

  /*     bees     */
  // public static BlockBeehive BEEHIVE;
  // BEEHIVE = new BlockBeehive();
  // public static BlockApiary APIARY;
  //		APIARY = new BlockApiary();

  /*     crops     */
    /*
    public static BlockStakeCrop TOMATO_CROP;
    TOMATO_CROP = new BlockStakeCrop("tomato_crop") {
        @Override
        public Item getCrop() {
            return ModItems.TOMATO;
        }

        @Override
        public Item getSeed() {
            return ModItems.TOMATO_SEEDS;
        }
    };

    public static BlockStakeCrop CHILI_CROP;
    CHILI_CROP = new BlockStakeCrop("chili_crop") {
        @Override
        public Item getCrop() {
            return ModItems.CHILI_PEPPER;
        }

        @Override
        public Item getSeed() {
            return ModItems.CHILI_PEPPER_SEEDS;
        }

        @Override
        public int getMaxHeight() {
            return 2;
        }
    };

    public static BlockBerryBush WILDBERRY_BUSH;
    WILDBERRY_BUSH = new BlockBerryBush("wildberry_bush") {
        @Override
        public Item getBerries() {
            return ModItems.WILDBERRIES;
        }
    };
    */

  /*     apple     */
  // public static BlockAppleSeeds APPLE_SEEDS;
  // public static BlockSaplingApple APPLE_SAPLING;
  // public static BlockLeavesApple APPLE_LEAVES;
  //APPLE_SEEDS = new BlockAppleSeeds();
  //APPLE_SAPLING = new BlockSaplingApple();
  //APPLE_LEAVES = new BlockLeavesApple();

  /*     industry     */
  // public static BlockBrewingBarrel BREWING_BARREL;
  // public static BlockCondenser CONDENSER;
  // public static BlockRetort RETORT;
  // public static BlockCondenserAdvanced CONDENSER_ADVANCED;
  // public static BlockRetort RETORT_ADVANCED;
  // public static BlockCrushingTub CRUSHING_TUB;
  // public static BlockEvaporatingBasin EVAPORATING_BASIN;
  //CRUSHING_TUB = new BlockCrushingTub();
  //    EVAPORATING_BASIN = new BlockEvaporatingBasin();
  //    CONDENSER = new BlockCondenser();
  //    RETORT = new BlockRetort("retort");
  //    CONDENSER_ADVANCED = new BlockCondenserAdvanced();
  //    RETORT_ADVANCED = new BlockRetort("retort_advanced");
  //BREWING_BARREL = new BlockBrewingBarrel();

  /*     grape     */
  // public static BlockGrapeStem GRAPE_STEM;
  // public static BlockGrapeLeaves GRAPE_LEAVES;
  //GRAPE_STEM = new BlockGrapeStem();
  //GRAPE_LEAVES = new BlockGrapeLeaves();

  /*     slate     */
  // public static BlockBase SLATE;
  // public static BlockBase SLATE_ROOF;
  // public static BlockBase SLATE_TILE;
  // public static BlockBase SLATE_BRICK;
  // public static BlockBase SLATE_CHISELED;
  // public static BlockStairsBase SLATE_ROOF_STAIRS;
  // public static BlockDoubleSlabBase SLATE_ROOF_DOUBLESLAB;
  // public static BlockSlabBase SLATE_ROOF_SLAB;
  // public static ItemBlockSlabBase SLATE_ROOF_SLAB_ITEM;
  // public static BlockDoubleSlabBase SLATE_BRICK_DOUBLESLAB;
  // public static BlockSlabBase SLATE_BRICK_SLAB;
  // public static ItemBlockSlabBase SLATE_BRICK_SLAB_ITEM;
  // public static BlockStairsBase SLATE_BRICK_STAIRS;
  //  SLATE = (BlockBase) new BlockBase(Material.ROCK, "slate").setHardness(2.0F);
  //  SLATE_ROOF = (BlockBase) new BlockBase(Material.ROCK, "slate_roof").setHardness(2.0F);
  //  SLATE_TILE = (BlockBase) new BlockBase(Material.ROCK, "slate_tile").setHardness(2.0F);
  //  SLATE_BRICK = (BlockBase) new BlockBase(Material.ROCK, "slate_brick").setHardness(2.0F);
  //  SLATE_CHISELED = (BlockBase) new BlockBase(Material.ROCK, "slate_chiseled").setHardness(2.0F);
  //  SLATE_ROOF_STAIRS = new BlockStairsBase(SLATE_ROOF.getDefaultState(), "stairs_slate_roof");
  //  SLATE_ROOF_DOUBLESLAB = new BlockDoubleSlabBase(Material.ROCK, "slate_roof_doubleslab");
  //  SLATE_ROOF_SLAB = new BlockSlabBase(Material.ROCK, "slate_roof_slab", SLATE_ROOF_DOUBLESLAB);
  //      SLATE_ROOF_DOUBLESLAB.setSlab(SLATE_ROOF_SLAB);
  //  SLATE_ROOF_SLAB_ITEM = new ItemBlockSlabBase(SLATE_ROOF_SLAB, SLATE_ROOF_DOUBLESLAB);
  //  SLATE_BRICK_STAIRS = new BlockStairsBase(SLATE_BRICK.getDefaultState(), "stairs_slate_brick");
  //  SLATE_BRICK_DOUBLESLAB = new BlockDoubleSlabBase(Material.ROCK, "slate_brick_doubleslab");
  //  SLATE_BRICK_SLAB = new BlockSlabBase(Material.ROCK, "slate_brick_slab", SLATE_BRICK_DOUBLESLAB);
  //      SLATE_BRICK_DOUBLESLAB.setSlab(SLATE_BRICK_SLAB);
  //  SLATE_BRICK_SLAB_ITEM = new ItemBlockSlabBase(SLATE_BRICK_SLAB, SLATE_BRICK_DOUBLESLAB);

  /*     pillar     */
  // public static final BlockPillar STONE_PILLAR;
  //STONE_PILLAR = new BlockPillar("stone");
  // public static BlockPillar ANDESITE_PILLAR;
  //ANDESITE_PILLAR = new BlockPillar("andesite");
  // public static BlockPillar DIORITE_PILLAR;
  //DIORITE_PILLAR = new BlockPillar("diorite");
  // public static BlockPillar GRANITE_PILLAR;
  //GRANITE_PILLAR = new BlockPillar("granite");
  // public static BlockPillar SLATE_PILLAR;
  //SLATE_PILLAR = new BlockPillar("slate");

  /*     candle     */
  // public static BlockCandle CANDLE;
  //CANDLE = new BlockCandle();
  // public static BlockCandle CANDLE_LEVER;
  //CANDLE_LEVER = new BlockCandleLever();
  // public static BlockCandle CANDLE_GOLD;
  // public static BlockCandle CANDLE_LEVER_GOLD;
  // public static BlockCandle CANDLE_SILVER;
  // public static BlockCandle CANDLE_LEVER_SILVER;
  //  CANDLE_GOLD = new BlockCandle("candle_gold");
  //  CANDLE_SILVER = new BlockCandle("candle_silver");
  //  CANDLE_LEVER_GOLD = new BlockCandleLever("candle_lever_gold");
  //  CANDLE_LEVER_SILVER = new BlockCandleLever("candle_lever_silver");

  /*     chandelier     */
  // public static BlockChandelier CHANDELIER;
  //CHANDELIER = new BlockChandelier();
  // public static BlockChandelier CHANDELIER_GOLD;
  // public static BlockChandelier CHANDELIER_SILVER;
  //  CHANDELIER_GOLD = new BlockChandelier("chandelier_gold");
  //  CHANDELIER_SILVER = new BlockChandelier("chandelier_silver");

  /*     painted wood     */
  // public static BlockBase PAINTED_WOOD_WHITE;
  // public static BlockBase PAINTED_WOOD_ORANGE;
  // public static BlockBase PAINTED_WOOD_MAGENTA;
  // public static BlockBase PAINTED_WOOD_LIGHT_BLUE;
  // public static BlockBase PAINTED_WOOD_YELLOW;
  // public static BlockBase PAINTED_WOOD_LIME;
  // public static BlockBase PAINTED_WOOD_PINK;
  // public static BlockBase PAINTED_WOOD_GRAY;
  // public static BlockBase PAINTED_WOOD_SILVER;
  // public static BlockBase PAINTED_WOOD_CYAN;
  // public static BlockBase PAINTED_WOOD_PURPLE;
  // public static BlockBase PAINTED_WOOD_BLUE;
  // public static BlockBase PAINTED_WOOD_BROWN;
  // public static BlockBase PAINTED_WOOD_GREEN;
  // public static BlockBase PAINTED_WOOD_RED;
  // public static BlockBase PAINTED_WOOD_BLACK;
  //PAINTED_WOOD_WHITE = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_white").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_ORANGE = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_orange").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_MAGENTA = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_magenta").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_LIGHT_BLUE = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_light_blue").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_YELLOW = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_yellow").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_LIME = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_lime").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_PINK = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_pink").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_GRAY = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_gray").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_SILVER = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_silver").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_CYAN = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_cyan").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_PURPLE = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_purple").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_BLUE = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_blue").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_BROWN = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_brown").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_GREEN = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_green").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_RED = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_red").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //PAINTED_WOOD_BLACK = ((BlockBase) new BlockBase(Material.WOOD, "painted_wood_black").setHardness(2.0F)).setBlockSoundType(SoundType.WOOD);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_WHITE, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_ORANGE, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_MAGENTA, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_LIGHT_BLUE, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_YELLOW, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_LIME, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_PINK, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_GRAY, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_SILVER, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_CYAN, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_PURPLE, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_BLUE, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_BROWN, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_GREEN, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_RED, 5, 20);
  //    Blocks.FIRE.setFireInfo(PAINTED_WOOD_BLACK, 5, 20);

  /*     chair     */
  // public static BlockChair CHAIR_OAK;
  // public static BlockChair CHAIR_BIG_OAK;
  // public static BlockChair CHAIR_BIRCH;
  // public static BlockChair CHAIR_SPRUCE;
  // public static BlockChair CHAIR_ACACIA;
  // public static BlockChair CHAIR_JUNGLE;
  // public static BlockChair CHAIR_OLIVE;
  // public static BlockChair CHAIR_IRONWOOD;
  //    CHAIR_OAK = new BlockChair("oak");
  //    CHAIR_BIG_OAK = new BlockChair("big_oak");
  //    CHAIR_BIRCH = new BlockChair("birch");
  //    CHAIR_SPRUCE = new BlockChair("spruce");
  //    CHAIR_ACACIA = new BlockChair("acacia");
  //    CHAIR_JUNGLE = new BlockChair("jungle");
  //    CHAIR_OLIVE = new BlockChair("olive");
  //    CHAIR_IRONWOOD = new BlockChair("ironwood");

  /*     table     */
  // public static BlockTable TABLE_OLIVE;
  // public static BlockTable TABLE_IRONWOOD;
  // public static BlockTable TABLE_OAK;
  // public static BlockTable TABLE_BIG_OAK;
  // public static BlockTable TABLE_BIRCH;
  // public static BlockTable TABLE_SPRUCE;
  // public static BlockTable TABLE_ACACIA;
  // public static BlockTable TABLE_JUNGLE;
  //    TABLE_OAK = new BlockTable("oak");
  //    TABLE_BIG_OAK = new BlockTable("big_oak");
  //    TABLE_BIRCH = new BlockTable("birch");
  //    TABLE_SPRUCE = new BlockTable("spruce");
  //    TABLE_ACACIA = new BlockTable("acacia");
  //    TABLE_JUNGLE = new BlockTable("jungle");
  //    TABLE_OLIVE = new BlockTable("olive");
  //    TABLE_IRONWOOD = new BlockTable("ironwood");

  //------------------------------------- utility -------------------------------------//
  private static <T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block, CreativeModeTab tab){
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn, tab);
    return toReturn;
  }

  private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
    return ModItems.ITEM.register(name, () -> new BlockItem(block.get(),
            new Item.Properties().tab(tab)));
  }

}
