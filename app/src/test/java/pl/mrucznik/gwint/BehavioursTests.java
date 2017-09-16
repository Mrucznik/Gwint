package pl.mrucznik.gwint;

import android.os.Build;
import org.junit.Test;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import pl.mrucznik.gwint.controller.activities.IGameController;
import pl.mrucznik.gwint.model.Game;
import pl.mrucznik.gwint.model.Player;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;
import pl.mrucznik.gwint.model.cards.GwentCards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BehavioursTests {
    private Game game;
    private Player[] player;

    public BehavioursTests() {
        player = new Player[2];
        player[0] = new Player("Tester pierwszy");
        player[1] = new Player("Tester drugi");

        game = new Game(new IGameController() {
            @Override
            public void startGame() {

            }

            @Override
            public void showRowMenu(Consumer<AttackRow> callback) {

            }

            @Override
            public void sendButtonMessage(String message, String buttonMeesage) {

            }

            @Override
            public void updatePoints(Map<Player, Map<AttackRow, Integer>> points) {

            }

            @Override
            public void chooseCard(Stream<GwentCard> cards, Consumer<GwentCard> callback) {

            }

            @Override
            public void sendMessage(String message) {

            }

            @Override
            public void updatePlayer(Player player) {

            }

            @Override
            public void onNextRound() {

            }

            @Override
            public void onGameEnds() {

            }
        }, player[0], player[1]);
    }

    //region pozoga
    @Test
    public void PozogaTest_OneCard() throws Exception {
        game.processCard(GwentCards.getCard("Cynthia")); //4 p0
        game.processCard(GwentCards.getCard("Ves")); //5 p1
        assertEquals(4, game.getPoints(player[0]));
        assertEquals(5, game.getPoints(player[1]));

        game.processCard(GwentCards.getCard("Pożoga")); //p0

        assertEquals(4, game.getPoints(player[0]));
        assertEquals(0, game.getPoints(player[1]));

        assertTrue(game.getGraveyardCards(player[1]).contains(GwentCards.getCard("Ves")));
    }

    @Test
    public void PozogaTest_ManyCards() throws Exception {
        game.processCard(GwentCards.getCard("Rainfarn")); //p0 4 cc
        game.processCard(GwentCards.getCard("Ves")); //p1 5 cc destroyed
        game.processCard(GwentCards.getCard("Emiel Regis Rohellec Terzieff")); //p0 5 cc destroyed
        game.processCard(GwentCards.getCard("Yarpen Zigrin")); //p1 2 cc
        game.processCard(GwentCards.getCard("Sabrina Glevissig")); //p0 4 lr
        game.processCard(GwentCards.getCard("Sheala de Tancarville")); //p1 5 lr destroyed
        assertEquals(13, game.getPoints(player[0]));
        assertEquals(12, game.getPoints(player[1]));

        game.processCard(GwentCards.getCard("Pożoga")); //p0
        game.processCard(GwentCards.getCard("Pożoga")); //p1

        assertEquals(8, game.getPoints(player[0]));
        assertEquals(2, game.getPoints(player[1]));


        assertTrue(game.getGraveyardCards(player[0]).contains(GwentCards.getCard("Emiel Regis Rohellec Terzieff")));

        assertTrue(game.getGraveyardCards(player[1]).contains(GwentCards.getCard("Ves")));
        assertTrue(game.getGraveyardCards(player[1]).contains(GwentCards.getCard("Sheala de Tancarville")));
    }

    @Test
    public void PozogaTest_NoCards() throws Exception {
        game.processCard(GwentCards.getCard("Pożoga"));

        assertEquals(0, game.getPoints(player[0]));
        assertEquals(0, game.getPoints(player[1]));

        assertEquals(0, game.getGraveyardCards(player[0]).size());
    }

    @Test
    public void PozogaTest_GoldCards() throws Exception {
        game.processCard(GwentCards.getCard("Cynthia")); //4 lr
        game.processCard(GwentCards.getCard("Ves")); //5 cc
        game.processCard(GwentCards.getCard("Geralt z Rivii")); //15
        game.processCard(GwentCards.getCard("Pożoga"));

        assertEquals(15, game.getPoints(player[0]));
        assertEquals(5, game.getPoints(player[1]));
    }

    @Test
    public void PozogaTest_CardsWithEffects() throws Exception {
        game.processCard(GwentCards.getCard("Cynthia")); //4 lr
        game.processCard(GwentCards.getCard("Ves")); //5 cc
        game.processCard(GwentCards.getCard("Trebusz")); //6 s
        game.processCard(GwentCards.getCard("Ulewny deszcz")); //0
        game.processCard(GwentCards.getCard("Ves")); //5 cc

        assertEquals(10, game.getPoints(player[0]));
        assertEquals(5, game.getPoints(player[1]));

        game.processCard(GwentCards.getCard("Pożoga"));

        assertEquals(5, game.getPoints(player[0]));
        assertEquals(5, game.getPoints(player[1]));
    }
    //endregion
}