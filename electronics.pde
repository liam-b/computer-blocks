Space space;
Player player;
UI ui;

// TODO: fix scrolling

void setup() {
  size(800, 800);
  rectMode(CENTER);
  noStroke();
  frameRate(60);
  cursor(CROSS);
  setupColors();

  space = new Space(SPACE_SIZE, SPACE_LAYERS);
  player = new Player();
  ui = new UI(new Position(50 , height - 50), 50, 5);

  space.drawAllBlocks(player);
}

void draw() {
  space.update(player);
  player.updateTranslate();
  ui.draw(player);
}

void mousePressed() {
  player.resetTranslate();
  player.selectionUpdate(space);
}

void mouseReleased() {
  space.unlockAllBlocks();
  player.resetTranslate();
  player.resetMode();
}

void keyPressed() {
  player.update(space);

  if (key == 's') {
    saveSpace(SAVE_FILE);
  }

  if (key == 'l') {
    loadSpace(LOAD_FILE);
  }
}

//void mouseWheel(MouseEvent event) {
//  float e = event.getCount();
//  player.scrollValue += -e / 1000;
//  player.scrollUpdate();
//}
