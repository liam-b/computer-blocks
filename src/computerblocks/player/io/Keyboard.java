package computerblocks.player.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import computerblocks.display.Display;

public class Keyboard implements KeyListener {
  public static final int SHIFT = 16;
  public static final int ESC = 27;

  public boolean[] keys;

  public Keyboard(Display display) {
    display.frame.addKeyListener(this);
    keys = new boolean[256];
  }

  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    if (code <= 256) keys[code] = true;
  }

  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if (code <= 256) keys[code] = false;
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

  public String keyStream(String textThing) {
    String result = textThing;
    for (int i = 0; i < keys.length; i++) {
      if (i != 27) {
        if (down(i)) {
          if (i == 8) {
            if (result.length() > 0) result = result.substring(0, result.length() - 1);
          } else if ((i >= 65 && i <= 90) || (i >= 48 && i <= 57) || i == 32 || i == 45){
            result += (char) i;
          }
        }
      }
    }
    return result;
  }
}
