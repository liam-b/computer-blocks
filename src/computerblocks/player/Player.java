package computerblocks.player;

import computerblocks.position.*;

public class Player {
  public static int PAN_SPEED = 5;

  public RealPosition translate;
  public float zoom = 5;

  public Keyboard keyboard;
  public Mouse mouse;

  public Player() {
    translate = new RealPosition(50, 0);
    keyManager = new Keyboard(display);
    mouseManager = new Mouse(display);
  }

  public void update(KeyManager keyManager, MouseManager mouseManager) {
    updatePlayerInput(keyManager, mouseManager);
  }

  private void updatePlayerInput(KeyManager keyManager, MouseManager mouseManager) {
    translate.x += ((keyManager.getKey('A') ? 1: 0) - (keyManager.getKey('D') ? 1 : 0)) * PAN_SPEED;
    translate.y += ((keyManager.getKey('W') ? 1: 0) - (keyManager.getKey('S') ? 1 : 0)) * PAN_SPEED;

    zoom += ((keyManager.getKey('.') ? 1: 0) - (keyManager.getKey(',') ? 1: 0)) * zoom / 100.0f;


    if (mouseManager.left) {
      BlockPosition clickedPosition = grid.getBlockPosition(mouseX, mouseY);
      Block blockAtPos = grid.getBlockAtPosition(clickedPosition);
      if (clickedPosition != null && blockAtPos == null) grid.place(player.selectedType, new BlockPosition(clickedPosition.x, clickedPosition.y, selectedRotation, selectedLayer));
    }

    if (mouseManager.right) {
      System.out.println("out");
      // BlockPosition clickedPosition = grid.getBlockPosition(mouseX, mouseY);
      // if (clickedPosition != null) grid.erase(clickedPosition);
    }
  }
}