package computerblocks.display;

public class Color {
  public static final Color BACKGROUND = new Color("#e6e6e6");
  public static final Color EMPTY = new Color("#D7D7D7");
  public static final Color CABLE_OFF = new Color("#b4b4b4");
  public static final Color SOURCE = new Color("#F2E24F");
  public static final Color INVERTER_OFF = new Color("#ce4e4a");
  public static final Color DELAY_OFF = new Color("#59C664");
  public static final Color VIA_OFF = new Color("#589EC9");

  public static final Color UI_BORDER = new Color(124f, 124f, 124f);

  public java.awt.Color data;

  public Color(String colorString) {
    data = java.awt.Color.decode(colorString);
  }

  public Color(String colorString, float alpha) {
    java.awt.Color temp = java.awt.Color.decode(colorString);
    data = new java.awt.Color(temp.getRed()/255, temp.getGreen()/255, temp.getBlue()/255, alpha);
  }

  public Color(float r, float g, float b, float a) {
    data = new java.awt.Color(r/255, g/255, b/255, a);
  }

  public Color(float r, float g, float b) {
    data = new java.awt.Color(r/255, g/255, b/255);
  }

  public Color(java.awt.Color color) {
    data = color;
  }

}
