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
    RealPosition drawPosition = new RealPosition(
      player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom,
      player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom
    );

    if (withinScreenBounds(display, rectSize, drawPosition)) {
      if (selected) highlightBlock(display, player, rectSize, drawPosition);
      Color drawColor = (charge) ? chargeColor : color;
      display.color((ghost) ? new Color(drawColor, 0.5f) : drawColor);
      display.rect((int)drawPosition.x, (int)drawPosition.y, (int)rectSize, (int)rectSize);
      drawCore(rectSize, drawPosition, display);
    }
  }

  void drawCore(double rectSize, RealPosition drawPosition, Display display) {
    double coreSize = rectSize / 6f;

    display.color(coreColor);
    display.rect(drawPosition.x + rectSize / 2f - coreSize / 2f, drawPosition.y + rectSize / 2f - coreSize / 2f, coreSize, coreSize);
  }

  public void update(Grid grid, Block updater, Display display, Player player) {
    inputs = new ArrayList<Block>();
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
    ArrayList<Block> removeQueue = new ArrayList<Block>();

    for (int l = 0; l < grid.layers; l++) {
      Block block = grid.blockAt(new BlockPosition(position.x, position.y, l));

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
    updateSurroundingBlocks(grid, surroundingBlocks, display, player);
  }
}