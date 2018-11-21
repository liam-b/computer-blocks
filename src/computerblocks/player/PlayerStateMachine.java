package computerblocks.player;

import java.lang.reflect.*;

import computerblocks.game.Game;
import computerblocks.player.stateMachine.State;
import computerblocks.player.stateMachine.StateMethodCaller;
import computerblocks.player.stateMachine.StateMachine;

public class PlayerStateMachine extends StateMachine<Game> {
  PlayerStateMachine(Game game) {
    super(game, (Class state, String method, Game context) -> {
      try { state.getMethod(method, Game.class).invoke(null, context); }
      catch (Exception err) {}
    });

    addState(Initial.class);
    addState(Thing.class);
  }
}

class Initial extends State {
  public static void enter(Game game) {
    System.out.println("initial enter");
  }

  public static void update(Game game) {
    System.out.println("initial update");
  }
}

class Thing extends State {
  public static void enter(Game game) {
    System.out.println("thing enter");
    game.player.stateMachine.transition("Initial");
  }
}