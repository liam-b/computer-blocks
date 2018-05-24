Space space;
Player player;
UI ui;

int lastLoad;

// BUG: fix scrolling
// BUG: make infinite block update loops impossible
// BUG: panning through world while load / save menu is open is reaaaaly laggy
// TODO: more versatile copy, past selection system
// TODO: load fonts on start
// TODO: add autosave feature

void setup() {
  fullScreen();
  pixelDensity(2);
  frameRate(60);
  cursor(CROSS);

  rectMode(CENTER);
  noStroke();
  setupColors();

  space = new Space(SPACE_SIZE, SPACE_LAYERS);
  player = new Player();
  ui = new UI(new Position(UI_OFFSET_X , height - UI_OFFSET_Y), UI_SPACING, UI_BLOCK_SIZE, UI_BACKGROUND_MARGIN);

  space.drawAllBlocks(player);
}

void draw() {
  space.update(player);
  player.updateTranslate();
  ui.draw(space, player);

  if (frameCount % 10 == 0) {
    space.tickAllBlocks(player);
  }

  if(frameCount % (AUTOSAVE_RATE*60) == 0 && lastLoad != -1) {
    saveSpace(SAVE_FILE + "_" + str(lastLoad) + ".xml");
    println("AUTOSAVE: " + SAVE_FILE + "_" + str(lastLoad) + ".xml");
  }
}

void mousePressed() {
  player.resetTranslate();
  if (mouseButton == LEFT) player.selectionUpdate(space);
}

void mouseReleased() {
  space.unlockAllBlocks();
  player.resetTranslate();
  player.resetMode();
}

void keyPressed() {
  player.update(space);

  if (key == ',') player.zoom -= 0.3;
  if (key == '.') player.zoom += 0.3;
  if (key == '.' || key == ',') player.updateScroll();
}

void keyReleased() {
  player.deupdate(space);
}

// void mouseWheel(MouseEvent event) {
//  float e = event.getCount();
//  player.zoom += -e / 1000;
//  player.updateScroll();
// }

int convertKeyToInt(char charKey) {
  if(charKey == '0') return 0;
  if(charKey == '1') return 1;
  if(charKey == '2') return 2;
  if(charKey == '3') return 3;
  if(charKey == '4') return 4;
  if(charKey == '5') return 5;
  if(charKey == '6') return 6;
  if(charKey == '7') return 7;
  if(charKey == '8') return 8;
  if(charKey == '9') return 9;
  return -1;
}
