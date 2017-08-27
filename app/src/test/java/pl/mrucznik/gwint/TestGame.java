package pl.mrucznik.gwint;

import pl.mrucznik.gwint.cards.GwentCard;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public class TestGame extends Game {
    public TestGame(TestPlayer playerOne, TestPlayer playerTwo) {
        super(playerOne, playerTwo);
    }

    @Override
    public void start() {
        super.start();


        while(true)
        {
            super.activePlayer = (activePlayer == playerTwo) ? playerOne : playerTwo;

            GwentCard thrownCard = ((TestPlayer)activePlayer).throwCard();
            if(thrownCard != null)
            {
                gameFields.get(activePlayer).putCard(thrownCard);
                System.out.println("Gracz " + activePlayer.toString() + " rzucił kartę " + thrownCard.toString());
            }
            else
            {
                break;
            }
        }

        System.out.println(gameFields.get(playerOne).getPoints());
        System.out.println(gameFields.get(playerTwo).getPoints());
    }
}
