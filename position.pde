class Position {
  float x;
  float y;
  
  Position(float x_, float y_) {
    x = x_;
    y = y_;
  }
}

class BlockPosition {
  int x;
  int y;
  int r;
  int l;
  
  BlockPosition(int x_, int y_, int r_, int l_) {
    x = x_;
    y = y_;
    r = r_;
    l = l_;
  }
  
  boolean isEqual(BlockPosition pos) {
    if (x == pos.x || y == pos.y || l == pos.l) return true;
    return false;
  }
  
  boolean isFacing(BlockPosition pos) {
    int posX = pos.x;
    int posY = pos.y;
    
    if (r == 0) posY++;
    if (r == 1) posX--;
    if (r == 2) posY--;
    if (r == 3) posX++;
    
    if (x == posX && y == posY) return true;
    return false;
  }
  
  BlockPosition duplicate() {
    return new BlockPosition(x, y, r, l);
  }
}
