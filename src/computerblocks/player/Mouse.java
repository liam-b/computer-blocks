package computerblocks.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import computerblocks.position.*;
import java.awt.Point;

import computerblocks.display.Display;

public class Mouse implements MouseListener {
  public boolean onScreen;
  public boolean right, left;
  public RealPosition position;

  public MouseManager(Display display) {
    display.canvas.addMouseListener(this);
  }

  public void update(Display display) {
    Point mousePosition = display.canvas.getMousePosition();
    onScreen = mousePosition != null;
    if (onScreen) {
      position = new RealPosition(mousePosition);
    }
  }

  public void mousePressed(MouseEvent e) {
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