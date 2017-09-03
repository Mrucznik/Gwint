package pl.mrucznik.gwint.effects;

import android.support.annotation.NonNull;

import pl.mrucznik.gwint.cards.AttackRow;
import pl.mrucznik.gwint.cards.GwentCard;

public class HornEffect extends StrengthEffect {
    private AttackRow attackRow;

    public HornEffect(AttackRow attackRow)
    {
        piority = StrengthEffectsPriority.Horn;
        this.attackRow = attackRow;
    }

    @Override
    public boolean areEffectsAffectedOnCard(GwentCard card) {
        return !card.isGolden() && card.getAttackRow() == attackRow;
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
        if(o instanceof HornEffect)
        {
            HornEffect effect = (HornEffect)o;
            if(attackRow.ordinal() > effect.attackRow.ordinal())
                return 1;
            else if(attackRow.ordinal() < effect.attackRow.ordinal())
                return -1;
        }
        return super.compareTo(o);
    }
}
