package pl.mrucznik.gwint.model.effects;

import android.support.annotation.NonNull;

import java.util.Objects;

import pl.mrucznik.gwint.model.cards.GwentCard;

//Więź
public class TightBondEffect extends StrengthEffect {
    private GwentCard effectCard;

    public TightBondEffect(GwentCard effectCard)
    {
        piority = StrengthEffectsPriority.TightBond;
        this.effectCard = effectCard;
    }

    @Override
    public boolean areEffectsAffectedOnCard(GwentCard card) {
        return effectCard != card && card.getAttackRow() == effectCard.getAttackRow() && Objects.equals(card.getName(), effectCard.getName());
    }

    @Override
    public int getCardStrengthAfterEffectAffected(GwentCard card) {
        if(areEffectsAffectedOnCard(card)) {
            return card.getStrength() * 2;
        }
        return card.getStrength();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof TightBondEffect) {
            TightBondEffect effect = (TightBondEffect) o;
            return (effect.effectCard == effectCard) ? 0 : 1;
        }
        return super.compareTo(o);
    }
}
