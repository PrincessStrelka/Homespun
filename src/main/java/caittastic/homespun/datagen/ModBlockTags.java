package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTags extends BlockTagsProvider{

  public ModBlockTags(DataGenerator generator, ExistingFileHelper helper){
    super(generator, Homespun.MOD_ID, helper);
  }

  @Override
  protected void addTags(){
    //------------------------------------- mining tool -------------------------------------//
    tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.GOLD_CHAIN.get())
            .add(ModBlocks.COPPER_CHAIN.get())
            .add(ModBlocks.IRON_POST.get())

            .add(ModBlocks.SMOOTH_STONE_PILLAR.get())

            .add(ModBlocks.CALCITE_BRICKS.get())
            .add(ModBlocks.CALCITE_BRICK_SLAB.get())
            .add(ModBlocks.CALCITE_BRICK_STAIRS.get())

            .add(ModBlocks.TUFF_TILES.get())
            .add(ModBlocks.TUFF_TILE_STAIRS.get())
            .add(ModBlocks.TUFF_TILE_SLAB.get())

            .add(ModBlocks.EVAPORATING_BASIN.get());
    tag(BlockTags.MINEABLE_WITH_HOE)
            .add(ModBlocks.IRONWOOD_LEAVES.get())
            .add(ModBlocks.OLIVE_LEAVES.get());
    tag(BlockTags.MINEABLE_WITH_AXE)
            .add(ModBlocks.IRONWOOD_LOG.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get())
            .add(ModBlocks.IRONWOOD_WOOD.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get())
            .add(ModBlocks.IRONWOOD_PLANKS.get())
            .add(ModBlocks.IRONWOOD_STAIRS.get())
            .add(ModBlocks.IRONWOOD_SLAB.get())
            .add(ModBlocks.IRONWOOD_FENCE.get())
            .add(ModBlocks.IRONWOOD_FENCE_GATE.get())
            .add(ModBlocks.IRONWOOD_BUTTON.get())
            .add(ModBlocks.IRONWOOD_PRESSURE_PLATE.get())
            .add(ModBlocks.IRONWOOD_DOOR.get())
            .add(ModBlocks.IRONWOOD_TRAPDOOR.get())
            .add(ModBlocks.IRONWOOD_SAPLING.get())

            .add(ModBlocks.OLIVE_LOG.get())
            .add(ModBlocks.STRIPPED_OLIVE_LOG.get())
            .add(ModBlocks.OLIVE_WOOD.get())
            .add(ModBlocks.STRIPPED_OLIVE_WOOD.get())
            .add(ModBlocks.OLIVE_PLANKS.get())
            .add(ModBlocks.OLIVE_STAIRS.get())
            .add(ModBlocks.OLIVE_SLAB.get())
            .add(ModBlocks.OLIVE_FENCE.get())
            .add(ModBlocks.OLIVE_FENCE_GATE.get())
            .add(ModBlocks.OLIVE_BUTTON.get())
            .add(ModBlocks.OLIVE_PRESSURE_PLATE.get())
            .add(ModBlocks.OLIVE_DOOR.get())
            .add(ModBlocks.OLIVE_TRAPDOOR.get())
            .add(ModBlocks.OLIVE_SAPLING.get())

            .add(ModBlocks.CRUSHING_TUB.get())
            .add(ModBlocks.WOODEN_POST.get());
    //------------------------------------- block tags -------------------------------------//
    tag(BlockTags.SLABS)
            .add(ModBlocks.TUFF_TILE_SLAB.get())
            .add(ModBlocks.CALCITE_BRICK_SLAB.get());
    tag(BlockTags.STAIRS)
            .add(ModBlocks.TUFF_TILE_STAIRS.get())
            .add(ModBlocks.CALCITE_BRICK_STAIRS.get());
    tag(BlockTags.PARROTS_SPAWNABLE_ON)
            .add(ModBlocks.IRONWOOD_LOG.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get())
            .add(ModBlocks.IRONWOOD_WOOD.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get())
            .add(ModBlocks.OLIVE_LOG.get())
            .add(ModBlocks.STRIPPED_OLIVE_LOG.get())
            .add(ModBlocks.OLIVE_WOOD.get())
            .add(ModBlocks.STRIPPED_OLIVE_WOOD.get());

    tag(BlockTags.LOGS_THAT_BURN)
            .add(ModBlocks.IRONWOOD_LOG.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get())
            .add(ModBlocks.IRONWOOD_WOOD.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get())
            .add(ModBlocks.OLIVE_LOG.get())
            .add(ModBlocks.STRIPPED_OLIVE_LOG.get())
            .add(ModBlocks.OLIVE_WOOD.get())
            .add(ModBlocks.STRIPPED_OLIVE_WOOD.get());

    tag(BlockTags.OVERWORLD_NATURAL_LOGS)
            .add(ModBlocks.IRONWOOD_LOG.get())
            .add(ModBlocks.OLIVE_LOG.get());

    tag(BlockTags.SNAPS_GOAT_HORN)
            .add(ModBlocks.IRONWOOD_LOG.get())
            .add(ModBlocks.OLIVE_LOG.get());

    tag(TagInit.Blocks.IRONWOOD_LOGS)
            .add(ModBlocks.IRONWOOD_LOG.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get())
            .add(ModBlocks.IRONWOOD_WOOD.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get());

    tag(TagInit.Blocks.OLIVE_LOGS)
            .add(ModBlocks.OLIVE_LOG.get())
            .add(ModBlocks.STRIPPED_OLIVE_LOG.get())
            .add(ModBlocks.OLIVE_WOOD.get())
            .add(ModBlocks.STRIPPED_OLIVE_WOOD.get());

    tag(BlockTags.LOGS)
            .add(ModBlocks.IRONWOOD_LOG.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get())
            .add(ModBlocks.IRONWOOD_WOOD.get())
            .add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get())
            .add(ModBlocks.OLIVE_LOG.get())
            .add(ModBlocks.STRIPPED_OLIVE_LOG.get())
            .add(ModBlocks.OLIVE_WOOD.get())
            .add(ModBlocks.STRIPPED_OLIVE_WOOD.get());

    tag(BlockTags.PLANKS)
            .add(ModBlocks.IRONWOOD_PLANKS.get())
            .add(ModBlocks.OLIVE_PLANKS.get());

    tag(BlockTags.LEAVES)
            .add(ModBlocks.IRONWOOD_LEAVES.get())
            .add(ModBlocks.OLIVE_LEAVES.get());

    tag(BlockTags.WOODEN_STAIRS)
            .add(ModBlocks.IRONWOOD_STAIRS.get())
            .add(ModBlocks.OLIVE_STAIRS.get());

    tag(BlockTags.WOODEN_FENCES)
            .add(ModBlocks.IRONWOOD_FENCE.get())
            .add(ModBlocks.OLIVE_FENCE.get());

    tag(BlockTags.FENCE_GATES)
            .add(ModBlocks.IRONWOOD_FENCE_GATE.get())
            .add(ModBlocks.OLIVE_FENCE_GATE.get());

    tag(BlockTags.WOODEN_BUTTONS)
            .add(ModBlocks.IRONWOOD_BUTTON.get())
            .add(ModBlocks.OLIVE_BUTTON.get());

    tag(BlockTags.WOODEN_PRESSURE_PLATES)
            .add(ModBlocks.IRONWOOD_PRESSURE_PLATE.get())
            .add(ModBlocks.OLIVE_PRESSURE_PLATE.get());

    tag(BlockTags.WOODEN_DOORS)
            .add(ModBlocks.IRONWOOD_DOOR.get())
            .add(ModBlocks.OLIVE_DOOR.get());

    tag(BlockTags.WOODEN_TRAPDOORS)
            .add(ModBlocks.IRONWOOD_TRAPDOOR.get())
            .add(ModBlocks.OLIVE_TRAPDOOR.get());

    tag(BlockTags.WOODEN_SLABS)
            .add(ModBlocks.IRONWOOD_SLAB.get())
            .add(ModBlocks.OLIVE_SLAB.get());

    tag(BlockTags.LEAVES)
            .add(ModBlocks.IRONWOOD_LEAVES.get())
            .add(ModBlocks.OLIVE_LEAVES.get());

    tag(BlockTags.SAPLINGS)
            .add(ModBlocks.IRONWOOD_SAPLING.get())
            .add(ModBlocks.OLIVE_SAPLING.get());

    tag(BlockTags.FLOWER_POTS)
            .add(ModBlocks.POTTED_IRONWOOD_SAPLING.get())
            .add(ModBlocks.POTTED_OLIVE_SAPLING.get());


    tag(BlockTags.WALLS)
            .add(ModBlocks.CALCITE_BRICK_WALL.get())
            .add(ModBlocks.TUFF_TILE_WALL.get());

    tag(Tags.Blocks.FENCE_GATES_WOODEN)
            .add(ModBlocks.IRONWOOD_FENCE_GATE.get())
            .add(ModBlocks.OLIVE_FENCE_GATE.get());


  }

  @Override
  public String getName(){
    return "Rustic Tags";
  }
  //------------------------------------- ===== -------------------------------------//
}