package computerblocks.player.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import computerblocks.display.Display;

public class Keyboard implements KeyListener {
  // public static final int

  private boolean[] keys;

  public Keyboard(Display display) {
    display.frame.addKeyListener(this);
    keys = new boolean[256];
  }

  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
  }

  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }

  public boolean held(int code) {
    return keys[code];
  }

  public boolean down(int code) {
    if (keys[code]) {
      keys[code] = false;
      return true;
    }
    return false;
  }

  public void keyTyped(KeyEvent e) {}
}
