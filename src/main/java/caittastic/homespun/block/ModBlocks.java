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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModBlocks{
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID); // Create a Deferred Register to hold Blocks registered under the MOD_ID namespace
  //------------------------------------- blocks -------------------------------------//
  /*     ironwood     */
  public static final RegistryObject<Block> IRONWOOD_SAPLING = registerBlockAndItem("ironwood_sapling", () -> new SaplingBlock(new IronwoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModTabRegistry.AGRICULTURE);
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
  public static final RegistryObject<Block> OLIVE_SAPLING = registerBlockAndItem("olive_sapling", () -> new SaplingBlock(new OliveTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModTabRegistry.AGRICULTURE);
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
  public static final RegistryObject<Block> GOLD_CHAIN = registerBlockAndItem("gold_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> COPPER_CHAIN = registerBlockAndItem("copper_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)), ModTabRegistry.DECORATION);

  /*     deco stone     */
  public static final RegistryObject<Block> SMOOTH_STONE_PILLAR = registerBlockAndItem("smooth_stone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE)), ModTabRegistry.DECORATION);

  public static final RegistryObject<Block> CALCITE_BRICKS = registerBlockAndItem("calcite_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CALCITE)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> CALCITE_BRICK_STAIRS = registerBlockAndItem("calcite_brick_stairs", () -> new StairBlock(() -> ModBlocks.CALCITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CALCITE_BRICKS.get())), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> CALCITE_BRICK_SLAB = registerBlockAndItem("calcite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CALCITE_BRICKS.get())), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> CALCITE_BRICK_WALL = registerBlockAndItem("calcite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CALCITE_BRICKS.get())), ModTabRegistry.DECORATION);

  public static final RegistryObject<Block> TUFF_TILES = registerBlockAndItem("tuff_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> TUFF_TILE_STAIRS = registerBlockAndItem("tuff_tile_stairs", () -> new StairBlock(() -> ModBlocks.TUFF_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(TUFF_TILES.get())), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> TUFF_TILE_SLAB = registerBlockAndItem("tuff_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(TUFF_TILES.get())), ModTabRegistry.DECORATION);
  public static final RegistryObject<Block> TUFF_TILE_WALL = registerBlockAndItem("tuff_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(TUFF_TILES.get())), ModTabRegistry.DECORATION);
  /*     industry     */
  public static final RegistryObject<Block> CRUSHING_TUB = registerBlockAndItem("crushing_tub", () -> new CrushingTubBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModTabRegistry.INDUSTRY);
  //CONDENSER = new BlockCondenser();
  //RETORT = new BlockRetort("retort");
  //CONDENSER_ADVANCED = new BlockCondenserAdvanced();
  //RETORT_ADVANCED = new BlockRetort("retort_advanced");
  //BREWING_BARREL = new BlockBrewingBarrel();

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
  // public static BlockLiquidBarrel LIQUID_BARREL;
  // public static BlockBarrel BARREL;
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

  /*     grape     */
  //GRAPE_STEM = new BlockGrapeStem();
  //GRAPE_LEAVES = new BlockGrapeLeaves();

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
