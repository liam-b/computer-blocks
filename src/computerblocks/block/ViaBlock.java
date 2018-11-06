package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.Grid;
import computerblocks.player.Player;

public class ViaBlock extends Block {
  public Color coreColor;

  public ViaBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.VIA;
    this.color = Color.VIA;
    this.chargeColor = Color.VIA_CHARGE;
    this.coreColor = Color.SOURCE;
  }

  public void draw(Display display, Player player) {
    double rectSize = (double)BLOCK_SIZE * player.zoom;
    double x = player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom;
    double y = player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom;

    if (withinScreenBounds(display, rectSize, x, y)) {
      if (selected) highlightBlock(display, player, rectSize, x, y);
      Color drawColor = (charge) ? chargeColor : color;
      display.color((ghost) ? new Color(drawColor, 0.5f) : drawColor);
      display.rect(x, y, rectSize, rectSize);
      drawCore(rectSize, x, y, display);
    }
  }

  void drawCore(double rectSize, double x, double y, Display display) {
    double coreSize = rectSize / 6.0;

    display.color(coreColor);
    display.rect(x + rectSize / 2.0 - coreSize / 2.0, y + rectSize / 2.0 - coreSize / 2.0, coreSize, coreSize);
  }

  public ArrayList<Block> update(Grid grid, Block updater, Display display, Player player) {
    inputs = new ArrayList<Block>();
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
    ArrayList<Block> removeQueue = new ArrayList<Block>();

    for (int l = 0; l < grid.layers; l++) {
      Block block = grid.blockAt(position.x, position.y, l);

      if (block != null && block.type == BlockType.VIA && block.position.l != position.l) surroundingBlocks.add(block);
    }

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

    charge = inputs.size() != 0;
    // updateSurroundingBlocks(grid, surroundingBlocks, display, player);
    return surroundingBlocks;
  }
}