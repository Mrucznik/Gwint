package pl.mrucznik.gwint;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import pl.mrucznik.gwint.cards.GwentCard;
import pl.mrucznik.gwint.cards.GwentCards;

import static org.junit.Assert.*;


public class GameTests {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void gameTest() throws Exception
    {
        Player playerOne = new Player("Szymon");
        Player playerTwo = new Player("Seba");

        GameController gameController = new GameController(playerOne, playerTwo);

        int turn = 1;
        while(gameController.isGameOn())
        {
            System.out.println("\nKolejka numer " + turn);
            System.out.println("Aktywne efekty: ");
            System.out.print(playerOne + ": ");
            gameController.getActiveEffects(playerOne).forEach((v) -> System.out.print(v.toString() + " "));
            System.out.print("\n" + playerTwo + ": ");
            gameController.getActiveEffects(playerTwo).forEach((v) -> System.out.print(v.toString() + " "));


            int[] oldPoints = gameController.getPoints();
            GwentCard gwentCard = GwentCards.generateRandomCard();
            System.out.println("\n" + gameController.getActivePlayer().toString() + " rzuca kartę " + gwentCard.getName() + " siła: " + gwentCard.getStrength());
            try {
                gameController.processCard(gwentCard);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            int[] points = gameController.getPoints();
            System.out.println("Punkty " + playerOne.toString() + ": " + points[0] + " (" + (points[0] - oldPoints[0]) + ")");
            System.out.println("Punkty " + playerTwo.toString() + ": " + points[1] + " (" + (points[1] - oldPoints[1]) + ")");
            if(turn++ == 150)
                break;
        }

        System.out.println("Wynik końcowy ");
        gameController.printPoints();
    }
}
