class UI {
  Position blockPosition;
  float blockWidth;
  float blockBackgroundWidth;
  float blockMargin = 25;

  //hi

  int EDIT = 0;
  int COPY = 1;
  int COPY_STARTED = 2;
  int COPY_ENDED = 3;
  int PASTE = 4;

  UI(Position blockPosition_, float blockWidth_, float blockBackgroundWidth_) {
    blockPosition = blockPosition_;
    blockWidth = blockWidth_;
    blockBackgroundWidth = blockBackgroundWidth_;
  }

  void draw(Player player) {
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

    fill(COLOR_BACKGROUND);
    rect(blockPosition.x + blockWidth + blockMargin, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);

    fill(COLOR_CABLE_OFF);
    rect(blockPosition.x + blockWidth + blockMargin, blockPosition.y, blockWidth, blockWidth);

    textAlign(CENTER, CENTER);
    fill(COLOR_BACKGROUND);
    textSize(30);
    text(str(player.selectedLayer + 1), blockPosition.x + blockWidth + blockMargin, blockPosition.y - blockWidth/16);

    if (player.mode == COPY || player.mode == COPY_STARTED) {
      fill(COLOR_BACKGROUND);
      rect(blockPosition.x, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);
      fill(COLOR_CABLE_OFF);
      rect(blockPosition.x, blockPosition.y, blockWidth, blockWidth);

      fill(COLOR_BACKGROUND);
      if (player.mode == COPY) text("C", blockPosition.x, blockPosition.y - blockWidth/16);
      if (player.mode == COPY_STARTED) text("Cs", blockPosition.x, blockPosition.y - blockWidth/16);
    }

    if (player.mode == PASTE) {
      fill(COLOR_BACKGROUND);
      rect(blockPosition.x, blockPosition.y, blockWidth + blockBackgroundWidth, blockWidth + blockBackgroundWidth);
      fill(COLOR_CABLE_OFF);
      rect(blockPosition.x, blockPosition.y, blockWidth, blockWidth);

      fill(COLOR_BACKGROUND);
      text("P", blockPosition.x, blockPosition.y - blockWidth/16);
    }

  }
}
