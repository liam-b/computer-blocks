package computerblocks.snippet;

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
}