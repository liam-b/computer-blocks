package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class Block {
  public static final int BLOCK_RATIO = 10;
  public static final int BLOCK_SPACING = 1;
  public static final int BLOCK_OFFSET = BLOCK_RATIO / 2;
  public static final int BLOCK_SIZE = BLOCK_RATIO - BLOCK_SPACING;

  BlockType type;
  Color color;
  Color chargeColor;

  BlockPosition position;
  ArrayList<Block> inputs;

  boolean charge;
  boolean lastCharge;
  boolean selected;

  public Block(BlockPosition position) {
    this.position = position;
    this.inputs = new ArrayList<Block>();

    this.charge = false;
    this.lastCharge = false;
    this.selected = false;
  }

  public void draw(Display display, Player player) {
    float rectSize = (float)BLOCK_SIZE * player.zoom;
    RealPosition drawPosition = new RealPosition(
      player.translate.x + (float)BLOCK_RATIO * (float)position.x * player.zoom,
      player.translate.y + (float)BLOCK_RATIO * (float)position.y * player.zoom
    );

    if (withinScreenBounds(display, (int)rectSize, (int)drawPosition.x, (int)drawPosition.y)) {
      // if (selected) highlightBlock();
      display.color((charge) ? chargeColor : color);
      display.rect((int)drawPosition.x, (int)drawPosition.y, (int)rectSize, (int)rectSize);
    }
  }

  // private void highlightBlock() {}

  private boolean withinScreenBounds(Display display, int size, int x, int y) {
    return x > 0 - size / 2 && x < display.width + size / 2 && y > 0 - size / 2 && y < display.height + size / 2;
  }
}