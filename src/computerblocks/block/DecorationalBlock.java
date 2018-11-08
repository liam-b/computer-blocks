package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;
import computerblocks.Grid;

public class DecorationalBlock extends Block {
  public Color markerColor;

  public DecorationalBlock(BlockPosition position) {
    super(position);
  }

  public void draw(Display display, Player player) {
    double rectSize = (double)BLOCK_SIZE * player.zoom;
    double x = player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom;
    double y = player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom;

    if (withinScreenBounds(display, rectSize, x, y)) {
      if (selected) highlightBlock(display, player, rectSize, x, y);
      Color drawColor = color;
      display.color((ghost) ? new Color(drawColor, 0.5f) : drawColor);
      display.rect(x, y, rectSize, rectSize);
    }
  }

  public ArrayList<Block> update(Grid grid, Block updater, Display display, Player player) {
    return new ArrayList<Block>();
  }
}
