package computerblocks.block;

import computerblocks.display.Color;
import computerblocks.position.*;

public class CableBlock extends Block {
  public CableBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.CABLE;
    this.color = new Color("#b4b4b4");
    this.chargeColor = new Color("#dbd44e");
  }
}