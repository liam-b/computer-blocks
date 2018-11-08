package computerblocks.block;

import computerblocks.display.Color;
import computerblocks.position.*;
import computerblocks.display.*;
import computerblocks.display.ui.menu.elements.*;
import computerblocks.player.*;
import computerblocks.player.io.*;
import computerblocks.Grid;

public class LabelBlock extends DecorationalBlock {
  public LabelBlock(BlockPosition position) {
    super(position);

    this.type = BlockType.LABEL;
    this.color = Color.LABEL;
  }

  public void draw(Display display, Player player) {
    double rectSize = (double)BLOCK_SIZE * player.zoom;
    double x = player.drawTranslate.x + (double)BLOCK_RATIO * (double)position.x * player.zoom;
    double y = player.drawTranslate.y + (double)BLOCK_RATIO * (double)position.y * player.zoom;

    if (labelText.equals("")) {
      String font = Fonts.pixelmix;
      int textSize = 20;
      String text = "New Label";
      String tempText = "";

      while (true) {
        tempText = player.keyboard.keyStream(tempText, 25);

        if (player.keyboard.down(Keyboard.ESC)) break;

        display.reset(Color.BACKGROUND);
        display.font(font, textSize);
        if (!tempText.equals("")) {
          display.color(Color.UI_BORDER);
          display.text(tempText,  (display.width / 2) - display.getStringWidth(tempText, font, textSize) / 2, (display.height / 2) + display.getFontHeight(font, textSize) / 2);
        } else {
          display.color(new Color(Color.UI_BORDER, 0.25f));
          display.text(text,  (display.width / 2) - display.getStringWidth(text, font, textSize) / 2, (display.height / 2) + display.getFontHeight(font, textSize) / 2);
        }
        display.draw();

        if (player.keyboard.enterDown) {
          if (!tempText.equals("")) {
            labelText = tempText;
            break;
          } else {
            labelText = text;
            break;
          }
        }
      }
    }

    if (withinScreenBounds(display, rectSize, x, y)) {
      if (selected) highlightBlock(display, player, rectSize, x, y);
      Color drawColor = color;
      display.color((ghost) ? new Color(drawColor, 0.5f) : drawColor);
      display.rect(x, y, rectSize, rectSize);
    }

    if (mouseOver(player)) {
      String font = Fonts.pixelmix;
      int textSize = 20;

      display.color(Color.UI_BORDER);
      display.rect(display.width / 2 - display.width / 6 - 4, display.height / 16 * 0.5 - 4, display.width / 3 + 8, display.height / 16 + 8);
      display.color(Color.BACKGROUND);
      display.rect(display.width / 2 - display.width / 6, display.height / 16 * 0.5, display.width / 3, display.height / 16);

      display.color(Color.CABLE);
      display.font(font, textSize);
      display.text(labelText, display.width / 2 - display.getStringWidth(labelText, font, textSize) / 2, display.height / 16  + display.getFontHeight(font, textSize) / 2);
    }

  }
}
