package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.Grid;

public class InverterBlock extends DirectionalBlock {
  public InverterBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.INVERTER;
    this.color = new Color("#ce4e4a");
    this.chargeColor = new Color("#f95e59");
    this.markerColor = new Color("#f2e24f");
  }

  public void update(Grid grid, Block updater) {
    inputs = new ArrayList<Block>();
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);

    for (Block block : surroundingBlocks) {
      if (block.type.isDirectional()) {
        if (block.position.isFacing(position) && block.charge) inputs.add(block);
      }
      else if (block.charge && !position.isFacing(block.position)) inputs.add(block);
    }
    surroundingBlocks.remove(updater);

    charge = !(inputs.size() != 0);
    boolean willUpdateSurroundingBlocks = charge != lastCharge;
    lastCharge = charge;
    if (willUpdateSurroundingBlocks) updateSurroundingBlocks(grid, surroundingBlocks);
  }
}
