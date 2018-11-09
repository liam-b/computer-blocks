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
  public static final double BLOCK_HIGHLIGHT_OFFSET = 1f;

  public BlockType type;
  public Color color;
  public Color chargeColor;
  public boolean selected = false;
  public boolean ghost = false;

  public BlockPosition position;
  public ArrayList<Block> inputs = new ArrayList<Block>();
  public ArrayList<BlockPosition> saveInputPositions = null;
  // public ArrayList<Path> paths = new ArrayList<Path>();

  public boolean charge = false;
  public boolean lastCharge = false;
  public boolean tickCharge = false;

  public Block(BlockPosition position) {
    this.position = position;
  }

  public static Block fromType(BlockType type, BlockPosition position) {
    switch(type) {
      case CABLE:
        return new CableBlock(position);
      case SOURCE:
        return new SourceBlock(position);
      case INVERTER:
        return new InverterBlock(position);
      case VIA:
        return new ViaBlock(position);
      case DELAY:
        return new DelayBlock(position);
    }
    return null;
  }

  public ArrayList<Block> update(Grid grid, Block updater, Display display, Player player) {
    inputs = new ArrayList<Block>();
    ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
    ArrayList<Block> removeQueue = new ArrayList<Block>();

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

    // selected = true;
    // if (position.l == player.selectedLayer) {
    //   display.fing();
    //   draw(display, player);
    //   display.draw();
    // }
    //
    // try { Thread.sleep(100); }
    // catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    //
    // selected = false;

    charge = inputs.size() != 0;
    return surroundingBlocks;
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
    }
  }

  public ArrayList<Block> getSurroundingBlocks(Grid grid) {
    ArrayList<Block> blocks = new ArrayList<Block>();
    putBlock(blocks, grid, position.x + 1, position.y, position.l);
    putBlock(blocks, grid, position.x - 1, position.y, position.l);
    putBlock(blocks, grid, position.x, position.y + 1, position.l);
    putBlock(blocks, grid, position.x, position.y - 1, position.l);
    return blocks;
  }

  private void putBlock(ArrayList<Block> blocks, Grid grid, int x, int y, int l) {
    Block block = grid.blockAt(x, y, l);
    if (block != null) {
      blocks.add(block);
    }
  }

  public void highlightBlock(Display display, Player player, double size, double x, double y) {
    double highlightOffset = BLOCK_HIGHLIGHT_OFFSET * player.zoom;
    display.color(Color.HIGHLIGHT);
    display.rect(
      x - highlightOffset / 2.0,
      y - highlightOffset / 2.0,
      size + highlightOffset,
      size + highlightOffset
    );
  }

  public boolean withinScreenBounds(Display display, double size, double x, double y) {
    return
      x > 0 - size &&
      x < display.width + size / 2.0 &&
      y > 0 - size &&
      y < display.height + size / 2.0;
  }

  public boolean mouseOver(Player player) {
    return
      player.mouse.position.x > player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom &&
      player.mouse.position.x < player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom + (double)BLOCK_SIZE * player.zoom &&
      player.mouse.position.y > player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom &&
      player.mouse.position.y < player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom + (double)BLOCK_SIZE * player.zoom;
  }

  public boolean tick() { return false; }
}
