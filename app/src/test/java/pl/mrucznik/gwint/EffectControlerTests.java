package pl.mrucznik.gwint;
import org.junit.Test;

import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.CardBehaviour;
import pl.mrucznik.gwint.model.cards.GwentCard;
import pl.mrucznik.gwint.model.effects.EffectControler;

import static org.junit.Assert.*;

public class EffectControlerTests {
    private EffectControler effectControler;

    //weather cards
    private GwentCard coldCard;
    private GwentCard fogCard;
    private GwentCard rainCard;
    private GwentCard clearSky;

    //horn effect cards
    private GwentCard closeCombatHorn;
    private GwentCard longRangeHorn;
    private GwentCard siegeHorn;
    private GwentCard jaskier;
    private GwentCard jaskier2;

    //morale effect cards
    private GwentCard siegeMorale;
    private GwentCard siegeMorale2;

    //tight bond effect cards
    private GwentCard tightBond[];

    //test cards
    private GwentCard closeCombatCard;
    private GwentCard closeCombatGoldCard;
    private GwentCard longRangeCard;
    private GwentCard longRangeGoldCard;
    private GwentCard siegeCard;
    private GwentCard siegeGoldCard;

    public EffectControlerTests()
    {
        effectControler = new EffectControler();

        closeCombatHorn = new GwentCard(0, "Róg dowódcy", 0, AttackRow.CloseCombat, false, CardBehaviour.RogDowodcy);
        longRangeHorn = new GwentCard(0, "Róg dowódcy", 0, AttackRow.LongRange, false, CardBehaviour.RogDowodcy);
        siegeHorn = new GwentCard(0, "Róg dowódcy", 0, AttackRow.Siege, false, CardBehaviour.RogDowodcy);
        jaskier = new GwentCard(0, "Jaskier", 2, AttackRow.CloseCombat, false, CardBehaviour.RogJaskra);
        jaskier2 = new GwentCard(0, "Jaskier", 2, AttackRow.CloseCombat, false, CardBehaviour.RogJaskra);

        coldCard = new GwentCard(1, "Trzaskający mróz", 0, AttackRow.CloseCombat, false, CardBehaviour.Mroz);
        fogCard = new GwentCard(2, "Gęsta mgła", 0, AttackRow.LongRange, false, CardBehaviour.Mgla);
        rainCard = new GwentCard(3, "Ulewny deszcz", 0, AttackRow.Siege, false, CardBehaviour.Deszcz);
        clearSky = new GwentCard(4, "Czyste niebo", 0, AttackRow.All, false, CardBehaviour.CzysteNiebo);

        siegeMorale = new GwentCard(4, "Mistrz Oblężeń z Keadwen", 1, AttackRow.Siege, false, CardBehaviour.WysokieMorale);
        siegeMorale2 = new GwentCard(4, "Mistrz Oblężeń z Keadwen", 1, AttackRow.Siege, false, CardBehaviour.WysokieMorale);

        tightBond = new GwentCard[3];
        tightBond[0] = new GwentCard(0, "Rębacze z Crinfrid", 5, AttackRow.LongRange, false, CardBehaviour.Wiez);
        tightBond[1] = new GwentCard(1, "Rębacze z Crinfrid", 5, AttackRow.LongRange, false, CardBehaviour.Wiez);
        tightBond[2] = new GwentCard(2, "Rębacze z Crinfrid", 5, AttackRow.LongRange, false, CardBehaviour.Wiez);

        closeCombatCard = new GwentCard(5, "Brygada Impera", 3, AttackRow.CloseCombat, false, CardBehaviour.Wiez);
        closeCombatGoldCard = new GwentCard(6, "Letho z Gulety", 10, AttackRow.CloseCombat, true, CardBehaviour.None);
        longRangeCard = new GwentCard(7, "Nilfgaardzki Łucznik", 10, AttackRow.LongRange, false, CardBehaviour.None);
        longRangeGoldCard = new GwentCard(8, "Yennefer z Vengerbergu", 7, AttackRow.LongRange, true, CardBehaviour.Medyk);
        siegeCard = new GwentCard(9, "Balista", 6, AttackRow.Siege, false, CardBehaviour.None);
        siegeGoldCard = new GwentCard(10, "Morvran Voorhis", 10, AttackRow.Siege, true, CardBehaviour.None);
    }

