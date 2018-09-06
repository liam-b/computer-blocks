package computerblocks.player;

import computerblocks.position.*;
import computerblocks.Grid;
import computerblocks.display.*;

public class Selection {
  private RealPosition initialPosition;
  private BlockPosition initialBlockPosition;

  public Selection(Grid grid, Player player) {
    this.initialPosition = new RealPosition(player.mouse.position);
    this.initialBlockPosition = grid.mouseOverBlock(player);
  }

  public void draw(Display display, Grid grid, Player player) {
    RealPosition positionLeast = new RealPosition(Math.min(initialPosition.x, player.mouse.position.x), Math.min(initialPosition.y, player.mouse.position.y));
    RealPosition positionMost = new RealPosition(Math.max(initialPosition.x, player.mouse.position.x), Math.max(initialPosition.y, player.mouse.position.y));
    display.rect(positionLeast, positionMost.x - positionLeast.x, positionMost.y - positionLeast.y);

    BlockPosition mouseBlockPosition = grid.mouseOverBlock(player);
    for (int x = 0; x < grid.width; x++) {
      for (int y = 0; y < grid.height; y++) {
        for (int l = 0; l < grid.layers; l++) {
          if (mouseBlockPosition != null && grid.blocks[x][y][l] != null) {
            if (grid.blocks[x][y][l].position.isWithin(initialBlockPosition, mouseBlockPosition)) {
              grid.blocks[x][y][l].selected = true;
            } else grid.blocks[x][y][l].selected = false;
          }
        }
      }
    }
  }
}