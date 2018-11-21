package computerblocks.player;

import java.lang.reflect.*;

import computerblocks.game.Game;
import computerblocks.player.stateMachine.State;
import computerblocks.player.stateMachine.StateMachine;

public class PlayerStateMachine extends StateMachine<Game> {
  public PlayerStateMachine(Game game) {
    super(game);

    initStates("GAME", new GAME(), new MENU());
  }
}

class GAME extends State<Game> {
  public void enter(Game game) {
    System.out.println("yoet entered GAME");
  }

  public void update(Game game) {
    System.out.println("yoet updated GAME");
  }
}

class MENU extends State<Game> {
  public void enter(Game game) {
    System.out.println("yoet entered MENU");
    game.player.stateMachine.transition("GAME");
    // System.out.println(game.player);
  }
}