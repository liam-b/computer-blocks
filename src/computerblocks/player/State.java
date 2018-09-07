package computerblocks.player;

public class State {
  public static final State GAME = new State(true, true);
  public static final State MENU = new State(false, false);
  public static final State SELECT = new State(true, false);

  public boolean doPlayerTranslate;
  public boolean doPlayerInteraction;

  public State(boolean doPlayerTranslate, boolean doPlayerInteraction) {
    this.doPlayerTranslate = doPlayerTranslate;
    this.doPlayerInteraction = doPlayerInteraction;
  }
}