package computerblocks.player;

import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.Grid;
import computerblocks.block.BlockType;

public class Player {
  public static int PAN_SPEED = 8;
  public static float ZOOM_SPEED = 80f;

  public RealPosition translate;
  public float zoom = 5;

  public BlockType selectedType;
  public Rotation selectedRotation;

  public Keyboard keyboard;
  public Mouse mouse;

  public Player(Display display) {
    translate = new RealPosition(50, 0);
    keyboard = new Keyboard(display);
    mouse = new Mouse(display);

    selectedType = BlockType.CABLE;
    selectedRotation = Rotation.UP;
  }

  public void update(Display display, Grid grid) {
    mouse.update(display);
    updatePlayerInput(grid);
  }

  private void updatePlayerInput(Grid grid) {
    translate.x += ((keyboard.getKey('A') ? 1 : 0) - (keyboard.getKey('D') ? 1 : 0)) * PAN_SPEED;
    translate.y += ((keyboard.getKey('W') ? 1 : 0) - (keyboard.getKey('S') ? 1 : 0)) * PAN_SPEED;

    if (keyboard.getKey('1')) selectedType = BlockType.CABLE;
    if (keyboard.getKey('2')) selectedType = BlockType.SOURCE;

    zoom += ((keyboard.getKey('.') ? 1 : 0) - (keyboard.getKey(',') ? 1 : 0)) * zoom / ZOOM_SPEED;

    if (keyboard.getKey('1')) selectedType = BlockType.CABLE;
    if (keyboard.getKey('2')) selectedType = BlockType.SOURCE;
    if (keyboard.getKey('3')) selectedType = BlockType.INVERTER;
    if (keyboard.getKey('4')) selectedType = BlockType.DELAY;
    if (keyboard.getKey('5')) selectedType = BlockType.VIA;

    if (keyboard.getKey('r')) selectedRotation = Rotation.values()[(selectedRotation.ordinal() + 1) > 3 ? 0 : selectedRotation.ordinal() + 1];

    if (mouse.left) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null && grid.blockAt(mouseBlockPosition) == null) grid.place(selectedType, mouseBlockPosition);
    }

    if (mouse.right) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null) grid.erase(mouseBlockPosition);
    }
  }
}
