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
  public ArrayList<BlockPosition> saveInputPositions = new ArrayList<BlockPosition>();
  // public ArrayList<Path> paths = new ArrayList<Path>();

  public boolean charge = false;
  public boolean lastCharge = false;
  public boolean tickCharge = false;

  public Block(BlockPosition position) {
    this.position = position;
  }

  public static Block fromType(BlockType type, BlockPosition position) {
    Block block = new CableBlock(position);
    if (type == BlockType.SOURCE) block = new SourceBlock(position);
    if (type == BlockType.INVERTER) block = new InverterBlock(position);
    if (type == BlockType.VIA) block = new ViaBlock(position);
    if (type == BlockType.DELAY) block = new DelayBlock(position);
    return block;
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

    charge = inputs.size() != 0;

    // selected = true;
    // display.fing();
    // draw(display, player);
    // display.draw();
    //
    // try { Thread.sleep(100); }
    // catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    //
    // selected = false;
    //
    // return surroundingBlocks;
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

  public void highlightBlock(Display display, Player player, double size, RealPosition position) {
    double highlightOffset = BLOCK_HIGHLIGHT_OFFSET * player.zoom;
    display.color(new Color("#31c831"));
    display.rect(
      (int)(position.x - highlightOffset / 2f),
      (int)(position.y - highlightOffset / 2f),
      (int)(size + highlightOffset),
      (int)(size + highlightOffset)
    );
  }

  public boolean withinScreenBounds(Display display, double size, RealPosition position) {
    return
      (int)position.x > 0 - (int)size &&
      (int)position.x < display.width + (int)size / 2 &&
      (int)position.y > 0 - (int)size &&
      (int)position.y < display.height + (int)size / 2;
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
