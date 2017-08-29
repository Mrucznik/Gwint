package pl.mrucznik.gwint;

import java.util.Stack;

import pl.mrucznik.gwint.cards.GwentCard;


public class TestPlayer extends Player {
    Stack<GwentCard> cards;

    public TestPlayer(String name, Stack<GwentCard> cards) {
        super(name);
        this.cards = cards;
    }

    public GwentCard throwCard()
    {
        if(cards.empty())
            return null;
        return cards.pop();
    }
}
