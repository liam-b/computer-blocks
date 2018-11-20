package computerblocks.player;

import computerblocks.game.Game;
import computerblocks.player.stateMachine.State;
import computerblocks.player.stateMachine.StateMethod;
import computerblocks.player.stateMachine.StateMachine;

enum PlayerState {
  SETUP, GAME, MENU
}

public class PlayerStateMachine extends StateMachine<PlayerState,Game> {
  public PlayerStateMachine(Game game) {
    super(PlayerState.SETUP, game);

    setup();
  }

  public void setup() {
    add(PlayerState.SETUP, new State<Game>());

    add(PlayerState.GAME, new State<Game>(
      (Game game) -> { System.out.println("during GAME"); }
    ));

    add(PlayerState.MENU, new State<Game>(
      (Game game) -> { System.out.println("entered MENU"); },
      (Game game) -> { System.out.println("during MENU"); },
      (Game game) -> { System.out.println("exited MENU"); }
    ));

    update();
    transition(PlayerState.GAME);
    update();
    transition(PlayerState.MENU);
    update();
    transition(PlayerState.GAME);
  }
}