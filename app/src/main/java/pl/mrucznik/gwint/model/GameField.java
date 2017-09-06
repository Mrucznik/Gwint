package pl.mrucznik.gwint.model;

import android.util.ArraySet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.CardBehaviour;
import pl.mrucznik.gwint.model.cards.GwentCard;
import pl.mrucznik.gwint.model.effects.EffectControler;
import pl.mrucznik.gwint.model.effects.StrengthEffect;

public class GameField {
    private GwentCard king;
    private ArrayList<GwentCard> graveyard;
    private ArrayList<GwentCard> cards;
    private EffectControler effectControler;

    public GameField()
    {
        graveyard = new ArrayList<>();
        cards = new ArrayList<>();
        effectControler = new EffectControler();
    }

    public boolean putCard(GwentCard card)  {
        if(card.getAttackRow() == AttackRow.King) {
            if(kingPlaced()) {
                return false;
            }
            king = card;
        } else {
            cards.add(card);
        }
        effectControler.addEffectIfExists(card);
        return true;
    }

    public void removeCard(GwentCard card)
    {
        cards.remove(card);
    }

    public boolean kingPlaced()
    {
        return king == null;
    }

    public void blockKing()
    {
        king = new GwentCard(0, "Zablokowano", 0, AttackRow.King, false, CardBehaviour.None);
    }

    public void clearCardArea()
    {
        graveyard.addAll(cards);
        cards.clear();
        effectControler.clear();
    }

    public void moveToGraveyard(GwentCard card)
    {
        graveyard.remove(card);
        cards.add(card);
    }

    public int getPoints()
    {
        int points = 0;
        for (GwentCard card : cards) {
            points += effectControler.getCardStrengthAfterEffectsAffected(card);
        }
        return points;
    }


    public Map<AttackRow, Integer> getRowsPoints()
    {
        Map<AttackRow, Integer> points = new HashMap<>();
        for (GwentCard card : cards) {
            points.put(card.getAttackRow(), points.getOrDefault(card.getAttackRow(), 0) + effectControler.getCardStrengthAfterEffectsAffected(card));
        }
        return points;
    }

    public ArrayList<GwentCard> getStrongestCards()
    {
        return null;//TODO: Implement
    }

    public boolean cardExists(GwentCard card)
    {
        return cards.contains(card);
    }

    public ArrayList<GwentCard> getGraveyard() {
        return graveyard;
    }

    public ArrayList<GwentCard> getCards() {
        return cards;
    }

    public Collection<StrengthEffect> getEffects()
    {
        return effectControler.getAllEffects();
    }

    public void removeGraveyardCard(GwentCard card) {
        cards.remove(card);
    }
}