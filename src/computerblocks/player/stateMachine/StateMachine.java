package computerblocks.player.stateMachine;

import java.util.HashMap;

public class StateMachine<V, K> {
  public HashMap<V,State<K>> states;

  public StateMachine() {
    states = new HashMap<V,State<K>>();
  }
}