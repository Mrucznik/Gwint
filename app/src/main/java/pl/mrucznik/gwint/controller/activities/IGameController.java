package pl.mrucznik.gwint.controller.activities;

import android.widget.Toast;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import pl.mrucznik.gwint.model.Game;
import pl.mrucznik.gwint.model.Player;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;

/**
 * Created by Mrucznik on 09.09.2017.
 */

public interface IGameController {
    void startGame();
    void showRowMenu(Consumer<AttackRow> callback);
    void sendButtonMessage(String message, String buttonMeesage);
    void updatePoints(Map<Player, Map<AttackRow, Integer>> points);
    void chooseCard(Stream<GwentCard> cards, Consumer<GwentCard> callback);
    void sendMessage(String message);
    void updatePlayer(Player player);
}
