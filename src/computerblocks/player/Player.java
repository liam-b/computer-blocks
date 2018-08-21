package computerblocks.player;

import computerblocks.position.*;

public class Player {
  public static int PAN_SPEED = 5;

  public RealPosition translate;
  public float zoom = 5;

  public Player() {
    translate = new RealPosition(50, 0);
  }

  public void tick(KeyManager keyManager) {
    translate.x += ((keyManager.getKey('A') ? 1: 0) - (keyManager.getKey('D') ? 1 : 0)) * PAN_SPEED;
    translate.y += ((keyManager.getKey('W') ? 1: 0) - (keyManager.getKey('S') ? 1 : 0)) * PAN_SPEED;

    zoom += ((keyManager.getKey('.') ? 1: 0) - (keyManager.getKey(',') ? 1: 0)) * zoom / 100.0f;
  }
}