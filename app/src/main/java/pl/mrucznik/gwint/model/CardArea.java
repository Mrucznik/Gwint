package pl.mrucznik.gwint.model;

import java.util.ArrayList;

import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;

public class CardArea {
    private GwentCard king;
    private ArrayList<GwentCard> cards;

    CardArea()
    {
        cards = new ArrayList<>();
    }

    public void putCard(GwentCard card) throws InvaildCardException {
        if(card.getAttackRow() == AttackRow.King)
        {
            if(king == null)
            {
                king = card;
            }
            else
            {
                throw new InvaildCardException("King card already on game field!");
            }
        }

        cards.add(card);
    }

    public ArrayList<GwentCard> getAllCards()
    {
        return cards;
    }

    public void clear()
    {
        cards.clear();
    }
}
