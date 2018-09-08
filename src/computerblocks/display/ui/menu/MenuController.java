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
    pauseMenu.addElement(new Button("Saves", -0.5f, 0, 0.5f));
    pauseMenu.addElement(new Button("Load", 0.5f, -1, 0.5f));
    pauseMenu.addElement(new Button("Settings", -1));
    pauseMenu.addElement(new Button("Credits", -1));
    pauseMenu.addElement(new Button("Exit", -1));

    saveMenu = new Menu(display.width/3, display.height*0.7f, "logo.png");
    saveMenu.addElement(new Label("Slot 1", Color.UI_BORDER, -0.5f, 0, 0.5f));
    saveMenu.addElement(new Button("Save", 0.45f, -1, 0.25f, 0));
    saveMenu.addElement(new Button("Load", 1.5f, -2, 0.25f));
    saveMenu.addElement(new Label("Slot 2", Color.UI_BORDER, -0.5f, -2, 0.5f));
    saveMenu.addElement(new Button("Save", 0.45f, -3, 0.25f, 1));
    saveMenu.addElement(new Button("Load", 1.5f, -4, 0.25f));
    saveMenu.addElement(new Label("JUST A TEMP SCREEN", Color.INVERTER_OFF, 0, 0));

    creditsMenu = new Menu(display.width*0.7f, display.height*0.9f, "credits_title.png");
    // creditsMenu.addElement(new Label("By Liam Brennan and Joshua Briant"));
    // creditsMenu.addElement(new Label("Assisted by Roizi Boi"));

    currentMenu = pauseMenu;
  }

  public void update(Display display, Player player) {
    if (currentMenu != null) currentMenu.draw(display, player, this);
  }
}
