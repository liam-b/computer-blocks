package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;

public class Label extends MenuElement {

  public Label(String text, Color color) {
    super(0, 0, 1);
    this.color = color;
    this.text = text;
  }

  public Label(String text, Color color, float xOffset, float yOffset) {
    super(xOffset, yOffset, 1);
    this.color = color;
    this.text = text;
  }

  public Label(String text, Color color, float xOffset, float yOffset, float widthModifier) {
    super(xOffset, yOffset, widthModifier);
    this.color = color;
    this.text = text;
  }

  public void draw(Display display, Menu menu) {
    height = (menu.height - menu.width * 0.18f) / 6;
    width = menu.width * 0.9f * widthModifier;
    x = display.width / 2 - width / 2 + width * xOffset;
    y = display.height / 2 - menu.height / 2 + menu.width * 0.18f + (height + buttonSpacing) * (slot + yOffset);

    if (color == null) display.color(Color.UI_BORDER);
    else display.color(color);
    display.font(font, textSize);
    display.text(text,  x + width/2 - display.getStringWidth(text, font, textSize) / 2,
                        y + height / 2 + display.getFontHeight(font, textSize) / 2);
  }
}
