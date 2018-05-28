class Notification {
  String label;
  color col;
  int type;
  String typeLabel;
  int frameCreatedOn;

  Notification(int type_, String label_) {
    type = type_;
    label = label_;

    frameCreatedOn = frameCount;

    if (type == NOTIF_ERROR) typeLabel = "ERROR - ";
    if (type == NOTIF_WARNING) typeLabel = "WARNING - ";
    if (type == NOTIF_DEFAULT) typeLabel = "";

    if (type == NOTIF_ERROR) col = COLOR_ERROR;
    if (type == NOTIF_WARNING) col = COLOR_WARNING;
    if (type == NOTIF_DEFAULT) col = COLOR_DEFAULT;
  }
}
