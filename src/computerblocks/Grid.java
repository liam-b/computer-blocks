package computerblocks;

import computerblocks.block.*;

public class Grid {
  public Block[][][] blocks;
  public int width, height, layers;

  public Grid(int width, int height, int layers) {
    this.width = width;
    this.height = height;
    this.layers = layers;

    blocks = new Block[width][height][layers];
  }

  void draw() {}
}