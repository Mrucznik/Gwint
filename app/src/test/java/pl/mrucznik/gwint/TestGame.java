package pl.mrucznik.gwint;

import pl.mrucznik.gwint.cards.GwentCard;

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
                try {
                    gameFields.get(activePlayer).putCard(thrownCard);
                } catch (InvaildCardException e) {
                    e.printStackTrace();
                }
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
