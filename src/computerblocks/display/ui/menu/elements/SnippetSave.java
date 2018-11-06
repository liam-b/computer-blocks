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

  public void checkPress(Display display, Player player) {
    if (pointOver(player.mouse.position.x, player.mouse.position.y)) {
      if (player.mouse.down(Mouse.LEFT)) {
        buttonPress(player);
      }
      display.color(new Color(255, 255, 255, 0.2f));
      display.rect(x, y, width, height);
    }
  }

  public void buttonPress(Player player) {

  }
}
