package computerblocks.display;

import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.awt.*;

public class Display {
  public static int PRIMARY = 0;

  private String title;
  public int width, height, screen;

  private JFrame frame;
  private Canvas canvas;
  private BufferStrategy strategy;
  private Graphics graphics;

  public Display(String title, int width, int height, int screen) {
    this.title = title;
    this.width = width;
    this.height = height;
    this.screen = screen;

    createDisplay(screen);
  }

  private void createDisplay(int screen) {
    frame = new JFrame(title);
    frame.setSize(width, height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    showOnScreen(frame, screen);

    canvas = new Canvas();
    canvas.setPreferredSize(new Dimension(width, height));
    canvas.setMaximumSize(new Dimension(width, height));
    canvas.setMinimumSize(new Dimension(width, height));
    canvas.setFocusable(false);
    frame.add(canvas);
    frame.pack();

    canvas.createBufferStrategy(3);
    strategy = canvas.getBufferStrategy();
    graphics = strategy.getDrawGraphics();
  }

  public void showOnScreen(JFrame frame, int screen) {
    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] screens = environment.getScreenDevices();

    if (screen > -1 && screen < screens.length) frame.setLocation(screens[screen].getDefaultConfiguration().getBounds().x, frame.getY());
    else if (screens.length > 0) frame.setLocation(screens[0].getDefaultConfiguration().getBounds().x, frame.getY());
    else throw new RuntimeException("No Screens Found");
  }

  public void reset() {
    graphics = strategy.getDrawGraphics();

    graphics.clearRect(0, 0, width, height);
    graphics.setColor(new Color("#e6e6e6").data);
    graphics.fillRect(0, 0, width, height);
  }

  public void draw() {
    strategy.show();
    graphics.dispose();
  }

  public void color(Color color) {
    graphics.setColor(color.data);
  }

  public void rect(int x, int y, int width, int height) {
    graphics.fillRect(x, y, width, height);
  }
}
