package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.snippet.*;
import computerblocks.Grid;

public class TextField extends MenuElement {

  public String defaultText;

  public TextField(String text, Color color) {
    super(0, 0, 1);
    this.color = color;
    this.defaultText = text;
  }

  public TextField(String text, Color color, double xOffset, double yOffset) {
    super(xOffset, yOffset, 1);
    this.color = color;
    this.defaultText = text;
  }

  public TextField(String text, Color color, double xOffset, double yOffset, double widthModifier) {
    super(xOffset, yOffset, widthModifier);
    this.color = color;
    this.defaultText = text;
  }

  public void draw(Display display, Menu menu) {
    height = (menu.height - menu.width * 0.18f) / 6;
    width = menu.width * 0.9f * widthModifier;
    x = display.width / 2 - width / 2 + width * xOffset;
    y = display.height / 2 - menu.height / 2 + menu.width * 0.18f + (height + buttonSpacing) * (slot + yOffset);

    if (color == null) display.color(Color.UI_BORDER);
    else display.color(color);
    display.font(font, textSize);
    if (text != "") {
      display.text(text,  x + width/2 - display.getStringWidth(text, font, textSize) / 2,
      y + height / 2 + display.getFontHeight(font, textSize) / 2);
    } else {
      display.text(defaultText,  x + width/2 - display.getStringWidth(defaultText, font, textSize) / 2,
      y + height / 2 + display.getFontHeight(font, textSize) / 2);
    }
    display.color(color);
    display.outline(x, y, width, height);

    if (menu.focusedField == this) {
      display.color(new Color(0f, 0f, 0f, 0.1f));
      display.rect(x, y, width, height);
    }
  }

  public void buttonPress(Player player, MenuController menuController, Grid grid) {
    menuController.currentMenu.focusedField = this;
  }
}
