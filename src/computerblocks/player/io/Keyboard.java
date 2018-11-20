package computerblocks.player.io;

import static org.lwjgl.glfw.GLFW.*;

import computerblocks.display.Display;

enum KeyState {
  NONE, PRE_PRESSED, PRESSED, HELD, PRE_RELEASED, RELEASED;
}

public class Keyboard {
  KeyState[] keys;

  public Keyboard(Display display) {
    keys = new KeyState[284];

    glfwSetKeyCallback(display.window, this::callback);
  }

  public void update() {
    for (int key = 0; key < keys.length; key += 1) {
      if (keys[key] == KeyState.PRESSED) keys[key] = KeyState.HELD;
      if (keys[key] == KeyState.PRE_PRESSED) keys[key] = KeyState.PRESSED;
      if (keys[key] == KeyState.RELEASED) keys[key] = KeyState.NONE;
      if (keys[key] == KeyState.PRE_RELEASED) keys[key] = KeyState.RELEASED;
    }
  }

  public void callback(long window, int key, int scancode, int action, int mods) {
    if (key >= 0 && key < keys.length) {
      if (action == 1) keys[key] = KeyState.PRE_PRESSED;
      if (action == 0) keys[key] = KeyState.PRE_RELEASED;
    }
  }

  public boolean pressed(int key) {
    return keys[key] == KeyState.PRESSED;
  }

  public boolean held(int key) {
    return keys[key] == KeyState.HELD || keys[key] == KeyState.PRE_RELEASED;
  }

  public boolean released(int key) {
    return keys[key] == KeyState.RELEASED;
  }
}