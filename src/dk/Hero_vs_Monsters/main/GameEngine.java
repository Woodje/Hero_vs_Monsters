package dk.Hero_vs_Monsters.main;

import java.awt.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * GameEngine - Used for controlling the basic logic of the game.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 2.0 19/02-2015.
 */
public class GameEngine {

    /** This is the list used for storing all characters in the game. */
    private ArrayList<Character> characters;

    /** This is used for map operations. */
    private Map map;

    /** This is used for user interfacing. */
    private UserInterface userInterface;
    
    private GameDatabase gameDatabase;

    /**
     * Constructor.
     * Instantiating of: userInterface, map and characters.
     */
    public GameEngine() {

        gameDatabase = new GameDatabase();
        userInterface = new UserInterface();
        map = new Map();
        characters = new ArrayList<Character>();

    }

    /** Represent the user for the first menu, and wait for an input. */
    public void initializeGame() {

        gameDatabase.CreateTables();
        
        userInterface.drawToScreen("  Welcome\n  --------------");

        int input = convertToInteger(userInterface.loadMenu(UserInterface.menu.FIRST, ""));

        switch (input) {

            case 1:  startGame();
                     break;

            case 2:  listMaps(true);
                     break;

            case 3:  exitGame();
                     break;

            default: initializeGame();

        }

    }

    /**
     * Starts up the necessary precautions before starting the games loop.
     * Minimum one hero and one monster is created.
     * The games loop will be started after an input is given.
     */
    private void startGame() {

        createCharacter(true);
        
        createCharacter(false);

        userInterface.getInput("  Press 'ENTER' to start playing");

        gameLoop();

    }

    /**
     *  This is the games loop from where the actual basic logic of the game takes place.
     *  Step 1: A user is prompted for an input for where to go.
     *  Step 2: If no fight has occurred, the move the monsters.
     *  Step 3: If a fight has occurred, then enter a combat scene.
     *  Step 4: If a monster won the combat, the send the hero back to his previous location.
     *  Step 5: If the monster lost, then delete him from the game, reward the hero with experience and update the database.
     *  Step 6: If there are no more monster left in the map, the spawn as many monsters at the hero's level. (Max 5)
     *  Step 7: Start over by prompting for an input for where to go.
     *  The user is prompt for an input for each run through.
     */
    private void gameLoop() {

        while(true) {

            userInterface.drawToScreen(getStats() + map.getMap());

            processUserInput(userInterface.loadMenu(UserInterface.menu.MOVEMENT, "  Where to go?  "));

            userInterface.drawToScreen(map.getMap());

            Character[] charactersFighting = getCharactersFighting();

            if (charactersFighting == null)
                moveMonsters();

            charactersFighting = getCharactersFighting();

            if (charactersFighting != null) {

                CombatScene combatScene = new CombatScene(charactersFighting[0], charactersFighting[1]);

                userInterface.drawToScreen(getStats() + map.getMap());

                userInterface.getInput("  You have entered combat, press 'ENTER' to start the fight!");

                while (combatScene.getWinner() == null) {

                    if (charactersFighting[0].getHealth() == charactersFighting[0].getMaxHealth())
                        userInterface.drawToScreen(combatScene.getCombatScene() + combatScene.getTextures(0));
                    else
                        userInterface.drawToScreen(combatScene.getCombatScene() + combatScene.getTextures(3));

                    String result = combatScene.attackWithSkill(userInterface.loadMenu(UserInterface.menu.COMBAT, ""), true);

                    if (combatScene.getWinner() == null) {

                        result += combatScene.attackWithSkill("1", false);

                        if (combatScene.getWinner() != null) {

                            result += "\n  " + combatScene.getWinner().getName() + " wins the fight!\n";

                            userInterface.drawToScreen(combatScene.getCombatScene() + combatScene.getTextures(2));

                        }
                        else
                            userInterface.drawToScreen(combatScene.getCombatScene() + combatScene.getTextures(3));

                    }
                    else {

                        userInterface.drawToScreen(combatScene.getCombatScene() + combatScene.getTextures(1));

                        result +=   "\n  " + combatScene.getWinner().getName() + " wins the fight!\n" +
                                    "\n  You gained " + (combatScene.getWinner().getHealth() * 5) + " experience!\n";

                    }

                    userInterface.getInput(result + "\n  Press 'ENTER' to continue");

                }

                Character winner = combatScene.getWinner(), loser = combatScene.getLoser();

                if (winner instanceof Hero) {
                    
                    ((Hero) winner).setExperience((((Hero) winner).getExperience() + winner.getHealth() * 5));
                    
                    winner.setTexture(map.heroTexture);

                    winner.setHealth(winner.getMaxHealth());

                    characters.remove(loser);
                    
                    gameDatabase.setHero((Hero) winner);
                    
                    for (Character character : characters)
                        if (!(character instanceof Hero))
                            character.setLevel(winner.getLevel());

                }
                else {

                    winner.setTexture(map.monsterTexture);

                    winner.setHealth(winner.getMaxHealth());

                    loser.setHealth(loser.getMaxHealth());

                    loser.setTexture(map.heroTexture);

                    loser.setLocation(loser.getPreviousLocation());

                    map.setTextureLocation(loser.getTexture(),loser.getLocation());

                }

                map.setTextureLocation(winner.getTexture(), winner.getLocation());

                if (characters.size() == 1)
                    spawnExtraMonsters(winner.getLevel(), winner.getLevel());

            }

        }

    }

