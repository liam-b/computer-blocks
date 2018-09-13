package computerblocks.player;

import computerblocks.player.io.*;
import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.Grid;
import computerblocks.block.BlockType;
import computerblocks.snippet.Snippet;
import computerblocks.player.*;
import computerblocks.display.ui.menu.*;

public class Player {
  public static final int PAN_SPEED = 8;
  public static final double ZOOM_SPEED = 80f;

  public RealPosition translate = new RealPosition(50, 0);
  public double zoom = 1;

  public int selectedLayer = 0;
  public BlockType selectedType = BlockType.CABLE;
  public Rotation selectedRotation = Rotation.UP;

  public Selection selection;
  public Snippet snippet;

  public Keyboard keyboard;
  public Mouse mouse;

  public State state = State.GAME;

  public Player(Display display) {
    this.keyboard = new Keyboard(display);
    this.mouse = new Mouse(display);
  }

  public void draw(Display display, Grid grid) {
    if (selection != null) selection.draw(display, grid, this);
    if (state == State.PASTE && snippet != null) snippet.ghost(display, this, grid.mouseOverBlock(this));
  }

  public void update(Display display, Grid grid, MenuController menuController) {
    updateMenu(menuController);
    updateSelection(grid);
    updatePaste(grid, display);

    if (state.doPlayerTranslate) updateTranslate(grid);
    if (state.doPlayerInteraction) updateInteraction(grid);

    mouse.update(display);
  }

  private void updateMenu(MenuController menuController) {
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

  private void updateTranslate(Grid grid) {
    translate.x += ((keyboard.held('A') ? 1 : 0) - (keyboard.held('D') ? 1 : 0)) * PAN_SPEED;
    translate.y += ((keyboard.held('W') ? 1 : 0) - (keyboard.held('S') ? 1 : 0)) * PAN_SPEED;
    zoom += ((keyboard.held('.') ? 1 : 0) - (keyboard.held(',') ? 1 : 0)) * zoom / ZOOM_SPEED;

    if (keyboard.down('[')) selectedLayer = Math.max(0, selectedLayer - 1);
    if (keyboard.down(']')) selectedLayer = Math.min(selectedLayer + 1, grid.layers - 1);
  }

  private void updateInteraction(Grid grid) {
    if (mouse.held(Mouse.LEFT) && !keyboard.held(Keyboard.SHIFT)) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null && grid.blockAt(mouseBlockPosition) == null) grid.place(selectedType, mouseBlockPosition);
    }

    if (mouse.held(Mouse.RIGHT)) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null) grid.erase(mouseBlockPosition);
    }

    if (keyboard.down('1')) selectedType = BlockType.CABLE;
    if (keyboard.down('2')) selectedType = BlockType.SOURCE;
    if (keyboard.down('3')) selectedType = BlockType.INVERTER;
    if (keyboard.down('4')) selectedType = BlockType.DELAY;
    if (keyboard.down('5')) selectedType = BlockType.VIA;
    if (keyboard.down('R')) selectedRotation = Rotation.values()[(selectedRotation.ordinal() + 1) > 3 ? 0 : selectedRotation.ordinal() + 1];
  }

  private void updateSelection(Grid grid) {
    if (state == State.GAME && mouse.down(Mouse.LEFT) && keyboard.held(Keyboard.SHIFT)) {
      state = State.SELECT;
      if (grid.mouseOverBlock(this) != null) selection = new Selection(grid, this);
    }

    if (state == State.SELECT && mouse.up(Mouse.LEFT)) {
      state = State.GAME;
      grid.unselect();
      if (selection != null && grid.mouseOverBlock(this) != null) new Snippet(selection.initialBlockPosition, grid.mouseOverBlock(this), grid).saveToFile("../saves/", "save");
      selection = null;
    }
  }

  private void updatePaste(Grid grid, Display display) {
    if (state == State.GAME && mouse.down(Mouse.RIGHT) && keyboard.held(Keyboard.SHIFT)) {
    // if (state == State.GAME && keyboard.down('P')) {
      state = State.PASTE;
      snippet = new Snippet("../saves/", "save");
    }

    if (state == State.PASTE && mouse.up(Mouse.LEFT) && grid.mouseOverBlock(this) != null) {
      state = State.GAME;
      grid.paste(snippet, grid.mouseOverBlock(this));
      snippet = null;
    }
  }
}
