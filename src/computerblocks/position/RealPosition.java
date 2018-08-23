package computerblocks.position;

import java.awt.Point;

public class RealPosition {
  public float x, y;

  public RealPosition(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public RealPosition(Point pos) {
    this.x = pos.x;
    this.y = pos.y;
  }

  public RealPosition() {
    this.x = 0f;
    this.y = 0f;
  }
}