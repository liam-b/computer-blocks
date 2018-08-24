package computerblocks.block;

public enum BlockType {
  EMPTY, CABLE, SOURCE, INVERTER, VIA, DELAY;

  public boolean isDirectional() {
    return this == INVERTER || this == DELAY;
  }
};