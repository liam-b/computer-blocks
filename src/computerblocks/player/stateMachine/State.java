package computerblocks.player.stateMachine;

public abstract class State<T> {
  public void enter(T context) {}
  public void update(T context) {}
  public void exit(T context) {}
}