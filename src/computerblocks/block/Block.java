// package computerblocks.block;
//
// import java.util.ArrayList;
//
// import computerblocks.display.*;
// import computerblocks.position.*;
// import computerblocks.player.*;
// import computerblocks.Grid;
//
// public class Block {
//   public static final double BLOCK_RATIO = 10.0;
//   public static final double BLOCK_SPACING = 1.0;
//   public static final double BLOCK_OFFSET = BLOCK_RATIO / 2.0;
//   public static final double BLOCK_SIZE = BLOCK_RATIO - BLOCK_SPACING;
//   public static final double BLOCK_HIGHLIGHT_OFFSET = 1.0;
//
//   public BlockType type;
//   public Color color;
//   public Color chargeColor;
//   public boolean highlighted = false;
//   public boolean ghost = false;
//
//   public boolean charge = false;
//   public boolean lastCharge = false;
//   public boolean tickCharge = false;
//
//   public BlockPosition position;
//   public ArrayList<Block> inputs = new ArrayList<Block>();
//
//   public Block(BlockPosition position) {
//     this.position = position;
//   }
//
//   public void draw(Display display, Player player) {
//     double rectSize = (double)BLOCK_SIZE * player.zoom;
//     double x = player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom;
//     double y = player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom;
//
//     if (withinScreenBounds(display, rectSize, x, y)) {
//       if (highlighted) highlightBlock(display, player, rectSize, x, y);
//       Color drawColor = (charge) ? chargeColor : color;
//       display.color((ghost) ? new Color(drawColor, 0.5f) : drawColor);
//       display.rect(x, y, rectSize, rectSize);
//     }
//   }
//
//   public ArrayList<Block> getSurroundingBlocks(Grid grid) {
//     ArrayList<Block> blocks = new ArrayList<Block>();
//     putBlock(blocks, grid, position.x + 1, position.y, position.l);
//     putBlock(blocks, grid, position.x - 1, position.y, position.l);
//     putBlock(blocks, grid, position.x, position.y + 1, position.l);
//     putBlock(blocks, grid, position.x, position.y - 1, position.l);
//     return blocks;
//   }
//
//   private void putBlock(ArrayList<Block> blocks, Grid grid, int x, int y, int l) {
//     Block block = grid.blockAt(x, y, l);
//     if (block != null) {
//       blocks.add(block);
//     }
//   }
//
//   public void highlightBlock(Display display, Player player, double size, double x, double y) {
//     double highlightOffset = BLOCK_HIGHLIGHT_OFFSET * player.zoom;
//     display.color(Color.HIGHLIGHT);
//     display.rect(
//       x - highlightOffset / 2.0,
//       y - highlightOffset / 2.0,
//       size + highlightOffset,
//       size + highlightOffset
//     );
//   }
//
//   public boolean withinScreenBounds(Display display, double size, double x, double y) {
//     return
//       x > 0 - size &&
//       x < display.width + size / 2.0 &&
//       y > 0 - size &&
//       y < display.height + size / 2.0;
//   }
//
//   public boolean mouseOver(Player player) {
//     return
//       player.mouse.position.x > player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom &&
//       player.mouse.position.x < player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom + (double)BLOCK_SIZE * player.zoom &&
//       player.mouse.position.y > player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom &&
//       player.mouse.position.y < player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom + (double)BLOCK_SIZE * player.zoom;
//   }
// }
