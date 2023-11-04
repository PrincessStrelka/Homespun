package caittastic.homespun.block;

import caittastic.homespun.item.ModItems;
import caittastic.homespun.world.feature.tree.IronwoodTreeGrower;
import caittastic.homespun.world.feature.tree.OliveTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModBlocks{
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID); // Create a Deferred Register to hold Blocks registered under the MOD_ID namespace
  //------------------------------------- blocks -------------------------------------//
  /*ironwood & olive*/
  public static final BlockBehaviour.Properties commonFragile = BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).instabreak();
  public static final BlockBehaviour.Properties commonSapling = commonFragile.sound(SoundType.GRASS).noCollission().randomTicks().mapColor(MapColor.PLANT);
  public static final BlockBehaviour.Properties commonFlowerPot = commonFragile.noOcclusion();
  public static final BlockBehaviour.Properties commonLeaves = BlockBehaviour.Properties.of().strength(0.2F).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().isValidSpawn((s, g, p, e) -> e == EntityType.OCELOT || e == EntityType.PARROT).isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false).isRedstoneConductor((s, g, p) -> false).mapColor(MapColor.PLANT);
  public static final BlockBehaviour.Properties commonLog = BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS);
  public static final BlockBehaviour.Properties commonPlank = BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS);
  public static final BlockBehaviour.Properties commonWoodenButton = BlockBehaviour.Properties.of().strength(3.0F).instrument(NoteBlockInstrument.BASS).noOcclusion();

  public static final BlockSetType IRONWOOD_BLOCK_SET = BlockSetType.register(new BlockSetType("ironwood"));
  public static final BlockSetType OLIVE_BLOCK_SET = BlockSetType.register(new BlockSetType("olive"));
  private static final WoodType IRONWOOD_WOOD_TYPE = new WoodType("ironwood", IRONWOOD_BLOCK_SET, SoundType.WOOD, SoundType.HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
  private static final WoodType OLIVE_WOOD_TYPE = new WoodType("ironwood", OLIVE_BLOCK_SET, SoundType.WOOD, SoundType.HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);

  public static final RegistryObject<Block> IRONWOOD_SAPLING = registerBlockAndItem("ironwood_sapling", () -> new SaplingBlock(new IronwoodTreeGrower(), commonSapling));
  public static final RegistryObject<Block> POTTED_IRONWOOD_SAPLING = BLOCKS.register("potted_ironwood_sapling", () -> new FlowerPotBlock(IRONWOOD_SAPLING.get(), commonFlowerPot));
  public static final RegistryObject<Block> IRONWOOD_LEAVES = registerBlockAndItem("ironwood_leaves", () -> new LeavesBlock(commonLeaves));
  public static final RegistryObject<Block> IRONWOOD_LOG = registerBlockAndItem("ironwood_log", () -> new RotatedPillarBlock(commonLog.mapColor((s) -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.RAW_IRON : MapColor.TERRACOTTA_BLACK)){
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate){if(toolAction == ToolActions.AXE_STRIP) return STRIPPED_IRONWOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, toolAction, simulate);}});
  public static final RegistryObject<Block> STRIPPED_IRONWOOD_LOG = registerBlockAndItem("stripped_ironwood_log", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.RAW_IRON)));
  public static final RegistryObject<Block> IRONWOOD_WOOD = registerBlockAndItem("ironwood_wood", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.TERRACOTTA_BLACK)){
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate){if(toolAction == ToolActions.AXE_STRIP) return STRIPPED_IRONWOOD_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, toolAction, simulate);}});
  public static final RegistryObject<Block> STRIPPED_IRONWOOD_WOOD = registerBlockAndItem("stripped_ironwood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(STRIPPED_IRONWOOD_LOG.get())));
  public static final RegistryObject<Block> IRONWOOD_PLANKS = registerBlockAndItem("ironwood_planks", () -> new Block(commonPlank.mapColor(MapColor.RAW_IRON)));
  public static final RegistryObject<Block> IRONWOOD_STAIRS = registerBlockAndItem("ironwood_stairs", () -> new StairBlock(IRONWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));
  public static final RegistryObject<Block> IRONWOOD_SLAB = registerBlockAndItem("ironwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));
  public static final RegistryObject<Block> IRONWOOD_FENCE = registerBlockAndItem("ironwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));
  public static final RegistryObject<Block> IRONWOOD_FENCE_GATE = registerBlockAndItem("ironwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get()).forceSolidOn(), IRONWOOD_WOOD_TYPE));
  public static final RegistryObject<Block> IRONWOOD_DOOR = registerBlockAndItem("ironwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noOcclusion().mapColor(MapColor.RAW_IRON), IRONWOOD_BLOCK_SET));
  public static final RegistryObject<Block> IRONWOOD_TRAPDOOR = registerBlockAndItem("ironwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().strength(3.0F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noOcclusion().isValidSpawn((s, g, p, e) -> false).mapColor(MapColor.RAW_IRON), IRONWOOD_BLOCK_SET));
  public static final RegistryObject<Block> IRONWOOD_BUTTON = registerBlockAndItem("ironwood_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().strength(0.5F).pushReaction(PushReaction.DESTROY).noCollission(), IRONWOOD_BLOCK_SET, 30, true));
  public static final RegistryObject<Block> IRONWOOD_PRESSURE_PLATE = registerBlockAndItem("ironwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().strength(0.5F).instrument(NoteBlockInstrument.BASS).noCollission().forceSolidOn().mapColor(MapColor.RAW_IRON), IRONWOOD_BLOCK_SET));
  public static final RegistryObject<Block> IRONWOOD_MOSAIC = registerBlockAndItem("ironwood_mosaic", () -> new Block(BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));
  public static final RegistryObject<Block> IRONWOOD_MOSAIC_STAIRS = registerBlockAndItem("ironwood_mosaic_stairs", () -> new StairBlock(IRONWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));
  public static final RegistryObject<Block> IRONWOOD_MOSAIC_SLAB = registerBlockAndItem("ironwood_mosaic_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));

  public static final RegistryObject<Block> OLIVE_SAPLING = registerBlockAndItem("olive_sapling", () -> new SaplingBlock(new OliveTreeGrower(), commonSapling));
  public static final RegistryObject<Block> POTTED_OLIVE_SAPLING = BLOCKS.register("potted_olive_sapling", () -> new FlowerPotBlock(OLIVE_SAPLING.get(), commonFlowerPot));
  public static final RegistryObject<Block> OLIVE_LEAVES = registerBlockAndItem("olive_leaves", () -> new LeavesBlock(commonLeaves.ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 30;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 60;}});
  public static final RegistryObject<Block> OLIVE_LOG = registerBlockAndItem("olive_log", () -> new RotatedPillarBlock(commonLog.mapColor((s) -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_YELLOW : MapColor.TERRACOTTA_BLACK).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate){if(toolAction == ToolActions.AXE_STRIP) return STRIPPED_OLIVE_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, toolAction, simulate);}});
  public static final RegistryObject<Block> STRIPPED_OLIVE_LOG = registerBlockAndItem("stripped_olive_log", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.COLOR_YELLOW).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}});
  public static final RegistryObject<Block> OLIVE_WOOD = registerBlockAndItem("olive_wood", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.TERRACOTTA_BLACK).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate){if(toolAction == ToolActions.AXE_STRIP) return STRIPPED_OLIVE_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, toolAction, simulate);}});
  public static final RegistryObject<Block> STRIPPED_OLIVE_WOOD = registerBlockAndItem("stripped_olive_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(STRIPPED_OLIVE_LOG.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}});
  public static final RegistryObject<Block> OLIVE_PLANKS = registerBlockAndItem("olive_planks", () -> new Block(commonPlank.mapColor(MapColor.COLOR_YELLOW).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final RegistryObject<Block> OLIVE_STAIRS = registerBlockAndItem("olive_stairs", () -> new StairBlock(OLIVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final RegistryObject<Block> OLIVE_SLAB = registerBlockAndItem("olive_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final RegistryObject<Block> OLIVE_FENCE = registerBlockAndItem("olive_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final RegistryObject<Block> OLIVE_FENCE_GATE = registerBlockAndItem("olive_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(OLIVE_PLANKS.get()).forceSolidOn(), OLIVE_WOOD_TYPE){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final RegistryObject<Block> OLIVE_DOOR = registerBlockAndItem("olive_door", () -> new DoorBlock(commonWoodenButton.mapColor(MapColor.COLOR_YELLOW).pushReaction(PushReaction.DESTROY).ignitedByLava(), OLIVE_BLOCK_SET));
  public static final RegistryObject<Block> OLIVE_TRAPDOOR = registerBlockAndItem("olive_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().strength(3.0F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noOcclusion().isValidSpawn((s, g, p, e) -> false).mapColor(MapColor.COLOR_YELLOW).ignitedByLava(), OLIVE_BLOCK_SET));
  public static final RegistryObject<Block> OLIVE_BUTTON = registerBlockAndItem("olive_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().strength(0.5F).noCollission(), OLIVE_BLOCK_SET, 30, true));
  public static final RegistryObject<Block> OLIVE_PRESSURE_PLATE = registerBlockAndItem("olive_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().strength(0.5F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noCollission().forceSolidOn().mapColor(MapColor.COLOR_YELLOW).ignitedByLava(), OLIVE_BLOCK_SET));
  public static final RegistryObject<Block> OLIVE_MOSAIC = registerBlockAndItem("olive_mosaic", () -> new Block(BlockBehaviour.Properties.copy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final RegistryObject<Block> OLIVE_MOSAIC_STAIRS = registerBlockAndItem("olive_mosaic_stairs", () -> new StairBlock(OLIVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final RegistryObject<Block> OLIVE_MOSAIC_SLAB = registerBlockAndItem("olive_mosaic_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});

  /*deco stone*/
  public static final RegistryObject<Block> SMOOTH_STONE_PILLAR = registerBlockAndItem("smooth_stone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE)));
  public static final RegistryObject<Block> CALCITE_BRICKS = registerBlockAndItem("calcite_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CALCITE)));
  public static final RegistryObject<Block> CALCITE_BRICK_STAIRS = registerBlockAndItem("calcite_brick_stairs", () -> new StairBlock(() -> CALCITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CALCITE_BRICKS.get())));
  public static final RegistryObject<Block> CALCITE_BRICK_SLAB = registerBlockAndItem("calcite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CALCITE_BRICKS.get())));
  public static final RegistryObject<Block> CALCITE_BRICK_WALL = registerBlockAndItem("calcite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CALCITE_BRICKS.get())));
  public static final RegistryObject<Block> TUFF_TILES = registerBlockAndItem("tuff_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF)));
  public static final RegistryObject<Block> TUFF_TILE_STAIRS = registerBlockAndItem("tuff_tile_stairs", () -> new StairBlock(() -> TUFF_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(TUFF_TILES.get())));
  public static final RegistryObject<Block> TUFF_TILE_SLAB = registerBlockAndItem("tuff_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(TUFF_TILES.get())));
  public static final RegistryObject<Block> TUFF_TILE_WALL = registerBlockAndItem("tuff_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(TUFF_TILES.get())));

  /*industry*/
  public static final RegistryObject<Block> CRUSHING_TUB = registerBlockAndItem("crushing_tub", () -> new CrushingTubBlock(BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));
  public static final RegistryObject<Block> EVAPORATING_BASIN = registerBlockAndItem("evaporating_basin", () -> new EvaporatingBasinBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA)));

  /*metalurgy*/
  public static final RegistryObject<Block> GOLD_CHAIN = registerBlockAndItem("gold_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));
  public static final RegistryObject<Block> COPPER_CHAIN = registerBlockAndItem("copper_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));
  public static final RegistryObject<Block> IRON_POST = registerBlockAndItem("iron_post", () -> new PostBlock(BlockBehaviour.Properties.copy(Blocks.LIGHTNING_ROD).noOcclusion()));
  public static final RegistryObject<Block> WOODEN_POST = registerBlockAndItem("wooden_post", () -> new PostBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).noOcclusion()));
  public static final RegistryObject<Block> CAST_IRON_BLOCK = registerBlockAndItem("cast_iron_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
  public static final RegistryObject<Block> CAST_IRON_TILES = registerBlockAndItem("cast_iron_tiles", () -> new Block(BlockBehaviour.Properties.copy(CAST_IRON_BLOCK.get())));
  public static final RegistryObject<Block> CAST_IRON_TILE_WALL = registerBlockAndItem("cast_iron_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CAST_IRON_TILES.get())));
  public static final RegistryObject<Block> CAST_IRON_TILE_STAIRS = registerBlockAndItem("cast_iron_tile_stairs", () -> new StairBlock(() -> CAST_IRON_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(CAST_IRON_TILES.get())));
  public static final RegistryObject<Block> CAST_IRON_TILE_SLAB = registerBlockAndItem("cast_iron_tiles_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CAST_IRON_TILES.get())));

  /*ceramic vessels*/
  public static final RegistryObject<Block> CERAMIC_VESSEL = registerBlockAndItem("ceramic_vessel", () -> new VesselBlock(null, BlockBehaviour.Properties.copy(Blocks.TERRACOTTA)));
  public static final Map<String, RegistryObject<Block>> VESSEL_MAP = new HashMap<>();
  public static final String[][] vessel_patterns = {
          {"ashen", "non binary"}, //nb
          {"cerulean", "pansexual"}, //pan
          {"ivory", "transgender"}, //trans
          {"roseate", "lesbian"}, //lesbian
          {"verdant", "agender aromantic"}, //agender/aromantic can work for both
          {"violaceous", "lgbt"} //lgbt
  };
  /*other storage*/
  public static final RegistryObject<Block> FLUID_STORAGE = registerBlockAndItem("fluid_storage", () -> new FluidStorageBlock(BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())));

  static{
    for(String[] patternKeyword: vessel_patterns){
      String pattern = patternKeyword[0];
      VESSEL_MAP.put(pattern, registerBlockAndItem(pattern + "_ceramic_vessel", () -> new VesselBlock(pattern, BlockBehaviour.Properties.copy(Blocks.TERRACOTTA))));
    }
  }

  //------------------------------------- utility -------------------------------------//
  private static <T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block){
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    ModItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
    return toReturn;
  }

}
