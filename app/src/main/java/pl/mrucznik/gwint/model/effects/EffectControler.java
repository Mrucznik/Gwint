package pl.mrucznik.gwint.model.effects;
import java.util.Collection;
import java.util.TreeSet;

import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.CardBehaviour;
import pl.mrucznik.gwint.model.cards.GwentCard;

public class EffectControler {
    private TreeSet<StrengthEffect> effects;

    public EffectControler()
    {
        effects = new TreeSet<>();
    }

    public void addEffectIfExists(GwentCard effectCard)
    {
        switch (effectCard.getCardBehaviour())
        {
            case RogDowodcy:
                effects.add(new HornEffect(effectCard.getAttackRow()));
                break;
            case Mroz:
                effects.add(new WeatherEffect(AttackRow.CloseCombat));
                break;
            case Mgla:
                effects.add(new WeatherEffect(AttackRow.LongRange));
                break;
            case Deszcz:
                effects.add(new WeatherEffect(AttackRow.Siege));
                break;
            case CzysteNiebo:
                effects.remove(new WeatherEffect(AttackRow.CloseCombat));
                effects.remove(new WeatherEffect(AttackRow.LongRange));
                effects.remove(new WeatherEffect(AttackRow.Siege));
                break;
            case Wiez:
                effects.add(new TightBondEffect(effectCard));
                break;
            case WysokieMorale:
                effects.add(new MoraleEffect(effectCard));
                break;
            case RogJaskra:
                effects.add(new JaskierHornEffect(effectCard.getAttackRow(), effectCard));
                break;
            case FoltestZdobywca:
                effects.add(new HornEffect(AttackRow.Siege));
                break;
        }
    }

    public void removeEffect(GwentCard effectCard)
    {
        switch (effectCard.getCardBehaviour())
        {
            case RogDowodcy:
                effects.remove(new HornEffect(effectCard.getAttackRow()));
                break;
            case Mroz:
            case Mgla:
            case Deszcz:
                effects.remove(new WeatherEffect(effectCard.getAttackRow()));
                break;
            case Wiez:
                effects.remove(new TightBondEffect(effectCard));
                break;
            case WysokieMorale:
                effects.remove(new MoraleEffect(effectCard));
                break;
            case RogJaskra:
                effects.remove(new JaskierHornEffect(effectCard.getAttackRow(), effectCard));
                break;
            case FoltestZdobywca:
                effects.remove(new WeatherEffect(AttackRow.Siege));
                break;
        }
    }

    public boolean areEffectsAffectedOnCard(GwentCard card)
    {
        for (StrengthEffect effect: effects) {
            if(effect.areEffectsAffectedOnCard(card)) {
                return true;
            }
        }
        return false;
    }

    public int getCardStrengthAfterEffectsAffected(GwentCard card)
    {
        for (StrengthEffect effect : effects) {
            if(areEffectsAffectedOnCard(card)) {
                card.setStrength(effect.getCardStrengthAfterEffectAffected(card));
            }
        }
        int cardStrength = card.getStrength();
        card.resetStrength();
        return cardStrength;
    }

    public void clear() {
        effects.clear();
    }

    public Collection<StrengthEffect> getAllEffects()
    {
        return effects;
    }
}
