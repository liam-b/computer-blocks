package computerblocks.position;

public class RealPosition {
  public double x = 0, y = 0;

  public RealPosition() {}

  public RealPosition(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public RealPosition(RealPosition pos) {
    this.x = pos.x;
    this.y = pos.y;
  }

  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public String toString() {
    return "[" + x + ", " + y + "]";
  }
}
