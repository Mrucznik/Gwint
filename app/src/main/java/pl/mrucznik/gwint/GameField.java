package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.GwentCard;

public class GameField {
    private Graveyard graveyard;
    private CardArea cardArea;

    public GameField()
    {
        graveyard = new Graveyard();
        cardArea = new CardArea();
    }

    public void putCard(IGameControler gameControler, GwentCard card) throws InvaildCardException {
        cardArea.putCard(gameControler, card);
    }

    public void clearCardArea()
    {
        graveyard.addCarts(cardArea.getAllCards());
        cardArea.clear();
    }

    public int getPoints()
    {
        return cardArea.getPoints();
    }
    public ArrayList<RowEffect> getActiveEffects() { return cardArea.getActiveEffects(); }
}
