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

  private Block myBlock;
  private Player myPlayer;

  public Game(String title, int displayWidth, int displayHeight, int displayScreen) {
    display = new Display(title, displayWidth, displayHeight, displayScreen);

    setup();
    loop();
  }

  private void loop() {
    long lastLoopTime = System.nanoTime();
    long optimalTime = 1000000000l / targetFps;

    while (running) {
      long timeNow = System.nanoTime();
      long updateLength = timeNow - lastLoopTime;
      double delta = updateLength / ((double) optimalTime);
      lastLoopTime = timeNow;

      double fps = (1000000000d / (double) (timeNow - System.nanoTime() + optimalTime));
      double displayFps = Math.round(fps * 100d) / 100d;
      // System.out.println(displayFps);

      update();
      render();

      long delay = Math.max((timeNow - System.nanoTime() + optimalTime) / 1000000l, 0l);
      // long stress = ((optimalTime / 1000000l) - delay);
      try { Thread.sleep(delay); }
      catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }
  }

  private void setup() {
    myPlayer = new Player();
    myBlock = new CableBlock(new BlockPosition());
  }

  private void update() {
  }

  private void render() {
    display.reset();

    myBlock.draw(display, myPlayer);

    // display.color(new Color("#dddddd"));
    // display.rect(10, 10, 300, 300);

    display.draw();
  }
}