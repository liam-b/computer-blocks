package computerblocks.block.types;

import java.util.ArrayList;

import computerblocks.*;
import computerblocks.block.*;
import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class DirectionalBlock extends FunctionalBlock {
  public Color markerColor;

  public DirectionalBlock(BlockPosition position) {
    super(position);
  }

  public void draw(Display display, Player player) {
    double rectSize = (double)BLOCK_SIZE * player.zoom;
    double x = player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom;
    double y = player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom;

    if (withinScreenBounds(display, rectSize, x, y)) {
      if (selected) highlightBlock(display, player, rectSize, x, y);
      Color drawColor = (charge) ? chargeColor : color;
      drawColor = (ghost) ? drawColor : new Color(drawColor, 128);
      display.color(drawColor);
      display.rect(x, y, rectSize, rectSize);
      drawDirectionMarker(rectSize, x, y, display);
    }
  }

  void drawDirectionMarker(double rectSize, double x, double y, Display display) {
    double markerHeight = rectSize / 12.0;
    double markerWidth = rectSize / 2.0;
    double markerOffset = rectSize / 8.0;

    display.color(markerColor);
    if (position.r == Rotation.UP) display.rect(x + markerWidth / 2.0, y + markerOffset, markerWidth, markerHeight);
    if (position.r == Rotation.RIGHT) display.rect(x + rectSize - markerOffset - markerHeight / 2.0, y + markerWidth / 2.0, markerHeight, markerWidth);
    if (position.r == Rotation.DOWN) display.rect(x + markerWidth / 2.0, y + rectSize - markerOffset - markerHeight / 2.0, markerWidth, markerHeight);
    if (position.r == Rotation.LEFT) display.rect(x + markerOffset, y + markerWidth / 2.0, markerHeight, markerWidth);
  }
}