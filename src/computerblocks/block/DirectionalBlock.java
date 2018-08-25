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
    float rectSize = (float)BLOCK_SIZE * player.zoom;
    RealPosition drawPosition = new RealPosition(
      player.translate.x + (float)BLOCK_RATIO * (float)position.x * player.zoom,
      player.translate.y + (float)BLOCK_RATIO * (float)position.y * player.zoom
    );

    if (withinScreenBounds(display, rectSize, drawPosition)) {
      if (selected) highlightBlock(display, player, rectSize, drawPosition);
      display.color((charge) ? chargeColor : color);
      display.rect((int)drawPosition.x, (int)drawPosition.y, (int)rectSize, (int)rectSize);
      drawDirectionMarker(rectSize, drawPosition, display);
    }
  }

  void drawDirectionMarker(float rectSize, RealPosition drawPosition, Display display) {
    float markerHeight = rectSize / 12f;
    float markerWidth = rectSize / 2f;
    float markerOffset = rectSize / 8f;

    display.color(new Color("#f2e24f"));
    if (position.r == Rotation.UP) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + markerOffset, markerWidth, markerHeight);
    if (position.r == Rotation.RIGHT) display.rect(drawPosition.x + rectSize - markerOffset - markerHeight / 2f, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
    if (position.r == Rotation.DOWN) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + rectSize - markerOffset - markerHeight / 2f, markerWidth, markerHeight);
    if (position.r == Rotation.LEFT) display.rect(drawPosition.x + markerOffset, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
  }
}
