class Player {
  int selectedType;
  int selectedRotation;
  int selectedLayer;
  
  Player() {
    selectedType = CABLE;
    selectedRotation = 0;
    selectedLayer = 0;
  }
  
  void update(Space space) {
    if (key == '1') selectedType = EMPTY;
    if (key == '2') selectedType = CABLE;
    if (key == '3') selectedType = SOURCE;
    if (key == '4') selectedType = INVERTER;
    
    if (key == '0') selectedLayer = 0;
    if (key == '9') selectedLayer = 1;
    
    if (key == 'r') selectedRotation += 1;
    //if (key == '4') selectedType = TRIGGER;
    
    if (selectedRotation > 3) selectedRotation = 0;
    
    if (key == '0' || key == '9') {
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(selectedLayer);
    }
  }
}
