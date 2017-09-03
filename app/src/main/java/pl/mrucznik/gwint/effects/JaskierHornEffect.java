package pl.mrucznik.gwint.effects;

import android.support.annotation.NonNull;

import pl.mrucznik.gwint.cards.AttackRow;
import pl.mrucznik.gwint.cards.GwentCard;

public class JaskierHornEffect extends HornEffect {
    private GwentCard jaskier;

    public JaskierHornEffect(AttackRow attackRow, GwentCard jaskier) {
        super(attackRow);
        this.jaskier = jaskier;
    }

    @Override
    public int getCardStrengthAfterEffectAffected(GwentCard card)
    {
        return card != jaskier ? super.getCardStrengthAfterEffectAffected(card) : card.getStrength();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof JaskierHornEffect) {
            JaskierHornEffect effect = (JaskierHornEffect) o;
            return (effect.jaskier == jaskier) ? 0 : 1;
        }
        return super.compareTo(o);
    }
}
