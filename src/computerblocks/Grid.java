package computerblocks;

import java.util.ArrayList;

import computerblocks.position.*;
import computerblocks.display.*;
import computerblocks.block.*;
import computerblocks.block.types.*;
import computerblocks.player.*;

public class Grid {
  public Block[][][] blocks;
  public int width, height, layers;
  private Block dummyBlock = new EmptyBlock(new BlockPosition(0, 0, 0));

  public Grid(int width, int height, int layers) {
    this.width = width;
    this.height = height;
    this.layers = layers;

    blocks = new Block[width][height][layers];
  }

  // public Grid(Snippet snippet) {
  //   this.width = snippet.width;
  //   this.height = snippet.height;
  //   this.layers = snippet.layers;
  //
  //   blocks = snippet.blocks;
  // }

  public void draw(Display display, Player player) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (blocks[x][y][player.selectedLayer] != null) {
          blocks[x][y][player.selectedLayer].draw(display, player);
        }
        else {
          dummyBlock.position.x = x;
          dummyBlock.position.y = y;
          dummyBlock.position.l = player.selectedLayer;
          dummyBlock.draw(display, player);
        }
      }
    }
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (blocks[x][y][player.selectedLayer] != null) {
          if (blocks[x][y][player.selectedLayer].type == BlockType.LABEL && blocks[x][y][player.selectedLayer].mouseOver(player)) blocks[x][y][player.selectedLayer].draw(display, player);
        }
      }
    }
  }

  // public void unselect() {
  //   for (int x = 0; x < width; x++) {
  //     for (int y = 0; y < height; y++) {
  //       for (int l = 0; l < layers; l++) {
  //         if (blocks[x][y][l] != null) blocks[x][y][l].highlighted = false;
  //       }
  //     }
  //   }
  // }

  // public void tick(Display display, Player player) {
  //   ArrayList<Block> queue = new ArrayList<Block>();
  //   for (int x = 0; x < width; x++) {
  //     for (int y = 0; y < height; y++) {
  //       for (int l = 0; l < layers; l++) {
  //         if (blocks[x][y][l] != null && blocks[x][y][l].type == BlockType.DELAY) {
  //           if (blocks[x][y][l].tick()) {
  //             queue.add(blocks[x][y][l]);
  //           }
  //         }
  //       }
  //     }
  //   }
  //
  //   for (Block block : queue) {
  //     ArrayList<Block> surroundingBlocks = block.getSurroundingBlocks(this);
  //     for (Block surroundingBlock : surroundingBlocks) {
  //       propagate(surroundingBlock, display, player);
  //     }
  //   }
  // }

  // public void fromSnippet(Snippet snippet) {
  //   this.width = snippet.width;
  //   this.height = snippet.height;
  //   this.layers = snippet.layers;
  //
  //   blocks = snippet.blocks;
  // }

  public Block blockAt(int x, int y, int l) {
    if (
      x < 0 || x >= width ||
      y < 0 || y >= height ||
      l < 0 || l >= layers
    ) return null;
  
    return blocks[x][y][l];
  }

  // public BlockPosition mouseOverBlock(Player player) {
  //   for (int x = 0; x < width; x++) {
  //     for (int y = 0; y < height; y++) {
  //       if (blocks[x][y][player.selectedLayer] != null) {
  //         if (blocks[x][y][player.selectedLayer].mouseOver(player)) return new BlockPosition(x, y, player.selectedRotation, player.selectedLayer);
  //       }
  //       else {
  //         dummyBlock.position.x = x;
  //         dummyBlock.position.y = y;
  //         dummyBlock.position.l = player.selectedLayer;
  //         if (dummyBlock.mouseOver(player)) return new BlockPosition(x, y, player.selectedRotation, player.selectedLayer);
  //       }
  //     }
  //   }
  //   return null;
  // }

  // public void paste(Snippet snippet, BlockPosition placePosition) {
  //   for (int x = 0; x < snippet.width; x++) {
  //     for (int y = 0; y < snippet.height; y++) {
  //       for (int l = 0; l < snippet.layers; l++) {
  //         Block snippetBlock = snippet.blocks[x][y][l];
  //         if (snippetBlock != null) {
  //           BlockPosition position = new BlockPosition(x, y, snippetBlock.position.r, l).add(placePosition);
  //           Block block = Block.fromType(snippetBlock.type, position);
  //           block.charge = snippetBlock.charge;
  //           block.lastCharge = snippetBlock.lastCharge;
  //           block.tickCharge = snippetBlock.tickCharge;
  //           if (block.type == BlockType.LABEL) block.labelText = snippetBlock.labelText;
  //
  //           block.saveInputPositions = new ArrayList<BlockPosition>();
  //           for (Block inputBlock : snippetBlock.inputs) {
  //             block.saveInputPositions.add(inputBlock.position.add(placePosition));
  //           }
  //
  //           blocks[position.x][position.y][position.l] = block;
  //         }
  //       }
  //     }
  //   }
  //
  //   for (int x = 0; x < width; x++) {
  //     for (int y = 0; y < height; y++) {
  //       for (int l = 0; l < layers; l++) {
  //         Block block = blocks[x][y][l];
  //         if (block != null) {
  //           if (block.saveInputPositions != null) {
  //             for (BlockPosition inputPosition : block.saveInputPositions) {
  //               block.inputs.add(blocks[inputPosition.x][inputPosition.y][inputPosition.l]);
  //
  //               System.out.println("---------");
  //               System.out.println("position: " + block.position.toString());
  //               System.out.println("inputs:");
  //               for (Block inp : block.inputs) {
  //                 System.out.println("  " + inp.position.toString());
  //               }
  //               System.out.println("save inputs:");
  //               for (BlockPosition inp : block.saveInputPositions) {
  //                 System.out.println("  " + inp.toString());
  //               }
  //               System.out.println("---------");
  //             }
  //           }
  //           block.saveInputPositions = null;
  //         }
  //       }
  //     }
  //   }
  // }

  // public void place(BlockType type, BlockPosition position, Display display, Player player) {
  //   blocks[position.x][position.y][position.l] = Block.fromType(type, position);
  //   Block block = blocks[position.x][position.y][position.l];
  //
  //   try {
  //     propagate(block, display, player);
  //   }
  //   catch(StackOverflowError e) {
  //     blocks[position.x][position.y][position.l] = null;
  //   }
  // }

  // public void erase(BlockPosition position, Display display, Player player) {
  //   Block block = blockAt(position.x, position.y, position.l);
  //   if (block != null) {
  //     blocks[position.x][position.y][position.l] = null;
  //
  //     ArrayList<Block> surroundingBlocks = block.getSurroundingBlocks(this);
  //     for (Block surroundingBlock : surroundingBlocks) {
  //       propagate(surroundingBlock, display, player);
  //     }
  //   }
  // }

  // public void propagate(Block source, Display display, Player player) {
  //   ArrayList<BlockUpdate> updateQueue = new ArrayList<BlockUpdate>();
  //
  //   ArrayList<Block> surroundingBlocks = new ArrayList<Block>();
  //   surroundingBlocks.add(source);
  //   updateQueue.add(new BlockUpdate(surroundingBlocks, source));
  //
  //   int iterations = 0;
  //   while (updateQueue.size() > 0 && iterations < 10000) {
  //     iterations += 1;
  //     ArrayList<BlockUpdate> nextUpdateQueue = new ArrayList<BlockUpdate>();
  //
  //     for (BlockUpdate blockUpdate : updateQueue) {
  //       nextUpdateQueue.addAll(blockUpdate.update(this, display, player));
  //     }
  //
  //     updateQueue = nextUpdateQueue;
  //   }
  //   System.out.println("iters: " + iterations);
  // }
}
