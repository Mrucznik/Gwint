package pl.mrucznik.gwint.cards;

import java.util.Locale;

public class GwentCard {
    private short id;
    private char[] name;
    private int baseStrength;
    private int strength;
    private AttackRow attackRow;
    private boolean golden;
    private CardBehaviour cardBehaviour;

    public GwentCard(int id, String name, int baseStrength, AttackRow attackRow, boolean golden, CardBehaviour cardBehaviour) {
        this.id = (short)id;
        this.name = name.toCharArray();
        this.baseStrength = baseStrength;
        this.strength = baseStrength;
        this.attackRow = attackRow;
        this.golden = golden;
        this.cardBehaviour = cardBehaviour;
    }


    public GwentCard(String text)
    {
        String[] tokens = text.split("#");
        id = Short.parseShort(tokens[0]);
        name = tokens[1].toCharArray();
        baseStrength = Byte.parseByte(tokens[2]);
        attackRow = AttackRow.values()[Byte.parseByte(tokens[3])];
        golden = Boolean.parseBoolean(tokens[4]);
        cardBehaviour = CardBehaviour.values()[Byte.parseByte(tokens[5])];
    }

    public GwentCard(GwentCard card) {
        this.id = card.id;
        this.name = card.name.clone();
        this.baseStrength = card.baseStrength;
        this.attackRow = card.attackRow;
        this.golden = card.golden;
        this.cardBehaviour = card.cardBehaviour;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d#%s#%d#%s#%b#%s", id, new String(name), baseStrength, attackRow.name(), golden, cardBehaviour.name());
    }

    public String toHumanString() {
        return String.format(Locale.getDefault(),
                "ID: %d\nName: %s\nStrength: %d\nAttack Row: %s\nGolden: %b\nCard Behaviour: %s",
                id, new String(name), baseStrength, attackRow.name(), golden, cardBehaviour.name()
        );
    }

    public void resetStrength()
    {
        strength = baseStrength;
    }

    // ---- Setters -----
    public void setStrength(int strength)
    {
        this.strength = strength;
    }

    // ---- Getters -----
    public String getName() {
        return new String(name);
    }
    public int getStrength()
    {
        return strength;
    }
    public AttackRow getAttackRow() {
        return attackRow;
    }
    public boolean isGolden() {
        return golden;
    }
    public CardBehaviour getCardBehaviour() {
        return cardBehaviour;
    }
}
