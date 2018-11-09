package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;
import computerblocks.display.ui.menu.*;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.*;
import java.awt.Image;
import java.io.IOException;

public class SaveMenu {
  public int x, y, width, height;
  private int border = 8;

  public int scroll;
  private int scrollSpeed = 3;

  public ArrayList<SaveButton> saves;
  private CreateSave saveButton;

  public boolean refreshSaveNames = true;

  public SaveMenu(Display display) {
    saves = new ArrayList<SaveButton>();
    height = display.height * 7 / 10;
    width = display.width / 3;
    saveButton = new CreateSave(display, this);
  }

  public void update(Display display, Player player, Grid grid) {
    if (refreshSaveNames) {
      saves = new ArrayList<SaveButton>();
      for (final File fileEntry : new File("../saves/grids").listFiles()) {
        if (!fileEntry.isDirectory()) {
          saves.add(new SaveButton(display, this, fileEntry.getName().substring(0, fileEntry.getName().length() - 5)));
        }
      }
    }

    // darken game
    display.color(new Color(0f, 0f, 0f, 0.4f));
    display.rect(0, 0, display.width, display.height);

    // draw background box
    display.color(Color.UI_BORDER);
    display.rect(display.width/2 - (width+border)/2, display.height/2 - (height+border)/2, width+border, height+border);
    display.color(Color.BACKGROUND);
    display.rect(display.width/2 - width/2, display.height/2 - height/2, width, height);

    for (SaveButton i : saves) {
      i.draw(display, this);
      i.checkPress(display, player, this, grid);
    }
    saveButton.draw(display, this);
    saveButton.checkPress(display, player, this, grid);

    if (player.keyboard.held(40)) scroll += scrollSpeed;
    if (player.keyboard.held(38)) scroll -= scrollSpeed;
  }
}
