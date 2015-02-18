package dk.Hero_vs_Monsters.main;

import java.util.Scanner;

/**
 * UserInterface.java - Used for displaying the game and retrieving inputs.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 05/02-2015.
 */
public class UserInterface {

    /** Enumerations used for representing the different kind of menu's that is needed. */
    public enum menu { FIRST, SHOWMAP, SELECTMAP, MOVEMENT, COMBAT };

    /**
     * Prints the provided string to the screen.
     * 55 empty lines will be printed to the screen prior to the given output string.
     * @param outputString - This is the string that will be printed to the screen.
     */
    public void drawToScreen(String outputString) {

        for (int i = 0; i < 55; i++)
            System.out.println();

        System.out.println(outputString);

    }

    /**
     * Print the specified menu type to the screen and return the inputs given for this menu.
     * @param menuType - This is what type of menu to use.
     * @param additionalString - This is a string that can be used for creating a little more custom menu.
     */
    public String loadMenu(menu menuType, String additionalString) {

        String input = "";

        switch (menuType) {

            case FIRST: input = getInput("  1 - Start game\n  2 - Show maps\n  3 - Exit game\n\n  ");
                break;

            case SHOWMAP:   input = getInput("  0 - Exit menu\n" + additionalString);
                break;

            case SELECTMAP: input = getInput(additionalString);
                break;

            case MOVEMENT:  input = getInput(additionalString + "[w]UP [s]DOWN [a]LEFT [d]RIGHT\n  ");
                break;

            case COMBAT:  input = getInput("  What skill to use? " + additionalString);
                break;

        }

        return input;

    }

    /**
     * Prompts the user for an input, and returns this input.
     * @param promptString - This is a string that will be printed to the screen.
     */
    public String getInput(String promptString) {

        System.out.print(promptString);

        Scanner input = new Scanner(System.in);

        return input.nextLine();

    }

}
