package pl.mrucznik.gwint.model.effects;

import android.support.annotation.NonNull;

import pl.mrucznik.gwint.model.cards.GwentCard;

public class MoraleEffect extends StrengthEffect {
    private GwentCard moraleCard;

    public MoraleEffect(GwentCard moraleCard)
    {
        piority = StrengthEffectsPriority.Morale;
        this.moraleCard = moraleCard;
    }

    @Override
    public boolean areEffectsAffectedOnCard(GwentCard card) {
        return !card.isGolden() && moraleCard.getAttackRow() == card.getAttackRow() && moraleCard != card;
    }

    @Override
    public int getCardStrengthAfterEffectAffected(GwentCard card) {
        if(areEffectsAffectedOnCard(card)) {
            return card.getStrength() + 1;
        }
        return card.getStrength();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof MoraleEffect)
        {
            MoraleEffect effect = (MoraleEffect)o;
            return (effect.moraleCard == moraleCard) ? 0 : 1;
        }
        return super.compareTo(o);
    }
}
