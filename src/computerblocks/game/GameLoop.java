package computerblocks.game;

public abstract class GameLoop {
  public boolean running = true;

  public TickThread tickThread;
  public RenderThread renderThread;

  public abstract void start();
  public abstract void tick();
  public abstract void update();
  public abstract void render();
  public abstract void cleanup();

  GameLoop(double ticksPerSecond, double framesPerSecond) {
    tickThread = new TickThread(this, ticksPerSecond);
    renderThread = new RenderThread(this, framesPerSecond);

    start();
    tickThread.start();
    renderThread.run();
  }

  public final void destroy() {
    tickThread.end();
    renderThread.end();
    cleanup();
  }
}

class TickThread extends GameThread {
  private GameLoop gameLoop;
  private double ticksPerSecond;

  TickThread(GameLoop gameLoop, double ticksPerSecond) {
    super("update", ticksPerSecond);

    this.gameLoop = gameLoop;
    this.ticksPerSecond = ticksPerSecond;
  }

  public void cycle() {
    gameLoop.tick();
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
    gameLoop.update();
    gameLoop.render();
    try { Thread.sleep(10); }
    catch (Exception e) {}
  }
}