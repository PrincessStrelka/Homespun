package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTags extends BlockTagsProvider {

    public ModBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Homespun.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        //------------------------------------- mining tool -------------------------------------//
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.IRONWOOD_LEAVES.get());
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
                .add(ModBlocks.IRONWOOD_TRAPDOOR.get());
        //------------------------------------- block tags -------------------------------------//
        tag(TagInit.Blocks.IRONWOOD_LOGS)
                .add(ModBlocks.IRONWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get());
        tag(TagInit.Blocks.IRONWOOD_WOOD)
                .add(ModBlocks.IRONWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.IRONWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get());
        
        tag(BlockTags.PLANKS)
                .add(ModBlocks.IRONWOOD_PLANKS.get());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.IRONWOOD_LEAVES.get());

        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.IRONWOOD_STAIRS.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.IRONWOOD_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.IRONWOOD_FENCE_GATE.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.IRONWOOD_BUTTON.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.IRONWOOD_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.IRONWOOD_DOOR.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.IRONWOOD_TRAPDOOR.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.IRONWOOD_TRAPDOOR.get());

        tag(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_IRONWOOD_SAPLING.get());
    }

    @Override
    public String getName() {
        return "Rustic Tags";
    }
    //------------------------------------- ===== -------------------------------------//
}