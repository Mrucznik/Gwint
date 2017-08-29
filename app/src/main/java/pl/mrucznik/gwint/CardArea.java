package pl.mrucznik.gwint;

import java.util.ArrayList;

import pl.mrucznik.gwint.cards.AttackRow;
import pl.mrucznik.gwint.cards.GwentCard;
import pl.mrucznik.gwint.cards.WeatherEffect;

class InvaildCardException extends Exception {
    InvaildCardException(String msg){
        super(msg);
    }
}

public class CardArea {
    private GwentCard king;
    private ArrayList<GwentCard> cards;
    private WeatherEffects weatherEffects;
    private HornEffects hornEffects;

    CardArea()
    {
        weatherEffects = new WeatherEffects();
        hornEffects = new HornEffects();
        cards = new ArrayList<>();
    }

    public void putCard(GwentCard card) throws InvaildCardException {
        if(card.getAttackRow() == AttackRow.King)
        {
            if(king == null)
            {
                king = card;
            }
            else
            {
                throw new InvaildCardException("Król już został rzucony!");
            }
        }

        cards.add(card);
    }

    private void activateCardBehaviour(GwentCard card)
    {
        switch(card.getCardBehaviour())
        {
            case None:
                break;
            case RogDowodcy:
                //rząd wybiera się w GUI aplikacji
                break;
            case Pozoga:
                //następna przyłożona karta trafia na cmentarz (nie złotą)
                break;
            case Manekin:
                //usuwa następną przyłożoną kartę (nie złotą)
                break;
            case Mroz:
                weatherEffects.addEffect(WeatherEffect.Cold);
                break;
            case Mgla:
                weatherEffects.addEffect(WeatherEffect.Fog);
                break;
            case Deszcz:
                weatherEffects.addEffect(WeatherEffect.Rain);
                break;
            case CzysteNiebo:
                weatherEffects.clearEffects();
                break;
            case Braterstwo:
                //przedłuża kolejke dla kart tego samego typu (taka sama nazwa)
                break;
            case Szpieg:
                //usuwa następną przyłożoną kartę (nie złotą)
                break;
            case Zrecznosc:
                //rząd wybiera się w GUI aplikacji
                break;
            case Medyk:
                //wskrzesza następną przyłożoną kartę z cmentarza (nie złotą)
                break;
            case Wiez:
                //wyszukaj rzucone karty tego samego typu i podwój ich siłę - nic nie rób, liczy się podczas obliczania punktów
                break;
            case WysokieMorale:
                //powiększa siłę o 1 wszystkich kart w rzędzie
                break;
            case PozogaSmoka:
                //jeśli przeciwnik ma w tym samym rzędzie, co rzucona karta, 10 punktów, usuń następną wybraną kartę przeciwnika
                break;
            case RogJaskra:
                //rząd wybiera się w GUI aplikacji
                break;
            case FoltestWladca:
                //Niszczy najsilniejszą/e jednostkę/ki oblężnicze twojego przeciwnika, jeśli suma siły jego jednostek oblężniczych wynosi 10 lub więcej.
                break;
            case FoltestZdobywca:
                //Podwaja siłę wszystkich twoich jednostek oblężniczych (o ile w ich rzędzie nie ma już Rogu Dowódcy).
                break;
            case FlotestSyn:
                // Niszczy najsilniejszą/e jednostkę/i dalekiego zasięgu twojego przeciwnika, jeśli suma siły jego jednostek dalekiego zasięgu wynosi 10 lub więcej.
                break;
            case EmhyrNajezdzca:
                //Gdy gracz przywraca jednostkę na pole bitwy, przywrócona zostaje losowa jednostka. Dotyczy obu graczy.
                break;
            case EmhyrPlomien:
                //Blokuje umiejętność dowódcy twojego przeciwnika.
                break;
            case EmhyrPan:
                //Wybierz kartę ze stosu kart odrzuconych twojego przeciwnika.
                break;
            case EmhyrCesarz:
                //Obejrzyj trzy losowe karty z ręki przeciwnika
                break;
            case Krowa:
                //po usunięciu stwórz jednostkę siły bydlęce
                break;
        }
    }

    private int calculateStrength(GwentCard card)
    {
        int strength = card.getStrength();
        if(weatherEffects.areEffectsAffectedOnCard(card))
            strength = 1;
        if(hornEffects.areEffectsAffectedOnCard(card))
            strength *= 2;
        return strength;
    }

    public int getPoints()
    {
        int score = 0;
        for(GwentCard card : cards)
        {
            score += calculateStrength(card);
        }
        return score;
    }

    public ArrayList<GwentCard> getAllCards()
    {
        return cards;
    }
}
