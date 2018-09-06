package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;

import javax.imageio.ImageIO;
import java.io.File;

public class Menu {
  private String title;
  private int width, height;
  private int border = 8;
  // private ArrayList<Button> buttons;

  private BufferedImage logo;

  public Menu(float width, float height) {
    this.width = (int) width;
    this.height = (int) height;

    logo = ImageIO.read(new File("computerblocks/assets/logo.png"));
  }

  public void draw(Display display) {
    display.color(new Color(0f, 0f, 0f, 0.4f));
    display.rect(0, 0, display.width, display.height);

    display.color(new Color(124f, 124f, 124f));
    display.rect(display.width/2 - (width+border)/2, display.height/2 - (height+border)/2, width+border, height+border);
    display.color(Color.BACKGROUND);
    display.rect(display.width/2 - width/2, display.height/2 - height/2, width, height);

    display.image(image, x, y, width, height);
  }

  public void addButton(Button button) {

  }

  public void addTextField(TextField textField) {

  }

  public void addLabel(Label label) {

  }
}
