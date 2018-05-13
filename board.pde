class Space {
  Block[][][] blocks;
  int size;
  float spacing;
  int layers;
  
  Space(int size_, int layers_, float spacing_) {
    size = size_;
    spacing = spacing_;
    layers = layers_;
  
    blocks = new Block[layers][size][size];
    
    for (int l = 0; l < layers; l++) {
      for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {    
          blocks[l][x][y] = new Block(new Position(x, y, l), size, spacing, layers);
        }
      }
    }
  }
  
  void setup(Player player) {
    drawAllBlocks(player);
  }
  
  void draw(Player player) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        for (int l = 0; l < layers; l++) {
          blocks[l][x][y].updated = false;
        }
        
        if (blocks[player.selectedLayer][x][y].mouseOver(player) && mousePressed && mouseButton == LEFT) {
          blocks[player.selectedLayer][x][y].lock = true;
          
          if (player.selectedType == EMPTY) blocks[player.selectedLayer][x][y].erase(player, blocks);
          else blocks[player. selectedLayer][x][y].place(player, blocks);
        }
        //else if (blocks[selectedLayer][x][y].mouseOver() && mousePressed && mouseButton == RIGHT) {
        //  println(blocks[selectedLayer][x][y].position.x, blocks[selectedLayer][x][y].position.y);
        //  println(blocks[selectedLayer][x][y].charge);
        //  println("inputs:");
        //  for (int i = 0; i < blocks[selectedLayer][x][y].inputs.size(); i++) {
        //    println("  " + blocks[selectedLayer][x][y].inputs.get(i).x, blocks[selectedLayer][x][y].inputs.get(i).y);
        //  }
        //  println("-----");
        //}
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
  
  void drawAllBlocks(Player player) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[player.selectedLayer][x][y].draw(player);
      }
    }
  }
}
