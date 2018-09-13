package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.Color;
import computerblocks.position.*;
import computerblocks.Grid;

public class SourceBlock extends Block {
  public SourceBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.SOURCE;
    this.color = new Color("#f2e24f");
    this.chargeColor = new Color("#f2e24f");
  }

  public void update(Grid grid, Block updater) {
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
    surroundingBlocks.remove(updater);
    charge = true;

    updateSurroundingBlocks(grid, surroundingBlocks);
  }
}