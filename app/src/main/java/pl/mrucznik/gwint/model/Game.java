package pl.mrucznik.gwint.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mrucznik.gwint.controller.activities.IGameController;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.GwentCard;
import pl.mrucznik.gwint.model.effects.StrengthEffect;

public class Game {
    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;
    private HashMap<Player, GameField> gameFields;
    private IGameController gameController;
    private GwentCard waitForNextCard;


    public Game(IGameController gameController, Player playerOne, Player playerTwo) {
        this.gameController = gameController;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        gameFields = new HashMap<>();
        gameFields.put(playerOne, new GameField());
        gameFields.put(playerTwo, new GameField());
        activePlayer = playerOne;
    }

    /** @deprecated */
    @Deprecated
    public void start()
    {
        activePlayer = playerOne;
    }

    public void processCard(GwentCard card) {

        if(card.getAttackRow() == AttackRow.All) {
            gameController.showRowMenu((AttackRow attackRow) -> {
                card.setAttackRow(attackRow);
                processCardBehaviour(card);
            });
        } else {
            processCardBehaviour(card);
        }

        updatePoints();
    }

    private void updatePoints()
    {
        HashMap<Player, Map<AttackRow, Integer>> points = new HashMap<>();
        points.put(playerOne, gameFields.get(playerOne).getRowsPoints());
        points.put(playerTwo, gameFields.get(playerTwo).getRowsPoints());
        gameController.updatePoints(points);
    }

    //TODO: Rozbić na mniejsze metody/klasy
    private void processCardBehaviour(GwentCard card) {

        //Braterstwo
        if(waitForNextCard != null) {
            switch(waitForNextCard.getCardBehaviour())
            {
                case Braterstwo:
                    //next player if card other than brotherhood card threw
                    if(!waitForNextCard.getName().equals(card.getName())) {
                        nextPlayer();
                    }
                    break;
                case Manekin:
                    if(gameFields.get(activePlayer).cardExists(card)) {
                        gameFields.get(activePlayer).removeCard(card);
                        waitForNextCard = null;
                        nextPlayer();
                    } else {
                        gameController.sendMessage("Ta karta nie istnieje na polu rozgrywki.");
                    }
                    return;
                case Medyk:
                    if(gameFields.get(activePlayer).getGraveyard().contains(card)) {
                        gameFields.get(activePlayer).removeGraveyardCard(card);
                        gameController.sendMessage("Karta " + card.getName() + " uzdrowiona.");
                    } else {
                        gameController.sendMessage("Tej karty nie ma na cmentarzu.");
                        return;
                    }
                    break;
                case EmhyrPan:
                    if(gameFields.get(getNextPlayer()).getGraveyard().contains(card)) {
                        gameFields.get(getNextPlayer()).removeGraveyardCard(card);
                        gameController.sendMessage("Karta " + card.getName() + " usunięta z cmentarza przeciwnika.");
                        nextPlayer();
                    } else {
                        gameController.sendMessage("Tej karty nie ma na cmentarzu przeciwnika.");
                    }
                    return;
            }
        }
        waitForNextCard = card;

        switch(card.getCardBehaviour())
        {
            //TODO: Przenieść implementacje, poprawić hermetyzacje
            case None:
                break;
            case Mroz:
            case Mgla:
            case Deszcz:
            case CzysteNiebo:
                gameFields.get(getNextPlayer()).addEffect(card);
                break;
            case Wiez: //TightBond effect
            case WysokieMorale://HighMorale effect
            case RogDowodcy:
            case FoltestZdobywca: //Podwaja siłę wszystkich twoich jednostek oblężniczych (o ile w ich rzędzie nie ma już Rogu Dowódcy). - zwykły róg dowódcy
            case RogJaskra:
            case Zrecznosc: //rząd wybiera się w GUI aplikacji, zaimplementowane dla AttackRow.All
                //domyślne zachowanie lub efekty zmieniające siłę kart
                break;
            case Pozoga: //niszczy najsilniejsze karty
                for (GwentCard c : gameFields.get(getNextPlayer()).getStrongestNonGoldCards()) {
                    gameFields.get(getNextPlayer()).moveToGraveyard(c);
                }
                break;
            case Manekin:
                gameController.sendMessage("Przyłóż kartę, którą chcesz podmienić.");
                return;
            case Braterstwo: //wyrzuca wszystkie karty takiego samego typu
                gameController.sendMessage("Jeżeli masz jeszcze karty tego samego typu (ta sama nazwa), musisz je wyrzucić.");
                gameFields.get(activePlayer).putCard(card);
                return;
            case Szpieg:
                gameController.sendMessage("Dobierz dwie karty.");
                gameFields.get(getNextPlayer()).putCard(card);
                nextPlayer();
                return;
            case Medyk:
                //Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.
                gameController.sendMessage("Przyłóż kartę, którą chcesz uzdrowić.");
                return;
            case PozogaSmoka: //jeśli przeciwnik ma w tym samym rzędzie, co rzucona karta, 10 punktów, usuń następną wybraną kartę przeciwnika
                pozogaSmoka(card, card.getAttackRow());
                break;
            case FoltestWladca: //Niszczy najsilniejszą/e jednostkę/ki oblężnicze twojego przeciwnika, jeśli suma siły jego jednostek oblężniczych wynosi 10 lub więcej.
                pozogaSmoka(card, AttackRow.Siege);
                break;
            case FlotestSyn: //Niszczy najsilniejszą/e jednostkę/i dalekiego zasięgu twojego przeciwnika, jeśli suma siły jego jednostek dalekiego zasięgu wynosi 10 lub więcej.
                pozogaSmoka(card, AttackRow.LongRange);
                break;
            case EmhyrNajezdzca: //CardControl
                //TODO: Gdy gracz przywraca jednostkę na pole bitwy, przywrócona zostaje losowa jednostka. Dotyczy obu graczy.
                gameController.sendMessage("Niestety, ta umiejętność nie jest jeszcze obsługiwana w tej wersji.");
                break;
            case EmhyrPlomien: //CardControl
                //TODO: testy
                //Blokuje umiejętność dowódcy twojego przeciwnika.
                gameController.sendMessage("Umiejętność wrogiego dowódcy zablokowana.");
                gameFields.get(getNextPlayer()).blockKing();
                break;
            case EmhyrPan: //CardControl
                //TODO: testy
                //Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.
                gameController.sendMessage("Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.");
                return;
            case EmhyrCesarz: //CardControl
                //Obejrzyj trzy losowe karty z ręki przeciwnika
                gameController.sendMessage("Możesz wybrać z ręki przeciwnika karty, które chcesz obejrzeć.");
                break;
            case Krowa:
                //TODO: po usunięciu stwórz jednostkę siły bydlęce - pytanie jak to obsłużyć
                gameController.sendMessage("Niestety, ta karta nie jest jeszcze obsługiwana w tej wersji.");
                break;
            case Pass:
                activePlayer.jamOut();
                if(!playerOne.isActive() && !playerTwo.isActive())
                {
                    nextRound();
                    gameController.sendMessage("Następna runda!.");
                }
                nextPlayer();
                return;
        }

        gameFields.get(activePlayer).putCard(card);
        nextPlayer();
    }

