package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;

public class Button {

  private String text;
  private float x, y, width, height;
  public int slot;

  private int buttonSpacing = 8;

  public Button(String text) {
    this.text = text;
  }

  public void draw(Display display, Menu menu) {
    height = (menu.height - menu.width * 0.18f) / 6;
    width = menu.width * 0.9f;
    x = display.width / 2 - width / 2;
    y = display.height / 2 - menu.height / 2 + menu.width * 0.18f + (height + buttonSpacing) * slot;

    display.color(Color.UI_BORDER);
    display.rect(x, y + 4, width, height);
    display.color(Color.CABLE_OFF);
    display.rect(x, y, width, height);
  }

  private boolean pointOver(float x, float y) {
    if (x > this.x && x < this.x + width &&
        y > this.y && y < this.y + height) {
      return true;
    }
    return false;
  }

  public void checkPress(Display display, Player player, MenuController menuController) {
    if (pointOver(player.mouse.position.x, player.mouse.position.y)) {
      if (player.mouse.left) {
        buttonPress(player, menuController);
      }
      display.color(new Color(255, 255, 255, 0.2f));
      display.rect(x, y, width, height);
    }
  }

  private void buttonPress(Player player, MenuController menuController) {
    menuController.currentMenu = menuController.settingsMenu;
  }
}
