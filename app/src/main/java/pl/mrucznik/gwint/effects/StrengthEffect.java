package pl.mrucznik.gwint.effects;

import android.support.annotation.NonNull;

import pl.mrucznik.gwint.cards.GwentCard;

abstract public class StrengthEffect implements Comparable {
    protected StrengthEffectsPriority piority = StrengthEffectsPriority.None;

    abstract public boolean areEffectsAffectedOnCard(GwentCard card);
    abstract public int getCardStrengthAfterEffectAffected(GwentCard card);

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof StrengthEffect)
        {
            StrengthEffect effect = (StrengthEffect)o;
            return piority.compareTo(effect.piority);
        }
        //TODO: Should we throw an exception?
        return 0;
    }
}
