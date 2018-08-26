package computerblocks;

import java.util.ArrayList;

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
          if (blocks[x][y][l] != null) {
            blocks[x][y][l].draw(display, player);
            blocks[x][y][l].update(this, blocks[x][y][l]);
          }
          else {
            dummyBlock.position = new BlockPosition(x, y, l);
            dummyBlock.draw(display, player);
          }
        }
      }
    }
  }

  public void tick() {
    ArrayList<Block> queue = new ArrayList<Block>();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int l = 0; l < layers; l++) {
          if (blocks[x][y][l] != null && blocks[x][y][l].type == BlockType.DELAY) {
            if (blocks[x][y][l].tick()) {
              queue.add(blocks[x][y][l]);
            }
          }
        }
      }
    }

    for (Block block : queue) {
      block.updateSurroundingBlocks(this, block.getSurroundingBlocks(this));
    }
  }

  public Block blockAt(BlockPosition position) {
    if (
      position.x < 0 || position.x > width ||
      position.y < 0 || position.y > height ||
      position.l < 0 || position.l > layers
    ) return null;

    return blocks[position.x][position.y][position.l];
  }

  private Block getBlockFromType(BlockType type, BlockPosition position) {
    Block block = new CableBlock(position);
    if (type == BlockType.SOURCE) block = new SourceBlock(position);
    if (type == BlockType.INVERTER) block = new InverterBlock(position);
    // if (type == BlockType.VIA) block = new ViaBlock(position);
    if (type == BlockType.DELAY) block = new DelayBlock(position);
    return block;
  }

  public BlockPosition mouseOverBlock(Player player) {
    Block dummyBlock = new EmptyBlock(new BlockPosition(0, 0, 0));
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int l = 0; l < layers; l++) {
          if (blocks[x][y][l] != null) {
            if (blocks[x][y][l].mouseOver(player)) return new BlockPosition(x, y, player.selectedRotation, l);
          }
          else {
            dummyBlock.position = new BlockPosition(x, y, player.selectedRotation, l);
            if (dummyBlock.mouseOver(player)) return new BlockPosition(x, y, player.selectedRotation, l);
          }
        }
      }
    }
    return null;
  }

  public void place(BlockType type, BlockPosition position) {
    blocks[position.x][position.y][position.l] = getBlockFromType(type, position);;
    Block block = blocks[position.x][position.y][position.l];
    block.update(this, block);
  }

  public void erase(BlockPosition position) {
    Block block = blockAt(position);
    if (block != null) {
      blocks[position.x][position.y][position.l] = null;
      block.updateSurroundingBlocks(this, block.getSurroundingBlocks(this));
    }
  }
}