    /**
     * Return a Character array containing the two fighting characters.
     * If nothing is found, null is returned.
     * The first character in the returned Character array represents the hero character.
     */
    private Character[] getCharactersFighting() {

        Character[] charactersFighting = new Character[2];

        int x = 0;

        for (int i = 0; i < characters.size(); i++)
            if (characters.get(i).getTexture() == map.fightTexture) {

                charactersFighting[x] = characters.get(i);

                x++;

            }

        if (x < 1)
            return null;
        else {

            if (charactersFighting[0] instanceof Monster) {

                Character monster = charactersFighting[0];

                charactersFighting[0] = charactersFighting[1];

                charactersFighting[1] = monster;

            }

        }

        return charactersFighting;

    }

    /** Return a string representing the stats for each character in the game. */
    public String getStats() {

        String statsIndicator = "";

        for (Character character : characters)
            if (character instanceof Hero) {

                statsIndicator +=   "  (" + character.getName() + ") - " +
                        "Level: " + character.getLevel() + " - " +
                        "Health: " + character.getHealth() + "/" + character.getMaxHealth() + " - " +
                        "XP: " + ((Hero) character).getExperience() + "/" + ((Hero) character).getMaxExperience() + "\n";
            }
            else {

                statsIndicator +=   "  (" + character.getName() + ") - " +
                        "Level: " + character.getLevel() + " - " +
                        "Health: " + character.getHealth() + "/" + character.getMaxHealth() + "\n";
            }

        return statsIndicator;

    }

    /**
     * Do the appropriate hero movement according to the users input.
     * @param input - This should be the the users input.
     */
    private void processUserInput(String input) {

        if (input.toCharArray().length == 1) {

            switch (input.toCharArray()[0]) {

                case 'w':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(0, -1));

                            break;

                case 's':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(0, 1));

                            break;

                case 'a':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(-1, 0));

                            break;

                case 'd':   for (Character hero : characters)
                                if (hero instanceof Hero)
                                    moveCharacter(hero, new Point(1, 0));

