package computerblocks;

public class Game extends GameLoop {
  private Display display;

  public Game() { super(30.0, 60.0); }

  public void start() {
    System.out.println("START");
    display = new Display();
  }

  public void tick() {
    System.out.println("TICK");
  }

  public void update() {
    System.out.println("UPDATE");
  }

  public void render() {
    System.out.println("RENDER");
    display.draw();
  }

  public void cleanup() {
    System.out.println("CLEANUP");
  }
}