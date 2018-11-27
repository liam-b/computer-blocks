package computerblocks.player;

import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.Grid;
import computerblocks.block.*;
import computerblocks.display.*;
import computerblocks.*;
import computerblocks.player.io.*;
import computerblocks.game.Game;

import java.io.*;
import java.util.ArrayList;

public class Player {
  public static final double PAN_SPEED = 6.5;
  public static final double ZOOM_SPEED = 70.0;

  public RealPosition drawTranslate = new RealPosition(0, 0);
  public RealPosition translate = new RealPosition(50, 0);
  public double zoom = 3.0;

  public int selectedLayer = 0;
  public BlockType selectedType = BlockType.CABLE;
  public Rotation selectedRotation = Rotation.UP;

  public Keyboard keyboard;
  public Mouse mouse;

  public PlayerStateMachine stateMachine;

  public Player(Display display, Game game) {
    stateMachine = new PlayerStateMachine(game);
    keyboard = new Keyboard(display);
    mouse = new Mouse(display);
  }

  public void update(Display display, Grid grid) {
    updateTranslate(grid, display);
  }

  public void updateUserInteraction() {
    keyboard.update();
    mouse.update();
  }

  private void updateTranslate(Grid grid, Display display) {
    translate.x += ((keyboard.held('A') ? 1 : 0) - (keyboard.held('D') ? 1 : 0)) * PAN_SPEED * (1 / zoom);
    translate.y += ((keyboard.held('W') ? 1 : 0) - (keyboard.held('S') ? 1 : 0)) * PAN_SPEED * (1 / zoom);

    zoom += ((keyboard.held('.') ? 1 : 0) - (keyboard.held(',') ? 1 : 0)) * zoom / ZOOM_SPEED;
    drawTranslate.x = (translate.x - (display.width / 2)) * zoom + (display.width / 2);
    drawTranslate.y = (translate.y - (display.height / 2)) * zoom + (display.height / 2);

    // if (keyboard.down('[')) selectedLayer = Math.max(0, selectedLayer - 1);
    // if (keyboard.down(']')) selectedLayer = Math.min(selectedLayer + 1, grid.layers - 1);
  }

  // public void draw(Display display, Grid grid, SnippetTray snippetTray) {
  //   // if (selection != null) selection.draw(display, grid, this);
  // }
}
