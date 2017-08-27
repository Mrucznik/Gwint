package pl.mrucznik.gwint;

/**
 * Created by Mrucznik on 27.08.2017.
 */

public class Game {
    private Player[] player;

    public Game() {
        player = new Player[2];
        for (int i=0; i<player.length; ++i) {
            player[i] = new Player();
        }


    }
}
