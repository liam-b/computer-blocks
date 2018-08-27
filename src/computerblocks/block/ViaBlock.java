package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.Color;
import computerblocks.position.*;
import computerblocks.Grid;

public class ViaBlock extends Block {
  public ViaBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.VIA;
    this.color = new Color("#589ec9");
    this.chargeColor = new Color("#75bdea");
  }

  public void update(Grid grid, Block updater) {
    inputs = new ArrayList<Block>();
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);

    for (int l = 0; l < grid.layers; l++) {
      Block block = grid.blockAt(new BlockPosition(position.x, position.y, l));
      if (block != null && block.type == BlockType.VIA && block.position.l != position.l) surroundingBlocks.add(block);
    }

    for (Block block : surroundingBlocks) {
      boolean onlyItemInSurroundingBlockInputs = block.inputs.size() == 1 && block.inputs.get(0) == this;
      if (block.type.isDirectional()) {
        if (block.position.isFacing(position) && block.charge) inputs.add(block);
      }
      else if (block.charge && !onlyItemInSurroundingBlockInputs) inputs.add(block);
    }
    surroundingBlocks.remove(updater);

    charge = inputs.size() != 0;
    updateSurroundingBlocks(grid, surroundingBlocks);
  }
}