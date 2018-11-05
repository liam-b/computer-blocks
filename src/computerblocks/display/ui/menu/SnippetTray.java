package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.*;
import java.awt.Image;
import java.io.IOException;

public class SnippetTray {
  public int lifeTime = 0;
  public int x, y, width, height;
  private int border = 8;

  private int animationTime = 10;

  public SnippetTray() {
    System.out.println("SnippetTrat Init");
  }

  public void update(Display display, Player player, Grid grid) {
    System.out.println("lifeTime: " + lifeTime);

    height = display.height * 8 / 10;
    width = display.width / 6;

    // darken game
    display.color(new Color(0f, 0f, 0f, 0.4f));
    display.rect(0, 0, display.width, display.height);

    // draw background box
    display.color(Color.UI_BORDER);
    display.rect(display.width - (width+border)/2 - width/2 + animX(), display.height/2 - (height+border)/2, width+border, height+border);
    display.color(Color.BACKGROUND);
    display.rect(display.width - width + animX(), display.height/2 - height/2, width, height);
  }

  private int animX() {
    if (lifeTime < animationTime) {
      lifeTime += 1;
      return width/animationTime*(animationTime - lifeTime);
    } else return 0;
  }
}
