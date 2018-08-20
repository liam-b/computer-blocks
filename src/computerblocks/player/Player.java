package computerblocks.player;

import computerblocks.position.*;

public class Player {
  public static int PAN_SPEED = 5;

  public RealPosition translate;
  public float zoom = 5;

  public Player() {
    translate = new RealPosition();
  }
}