    private void pozogaSmoka(GwentCard card, AttackRow attackRow)
    {
        if(gameFields.get(getNextPlayer()).getRowsPoints().get(attackRow) >= 10) {
            for (GwentCard c : gameFields.get(getNextPlayer()).getStrongestNonGoldCards(attackRow)) {
                gameFields.get(getNextPlayer()).moveToGraveyard(c);
            }
        }
    }

    private void nextPlayer()
    {
        activePlayer = getNextPlayer();
        gameController.updatePlayer(activePlayer);
    }

    private Player getNextPlayer()
    {
        if(!playerOne.isActive()) return playerTwo;
        if(!playerTwo.isActive()) return playerOne;
        return (activePlayer == playerTwo) ? playerOne : playerTwo;
    }

    private void nextRound()
    {
        //process winners
        getWinners().forEach(player -> player.addWin());

        if(playerOne.getWins() >= 2 || playerTwo.getWins() >= 2) {
            endGame();
        } else {
            playerOne.setActive();
            playerTwo.setActive();

            gameFields.forEach(
                    (k, v) -> v.clearCardArea()
            );

            //TODO: set appropriate active player
        }
        gameController.onNextRound();
    }

    public ArrayList<Player> getWinners()
    {
        ArrayList<Player> winners = new ArrayList<>();
        int playerOnePoints = gameFields.get(playerOne).getPoints();
        int playerTwoPoints = gameFields.get(playerTwo).getPoints();

        if(playerOnePoints >= playerTwoPoints) {
            winners.add(playerOne);
        }
        if(playerOnePoints <= playerTwoPoints) {
            winners.add(playerTwo);
        }
        return winners;
    }

    private void endGame()
    {
        gameController.onGameEnds();
    }

    public int[] getPoints()
    {
        int[] pointsArray = new int[2];
        pointsArray[0] = gameFields.get(playerOne).getPoints();
        pointsArray[1] = gameFields.get(playerTwo).getPoints();
        return pointsArray;
    }

    public int getPoints(Player player)
    {
        return gameFields.get(player).getPoints();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Collection<StrengthEffect> getActiveEffects(Player player) {
        return gameFields.get(player).getEffects();
    }

    public ArrayList<GwentCard> getGraveyardCards(Player player)
    {
        return gameFields.get(player).getGraveyard();
    }
}
