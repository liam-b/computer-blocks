package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;

public class SnippetButton {

  public String text = "";
  public double x, y, width, height;
  public double maxY, minY;
  public int slot, ID = 0;

  public SnippetButton(Display display, SnippetTray snippetTray, String text) {
    height = snippetTray.height / 10;
    width = snippetTray.width * 0.8;
    minY = display.height / 2 - snippetTray.height / 2;
    maxY = display.height / 2 + snippetTray.height / 2 - height;
    y = minY + (height * 1.2) * (snippetTray.snippets.size() + 1);
    this.text = text;
  }

  public void draw(Display display, SnippetTray snippetTray) {
    x = display.width - snippetTray.width / 2 + snippetTray.animX() - width / 2;

    // middle
    if (y + snippetTray.scroll < maxY - height && y + snippetTray.scroll > minY + height) {
      display.color(Color.UI_BORDER);
      display.rect(x, y + 4 + snippetTray.scroll, width, height);
      display.color(Color.CABLE);
      display.rect(x, y + snippetTray.scroll, width, height);

    // top
    } else if (y + snippetTray.scroll < minY + height && y + snippetTray.scroll > minY) {
      display.color(new Color(Color.UI_BORDER, (float) (1 / height * (y + snippetTray.scroll - minY))));
      display.rect(x, y + 4 + snippetTray.scroll, width, height);
      display.color(new Color(Color.CABLE, (float) (1 / height * (y + snippetTray.scroll - minY))));
      display.rect(x, y + snippetTray.scroll, width, height);

    // bottom
    } else if (y + snippetTray.scroll > maxY - height && y + snippetTray.scroll < maxY) {
      display.color(new Color(Color.UI_BORDER, (float) (1 / height * (-(y + snippetTray.scroll) + maxY))));
      display.rect(x, y + 4 + snippetTray.scroll, width, height);
      display.color(new Color(Color.CABLE, (float) (1 / height * (-(y + snippetTray.scroll) + maxY))));
      display.rect(x, y + snippetTray.scroll, width, height);
    }

    // display.color(Color.INVERTER_CHARGE);
    // display.rect(x, maxY, width, height);
    // display.rect(x, minY, width, height);
  }
}
