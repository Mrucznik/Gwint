package pl.mrucznik.gwint;

import pl.mrucznik.gwint.cards.AttackRow;
import pl.mrucznik.gwint.cards.GwentCard;

public class HornEffects extends RowEffect<AttackRow> {
    @Override
    public boolean areEffectsAffectedOnCard(GwentCard card) {
        if(card.isGolden())
            return false;

        switch(card.getAttackRow())
        {
            case None:
                return false;
            case King:
                return false;
            case CloseCombat:
                return effectList.contains(AttackRow.CloseCombat);
            case LongRange:
                return effectList.contains(AttackRow.LongRange);
            case Siege:
                return effectList.contains(AttackRow.Siege);
            case All:
                return false;
            default:
                return false;
        }
    }
}
