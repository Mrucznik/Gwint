package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.GwentCard;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public abstract class RowEffect<T> {
    protected ArrayList<T> effectList;

    public RowEffect()
    {
        effectList = new ArrayList<>();
    }

    public ArrayList<T> getEffects() {
        return effectList;
    }

    public void addEffect(T weatherEffect) {
        effectList.add(weatherEffect);
    }

    abstract public boolean effectsAffectedOnCard(GwentCard card);
}
