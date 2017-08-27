package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.AttackRow;
import pl.mrucznik.gwint.cards.GwentCard;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public class CardArea {
    private ArrayList<GwentCard> cards;
    private WeatherEffects weatherEffects;
    private HornEffects hornEffects;

    CardArea()
    {
        weatherEffects = new WeatherEffects();
        hornEffects = new HornEffects();
        cards = new ArrayList<>();
    };

    public void putCard(GwentCard card)
    {
        cards.add(card);
    }

    private int calculateStrength(GwentCard card)
    {
        int strength = card.getStrength();
        if(weatherEffects.effectsAffectedOnCard(card))
            strength = 1;
        if(hornEffects.effectsAffectedOnCard(card))
            strength *= 2;
        return strength;
    }

    public int getPoints()
    {
        int score = 0;
        for(GwentCard card : cards)
        {
            score += calculateStrength(card);
        }
        return score;
    }
}
