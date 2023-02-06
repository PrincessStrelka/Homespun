package caittastic.homespun;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagInit{
  public static class Blocks{
    public static final TagKey<Block> IRONWOOD_LOGS = CreateBlockTag("ironwood_logs");
    public static final TagKey<Block> IRONWOOD_WOOD = CreateBlockTag("ironwood_wood");
    public static final TagKey<Block> OLIVE_LOGS = CreateBlockTag("olive_logs");
    public static final TagKey<Block> OLIVE_WOOD = CreateBlockTag("olive_wood");

    private static TagKey<Block> CreateBlockTag(String PathName){
      return BlockTags.create(new ResourceLocation(Homespun.MOD_ID, PathName));
    }
  }

  public static class Items{
    public static final TagKey<Item> IRONWOOD_LOGS = CreateItemTag("ironwood_logs");
    public static final TagKey<Item> IRONWOOD_WOOD = CreateItemTag("ironwood_wood");
    public static final TagKey<Item> OLIVE_LOGS = CreateItemTag("olive_logs");
    public static final TagKey<Item> OLIVE_WOOD = CreateItemTag("olive_wood");

    private static TagKey<Item> CreateItemTag(String PathName){
      return ItemTags.create(new ResourceLocation(Homespun.MOD_ID, PathName));
    }
  }
}
