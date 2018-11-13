package computerblocks.player;

import computerblocks.player.io.*;
import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.Grid;
import computerblocks.block.*;
import computerblocks.snippet.Snippet;
import computerblocks.player.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.display.*;
import computerblocks.*;

import java.io.*;
import java.util.ArrayList;

public class Player {
  public static final double PAN_SPEED = 6.5;
  public static final double ZOOM_SPEED = 70.0;

  public RealPosition drawTranslate = new RealPosition(0, 0);
  public RealPosition translate = new RealPosition(50, 0);
  public double zoom = 3;

  public int snipTime = 0;
  public SnippetSave snipSaveButton;

  public int selectedLayer = 0;
  public BlockType selectedType = BlockType.CABLE;
  public Rotation selectedRotation = Rotation.UP;

  public Selection selection;
  public Snippet snippet;

  public int placeTime = 0;
  private Game game;

  public Keyboard keyboard;
  public Mouse mouse;

  public State state = State.GAME;

  public boolean getTextInput = false;
  public String lastTextInput;

  public Player(Display display, Game game) {
    this.keyboard = new Keyboard(display);
    this.mouse = new Mouse(display);
    this.game = game;
    snipSaveButton = new SnippetSave(display.width - display.width / 25 * 1.2, display.width / 25 * 0.2, display.width / 25, display.width / 25);
  }

  public void draw(Display display, Grid grid, SnippetTray snippetTray) {
    if (selection != null) selection.draw(display, grid, this);
    if (state == State.PASTE && snippet != null) {
      snippet.ghost(display, this, grid.mouseOverBlock(this));
      snipSaveButton.draw(display);
      snipSaveButton.checkPress(display, this, snippetTray);
    }
  }

  public void update(Display display, Grid grid, MenuController menuController, SnippetTray snippetTray) {
    if (state != State.SNIPPET && state != State.PASTE) updateMenu(menuController, snippetTray);
    updateSelection(grid);
    updatePaste(grid, display);
    if (state == State.SNIPPET) {
      if (keyboard.down(Keyboard.ESC) || keyboard.down('P')) {
        state = State.GAME;
        placeTime = 0;
      }
    }
    if (state == State.PASTE) {
      if (keyboard.down(Keyboard.ESC)) {
        state = State.GAME;
        placeTime = 0;
        snippet = null;
      }
      if (keyboard.down('P')) {
        snippet = null;
        snippetTray.lifeTime = 0;
        snippetTray.scroll = 0;
        state = State.SNIPPET;
      }
    }
    if (state.doPlayerTranslate) updateTranslate(grid, display);
    if (state.doPlayerInteraction) updateInteraction(grid, display);

    mouse.update(display);
  }

  private void updateMenu(MenuController menuController, SnippetTray snippetTray) {
    if (state == State.GAME) {
      if (keyboard.down('P')) {
        snippetTray.lifeTime = 0;
        snippetTray.scroll = 0;
        state = State.SNIPPET;
      }
    }
    if (keyboard.down(Keyboard.ESC)) {
      if (state == State.GAME) {
        menuController.currentMenu = menuController.pauseMenu;
        state = State.MENU;
      } else if (state == State.MENU) {
        menuController.saveMenuElement.scroll = 0;
        if (menuController.currentMenu == menuController.pauseMenu) {
          state = State.GAME;
          placeTime = 0;
        } else menuController.currentMenu = menuController.pauseMenu;
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
    placeTime += 1;
    if (mouse.held(Mouse.LEFT) && !keyboard.held(Keyboard.SHIFT) && placeTime > 15) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null && grid.blockAt(mouseBlockPosition.x, mouseBlockPosition.y, mouseBlockPosition.l) == null) grid.place(selectedType, mouseBlockPosition, display, this);
    }

    if (mouse.held(Mouse.RIGHT)) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null) grid.erase(mouseBlockPosition, display, this);
    }

    if (keyboard.down('F')) {
      for (int x = 0; x < grid.width; x++) {
        for (int y = 0; y < grid.height; y++) {
          for (int l = 0; l < grid.layers; l++) {
            if (grid.blocks[x][y][l] != null) {
              // grid.blocks[x][y][l].charge = false;
              // grid.blocks[x][y][l].inputs = new ArrayList<Block>();
              // System.out.println(grid.blocks[x][y][l].position.toString());
              grid.blocks[x][y][l].saveInputPositions = null;
            }
          }
        }
      }
    }

    if (keyboard.down('X')) {
      BlockPosition mouseBlockPosition = grid.mouseOverBlock(this);
      if (mouseBlockPosition != null) {
        Block mouseBlock = grid.blockAt(mouseBlockPosition.x, mouseBlockPosition.y, mouseBlockPosition.l);
        if (mouseBlock != null) {
          System.out.println("----------");
          System.out.println("block pos: " + mouseBlockPosition.toString());
          System.out.println("charge: " + mouseBlock.charge);
          System.out.println("");
          System.out.println("inputs:");
          for (Block block : mouseBlock.inputs) {
            System.out.println("  " + block.position.toString());
          }
          System.out.println("save inputs:");
          if (mouseBlock.saveInputPositions != null){
            for (BlockPosition inp : mouseBlock.saveInputPositions) {
              System.out.println("  " + inp.toString());
            }
          }
          System.out.println("----------");
        }
      }
    }

    if (keyboard.down('1')) selectedType = BlockType.CABLE;
    if (keyboard.down('2')) selectedType = BlockType.SOURCE;
    if (keyboard.down('3')) selectedType = BlockType.INVERTER;
    if (keyboard.down('4')) selectedType = BlockType.DELAY;
    if (keyboard.down('5')) selectedType = BlockType.VIA;
    if (keyboard.down('9')) selectedType = BlockType.LABEL;
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
      placeTime = 0;
      grid.unselect();
      if (selection != null && grid.mouseOverBlock(this) != null) {
        snippet = new Snippet(selection.initialBlockPosition, grid.mouseOverBlock(this), grid);
        state = State.PASTE;
      }
      selection = null;
    }
  }

  private void updatePaste(Grid grid, Display display) {
    if (state == State.PASTE) snipTime += 1;
    if (state == State.PASTE && mouse.down(Mouse.LEFT) && grid.mouseOverBlock(this) != null && snipTime > 5 && snippet.layers + selectedLayer <= grid.layers) {
      if (!snipSaveButton.pointOver(mouse.position.x, mouse.position.y)) {
        if (snippet != null) grid.paste(snippet, grid.mouseOverBlock(this));
      }
    }

    // if (state == State.PASTE && mouse.up(Mouse.LEFT) && grid.mouseOverBlock(this) != null) {
    //   state = State.GAME;
    //   grid.paste(snippet, grid.mouseOverBlock(this));
    //   snippet = null;
    // }
  }
}
