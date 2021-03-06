package computerblocks.display;

public class Color {
  public static final Color BACKGROUND = new Color("#e6e6e6");

  public static final Color EMPTY = new Color("#D7D7D7");
  public static final Color CABLE = new Color("#b4b4b4");
  public static final Color CABLE_CHARGE = new Color("#dbd44e");
  public static final Color SOURCE = new Color("#F2E24F");
  public static final Color INVERTER = new Color("#ce4e4a");
  public static final Color INVERTER_CHARGE = new Color("#f95e59");
  public static final Color DELAY = new Color("#59C664");
  public static final Color DELAY_CHARGE = new Color("#62db6e");
  public static final Color VIA = new Color("#589EC9");
  public static final Color VIA_CHARGE = new Color("#75bdea");
  public static final Color HIGHLIGHT = new Color("#31c831");
  public static final Color LABEL = new Color("#bb578a");

  public static final Color UI_BACKGROUND = new Color("#e0e0e0");
  public static final Color UI_BORDER = new Color("#7c7c7c");

  public java.awt.Color data;

  public Color(String colorString) {
    data = java.awt.Color.decode(colorString);
  }

  public Color(String colorString, float alpha) {
    java.awt.Color temp = java.awt.Color.decode(colorString);
    data = new java.awt.Color(temp.getRed() / 255f, temp.getGreen() / 255f, temp.getBlue() / 255f, alpha);
  }

  public Color(Color color, float alpha) {
    data = new java.awt.Color(color.data.getRed() / 255f, color.data.getGreen() / 255f, color.data.getBlue() / 255f, alpha);
  }

  public Color(float r, float g, float b, float a) {
    data = new java.awt.Color(r / 255, g / 255, b / 255, a);
  }

  public Color(float r, float g, float b) {
    data = new java.awt.Color(r / 255, g / 255, b / 255);
  }

  public Color(java.awt.Color color) {
    data = color;
  }

}
