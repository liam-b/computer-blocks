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

  public int scroll;
  private int scrollSpeed = 3;

  public ArrayList<SnippetButton> snippets;

  private int animationTime = 25;

  public SnippetTray(Display display) {
    snippets = new ArrayList<SnippetButton>();
  }

  public void update(Display display, Player player, Grid grid) {

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

    for (SnippetButton i : snippets) {
      i.draw(display, this);
      i.checkPress(display, player, this);
    }

    if (player.keyboard.held(40)) scroll += scrollSpeed;
    if (player.keyboard.held(38)) scroll -= scrollSpeed;
  }

  public int animX() {
    if (lifeTime < animationTime) {
      lifeTime += 1;
      return width/animationTime*(animationTime - lifeTime);
    } else return 0;
  }
}
