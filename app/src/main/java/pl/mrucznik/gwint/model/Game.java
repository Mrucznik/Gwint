package pl.mrucznik.gwint.model;

import java.util.Collection;
import java.util.HashMap;

import pl.mrucznik.gwint.controller.GameController;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.CardBehaviour;
import pl.mrucznik.gwint.model.cards.GwentCard;
import pl.mrucznik.gwint.model.effects.StrengthEffect;

public class Game {
    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;
    private HashMap<Player, GameField> gameFields;
    private GameController gameController;
    private int round = 0;
    private GwentCard waitForNextCard;

    public Game(GameController gameController, Player playerOne, Player playerTwo) {
        this.gameController = gameController;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        activePlayer = playerOne;

        gameFields = new HashMap<>();
        gameFields.put(playerOne, new GameField());
        gameFields.put(playerTwo, new GameField());

        //cardController = new CardController(gameFields.values(), effectControler);
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
    }

    private void processCardBehaviour(GwentCard card) {

        //Braterstwo
        if(waitForNextCard != null) {
            switch(waitForNextCard.getCardBehaviour())
            {
                case Braterstwo:
                    if(waitForNextCard != null && !waitForNextCard.getName().equals(card.getName())) {
                        nextPlayer();
                        waitForNextCard = null;
                    }
                    break;
                case Manekin:
                    if(gameFields.get(activePlayer).cardExists(card)) {
                        gameFields.get(activePlayer).removeCard(card);
                        waitForNextCard = null;
                    } else {
                        gameController.sendMessage("Ta karta nie istnieje na polu rozgrywk.");
                    }
                    return;
                case PozogaSmoka:
                case FoltestWladca:
                case FlotestSyn: //CardControl
                    if(gameFields.get(getNextPlayer()).cardExists(card) && waitForNextCard.getAttackRow() == card.getAttackRow()) {
                        gameFields.get(getNextPlayer()).removeCard(card);
                        waitForNextCard = null;
                    } else {
                        gameController.sendMessage("Ta karta nie istnieje na polu rozgrywki/nie jest w tym samym rzędzie.");
                    }
                    return;
            }
        }

        switch(card.getCardBehaviour())
        {
            //TODO: Przenieść implementacje, poprawić hermetyzacje
            case None:
            case Mroz:
            case Mgla:
            case Deszcz:
            case CzysteNiebo: //TightBond effect
            case Wiez: //HighMorale effect
            case WysokieMorale:
            case RogDowodcy:
            case FoltestZdobywca: //Podwaja siłę wszystkich twoich jednostek oblężniczych (o ile w ich rzędzie nie ma już Rogu Dowódcy). - zwykły róg dowódcy
            case RogJaskra:
            case Zrecznosc: //rząd wybiera się w GUI aplikacji, zaimplementowane dla AttackRow.All
                //domyślne zachowanie lub efekty zmieniające siłę kart
                break;
            case Pozoga: //niszczy najsilniejsze karty
                gameFields.get(getNextPlayer()).getCards().removeAll(gameFields.get(getNextPlayer()).getStrongestCards());
                break;
            case Manekin:
                waitForNextCard = card;
                gameController.sendMessage("Wybierz kartę, którą chcesz podmienić.");
                break;
            case Braterstwo: //wyrzuca wszystkie karty takiego samego typu
                gameFields.get(activePlayer).putCard(card);
                waitForNextCard = card;
                return;
            case Szpieg:
                gameController.sendMessage("Dobierz dwie karty.");
                gameFields.get(getNextPlayer()).putCard(card);
                break;
            case Medyk:
                //Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.
                gameController.chooseCard(gameFields.get(activePlayer).getGraveyard().stream().filter(c -> card.isFightingCard()).filter(c -> !card.isGolden()), (GwentCard resurectedCard) -> {
                    gameFields.get(activePlayer).removeGraveyardCard(resurectedCard);
                    processCard(resurectedCard);
                });
                return;
            case PozogaSmoka: //jeśli przeciwnik ma w tym samym rzędzie, co rzucona karta, 10 punktów, usuń następną wybraną kartę przeciwnika
            case FoltestWladca: //Niszczy najsilniejszą/e jednostkę/ki oblężnicze twojego przeciwnika, jeśli suma siły jego jednostek oblężniczych wynosi 10 lub więcej.
            case FlotestSyn: //Niszczy najsilniejszą/e jednostkę/i dalekiego zasięgu twojego przeciwnika, jeśli suma siły jego jednostek dalekiego zasięgu wynosi 10 lub więcej.
                if(gameFields.get(getNextPlayer()).getRowsPoints().getOrDefault(card.getAttackRow(), 0) >= 10) {
                    waitForNextCard = card;
                    gameController.sendMessage("Wybierz kartę do zniszczenia.");
                }
                gameFields.get(activePlayer).putCard(card);
                return;
            case EmhyrNajezdzca: //CardControl
                //TODO: Gdy gracz przywraca jednostkę na pole bitwy, przywrócona zostaje losowa jednostka. Dotyczy obu graczy.
                break;
            case EmhyrPlomien: //CardControl
                //Blokuje umiejętność dowódcy twojego przeciwnika.
                gameController.sendMessage("Umiejętność wrogiego dowódcy zablokowana.");
                gameFields.get(getNextPlayer()).blockKing();
                break;
            case EmhyrPan: //CardControl
                //Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.
                gameController.chooseCard(gameFields.get(getNextPlayer()).getGraveyard().stream().filter(c -> card.isFightingCard()).filter(c -> !card.isGolden()), (GwentCard resurectedCard) -> {
                    gameFields.get(getNextPlayer()).removeGraveyardCard(resurectedCard);
                    processCard(resurectedCard);
                });
                return;
            case EmhyrCesarz: //CardControl
                //Obejrzyj trzy losowe karty z ręki przeciwnika
                gameController.sendMessage("Możesz wybrać z ręki przeciwnika karty, które chcesz obejrzeć.");
                break;
            case Krowa:
                //TODO: po usunięciu stwórz jednostkę siły bydlęce - pytanie jak to obsłużyć
                break;
        }

        gameFields.get(activePlayer).putCard(card);
        nextPlayer();
    }

    private void nextPlayer()
    {
        activePlayer = getNextPlayer();
    }

    private Player getNextPlayer()
    {
        return(activePlayer == playerTwo) ? playerOne : playerTwo;
    }

    private void nextRound()
    {
        gameFields.forEach(
                (k,v) -> v.clearCardArea()
        );
        round++;
    }

    public boolean isGameOn()
    {
        return round <= 2;
    }

    public int[] getPoints()
    {
        int[] pointsArray = new int[2];
        pointsArray[0] = gameFields.get(playerOne).getPoints();
        pointsArray[1] = gameFields.get(playerTwo).getPoints();
        return pointsArray;
    }
    public Player getActivePlayer() {
        return activePlayer;
    }

    public void printPoints()
    {
        gameFields.forEach((k,v) -> System.out.println(v.getPoints()));
    }

    public Collection<StrengthEffect> getActiveEffects(Player player) {
        return gameFields.get(player).getEffects();
    }
}
