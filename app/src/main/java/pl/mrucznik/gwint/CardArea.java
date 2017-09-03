package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.AttackRow;
import pl.mrucznik.gwint.cards.GwentCard;
import pl.mrucznik.gwint.effects.EffectControler;

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
