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
    pauseMenu.addElement(new Button("Continue"));
    pauseMenu.addElement(new Button("Settings"));
    pauseMenu.addElement(new Button("Credits"));
    pauseMenu.addElement(new Button("Exit"));
    settingsMenu = new Menu(display.width*0.7f, display.height*0.9f);
    settingsMenu.addElement(new Button("apwdokadpwakodpad"));
    currentMenu = pauseMenu;
  }

  public void update(Display display, Player player) {
    if (currentMenu != null) currentMenu.draw(display, player, this);
  }
}
