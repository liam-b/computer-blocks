package computerblocks;

import java.lang.*;

import computerblocks.display.*;
import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.player.*;
import computerblocks.display.ui.*;
import computerblocks.snippet.Snippet;

public class Game {
  private boolean running = true;
  private long targetFps = 60;
  private int updateCount = 0;

  private Display display;
  private UserInterface ui;

  private Player player;
  private Grid grid;

  public Game(String title, int displayWidth, int displayHeight, int displayScreen) {
    display = new Display(title, displayWidth, displayHeight, displayScreen);

    setup();
    loop();
  }

  private void loop() {
    long lastLoopTime = System.nanoTime();
    double optimalTime = 1000000000d / (double)targetFps;
    double fps;

    while (running) {
      long timeNow = System.nanoTime();
      long updateLength = timeNow - lastLoopTime;
      double delta = updateLength / ((double)optimalTime);
      lastLoopTime = timeNow;

      if (updateCount % 10 == 0) tick();
      update();
      render();

      updateCount += 1;

      long delay = (long)optimalTime - (System.nanoTime() - timeNow);
      double sleepTime = Math.max((double)delay / 1000000d, 0d);
      // long stress = (long)optimalTime - delay;
      // System.out.println(stress / 100000l);

      try { Thread.sleep((long)sleepTime); }
      catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }
  }

  private void setup() {
    player = new Player(display);
    grid = new Grid(300, 300, 6);
    ui = new UserInterface();
  }

  private void update() {
    player.update(display, grid);
    if (player.keyboard.down('E')) grid = new Grid(new Snippet("../saves/", "save"));
  }

  private void tick() {
    grid.tick();
  }

  private synchronized void render() {
    display.reset(Color.BACKGROUND);

    grid.draw(display, player);
    ui.draw(display, player, grid);
    if (player.selection != null) player.selection.draw(display, grid, player);

    display.draw();
  }
}
