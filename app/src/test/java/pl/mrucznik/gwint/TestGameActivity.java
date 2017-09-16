package pl.mrucznik.gwint;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import pl.mrucznik.gwint.controller.activities.IGameController;
import pl.mrucznik.gwint.model.Player;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;

/**
 * Created by Mrucznik on 09.09.2017.
 */

public class TestGameActivity implements IGameController{

    @Override
    public void startGame() {

    }

    @Override
    public void showRowMenu(Consumer<AttackRow> callback) {

    }

    @Override
    public void updatePoints(Map<Player, Map<AttackRow, Integer>> points) {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void updatePlayer(Player player) {

    }

    @Override
    public void onNextRound() {

    }

    @Override
    public void onGameEnds() {

    }
}
