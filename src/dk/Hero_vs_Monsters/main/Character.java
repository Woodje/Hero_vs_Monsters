package dk.Hero_vs_Monsters.main;

import java.awt.Point;

/**
 * Character.java - Represents a single character.
 * @Author Simon Jon Pedersen.
 * @Author Kristoffer Broch Møller.
 * @Version 1.0 05/02-2015
 */
public class Character {

    /** The name of the character. */
    private String name;

    /** The characters level. */
    private int level;

    /** The characters amount of health. */
    private int health;

    /** The characters max amount of health. */
    private int maxHealth;

    /** The amount of damage that the character can deal. */
    private int damage;

    /** The characters location on the map. */
    private Point location;

    /** The characters previous location on the map */
    private Point previousLocation;

    /** The texture of the character */
    private String[] texture;

    /** The characters skills. */
    private Skill[] skillArray;

    /**
     * Constructor.
     * @param name - The name of the character.
     * @param skills - Decides how many skills the character has.
     */
    public Character(String name, int skills) {

        this.name = name;
        skillArray = new Skill[skills];

    }

    /** Gets the name of the character. */
    public String getName() {

        return name;

    }

    /**
     * Sets the level of the character.
     * Sets max health and damage corresponding to the level.
     * @param level - The level.
     */
    public void setLevel(int level) {

        this.level = level;
        health = (maxHealth = 100 * level);
        damage = this.level;

    }

    /** Gets the level of the character. */
    public int getLevel() {

        return level;

    }

    /**
     * Sets the amount of health on the character.
     * @param health - Amount of health.
     */
    public void setHealth(int health) {

        if (health > maxHealth)
            this.health = maxHealth;
        else {

            if (health < 0)
                this.health = 0;
            else
                this.health = health;

        }

    }

    /** Gets the health of the character. */
    public int getHealth() {

        return health;

    }

    /** Gets the maximum health amount of the character */
    public int getMaxHealth() {

        return maxHealth;

    }

    /** Gets the amount of damage that the character can deal. */
    public int getDamage() {

        return damage;

    }

    /**
     * Sets the characters location on the map.
     * @param location - location on the map.
     */
    public void setLocation(Point location) {

        previousLocation = this.location;
        this.location = location;

    }

    /** Gets the location of the character on the map.*/
    public Point getLocation() {

        return location;

    }

    /** Gets the previous location on the map */
    public Point getPreviousLocation() {

        return previousLocation;

    }

    /**
     * Sets the texture of the character.
     * @param texture - The texture
     */
    public void setTexture(String[] texture) {

        this.texture = texture;

    }

    /** Gets the texture of the character */
    public String[] getTexture() {

        return texture;

    }

    /**
     * If the index is higher then the length of the array, throw and exception.
     * Else, add the skill to the array.
     * @param skill - The skill.
     * @param index - The index in the array.
     */
    public void setSkillArray(Skill skill, int index) {

        if(index > skillArray.length - 1) {

            throw new ArrayIndexOutOfBoundsException("Only " + skillArray.length + " skills allowed. Index: " + index + " does not exist.");
        }

        skillArray[index] = skill;

    }

    /** Gets all the skills in the skillArray. */
    public Skill[] getSkillArray() {

        return skillArray;

    }

}