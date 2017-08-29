package pl.mrucznik.gwint;

import java.util.ArrayList;
import java.util.HashMap;

import pl.mrucznik.gwint.cards.GwentCard;

interface IGameControler {
    void nextPlayer();
    void nextRound();
}

public class Game implements IGameControler {
    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;
    private HashMap<Player, GameField> gameFields;
    private int round;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        activePlayer = playerOne;

        gameFields = new HashMap<>();
        gameFields.put(playerOne, new GameField());
        gameFields.put(playerTwo, new GameField());

        round = 0;
    }

    public void processCard(GwentCard card)
    {
        try {
            gameFields.get(activePlayer).putCard(this, card);

        } catch (InvaildCardException e) {
            //e.printStackTrace();
            //error during processing card/wrong card
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public boolean isGameOn()
    {
        return round <= 2;
    }

    public void nextPlayer()
    {
        activePlayer = (activePlayer == playerTwo) ? playerOne : playerTwo;
    }

    public void nextRound()
    {
        gameFields.forEach((k,v) -> v.clearCardArea());
        round++;
    }

    public int[] getPoints()
    {
        int[] pointsArray = new int[2];
        pointsArray[0] = gameFields.get(playerOne).getPoints();
        pointsArray[1] = gameFields.get(playerTwo).getPoints();
        return pointsArray;
    }
    public Player getActivePlayer() {
        return activePlayer;
    }

    public void printPoints()
    {
        gameFields.forEach((k,v) -> System.out.println(v.getPoints()));
    }

    public ArrayList getActiveEffects(Player player)
    {
        ArrayList arrayList = new ArrayList();
        gameFields.get(player).getActiveEffects().forEach((effect) -> {
            arrayList.addAll(effect.getEffects());
        });
        return arrayList;
    }
}
