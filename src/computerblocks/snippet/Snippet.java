package computerblocks.snippet;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import computerblocks.Grid;
import computerblocks.block.*;

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