int EDIT = 0;
int COPY = 1;
int COPY_STARTED = 2;
int COPY_ENDED = 3;
int PASTE = 4;

class Player {
  int selectedType;
  int selectedRotation;
  int selectedLayer;
  int size;
  int layers;

  int mode;
  BlockPosition selectionA;
  BlockPosition selectionB;
  Block[][][] selection;

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

    mode = EDIT;
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

    if (mode == EDIT && key == 'c') mode = COPY;
    if (mode == EDIT && key == 'x') space.unselectAllBlocks(this);
    if (mode == EDIT && key == 'p') mode = PASTE;

    if (selectedRotation > 3) selectedRotation = 0;

    if (key == '[' || key == ']') {
      if (selectedLayer < 0) selectedLayer = 0;
      if (selectedLayer > layers - 1) selectedLayer = layers - 1;

      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
      ui.draw(this);
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

  void selectionReset() {
    if (mode == COPY_ENDED) mode = EDIT;
    if (mode == PASTE) mode = EDIT;
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

  BlockPosition findClickedBlock(Space space) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        if (space.blocks[player.selectedLayer][x][y].mouseOver(this) && mouseButton == LEFT) return space.blocks[player.selectedLayer][x][y].position;
      }
    }
    return null;
  }

  Block[][][] getSelection(BlockPosition positionA, BlockPosition positionB, Space space) {
    int selectionOffsetX = min(positionA.x, positionB.x);
    int selectionOffsetY = min(positionA.y, positionB.y);

    Block[][][] foundSelection = new Block[space.layers][max(positionA.x, positionB.x) - min(positionA.x, positionB.x) + 1][max(positionA.y, positionB.y) - min(positionA.y, positionB.y) + 1];

    for (int l = 0; l < space.layers; l++) {
      for (int x = min(positionA.x, positionB.x); x <= max(positionA.x, positionB.x); x++) {
        for (int y = min(positionA.y, positionB.y); y <= max(positionA.y, positionB.y); y++) {
          foundSelection[l][x - selectionOffsetX][y - selectionOffsetY] = space.blocks[l][x][y];
          space.blocks[l][x][y].selected = true;
          space.blocks[l][x][y].draw(this);
        }
      }
    }
    return foundSelection;
  }

  void pasteSelection(Space space, Block[][][] selection, BlockPosition pos) {
    //ArrayList<BlockPosition> updateQueue = new ArrayList<BlockPosition>();

    for (int l = 0; l < space.layers; l++) {
      for (int x = pos.x; x < pos.x + selection[l].length; x++) {
        for (int y = pos.y; y < pos.y + selection[l][x - pos.x].length; y++) {
          Block selectionBlock = selection[l][x - pos.x][y - pos.y];
          //Block spaceBlock = space.blocks[l][x][y];

          space.blocks[l][x][y].type = selectionBlock.type;
          space.blocks[l][x][y].position.r = selectionBlock.position.r;
          space.blocks[l][x][y].charge = selectionBlock.charge;
          space.blocks[l][x][y].lastCharge = selectionBlock.lastCharge;

          BlockPosition positionDifference = new BlockPosition(space.blocks[l][x][y].position.x - selectionBlock.position.x, space.blocks[l][x][y].position.y - selectionBlock.position.y, 0, 0);
          space.blocks[l][x][y].inputs = new ArrayList<BlockPosition>();
          for (int i = 0; i < selectionBlock.inputs.size(); i++) {
            BlockPosition newInput = selectionBlock.inputs.get(i);
            newInput.x += positionDifference.x;
            newInput.y += positionDifference.y;

            space.blocks[l][x][y].inputs.add(newInput);
            //spaceBlock.position.r = 0; //<>// //<>//
          }

          //if (selectionBlock.type == SOURCE) updateQueue.add(selectionBlock.position);

          space.blocks[l][x][y].draw(this);
        }
      }
    }

    //for (int j = 0; j < updateQueue.size(); j++) {
    //  BlockPosition currentUpdate = updateQueue.get(j);
    //  space.blocks[currentUpdate.l][currentUpdate.x][currentUpdate.y].update(this, space, currentUpdate);
    //  space.blocks[currentUpdate.l][currentUpdate.x][currentUpdate.y].update(this, space, currentUpdate);
    //}
  }
}
