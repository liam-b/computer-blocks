package computerblocks.position;

public class BlockPosition {
  public int x, y, l;
  public Rotation r;

  public BlockPosition(int x, int y, Rotation r, int l) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.l = l;
  }

  public BlockPosition(int x, int y, int l) {
    this.x = x;
    this.y = y;
    this.r = Rotation.UP;
    this.l = l;
  }

  public BlockPosition(BlockPosition pos) {
    this.x = pos.x;
    this.y = pos.y;
    this.r = pos.r;
    this.l = pos.l;
  }

  public BlockPosition() {
    this.x = 0;
    this.y = 0;
    this.r = Rotation.UP;
    this.l = 0;
  }

  public boolean isEqual(BlockPosition pos) {
    return pos != null && (x == pos.x && y == pos.y && l == pos.l);
  }

  public boolean isFacing(BlockPosition pos) {
    int posX = pos.x;
    int posY = pos.y;

    if (r == Rotation.UP) posY ++;
    if (r == Rotation.RIGHT) posX --;
    if (r == Rotation.DOWN) posY --;
    if (r == Rotation.LEFT) posX ++;

    if (x == posX && y == posY) return true;
    return false;
  }

  public String toString() {
    return "[" + x + ", " + y + ", " + l + "]";
  }
}