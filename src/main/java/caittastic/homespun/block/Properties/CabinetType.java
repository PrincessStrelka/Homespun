package caittastic.homespun.block.Properties;

import net.minecraft.util.StringRepresentable;

public enum CabinetType implements StringRepresentable{
  SINGLE("single"),
  TOP("top"),
  BOTTOM("bottom");

  public static final CabinetType[] BY_ID = values();
  private final String name;

  CabinetType(String pName){
    this.name = pName;
  }

  public String getSerializedName(){
    return this.name;
  }
}