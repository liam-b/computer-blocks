package computerblocks.display.ui.menu;

import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;

public class MenuController {
  public Menu currentMenu;
  public Menu pauseMenu, saveMenu, settingsMenu, creditsMenu;

  public MenuController(Display display) {
    init(display);
  }

  public void init(Display display) {
    pauseMenu = new Menu(display.width/3, display.height*0.7f, "logo.png");
    pauseMenu.addElement(new Button("Continue"));
    pauseMenu.addElement(new Button("Settings"));
    pauseMenu.addElement(new Button("Credits"));
    pauseMenu.addElement(new Button("Exit"));

    creditsMenu = new Menu(display.width*0.7f, display.height*0.9f, "credits_title.png");
    creditsMenu.addElement(new Label("By Liam Brennan and Joshua Briant"));
    creditsMenu.addElement(new Label("Assisted by Roizi Boi"));

    currentMenu = pauseMenu;
  }

  public void update(Display display, Player player) {
    if (currentMenu != null) currentMenu.draw(display, player, this);
  }
}
