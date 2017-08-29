package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.GwentCard;

public class Graveyard {
    private ArrayList<GwentCard> cards;

    Graveyard()
    {
        cards = new ArrayList<>();
    }

    public void addCart(GwentCard card)
    {
        cards.add(card);
    }

    public void addCarts(ArrayList<GwentCard> card)
    {
        cards.addAll(card);
    }
}
