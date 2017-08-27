package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.GwentCard;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public class Player {
    private String name;

    public Player(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
