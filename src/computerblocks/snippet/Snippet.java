package computerblocks.snippet;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import computerblocks.Grid;
import computerblocks.block.*;
import computerblocks.position.*;

public class Snippet {
  public static final String SAVE_PATH = "./";
  public static final String SNIPPET_PATH = "./";
  public static final String EXTENSION = ".snip";

  public Block[][][] blocks;
  public int width, height, layers;

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

      this.width = snippetJSON.getInt("width");
      this.height = snippetJSON.getInt("height");
      this.layers = snippetJSON.getInt("layers");
      this.blocks = JSON.JSONToBlocks(snippetJSON.getJSONArray("blocks"), width, height, layers);
    }
  }

  public Snippet(BlockPosition positionA_, BlockPosition positionB_, Grid grid) {
    BlockPosition positionLeast = new BlockPosition(Math.min(positionA_.x, positionB_.x), Math.min(positionA_.y, positionB_.y), Math.min(positionA_.l, positionB_.l));
    BlockPosition positionMost = new BlockPosition(Math.max(positionA_.x, positionB_.x), Math.max(positionA_.y, positionB_.y), Math.max(positionA_.l, positionB_.l));
    BlockPosition size = positionMost.subtract(positionLeast).add(new BlockPosition(1, 1, 1));

    // could see weird behaviour as Block references are being passed into this new Block array

    this.blocks = new Block[size.x][size.y][size.l];
    for (int x = positionLeast.x; x <= positionMost.x; x++) {
      for (int y = positionLeast.y; y <= positionMost.y; y++) {
        for (int l = positionLeast.l; l <= positionMost.l; l++) {
          blocks[x - positionLeast.x][y - positionLeast.y][l - positionLeast.l] = grid.blockAt(new BlockPosition(x, y, l));
        }
      }
    }

    width = size.x;
    height = size.y;
    layers = size.l;
  }

  public void saveToFile(String path, String name) {
    JSONObject snippetJSON = new JSONObject();
    snippetJSON.put("name", name);
    snippetJSON.put("width", width);
    snippetJSON.put("height", height);
    snippetJSON.put("layers", layers);
    snippetJSON.put("blocks", JSON.blocksToJSON(blocks));

    try (FileWriter file = new FileWriter(path + name + EXTENSION)) {
      file.write(snippetJSON.toString());
      file.flush();
    } catch (IOException err) {
      err.printStackTrace();
    }
  }
}