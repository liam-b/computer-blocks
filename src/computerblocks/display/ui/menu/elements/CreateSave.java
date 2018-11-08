package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;
import computerblocks.snippet.*;

public class CreateSave {

  public String text = "New Save";
  public double x, y, width, height;

  private String font = Fonts.pixelmix;
  private int textSize = 20;

  public CreateSave(Display display, SaveMenu saveMenu) {
    // height = saveMenu.height / 10;
    // width = saveMenu.width * 6 / 10;
    height = saveMenu.height / 10 * 3;
    width = saveMenu.width;
    y = display.height / 2 + saveMenu.height / 2 - height;
    x = display.width / 2 - width / 2;
  }

  public void draw(Display display, SaveMenu saveMenu) {
    display.color(Color.UI_BORDER);
    display.rect(x, y + 4, width, height);
    display.color(Color.CABLE);
    display.rect(x, y, width, height);
    drawText(display, 1f);

    // display.color(Color.INVERTER_CHARGE);
    // display.rect(x, maxY, width, height);
    // display.rect(x, minY, width, height);
  }

  private void drawText(Display display, float alpha) {
    display.color(new Color(Color.UI_BORDER, alpha));
    display.font(font, textSize);
    display.text(text,  x + width / 2 - display.getStringWidth(text, font, textSize) / 2,
                        y + height / 2 + display.getFontHeight(font, textSize) / 2);
  }

  public boolean pointOver(double x, double y) {
    if (x > this.x && x < this.x + width &&
        y > this.y && y < this.y + height) {
      return true;
    }
    return false;
  }

  public void checkPress(Display display, Player player, SaveMenu saveMenu, Grid grid) {
    if (pointOver(player.mouse.position.x, player.mouse.position.y)) {
      if (player.mouse.down(Mouse.LEFT)) {
        buttonPress(player, saveMenu, grid, display);
      }
      display.color(new Color(255, 255, 255, 0.2f));
      display.rect(x, y, width, height);
    }
  }

  public void buttonPress(Player player, SaveMenu saveMenu, Grid grid, Display display) {
    String saveName = "";
    while (true) {
      saveName = player.keyboard.keyStream(saveName, 20);

      if (player.keyboard.down(Keyboard.ESC)) break;

      display.reset(Color.BACKGROUND);
      display.font(font, textSize);
      if (!saveName.equals("")) {
        if (saveAlreadyExists(saveName, saveMenu)) {
          display.color(Color.INVERTER);
          String warningText = "WARINING! - THIS WILL REPLACE EXISTING SAVE";
          display.text(warningText,  (display.width / 2) - display.getStringWidth(warningText, font, textSize) / 2, (display.height / 2) + display.getFontHeight(font, textSize) / 2  + display.height / 8);
        }
        display.color(Color.UI_BORDER);
        display.text(saveName,  (display.width / 2) - display.getStringWidth(saveName, font, textSize) / 2, (display.height / 2) + display.getFontHeight(font, textSize) / 2);
      } else {
        display.color(new Color(Color.UI_BORDER, 0.25f));
        display.text(text,  (display.width / 2) - display.getStringWidth(text, font, textSize) / 2, (display.height / 2) + display.getFontHeight(font, textSize) / 2);
      }
      display.draw();

      if (player.keyboard.enterDown) {
        if (!saveName.equals("")) {
          new Snippet(grid).saveToFile("../saves/grids/", saveName);
          saveMenu.refreshSaveNames = true;
          player.state = State.GAME;
          player.placeTime = 0;
          break;
        } else {
          new Snippet(grid).saveToFile("../saves/grids/", text);
          saveMenu.refreshSaveNames = true;
          player.state = State.GAME;
          player.placeTime = 0;
          break;
        }
      }
    }
  }

  public boolean saveAlreadyExists(String saveName, SaveMenu saveMenu) {
    for (SaveButton i : saveMenu.saves) {
      if (i.text.toLowerCase().equals(saveName.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
}
