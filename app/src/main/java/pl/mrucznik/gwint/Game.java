package pl.mrucznik.gwint;

import java.util.HashMap;

public class Game {
    protected Player playerOne;
    protected Player playerTwo;
    protected Player activePlayer;
    protected HashMap<Player, GameField> gameFields;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        gameFields = new HashMap<>();
        gameFields.put(playerOne, new GameField());
        gameFields.put(playerTwo, new GameField());
    }

    public void start()
    {
        activePlayer = (activePlayer == playerTwo) ? playerOne : playerTwo;
    }
}
