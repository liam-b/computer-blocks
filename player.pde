int EDIT = 0;
int COPY = 1;
int COPY_STARTED = 2;
int COPY_ENDED = 3;
int PASTE = 4;
int SAVE = 5;
int LOAD = 6;

class Player {
  int selectedType;
  int selectedRotation;
  int selectedLayer;

  int mode;
  int lastMode;
  BlockPosition selectionA;
  BlockPosition selectionB;
  BlockPosition[][][] selection;

  Position translate;
  Position oldTranslate;
  float zoom;

  Player() {
    selectedType = CABLE;
    selectedRotation = 0;
    selectedLayer = 0;

    mode = EDIT;
    translate = new Position(0, 0);
    oldTranslate = new Position(0, 0);
    zoom = 2;
  }

  Player(Player oldPlayer) {
    selectedType = oldPlayer.selectedType;
    selectedRotation = oldPlayer.selectedRotation;
    selectedLayer = oldPlayer.selectedLayer;

    mode = EDIT;
    translate = oldPlayer.translate;
    oldTranslate = oldPlayer.oldTranslate;
    zoom = oldPlayer.zoom;
  }

  void update(Space space) {
    if (mode != SAVE && mode != LOAD) {
      if (key == '1') selectedType = EMPTY;
      if (key == '2') selectedType = CABLE;
      if (key == '3') selectedType = SOURCE;
      if (key == '4') selectedType = INVERTER;
      if (key == '5') selectedType = VIA;
      if (key == '6') selectedType = DELAY;
    }

    if (key == '[') selectedLayer -= 1;
    if (key == ']') selectedLayer += 1;

    if (key == 'r') selectedRotation += 1;

    if (mode == EDIT && key == 'c') mode = COPY;
    if (mode == EDIT && key == 'x') space.unselectAllBlocks(this);
    if (mode == EDIT && key == 'p') mode = PASTE;

    if (mode == EDIT && key == 's') mode = SAVE;
    if (mode == EDIT && key == 'l') mode = LOAD;

    if (mode == SAVE) {
      if (key >= '0' && key <= '9') {
        saveSpace(SAVE_FILE + "_" + key + ".xml");
      }
    }

    if (mode == LOAD) {
      if (key >= '0' && key <= '9') {
        loadSpace(SAVE_FILE + "_" + key + ".xml");
      }
    }

    if (selectedRotation > 3) selectedRotation = 0;

    if (key == '[' || key == ']') {
      if (selectedLayer < 0) selectedLayer = 0;
      if (selectedLayer > space.layers - 1) selectedLayer = space.layers - 1;

      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
      ui.draw(space, this);
    }
  }

  void deupdate(Space space) {
    if (mode == SAVE && key == 's') {
      mode = EDIT;
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(this);
    }
    if (mode == LOAD && key == 'l') {
      mode = EDIT;
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(this);
    }
  }

  void selectionUpdate(Space space) {
    if (mode == COPY_STARTED && mouseButton == LEFT) mode = COPY_ENDED;
    if (mode == COPY && mouseButton == LEFT) mode = COPY_STARTED;

    if (mode == COPY_STARTED) {
      if (selection != null) space.unselectAllBlocks(this);
      selectionA = findClickedBlock(space);
      if (selectionA == null) mode = EDIT;
    }
    if (mode == COPY_ENDED) {
      selectionB = findClickedBlock(space);

      if (selectionB == null) {
        mode = EDIT;
        selection = null;
      }
      else selection = getSelection(selectionA, selectionB, space);
    }
    if (mode == PASTE) {
      BlockPosition pastePosition = findClickedBlock(space);
      if (pastePosition != null) pasteSelection(space, selection, pastePosition);
    }
  }

  void resetMode() {
    if (mode == COPY_ENDED) mode = EDIT;
    if (mode == PASTE) mode = EDIT;
  }

  void updateTranslate() {
    if (mousePressed && mouseButton == RIGHT) {
      translate.x = mouseX - oldTranslate.x;
      translate.y = mouseY - oldTranslate.y;

      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
    }
  }

  void updateScroll() {
    background(COLOR_BACKGROUND);
    space.drawAllBlocks(player);
  }

  void resetTranslate() {
    if (mouseButton == RIGHT) {
      oldTranslate.x = mouseX - oldTranslate.x;
      oldTranslate.y = mouseY - oldTranslate.y;
    }
  }

  BlockPosition findClickedBlock(Space space) {
    for (int x = 0; x < space.size; x++) {
      for (int y = 0; y < space.size; y++) {
        if (space.blocks[player.selectedLayer][x][y].mouseOver(this) && mouseButton == LEFT) return space.blocks[player.selectedLayer][x][y].position;
      }
    }
    return null;
  }

  BlockPosition[][][] getSelection(BlockPosition positionA, BlockPosition positionB, Space space) {
    int selectionOffsetX = min(positionA.x, positionB.x);
    int selectionOffsetY = min(positionA.y, positionB.y);

    BlockPosition[][][] foundSelection = new BlockPosition[space.layers][max(positionA.x, positionB.x) - min(positionA.x, positionB.x) + 1][max(positionA.y, positionB.y) - min(positionA.y, positionB.y) + 1];

    for (int l = 0; l < space.layers; l++) {
      for (int x = min(positionA.x, positionB.x); x <= max(positionA.x, positionB.x); x++) {
        for (int y = min(positionA.y, positionB.y); y <= max(positionA.y, positionB.y); y++) {
          foundSelection[l][x - selectionOffsetX][y - selectionOffsetY] = space.blocks[l][x][y].position.duplicate();
          space.blocks[l][x][y].selected = true;
          space.blocks[l][x][y].draw(this);
        }
      }
    }
    return foundSelection;
  }

  void pasteSelection(Space space, BlockPosition[][][] selection, BlockPosition pos) {
    for (int l = 0; l < space.layers; l++) {
      for (int x = pos.x; x < pos.x + selection[l].length; x++) {
        for (int y = pos.y; y < pos.y + selection[l][x - pos.x].length; y++) {
          BlockPosition selectionBlockPosition = selection[l][x - pos.x][y - pos.y];
          Block selectionBlock = space.blocks[selectionBlockPosition.l][selectionBlockPosition.x][selectionBlockPosition.y];

          space.blocks[l][x][y].type = selectionBlock.type;
          space.blocks[l][x][y].position.r = selectionBlockPosition.r;
          space.blocks[l][x][y].charge = selectionBlock.charge;
          space.blocks[l][x][y].lastCharge = selectionBlock.lastCharge;

          BlockPosition positionDifference = new BlockPosition(space.blocks[l][x][y].position.x - selectionBlock.position.x, space.blocks[l][x][y].position.y - selectionBlock.position.y, 0, 0);
          space.blocks[l][x][y].inputs = new ArrayList<BlockPosition>();
          for (int i = 0; i < selectionBlock.inputs.size(); i++) {
            BlockPosition newInput = selectionBlock.inputs.get(i).duplicate();
            newInput.x += positionDifference.x;
            newInput.y += positionDifference.y;

            space.blocks[l][x][y].inputs.add(newInput);
          }

          space.blocks[l][x][y].draw(this);
        }
      }
    }
  }
}
