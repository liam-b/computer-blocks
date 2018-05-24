class Space {
  Block[][][] blocks;
  int size;
  int layers;

  Space() {}

  Space(int size_, int layers_) {
    size = size_;
    layers = layers_;

    blocks = new Block[layers][size][size];

    for (int l = 0; l < layers; l++) {
      for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {
          blocks[l][x][y] = new Block(new BlockPosition(x, y, 0, l));
        }
      }
    }
  }

  void update(Player player) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        if (blocks[player.selectedLayer][x][y].mouseOver(player) && mousePressed && mouseButton == LEFT && player.mode == EDIT) {
          blocks[player.selectedLayer][x][y].lock = true;

          if (player.selectedType == EMPTY) blocks[player.selectedLayer][x][y].erase(player, this);
          else blocks[player. selectedLayer][x][y].place(player, this);
        }
      }
    }
  }

  void updateSaveNames() {
    for(int i = 0; i < 10; i++) {
      if (fileExists(sketchPath(SAVE_FILE + "_" + i + ".xml"))) {
        saveNames[i] = loadXML(SAVE_FILE + "_" + i + ".xml").getString("saveName", "Unnamed Save");
      } else {
        saveNames[i] = "__";
      }
    }
  }

  void unlockAllBlocks() {
    for (int l = 0; l < layers; l++) {
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          blocks[l][i][j].lock = false;
        }
      }
    }
  }

  void unselectAllBlocks(Player player) {
    for (int l = 0; l < layers; l++) {
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          if (blocks[l][i][j].selected) {
            blocks[l][i][j].selected = false;
            blocks[l][i][j].draw(player);
          }
        }
      }
    }
  }

  void drawAllBlocks(Player player) {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        blocks[player.selectedLayer][x][y].draw(player);
      }
    }
  }

  void tickAllBlocks(Player player) {
    ArrayList<BlockPosition> updateQueue = new ArrayList<BlockPosition>();

    for (int l = 0; l < layers; l++) {
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          if (blocks[l][i][j].type == DELAY) updateQueue.add(blocks[l][i][j].tick(player));
        }
      }
    }

    for (int b = 0; b < updateQueue.size(); b++) {
      BlockPosition currentBlock = updateQueue.get(b);
      if (currentBlock != null) {
        blocks[currentBlock.l][currentBlock.x][currentBlock.y].updateSurroundingBlocks(blocks[currentBlock.l][currentBlock.x][currentBlock.y].getSurroundingBlocks(this), this, player, currentBlock);
      }
    }
  }
}
