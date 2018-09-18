package computerblocks.block;

import computerblocks.display.Color;
import computerblocks.position.*;

public class CableBlock extends Block {
  public CableBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.CABLE;
    this.color = Color.CABLE;
    this.chargeColor = Color.CABLE_CHARGE;
  }
}