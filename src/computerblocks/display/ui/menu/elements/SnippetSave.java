package computerblocks.display.ui.menu.elements;

import java.util.ArrayList;
import computerblocks.display.*;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;
import computerblocks.snippet.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.*;
import java.awt.Image;
import java.io.IOException;

public class SnippetSave {
  public double x, y, width, height;
  private Image icon;

  private String font = Fonts.pixelmix;
  private int textSize = 20;
  private String text = "New Snippet";

  public SnippetSave(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.width = width;

    loadImage("icon.png");
  }

  public void loadImage(String header) {
    try {
      icon = ImageIO.read(new File("../assets/" + header));
    } catch (IOException err) {
      err.printStackTrace();
    }
  }

  public void draw(Display display) {
    display.color(Color.UI_BORDER);
    display.rect(x, y + 4, width, height);
    display.color(Color.CABLE);
    display.rect(x, y, width, height);
    display.image(icon, x, y, width, height);
  }

  public boolean pointOver(double x, double y) {
    if (x > this.x && x < this.x + width &&
        y > this.y && y < this.y + height) {
      return true;
    }
    return false;
  }

  public void checkPress(Display display, Player player, SnippetTray snippetTray) {
    if (pointOver(player.mouse.position.x, player.mouse.position.y)) {
      if (player.mouse.down(Mouse.LEFT)) {
        buttonPress(display, player, snippetTray);
      }
      display.color(new Color(255, 255, 255, 0.2f));
      display.rect(x, y, width, height);
    }
  }

  public void buttonPress(Display display, Player player, SnippetTray snippetTray) {
    String saveName = "";
    while (true) {
      saveName = player.keyboard.keyStream(saveName, 20);

      if (player.keyboard.down(Keyboard.ESC)) break;

      display.reset(Color.BACKGROUND);
      display.font(font, textSize);
      if (!saveName.equals("")) {
        if (snippetAlreadyExists(saveName, snippetTray)) {
          display.color(Color.INVERTER);
          String warningText = "WARINING! - THIS WILL REPLACE PREVIOUS SNIPPET";
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
          player.snippet.saveToFile("../saves/snippets/", saveName);
          snippetTray.refreshSaveNames = true;
          player.state = State.GAME;
          player.placeTime = 0;
          break;
        } else {
          player.snippet.saveToFile("../saves/snippets/", text);
          snippetTray.refreshSaveNames = true;
          player.state = State.GAME;
          player.placeTime = 0;
          break;
        }
      }
    }
  }

  public boolean snippetAlreadyExists(String saveName, SnippetTray snippetTray) {
    for (SnippetButton i : snippetTray.snippets) {
      if (i.text.toLowerCase().equals(saveName.toLowerCase())) {
        return true;
      }
    }
    return false;
  }
}
