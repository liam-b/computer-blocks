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
color COLOR_DELAY_OFF;
color COLOR_DELAY_ON;

// ui colors
color COLOR_UI_BACKGROUND;
color COLOR_UI_COPY;
color COLOR_UI_PASTE;
color COLOR_UI_MENU_SELECTION;

// block types
int EMPTY = 0;
int CABLE = 1;
int SOURCE = 2;
int INVERTER = 3;
int VIA = 4;
int DELAY = 5;

// space attributes
int SPACE_SIZE = 150;
int SPACE_LAYERS = 5;

// draw variables
int BLOCK_RATIO = 10;
int BLOCK_SPACING = 1;
int BLOCK_OFFSET = BLOCK_RATIO / 2;
int BLOCK_SIZE = BLOCK_RATIO - BLOCK_SPACING;

// notification types
int NOTIF_DEFAULT = 0;
int NOTIF_ERROR = 1;
int NOTIF_WARNING = 2;

// notification colors
color COLOR_WARNING;
color COLOR_ERROR;
color COLOR_DEFAULT;

// ui variables
float UI_OFFSET_X = 30;
float UI_OFFSET_Y = 30;
float UI_SPACING = 80;
float UI_BLOCK_SIZE = 50;
float UI_BACKGROUND_MARGIN = 5;

// file names
String SAVE_FILE = "saves/save";

//Auto save rate in seconds
int AUTOSAVE_RATE = 10;

void setupColors() {
  colorMode(HSB, 100);

  COLOR_WARNING = color(map(50,0,360,0,100), 95, 100);
  COLOR_ERROR = color(0, 100, 100);
  COLOR_DEFAULT = color(0, 0, 70);

  COLOR_BACKGROUND = color(0, 0, 90);
  COLOR_EMPTY = color(0, 0, 85);
  COLOR_CABLE_OFF = color(0, 0, 70);
  COLOR_CABLE_ON = color(15, 45, 85);
  COLOR_SOURCE = color(17, 55, 90);
  COLOR_INVERTER_OFF = color(0, 55, 74);
  COLOR_INVERTER_ON = color(0, 55, 88);
  COLOR_VIA_OFF = color(55, 55, 74);
  COLOR_VIA_ON = color(55, 55, 88);
  COLOR_DELAY_OFF = color(35, 55, 78);
  COLOR_DELAY_ON = color(35, 55, 86);

  COLOR_UI_BACKGROUND = color(0, 0, 88);
  COLOR_UI_COPY = color(70, 37, 80);
  COLOR_UI_PASTE = color(80, 37, 80);
  COLOR_UI_MENU_SELECTION = color(166, 64, 80);

  background(COLOR_BACKGROUND);
}
