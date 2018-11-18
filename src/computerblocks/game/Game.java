package computerblocks.game;

import java.lang.*;
import java.io.*;

import computerblocks.Grid;
import computerblocks.display.*;
import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.player.Player;
import computerblocks.player.io.*;

public class Game extends GameLoop {
  private Display display;
  // private UserInterface ui;

  private Player player;
  private Grid grid;

  public Game() { super(30.0, 60.0); }

  public void start() {
    System.out.println("START");

    display = new Display(1280, 720, "computerblocks");
    grid = new Grid(400, 400, 3);
    player = new Player(display);
    // ui = new UserInterface();
  }

  public void tick() {
    // grid.tick(display, player);
  }

  public void update() {
    player.updateUserInteraction();
    // player.update(display, grid, menuController, snippetTray);
    System.out.println(player.mouse.up(Mouse.LEFT));
  }

  public void render() {
    display.clear();

    // grid.draw(display, player);
    // player.draw(display, grid, snippetTray);
    // ui.draw(display, player, grid);

    display.color(255, 0, 255);
    display.rect(100, 100, 50, 50);

    display.draw();
    if (display.windowShouldClose) destroy();
  }

  public void cleanup() {
    System.out.println("CLEANUP");
    display.destroy();
  }
}