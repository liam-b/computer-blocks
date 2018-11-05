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
  public static final double PAN_SPEED = 6.5;
  public static final double ZOOM_SPEED = 70.0;

  public RealPosition drawTranslate = new RealPosition(0, 0);
  public RealPosition translate = new RealPosition(50, 0);
  public double zoom = 3;

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

  public void update(Display display, Grid grid, MenuController menuController, SnippetTray snippetTray) {
    if (state != State.SNIPPET) updateMenu(menuController, snippetTray);
    updateSelection(grid);
    updatePaste(grid, display);
    if (state == State.SNIPPET) {
      if (keyboard.down(Keyboard.ESC) || keyboard.down('P')) {
        state = State.GAME;
      }
    }
    if (state.doPlayerTranslate) updateTranslate(grid, display);
    if (state.doPlayerInteraction) updateInteraction(grid, display);

    mouse.update(display);
  }

  private void updateMenu(MenuController menuController, SnippetTray snippetTray) {
    if (keyboard.down('P')) {
      snippetTray.lifeTime = 0;
      state = State.SNIPPET;
    }
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

  private void updateTranslate(Grid grid, Display display) {
    translate.x += ((keyboard.held('A') ? 1 : 0) - (keyboard.held('D') ? 1 : 0)) * PAN_SPEED * (1 / zoom);
    translate.y += ((keyboard.held('W') ? 1 : 0) - (keyboard.held('S') ? 1 : 0)) * PAN_SPEED * (1 / zoom);

    zoom += ((keyboard.held('.') ? 1 : 0) - (keyboard.held(',') ? 1 : 0)) * zoom / ZOOM_SPEED;
    drawTranslate.x = (translate.x - (display.width / 2)) * zoom + (display.width / 2);
    drawTranslate.y = (translate.y - (display.height / 2)) * zoom + (display.height / 2);
    // zoomTranslate.x = zoom / ZOOM_SPEED * -translate.x;
    // zoomTranslate.y = zoom / ZOOM_SPEED * -translate.y;

    if (keyboard.down('[')) selectedLayer = Math.max(0, selectedLayer - 1);
    if (keyboard.down(']')) selectedLayer = Math.min(selectedLayer + 1, grid.layers - 1);
  }

  private void updateInteraction(Grid grid, Display display) {
    if (mouse.held(Mouse.LEFT) && !keyboard.held(Keyboard.SHIFT)) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null && grid.blockAt(mouseBlockPosition) == null) grid.place(selectedType, mouseBlockPosition, display, this);
    }

    if (mouse.held(Mouse.RIGHT)) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null) grid.erase(mouseBlockPosition, display, this);
    }

    if (keyboard.down('1')) selectedType = BlockType.CABLE;
    if (keyboard.down('2')) selectedType = BlockType.SOURCE;
    if (keyboard.down('3')) selectedType = BlockType.INVERTER;
    if (keyboard.down('4')) selectedType = BlockType.DELAY;
    if (keyboard.down('5')) selectedType = BlockType.VIA;
    if (keyboard.down('R')) selectedRotation = Rotation.values()[(selectedRotation.ordinal() + 1) > 3 ? 0 : selectedRotation.ordinal() + 1];

    // if (keyboard.held('B')) System.out.println("-----------");
  }

  private void updateSelection(Grid grid) {
    if (state == State.GAME && mouse.down(Mouse.LEFT) && keyboard.held(Keyboard.SHIFT)) {
      state = State.SELECT;
      if (grid.mouseOverBlock(this) != null) selection = new Selection(grid, this);
    }

    if (state == State.SELECT && mouse.up(Mouse.LEFT)) {
      state = State.GAME;
      grid.unselect();
      if (selection != null && grid.mouseOverBlock(this) != null) new Snippet(selection.initialBlockPosition, grid.mouseOverBlock(this), grid).saveToFile("../saves/", "snippet");
      selection = null;
    }
  }

  private void updatePaste(Grid grid, Display display) {
    if (state == State.GAME && mouse.down(Mouse.RIGHT) && keyboard.held(Keyboard.SHIFT)) {
    // if (state == State.GAME && keyboard.down('P')) {
      state = State.PASTE;
      snippet = new Snippet("../saves/", "snippet");
    }

    if (state == State.PASTE && mouse.up(Mouse.LEFT) && grid.mouseOverBlock(this) != null) {
      state = State.GAME;
      grid.paste(snippet, grid.mouseOverBlock(this));
      snippet = null;
    }
  }
}
