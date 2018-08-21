package computerblocks.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
  private boolean[] keys;
  private boolean[] keysPressed;

  public KeyManager() {
    keys = new boolean[256];
  }

  @Override
  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  public boolean getKey(int keyCode) {
    return keys[keyCode];
  }
}
