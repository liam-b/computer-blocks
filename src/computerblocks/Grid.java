package computerblocks;

import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.display.*;
import computerblocks.player.*;

public class Grid {
  public Block[][][] blocks;
  public int width, height, layers;

  public Grid(int width, int height, int layers) {
    this.width = width;
    this.height = height;
    this.layers = layers;

    blocks = new Block[width][height][layers];
  }

  public void draw(Display display, Player player) {
    Block dummyBlock = new EmptyBlock(new BlockPosition(0, 0, 0));
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int l = 0; l < layers; l++) {
          if (blocks[x][y][l] != null) blocks[x][y][l].draw(display, player);
          else {
            dummyBlock.position = new BlockPosition(x, y, l);
            dummyBlock.draw(display, player);
          }
        }
      }
    }
  }

  public Block blockAt(BlockPosition position) {
    return blocks[position.x][position.y][position.l];
  }

  private Block getBlockFromType(BlockType type, BlockPosition position) {
    Block block = new CableBlock(position);
    if (type == BlockType.SOURCE) block = new SourceBlock(position);
    // if (type == BlockType.INVERTER) block = new InverterBlock(position);
    // if (type == BlockType.VIA) block = new ViaBlock(position);
    // if (type == BlockType.DELAY) block = new DelayBlock(position);
    return block;
  }

  public BlockPosition mouseOverBlock(Player player) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int l = 0; l < layers; l++) {
          if (blocks[x][y][l] != null) {
            if (blocks[x][y][l].mouseOver(player)) return new BlockPosition(blocks[x][y][l].position);
          }
        }
      }
    }
    return null;
  }

  public void place(BlockType type, BlockPosition position) {
    blocks[position.x][position.y][position.l] = getBlockFromType(type, position);;
    // block.update();
  }

  public void erase(BlockPosition position) {
    Block block = blockAt(position);
    // block.updateSurroundingBlocks();
    blocks[position.x][position.y][position.l] = null;
  }
}