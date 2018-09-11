package computerblocks.display;

import computerblocks.position.*;
import computerblocks.player.io.Keyboard;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.awt.*;
import java.awt.FontMetrics;

import java.awt.geom.*;

public class Display {
  public static int PRIMARY = 0;

  private String title;
  public int width, height, screen;

  public JFrame frame;
  public Canvas canvas;
  private BufferStrategy strategy;
  private Graphics2D graphics;

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
    graphics = (Graphics2D)strategy.getDrawGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    // graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

  }

  public void showOnScreen(JFrame frame, int screen) {
    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] screens = environment.getScreenDevices();

    if (screen > -1 && screen < screens.length) frame.setLocation(screens[screen].getDefaultConfiguration().getBounds().x, frame.getY());
    else if (screens.length > 0) frame.setLocation(screens[0].getDefaultConfiguration().getBounds().x, frame.getY());
    else throw new RuntimeException("No Screens Found");
  }

  public void reset(Color backgroundColor) {
    graphics = (Graphics2D)strategy.getDrawGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    // graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    graphics.clearRect(0, 0, width, height);
    graphics.setColor(backgroundColor.data);
    graphics.fillRect(0, 0, width, height);
  }

  public void draw() {
    strategy.show();
    graphics.dispose();
  }

  public void color(Color color) {
    graphics.setColor(color.data);
  }

  public void rect(float x, float y, float width, float height) {
    graphics.fill(new Rectangle2D.Float(x, y, width, height));
  }

  public void rect(RealPosition position, float width, float height) {
    graphics.fill(new Rectangle2D.Float(position.x, position.y, width, height));
  }

  public void image(Image image, float x, float y, float width, float height) {
    graphics.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
  }

  public void text(String text, float x, float y) {
    graphics.drawString(text, (int) x, (int) y);
  }

  public void font(String font, int size) {
    graphics.setFont(new Font(font, Font.PLAIN, size));
  }

  public int getFontHeight(String font, int size) {
    FontMetrics metrics = graphics.getFontMetrics(new Font(font, Font.PLAIN, size));
    return metrics.getHeight();
  }

  public int getStringWidth(String text, String font, int size) {
    FontMetrics metrics = graphics.getFontMetrics(new Font(font, Font.PLAIN, size));
    return metrics.stringWidth(text);
  }

}
