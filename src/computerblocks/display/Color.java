package computerblocks.display;

public class Color {
  public java.awt.Color data;

  public Color(String colorString) {
    data = java.awt.Color.decode(colorString);
  }

  public Color(java.awt.Color color) {
    data = color;
  }
}