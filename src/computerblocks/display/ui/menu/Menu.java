package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.*;
import java.awt.Image;
import java.io.IOException;

public class Menu {
  private String title;
  private int width, height;
  private int border = 8;
  // private ArrayList<Button> buttons;

  private Image logo;

  public Menu(float width, float height) {
    this.width = (int) width;
    this.height = (int) height;

    try {
      logo = ImageIO.read(new File("../assets/logo.png"));
    } catch (IOException err) {
      err.printStackTrace();
    }
  }

  public void draw(Display display) {
    display.color(new Color(0f, 0f, 0f, 0.4f));
    display.rect(0, 0, display.width, display.height);

    display.color(new Color(124f, 124f, 124f));
    display.rect(display.width/2 - (width+border)/2, display.height/2 - (height+border)/2, width+border, height+border);
    display.color(Color.BACKGROUND);
    display.rect(display.width/2 - width/2, display.height/2 - height/2, width, height);

    display.image(logo, display.width/2 - width/2, display.height/2 - height/2, width, width*0.18f);
  }

  public void addButton(Button button) {

  }

  public void addTextField(TextField textField) {

  }

  public void addLabel(Label label) {

  }
}
