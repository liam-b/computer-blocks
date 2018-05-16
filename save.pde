void saveSpace(String path) {
  XML xml = new XML("space");
  xml.setInt("size", space.size);
  xml.setInt("layers", space.layers);

  xml.addChild("blocks");

  for (int l = 0; l < space.layers; l++) {
    for (int x = 0; x < space.size; x++) {
      for (int y = 0; y < space.size; y++) {
        if (space.blocks[l][x][y].type != EMPTY) {
          XML currentBlockXML = xml.getChild("blocks").addChild("block");
          Block currentBlock = space.blocks[l][x][y];
          currentBlockXML.setInt("type", currentBlock.type);
          currentBlockXML.setInt("charge", int(currentBlock.charge));
          currentBlockXML.setInt("lastCharge", int(currentBlock.lastCharge));

          setPosition(currentBlockXML.addChild("position"), currentBlock.position);

          if (currentBlock.inputs.size() != 0) {
            XML currentBlockInputsXML = currentBlockXML.addChild("inputs");
            for (int i = 0; i < currentBlock.inputs.size(); i++) {
              setPosition(currentBlockInputsXML.addChild("position"), currentBlock.inputs.get(i));
            }
          }
        }
      }
    }
  }

  saveXML(xml, path);
}

void loadSpace(String path) {
  XML xml = loadXML(path);

  space = new Space();

  space.size = xml.getInt("size");
  space.layers = xml.getInt("layers");
  space.blocks = new Block[space.layers][space.size][space.size];

  for (int l = 0; l < space.layers; l++) {
    for (int x = 0; x < space.size; x++) {
      for (int y = 0; y < space.size; y++) {
        space.blocks[l][x][y] = new Block(new BlockPosition(x, y, 0, l));
      }
    }
  }

  XML[] blocksXML = xml.getChild("blocks").getChildren("block");
  for (int b = 0; b < blocksXML.length; b++) {
    XML blockXML = blocksXML[b];
    BlockPosition newBlockPosition = getPosition(blockXML.getChild("position"));

    space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].position.r = newBlockPosition.r;
    space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].type = blockXML.getInt("type");
    space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].charge = boolean(blockXML.getInt("charge"));
    space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].lastCharge = boolean(blockXML.getInt("lastCharge"));

    XML[] blockInputsXML = blockXML.getChildren("inputs");
    for (int i = 0; i < blockInputsXML.length; i++) {
      XML blockInputXML = blockInputsXML[i].getChild("position");
      BlockPosition newBlockInput = getPosition(blockInputXML);

      space.blocks[newBlockPosition.l][newBlockPosition.x][newBlockPosition.y].inputs.add(newBlockInput);
    }
  }

  player = new Player(player);
  space.drawAllBlocks(player);
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
