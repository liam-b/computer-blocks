package computerblocks.player;

import computerblocks.player.io.*;
import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.Grid;
import computerblocks.block.BlockType;
import computerblocks.snippet.*;

public class Player {
  public static int PAN_SPEED = 8;
  public static float ZOOM_SPEED = 80f;

  public RealPosition translate;
  public float zoom = 5;

  public int selectedLayer;
  public BlockType selectedType;
  public Rotation selectedRotation;

  public Keyboard keyboard;
  public Mouse mouse;

  public Player(Display display) {
    translate = new RealPosition(50, 0);
    keyboard = new Keyboard(display);
    mouse = new Mouse(display);

    selectedLayer = 0;
    selectedType = BlockType.CABLE;
    selectedRotation = Rotation.UP;
  }

  public void update(Display display, Grid grid) {
    mouse.update(display);
    updatePlayerInput(grid);
  }

  private void updatePlayerInput(Grid grid) {
    // if (keyboard.down('Q')) new Snippet(grid).saveToFile("../saves/", "save");
    if (keyboard.down('Q')) new Snippet(new BlockPosition(0, 0, 0), new BlockPosition(10, 10, 0), grid).saveToFile("../saves/", "save");

    translate.x += ((keyboard.held('A') ? 1 : 0) - (keyboard.held('D') ? 1 : 0)) * PAN_SPEED;
    translate.y += ((keyboard.held('W') ? 1 : 0) - (keyboard.held('S') ? 1 : 0)) * PAN_SPEED;

    zoom += ((keyboard.held('.') ? 1 : 0) - (keyboard.held(',') ? 1 : 0)) * zoom / ZOOM_SPEED;

    if (keyboard.down('[')) selectedLayer = Math.max(0, selectedLayer - 1);
    if (keyboard.down(']')) selectedLayer = Math.min(selectedLayer + 1, grid.layers - 1);

    if (keyboard.down('1')) selectedType = BlockType.CABLE;
    if (keyboard.down('2')) selectedType = BlockType.SOURCE;
    if (keyboard.down('3')) selectedType = BlockType.INVERTER;
    if (keyboard.down('4')) selectedType = BlockType.DELAY;
    if (keyboard.down('5')) selectedType = BlockType.VIA;

    if (keyboard.down('R')) selectedRotation = Rotation.values()[(selectedRotation.ordinal() + 1) > 3 ? 0 : selectedRotation.ordinal() + 1];

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
