// package computerblocks.block.types;
//
// public class FunctionalBlock extends Block {
//   public FunctionalBlock(BlockPosition position) {
//     super(position);
//   }
//
//   public ArrayList<Block> update(Grid grid, Block updater, Display display, Player player) {
//     inputs = new ArrayList<Block>();
//     ArrayList<Block> surroundingBlocks = getSurroundingBlocks(grid);
//     ArrayList<Block> removeQueue = new ArrayList<Block>();
//
//     for (Block block : surroundingBlocks) {
//       boolean onlyItemInSurroundingBlockInputs = block.inputs.size() == 1 && block.inputs.get(0) == this;
//       if (block.type.isDirectional()) {
//         if (block.position.isFacing(position)) {
//           if (block.charge) inputs.add(block);
//           removeQueue.add(block);
//         }
//       }
//       else if (block.charge && !onlyItemInSurroundingBlockInputs) inputs.add(block);
//     }
//     surroundingBlocks.remove(updater);
//     surroundingBlocks.removeAll(removeQueue);
//
//     charge = inputs.size() != 0;
//     return surroundingBlocks;
//   }
// }