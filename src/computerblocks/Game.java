package computerblocks;

public class Game extends GameLoop {
  public Game() {
    super(60.0, 60.0);
  }

  public void start() {
    System.out.println("START");
  }

  public void update() {
    System.out.println("UPDATE");
  }

  public void render() {
    System.out.println("RENDER");
  }

  public void cleanup() {
    System.out.println("CLEANUP");
  }
}