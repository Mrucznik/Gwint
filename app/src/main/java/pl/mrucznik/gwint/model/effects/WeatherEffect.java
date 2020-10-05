package pl.mrucznik.gwint.model.effects;

import androidx.annotation.NonNull;

import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;

public class WeatherEffect extends StrengthEffect {
    private AttackRow attackRow;

    public WeatherEffect(AttackRow attackRow)
    {
        piority = StrengthEffectsPriority.Weather;
        this.attackRow = attackRow;
    }

    @Override
    public boolean areEffectsAffectedOnCard(GwentCard card) {
        return !card.isGolden() && card.getAttackRow() == attackRow && card.isFightingCard();
    }

    @Override
    public int getCardStrengthAfterEffectAffected(GwentCard card) {
        if(areEffectsAffectedOnCard(card)) {
            return 1;
        }
        return card.getStrength();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof WeatherEffect)
        {
            WeatherEffect effect = (WeatherEffect)o;
            if(attackRow.ordinal() > effect.attackRow.ordinal())
                return 1;
            else if(attackRow.ordinal() < effect.attackRow.ordinal())
                return -1;
        }
        return super.compareTo(o);
    }
}
