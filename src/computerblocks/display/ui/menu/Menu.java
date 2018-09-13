package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.*;
import java.awt.Image;
import java.io.IOException;

public class Menu {
  private String title;
  public int width, height;
  private int border = 8;
  public ArrayList<MenuElement> elements;

  // private MenuElement focusedField;

  private Image logo;

  public Menu(double width, double height, String header) {
    this.width = (int) width;
    this.height = (int) height;
    elements = new ArrayList<MenuElement>();

    if (header != null) loadImages(header);
  }

  public void loadImages(String header) {
    try {
      logo = ImageIO.read(new File("../assets/" + header));
    } catch (IOException err) {
      err.printStackTrace();
    }
  }

  public void draw(Display display, Player player, MenuController menuController) {
    // darken game
    display.color(new Color(0f, 0f, 0f, 0.4f));
    display.rect(0, 0, display.width, display.height);

    // draw background box
    display.color(Color.UI_BORDER);
    display.rect(display.width/2 - (width+border)/2, display.height/2 - (height+border)/2, width+border, height+border);
    display.color(Color.BACKGROUND);
    display.rect(display.width/2 - width/2, display.height/2 - height/2, width, height);

    // draw title logo
    display.image(logo, display.width/2 - width/2, display.height/2 - height/2, width, width*0.18f);

    for (MenuElement element : elements) {
      element.draw(display, this);
      element.checkPress(display, player, menuController);
    }
  }

  public void addElement(MenuElement element) {
    elements.add(element);
    element.slot = elements.size() - 1;
  }
  public void addElement(Button element) {
    elements.add(element);
    element.slot = elements.size() - 1;
  }

  public void addTextField(TextField textField) {

  }

  public void addLabel(Label label) {

  }
}
