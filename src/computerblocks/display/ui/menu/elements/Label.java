package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;

public class Label extends Button {
  public Label(String text) {
    super(text);
  }

  public Label(String text, Color color) {
    super(text);
    this.color = color;
  }

  public Label(String text, Color color, float offset) {
    super(text);
    this.color = color;
    this.offset = offset;
  }

  public void draw(Display display, Menu menu) {
    height = (menu.height - menu.width * 0.18f) / 6;
    width = menu.width * 0.9f;
    x = display.width / 2 - width / 2;
    y = display.height / 2 - menu.height / 2 + menu.width * 0.18f + (height + buttonSpacing) * (slot + offset);

    if (color == null) display.color(Color.UI_BORDER);
    else display.color(color);
    display.font(font, textSize);
    display.text(text,  x + width/2 - display.getStringWidth(text, font, textSize) / 2,
                        y + height / 2 + display.getFontHeight(font, textSize) / 2);
  }
}
