package computerblocks;

import java.lang.*;

import computerblocks.display.*;
import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.player.*;

public class Game {
  private boolean running = true;
  private long targetFps = 60;
  private int fps;

  private Display display;

  private Player player;
  private KeyManager keyManager;
  private Grid grid;

  public Game(String title, int displayWidth, int displayHeight, int displayScreen) {
    display = new Display(title, displayWidth, displayHeight, displayScreen);
    keyManager = new KeyManager();
    display.addKeyListener(keyManager);

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

      update();
      render();

      long delay = (long)optimalTime - (System.nanoTime() - timeNow);
      double sleepTime = Math.max((double)delay / 1000000d, 0d);
      // long stress = (long)optimalTime - delay;
      // System.out.println(stress / 100000l);

      try { Thread.sleep((long)sleepTime); }
      catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }
  }

  private void setup() {
    player = new Player();
    grid = new Grid(300, 300, 1);

    grid.blocks[0][0][0] = new CableBlock(new BlockPosition(0, 0, 0));
    grid.blocks[1][0][0] = new SourceBlock(new BlockPosition(1, 0, 0));

  }

  private void update() {
    player.tick(keyManager);
  }

  private synchronized void render() {
    display.reset(Color.BACKGROUND);

    grid.draw(display, player);

    display.draw();
  }
}