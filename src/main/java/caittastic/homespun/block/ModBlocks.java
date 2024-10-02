package caittastic.homespun.block;

import caittastic.homespun.Homespun;
import caittastic.homespun.datagen.ModWorldGenProvider;
import caittastic.homespun.item.ModItems;
import caittastic.homespun.world.feature.ModConfiguredFeatures;
import caittastic.homespun.world.feature.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static caittastic.homespun.Homespun.MOD_ID;

public class ModBlocks{
  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID); // Create a Deferred Register to hold Blocks registered under the MOD_ID namespace
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

  public static final DeferredBlock<Block> IRONWOOD_SAPLING = registerBlockAndItem("ironwood_sapling", () -> new SaplingBlock(ModTreeGrowers.IRONWOOD, commonSapling));
  public static final DeferredBlock<Block> POTTED_IRONWOOD_SAPLING = BLOCKS.register("potted_ironwood_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, IRONWOOD_SAPLING, commonFlowerPot));
  public static final DeferredBlock<Block> IRONWOOD_LEAVES = registerBlockAndItem("ironwood_leaves", () -> new LeavesBlock(commonLeaves));
  public static final DeferredBlock<Block> IRONWOOD_LOG = registerBlockAndItem("ironwood_log", () -> new RotatedPillarBlock(commonLog.mapColor((s) -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.RAW_IRON : MapColor.TERRACOTTA_BLACK)){
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility ItemAbility, boolean simulate){if(ItemAbility == ItemAbilities.AXE_STRIP) return STRIPPED_IRONWOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, ItemAbility, simulate);}});
  public static final DeferredBlock<Block> STRIPPED_IRONWOOD_LOG = registerBlockAndItem("stripped_ironwood_log", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.RAW_IRON)));
  public static final DeferredBlock<Block> IRONWOOD_WOOD = registerBlockAndItem("ironwood_wood", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.TERRACOTTA_BLACK)){
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility ItemAbility, boolean simulate){if(ItemAbility == ItemAbilities.AXE_STRIP) return STRIPPED_IRONWOOD_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, ItemAbility, simulate);}});
  public static final DeferredBlock<Block> STRIPPED_IRONWOOD_WOOD = registerBlockAndItem("stripped_ironwood_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(STRIPPED_IRONWOOD_LOG.get())));
  public static final DeferredBlock<Block> IRONWOOD_PLANKS = registerBlockAndItem("ironwood_planks", () -> new Block(commonPlank.mapColor(MapColor.RAW_IRON)));
  public static final DeferredBlock<Block> IRONWOOD_STAIRS = registerBlockAndItem("ironwood_stairs", () -> new StairBlock(IRONWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));
  public static final DeferredBlock<Block> IRONWOOD_SLAB = registerBlockAndItem("ironwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));
  public static final DeferredBlock<Block> IRONWOOD_FENCE = registerBlockAndItem("ironwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));
  public static final DeferredBlock<Block> IRONWOOD_FENCE_GATE = registerBlockAndItem("ironwood_fence_gate", () -> new FenceGateBlock(IRONWOOD_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get()).forceSolidOn()));
  public static final DeferredBlock<Block> IRONWOOD_DOOR = registerBlockAndItem("ironwood_door", () -> new DoorBlock(IRONWOOD_BLOCK_SET, BlockBehaviour.Properties.of().strength(3.0F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noOcclusion().mapColor(MapColor.RAW_IRON)));
  public static final DeferredBlock<Block> IRONWOOD_TRAPDOOR = registerBlockAndItem("ironwood_trapdoor", () -> new TrapDoorBlock(IRONWOOD_BLOCK_SET, BlockBehaviour.Properties.of().strength(3.0F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noOcclusion().isValidSpawn((s, g, p, e) -> false).mapColor(MapColor.RAW_IRON)));
  public static final DeferredBlock<Block> IRONWOOD_BUTTON = registerBlockAndItem("ironwood_button", () -> new ButtonBlock(IRONWOOD_BLOCK_SET, 30, BlockBehaviour.Properties.of().strength(0.5F).pushReaction(PushReaction.DESTROY).noCollission()));
  public static final DeferredBlock<Block> IRONWOOD_PRESSURE_PLATE = registerBlockAndItem("ironwood_pressure_plate", () -> new PressurePlateBlock(IRONWOOD_BLOCK_SET, BlockBehaviour.Properties.of().strength(0.5F).instrument(NoteBlockInstrument.BASS).noCollission().forceSolidOn().mapColor(MapColor.RAW_IRON)));
  public static final DeferredBlock<Block> IRONWOOD_MOSAIC = registerBlockAndItem("ironwood_mosaic", () -> new Block(BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));
  public static final DeferredBlock<Block> IRONWOOD_MOSAIC_STAIRS = registerBlockAndItem("ironwood_mosaic_stairs", () -> new StairBlock(IRONWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));
  public static final DeferredBlock<Block> IRONWOOD_MOSAIC_SLAB = registerBlockAndItem("ironwood_mosaic_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));

  public static final DeferredBlock<Block> OLIVE_SAPLING = registerBlockAndItem("olive_sapling", () -> new SaplingBlock(ModTreeGrowers.OLIVE, commonSapling));
  public static final DeferredBlock<Block> POTTED_OLIVE_SAPLING = BLOCKS.register("potted_olive_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, OLIVE_SAPLING, commonFlowerPot));
  public static final DeferredBlock<Block> OLIVE_LEAVES = registerBlockAndItem("olive_leaves", () -> new LeavesBlock(commonLeaves.ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 30;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 60;}});
  public static final DeferredBlock<Block> OLIVE_LOG = registerBlockAndItem("olive_log", () -> new RotatedPillarBlock(commonLog.mapColor((s) -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_YELLOW : MapColor.TERRACOTTA_BLACK).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility ItemAbility, boolean simulate){if(ItemAbility == ItemAbilities.AXE_STRIP) return STRIPPED_OLIVE_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, ItemAbility, simulate);}});
  public static final DeferredBlock<Block> STRIPPED_OLIVE_LOG = registerBlockAndItem("stripped_olive_log", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.COLOR_YELLOW).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}});
  public static final DeferredBlock<Block> OLIVE_WOOD = registerBlockAndItem("olive_wood", () -> new RotatedPillarBlock(commonLog.mapColor(MapColor.TERRACOTTA_BLACK).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility ItemAbility, boolean simulate){if(ItemAbility == ItemAbilities.AXE_STRIP) return STRIPPED_OLIVE_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));return super.getToolModifiedState(state, context, ItemAbility, simulate);}});
  public static final DeferredBlock<Block> STRIPPED_OLIVE_WOOD = registerBlockAndItem("stripped_olive_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(STRIPPED_OLIVE_LOG.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}});
  public static final DeferredBlock<Block> OLIVE_PLANKS = registerBlockAndItem("olive_planks", () -> new Block(commonPlank.mapColor(MapColor.COLOR_YELLOW).ignitedByLava()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final DeferredBlock<Block> OLIVE_STAIRS = registerBlockAndItem("olive_stairs", () -> new StairBlock(OLIVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final DeferredBlock<Block> OLIVE_SLAB = registerBlockAndItem("olive_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final DeferredBlock<Block> OLIVE_FENCE = registerBlockAndItem("olive_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final DeferredBlock<Block> OLIVE_FENCE_GATE = registerBlockAndItem("olive_fence_gate", () -> new FenceGateBlock(OLIVE_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(OLIVE_PLANKS.get()).forceSolidOn()){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final DeferredBlock<Block> OLIVE_DOOR = registerBlockAndItem("olive_door", () -> new DoorBlock(OLIVE_BLOCK_SET, commonWoodenButton.mapColor(MapColor.COLOR_YELLOW).pushReaction(PushReaction.DESTROY).ignitedByLava()));
  public static final DeferredBlock<Block> OLIVE_TRAPDOOR = registerBlockAndItem("olive_trapdoor", () -> new TrapDoorBlock(OLIVE_BLOCK_SET, BlockBehaviour.Properties.of().strength(3.0F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noOcclusion().isValidSpawn((s, g, p, e) -> false).mapColor(MapColor.COLOR_YELLOW).ignitedByLava()));
  public static final DeferredBlock<Block> OLIVE_BUTTON = registerBlockAndItem("olive_button", () -> new ButtonBlock(OLIVE_BLOCK_SET,30,  BlockBehaviour.Properties.of().strength(0.5F).noCollission()));
  public static final DeferredBlock<Block> OLIVE_PRESSURE_PLATE = registerBlockAndItem("olive_pressure_plate", () -> new PressurePlateBlock(OLIVE_BLOCK_SET, BlockBehaviour.Properties.of().strength(0.5F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noCollission().forceSolidOn().mapColor(MapColor.COLOR_YELLOW).ignitedByLava()));
  public static final DeferredBlock<Block> OLIVE_MOSAIC = registerBlockAndItem("olive_mosaic", () -> new Block(BlockBehaviour.Properties.ofFullCopy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final DeferredBlock<Block> OLIVE_MOSAIC_STAIRS = registerBlockAndItem("olive_mosaic_stairs", () -> new StairBlock(OLIVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});
  public static final DeferredBlock<Block> OLIVE_MOSAIC_SLAB = registerBlockAndItem("olive_mosaic_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(OLIVE_PLANKS.get())){
    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 5;}
    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction){return 20;}});

  /*deco stone*/
  public static final DeferredBlock<Block> SMOOTH_STONE_PILLAR = registerBlockAndItem("smooth_stone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)));
  public static final DeferredBlock<Block> CALCITE_BRICKS = registerBlockAndItem("calcite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CALCITE)));
  public static final DeferredBlock<Block> CALCITE_BRICK_STAIRS = registerBlockAndItem("calcite_brick_stairs", () -> new StairBlock(CALCITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS.get())));
  public static final DeferredBlock<Block> CALCITE_BRICK_SLAB = registerBlockAndItem("calcite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS.get())));
  public static final DeferredBlock<Block> CALCITE_BRICK_WALL = registerBlockAndItem("calcite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE_BRICKS.get())));
  public static final DeferredBlock<Block> TUFF_TILES = registerBlockAndItem("tuff_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)));
  public static final DeferredBlock<Block> TUFF_TILE_STAIRS = registerBlockAndItem("tuff_tile_stairs", () -> new StairBlock(TUFF_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(TUFF_TILES.get())));
  public static final DeferredBlock<Block> TUFF_TILE_SLAB = registerBlockAndItem("tuff_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(TUFF_TILES.get())));
  public static final DeferredBlock<Block> TUFF_TILE_WALL = registerBlockAndItem("tuff_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(TUFF_TILES.get())));

  /*industry*/
  public static final DeferredBlock<Block> CRUSHING_TUB = registerBlockAndItem("crushing_tub", () -> new CrushingTubBlock(BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));
  public static final DeferredBlock<Block> EVAPORATING_BASIN = registerBlockAndItem("evaporating_basin", () -> new EvaporatingBasinBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));

  /*metalurgy*/
  public static final DeferredBlock<Block> GOLD_CHAIN = registerBlockAndItem("gold_chain", () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));
  public static final DeferredBlock<Block> COPPER_CHAIN = registerBlockAndItem("copper_chain", () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));
  public static final DeferredBlock<Block> IRON_POST = registerBlockAndItem("iron_post", () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD).noOcclusion()));
  public static final DeferredBlock<Block> WOODEN_POST = registerBlockAndItem("wooden_post", () -> new PostBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).noOcclusion()));
  public static final DeferredBlock<Block> CAST_IRON_BLOCK = registerBlockAndItem("cast_iron_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER)));
  public static final DeferredBlock<Block> CAST_IRON_TILES = registerBlockAndItem("cast_iron_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(CAST_IRON_BLOCK.get())));
  public static final DeferredBlock<Block> CAST_IRON_TILE_WALL = registerBlockAndItem("cast_iron_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CAST_IRON_TILES.get())));
  public static final DeferredBlock<Block> CAST_IRON_TILE_STAIRS = registerBlockAndItem("cast_iron_tile_stairs", () -> new StairBlock(CAST_IRON_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CAST_IRON_TILES.get())));
  public static final DeferredBlock<Block> CAST_IRON_TILE_SLAB = registerBlockAndItem("cast_iron_tiles_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CAST_IRON_TILES.get())));

  /*ceramic vessels*/
  public static final DeferredBlock<Block> CERAMIC_VESSEL = registerBlockAndItem("ceramic_vessel", () -> new VesselBlock(null, BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
  public static final Map<String, DeferredBlock<Block>> VESSEL_MAP = new HashMap<>();
  public static final String[][] vessel_patterns = {
          {"ashen", "non binary"}, //nb
          {"cerulean", "pansexual"}, //pan
          {"ivory", "transgender"}, //trans
          {"roseate", "lesbian"}, //lesbian
          {"verdant", "agender aromantic"}, //agender/aromantic can work for both
          {"violaceous", "lgbt"} //lgbt
  };
  /*other storage*/
  public static final DeferredBlock<Block> FLUID_STORAGE = registerBlockAndItem("fluid_storage", () -> new FluidStorageBlock(BlockBehaviour.Properties.ofFullCopy(IRONWOOD_PLANKS.get())));

  static{
    for(String[] patternKeyword: vessel_patterns){
      String pattern = patternKeyword[0];
      VESSEL_MAP.put(pattern, registerBlockAndItem(pattern + "_ceramic_vessel", () -> new VesselBlock(pattern, BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA))));
    }
  }

  //------------------------------------- utility -------------------------------------//
  private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> block){
    DeferredBlock<T> toReturn = BLOCKS.register(name, block);
    ModItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
    return toReturn;
  }

}
