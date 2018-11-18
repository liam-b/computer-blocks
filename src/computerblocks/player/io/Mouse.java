package computerblocks.player.io;

import java.util.HashMap;

import computerblocks.position.RealPosition;

enum ButtonState {
  NONE, PRE_PRESSED, PRESSED, HELD, PRE_RELEASED, RELEASED;
}

public class Mouse {
  public static final int LEFT = 0;
  public static final int RIGHT = 1;

  ButtonState[] buttons;
  RealPosition position;

  public Mouse() {
    buttons = new ButtonState[8];
    position = new RealPosition(0, 0);
  }

  public void update() {
    for (int button = 0; button < buttons.length; button += 1) {
      if (buttons[button] == ButtonState.PRESSED) buttons[button] = ButtonState.HELD;
      if (buttons[button] == ButtonState.PRE_PRESSED) buttons[button] = ButtonState.PRESSED;
      if (buttons[button] == ButtonState.RELEASED) buttons[button] = ButtonState.NONE;
      if (buttons[button] == ButtonState.PRE_RELEASED) buttons[button] = ButtonState.RELEASED;
    }
  }

  public void buttonCallback(long window, int button, int action, int mods) {
    if (button >= 0 && button < buttons.length) {
      if (action == 1) buttons[button] = ButtonState.PRE_PRESSED;
      if (action == 0) buttons[button] = ButtonState.PRE_RELEASED;
    }
  }

  public void positionCallback(long window, double x, double y) {
    position.set(x, y);
  }

  public boolean down(int button) {
    return buttons[button] == ButtonState.PRESSED;
  }

  public boolean held(int button) {
    return buttons[button] == ButtonState.HELD || buttons[button] == ButtonState.PRE_RELEASED;
  }

  public boolean up(int button) {
    return buttons[button] == ButtonState.RELEASED;
  }
}