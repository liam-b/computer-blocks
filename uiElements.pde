void drawMainMenu() {

}

void drawUiBlock(color blockColor, boolean hasRotation) {
  fill(blockColor);
  rect(ui.blockPosition.x, ui.blockPosition.y, ui.blockSize, ui.blockSize);
  if(hasRotation) {
    drawUiBlockRot();
  }
}

void drawUiBlockRot() {
  fill(COLOR_SOURCE);
  if (player.selectedRotation == 1) rect(ui.blockPosition.x + ui.blockSize / 3, ui.blockPosition.y, ui.blockSize / 15, ui.blockSize / 2);
  if (player.selectedRotation == 0) rect(ui.blockPosition.x, ui.blockPosition.y - ui.blockSize / 3, ui.blockSize / 2, ui.blockSize / 15);
  if (player.selectedRotation == 3) rect(ui.blockPosition.x - ui.blockSize / 3, ui.blockPosition.y, 50 / 15, 50 / 2);
  if (player.selectedRotation == 2) rect(ui.blockPosition.x, ui.blockPosition.y + ui.blockSize / 3, ui.blockSize / 2, ui.blockSize / 15);
}

void drawModeIcon(String textIcon, color backColor, color textColor) {
  fill(backColor);
  rect(ui.blockPosition.x, ui.blockPosition.y, ui.blockSize, ui.blockSize);

  fill(textColor);
  textSize(20);
  textAlign(CENTER, CENTER);
  text(textIcon, ui.blockPosition.x, ui.blockPosition.y - ui.blockSize / 14);
}

void drawSaveNameList() {
  for(int i = 0; i < 10; i++) {
    fill(COLOR_UI_PASTE);
    rect(ui.blockPosition.x - ui.blockSize/4, ui.blockPosition.y -  ui.blockSize - (i * (ui.blockSize/4 + ui.spacing/4)), ui.blockSize/2, ui.blockSize/2);

    fill(COLOR_UI_BACKGROUND);
    textSize(10);
    textAlign(CENTER, CENTER);
    text(i, ui.blockPosition.x - ui.blockSize/4, ui.blockPosition.y -  ui.blockSize - (i * (ui.blockSize/4 + ui.spacing/4)) - ui.blockSize/24);

    // textAlign(LEFT, CENTER);
    // fill(COLOR_BACKGROUND);
    // rect(ui.blockPosition.x - ui.blockSize/4 + ui.spacing/4 + textWidth(saveNames[i])/2, ui.blockPosition.y -  ui.blockSize - (i * (ui.blockSize/4 + ui.spacing/4)) - ui.blockSize/24, textWidth(saveNames[i]) + 10, 20);

    fill(COLOR_CABLE_OFF);
    textSize(15);
    textAlign(LEFT, CENTER);
    text(saveNames[i], ui.blockPosition.x - ui.blockSize/4 + ui.spacing/4, ui.blockPosition.y -  ui.blockSize - (i * (ui.blockSize/4 + ui.spacing/4)) - ui.blockSize/24);
  }
}

void drawUiLayers(int numOfLayers, int selectedLayer) {
  // layers
  fill(COLOR_UI_BACKGROUND);
  rect(ui.blockPosition.x + ui.spacing, ui.blockPosition.y, ui.blockBackgroundSize, ui.blockBackgroundSize);

  fill(COLOR_CABLE_OFF);
  rect(ui.blockPosition.x + ui.spacing, ui.blockPosition.y + ui.blockSize / 2 - ui.blockSize / numOfLayers / 2 - selectedLayer * (ui.blockSize / numOfLayers), ui.blockSize, ui.blockSize / numOfLayers);
}
