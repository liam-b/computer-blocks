// main colors
color COLOR_BACKGROUND;
color COLOR_EMPTY;
color COLOR_CABLE_OFF;
color COLOR_CABLE_ON;
color COLOR_SOURCE;
color COLOR_INVERTER_OFF;
color COLOR_INVERTER_ON;
color COLOR_VIA_OFF;
color COLOR_VIA_ON;

// ui colors
color COLOR_UI_BACKGROUND;
color COLOR_UI_COPY;
color COLOR_UI_PASTE;

// block types
int EMPTY = 0;
int CABLE = 1;
int SOURCE = 2;
int INVERTER = 3;
int VIA = 4;

// space attributes
int SPACE_SIZE = 50;
int SPACE_LAYERS = 5;

// draw variables
int BLOCK_RATIO = 10;
int BLOCK_SPACING = 1;
int BLOCK_OFFSET = BLOCK_RATIO / 2;
int BLOCK_SIZE = BLOCK_RATIO - BLOCK_SPACING;

void setupColors() {
  colorMode(HSB, 100);

  COLOR_BACKGROUND = color(0, 0, 90);
  COLOR_EMPTY = color(0, 0, 85);
  COLOR_CABLE_OFF = color(0, 0, 70);
  COLOR_CABLE_ON = color(15, 45, 80);
  COLOR_SOURCE = color(15, 55, 88);
  COLOR_INVERTER_OFF = color(0, 55, 74);
  COLOR_INVERTER_ON = color(0, 55, 88);
  COLOR_VIA_OFF = color(55, 55, 74);
  COLOR_VIA_ON = color(55, 55, 88);

  COLOR_UI_BACKGROUND = color(0, 0, 88);
  COLOR_UI_COPY = color(70, 38, 74);
  COLOR_UI_PASTE = color(80, 38, 74);

  background(COLOR_BACKGROUND);
}
