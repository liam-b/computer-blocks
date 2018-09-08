package computerblocks;

import java.util.ArrayList;

import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.display.*;
import computerblocks.player.*;
import computerblocks.snippet.Snippet;

public class Grid {
  public Block[][][] blocks;
  public int width, height, layers;

  public Grid(int width, int height, int layers) {
    this.width = width;
    this.height = height;
    this.layers = layers;

    blocks = new Block[width][height][layers];
  }

  public Grid(Snippet snippet) {
    this.width = snippet.width;
    this.height = snippet.height;
    this.layers = snippet.layers;

    blocks = snippet.blocks;
  }

  public void draw(Display display, Player player) {
    Block dummyBlock = new EmptyBlock(new BlockPosition(0, 0, player.selectedLayer));
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (blocks[x][y][player.selectedLayer] != null) {
          blocks[x][y][player.selectedLayer].draw(display, player);
        }
        else {
          dummyBlock.position = new BlockPosition(x, y, player.selectedLayer);
          dummyBlock.draw(display, player);
        }
      }
    }
  }

  public void unselect() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int l = 0; l < layers; l++) {
          if (blocks[x][y][l] != null) blocks[x][y][l].selected = false;
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
      position.x < 0 || position.x >= width ||
      position.y < 0 || position.y >= height ||
      position.l < 0 || position.l >= layers
    ) return null;

    return blocks[position.x][position.y][position.l];
  }

  public BlockPosition mouseOverBlock(Player player) {
    Block dummyBlock = new EmptyBlock(new BlockPosition(0, 0, 0));
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (blocks[x][y][player.selectedLayer] != null) {
          if (blocks[x][y][player.selectedLayer].mouseOver(player)) return new BlockPosition(x, y, player.selectedRotation, player.selectedLayer);
        }
        else {
          dummyBlock.position = new BlockPosition(x, y, player.selectedRotation, player.selectedLayer);
          if (dummyBlock.mouseOver(player)) return new BlockPosition(x, y, player.selectedRotation, player.selectedLayer);
        }
      }
    }
    return null;
  }

  public void place(Snippet snippet, BlockPosition placePosition) {
    for (int x = 0; x < snippet.width; x++) {
      for (int y = 0; y < snippet.height; y++) {
        for (int l = 0; l < snippet.layers; l++) {
          Block snippetBlock = snippet.blocks[x][y][l];
          if (snippetBlock != null) {
            BlockPosition position = new BlockPosition(x, y, snippetBlock.position.r, l).add(placePosition);
            Block block = Block.fromType(snippetBlock.type, position);
            block.charge = snippetBlock.charge;
            block.lastCharge = snippetBlock.lastCharge;
            block.tickCharge = snippetBlock.tickCharge;

            block.saveInputPositions = new ArrayList<BlockPosition>();
            for (Block inputBlock : snippetBlock.inputs) {
              block.saveInputPositions.add(inputBlock.position.add(placePosition));
            }

            blocks[position.x][position.y][position.l] = block;
          }
        }
      }
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        for (int l = 0; l < layers; l++) {
          Block block = blocks[x][y][l];
          if (block != null) {
            for (BlockPosition inputPosition : block.saveInputPositions) {
              block.inputs.add(blocks[inputPosition.x][inputPosition.y][inputPosition.l]);
            }
          }
        }
      }
    }
  }

  public void place(BlockType type, BlockPosition position) {
    blocks[position.x][position.y][position.l] = Block.fromType(type, position);
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
