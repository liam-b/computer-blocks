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
  public double zoom = 3;

  public int selectedLayer = 0;
  public BlockType selectedType = BlockType.CABLE;
  public Rotation selectedRotation = Rotation.UP;

  public Keyboard keyboard;
  public Mouse mouse;

  public PlayerStateMachine stateMachine;

  // public State[] states = new State[1];

  // State inital = new Initial();

  public Player(Display display, Game game) {
    stateMachine = new PlayerStateMachine(game);
    keyboard = new Keyboard(display);
    mouse = new Mouse(display);
  }

  public void updateUserInteraction() {
    keyboard.update();
    mouse.update();
  }

  // public void draw(Display display, Grid grid, SnippetTray snippetTray) {
  //   // if (selection != null) selection.draw(display, grid, this);
  // }
}
