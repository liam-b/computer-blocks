package computerblocks.snippet;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import computerblocks.block.*;
import computerblocks.position.*;

public class JSON {
  public static JSONArray blocksToJSON(Block[][][] blocks) {
    int index = 0;
    JSONArray blocksJSON = new JSONArray();
    for (int x = 0; x < blocks.length; x++) {
      for (int y = 0; y < blocks[x].length; y++) {
        for (int l = 0; l < blocks[x][y].length; l++) {
          if (blocks[x][y][l] != null) {
            Block block = blocks[x][y][l];

            JSONObject blockJSON = new JSONObject();
            blockJSON.put("type", block.type.ordinal());
            blockJSON.put("position", getJSONFromPosition(block.position));
            if (block.type == BlockType.LABEL) blockJSON.put("labelText", block.labelText);

            JSONArray inputsJSON = new JSONArray();
            for (int j = 0; j < block.inputs.size(); j++) {
              inputsJSON.put(j, getJSONFromPosition(block.inputs.get(j).position));
            }
            blockJSON.put("inputs", inputsJSON);

            blockJSON.put("charge", block.charge);
            blockJSON.put("lastCharge", block.lastCharge);
            blockJSON.put("tickCharge", block.tickCharge);

            blocksJSON.put(index, blockJSON);
            index += 1;
          }
        }
      }
    }
    return blocksJSON;
  }

  private static JSONObject getJSONFromPosition(BlockPosition position) {
    JSONObject json = new JSONObject();
    json.put("x", position.x);
    json.put("y", position.y);
    json.put("r", position.r.ordinal());
    json.put("l", position.l);
    return json;
  }

  public static Block[][][] JSONToBlocks(JSONArray blocksJSON, int width, int height, int layers) {
    Block[][][] blocks = new Block[width][height][layers];
    for (int i = 0; i < blocksJSON.length(); i++) {
      JSONObject blockJSON = blocksJSON.getJSONObject(i);
      Block block = Block.fromType(BlockType.values()[blockJSON.getInt("type")], getPositionFromJSON(blockJSON.getJSONObject("position")));
      block.charge = blockJSON.getBoolean("charge");
      block.lastCharge = blockJSON.getBoolean("lastCharge");
      block.tickCharge = blockJSON.getBoolean("tickCharge");
      if (block.type == BlockType.LABEL) block.labelText = blockJSON.getString("labelText");

      ArrayList<BlockPosition> inputs = new ArrayList<BlockPosition>();
      JSONArray inputsJSON = blockJSON.getJSONArray("inputs");
      for (int j = 0; j < inputsJSON.length(); j++) {
        inputs.add(getPositionFromJSON(inputsJSON.getJSONObject(j)));
      }
      block.saveInputPositions = inputs;

      blocks[block.position.x][block.position.y][block.position.l] = block;
    }

    for (int x = 0; x < blocks.length; x++) {
      for (int y = 0; y < blocks[x].length; y++) {
        for (int l = 0; l < blocks[x][y].length; l++) {
          if (blocks[x][y][l] != null && blocks[x][y][l].saveInputPositions != null) {
            for (BlockPosition inputPosition : blocks[x][y][l].saveInputPositions) {
              Block inputBlock = blocks[inputPosition.x][inputPosition.y][inputPosition.l];
              if (inputBlock != null) {
                blocks[x][y][l].inputs.add(inputBlock);
              }
            }
            blocks[x][y][l].saveInputPositions = null;
          }
        }
      }
    }

    return blocks;
  }

  private static BlockPosition getPositionFromJSON(JSONObject json) {
    return new BlockPosition(json.getInt("x"), json.getInt("y"), Rotation.values()[json.getInt("r")], json.getInt("l"));
  }
}
