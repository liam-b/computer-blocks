package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.Grid;
import computerblocks.player.*;

public class DelayBlock extends DirectionalBlock {
  public DelayBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.DELAY;
    this.color = Color.DELAY;
    this.chargeColor = Color.DELAY_CHARGE;
    this.markerColor = Color.SOURCE;
  }

  public void update(Grid grid, Block updater, Display display, Player player) {
    inputs = new ArrayList<Block>();
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
