package computerblocks.player.stateMachine;

import java.util.HashMap;
import computerblocks.game.Game;

public class StateMachine<T> {
  public HashMap<String, Class> states = new HashMap<String, Class>();
  public StateMethodCaller<T> caller;
  public String state;
  public T context;

  public StateMachine(T context, StateMethodCaller<T> caller) {
    this.context = context;
    this.caller = caller;
  }

  public void addState(Class state) {
    states.put(state.getSimpleName(), state);
    if (this.state == null) {
      this.state = state.getSimpleName();
      call(states.get(this.state), "enter");
    }
  }

  public void update() {
    call(states.get(state), "update");
  }

  public void transition(String newState) {
    System.out.println("yooo " + newState);
    call(states.get(state), "exit");
    state = newState;
    call(states.get(state), "enter");
  }

  public void call(Class state, String method) {
    caller.call(state, method, context);
  }
}