    @Test
    public void NoEffectsTest() throws Exception
    {
        assertEquals(2, effectControler.getCardStrengthAfterEffectsAffected(jaskier));
        assertEquals(2, effectControler.getCardStrengthAfterEffectsAffected(jaskier2));
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale));
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale2));
        assertEquals(5, effectControler.getCardStrengthAfterEffectsAffected(tightBond[0]));
        assertEquals(5, effectControler.getCardStrengthAfterEffectsAffected(tightBond[1]));
        assertEquals(5, effectControler.getCardStrengthAfterEffectsAffected(tightBond[2]));
        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    //region WeatherEffectTests
    @Test
    public void WeatherEffectTest_ColdEffectsTest() throws Exception
    {
        effectControler.addEffectIfExists(coldCard);
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();

    }

    @Test
    public void WeatherEffectTest_FogEffectsTest() throws Exception
    {
        effectControler.addEffectIfExists(fogCard);
        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    @Test
    public void WeatherEffectTest_RainEffectsTest() throws Exception
    {
        effectControler.addEffectIfExists(rainCard);
        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    @Test
    public void WeatherEffectTest_ClearSkyEffectsTest() throws Exception
    {
        effectControler.addEffectIfExists(coldCard);
        effectControler.addEffectIfExists(fogCard);
        effectControler.addEffectIfExists(rainCard);
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.addEffectIfExists(clearSky);
        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));

        assertEquals(0, effectControler.getAllEffects().size());

        effectControler.clear();
    }

    @Test
    public void WeatherEffectTest_NonFightingCard() throws Exception
    {
        effectControler.addEffectIfExists(coldCard);
        effectControler.addEffectIfExists(fogCard);
        effectControler.addEffectIfExists(rainCard);
        assertEquals(0, effectControler.getCardStrengthAfterEffectsAffected(coldCard));
        assertEquals(0, effectControler.getCardStrengthAfterEffectsAffected(fogCard));
        assertEquals(0, effectControler.getCardStrengthAfterEffectsAffected(rainCard));

        effectControler.clear();
    }
    //endregion

    //region HornEffectTests
    @Test
    public void HornEffectTests_CloseCombatEffectsTests() throws Exception {
        effectControler.addEffectIfExists(closeCombatHorn);

        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    @Test
    public void HornEffectTests_LongRangeEffectsTests() throws Exception {
        effectControler.addEffectIfExists(longRangeHorn);

        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(20, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    @Test
    public void HornEffectTests_SiegeEffectsTests() throws Exception {
        effectControler.addEffectIfExists(siegeHorn);

        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(12, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    @Test
    public void HornEffectTests_AllEffectsTests() throws Exception {
        effectControler.addEffectIfExists(closeCombatHorn);
        effectControler.addEffectIfExists(longRangeHorn);
        effectControler.addEffectIfExists(siegeHorn);

        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(20, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(12, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }
    //endregion

    //region JaskierHornEffectTests
    @Test
    public void JaskierEffectTests_JaskierTests() throws Exception {
        effectControler.addEffectIfExists(jaskier);

        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    @Test
    public void JaskierEffectTests_DoubleJaskierTests() throws Exception {
        effectControler.addEffectIfExists(jaskier);
        effectControler.addEffectIfExists(jaskier2);

        assertEquals(12, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        assertEquals(4, effectControler.getCardStrengthAfterEffectsAffected(jaskier));
        assertEquals(4, effectControler.getCardStrengthAfterEffectsAffected(jaskier2));
        effectControler.clear();
    }
    //endregion

    //region MoraleEffectTests
    @Test
    public void MoraleEffectTests_MoraleEffectsTests() throws Exception {
        effectControler.addEffectIfExists(siegeMorale);

        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(1, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }

    @Test
    public void MoraleEffectTests_DoubleMoraleEffectsTests() throws Exception {
        effectControler.addEffectIfExists(siegeMorale);
        effectControler.addEffectIfExists(siegeMorale2);

        assertEquals(3, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(8, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));
        assertEquals(2, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale));
        assertEquals(2, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale2));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));
        effectControler.clear();
    }
    //endregion

    //region TightBondEffectTests
    @Test
    public void TightBondEffectTests_DoubleTightBondTests() throws Exception {
        effectControler.addEffectIfExists(tightBond[0]);
        effectControler.addEffectIfExists(tightBond[1]);

        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(tightBond[0]));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(tightBond[1]));
        effectControler.clear();
    }

    @Test
    public void TightBondEffectTests_TripleTightBondTests() throws Exception {
        effectControler.addEffectIfExists(tightBond[0]);
        effectControler.addEffectIfExists(tightBond[1]);
        effectControler.addEffectIfExists(tightBond[2]);

        assertEquals(20, effectControler.getCardStrengthAfterEffectsAffected(tightBond[0]));
        assertEquals(20, effectControler.getCardStrengthAfterEffectsAffected(tightBond[1]));
        assertEquals(20, effectControler.getCardStrengthAfterEffectsAffected(tightBond[2]));
        effectControler.clear();
    }
    //endregion

    //region ManyEffectsTests
    @Test
    public void ManyEffectsTests_AllEffectsTest() throws Exception
    {
        effectControler.addEffectIfExists(coldCard);
        effectControler.addEffectIfExists(fogCard);
        effectControler.addEffectIfExists(rainCard);
        effectControler.addEffectIfExists(closeCombatHorn);
        effectControler.addEffectIfExists(longRangeHorn);
        effectControler.addEffectIfExists(siegeHorn);
        effectControler.addEffectIfExists(jaskier);
        effectControler.addEffectIfExists(siegeMorale);
        effectControler.addEffectIfExists(siegeMorale2);
        effectControler.addEffectIfExists(tightBond[0]);
        effectControler.addEffectIfExists(tightBond[1]);
        effectControler.addEffectIfExists(tightBond[2]);

        effectControler.getAllEffects().size();
        assertEquals(11, effectControler.getAllEffects().size());

        assertEquals(2, effectControler.getCardStrengthAfterEffectsAffected(jaskier));
        assertEquals(4, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale));
        assertEquals(4, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale2));
        assertEquals(8, effectControler.getCardStrengthAfterEffectsAffected(tightBond[0]));
        assertEquals(8, effectControler.getCardStrengthAfterEffectsAffected(tightBond[1]));
        assertEquals(8, effectControler.getCardStrengthAfterEffectsAffected(tightBond[2]));
        assertEquals(2, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(2, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));

        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));


        effectControler.addEffectIfExists(clearSky);
        assertEquals(4, effectControler.getCardStrengthAfterEffectsAffected(jaskier));
        assertEquals(4, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale));
        assertEquals(4, effectControler.getCardStrengthAfterEffectsAffected(siegeMorale2));
        assertEquals(40, effectControler.getCardStrengthAfterEffectsAffected(tightBond[0]));
        assertEquals(40, effectControler.getCardStrengthAfterEffectsAffected(tightBond[1]));
        assertEquals(40, effectControler.getCardStrengthAfterEffectsAffected(tightBond[2]));
        assertEquals(6, effectControler.getCardStrengthAfterEffectsAffected(closeCombatCard));
        assertEquals(20, effectControler.getCardStrengthAfterEffectsAffected(longRangeCard));
        assertEquals(16, effectControler.getCardStrengthAfterEffectsAffected(siegeCard));

        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(closeCombatGoldCard));
        assertEquals(7, effectControler.getCardStrengthAfterEffectsAffected(longRangeGoldCard));
        assertEquals(10, effectControler.getCardStrengthAfterEffectsAffected(siegeGoldCard));


        effectControler.clear();
    }
    //endregion

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}
