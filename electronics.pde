Space space;
Player player;

color COLOR_BACKGROUND;
color COLOR_EMPTY;
color COLOR_CABLE_OFF;
color COLOR_CABLE_ON;
color COLOR_SOURCE;
color COLOR_INVERTER_OFF;
color COLOR_INVERTER_ON;

class Position {
  int x;
  int y;
  int l;
  
  Position(int x_, int y_, int l_) {
    x = x_;
    y = y_;
    l = l_;
  }
}

void setup() {
  size(800, 800);
  colorMode(HSB, 100);
  rectMode(CENTER);
  noStroke();
  frameRate(60);
  cursor(CROSS);
  
  COLOR_BACKGROUND = color(0, 0, 90);
  COLOR_EMPTY = color(0, 0, 85);
  COLOR_CABLE_OFF = color(0, 0, 70);
  COLOR_CABLE_ON = color(15, 45, 80);
  COLOR_SOURCE = color(15, 55, 88);
  COLOR_INVERTER_OFF = color(0, 55, 74);
  COLOR_INVERTER_ON = color(0, 55, 88);
  
  background(COLOR_BACKGROUND);
  
  player = new Player();
  
  space = new Space(20, 2, 3);
  space.setup(player.selectedLayer);
}

void draw() {
  space.draw(player.selectedType, player.selectedRotation, player.selectedLayer);
}

void mouseReleased() {
  space.unlockAllBlocks();
}

void keyPressed() {
  player.update(space);
}
