package computerblocks.block;

import computerblocks.display.Color;
import computerblocks.position.*;

public class SourceBlock extends Block {
  public SourceBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.SOURCE;
    this.color = new Color("#f2e24f");
    this.chargeColor = new Color("#f2e24f");
  }
}