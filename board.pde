class Space {
  Block[][][] blocks;
  int size;
  int spacing;
  int layers;
  
  Space(int size_, int layers_, int spacing_) {
    size = size_;
    spacing = spacing_;
    layers = layers_;
  
    blocks = new Block[layers][size][size];
    
    for (int l = 0; l < layers; l++) {
      for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {    
          blocks[l][x][y] = new Block(new Position(x, y, l), size, spacing);
        }
      }
    }
  }
  
  void setup(int selectedLayer) {
    drawAllBlocks(selectedLayer);
  }
  
  void draw(int selectedType, int selectedRotation, int selectedLayer) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[selectedLayer][x][y].updated = false;
        if (blocks[selectedLayer][x][y].mouseOver() && mousePressed && mouseButton == LEFT) {
          blocks[selectedLayer][x][y].lock = true;
          
          if (selectedType == EMPTY) blocks[selectedLayer][x][y].erase(blocks);
          else blocks[selectedLayer][x][y].place(selectedType, selectedRotation, blocks);
        }
        else if (blocks[selectedLayer][x][y].mouseOver() && mousePressed && mouseButton == RIGHT) {
          println(blocks[selectedLayer][x][y].position.x, blocks[selectedLayer][x][y].position.y);
          println(blocks[selectedLayer][x][y].charge);
          println("inputs:");
          for (int i = 0; i < blocks[selectedLayer][x][y].inputs.size(); i++) {
            println("  " + blocks[selectedLayer][x][y].inputs.get(i).x, blocks[selectedLayer][x][y].inputs.get(i).y);
          }
          println("-----");
        }
      }
    }
  }
  
  void unlockAllBlocks() {
    for (int l = 0; l < layers; l++) {
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          blocks[l][i][j].lock = false;
        }
      }
    }
  }
  
  void drawAllBlocks(int layer) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[layer][x][y].draw();
      }
    }
  }
}
