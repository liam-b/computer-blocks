package computerblocks.display;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

public class Fonts {

  public static final String paloseco = "Paloseco Medium";
  public static final String roboto = "Roboto";
  public static final String superscr = "superscript";
  public static final String pixelmix = "pixelmix";

  private static ArrayList<Fonts> fontList = new ArrayList<Fonts>();

  public Fonts(String filePath) {
    String fontPath = "../assets/fonts/" + filePath;
    registerFont(fontPath);
  }

  private void registerFont(String fontPath) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    try {
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static void addFont(Fonts font) {
    fontList.add(font);
  }
}
