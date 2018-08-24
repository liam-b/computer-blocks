package computerblocks.display;

public class Color {
  public static final Color BACKGROUND = new Color("#e6e6e6");

  public java.awt.Color data;

  public Color(String colorString) {
    data = java.awt.Color.decode(colorString);
  }

  public Color(java.awt.Color color) {
    data = color;
  }
}
