package caittastic.homespun.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class ContainerBlockItem extends BlockItem{
  public ContainerBlockItem(Block pBlock, Properties pProperties){
    super(pBlock, pProperties);
  }

  @Override
  public boolean canFitInsideContainerItems(){
    return false;
  }
}
