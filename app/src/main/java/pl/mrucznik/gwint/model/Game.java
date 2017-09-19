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

    public void start()
    {
        activePlayer = playerOne;
        gameController.sendMessage("Gra rozpoczęta: " + playerOne + " kontra " + playerTwo + ". Zaczyna " + activePlayer);
    }

    public void processCard(GwentCard card) {
        if(card.getAttackRow() == AttackRow.King && gameFields.get(activePlayer).kingPlaced()) {
            gameController.sendMessage("Król został już położony!");
            return;
        }

        if(card.getAttackRow() == AttackRow.All) {
            gameController.showRowMenu((AttackRow attackRow) -> {
                card.setAttackRow(attackRow);
                processCardBehaviour(card);
                updatePoints();
            });
        } else {
            processCardBehaviour(card);
            updatePoints();
        }
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
                    if(card.isGolden() || !card.isFightingCard())
                    {
                        gameController.sendMessage("Tej karty nie można podmienić.");
                        return;
                    }

                    if(gameFields.get(activePlayer).cardExists(card)) {
                        gameFields.get(activePlayer).removeCard(card);
                        waitForNextCard = null;
                        gameController.sendMessage("Karta " + card.getName() + " podmieniona.");
                        nextPlayer();
                    } else {
                        gameController.sendMessage("Ta karta nie istnieje na polu rozgrywki.");
                    }
                    return;
                case Medyk:
                    if(card.isGolden() || !card.isFightingCard())
                    {
                        gameController.sendMessage("Tej karty nie można uzdrowić.");
                        return;
                    }

                    if(gameFields.get(activePlayer).graveyardCardExists(card)) {
                        gameFields.get(activePlayer).removeGraveyardCard(card);
                        gameController.sendMessage("Karta " + card.getName() + " uzdrowiona.");
                    } else {
                        gameController.sendMessage("Tej karty nie ma na cmentarzu.");
                        return;
                    }
                    break;
                case EmhyrPan:
                    if(card.isGolden() || !card.isFightingCard())
                    {
                        gameController.sendMessage("Tej karty nie można przenieść.");
                        return;
                    }

                    if(gameFields.get(getOtherPlayer(activePlayer)).graveyardCardExists(card)) {
                        gameFields.get(getOtherPlayer(activePlayer)).removeGraveyardCard(card);
                        gameController.sendMessage("Karta " + card.getName() + " usunięta z cmentarza przeciwnika.");
                        waitForNextCard = null;
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
                gameFields.get(getOtherPlayer(activePlayer)).addEffect(card);
                gameController.sendMessage("Wszystkie karty w rzędzie blistego starcia osłabione do jednego punktu.");
                break;
            case Mgla:
                gameFields.get(getOtherPlayer(activePlayer)).addEffect(card);
                gameController.sendMessage("Wszystkie karty w rzędzie dalekiego zasięgu osłabione do jednego punktu.");
                break;
            case Deszcz:
                gameFields.get(getOtherPlayer(activePlayer)).addEffect(card);
                gameController.sendMessage("Wszystkie karty w rzędzie machin oblężniczych osłabione do jednego punktu.");
                break;
            case CzysteNiebo:
                gameFields.get(getOtherPlayer(activePlayer)).addEffect(card);
                gameController.sendMessage("Efekty pogodowe usunięte.");
                break;
            case Wiez: //TightBond effect
                //TODO: Wymyślić wiadomość
                break;
            case WysokieMorale://HighMorale effect
                gameController.sendMessage("Plus jeden punkt do wszystkich kart w tym samym rzędzie.");
                break;
            case FoltestZdobywca: //TODO: Testy
                gameController.sendMessage("Rząd machin oblężniczych wzmocniony.");
                break;
            case RogJaskra:
                gameController.sendMessage("Rząd bliskiego zasięgu wzmocniony.");
                break;
            case Zrecznosc: //rząd wybiera się w GUI aplikacji, zaimplementowane dla AttackRow.All
                gameController.sendMessage("Naciśnij na rząd, na który ma zostać rzucona karta.");
                break;
            case RogDowodcy: //TODO: Testy
                gameController.sendMessage("Naciśnij na rząd, który ma zostać wzmocniony.");
                break;
            case Pozoga: //niszczy najsilniejsze karty
                //TODO: move to method(s)
                int playerOneStrongestCardPoints = gameFields.get(playerOne).getStrongestNonGoldCardPoints();
                int playerTwoStrongestCardPoints = gameFields.get(playerTwo).getStrongestNonGoldCardPoints();
                if(playerOneStrongestCardPoints >= playerTwoStrongestCardPoints)
                {
                    for (GwentCard c : gameFields.get(playerOne).getStrongestNonGoldCards()) {
                        gameFields.get(playerOne).moveToGraveyard(c);
                        gameController.sendMessage("Zniszczono kartę" + c.getName() + ".");
                    }
                }
                if(playerOneStrongestCardPoints <= playerTwoStrongestCardPoints)
                {
                    for (GwentCard c : gameFields.get(playerTwo).getStrongestNonGoldCards()) {
                        gameFields.get(playerTwo).moveToGraveyard(c);
                        gameController.sendMessage("Zniszczono kartę" + c.getName() + ".");
                    }
                }
                break;
            case Manekin:
                gameController.sendMessage("Przyłóż kartę, którą chcesz podmienić.");
                return;
            case Braterstwo: //wyrzuca wszystkie karty takiego samego typu
                gameController.sendMessage("Jeżeli masz jeszcze karty tego samego typu, musisz je wyrzucić.");
                putCard(activePlayer, card);
                return;
            case Szpieg:
                putCard(getOtherPlayer(activePlayer), card);
                gameController.sendMessage("Dobierz dwie karty.");
                nextPlayer();
                return;
            case Medyk:
                //Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.
                gameController.sendMessage("Przyłóż kartę, którą chcesz uzdrowić.");
                putCard(activePlayer, card);
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
                gameFields.get(getOtherPlayer(activePlayer)).blockKing();
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
                    gameController.sendMessage("Następna runda! Zaczyna " + activePlayer + ".");
                } else {
                    nextPlayer();
                }
                return;
        }

        putCard(activePlayer, card);
        nextPlayer();
    }

    private void pozogaSmoka(GwentCard card, AttackRow attackRow)
    {
        GameField gameField = gameFields.get(getOtherPlayer(activePlayer));
        if(gameField.getRowsPoints().get(attackRow) >= 10) {
            for (GwentCard c : gameField.getStrongestNonGoldCards(attackRow)) {
                gameField.moveToGraveyard(c);
                gameController.sendMessage("Zniszczono kartę" + c.getName() + ".");
            }
        }
    }

    private void putCard(Player player, GwentCard card)
    {
        int op1p = getPoints(activePlayer);
        int op2p = getPoints(getNextPlayer());

        gameFields.get(player).putCard(card);

        int np1p = getPoints(activePlayer);
        int np2p = getPoints(getNextPlayer());

        int rp1p = np1p - op1p;
        int rp2p = np2p - op2p;

        if(rp1p != 0)
            gameController.sendMessage(activePlayer.toString() + " " + rp1p + " punktów.");

        if(rp2p != 0)
            gameController.sendMessage(getNextPlayer().toString() + " " + rp2p + " punktów.");

    }

    private void nextPlayer()
    {
        activePlayer = getNextPlayer();
        gameController.updatePlayer(activePlayer);
        gameController.sendMessage("Następny gracz.");
    }

    private Player getNextPlayer()
    {
        if(!playerOne.isActive()) return playerTwo;
        if(!playerTwo.isActive()) return playerOne;
        return (activePlayer == playerTwo) ? playerOne : playerTwo;
    }

    private Player getOtherPlayer(Player player)
    {
        if(player == playerOne)
            return playerTwo;
        else if(player == playerTwo)
            return playerOne;
        return null;
    }

    private void nextRound()
    {
        //process winners
        for (Player player : getWinners()) {
            player.addWin();
        }

        if(playerOne.getWins() >= 2 || playerTwo.getWins() >= 2) {
            endGame();
        } else {
            playerOne.setActive();
            playerTwo.setActive();

            gameFields.get(playerOne).clearCardArea();
            gameFields.get(playerTwo).clearCardArea();

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
