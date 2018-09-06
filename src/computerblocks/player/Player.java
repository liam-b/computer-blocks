package computerblocks.player;

import computerblocks.player.io.*;
import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.Grid;
import computerblocks.block.BlockType;
import computerblocks.snippet.*;
import computerblocks.player.*;
import computerblocks.display.ui.menu.*;

public class Player {
  public static int PAN_SPEED = 8;
  public static float ZOOM_SPEED = 80f;

  public RealPosition translate;
  public float zoom = 5;

  public int selectedLayer;
  public BlockType selectedType;
  public Rotation selectedRotation;
  public Selection selection;

  public Keyboard keyboard;
  public Mouse mouse;
  private Grid grid;

  public State state = State.GAME;

  public Player(Display display, Grid grid) {
    this.translate = new RealPosition(50, 0);
    this.keyboard = new Keyboard(display);
    this.mouse = new Mouse(display, this);
    this.grid = grid;

    this.selectedLayer = 0;
    this.selectedType = BlockType.CABLE;
    this.selectedRotation = Rotation.UP;
  }

  public void update(Display display, Grid grid, MenuController menuController) {
    mouse.update(display);
    if (state == State.GAME) updatePlayerInput(grid);
    if (keyboard.down(Keyboard.ESC)) {
      if (state == State.GAME) {
        menuController.currentMenu = menuController.pauseMenu;
        state = State.MENU;
      } else if (state == State.MENU) {
        if (menuController.currentMenu == menuController.pauseMenu) state = State.GAME;
        else menuController.currentMenu = menuController.pauseMenu;
      }
    }
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

  public void mousePressed() {
    if (keyboard.held(Keyboard.SHIFT)) {
      selection = new Selection(grid, this);
    }
  }

  public void mouseReleased() {}
}
