package computerblocks.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import computerblocks.display.Display;

public class Keyboard implements KeyListener {
  private boolean[] keys;

  public KeyManager(Display display) {
    display.frame.addKeyListener(this);
    keys = new boolean[256];
  }

  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
  }

  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }

  public void keyTyped(KeyEvent e) {}

  public boolean getKey(int keyCode) {
    return keys[keyCode];
  }
}