                            break;

            }

        }

    }

    /**
     * Move all monsters within the characters list.
     * X and Y movements are randomly chosen from -1 to 1.
     */
    private void moveMonsters() {

        for (Character monster : characters)
            if (monster instanceof Monster) {

                int xMovement = (int) (Math.random() * 3) - 1,
                    yMovement = (int) (Math.random() * 3) - 1;

                moveCharacter(monster, new Point(xMovement, yMovement));

            }

    }

    /** Exits the game and prints a little kind message */
    private void exitGame() {

        userInterface.drawToScreen("Thank you for playing...");

    }

    /**
     * Move the given character with the amount of values from the provided point.
     * This is only done if a success is returned.
     * @param character - This is the character that should be moved.
     * @param point - This is for how much the characters location should be moved.
     */
    private void moveCharacter(Character character, Point point) {

        Point oldLocation = character.getLocation();

        Point newLocation = new Point(point.x + character.getLocation().x, point.y + character.getLocation().y);

        String result = map.moveTextureLocation(oldLocation, newLocation);

        if (result.contains("Success")) {

            character.setLocation(newLocation);

            if (result.contains("Hero") || result.contains("Monster")) {

                for (Character characterCollided : characters)
                    if (character.getLocation().equals(characterCollided.getLocation()))
                        characterCollided.setTexture(map.fightTexture);

            }

        }

    }

    /**
     * Create either a user defined character (hero) or create one or more monsters depending on the map.
     * The user will be asked to reset or continue if the specified hero name already exist in the database.
     * Simple SQL injection avoidance is used for the entered user name.
     * @param userDefined - True if the user should define a hero. False if monsters should be created.
     */
    private void createCharacter(boolean userDefined) {
        
        if (userDefined) {

            userInterface.drawToScreen("  Create hero\n  ---------------");

            Hero hero = new Hero(userInterface.getInput("  Name your hero: ").replaceAll("'", "''"), 3);

            String input = "";
            
            boolean heroExists = false;
            
            if (gameDatabase.getHero(hero.getName()) != null) {

                heroExists = true;
                
                input = userInterface.getInput( "  " + hero.getName() + " already exist!\n" +
                                                "  Press 'Enter' to continue loading last checkpoint...\n" +
                                                "  Type: 'RESET' to reset the user...");
                
            }

            if (!input.contains("RESET") & heroExists) {

                hero = gameDatabase.getHero(hero.getName());

                map = gameDatabase.getMap(hero.getName());
                
                if (Arrays.equals(map.heroTexture, hero.getTexture()))
                    hero.setTexture(map.heroTexture);
                else if (Arrays.equals(map.fightTexture, hero.getTexture()))
                    hero.setTexture(map.fightTexture);

                for (Point point : map.getTextureLocations(map.heroTexture))
                        map.setTextureLocation(map.floorTexture, point);
                
                map.setTextureLocation(hero.getTexture(), hero.getLocation());

                characters.add(hero);
                
            }
            else {

                hero.setLevel(1);

                hero.setSkillArray(new Skill("Basic", 1, 10), 0);
                hero.setSkillArray(new Skill("Medium", 3, 6), 1);
                hero.setSkillArray(new Skill("High", 6, 8), 2);

                hero.setTexture(map.heroTexture);

                listMaps(false);

                gameDatabase.setMap(map.getMapDirectory(), map.getMapFileName(), hero.getName());

                if (map.getTextureLocations(hero.getTexture()).size() == 0) {

                    if (map.getTextureLocations(map.floorTexture).size() == 0) {

                        userInterface.getInput("Error using map, no floor textures detected.\nPress 'ENTER' to start over...");

                        characters.clear();

                        createCharacter(true);

                    } else {

                        hero.setLocation(map.getTextureLocations(map.floorTexture).get(0));

                        hero.setLocation(map.getTextureLocations(map.floorTexture).get(0));

                        characters.add(hero);

                        map.setTextureLocation(hero.getTexture(), hero.getLocation());

                    }

                } else {

                    hero.setLocation(map.getTextureLocations(hero.getTexture()).get(0));

                    hero.setLocation(map.getTextureLocations(hero.getTexture()).get(0));

                    characters.add(hero);

                }

                gameDatabase.setHero(hero);
                
            }

        }
        else {

            Monster monster = new Monster("MONSTER1", 1);
            
            monster.setSkillArray(new Skill("Basic", 1, 10), 0);
            
            monster.setLevel(characters.get(0).getLevel());

            monster.setTexture(map.monsterTexture);

            if (map.getTextureLocations(monster.getTexture()).size() == 0) {

                if (map.getTextureLocations(map.floorTexture).size() == 0){

                    userInterface.getInput("Error using map, no floor textures detected.\nPress 'ENTER' to start over...");

                    characters.clear();

                    createCharacter(false);

                }
                else {

                    monster.setLocation(map.getTextureLocations(map.floorTexture).get(map.getTextureLocations(map.floorTexture).size() - 1));

                    characters.add(monster);

                    map.setTextureLocation(monster.getTexture(), monster.getLocation());

                }

            }
            else {

                for (int i = 0; i < map.getTextureLocations(map.monsterTexture).size(); i++) {

                    monster = new Monster("MONSTER" + String.valueOf(i + 1), 1);
                    
                    monster.setSkillArray(new Skill("Basic", 1, 10), 0);
                    
                    monster.setLevel(characters.get(0).getLevel());

                    monster.setTexture(map.monsterTexture);

                    monster.setLocation(map.getTextureLocations(monster.getTexture()).get(i));

                    characters.add(monster);

                }

            }

        }
        
    }

    /**
     * Spawn the specified amount of monsters of the provided level.
     * A maximum of five monsters will be spawned.
     * @param amount - This is the amount of monsters to create.
     * @param level - This is of what level the monsters should be.
     */
    private void spawnExtraMonsters(int amount, int level) {

        if (amount > 5)
            amount = 5;

        for (int i = 0; i < amount; i++) {

            Character monster = new Monster("MONSTER" + String.valueOf(i + 1), 1);
            
            monster.setSkillArray(new Skill("Basic", 1, 10), 0);
            
            monster.setLevel(level);

            monster.setTexture(map.monsterTexture);

            monster.setLocation(map.getTextureLocations(map.floorTexture).get(map.getTextureLocations(map.floorTexture).size() - 1));

            characters.add(monster);

            map.setTextureLocation(monster.getTexture(), monster.getLocation());

        }

    }

    /**
     * Represent the user for either a menu displaying the maps or a menu for selecting a map.
     * @param showOnly - True if a displaying menu should be shown. False if a selection menu should be shown.
     */
    private void listMaps(boolean showOnly) {

        int input;

        if (showOnly) {

            userInterface.drawToScreen("  Display Maps\n  -------------");

            input = convertToInteger(userInterface.loadMenu(UserInterface.menu.SHOWMAP, map.getMaps()));

        }
        else {

            userInterface.drawToScreen("  Select Map\n  ------------");

            input = convertToInteger(userInterface.loadMenu(UserInterface.menu.SELECTMAP, map.getMaps()));

        }

        switch (input) {

            case 0:  if (showOnly) {

                        initializeGame();

                        break;

                     }
            default: if (input <= map.getMapFiles().length && input >= 0) {

                        map.setMap(map.getMapFileName(input));

                        if (showOnly) {

                            userInterface.drawToScreen("");

                            userInterface.getInput(map.getMap() + "Press 'ENTER' to continue...");

                            listMaps(true);

                        }
                     }
                     else
                        if (showOnly)
                            listMaps(true);
                        else
                            listMaps(false);

        }

    }

    /**
     * Convert a string to an integer if possible, if not possible then a value of -1 is returned instead.
     * @param string - This is the string to be converted.
     */
    private int convertToInteger(String string) {

        int value;

        try {

            value = Integer.parseInt(string);

        } catch(NumberFormatException e) {

            return -1;

        }

        return value;

    }

}
