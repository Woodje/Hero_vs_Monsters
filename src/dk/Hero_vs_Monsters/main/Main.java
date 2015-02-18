package dk.Hero_vs_Monsters.main;

/**
 * Main.java - Used for starting the actual game.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 05/02-2015.
 */
public class Main {

    /** Create and instantiate a game engine and call it's initialize game method. */
    public static void main(String[] args) {

        GameEngine gameEngine = new GameEngine();

        gameEngine.initializeGame();

    }
}