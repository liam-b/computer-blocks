class Player {
  int selectedType;
  int selectedRotation;
  int selectedLayer;
  int size;
  int layers;
  
  float translateX;
  float translateY;
  
  float oldTranslateX;
  float oldTranslateY;
  
  float scrollValue;
  
  Player(int size_, int layers_) {
    selectedType = CABLE;
    selectedRotation = 0;
    selectedLayer = 0;
    size = size_;
    layers = layers_;
    
    translateX = 0;
    translateX = 0;
    
    oldTranslateX = 0;
    oldTranslateX = 0;
    
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
      println(selectedLayer);
      
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
    }
  }
  
  void cameraUpdate() {
    if (mousePressed && mouseButton == RIGHT) {
      translateX = mouseX - oldTranslateX;
      translateY = mouseY - oldTranslateY;
      
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
    }
  }
  
  void scrollUpdate() {
    background(COLOR_BACKGROUND);
    space.drawAllBlocks(player);
  }
  
  void cameraRelease() {
    if (mouseButton == RIGHT) {
      oldTranslateX = mouseX - oldTranslateX;
      oldTranslateY = mouseY - oldTranslateY;
    }
  }
  
  void cameraPress() {
    if (mouseButton == RIGHT) {
      oldTranslateX = mouseX - oldTranslateX;
      oldTranslateY = mouseY - oldTranslateY;
    }
  }
}
