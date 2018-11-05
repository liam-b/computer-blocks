import computerblocks.Game;
import computerblocks.display.Display;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.*;
import java.awt.Image;
import java.io.IOException;

public class Index {
  public static Game game;

  public static void main(String[] args) {
    System.out.println("-----------------------------------------------------------------------------------------------------------------\n                                             _                           _       _                  _          \r\n   ___    ___    _ __ ___    _ __    _   _  | |_    ___   _ __          | |__   | |   ___     ___  | | __  ___ \r\n  / __|  / _ \\  |  _   _ \\  |  _ \\  | | | | | __|  / _ \\ | '__|  _____  |  _ \\  | |  / _ \\   / __| | |/ / / __|\r\n | (__  | (_) | | | | | | | | |_) | | |_| | | |_  |  __/ | |    |_____| | |_) | | | | (_) | | (__  |   <  \\__ \\\r\n  \\___|  \\___/  |_| |_| |_| | .__/   \\__,_|  \\__|  \\___| |_|            |_.__/  |_|  \\___/   \\___| |_|\\_\\ |___/\r\n                            |_|                                                                                \n-----------------------------------------------------------------------------------------------------------------");

    System.out.println("Initializing save folders...");
    new File("../saves/snippets").mkdirs();
    System.out.println("Pre-Init Complete");


    game = new Game("Computer Blocks", 1280, 720, Display.PRIMARY);
  }
}
