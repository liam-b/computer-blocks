package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;
import computerblocks.Grid;

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

  public void update(Grid grid, Block updater) {
    inputs = new ArrayList<Block>();
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);

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

  public ArrayList<Block> getSurroundingBlocks(Grid grid) {
    ArrayList<Block> blocks = new ArrayList<Block>();
    blocks.add(grid.blockAt(new BlockPosition(position.x + 1, position.y, position.l)));
    blocks.add(grid.blockAt(new BlockPosition(position.x - 1, position.y, position.l)));
    blocks.add(grid.blockAt(new BlockPosition(position.x, position.y + 1, position.l)));
    blocks.add(grid.blockAt(new BlockPosition(position.x, position.y - 1, position.l)));

    while (blocks.remove(null));
    return blocks;
  }

  public void updateSurroundingBlocks(Grid grid, ArrayList<Block> surroundingBlocks) {
    for (Block block : surroundingBlocks) {
      if (block != null) block.update(grid, this);
    }
  }

  public void highlightBlock(Display display, Player player, float size, RealPosition position) {
    float highlightOffset = BLOCK_HIGHLIGHT_OFFSET * player.zoom;
    display.color(new Color("#31c831"));
    display.rect(
      (int)(position.x - highlightOffset / 2f),
      (int)(position.y - highlightOffset / 2f),
      (int)(size + highlightOffset),
      (int)(size + highlightOffset)
    );
  }

  public boolean withinScreenBounds(Display display, float size, RealPosition position) {
    return
      (int)position.x > 0 - (int)size &&
      (int)position.x < display.width + (int)size / 2 &&
      (int)position.y > 0 - (int)size &&
      (int)position.y < display.height + (int)size / 2;
  }

  public boolean mouseOver(Player player) {
    return
      player.mouse.position.x > player.translate.x + (float)BLOCK_RATIO * (float)position.x * player.zoom &&
      player.mouse.position.x < player.translate.x + (float)BLOCK_RATIO * (float)position.x * player.zoom + (float)BLOCK_SIZE * player.zoom &&
      player.mouse.position.y > player.translate.y + (float)BLOCK_RATIO * (float)position.y * player.zoom &&
      player.mouse.position.y < player.translate.y + (float)BLOCK_RATIO * (float)position.y * player.zoom + (float)BLOCK_SIZE * player.zoom;
  }
}
