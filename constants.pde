// main colors
color COLOR_BACKGROUND = #E6E6E6;
color COLOR_EMPTY = #D7D7D7;
color COLOR_CABLE_OFF = #B4B4B4;
color COLOR_CABLE_ON = #DBD44E;
color COLOR_SOURCE = #F2E24F;
color COLOR_INVERTER_OFF = #CE4E4A;
color COLOR_INVERTER_ON = #F95E59;
color COLOR_VIA_OFF = #589EC9;
color COLOR_VIA_ON = #75BDEA;
color COLOR_DELAY_OFF = #59C664;
color COLOR_DELAY_ON = #62DB6E;

// ui colors
color COLOR_UI_BACKGROUND = #E0E0E0;
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

// auto save rate in seconds
boolean AUTOSAVE = false;
int AUTOSAVE_RATE = 30;
