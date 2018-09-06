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

  public BlockPosition subtract(BlockPosition pos) {
    return new BlockPosition(x - pos.x, y - pos.y, r, l - pos.l);
  }

  public BlockPosition add(BlockPosition pos) {
    return new BlockPosition(x + pos.x, y + pos.y, r, l + pos.l);
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

  public boolean isWithin(BlockPosition posA, BlockPosition posB) {
    BlockPosition posLeast = new BlockPosition(Math.min(posA.x, posB.x), Math.min(posA.y, posB.y), Math.min(posA.l, posB.l));
    BlockPosition posMost = new BlockPosition(Math.max(posA.x, posB.x), Math.max(posA.y, posB.y), Math.max(posA.l, posB.l));
    return x >= posLeast.x && y >= posLeast.y && l >= posLeast.l && x <= posMost.x && y <= posMost.y && l <= posMost.l;
  }

  public String toString() {
    return "[" + x + ", " + y + ", " + l + "]";
  }
}