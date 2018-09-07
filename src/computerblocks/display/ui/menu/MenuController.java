package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;

public class MenuController {
  public Menu currentMenu;
  public Menu pauseMenu, saveMenu, settingsMenu;

  public MenuController(Display display) {
    init(display);
  }

  public void init(Display display) {
    pauseMenu = new Menu(display.width/3, display.height*0.7f);
    pauseMenu.addElement(new Button("_text_"));
    pauseMenu.addElement(new Button("_text_"));
    pauseMenu.addElement(new Button("_text_"));
    currentMenu = pauseMenu;
    settingsMenu = new Menu(display.width*0.7f, display.height*0.9f);
  }

  public void update(Display display, Player player) {
    if (currentMenu != null) currentMenu.draw(display, player, this);
  }
}
