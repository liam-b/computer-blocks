package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.Grid;

public class DelayBlock extends DirectionalBlock {
  public DelayBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.DELAY;
    this.color = new Color("#59c664");
    this.chargeColor = new Color("#62db6e");
    this.markerColor = new Color("#f2e24f");
  }

  public void update(Grid grid, Block updater) {
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
    ArrayList<Block> removeQueue = new ArrayList<Block>();

    for (Block block : surroundingBlocks) {
      if (block.type.isDirectional()) {
        if (block.position.isFacing(position) && block.charge) inputs.add(block);
      }
      else if (block.charge && !position.isFacing(block.position)) inputs.add(block);
      if (!position.isFacing(block.position)) removeQueue.add(block);
    }
    surroundingBlocks.remove(updater);
    surroundingBlocks.removeAll(removeQueue);

    tickCharge = inputs.size() != 0;
  }

  public boolean tick() {
    if (tickCharge != charge) {
      charge = tickCharge;
      return true;
    }
    return false;
  }
}
