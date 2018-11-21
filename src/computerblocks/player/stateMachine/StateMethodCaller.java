package computerblocks.player.stateMachine;

public interface StateMethodCaller<T> {
  public void call(Class state, String method, T context);
}