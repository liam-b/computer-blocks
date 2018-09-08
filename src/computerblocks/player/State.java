package computerblocks.player;

public class State {
  public static final State GAME = new State(true, true, true);
  public static final State MENU = new State(false, false, false);
  public static final State SELECT = new State(true, false, true);

  public boolean doPlayerTranslate;
  public boolean doPlayerInteraction;
  public boolean doPlayerSelection;

  public State(boolean doPlayerTranslate, boolean doPlayerInteraction, boolean doPlayerSelection) {
    this.doPlayerTranslate = doPlayerTranslate;
    this.doPlayerInteraction = doPlayerInteraction;
    this.doPlayerSelection = doPlayerSelection;
  }
}