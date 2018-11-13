package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.snippet.*;
import computerblocks.Grid;

public class Button extends MenuElement {

  public String text;
  public int textSize = 25;

  public Color color = null;
  public String font = Fonts.pixelmix;

  public Button(String text) {
    super(0, 0, 1);
    this.text = text;
  }

  public Button(String text, double yOffset) {
    super(0, yOffset, 1);
    this.text = text;
  }

  public Button(String text, double xOffset, double yOffset) {
    super(xOffset, yOffset, 1);
    this.text = text;
  }

  public Button(String text, double xOffset, double yOffset, double widthModifier) {
    super(xOffset, yOffset, widthModifier);
    this.text = text;
  }

  public Button(String text, double xOffset, double yOffset, double widthModifier, int ID) {
    super(xOffset, yOffset, widthModifier);
    this.text = text;
    this.ID = ID;
  }

  public void draw(Display display, Menu menu) {
    height = (menu.height - menu.width * 0.18f) / 6;
    width = menu.width * 0.9f * widthModifier;
    x = display.width / 2 - width / 2 + width * xOffset;
    y = display.height / 2 - menu.height / 2 + menu.width * 0.18f + (height + buttonSpacing) * (slot + yOffset);

    display.color(Color.UI_BORDER);
    display.rect(x, y + 4, width, height);
    display.color(Color.CABLE);
    display.rect(x, y, width, height);

    display.color(Color.UI_BORDER);
    display.font(font, textSize);
    display.text(text,  x + width/2 - display.getStringWidth(text, font, textSize) / 2,
                        y + height / 2 + display.getFontHeight(font, textSize) / 2);
  }

  public void buttonPress(Player player, MenuController menuController, Grid grid) {
    switch (text) {
      case "Exit":      System.exit(0); break;
      case "Continue":  player.state = State.GAME;
                        player.placeTime = 0;
                        break;
      case "Saves":     menuController.currentMenu = menuController.saveMenu; break;
      case "Settings":  menuController.currentMenu = menuController.settingsMenu; break;
      case "Credits":   menuController.currentMenu = menuController.creditsMenu; break;
      case "Save":      for (MenuElement i : menuController.saveMenu.elements) {
                          if (i.getClass().getName() == "computerblocks.display.ui.menu.elements.TextField") {
                            if (i.ID == this.ID) {
                              if (i.text != "") {
                                new Snippet(grid).saveToFile("../saves/", i.text);
                              } else {
                                new Snippet(grid).saveToFile("../saves/", "save");
                                // new Snippet(grid).saveToFile("../saves/", i.defaultText);
                              }
                            }
                          }
                        }
                        player.state = State.GAME;
                        player.placeTime = 0;
                        break;
      case "Load":      grid.fromSnippet(new Snippet("../saves/", "save_" + ID));
                        player.state = State.GAME;
                        player.placeTime = 0;
                        break;
    }
  }
}
