package computerblocks.player;

import computerblocks.game.Game;
import computerblocks.player.stateMachine.State;
import computerblocks.player.stateMachine.StateMethod;
import computerblocks.player.stateMachine.StateMachine;

enum PlayerState {
  INITIAL
}

public class PlayerStateMachine extends StateMachine<PlayerState,Game> {
  public PlayerStateMachine(Game game) {
    // super();

    states.put(PlayerState.INITIAL, new State<Game>(
      (Game context) -> { System.out.println("enter"); },
      (Game context) -> { System.out.println("during"); },
      (Game context) -> { System.out.println("exit"); }
    ));

    states.get(PlayerState.INITIAL).enter.method(game);
  }
}