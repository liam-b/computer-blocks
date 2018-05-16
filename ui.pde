class UI {
  Position blockPosition;
  float blockWidth;
  float blockBackgroundWidth;
  float blockMargin = 25;

  UI(Position blockPosition_, float blockWidth_, float blockBackgroundWidth_) {
    blockPosition = blockPosition_;
    blockWidth = blockWidth_;
    blockBackgroundWidth = blockBackgroundWidth_;
  }

  void draw(Player player) {
    fill(COLOR_UI_BACKGROUND);
    rect(blockPosition.x, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);

    fill(COLOR_BACKGROUND);
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

    fill(COLOR_UI_BACKGROUND);
    rect(blockPosition.x + blockWidth + blockMargin, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);

    fill(COLOR_UI_BACKGROUND);
    rect(blockPosition.x + blockWidth + blockMargin, blockPosition.y, blockWidth, blockWidth);

    //Draw Layers
    textAlign(CENTER, CENTER);
    fill(COLOR_CABLE_OFF);
    rect(blockPosition.x + blockWidth + blockMargin, blockPosition.y + blockWidth/2 - blockWidth/SPACE_LAYERS/2 - player.selectedLayer*(blockWidth/SPACE_LAYERS), blockWidth, blockWidth/SPACE_LAYERS);

    //Draw Copy
    if (player.mode == COPY || player.mode == COPY_STARTED) {
      fill(COLOR_UI_BACKGROUND);
      rect(blockPosition.x, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);
      fill(COLOR_UI_COPY);
      rect(blockPosition.x, blockPosition.y, blockWidth, blockWidth);

      fill(COLOR_UI_BACKGROUND);
      text("C", blockPosition.x, blockPosition.y - blockWidth/16);
    }

    //Draw Paste
    if (player.mode == PASTE) {
      fill(COLOR_UI_BACKGROUND);
      rect(blockPosition.x, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);
      fill(COLOR_UI_PASTE);
      rect(blockPosition.x, blockPosition.y, blockWidth, blockWidth);

      fill(COLOR_UI_BACKGROUND);
      text("P", blockPosition.x, blockPosition.y - blockWidth/16);
    }

  }
}
