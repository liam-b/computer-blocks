package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.Grid;
import computerblocks.player.*;

public class SourceBlock extends Block {
  public SourceBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.SOURCE;
    this.color = Color.SOURCE;
    this.chargeColor = Color.SOURCE;
  }

  public void update(Grid grid, Block updater, Display display, Player player) {
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
    surroundingBlocks.remove(updater);
    charge = true;

    updateSurroundingBlocks(grid, surroundingBlocks, display, player);
  }
}