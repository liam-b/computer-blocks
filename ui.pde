class UI {
  Position offset;
  float spacing;

  float blockSize;
  float blockBackgroundSize;

  Position blockPosition;

  UI(Position offset_, float spacing_, float blockSize_, float blockBackgroundMargin) {
    offset = offset_;
    spacing = spacing_;

    blockSize = blockSize_;
    blockBackgroundSize = blockSize + blockBackgroundMargin;

    blockPosition = new Position(offset.x + blockSize / 2, offset.y - blockSize / 2);
  }

  void draw(Space space, Player player) {
    fill(COLOR_UI_BACKGROUND);
    rect(blockPosition.x, blockPosition.y, blockBackgroundSize, blockBackgroundSize);

    if (player.mode == EDIT) {
      // current block
      fill(COLOR_BACKGROUND);
      if (player.selectedType == CABLE) fill(COLOR_CABLE_OFF);
      if (player.selectedType == SOURCE) fill(COLOR_SOURCE);
      if (player.selectedType == INVERTER) fill(COLOR_INVERTER_ON);
      if (player.selectedType == VIA) fill(COLOR_VIA_OFF);
      if (player.selectedType == DELAY) fill(COLOR_DELAY_OFF);
      rect(blockPosition.x, blockPosition.y, blockSize, blockSize);

      if (player.selectedType == INVERTER || player.selectedType == DELAY) {
        fill(COLOR_SOURCE);
        if (player.selectedRotation == 1) rect(blockPosition.x + blockSize / 3, blockPosition.y, blockSize / 15, blockSize / 2);
        if (player.selectedRotation == 0) rect(blockPosition.x, blockPosition.y - blockSize / 3, blockSize / 2, blockSize / 15);
        if (player.selectedRotation == 3) rect(blockPosition.x - blockSize / 3, blockPosition.y, 50 / 15, 50 / 2);
        if (player.selectedRotation == 2) rect(blockPosition.x, blockPosition.y + blockSize / 3, blockSize / 2, blockSize / 15);
      }
    } else {
      if (player.mode == COPY || player.mode == COPY_STARTED) {
        // copy mode
        fill(COLOR_UI_COPY);
        rect(blockPosition.x, blockPosition.y, blockSize, blockSize);

        fill(COLOR_UI_BACKGROUND);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("C", blockPosition.x, blockPosition.y - blockSize / 14);
      }
      if (player.mode == PASTE) {
        // paste mode
        fill(COLOR_UI_PASTE);
        rect(blockPosition.x, blockPosition.y, blockSize, blockSize);

        fill(COLOR_UI_BACKGROUND);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("P", blockPosition.x, blockPosition.y - blockSize / 14);
      }
      if (player.mode == SAVE) {
        // save mode
        fill(COLOR_UI_PASTE);
        rect(blockPosition.x, blockPosition.y, blockSize, blockSize);

        fill(COLOR_UI_BACKGROUND);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("S", blockPosition.x, blockPosition.y - blockSize / 14);
      }
      if (player.mode == LOAD) {
        // load mode
        fill(COLOR_UI_PASTE);
        rect(blockPosition.x, blockPosition.y, blockSize, blockSize);

        fill(COLOR_UI_BACKGROUND);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("L", blockPosition.x, blockPosition.y - blockSize / 14);

        for(int i = 0; i < 10; i++) {
          fill(COLOR_UI_PASTE);
          rect(blockPosition.x - blockSize/4, blockPosition.y -  blockSize - (i * (blockSize/4 + spacing/4)), blockSize/2, blockSize/2);

          fill(COLOR_UI_BACKGROUND);
          textSize(10);
          textAlign(CENTER, CENTER);
          text(i, blockPosition.x - blockSize/4, blockPosition.y -  blockSize - (i * (blockSize/4 + spacing/4)) - blockSize/24);

          fill(COLOR_CABLE_OFF);
          textSize(15);
          textAlign(LEFT, CENTER);
          text("thingy", blockPosition.x - blockSize/4 + spacing/4, blockPosition.y -  blockSize - (i * (blockSize/4 + spacing/4)) - blockSize/24);
        }


      }
    }

    // layers
    fill(COLOR_UI_BACKGROUND);
    rect(blockPosition.x + spacing, blockPosition.y, blockBackgroundSize, blockBackgroundSize);

    fill(COLOR_CABLE_OFF);
    rect(blockPosition.x + spacing, blockPosition.y + blockSize / 2 - blockSize / space.layers / 2 - player.selectedLayer * (blockSize / space.layers), blockSize, blockSize / space.layers);
  }
}
