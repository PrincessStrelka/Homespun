package caittastic.homespun.datagen;

import caittastic.homespun.Homespun;
import caittastic.homespun.TagInit;
import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class ModItemTags extends ItemTagsProvider{

  public ModItemTags(DataGenerator gen, BlockTagsProvider blockTagsProvider, ExistingFileHelper helper){
    super(gen, blockTagsProvider, Homespun.MOD_ID, helper);
  }

  @Override
  protected void addTags(){
    //------------------------------- tagging blocks -------------------------------//
    tag(TagInit.Items.IRONWOOD_LOGS)
            .add(ModBlocks.IRONWOOD_LOG.get().asItem())
            .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get().asItem());
    tag(TagInit.Items.IRONWOOD_WOOD)
            .add(ModBlocks.IRONWOOD_WOOD.get().asItem())
            .add(ModBlocks.STRIPPED_IRONWOOD_WOOD.get().asItem());
    tag(TagInit.Items.OLIVE_LOGS)
            .add(ModBlocks.OLIVE_LOG.get().asItem())
            .add(ModBlocks.STRIPPED_OLIVE_LOG.get().asItem());
    tag(TagInit.Items.OLIVE_WOOD)
            .add(ModBlocks.OLIVE_WOOD.get().asItem())
            .add(ModBlocks.STRIPPED_OLIVE_WOOD.get().asItem());
    tag(ItemTags.LOGS)
            .add(ModBlocks.IRONWOOD_LOG.get().asItem())
            .add(ModBlocks.STRIPPED_IRONWOOD_LOG.get().asItem())
            .add(ModBlocks.OLIVE_LOG.get().asItem())
            .add(ModBlocks.STRIPPED_OLIVE_LOG.get().asItem());
    tag(ItemTags.LEAVES)
            .add(ModBlocks.IRONWOOD_LEAVES.get().asItem())
            .add(ModBlocks.OLIVE_LEAVES.get().asItem());
    tag(ItemTags.PLANKS)
            .add(ModBlocks.IRONWOOD_PLANKS.get().asItem())
            .add(ModBlocks.OLIVE_PLANKS.get().asItem());
    tag(ItemTags.WOODEN_STAIRS)
            .add(ModBlocks.IRONWOOD_STAIRS.get().asItem())
            .add(ModBlocks.OLIVE_STAIRS.get().asItem());
    tag(ItemTags.WOODEN_FENCES)
            .add(ModBlocks.IRONWOOD_FENCE.get().asItem())
            .add(ModBlocks.OLIVE_FENCE.get().asItem());
    tag(Tags.Items.FENCE_GATES_WOODEN)
            .add(ModBlocks.IRONWOOD_FENCE_GATE.get().asItem())
            .add(ModBlocks.OLIVE_FENCE_GATE.get().asItem());
    tag(ItemTags.WOODEN_BUTTONS)
            .add(ModBlocks.IRONWOOD_BUTTON.get().asItem())
            .add(ModBlocks.OLIVE_BUTTON.get().asItem());
    tag(ItemTags.WOODEN_PRESSURE_PLATES)
            .add(ModBlocks.IRONWOOD_PRESSURE_PLATE.get().asItem())
            .add(ModBlocks.OLIVE_PRESSURE_PLATE.get().asItem());
    tag(ItemTags.WOODEN_DOORS)
            .add(ModBlocks.IRONWOOD_DOOR.get().asItem())
            .add(ModBlocks.OLIVE_DOOR.get().asItem());
    tag(ItemTags.WOODEN_TRAPDOORS)
            .add(ModBlocks.IRONWOOD_TRAPDOOR.get().asItem())
            .add(ModBlocks.OLIVE_TRAPDOOR.get().asItem());
    tag(ItemTags.SAPLINGS)
            .add(ModBlocks.IRONWOOD_SAPLING.get().asItem())
            .add(ModBlocks.OLIVE_SAPLING.get().asItem());
    //------------------------------- tagging items -------------------------------//
    tag(Tags.Items.DUSTS)
            .add(ModItems.TINY_IRON_DUST.get());

  }

  @Override
  public @NotNull String getName(){
    return "Homespun Tags";
  }
}

//OLIVES
//IRONBERRIES
//TOMATO
//CHILI_PEPPER
//WILDBERRIES
//GRAPES
//TOMATO_SEEDS
//CHILI_PEPPER_SEEDS
//BEE
//HONEYCOMB
//BEESWAX
//TALLOW
//TINY_IRON_DUST