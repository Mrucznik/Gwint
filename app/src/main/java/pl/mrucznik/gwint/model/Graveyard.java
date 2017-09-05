package pl.mrucznik.gwint.model;

import java.util.ArrayList;

import pl.mrucznik.gwint.model.cards.GwentCard;

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
