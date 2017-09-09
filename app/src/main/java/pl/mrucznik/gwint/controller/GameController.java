package pl.mrucznik.gwint.controller;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

import pl.mrucznik.gwint.model.Game;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;

public class GameController {
    Game game;

    public void showRowMenu(Consumer<AttackRow> callback)
    {
        callback.accept(AttackRow.CloseCombat);
    }

    public void sendMessage(String message)
    {

    }
    public void sendButtonMessage(String message, String buttonMeesage)
    {

    }

    public void waitForNextCard(boolean interruptible)
    {
        sendMessage("Oczekiwanie na następną kartę");
        //anuluj button
    }

    public void chooseCard(Stream<GwentCard> cards, Consumer<GwentCard> callback)
    {
        sendMessage("Wybierz kartę");
        callback.accept(cards.findAny().get()); //wybrana karta
    }
}
