package computerblocks;

import java.lang.*;

// import java.util.ArrayList;
// import org.json.JSONArray;
// import org.json.JSONObject;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
import java.io.*;

import computerblocks.display.*;
import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.player.*;
import computerblocks.display.ui.*;
import computerblocks.snippet.Snippet;
import computerblocks.display.ui.menu.*;
import computerblocks.display.ui.menu.elements.*;

public class Game {
  private boolean running = true;
  private long targetFps = 60;
  private int updateCount = 0;
  private double lastTime = 0;

  private Display display;
  private UserInterface ui;
  private MenuController menuController;
  public SnippetTray snippetTray;

  String demoText = "";

  private Player player;
  private Grid grid;

  public Game(String title, int displayWidth, int displayHeight, int displayScreen) {
    display = new Display(title, displayWidth, displayHeight, displayScreen);

    setup();
    loop();
  }

  private void loop() {
    long lastLoopTime = System.nanoTime();
    double optimalTime = 1000000000d / (double)targetFps;
    double fps;

    while (running) {
      long timeNow = System.nanoTime();
      long updateLength = timeNow - lastLoopTime;
      double delta = updateLength / ((double)optimalTime);
      lastLoopTime = timeNow;

      if (updateCount % 10 == 0) tick();
      update();
      render();

      System.out.println(updateCount / System.nanoTime());
      updateCount += 1;

      long delay = (long)optimalTime - (System.nanoTime() - timeNow);
      double sleepTime = Math.max((double)delay / 1000000d, 0d);
      // long stress = (long)optimalTime - delay;
      // System.out.println(stress / 100000l);

      try { Thread.sleep((long)sleepTime); }
      catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }
  }

  private void setup() {
    grid = new Grid(300, 300, 3);
    player = new Player(display, this);
    ui = new UserInterface();
    snippetTray = new SnippetTray(display);
    menuController = new MenuController(display);
    Fonts.addFont(new Fonts("Paloseco-Medium.ttf"));
    Fonts.addFont(new Fonts("Roboto-Regular.ttf"));
    Fonts.addFont(new Fonts("SUPERSCR.ttf"));
    Fonts.addFont(new Fonts("pixelmix.ttf"));

    snippetTray.update(display, player, grid);
    for (final File fileEntry : new File("../saves/snippets").listFiles()) {
        if (!fileEntry.isDirectory()) {
          snippetTray.snippets.add(new SnippetButton(display, snippetTray, fileEntry.getName().substring(0, fileEntry.getName().length() - 5)));
        }
    }
    menuController.saveMenuElement.update(display, player, grid);
    for (final File fileEntry : new File("../saves/grids").listFiles()) {
        if (!fileEntry.isDirectory()) {
          menuController.saveMenuElement.saves.add(new SaveButton(display, menuController.saveMenuElement, fileEntry.getName().substring(0, fileEntry.getName().length() - 5)));
        }
    }

  }

  private void update() {
    if (grid.newGrid != null) {
      grid = grid.newGrid;
    }
    player.update(display, grid, menuController, snippetTray);
  }

  private void tick() {
    grid.tick(display, player);
  }

  private synchronized void render() {
    display.reset(Color.BACKGROUND);

    grid.draw(display, player);
    player.draw(display, grid, snippetTray);
    ui.draw(display, player, grid);
    if (player.state == State.MENU) menuController.update(display, player, grid);
    if (player.state == State.SNIPPET) snippetTray.update(display, player, grid);
    display.draw();
  }
}
