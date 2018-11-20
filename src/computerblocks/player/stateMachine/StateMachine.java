package computerblocks.player.stateMachine;

import java.util.HashMap;

public class StateMachine<V, K> {
  private HashMap<V,State<K>> states;
  public V state;
  private K context;

  public StateMachine(V state, K context) {
    this.state = state;
    states = new HashMap<V,State<K>>();
  }

  public void add(V newName, State<K> newState) {
    states.put(newName, newState);
  }

  public void update() {
    states.get(state).during.method(context);
  }

  public void transition(V newState) {
    states.get(state).exit.method(context);
    state = newState;
    states.get(state).enter.method(context);
  }
}