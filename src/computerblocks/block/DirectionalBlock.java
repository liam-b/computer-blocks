package computerblocks.block;

import java.util.ArrayList;

import computerblocks.display.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class DirectionalBlock extends Block {

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
    display.color(new Color("#f2e24f"));
    if (position.r == Rotation.DOWN) display.rect(drawPosition.x + rectSize/4, drawPosition.y + rectSize / 2.5f - rectSize / 24 + rectSize/2, rectSize / 2, rectSize / 12);
    if (position.r == Rotation.RIGHT) display.rect(drawPosition.x + rectSize / 2.5f - rectSize / 24 + rectSize/4, drawPosition.y + rectSize/2, rectSize / 12, rectSize / 2);
    if (position.r == Rotation.UP) display.rect(drawPosition.x + rectSize/4, drawPosition.y - rectSize / 2.5f + rectSize / 24 + rectSize/2, rectSize / 2, rectSize / 12);
    if (position.r == Rotation.LEFT) display.rect(drawPosition.x - rectSize / 2.5f + rectSize / 24 + rectSize/4, drawPosition.y + rectSize/2, rectSize / 12, rectSize / 2);
  }
}
