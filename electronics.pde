Space space;
Player player;
UI ui;

void setup() {
  size(800, 800);
  rectMode(CENTER);
  noStroke();
  frameRate(60);
  cursor(CROSS);
  setupColors();
  background(COLOR_BACKGROUND);

  space = new Space(SPACE_SIZE, SPACE_LAYERS, SPACE_SPACING);
  player = new Player(space);
  
  ui = new UI(new Position(50 , height - 50), 50, 5);

  space.drawAllBlocks(player);
}

void draw() {
  space.draw(player);
  player.cameraUpdate();
  ui.draw(player);
}

void mousePressed() {
  player.resetTranslate();
  player.selectionUpdate(space);
  
  if (key == '0') {
    println("hrllo"); //<>//
    //BlockPosition pos = player.findClickedBlock(space);
    //Block myBlock = space.blocks[pos.l][pos.x][pos.y];
    
    //println(myBlock.type);
  };
}

void mouseReleased() {
  space.unlockAllBlocks();
  player.resetTranslate();
  player.selectionReset();
}

void keyPressed() {
  player.update(space);
}

//void mouseWheel(MouseEvent event) {
//  float e = event.getCount();
//  player.scrollValue += -e / 1000;
//  player.scrollUpdate();
//}
