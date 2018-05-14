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
          blocks[l][x][y] = new Block(new BlockPosition(x, y, 0, l), this, spacing);
        }
      }
    }
  }
  
  void draw(Player player) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        if (blocks[player.selectedLayer][x][y].mouseOver(player) && mousePressed && mouseButton == LEFT) {
          blocks[player.selectedLayer][x][y].lock = true;
          
          if (player.selectedType == EMPTY) blocks[player.selectedLayer][x][y].erase(player, this);
          else blocks[player. selectedLayer][x][y].place(player, this);
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
  
  void drawAllBlocks(Player player) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[player.selectedLayer][x][y].draw(player);
      }
    }
  }
}
