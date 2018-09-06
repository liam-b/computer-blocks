package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;

public class MenuController {
  public Menu currentMenu;
  private Menu pauseMenu, saveMenu, settingsMenu;

  public MenuController(Display display) {
    init(display);
  }

  public void init(Display display) {
    pauseMenu = new Menu(display.width/3, display.height*0.7f);
    currentMenu = pauseMenu;
  }

  public void update(Display display) {
    if (currentMenu != null) currentMenu.draw(display);
  }
}
