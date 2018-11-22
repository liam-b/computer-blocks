package computerblocks.player.stateMachine;

import java.util.HashMap;
import computerblocks.game.Game;

public class StateMachine<T> {
  private HashMap<String, State> states = new HashMap<String, State>();
  public String state;
  private T context;

  public StateMachine(T context, String initialState, State... newStates) {
    this.context = context;
    for (State newState : newStates) {
      states.put(newState.getClass().getSimpleName(), newState);
    }

    state = initialState;
    states.get(state).enter(context);
  }
  
  public void transition(String nextState) {
    states.get(state).exit(context);
    state = nextState;
    states.get(state).enter(context);
  }

  public void update() {
    states.get(state).update(context);
  }

  public void draw() {
    states.get(state).draw(context);
  }
}