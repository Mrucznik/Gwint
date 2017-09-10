package pl.mrucznik.gwint.model;

public class Player {
    private String name;
    private boolean active = true;
    private int wins = 0;

    public Player(String name)
    {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void jamOut() {
        active = false;
    }

    public void setActive() {
        active = true;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        ++wins;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
