package pl.mrucznik.gwint;

import pl.mrucznik.gwint.cards.AttackRow;
import pl.mrucznik.gwint.cards.GwentCard;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public class HornEffects extends RowEffect<AttackRow> {
    @Override
    public boolean effectsAffectedOnCard(GwentCard card) {
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
