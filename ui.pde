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
      if (player.selectedType == CABLE) drawUiBlock(COLOR_CABLE_OFF, false);
      if (player.selectedType == SOURCE) drawUiBlock(COLOR_SOURCE, false);
      if (player.selectedType == INVERTER) drawUiBlock(COLOR_INVERTER_ON, true);
      if (player.selectedType == VIA) drawUiBlock(COLOR_VIA_OFF, false);
      if (player.selectedType == DELAY) drawUiBlock(COLOR_DELAY_OFF, true);

    } else {
      if (player.mode == COPY || player.mode == COPY_STARTED) {
        drawModeIcon("C", COLOR_UI_PASTE, COLOR_UI_BACKGROUND);
      }
      if (player.mode == PASTE) {
        drawModeIcon("P", COLOR_UI_PASTE, COLOR_UI_BACKGROUND);
      }
      if (player.mode == SAVE) {
        drawModeIcon("S", COLOR_UI_PASTE, COLOR_UI_BACKGROUND);
        drawSaveNameList();
      }
      if (player.mode == LOAD) {
        drawModeIcon("L", COLOR_UI_PASTE, COLOR_UI_BACKGROUND);
        drawSaveNameList();
      }
      if (player.mode == MENU_MAIN) {
        drawModeIcon("M", COLOR_UI_MENU_SELECTION, COLOR_UI_BACKGROUND);
        drawMainMenu();
      }
    }
    drawUiLayers(space.layers);
  }
}
