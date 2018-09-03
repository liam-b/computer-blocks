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
    if (type == BlockType.CABLE) return Color.CABLE_OFF;
    if (type == BlockType.SOURCE) return Color.SOURCE;
    if (type == BlockType.INVERTER) return Color.INVERTER_OFF;
    if (type == BlockType.VIA) return Color.VIA_OFF;
    if (type == BlockType.DELAY) return Color.DELAY_OFF;
    return Color.BACKGROUND;
  }

  public void draw(Display display, Player player, Grid grid) {
    // block selected
    display.color(Color.BACKGROUND);
    display.rect(BORDER, display.height - BORDER - SCALE, SCALE, SCALE);
    display.color(getColorFromType(player.selectedType));
    display.rect(BORDER + SCALE/2 - SCALE*0.9f/2, display.height - BORDER - SCALE/2 - SCALE*0.9f/2, SCALE*0.9f, SCALE*0.9f);

    // selected block rotation
    if (grid.getBlockFromType(player.selectedType, new BlockPosition()).getClass().getSuperclass().getName() == "computerblocks.block.DirectionalBlock") {
      drawDirectionMarker(SCALE*0.9f, new RealPosition(BORDER + SCALE/2 - SCALE*0.9f/2, display.height - BORDER - SCALE/2 - SCALE*0.9f/2), display, player.selectedRotation);
    }

    // draw layers
    display.color(Color.BACKGROUND);
    display.rect(BORDER + SCALE + SPACING, display.height - BORDER - SCALE, SCALE, SCALE);
    display.color(Color.CABLE_OFF);
    display.rect(BORDER + SCALE*1.5f + SPACING - SCALE*0.9f/2, display.height - BORDER - SCALE/2 - SCALE*0.9f/2 + player.selectedLayer * (SCALE*0.9f / grid.layers), SCALE*0.9f, SCALE*0.9f / grid.layers);
  }

  void drawDirectionMarker(float rectSize, RealPosition drawPosition, Display display, Rotation rotation) {
    float markerHeight = rectSize / 12f;
    float markerWidth = rectSize / 2f;
    float markerOffset = rectSize / 8f;

    display.color(Color.SOURCE);
    if (rotation == Rotation.UP) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + markerOffset, markerWidth, markerHeight);
    if (rotation == Rotation.RIGHT) display.rect(drawPosition.x + rectSize - markerOffset - markerHeight / 2f, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
    if (rotation == Rotation.DOWN) display.rect(drawPosition.x + markerWidth / 2f, drawPosition.y + rectSize - markerOffset - markerHeight / 2f, markerWidth, markerHeight);
    if (rotation == Rotation.LEFT) display.rect(drawPosition.x + markerOffset, drawPosition.y + markerWidth / 2f, markerHeight, markerWidth);
  }
}