package pl.mrucznik.gwint.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import pl.mrucznik.gwint.model.Game;
import pl.mrucznik.gwint.model.Player;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;

public class GameController {
    Game game;
    Player playerOne, playerTwo;
    Context context;

    public GameController(Context context)
    {
        this.context = context;
        playerOne = new Player("Gracz pierwszy");
        playerTwo = new Player("Gracz drugi");
    }

    public void startGame()
    {
        game = new Game(this, playerOne, playerTwo);
        game.start();
    }

    public void showRowMenu(Consumer<AttackRow> callback)
    {
        callback.accept(AttackRow.CloseCombat);
    }
    public void sendButtonMessage(String message, String buttonMeesage)
    {

    }

    public void updatePoints(Map<Player, Map<AttackRow, Integer>> points)
    {
    }

    public void chooseCard(Stream<GwentCard> cards, Consumer<GwentCard> callback)
    {
        sendMessage("Wybierz kartę");
        callback.accept(cards.findAny().get()); //wybrana karta
    }


    public void sendMessage(String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
