package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;

public class MenuElement {

  public String text;
  public float x, y, width, height, yOffset = 0, xOffset = 0, widthModifier = 1;
  public int slot, textSize = 25, ID = 0;

  public Color color = null;
  public String font = Fonts.pixelmix;
  public int buttonSpacing = 8;

  public MenuElement(float xOffset, float yOffset, float widthModifier) {
    this.yOffset = yOffset;
    this.xOffset = xOffset;
    this.widthModifier = widthModifier;
  }

  public boolean pointOver(float x, float y) {
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

  public void draw(Display display, Menu menu) {}
  public void buttonPress(Player player, MenuController menuController) {}
}
