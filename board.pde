class Space {
  Block[][] blocks;
  int size;
  int spacing;
  
  Space(int size_, int spacing_) {
    size = size_;
    spacing = spacing_;
  
    blocks = new Block[size][size];
    
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[x][y] = new Block(new Position(x, y), size, spacing);
      }
    }
  }
  
  void setup() {
    drawAllBlocks();
  }
  
  void draw(int selectedType, int selectedRotation) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[x][y].updaters = new ArrayList<Position>();
        if (blocks[x][y].mouseOver() && mousePressed && mouseButton == LEFT) {
          blocks[x][y].lock = true;
          
          if (selectedType == EMPTY) blocks[x][y].erase(blocks);
          else blocks[x][y].place(selectedType, selectedRotation, blocks);
        }
        else if (blocks[x][y].mouseOver() && mousePressed && mouseButton == RIGHT) {
          println(blocks[x][y].position.x, blocks[x][y].position.y);
          println(blocks[x][y].charge);
          println("inputs:");
          for (int i = 0; i < blocks[x][y].inputs.size(); i++) {
            println("  " + blocks[x][y].inputs.get(i).x, blocks[x][y].inputs.get(i).y);
          }
          println("-----");
        }
      }
    }
  }
  
  void unlockAllBlocks() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        blocks[i][j].lock = false;
      }
    }
  }
  
  void drawAllBlocks() {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[x][y].draw();
      }
    }
  }
}
