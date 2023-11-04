package caittastic.homespun.recipes;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class SimpleContainerWithTank extends SimpleContainer{
  private final FluidTank fluidTank;
  private final ItemStack itemStack;

  public SimpleContainerWithTank(FluidTank fluidTank, ItemStack itemStack){
    this.fluidTank = fluidTank;
    this.itemStack = itemStack;
  }

  public FluidTank getTank(){
    return this.fluidTank;
  }

  public ItemStack getStack(){
    return this.itemStack;
  }
}
