package computerblocks.block;

import computerblocks.display.Color;
import computerblocks.position.*;

public class InverterBlock extends DirectionalBlock {
  public InverterBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.INVERTER;
    this.color = new Color("#b4b4b4");
    this.chargeColor = new Color("#dbd44e");
  }
}
