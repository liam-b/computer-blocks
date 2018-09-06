package computerblocks.block;

import computerblocks.display.Color;
import computerblocks.position.*;

public class EmptyBlock extends Block {
  public EmptyBlock(BlockPosition position) {
    super(position);

    this.selected = false;
    this.type = BlockType.EMPTY;
    this.color = new Color("#d7d7d7");
    this.chargeColor = new Color("#d7d7d7");
  }
}