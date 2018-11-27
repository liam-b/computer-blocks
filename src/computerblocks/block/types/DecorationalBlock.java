package computerblocks.block.types;

import java.util.ArrayList;

import computerblocks.*;
import computerblocks.block.*;
import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class DecorationalBlock extends Block {
  public DecorationalBlock(BlockPosition position) {
    super(position);
  }

  public ArrayList<Block> update(Grid grid, Block updater, Display display, Player player) {
    return new ArrayList<Block>();
  }
}