package computerblocks.display;

public class Color {
  public static final Color BACKGROUND = new Color("#e6e6e6");

  public static final Color EMPTY = new Color("#d7d7d7");
  public static final Color CABLE = new Color("#b4b4b4");
  public static final Color CABLE_CHARGE = new Color("#dbd44e");
  public static final Color SOURCE = new Color("#f2e24f");
  public static final Color INVERTER = new Color("#ce4e4a");
  public static final Color INVERTER_CHARGE = new Color("#f95e59");
  public static final Color DELAY = new Color("#59c664");
  public static final Color DELAY_CHARGE = new Color("#62db6e");
  public static final Color VIA = new Color("#589ec9");
  public static final Color VIA_CHARGE = new Color("#75bdea");
  public static final Color HIGHLIGHT = new Color("#31c831");
  public static final Color LABEL = new Color("#bb578a");

  public static final Color UI_BACKGROUND = new Color("#e0e0e0");
  public static final Color UI_BORDER = new Color("#7c7c7c");

  public int r = 0;
  public int g = 0;
  public int b = 0;
  public int a = 0;

  public Color(String hex) {
    r = Integer.valueOf(hex.substring(1, 3), 16);
    g = Integer.valueOf(hex.substring(3, 5), 16);
    b = Integer.valueOf(hex.substring(5, 7), 16);
  }

  public Color(String hex, int alpha) {
    this.a = alpha;
    r = Integer.valueOf(hex.substring(1, 3), 16);
    g = Integer.valueOf(hex.substring(3, 5), 16);
    b = Integer.valueOf(hex.substring(5, 7), 16);
  }

  public Color(Color color, int alpha) {
    this.a = alpha;
    r = color.r;
    g = color.g;
    b = color.b;
  }

  public Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
}
