package caittastic.homespun;

import caittastic.homespun.block.ModBlocks;
import caittastic.homespun.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabRegistry{
  //register the agriculture tab
  public static final CreativeModeTab AGRICULTURE =
          new CreativeModeTab("agriculture_tab"){
            @Override
            public ItemStack makeIcon(){
              return new ItemStack(ModItems.IRONBERRIES.get());
            }
          };

  //register the decoration tab
  public static final CreativeModeTab DECORATION =
          new CreativeModeTab("decoration_tab"){
    @Override
    public ItemStack makeIcon(){
      return new ItemStack(ModBlocks.IRONWOOD_PLANKS.get());
    }
  };

  //register the industry tab
  public static final CreativeModeTab INDUSTRY =
          new CreativeModeTab("production_tab"){
    @Override
    public ItemStack makeIcon(){return new ItemStack(ModItems.TINY_IRON_DUST.get());}
  };
}
