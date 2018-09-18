package computerblocks.display.ui;

import computerblocks.display.*;
import computerblocks.player.*;
import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.*;

public class UserInterface {
  static final Color BACKGROUND = new Color("#e0e0e0");

  private int SCALE = 75;
  private int BORDER = 30;
  private int SPACING = 20;

  public UserInterface() { }

  Color getColorFromType(BlockType type) {
    if (type == BlockType.CABLE) return Color.CABLE;
    if (type == BlockType.SOURCE) return Color.SOURCE;
    if (type == BlockType.INVERTER) return Color.INVERTER;
    if (type == BlockType.VIA) return Color.VIA;
    if (type == BlockType.DELAY) return Color.DELAY;
    return Color.UI_BACKGROUND;
  }

  public void draw (Display display, Player player, Grid grid) {
    // block selected
    display.color(Color.UI_BACKGROUND);
    display.rect(BORDER, display.height - BORDER - SCALE, SCALE, SCALE);
    display.color(getColorFromType(player.selectedType));
    display.rect(BORDER + SCALE/2 - SCALE*0.9f/2, display.height - BORDER - SCALE/2 - SCALE*0.9f/2, SCALE*0.9f, SCALE*0.9f);

    // selected block rotation
    if (player.selectedType.isDirectional()) drawDirectionMarker(SCALE*0.9f, new RealPosition(BORDER + SCALE/2 - SCALE*0.9f/2, display.height - BORDER - SCALE/2 - SCALE*0.9f/2), display, player.selectedRotation);
    if (player.selectedType == BlockType.VIA) drawCore(SCALE*0.9f, new RealPosition(BORDER + SCALE/2 - SCALE*0.9f/2, display.height - BORDER - SCALE/2 - SCALE*0.9f/2), display);

    // draw layers
    display.color(Color.UI_BACKGROUND);
    display.rect(BORDER + SCALE + SPACING, display.height - BORDER - SCALE, SCALE, SCALE);
    display.color(Color.CABLE);
    display.rect(BORDER + SCALE*1.5f + SPACING - SCALE*0.9f/2, display.height - BORDER - SCALE/2 - SCALE*0.9f/2 + (grid.layers - player.selectedLayer - 1) * (SCALE*0.9f / grid.layers), SCALE*0.9f, SCALE*0.9f / grid.layers);
  }

  void drawDirectionMarker(double rectSize, RealPosition drawPosition, Display display, Rotation rotation) {
    double markerHeight = rectSize / 12f;
    double markerWidth = rectSize / 2f;
    double markerOffset = rectSize / 8f;

    display.color(Color.SOURCE);
    if (rotation == Rotation.UP) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + markerOffset, markerWidth, markerHeight);
    if (rotation == Rotation.RIGHT) display.rect(drawPosition.x + rectSize - markerOffset - markerHeight / 2f, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
    if (rotation == Rotation.DOWN) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + rectSize - markerOffset - markerHeight / 2f, markerWidth, markerHeight);
    if (rotation == Rotation.LEFT) display.rect(drawPosition.x + markerOffset, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
  }

  void drawCore(double rectSize, RealPosition drawPosition, Display display) {
    double coreSize = rectSize / 6f;

    display.color(Color.SOURCE);
    display.rect(drawPosition.x + rectSize / 2f - coreSize / 2f, drawPosition.y + rectSize / 2f - coreSize / 2f, coreSize, coreSize);
  }
}
