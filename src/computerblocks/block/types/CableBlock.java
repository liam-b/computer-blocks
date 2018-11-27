package computerblocks.block.types;

import computerblocks.*;
import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class CableBlock extends FunctionalBlock {
  public CableBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.CABLE;
    this.color = Color.CABLE;
    this.chargeColor = Color.CABLE_CHARGE;
  }
}
