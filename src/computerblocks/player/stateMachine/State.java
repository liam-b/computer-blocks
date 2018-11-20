package computerblocks.player.stateMachine;

public class State<T> {
  public StateMethod<T> enter;
  public StateMethod<T> during;
  public StateMethod<T> exit;

  public State(StateMethod<T> enter, StateMethod<T> during, StateMethod<T> exit) {
    this.enter = enter;
    this.during = during;
    this.exit = exit;
  }
}