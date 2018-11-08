package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;
import computerblocks.snippet.*;

public class SnippetButton {

  public String text = "";
  public double x, y, width, height;
  public double maxY, minY;
  public int slot, ID = 0;

  private String font = Fonts.pixelmix;
  private int textSize = 10;

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
    if (y + snippetTray.scroll <= maxY - height && y + snippetTray.scroll >= minY + height) {
      display.color(Color.UI_BORDER);
      display.rect(x, y + 4 + snippetTray.scroll, width, height);
      display.color(Color.CABLE);
      display.rect(x, y + snippetTray.scroll, width, height);
      drawText(display, snippetTray, 1f);

    // top
    } else if (y + snippetTray.scroll < minY + height && y + snippetTray.scroll > minY) {
      display.color(new Color(Color.UI_BORDER, (float) (1 / height * (y + snippetTray.scroll - minY))));
      display.rect(x, y + 4 + snippetTray.scroll, width, height);
      display.color(new Color(Color.CABLE, (float) (1 / height * (y + snippetTray.scroll - minY))));
      display.rect(x, y + snippetTray.scroll, width, height);
      drawText(display, snippetTray, (float) (1 / height * (y + snippetTray.scroll - minY)));

    // bottom
    } else if (y + snippetTray.scroll > maxY - height && y + snippetTray.scroll < maxY) {
      display.color(new Color(Color.UI_BORDER, (float) (1 / height * (-(y + snippetTray.scroll) + maxY))));
      display.rect(x, y + 4 + snippetTray.scroll, width, height);
      display.color(new Color(Color.CABLE, (float) (1 / height * (-(y + snippetTray.scroll) + maxY))));
      display.rect(x, y + snippetTray.scroll, width, height);
      drawText(display, snippetTray, (float) (1 / height * (-(y + snippetTray.scroll) + maxY)));
    }

    // display.color(Color.INVERTER_CHARGE);
    // display.rect(x, maxY, width, height);
    // display.rect(x, minY, width, height);
  }

  private void drawText(Display display, SnippetTray snippetTray, float alpha) {
    display.color(new Color(Color.UI_BORDER, alpha));
    display.font(font, textSize);
    display.text(text,  x + width/2 - display.getStringWidth(text, font, textSize) / 2,
                        y + height / 2 + display.getFontHeight(font, textSize) / 2 + snippetTray.scroll);
  }

  public boolean pointOver(double x, double y) {
    if (x > this.x && x < this.x + width &&
        y > this.y && y < this.y + height) {
      return true;
    }
    return false;
  }

  public void checkPress(Display display, Player player, SnippetTray snippetTray) {
    if (pointOver(player.mouse.position.x, player.mouse.position.y - snippetTray.scroll)) {
      if (y + snippetTray.scroll < maxY - height && y + snippetTray.scroll > minY + height) {
        if (player.mouse.down(Mouse.LEFT)) {
          buttonPress(player, snippetTray);
        }
        display.color(new Color(255, 255, 255, 0.2f));
        display.rect(x, y + snippetTray.scroll, width, height);
      }
    }
  }

  public void buttonPress(Player player, SnippetTray snippetTray) {
    player.state = State.PASTE;
    player.snippet = new Snippet("../saves/snippets/", text);
    player.snipTime = 0;
  }
}
