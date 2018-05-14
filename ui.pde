class UI {
  Position blockPosition;
  float blockWidth;
  float blockBackgroundWidth;
  
  UI(Position blockPosition_, float blockWidth_, float blockBackgroundWidth_) {
    blockPosition = blockPosition_;
    blockWidth = blockWidth_;
    blockBackgroundWidth = blockBackgroundWidth_;
  }
  
  void draw() {
    fill(COLOR_BACKGROUND);
    rect(blockPosition.x, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);
    
    if (player.selectedType == CABLE) fill(COLOR_CABLE_OFF);
    if (player.selectedType == SOURCE) fill(COLOR_SOURCE);
    if (player.selectedType == INVERTER) fill(COLOR_INVERTER_OFF);
    if (player.selectedType == VIA) fill(COLOR_VIA_OFF);
    rect(blockPosition.x, blockPosition.y, blockWidth, blockWidth);
    
    fill(COLOR_SOURCE);
    if (player.selectedType == INVERTER) {
      if (player.selectedRotation == 1) rect(blockPosition.x + blockWidth / 3, blockPosition.y, blockWidth / 15, blockWidth / 2);
      if (player.selectedRotation == 0) rect(blockPosition.x, blockPosition.y - blockWidth / 3, blockWidth / 2, blockWidth / 15);
      if (player.selectedRotation == 3) rect(blockPosition.x - blockWidth / 3, blockPosition.y, 50 / 15, 50 / 2);
      if (player.selectedRotation == 2) rect(blockPosition.x, blockPosition.y + blockWidth / 3, blockWidth / 2, blockWidth / 15);
    }
  }
}
