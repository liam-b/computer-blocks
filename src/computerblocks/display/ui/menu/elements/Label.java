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

  public void draw(Display display, Menu menu) {
    height = (menu.height - menu.width * 0.18f) / 6;
    width = menu.width * 0.9f;
    x = display.width / 2 - width / 2;
    y = display.height / 2 - menu.height / 2 + menu.width * 0.18f + (height + buttonSpacing) * slot;

    display.color(Color.UI_BORDER);
    display.font(font, textSize);
    display.text(text,  x + width/2 - display.getStringWidth(text, font, textSize) / 2,
                        y + height / 2 + display.getFontHeight(font, textSize) / 2);
  }
}
