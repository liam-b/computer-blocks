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
  public static final float BLOCK_HIGHLIGHT_OFFSET = 0.5f;

  public BlockType type;
  public Color color;
  public Color chargeColor;

  public BlockPosition position;
  public ArrayList<Block> inputs;

  public boolean charge;
  public boolean lastCharge;
  public boolean selected;

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

    if (withinScreenBounds(display, rectSize, drawPosition)) {
      if (selected) highlightBlock(display, player, rectSize, drawPosition);
      display.color((charge) ? chargeColor : color);
      display.rect((int)drawPosition.x, (int)drawPosition.y, (int)rectSize, (int)rectSize);
    }
  }

  private void highlightBlock(Display display, Player player, float size, RealPosition position) {
    float highlightOffset = BLOCK_HIGHLIGHT_OFFSET * player.zoom;
    display.color(new Color("#31c831"));
    display.rect(
      (int)(position.x - highlightOffset / 2f),
      (int)(position.y - highlightOffset / 2f),
      (int)(size + highlightOffset),
      (int)(size + highlightOffset)
    );
  }

  private boolean withinScreenBounds(Display display, float size, RealPosition position) {
    return
      (int)position.x > 0 - (int)size &&
      (int)position.x < display.width + (int)size / 2 &&
      (int)position.y > 0 - (int)size &&
      (int)position.y < display.height + (int)size / 2;
  }
}