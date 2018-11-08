package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;
import computerblocks.snippet.*;

import java.io.*;

public class SaveButton {

  public String text = "";
  public double x, y, width, height;
  public double maxY, minY;
  public int slot, ID = 0;

  private String font = Fonts.pixelmix;
  private int textSize = 20;
  private int bottomMargin = 3;

  public SaveButton(Display display, SaveMenu saveMenu, String text) {
    height = saveMenu.height / 10;
    width = saveMenu.width * 0.9;
    minY = display.height / 2 - saveMenu.height / 2;
    maxY = display.height / 2 + saveMenu.height / 2 - height - height * bottomMargin;
    y = minY + (height * 1.2) * (saveMenu.saves.size() + 1) - height / 2;
    x = display.width / 2 - width / 2;
    this.text = text;
  }

  public void draw(Display display, SaveMenu saveMenu) {

    // middle
    if (y + saveMenu.scroll <= maxY - height && y + saveMenu.scroll >= minY + height) {
      display.color(Color.UI_BORDER);
      display.rect(x, y + 4 + saveMenu.scroll, width, height);
      display.color(Color.CABLE);
      display.rect(x, y + saveMenu.scroll, width, height);
      drawText(display, saveMenu, 1f);

    // top
    } else if (y + saveMenu.scroll < minY + height && y + saveMenu.scroll > minY) {
      display.color(new Color(Color.UI_BORDER, (float) (1 / height * (y + saveMenu.scroll - minY))));
      display.rect(x, y + 4 + saveMenu.scroll, width, height);
      display.color(new Color(Color.CABLE, (float) (1 / height * (y + saveMenu.scroll - minY))));
      display.rect(x, y + saveMenu.scroll, width, height);
      drawText(display, saveMenu, (float) (1 / height * (y + saveMenu.scroll - minY)));

    // bottom
    } else if (y + saveMenu.scroll > maxY - height && y + saveMenu.scroll < maxY) {
      display.color(new Color(Color.UI_BORDER, (float) (1 / height * (-(y + saveMenu.scroll) + maxY))));
      display.rect(x, y + 4 + saveMenu.scroll, width, height);
      display.color(new Color(Color.CABLE, (float) (1 / height * (-(y + saveMenu.scroll) + maxY))));
      display.rect(x, y + saveMenu.scroll, width, height);
      drawText(display, saveMenu, (float) (1 / height * (-(y + saveMenu.scroll) + maxY)));
    }

    // display.color(Color.INVERTER_CHARGE);
    // display.rect(x, maxY, width, height);
    // display.rect(x, minY, width, height);
  }

  private void drawText(Display display, SaveMenu saveMenu, float alpha) {
    display.color(new Color(Color.UI_BORDER, alpha));
    display.font(font, textSize);
    display.text(text,  x + width/2 - display.getStringWidth(text, font, textSize) / 2,
                        y + height / 2 + display.getFontHeight(font, textSize) / 2 + saveMenu.scroll);
  }

  public boolean pointOver(double x, double y) {
    if (x > this.x && x < this.x + width &&
        y > this.y && y < this.y + height) {
      return true;
    }
    return false;
  }

  public void checkPress(Display display, Player player, SaveMenu saveMenu, Grid grid) {
    if (pointOver(player.mouse.position.x, player.mouse.position.y - saveMenu.scroll)) {
      if (y + saveMenu.scroll < maxY && y + saveMenu.scroll > minY) {
        if (player.mouse.down(Mouse.LEFT)) {
          buttonPress(player, saveMenu, grid);
        }
        if (player.mouse.down(Mouse.RIGHT)) {
          while (true) {
            if (player.keyboard.down(Keyboard.ESC)) break;

            display.reset(Color.BACKGROUND);
            display.color(Color.INVERTER);
            display.font(font, textSize);
            display.text("'ENTER' to DELETE save or 'ESC' to CANCEL",  (display.width / 2) - display.getStringWidth("'ENTER' to DELETE save or 'ESC' to CANCEL", font, textSize) / 2, (display.height / 2) + display.getFontHeight(font, textSize) / 2);
            display.draw();

            if (player.keyboard.enterDown) {
              saveMenu.savesRemovalQueue.add(this);
              new File("../saves/grids/" + text + ".snip").delete();
              break;
            }
          }
        }
        display.color(new Color(255, 255, 255, 0.2f));
        display.rect(x, y + saveMenu.scroll, width, height);
      }
    }
  }

  public void buttonPress(Player player, SaveMenu saveMenu, Grid grid) {
    player.state = State.GAME;
    player.placeTime = 0;
    saveMenu.scroll = 0;
    grid.newGrid = new Grid(new Snippet("../saves/grids/", text));
  }
}
