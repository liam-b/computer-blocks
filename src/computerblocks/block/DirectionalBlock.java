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
      player.translate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom,
      player.translate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom
    );

    if (withinScreenBounds(display, rectSize, drawPosition)) {
      if (selected) highlightBlock(display, player, rectSize, drawPosition);
      Color drawColor = (charge) ? chargeColor : color;
      display.color((ghost) ? new Color(drawColor, 0.5f) : drawColor);
      display.rect((int)drawPosition.x, (int)drawPosition.y, (int)rectSize, (int)rectSize);
      drawDirectionMarker(rectSize, drawPosition, display);
    }
  }

  void drawDirectionMarker(double rectSize, RealPosition drawPosition, Display display) {
    double markerHeight = rectSize / 12f;
    double markerWidth = rectSize / 2f;
    double markerOffset = rectSize / 8f;

    display.color(markerColor);
    if (position.r == Rotation.UP) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + markerOffset, markerWidth, markerHeight);
    if (position.r == Rotation.RIGHT) display.rect(drawPosition.x + rectSize - markerOffset - markerHeight / 2f, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
    if (position.r == Rotation.DOWN) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + rectSize - markerOffset - markerHeight / 2f, markerWidth, markerHeight);
    if (position.r == Rotation.LEFT) display.rect(drawPosition.x + markerOffset, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
  }
}
