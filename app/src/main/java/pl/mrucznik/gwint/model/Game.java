package pl.mrucznik.gwint.model;

import java.util.Collection;
import java.util.HashMap;

import pl.mrucznik.gwint.model.cards.GwentCard;
import pl.mrucznik.gwint.model.effects.StrengthEffect;

public class Game {
    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;
    private HashMap<Player, GameField> gameFields;
    private int round = 0;

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        activePlayer = playerOne;

        gameFields = new HashMap<>();
        gameFields.put(playerOne, new GameField());
        gameFields.put(playerTwo, new GameField());

        //cardController = new CardController(gameFields.values(), effectControler);
    }

    public void processCard(GwentCard card) throws InvaildCardException {
        switch(card.getCardBehaviour())
        {
            case None:
                //Strength effects
            case Mroz:
            case Mgla:
            case Deszcz:
            case CzysteNiebo:
                //TightBond effect
            case Wiez:
                //HighMorale effect
            case WysokieMorale:
                putCard(card);
                nextPlayer();
                break;

            case RogDowodcy:
                //TODO: rząd wybiera się w GUI aplikacji
                break;
            case FoltestZdobywca:
                //Podwaja siłę wszystkich twoich jednostek oblężniczych (o ile w ich rzędzie nie ma już Rogu Dowódcy). - zwykły róg dowódcy
                break;
            case RogJaskra:
                //rząd wybiera się w GUI aplikacji
                break;
            case Pozoga:
                //TODO: następna przyłożona karta trafia na cmentarz (nie złotą)
                break;
            case Manekin:
                //TODO: usuwa następną przyłożoną kartę (nie złotą)
                break;
            case Braterstwo:
                //TODO: wyrzuca wszystkie karty takiego samego typu
                break;
            case Szpieg:
                //TODO: usuwa następną przyłożoną kartę (nie złotą)
                break;
            case Zrecznosc:
                //TODO: rząd wybiera się w GUI aplikacji
                break;
            case Medyk:
                //TODO: wskrzesza następną przyłożoną kartę z cmentarza (nie złotą)
                break;
            case PozogaSmoka:
                //TODO: jeśli przeciwnik ma w tym samym rzędzie, co rzucona karta, 10 punktów, usuń następną wybraną kartę przeciwnika
                break;
            case FoltestWladca:
                //TODO: Niszczy najsilniejszą/e jednostkę/ki oblężnicze twojego przeciwnika, jeśli suma siły jego jednostek oblężniczych wynosi 10 lub więcej.
                break;
            case FlotestSyn: //CardControl
                // TODO: Niszczy najsilniejszą/e jednostkę/i dalekiego zasięgu twojego przeciwnika, jeśli suma siły jego jednostek dalekiego zasięgu wynosi 10 lub więcej.
                break;
            case EmhyrNajezdzca: //CardControl
                //TODO: Gdy gracz przywraca jednostkę na pole bitwy, przywrócona zostaje losowa jednostka. Dotyczy obu graczy.
                break;
            case EmhyrPlomien: //CardControl
                //TODO: Blokuje umiejętność dowódcy twojego przeciwnika.
                break;
            case EmhyrPan: //CardControl
                //TODO: Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.
                break;
            case EmhyrCesarz: //CardControl
                //TODO: Obejrzyj trzy losowe karty z ręki przeciwnika
                break;
            case Krowa:
                //TODO: po usunięciu stwórz jednostkę siły bydlęce - pytanie jak to obsłużyć
                break;
        }
    }

    private void putCard(GwentCard card) throws InvaildCardException {
        gameFields.get(activePlayer).putCard(card);
    }

    private void nextPlayer()
    {
        activePlayer = (activePlayer == playerTwo) ? playerOne : playerTwo;
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
