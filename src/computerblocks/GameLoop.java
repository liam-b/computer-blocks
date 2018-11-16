package computerblocks;

import java.util.function.Function;

public abstract class GameLoop {
  public boolean running = true;

  public UpdateThread updateThread;
  public RenderThread renderThread;

  public abstract void start();
  public abstract void render();
  public abstract void update();
  public abstract void cleanup();

  GameLoop(double fps, double ups) {
    updateThread = new UpdateThread(this, ups);
    renderThread = new RenderThread(this, fps);

    start();
    updateThread.start();
    renderThread.start();
  }

  public final void end() {
    updateThread.end();
    renderThread.end();
    cleanup();
  }
}

class UpdateThread extends GameThread {
  private GameLoop gameLoop;
  private double updatesPerSecond;

  UpdateThread(GameLoop gameLoop, double updatesPerSecond) {
    super("update", updatesPerSecond);

    this.gameLoop = gameLoop;
    this.updatesPerSecond = updatesPerSecond;
  }

  public void cycle() {
    gameLoop.update();
  }
}

class RenderThread extends GameThread {
  private GameLoop gameLoop;
  private double framesPerSecond;

  RenderThread(GameLoop gameLoop, double framesPerSecond) {
    super("render", framesPerSecond);

    this.gameLoop = gameLoop;
    this.framesPerSecond = framesPerSecond;
  }

  public void cycle() {
    gameLoop.render();
  }
}