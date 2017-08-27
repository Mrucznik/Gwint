package pl.mrucznik.gwint;

import pl.mrucznik.gwint.cards.GwentCard;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public class GameField {
    private Graveyard graveyard;
    private CardArea cardArea;

    public GameField()
    {
        graveyard = new Graveyard();
        cardArea = new CardArea();
    }

    public void putCard(GwentCard card)
    {
        cardArea.putCard(card);
    }

    public int getPoints()
    {
        return cardArea.getPoints();
    }
}
