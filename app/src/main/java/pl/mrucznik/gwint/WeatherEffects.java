package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.GwentCard;
import pl.mrucznik.gwint.cards.WeatherEffect;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public class WeatherEffects extends RowEffect<WeatherEffect> {
    @Override
    public boolean effectsAffectedOnCard(GwentCard card)
    {
        switch(card.getAttackRow())
        {
            case None:
                return false;
            case King:
                return false;
            case CloseCombat:
                return effectList.contains(WeatherEffect.Cold);
            case LongRange:
                return effectList.contains(WeatherEffect.Fog);
            case Siege:
                return effectList.contains(WeatherEffect.Rain);
            case All:
                return false;
            default:
                return false;
        }
    }
}
