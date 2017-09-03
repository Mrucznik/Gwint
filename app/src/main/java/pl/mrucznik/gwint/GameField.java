package pl.mrucznik.gwint;

import java.util.Collection;

import pl.mrucznik.gwint.cards.GwentCard;
import pl.mrucznik.gwint.effects.EffectControler;
import pl.mrucznik.gwint.effects.StrengthEffect;

public class GameField {
    private Graveyard graveyard;
    private CardArea cardArea;
    private EffectControler effectControler;

    public GameField()
    {
        graveyard = new Graveyard();
        cardArea = new CardArea();
        effectControler = new EffectControler();
    }

    public void putCard(GwentCard card) throws InvaildCardException {
        cardArea.putCard(card);
        effectControler.addEffectIfExists(card);
    }

    public void clearCardArea()
    {
        graveyard.addCarts(cardArea.getAllCards());
        cardArea.clear();
        effectControler.clear();
    }

    public int getPoints()
    {
        int points = 0;
        for (GwentCard card: cardArea.getAllCards()) {
            points += effectControler.getCardStrengthAfterEffectsAffected(card);
        }
        return points;
    }

    public Collection<StrengthEffect> getEffects()
    {
        return effectControler.getAllEffects();
    }
}
