package computerblocks.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;

import computerblocks.display.Display;

public class MouseManager implements MouseListener {
  public boolean onScreen;
  public boolean right, left;
  public int x, y;

  public MouseManager(Display display) {
    display.frame.addMouseListener(this);
  }

  public void update(Display display) {
    Point mousePosition = display.canvas.getMousePosition();
    onScreen = mousePosition != null;
    if (onScreen) {
      x = mousePosition.x;
      y = mousePosition.y;
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println("iuhiuhiuhoih");
    if (e.getButton() == MouseEvent.BUTTON1) left = true;
    if (e.getButton() == MouseEvent.BUTTON2) right = true;
  }

  public void mouseReleased(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) left = false;
    if (e.getButton() == MouseEvent.BUTTON2) right = false;
  }

  public void mouseExited(MouseEvent e) {}

  public void mouseEntered(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {}
}