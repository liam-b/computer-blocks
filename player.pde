class Player {
  int selectedType;
  int selectedRotation;
  int selectedLayer;
  int size;
  int layers;
  
  Position translate;
  Position oldTranslate;
  
  float scrollValue;
  
  Player(Space space) {
    selectedType = CABLE;
    selectedRotation = 0;
    selectedLayer = 0;
    size = space.size;
    layers = space.layers;
    
    translate = new Position(0, 0);
    oldTranslate = new Position(0, 0);
    scrollValue = 3;
  }
  
  void update(Space space) {
    if (key == '1') selectedType = EMPTY;
    if (key == '2') selectedType = CABLE;
    if (key == '3') selectedType = SOURCE;
    if (key == '4') selectedType = INVERTER;
    if (key == '5') selectedType = VIA;
    
    if (key == '[') selectedLayer -= 1;
    if (key == ']') selectedLayer += 1;
    
    if (key == 'r') selectedRotation += 1;
    
    if (selectedRotation > 3) selectedRotation = 0;
    
    if (key == '[' || key == ']') {
      if (selectedLayer < 0) selectedLayer = 0;
      if (selectedLayer > layers - 1) selectedLayer = layers - 1;
      
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
    }
  }
  
  void cameraUpdate() {
    if (mousePressed && mouseButton == RIGHT) {
      translate.x = mouseX - oldTranslate.x;
      translate.y = mouseY - oldTranslate.y;
      
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
    }
  }
  
  void scrollUpdate() {
    background(COLOR_BACKGROUND);
    space.drawAllBlocks(player);
  }
  
  void resetTranslate() {
    if (mouseButton == RIGHT) {
      oldTranslate.x = mouseX - oldTranslate.x;
      oldTranslate.y = mouseY - oldTranslate.y;
    }
  }
}
