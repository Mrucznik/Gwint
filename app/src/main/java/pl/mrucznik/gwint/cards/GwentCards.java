package pl.mrucznik.gwint.cards;

import java.util.Random;
import java.util.Stack;

/**
 * Singleton class, contains gwent cards
 */
public final class GwentCards {
    private static GwentCards instance = null;
    private static GwentCard cards[];

    private GwentCards()
    {
        int i = 0;
        cards = new GwentCard[84];
        cards[i] = new GwentCard(i++, "Pusta karta", 0, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Emhyr van Emreis - Najeźdźca Północy", 0, AttackRow.King, false, CardBehaviour.EmhyrNajezdzca);
        cards[i] = new GwentCard(i++, "Emhyr var Emreis - Biały Płomień Tańczący Na Kurhanach Wrogów", 0, AttackRow.King, false, CardBehaviour.EmhyrPlomien);
        cards[i] = new GwentCard(i++, "Emhyr var Emreis - Pan Południa", 0, AttackRow.King, false, CardBehaviour.EmhyrPan);
        cards[i] = new GwentCard(i++, "Emhyr var Emreis - Jeż z Erlenwaldu", 0, AttackRow.King, false, CardBehaviour.Deszcz);
        cards[i] = new GwentCard(i++, "Emhyr var Emreis - Cesarz Nilfgaardu", 0, AttackRow.King, false, CardBehaviour.EmhyrCesarz);
        cards[i] = new GwentCard(i++, "Foltest - Dowódca Północy", 0, AttackRow.King, false, CardBehaviour.CzysteNiebo);
        cards[i] = new GwentCard(i++, "Foltest - Żelazny Władca", 0, AttackRow.King, false, CardBehaviour.FoltestWladca);
        cards[i] = new GwentCard(i++, "Foltest - Król Temerii", 0, AttackRow.King, false, CardBehaviour.Mgla);
        cards[i] = new GwentCard(i++, "Foltest - Zdobywca", 0, AttackRow.King, false, CardBehaviour.FoltestZdobywca);
        cards[i] = new GwentCard(i++, "Foltest - Syn Medella", 0, AttackRow.King, false, CardBehaviour.FlotestSyn);
        cards[i] = new GwentCard(i++, "Menno Coehoorn", 10, AttackRow.CloseCombat, true, CardBehaviour.Medyk);
        cards[i] = new GwentCard(i++, "Letho z Gulety", 10, AttackRow.CloseCombat, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Tibor Eggebracht", 10, AttackRow.None, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Morvran Voorhis", 10, AttackRow.Siege, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Wielki Ognisty Skorpion", 10, AttackRow.Siege, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Nilfgaardzki Łucznik", 10, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Stefan Skellen", 9, AttackRow.CloseCombat, false, CardBehaviour.Szpieg);
        cards[i] = new GwentCard(i++, "Shilard Fitz-Oesterlen", 7, AttackRow.CloseCombat, false, CardBehaviour.Szpieg);
        cards[i] = new GwentCard(i++, "Assier var Anahid", 6, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Cahir Mawr Dyffryn aep Ceallach", 6, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Saper", 6, AttackRow.Siege, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Fringilla Vigo", 6, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Renuald aep Matsen", 5, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Zerrikański Ognisty Skorpion", 5, AttackRow.Siege, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Młody Emisariusz", 5, AttackRow.CloseCombat, false, CardBehaviour.Wiez);
        cards[i] = new GwentCard(i++, "Vanhemar", 4, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Rainfarn", 4, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Cynthia", 4, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Vattier de Rideaux", 4, AttackRow.CloseCombat, false, CardBehaviour.Szpieg);
        cards[i] = new GwentCard(i++, "Zdezelowana Mangonela", 3, AttackRow.Siege, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Brygada Impera", 3, AttackRow.CloseCombat, false, CardBehaviour.Wiez);
        cards[i] = new GwentCard(i++, "Puttkammer", 3, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Morteisen", 3, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Albrich", 2, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Sweers", 2, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Vreemde", 2, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Kawaleria Nauzicaa", 2, AttackRow.CloseCombat, false, CardBehaviour.Wiez);
        cards[i] = new GwentCard(i++, "Wsparcie Łuczników", 1, AttackRow.None, false, CardBehaviour.Medyk);
        cards[i] = new GwentCard(i++, "Wsparcie Oblężnicze", 0, AttackRow.Siege, false, CardBehaviour.Medyk);
        cards[i] = new GwentCard(i++, "Jan Natalis", 10, AttackRow.CloseCombat, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Vernon Roche", 10, AttackRow.CloseCombat, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Esterad Thyssen", 10, AttackRow.CloseCombat, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Filippa Eilhart", 10, AttackRow.None, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Katapulta", 8, AttackRow.Siege, false, CardBehaviour.Wiez);
        cards[i] = new GwentCard(i++, "Balista", 6, AttackRow.Siege, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Trebusz", 6, AttackRow.Siege, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Detmold", 6, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Wieża oblężnicza", 6, AttackRow.Siege, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Ves", 5, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Zygfryd z Denesle", 5, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Keira Metz", 5, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Sheala de Tancarville", 5, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Książę Stennis", 5, AttackRow.CloseCombat, false, CardBehaviour.Szpieg);
        cards[i] = new GwentCard(i++, "Rębacze z Crinfrid", 5, AttackRow.None, false, CardBehaviour.Wiez);
        cards[i] = new GwentCard(i++, "Medyczka Burej Chorągwi", 5, AttackRow.Siege, false, CardBehaviour.Medyk);
        cards[i] = new GwentCard(i++, "Sabrina Glevissig", 4, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Sheldon Skaggs", 4, AttackRow.None, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Sigismund Dijkstra", 4, AttackRow.CloseCombat, false, CardBehaviour.Szpieg);
        cards[i] = new GwentCard(i++, "Komandos Niebieskich Pasów", 4, AttackRow.CloseCombat, false, CardBehaviour.Wiez);
        cards[i] = new GwentCard(i++, "Yarpen Zigrin", 2, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Talar", 1, AttackRow.Siege, false, CardBehaviour.Szpieg);
        cards[i] = new GwentCard(i++, "Redański Piechur", 1, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Biedna Pierdolona Piechota", 1, AttackRow.CloseCombat, false, CardBehaviour.Wiez);
        cards[i] = new GwentCard(i++, "Mistrz Oblężeń z Keadwen", 1, AttackRow.Siege, false, CardBehaviour.WysokieMorale);
        cards[i] = new GwentCard(i++, "Geralt z Rivii", 15, AttackRow.CloseCombat, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Cirilla Fiona Elen Riannon", 15, AttackRow.CloseCombat, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Yennefer z Vengerbergu", 7, AttackRow.None, true, CardBehaviour.Medyk);
        cards[i] = new GwentCard(i++, "Triss Merigold", 7, AttackRow.CloseCombat, true, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Avallac'h", 0, AttackRow.CloseCombat, true, CardBehaviour.Szpieg);
        cards[i] = new GwentCard(i++, "Bydlęce Siły Zbrojne", 8, AttackRow.All, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Villentretenmerth", 7, AttackRow.CloseCombat, false, CardBehaviour.PozogaSmoka);
        cards[i] = new GwentCard(i++, "Vesemir", 6, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Emiel Regis Rohellec Terzieff", 5, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Zoltan Chivay", 5, AttackRow.CloseCombat, false, CardBehaviour.None);
        cards[i] = new GwentCard(i++, "Jaskier", 2, AttackRow.CloseCombat, false, CardBehaviour.RogJaskra);
        cards[i] = new GwentCard(i++, "Manekin do ćwiczeń", 0, AttackRow.All, false, CardBehaviour.Manekin);
        cards[i] = new GwentCard(i++, "Róg dowódcy", 0, AttackRow.All, false, CardBehaviour.RogDowodcy);
        cards[i] = new GwentCard(i++, "Pożoga", 0, AttackRow.All, false, CardBehaviour.Pozoga);
        cards[i] = new GwentCard(i++, "Trzaskający mróz", 0, AttackRow.CloseCombat, false, CardBehaviour.Mroz);
        cards[i] = new GwentCard(i++, "Gęsta mgła", 0, AttackRow.None, false, CardBehaviour.Mgla);
        cards[i] = new GwentCard(i++, "Ulewny deszcz", 0, AttackRow.Siege, false, CardBehaviour.Deszcz);
        cards[i] = new GwentCard(i++, "Czyste niebo", 0, AttackRow.All, false, CardBehaviour.CzysteNiebo);
        cards[i] = new GwentCard(i, "Krowa", 0, AttackRow.LongRange, false, CardBehaviour.Krowa);
    }

    private static void checkInstance()
    {
        if(instance == null) {
            instance = new GwentCards();
        }
    }

    public static GwentCards getInstance() {
        if(instance == null) {
            instance = new GwentCards();
        }
        return instance;
    }

    public static GwentCard getCard(int id)
    {
        checkInstance();
        return cards[id];
    }

    public static Stack<GwentCard> generateCards(int number)
    {
        checkInstance();

        Stack<GwentCard> gwentCards = new Stack<>();
        Random randomGenerator  = new Random();
        for(int i=0; i<number; i++)
        {
            gwentCards.push(new GwentCard(cards[randomGenerator.nextInt(cards.length)]));
        }

        return gwentCards;
    }

    public static GwentCard generateRandomCard() {
        checkInstance();

        Random randomGenerator  = new Random();
        return new GwentCard(cards[randomGenerator.nextInt(cards.length)]);
    }
}
