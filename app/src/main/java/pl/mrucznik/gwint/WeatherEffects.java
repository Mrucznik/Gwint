package pl.mrucznik.gwint;

import pl.mrucznik.gwint.cards.GwentCard;
import pl.mrucznik.gwint.cards.WeatherEffect;

public class WeatherEffects extends RowEffect<WeatherEffect> {
    @Override
    public boolean areEffectsAffectedOnCard(GwentCard card)
    {
        if(card.isGolden())
            return false;

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
