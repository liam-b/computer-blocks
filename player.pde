class Player {
  int selectedType;
  int selectedRotation;
  int selectedLayer;
  int size;
  
  float translateX;
  float translateY;
  
  float oldTranslateX;
  float oldTranslateY;
  
  Player(int size_) {
    selectedType = CABLE;
    selectedRotation = 0;
    selectedLayer = 0;
    size = size_;
    
    translateX = 0;
    translateX = 0;
    
    oldTranslateX = 0;
    oldTranslateX = 0;
  }
  
  void update(Space space) {
    if (key == '1') selectedType = EMPTY;
    if (key == '2') selectedType = CABLE;
    if (key == '3') selectedType = SOURCE;
    if (key == '4') selectedType = INVERTER;
    if (key == '5') selectedType = VIA;
    
    if (key == '0') selectedLayer = 0;
    if (key == '9') selectedLayer = 1;
    
    if (key == 'r') selectedRotation += 1;
    
    if (selectedRotation > 3) selectedRotation = 0;
    
    if (key == '0' || key == '9') {
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
  
  void cameraRelease() {
    oldTranslateX = mouseX - oldTranslateX;
    oldTranslateY = mouseY - oldTranslateY;
  }
  
  void cameraPress() {
    oldTranslateX = mouseX - oldTranslateX;
    oldTranslateY = mouseY - oldTranslateY;
  }
}
