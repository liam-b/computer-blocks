package computerblocks.game;

public abstract class GameThread implements Runnable {
  private static final double NS = 1000000000.0;

  private Thread thread;
  private String name;

  public long lastTime = System.nanoTime();
  public double delta = 0.0;
  public long cycles = 0;
  public int cyclesPerSecond = (int)cycles;
  public double target = 60.0;
  public boolean running = true;

  public abstract void cycle();

  public GameThread(String name, double target) {
    this.name = name;
    this.target = target;
  }

  public void start() {
    if (thread == null) {
      thread = new Thread(this, name);
      thread.start();
    }
  }

  public void run() {
    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / (GameThread.NS / target);
      lastTime = now;
      if (delta >= 1.0) {
        cycle();
        cycles += 1;
        delta -= 1;
      }
    }
  }

  public void end() {
    running = false;
  }
}