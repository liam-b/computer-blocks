package computerblocks.player.stateMachine;

public class State<T> {
  public StateMethod<T> enter;
  public StateMethod<T> during;
  public StateMethod<T> exit;

  public State() {
    this.enter = (T context) -> {};
    this.during = (T context) -> {};
    this.exit = (T context) -> {};
  }

  public State(StateMethod<T> enter, StateMethod<T> during, StateMethod<T> exit) {
    this.enter = enter;
    this.during = during;
    this.exit = exit;
  }

  public State(StateMethod<T> during) {
    this.enter = (T context) -> {};
    this.during = during;
    this.exit = (T context) -> {};
  }
}