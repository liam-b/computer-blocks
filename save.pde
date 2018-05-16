String testString = "<blocks>" +
  "<Block>" +
    "<BlockPosition>" +
      "<x>0</x>" +
      "<y>0</y>" +
      "<r>0</r>" +
      "<l>0</l>" +
    "</BlockPosition>" +
    "<type>1</type>" +
    "<charge>false</charge>" +
    "<lastCharge>false</lastCharge>" +
    "<inputs>" +
      "<BlockPosition>" +
        "<x>0</x>" +
        "<y>1</y>" +
        "<r>0</r>" +
        "<l>0</l>" +
      "</BlockPosition>" +
    "</inputs>" +
  "</Block>" +
"</blocks>";

class Save {
  XML xml;

  Save(Space space) {
    xml = new XML("space");
    xml.setInt("size", space.size);
    xml.setInt("layers", space.layers);

    xml.addChild("blocks");

    for (int l = 0; l < space.layers; l++) {
      for (int x = 0; x < space.size; x++) {
        for (int y = 0; y < space.size; y++) {
          XML currentBlockXML = xml.getChild("blocks").addChild("block");
          Block currentBlock = space.blocks[l][x][y];
          currentBlockXML.setInt("type", currentBlock.type);
          currentBlockXML.setInt("charge", int(currentBlock.charge));
          currentBlockXML.setInt("lastCharge", int(currentBlock.lastCharge));

          setPosition(currentBlockXML.addChild("position"), currentBlock.position);

          XML currentBlockInputsXML = currentBlockXML.addChild("inputs");
          for (int i = 0; i < currentBlock.inputs.size(); i++) {
            setPosition(currentBlockInputsXML.addChild("position"), currentBlock.inputs.get(i));
          }
        }
      }
    }
  }

  Save(String path) {
    XML xml = loadXML(path);

    space = new Space(SPACE_SPACING);

    space.size = xml.getInt("size");
    space.layers = xml.getInt("layers");
    space.blocks = new Block[space.layers][space.size][space.size];

    XML[] blocksXML = xml.getChild("blocks").getChildren("block");
    for (int b = 0; b < blocksXML.length; b++) {
      XML blockXML = blocksXML[b];
      BlockPosition newBlockPosition = getPosition(blockXML.getChild("position"));

      space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y] = new Block(newBlockPosition, space, space.spacing);
      space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].type = blockXML.getInt("type");
      space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].charge = boolean(blockXML.getInt("charge"));
      space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].lastCharge = boolean(blockXML.getInt("lastCharge"));
      
      XML[] blockInputsXML = blockXML.getChildren("inputs");
      for (int i = 0; i < blockInputsXML.length; i++) {
        XML blockInputXML = blockInputsXML[i];
        BlockPosition newBlockInput = getPosition(blockInputXML);
        
        space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].inputs.add(newBlockInput);
      }
      
      space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].draw(player);
    }

    player = new Player(space);
  }

  void setPosition(XML positionXML, BlockPosition position) {
    positionXML.setInt("x", position.x);
    positionXML.setInt("y", position.y);
    positionXML.setInt("r", position.r);
    positionXML.setInt("l", position.l);
  }

  BlockPosition getPosition(XML positionXML) {
    return new BlockPosition(
      positionXML.getInt("x"),
      positionXML.getInt("y"),
      positionXML.getInt("r"),
      positionXML.getInt("l")
    );
  }

  void saveToFile(String path) {
    saveXML(xml, path);
  }
}
