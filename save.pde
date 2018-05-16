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
    xml = new XML("Space");
    xml.setInt("size", space.size);
    xml.setInt("layers", space.layers);

    xml.addChild("blocks");

    for (int l = 0; l < space.layers; l++) {
      for (int x = 0; x < space.size; x++) {
        for (int y = 0; y < space.size; y++) {
          XML currentBlockXML = xml.getChild("blocks").addChild("Block");
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

  void setPosition(XML positionXML, BlockPosition position) {
    positionXML.setInt("x", position.x);
    positionXML.setInt("y", position.y);
    positionXML.setInt("r", position.r);
    positionXML.setInt("l", position.l);
  }

  void saveToFile(String path) {
    saveXML(xml, path);
  }
}