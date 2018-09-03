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
    this.color = new Color("#589ec9");
    this.chargeColor = new Color("#75bdea");
    this.coreColor = new Color("#f2e24f");
  }

  public void draw(Display display, Player player) {
    float rectSize = (float)BLOCK_SIZE * player.zoom;
    RealPosition drawPosition = new RealPosition(
      player.translate.x + (float)BLOCK_RATIO * (float)position.x * player.zoom,
      player.translate.y + (float)BLOCK_RATIO * (float)position.y * player.zoom
    );

    if (withinScreenBounds(display, rectSize, drawPosition)) {
      if (selected) highlightBlock(display, player, rectSize, drawPosition);
      display.color((charge) ? chargeColor : color);
      display.rect((int)drawPosition.x, (int)drawPosition.y, (int)rectSize, (int)rectSize);
      drawCore(rectSize, drawPosition, display);
    }
  }

  void drawCore(float rectSize, RealPosition drawPosition, Display display) {
    float coreSize = rectSize / 5f;

    display.color(coreColor);
    display.rect(drawPosition.x + rectSize / 2f - coreSize / 2f, drawPosition.y + rectSize / 2f - coreSize / 2f, coreSize, coreSize);
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