package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.player.*;
import computerblocks.Grid;

public class BlockUpdate {
  public ArrayList<Block> blocks;
  public Block updater;

  public BlockUpdate(ArrayList<Block> blocks, Block updater) {
    this.blocks = blocks;
    this.updater = updater;
  }

  public ArrayList<BlockUpdate> update(Grid grid, Display display, Player player) {
    ArrayList<BlockUpdate> updates = new ArrayList<BlockUpdate>();
    for (Block block : blocks) {
      if (block != null) {
        updates.add(new BlockUpdate(block.update(grid, updater, display, player), block));
      }
    }

    return updates;
  }
}