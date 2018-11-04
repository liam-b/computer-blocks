package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class DirectionalBlock extends Block {
  public Color markerColor;

  public DirectionalBlock(BlockPosition position) {
    super(position);
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
      display.rect(drawPosition.x, drawPosition.y, rectSize, rectSize);
      drawDirectionMarker(rectSize, drawPosition, display);
    }
  }

  void drawDirectionMarker(double rectSize, RealPosition drawPosition, Display display) {
    double markerHeight = rectSize / 12.0;
    double markerWidth = rectSize / 2.0;
    double markerOffset = rectSize / 8.0;

    display.color(markerColor);
    if (position.r == Rotation.UP) display.rect(drawPosition.x + markerWidth / 2.0, drawPosition.y + markerOffset, markerWidth, markerHeight);
    if (position.r == Rotation.RIGHT) display.rect(drawPosition.x + rectSize - markerOffset - markerHeight / 2.0, drawPosition.y + markerWidth / 2.0, markerHeight, markerWidth);
    if (position.r == Rotation.DOWN) display.rect(drawPosition.x + markerWidth / 2.0, drawPosition.y + rectSize - markerOffset - markerHeight / 2.0, markerWidth, markerHeight);
    if (position.r == Rotation.LEFT) display.rect(drawPosition.x + markerOffset, drawPosition.y + markerWidth / 2.0, markerHeight, markerWidth);
  }
}
