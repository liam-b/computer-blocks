int EMPTY = 0;
int CABLE = 1;
int SOURCE = 2;
int INVERTER = 3;

class Block {
  int type;
  boolean lock;
  boolean charge;
  boolean lastCharge;
  int rotation;
  
  int drawOffset;
  int sizeRatio;
  int drawSize;
  int size;
  int spacing;
  
  Position position;
  
  ArrayList<Position> inputs;
  ArrayList<Position> updaters;
  
  Block(Position position_, int size_, int spacing_) {
    type = EMPTY;
    lock = false;
    charge = false;
    lastCharge = false;
    rotation = 0;
    
    size = size_;
    sizeRatio = width / size;
    drawOffset = sizeRatio / 2;
    drawSize = sizeRatio - spacing_;
    spacing = spacing_;
    
    position = position_;
    
    inputs = new ArrayList<Position>();
    updaters = new ArrayList<Position>();
  }
  
  void draw() {
    if (type != EMPTY) {
      if (charge == true) {
        if (type == CABLE) fill(COLOR_CABLE_ON);
        if (type == SOURCE) fill(COLOR_SOURCE);
        if (type == INVERTER) fill(COLOR_INVERTER_ON);
      }
      else {
        if (type == CABLE) fill(COLOR_CABLE_OFF);
        if (type == SOURCE) fill(COLOR_SOURCE);
        if (type == INVERTER) fill(COLOR_INVERTER_OFF);
      }
    }
    else fill(COLOR_EMPTY);
    
    rect(drawOffset + sizeRatio * position.x, drawOffset + sizeRatio * position.y, drawSize, drawSize);
    
    if (type == INVERTER) {
      fill(COLOR_SOURCE);
      
      int barOffset = sizeRatio / 2 - drawSize + drawSize / 15 / 2;
      
      if (rotation == 3) rect(drawOffset + sizeRatio * position.x - barOffset, drawOffset + sizeRatio * position.y, drawSize / 15, drawSize / 2);
      if (rotation == 2) rect(drawOffset + sizeRatio * position.x, drawOffset + sizeRatio * position.y + barOffset, drawSize / 2, drawSize / 15);
      if (rotation == 1) rect(drawOffset + sizeRatio * position.x + barOffset, drawOffset + sizeRatio * position.y, drawSize / 15, drawSize / 2);
      if (rotation == 0) rect(drawOffset + sizeRatio * position.x, drawOffset + sizeRatio * position.y - barOffset, drawSize / 2, drawSize / 15);
    }
  }
  
  void place(int selectedType, int selectedRotation, Block[][] blocks) {
    if (type != selectedType) type = selectedType;
    rotation = selectedRotation;
    
    charge = false;
    lock = false;
    lastCharge = false;
    
    inputs = new ArrayList<Position>();
    updaters = new ArrayList<Position>();
    
    update(blocks, position);
  }
  
  void erase(Block[][] blocks) {
    type = EMPTY;
    charge = false;
    lock = false;
    lastCharge = false;
    
    inputs = new ArrayList<Position>();
    updaters = new ArrayList<Position>();
    
    updateSurroundingBlocks(getSurroundingBlocks(blocks), blocks);
    
    draw();
  }
  
  void update(Block[][] blocks, Position updater) {
    if (updater != null) updaters.add(updater);
    if (type != EMPTY) {
      inputs = new ArrayList<Position>();
      
      ArrayList<Position> surroundingBlocks = getSurroundingBlocks(blocks);
      
      for (int i = 0; i < surroundingBlocks.size(); i++) {
        Position currentSurroundingBlockPosition = surroundingBlocks.get(i);
        Block currentSurroundingBlock = blocks[currentSurroundingBlockPosition.x][currentSurroundingBlockPosition.y];
        
        if (type == INVERTER) {
          if ((!isFacing(position, currentSurroundingBlockPosition, rotation) && currentSurroundingBlock.charge) || currentSurroundingBlock.type == SOURCE) inputs.add(currentSurroundingBlock.position);
        }
        else if (currentSurroundingBlock.type == INVERTER) {
          if (isFacing(currentSurroundingBlockPosition, position, currentSurroundingBlock.rotation) && currentSurroundingBlock.charge) inputs.add(currentSurroundingBlock.position);
        }
        else if (currentSurroundingBlock.charge && ((notOnlyItemInList(currentSurroundingBlock.inputs, position)) || currentSurroundingBlock.type == SOURCE)) inputs.add(currentSurroundingBlock.position);
      }
      
      charge = true;
      if (inputs.size() == 0) charge = false;
      if (type == SOURCE) charge = true;
      
      if (type == INVERTER) {
        if (inputs.size() == 0) charge = true;
        else charge = false;
      }
      
      if (type != INVERTER) updateSurroundingBlocks(surroundingBlocks, blocks);
      else {
        if (charge != lastCharge) updateSurroundingBlocks(surroundingBlocks, blocks);
        lastCharge = charge;
      }
      
      draw();
    }
  }
  
  boolean mouseOver() {
    return mouseX > drawOffset + sizeRatio * position.x - drawSize / 2 && 
           mouseX < drawOffset + sizeRatio * position.x + drawSize / 2 && 
           mouseY > drawOffset + sizeRatio * position.y - drawSize / 2 && 
           mouseY < drawOffset + sizeRatio * position.y + drawSize / 2;
  }
  
  ArrayList<Position> getSurroundingBlocks(Block[][] blocks) {
    ArrayList<Position> foundBlocks = new ArrayList<Position>();
    if (position.x + 1 < size - 1) { if (blocks[position.x + 1][position.y].type != EMPTY) foundBlocks.add(new Position(position.x + 1, position.y)); };
    if (position.x - 1 > 0) { if (blocks[position.x - 1][position.y].type != EMPTY) foundBlocks.add(new Position(position.x - 1, position.y)); };
    if (position.y + 1 < size - 1) { if (blocks[position.x][position.y + 1].type != EMPTY) foundBlocks.add(new Position(position.x, position.y + 1)); };
    if (position.y - 1 > 0) { if (blocks[position.x][position.y - 1].type != EMPTY) foundBlocks.add(new Position(position.x, position.y - 1)); };
    return foundBlocks;
  }
  
  void updateSurroundingBlocks(ArrayList<Position> surroundingBlocks, Block[][] blocks) {
    for (int i = 0; i < surroundingBlocks.size(); i++) {
      Position currentSurroundingBlockPosition = surroundingBlocks.get(i);
      if (blocks[currentSurroundingBlockPosition.x][currentSurroundingBlockPosition.y].updaters.size() == 0 || (blocks[currentSurroundingBlockPosition.x][currentSurroundingBlockPosition.y].charge == false && charge == true)) {
        if (i != surroundingBlocks.size() - 1 && surroundingBlocks.size() > 1) blocks[currentSurroundingBlockPosition.x][currentSurroundingBlockPosition.y].update(blocks, position);
        else blocks[currentSurroundingBlockPosition.x][currentSurroundingBlockPosition.y].update(blocks, position);
      }
    }
  }
  
  boolean notOnlyItemInList(ArrayList<Position> list, Position checkAgainst) {
    if (list.size() > 1) return true;
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).x == checkAgainst.x && list.get(j).y == checkAgainst.y) return false;
    }
    return true;
  }
  
  boolean isFacing(Position a, Position b, int rot) {
    int x = a.x;
    int y = a.y;
    if (rot == 0) y++;
    if (rot == 1) x--;
    if (rot == 2) y--;
    if (rot == 3) x++;
    
    if (b.x == x && b.y == y) return true;
    return false;
  }
}
