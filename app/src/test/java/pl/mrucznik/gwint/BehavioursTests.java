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
                System.out.println(message);
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

    //region dragon effect tests
    @Test
    public void DragonTest_DragonsOnly() throws Exception {
        game.processCard(GwentCards.getCard("Villentretenmerth")); //7 p0
        game.processCard(GwentCards.getCard("Villentretenmerth")); //7 p1
        game.processCard(GwentCards.getCard("Villentretenmerth")); //7 p0
        game.processCard(GwentCards.getCard("Villentretenmerth")); //7 p1

        assertEquals(0, game.getPoints(player[0]));
        assertEquals(14, game.getPoints(player[1]));

        assertTrue(game.getGraveyardCards(player[0]).contains(GwentCards.getCard("Villentretenmerth")));
    }


    @Test
    public void DragonTest_NotEnoughPoints() throws Exception {
        game.processCard(GwentCards.getCard("Ves")); //5 p0
        game.processCard(GwentCards.getCard("Pusta karta")); //p1
        game.processCard(GwentCards.getCard("Sabrina Glevissig")); //4 p0
        game.processCard(GwentCards.getCard("Pusta karta")); //p1
        game.processCard(GwentCards.getCard("Trebusz")); //6 p0
        game.processCard(GwentCards.getCard("Villentretenmerth")); //7 p1

        assertEquals(15, game.getPoints(player[0]));
        assertEquals(7, game.getPoints(player[1]));
    }

    @Test
    public void DragonTest_ManyRows() throws Exception {
        game.processCard(GwentCards.getCard("Ves")); //5 p0
        game.processCard(GwentCards.getCard("Pusta karta")); //p1
        game.processCard(GwentCards.getCard("Sabrina Glevissig")); //4 p0
        game.processCard(GwentCards.getCard("Pusta karta")); //p1
        game.processCard(GwentCards.getCard("Trebusz")); //6 p0
        game.processCard(GwentCards.getCard("Pusta karta")); //p1
        game.processCard(GwentCards.getCard("Ves")); //5 p0
        game.processCard(GwentCards.getCard("Villentretenmerth")); //7 p1

        assertEquals(10, game.getPoints(player[0]));
        assertEquals(7, game.getPoints(player[1]));

        assertTrue(game.getGraveyardCards(player[0]).contains(GwentCards.getCard("Ves")));
    }
    //endregion

    //region braterstwo tests
    //TODO: Braterstwo tests - brak kart by sprawdzić
    @Test
    public void BraterstwoTest() throws Exception {

    }

    //endregion

    //region manekin tests
    @Test
    public void ManekinTest() throws Exception {
        game.processCard(GwentCards.getCard("Balista")); //6 s p0
        game.processCard(GwentCards.getCard("Pusta karta"));
        game.processCard(GwentCards.getCard("Ves")); //5 cc p0
        game.processCard(GwentCards.getCard("Pusta karta"));
        game.processCard(GwentCards.getCard("Manekin do ćwiczeń")); //p0
        game.processCard(GwentCards.getCard("Balista")); //p0
        game.processCard(GwentCards.getCard("Geralt z Rivii")); //p1

        assertEquals(5, game.getPoints(player[0]));
        assertEquals(15, game.getPoints(player[1]));
    }
    //endregion

    //region medyk tests
    @Test
    public void MedykTest() throws Exception {
        //TODO
    }
    //endregion

    //region szpieg tests
    @Test
    public void SpyTest() throws Exception {
        //siege dragon effect
        game.processCard(GwentCards.getCard("Stefan Skellen")); //9 p0
        game.processCard(GwentCards.getCard("Talar")); //1 p1
        game.processCard(GwentCards.getCard("Avallac'h")); //0 p0

        assertEquals(1, game.getPoints(player[0]));
        assertEquals(9, game.getPoints(player[1]));
    }

    //endregion

    //region rog dowodcy tests
    //TODO: Róg dowódcy tests
    //endregion

    //region zrecznosc tests
    //TODO: Zrecznosc tests
    //endregion

    //region kings testing
    //TODO: Kings testing

    @Test
    public void FoltestWladcaTest() throws Exception {
        //siege dragon effect
        game.processCard(GwentCards.getCard("Balista")); //6 p0
        game.processCard(GwentCards.getCard("Pusta karta")); //p1
        game.processCard(GwentCards.getCard("Trebusz")); //6 p0
        game.processCard(GwentCards.getCard("Foltest - Żelazny Władca")); //king p1

        assertEquals(0, game.getPoints(player[0]));
        assertEquals(0, game.getPoints(player[1]));

        assertTrue(game.getGraveyardCards(player[0]).contains(GwentCards.getCard("Balista")));
        assertTrue(game.getGraveyardCards(player[0]).contains(GwentCards.getCard("Trebusz")));
    }

    @Test
    public void FlotestSynTest() throws Exception {
        //long range effect
        game.processCard(GwentCards.getCard("Assier var Anahid")); //6 p0
        game.processCard(GwentCards.getCard("Pusta karta")); //p1
        game.processCard(GwentCards.getCard("Filippa Eilhart")); //10 p0
        game.processCard(GwentCards.getCard("Foltest - Syn Medella")); //7 p1

        assertEquals(10, game.getPoints(player[0]));
        assertEquals(0, game.getPoints(player[1]));

        assertTrue(game.getGraveyardCards(player[0]).contains(GwentCards.getCard("Assier var Anahid")));
    }


    //endregion

    //region pass tests
    @Test
    public void PassTest_ActivePlayerTest() throws Exception {
        game.processCard(GwentCards.getCard("Ves")); //5 p0
        game.processCard(GwentCards.getCard("Cynthia")); //4 p1
        game.processCard(GwentCards.getCard("Pass")); //p0
        game.processCard(GwentCards.getCard("Cynthia")); //4 p1
        game.processCard(GwentCards.getCard("Cynthia")); //4 p1

        assertEquals(5, game.getPoints(player[0]));
        assertEquals(12, game.getPoints(player[1]));
    }

    @Test
    public void PassTest_P1Win() throws Exception {
        game.processCard(GwentCards.getCard("Ves")); //5 p0
        game.processCard(GwentCards.getCard("Cynthia")); //4 p1
        game.processCard(GwentCards.getCard("Pass")); //p0
        game.processCard(GwentCards.getCard("Pass")); //p1

        assertEquals(1, player[0].getWins());
        assertEquals(0, player[1].getWins());
    }


    @Test
    public void PassTest_P2Win() throws Exception {
        game.processCard(GwentCards.getCard("Ves")); //5 p0
        game.processCard(GwentCards.getCard("Cynthia")); //4 p1
        game.processCard(GwentCards.getCard("Pass")); //p0
        game.processCard(GwentCards.getCard("Ves")); //5 p1
        game.processCard(GwentCards.getCard("Pass")); //p1

        assertEquals(0, player[0].getWins());
        assertEquals(1, player[1].getWins());
    }


    @Test
    public void PassTest_BothPlayersWins() throws Exception {
        game.processCard(GwentCards.getCard("Ves"));
        game.processCard(GwentCards.getCard("Ves"));
        game.processCard(GwentCards.getCard("Pass"));
        game.processCard(GwentCards.getCard("Pass"));


        assertEquals(1, player[0].getWins());
        assertEquals(1, player[1].getWins());
    }
    //endregion
}
