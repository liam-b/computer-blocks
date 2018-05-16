class Block {
  int type;
  boolean lock;
  boolean charge;
  boolean lastCharge;
  boolean selected;

  BlockPosition position;

  ArrayList<BlockPosition> inputs;

  Block(BlockPosition position_) {
    type = EMPTY;
    lock = false;
    charge = false;
    lastCharge = false;
    selected = false;

    position = position_;
    inputs = new ArrayList<BlockPosition>();
  }

  void draw(Player player) {
    float rectSize = BLOCK_SIZE * player.zoom;
    Position drawPosition = new Position(
      BLOCK_OFFSET + player.translate.x + BLOCK_RATIO * position.x * player.zoom,
      BLOCK_OFFSET + player.translate.y + BLOCK_RATIO * position.y * player.zoom
    );

    if (!(drawPosition.x + rectSize < 0 || drawPosition.x - rectSize > width || drawPosition.y + rectSize < 0 || drawPosition.y - rectSize > height) && position.l == player.selectedLayer) {
      color drawFill = COLOR_EMPTY;
      if (type != EMPTY) {
        if (charge == true) {
          if (type == CABLE) drawFill = COLOR_CABLE_ON;
          if (type == SOURCE) drawFill = COLOR_SOURCE;
          if (type == INVERTER) drawFill = COLOR_INVERTER_ON;
          if (type == VIA) drawFill = COLOR_VIA_ON;
        }
        else {
          if (type == CABLE) drawFill = COLOR_CABLE_OFF;
          if (type == SOURCE) drawFill = COLOR_SOURCE;
          if (type == INVERTER) drawFill = COLOR_INVERTER_OFF;
          if (type == VIA) drawFill = COLOR_VIA_OFF;
        }
      }
      if (selected) drawFill = color(hue(drawFill), saturation(drawFill), brightness(drawFill) - 4);

      fill(drawFill);
      rect(drawPosition.x, drawPosition.y, rectSize, rectSize);

      if (type == INVERTER) {
        fill(COLOR_SOURCE);
        if (position.r == 2) rect(drawPosition.x, drawPosition.y + rectSize / 2.5 - rectSize / 24, rectSize / 2, rectSize / 12);
        if (position.r == 1) rect(drawPosition.x + rectSize / 2.5 - rectSize / 24, drawPosition.y, rectSize / 12, rectSize / 2);
        if (position.r == 0) rect(drawPosition.x, drawPosition.y - rectSize / 2.5 + rectSize / 24, rectSize / 2, rectSize / 12);
        if (position.r == 3) rect(drawPosition.x - rectSize / 2.5 + rectSize / 24, drawPosition.y, rectSize / 12, rectSize / 2);
      }
    }
  }

  void place(Player player, Space space) {
    if (type != player.selectedType) {
      type = player.selectedType;
      charge = false;
      lock = false;
      lastCharge = false;
      position.r = player.selectedRotation;
      inputs = new ArrayList<BlockPosition>();

      update(player, space, position);
    }
  }

  void erase(Player player, Space space) {
    type = EMPTY;
    charge = false;
    lock = false;
    lastCharge = false;
    inputs = new ArrayList<BlockPosition>();

    updateSurroundingBlocks(getSurroundingBlocks(space), space, player, position);
    draw(player);
  }

  void update(Player player, Space space, BlockPosition updater) {
    if (type != EMPTY) {
      inputs = new ArrayList<BlockPosition>();

      ArrayList<BlockPosition> surroundingBlocks = getSurroundingBlocks(space);
      if (type == VIA) surroundingBlocks = appendViasToList(surroundingBlocks, space);

      int updaterIndex = -1;
      for (int i = 0; i < surroundingBlocks.size(); i++) {
        BlockPosition currentSurroundingBlockPosition = surroundingBlocks.get(i);
        Block currentSurroundingBlock = space.blocks[currentSurroundingBlockPosition.l][currentSurroundingBlockPosition.x][currentSurroundingBlockPosition.y];

        if (updater.x == currentSurroundingBlockPosition.x && updater.y == currentSurroundingBlockPosition.y) updaterIndex = i;

        if (type == INVERTER) {
          if ((!position.isFacing(currentSurroundingBlockPosition) && currentSurroundingBlock.charge) || currentSurroundingBlock.type == SOURCE) inputs.add(currentSurroundingBlock.position);
        }
        else if (currentSurroundingBlock.type == INVERTER) {
          if (currentSurroundingBlockPosition.isFacing(position) && currentSurroundingBlock.charge) inputs.add(currentSurroundingBlock.position);
        }
        else if (currentSurroundingBlock.charge && ((notOnlyItemInList(currentSurroundingBlock.inputs, position)) || currentSurroundingBlock.type == SOURCE)) inputs.add(currentSurroundingBlock.position);
      }
      if (updaterIndex != -1) surroundingBlocks.remove(updaterIndex);

      charge = true;
      if (inputs.size() == 0) charge = false;
      if (type == SOURCE) charge = true;
      if (type == INVERTER) {
        if (inputs.size() == 0) charge = true;
        else charge = false;
      }

      if (type != INVERTER) updateSurroundingBlocks(surroundingBlocks, space, player, position);
      else {
        boolean willUpdateSurroundingBlocks = false;
        if (charge != lastCharge) willUpdateSurroundingBlocks = true;
        lastCharge = charge;
        if (willUpdateSurroundingBlocks) updateSurroundingBlocks(surroundingBlocks, space, player, position);
      }

      draw(player);
    }
  }

  boolean mouseOver(Player player) {
    return mouseX > BLOCK_OFFSET + player.translate.x + BLOCK_RATIO * position.x * player.zoom - BLOCK_SIZE * player.zoom / 2 &&
           mouseX < BLOCK_OFFSET + player.translate.x + BLOCK_RATIO * position.x * player.zoom + BLOCK_SIZE * player.zoom / 2 &&
           mouseY > BLOCK_OFFSET + player.translate.y + BLOCK_RATIO * position.y * player.zoom - BLOCK_SIZE * player.zoom / 2 &&
           mouseY < BLOCK_OFFSET + player.translate.y + BLOCK_RATIO * position.y * player.zoom + BLOCK_SIZE * player.zoom / 2;
  }


  ArrayList<BlockPosition> getSurroundingBlocks(Space space) {
    ArrayList<BlockPosition> foundBlocks = new ArrayList<BlockPosition>();
    if (position.x + 1 < space.size - 1) { if (space.blocks[position.l][position.x + 1][position.y].type != EMPTY)
      foundBlocks.add(new BlockPosition(position.x + 1, position.y, space.blocks[position.l][position.x + 1][position.y].position.r, position.l)); };
    if (position.x - 1 > 0) { if (space.blocks[position.l][position.x - 1][position.y].type != EMPTY)
      foundBlocks.add(new BlockPosition(position.x - 1, position.y, space.blocks[position.l][position.x - 1][position.y].position.r, position.l)); };
    if (position.y + 1 < space.size - 1) { if (space.blocks[position.l][position.x][position.y + 1].type != EMPTY)
      foundBlocks.add(new BlockPosition(position.x, position.y + 1, space.blocks[position.l][position.x][position.y + 1].position.r, position.l)); };
    if (position.y - 1 > 0) { if (space.blocks[position.l][position.x][position.y - 1].type != EMPTY)
      foundBlocks.add(new BlockPosition(position.x, position.y - 1, space.blocks[position.l][position.x][position.y - 1].position.r, position.l)); };

    return foundBlocks;
  }

  void updateSurroundingBlocks(ArrayList<BlockPosition> surroundingBlocks, Space space, Player player, BlockPosition updater) {
    for (int i = 0; i < surroundingBlocks.size(); i++) {
      BlockPosition currentSurroundingBlockPosition = surroundingBlocks.get(i);
      Block currentSurroundingBlock = space.blocks[currentSurroundingBlockPosition.l][currentSurroundingBlockPosition.x][currentSurroundingBlockPosition.y];
      if (currentSurroundingBlock.type == INVERTER && currentSurroundingBlockPosition.isFacing(position)) continue;
      currentSurroundingBlock.update(player, space, updater);
    }
  }

  boolean notOnlyItemInList(ArrayList<BlockPosition> list, BlockPosition checkAgainst) {
    if (list.size() > 1) return true;
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).x == checkAgainst.x && list.get(j).y == checkAgainst.y) return false;
    }
    return true;
  }

  ArrayList<BlockPosition> appendViasToList(ArrayList<BlockPosition> list, Space space) {
    ArrayList<BlockPosition> foundVias = list;
    for (int l = 0; l < space.layers; l++) {
      if (space.blocks[l][position.x][position.y].type == VIA && space.blocks[l][position.x][position.y].position.l != position.l) foundVias.add(space.blocks[l][position.x][position.y].position);
    }
    return foundVias;
  }
}
