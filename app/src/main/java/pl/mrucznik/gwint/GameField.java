package pl.mrucznik.gwint;

import pl.mrucznik.gwint.cards.GwentCard;

public class GameField {
    private Graveyard graveyard;
    private CardArea cardArea;

    public GameField()
    {
        graveyard = new Graveyard();
        cardArea = new CardArea();
    }

    public void putCard(GwentCard card) throws InvaildCardException {
        cardArea.putCard(card);
    }

    public void clearCardArea()
    {
        graveyard.addCarts(cardArea.getAllCards());
        cardArea = new CardArea();
    }

    public int getPoints()
    {
        return cardArea.getPoints();
    }
}
