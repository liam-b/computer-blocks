package computerblocks.player;

import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.Grid;
import computerblocks.block.*;
import computerblocks.player.*;
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

  public int selectedLayer = 0;
  public BlockType selectedType = BlockType.CABLE;
  public Rotation selectedRotation = Rotation.UP;

  // public Keyboard keyboard;
  // public Mouse mouse;
  //
  // public State state = State.GAME;

  public Player(Display display) {
    // this.keyboard = new Keyboard(display);
    // this.mouse = new Mouse(display);
  }

  // public void draw(Display display, Grid grid, SnippetTray snippetTray) {
  //   // if (selection != null) selection.draw(display, grid, this);
  // }
}
