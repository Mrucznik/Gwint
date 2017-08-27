package pl.mrucznik.gwint.cards;

import java.util.Locale;

/**
 * Created by Mrucznik on 17.04.2017.
 */

public class GwentCard {
    private short id;
    private char[] name;
    private byte strength;
    private byte attackRow;
    private boolean golden;
    private byte cardBehaviour;

    public GwentCard(int id, String name, int strength, int attackRow, boolean golden, int cardBehaviour) {
        this.id = (short)id;
        this.name = name.toCharArray();
        this.strength = (byte)strength;
        this.attackRow = (byte)attackRow;
        this.golden = golden;
        this.cardBehaviour = (byte) cardBehaviour;
    }

    public GwentCard(int id, String name, int strength, AttackRow attackRow, boolean golden, CardBehaviour cardBehaviour) {
        this.id = (short)id;
        this.name = name.toCharArray();
        this.strength = (byte)strength;
        this.attackRow = (byte)attackRow.ordinal();
        this.golden = golden;
        this.cardBehaviour = (byte) cardBehaviour.ordinal();
    }

    public GwentCard(String text)
    {
        String[] tokens = text.split("#");
        id = Short.parseShort(tokens[0]);
        name = tokens[1].toCharArray();
        strength = Byte.parseByte(tokens[2]);
        attackRow = Byte.parseByte(tokens[3]);
        golden = Boolean.parseBoolean(tokens[4]);
        cardBehaviour = Byte.parseByte(tokens[5]);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d#%s#%d#%d#%b#%d", id, new String(name), strength, attackRow, golden, cardBehaviour);
    }

    public String toHumanString() {
        return String.format(Locale.getDefault(),
                "ID: %d, Name: %s, Strength: %d, Attack Row: %s, Golden: %b, Card Behaviour: %s",
                id, new String(name), strength, AttackRow.values()[attackRow].name(), golden, CardBehaviour.values()[cardBehaviour].name()
        );
    }

    //W razie, jakby potrzebne było większe upakowanie danych - zamiast zapisywać karty na
    /*public GwentCard(byte[] bytes)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            GwentCard o = (GwentCard) in.readObject();
            this.id = o.id;
            this.name = o.name;
            this.strength = o.strength;
            this.attackRow = o.attackRow;
            this.golden = o.golden;
            this.cardBehaviour = o.cardBehaviour;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    public byte[] getBytes()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return bytes;
    }*/
}
