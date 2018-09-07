package computerblocks.position;

import java.awt.Point;

public class RealPosition {
  public float x = 0, y = 0;

  public RealPosition() {}

  public RealPosition(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public RealPosition(Point pos) {
    this.x = pos.x;
    this.y = pos.y;
  }

  public RealPosition(RealPosition pos) {
    this.x = pos.x;
    this.y = pos.y;
  }

  public String toString() {
    return "[" + x + ", " + y + "]";
  }
}