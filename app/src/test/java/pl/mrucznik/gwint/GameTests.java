package pl.mrucznik.gwint;

import org.junit.Test;

import pl.mrucznik.gwint.cards.GwentCards;

import static org.junit.Assert.*;
/**
 * Created by Mrucznik on 27.08.2017.
 */

public class GameTests {
    Game game;

    @Test
    public void RandomCardsTest() throws Exception {
        game = new TestGame(
                new TestPlayer("Szymon", GwentCards.generateCards(2)),
                new TestPlayer("Seba", GwentCards.generateCards(2))
        );

        game.start();
    }
}