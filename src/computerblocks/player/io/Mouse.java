package computerblocks.player.io;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import computerblocks.position.*;
import computerblocks.player.Player;
import java.awt.Point;

import computerblocks.display.Display;

public class Mouse implements MouseListener {
  public static final int RIGHT = MouseEvent.BUTTON3;
  public static final int LEFT = MouseEvent.BUTTON1;

  public boolean onScreen;
  public RealPosition position = new RealPosition();

  private boolean[] held;
  private boolean[] down;
  private boolean[] buffer;
  private boolean[] up;

  public Mouse(Display display) {
    display.canvas.addMouseListener(this);

    this.held = new boolean[10];
    this.down = new boolean[10];
    this.buffer = new boolean[10];
    this.up = new boolean[10];
  }

  public void update(Display display) {
    Point mousePosition = display.canvas.getMousePosition();
    onScreen = mousePosition != null;
    if (onScreen) position = new RealPosition(mousePosition);

    for (int i = 0; i < held.length; i++) {
      down[i] = false;
      up[i] = false;
      if (buffer[i]) {
        down[i] = true;
        buffer[i] = false;
      }
    };
  }

  public boolean held(int key) {
    return held[key] && !down[key] && !up[key];
  }

  public boolean down(int key) {
    return down[key];
  }

  public boolean up(int key) {
    return up[key];
  }

  public void mousePressed(MouseEvent e) {
    int button = e.getButton();
    held[button] = true;
    // down[button] = true;
    buffer[button] = true;
    up[button] = false;
  }

  public void mouseReleased(MouseEvent e) {
    int button = e.getButton();
    held[button] = false;
    down[button] = false;
    up[button] = true;
  }

  public void mouseExited(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {}
}