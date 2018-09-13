// package computerblocks.block;
//
// public class BlockBuilder {
//   public Block clone(Block old, BlockPosition position) {
//     Block block = new Block(old.getClass(), position);
//   }
//
//   public Block create() {}
//
//   private Block newFromClass(Class class, BlockPosition position) {
//     if (class == SourceBlock.class) return new SourceBlock(position);
//     if (class == InverterBlock.class) return new InverterBlock(position);
//     if (class == DelayBlock.class) return new DelayBlock(position);
//     if (class == ViaBlock.class) return new ViaBlock(position);
//     return new CableBlock(position);
//   }
// }

// if (grid.blockAt(new BlockPosition()) != null && grid.blockAt(new BlockPosition()).getClass() == DelayBlock.class) System.out.println("out");