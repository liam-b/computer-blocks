package computerblocks.snippet;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import computerblocks.Grid;
import computerblocks.block.*;
import computerblocks.position.*;
import computerblocks.display.Display;
import computerblocks.player.Player;

public class Snippet {
  public static final String SAVE_PATH = "./";
  public static final String SNIPPET_PATH = "./";
  public static final String EXTENSION = ".snip";

  public Block[][][] blocks;
  public int width, height, layers;

  private BlockPosition lastOffset = new BlockPosition();

  public Snippet(Grid grid) {
    this.width = grid.width;
    this.height = grid.height;
    this.layers = grid.layers;
    this.blocks = grid.blocks;
  }

  public Snippet(String path, String name) {
    String content = "";
    try {
      content = new String(Files.readAllBytes(Paths.get(path + name + EXTENSION)));
    } catch (IOException err) {
      err.printStackTrace();
    } finally {
      JSONObject snippetJSON = new JSONObject(content);

      // if (snippetJSON.getJSONArray("blocks").length() != 0) {
      this.width = snippetJSON.getInt("width");
      this.height = snippetJSON.getInt("height");
      this.layers = snippetJSON.getInt("layers");
      this.blocks = JSON.JSONToBlocks(snippetJSON.getJSONArray("blocks"), width, height, layers);
    }
  }

  public Snippet(BlockPosition positionA_, BlockPosition positionB_, Grid grid) {
    BlockPosition positionLeast = new BlockPosition(Math.min(positionA_.x, positionB_.x), Math.min(positionA_.y, positionB_.y), Math.min(positionA_.l, positionB_.l));
    BlockPosition positionMost = new BlockPosition(Math.max(positionA_.x, positionB_.x), Math.max(positionA_.y, positionB_.y), Math.max(positionA_.l, positionB_.l)).add(new BlockPosition(1, 1, 1));
    BlockPosition minimumBlockPosition = minimumPosition(positionLeast, positionMost, grid);
    BlockPosition maximumBlockPosition = maximumPosition(positionLeast, positionMost, grid);

    if (maximumBlockPosition.isEqual(new BlockPosition(-1, -1, -1))) {
      blocks = null;
    } else {
      positionLeast = minimumBlockPosition;
      positionMost = maximumBlockPosition.add(new BlockPosition(1, 1, 1));
      BlockPosition size = positionMost.subtract(positionLeast);

      this.blocks = new Block[size.x][size.y][size.l];
      for (int x = positionLeast.x; x < positionMost.x; x++) {
        for (int y = positionLeast.y; y < positionMost.y; y++) {
          for (int l = positionLeast.l; l < positionMost.l; l++) {
            Block block = grid.blockAt(x, y, l);
            if (block != null) {
              Block newBlock = new Block(new BlockPosition(x, y, l).subtract(positionLeast));
              newBlock.position.r = block.position.r;
              newBlock.type = block.type;
              newBlock.charge = block.charge;
              newBlock.lastCharge = block.lastCharge;
              newBlock.tickCharge = block.tickCharge;
              if (newBlock.type == BlockType.LABEL) newBlock.labelText = block.labelText;

              ArrayList<BlockPosition> inputs = new ArrayList<BlockPosition>();
              for (Block inputBlock : block.inputs) {
                inputs.add(inputBlock.position.subtract(positionLeast));
              }
              newBlock.saveInputPositions = inputs;

              blocks[x - positionLeast.x][y - positionLeast.y][l - positionLeast.l] = newBlock;
            }
          }
        }
      }

      for (int x = 0; x < size.x; x++) {
        for (int y = 0; y < size.y; y++) {
          for (int l = 0; l < size.l; l++) {
            Block block = blocks[x][y][l];
            if (block != null) {
              for (BlockPosition inputPosition : block.saveInputPositions) {
                if (inputPosition.isWithin(new BlockPosition(), size.subtract(new BlockPosition(1, 1, 1)))) block.inputs.add(blocks[inputPosition.x][inputPosition.y][inputPosition.l]);
              }
            }
          }
        }
      }

      width = size.x;
      height = size.y;
      layers = size.l;
    }
  }

  public void ghost(Display display, Player player, BlockPosition offset) {
    if (blocks != null) {
      if (offset == null) offset = lastOffset;
      lastOffset = offset;

      Block[][][] ghostBlocks = new Block[width][height][layers];

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          Block snippetBlock = blocks[x][y][0];
          if (snippetBlock != null) {
            Block block = Block.fromType(snippetBlock.type, snippetBlock.position.add(offset));
            block.charge = snippetBlock.charge;
            block.ghost = true;

            ghostBlocks[x][y][0] = block;
          }
        }
      }

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          if (ghostBlocks[x][y][0] != null) {
            ghostBlocks[x][y][0].draw(display, player);
          }
        }
      }
    }
  }

  public void saveToFile(String path, String name) {
    if (blocks != null) {
      JSONObject snippetJSON = new JSONObject();
      snippetJSON.put("name", name);
      snippetJSON.put("width", width);
      snippetJSON.put("height", height);
      snippetJSON.put("layers", layers);
      snippetJSON.put("blocks", JSON.blocksToJSON(blocks));

      try (FileWriter file = new FileWriter(path + name + EXTENSION)) {
        file.write(snippetJSON.toString());
        file.flush();
        file.close();
      } catch (IOException err) {
        err.printStackTrace();
      }
    }
  }

  private BlockPosition minimumPosition(BlockPosition positionLeast, BlockPosition positionMost, Grid grid) {
    BlockPosition minimumBlockPosition = new BlockPosition(grid.width, grid.height, grid.layers);
    for (int x = positionLeast.x; x < positionMost.x; x++) {
      for (int y = positionLeast.y; y < positionMost.y; y++) {
        for (int l = positionLeast.l; l < positionMost.l; l++) {
          Block block = grid.blockAt(x, y, l);
          if (block != null) {
            if (block.position.x < minimumBlockPosition.x) minimumBlockPosition.x = block.position.x;
            if (block.position.y < minimumBlockPosition.y) minimumBlockPosition.y = block.position.y;
            if (block.position.l < minimumBlockPosition.l) minimumBlockPosition.l = block.position.l;
          }
        }
      }
    }
    return minimumBlockPosition;
  }

  private BlockPosition maximumPosition(BlockPosition positionLeast, BlockPosition positionMost, Grid grid) {
    BlockPosition maximumBlockPosition = new BlockPosition(-1, -1, -1);
    for (int x = positionLeast.x; x < positionMost.x; x++) {
      for (int y = positionLeast.y; y < positionMost.y; y++) {
        for (int l = positionLeast.l; l < positionMost.l; l++) {
          Block block = grid.blockAt(x, y, l);
          if (block != null) {
            if (block.position.x > maximumBlockPosition.x) maximumBlockPosition.x = block.position.x;
            if (block.position.y > maximumBlockPosition.y) maximumBlockPosition.y = block.position.y;
            if (block.position.l > maximumBlockPosition.l) maximumBlockPosition.l = block.position.l;
          }
        }
      }
    }
    return maximumBlockPosition;
  }
}
