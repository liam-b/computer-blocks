package computerblocks.block.types;

import java.util.ArrayList;

import computerblocks.*;
import computerblocks.block.*;
import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class EmptyBlock extends Block {
  public EmptyBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.EMPTY;
    this.color = Color.EMPTY;
    this.chargeColor = Color.EMPTY;
  }

  public ArrayList<Block> update(Grid grid, Block updater, Display display, Player player) {
    return new ArrayList<Block>();
  }
}