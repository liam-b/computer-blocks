package computerblocks.block.types;

import java.util.ArrayList;

import computerblocks.*;
import computerblocks.block.*;
import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class DelayBlock extends FunctionalBlock {
  public boolean tickCharge = false;

  public DelayBlock(BlockPosition position) {
    super(position);
  }

  public ArrayList<Block> update(Grid grid, Block updater, Display display, Player player) {
    inputs = new ArrayList<Block>();
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
    ArrayList<Block> removeQueue = new ArrayList<Block>();

    for (Block block : surroundingBlocks) {
      boolean onlyItemInSurroundingBlockInputs = block.inputs.size() == 1 && block.inputs.get(0) == this;
      if (block.type.isDirectional()) {
        if (block.position.isFacing(position)) {
          if (block.charge) inputs.add(block);
          removeQueue.add(block);
        }
      }
      else if (block.charge && !onlyItemInSurroundingBlockInputs) inputs.add(block);
    }
    surroundingBlocks.remove(updater);
    surroundingBlocks.removeAll(removeQueue);

    tickCharge = inputs.size() != 0;
    return new ArrayList<Block>();
  }

  public boolean tick() {
    if (tickCharge != charge) {
      charge = tickCharge;
      return true;
    }
    return false;
  }
}