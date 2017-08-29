package pl.mrucznik.gwint;

import java.util.ArrayList;
import java.util.HashSet;

import pl.mrucznik.gwint.cards.GwentCard;

public abstract class RowEffect<T> {
    protected HashSet<T> effectList;

    public RowEffect()
    {
        effectList = new HashSet<>();
    }

    public HashSet<T> getEffects() {
        return effectList;
    }

    public void addEffect(T weatherEffect) {
        effectList.add(weatherEffect);
    }

    public void removeEffect(T weatherEffect) {
        effectList.remove(weatherEffect);
    }

    public void clearEffects() { effectList.clear(); }

    abstract public boolean areEffectsAffectedOnCard(GwentCard card);
}
