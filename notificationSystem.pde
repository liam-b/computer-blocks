void drawNotifications() {
  textAlign(LEFT, CENTER);
  int i = 0;
  for (Notification notif : notifications) {
    if (notif.label != "") {
      textSize(15);
      fill(COLOR_BACKGROUND);
      rect(25 + textWidth(notif.typeLabel + notif.label)/2, 25 + 25*i + 2, textWidth(notif.typeLabel + notif.label) + 10, 20);
      fill(notif.col);
      text(notif.typeLabel + notif.label, 25, 25 + 25*i);

      i++;
    }
  }
}

void tickNotifications() {
  for (Notification notif : notifications) {
    if (frameCount - notif.frameCreatedOn == 120) {
      background(COLOR_BACKGROUND);
      space.drawAllBlocks(player);
      notif.label = "";
    }
  }
}
