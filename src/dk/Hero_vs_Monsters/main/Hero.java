package dk.Hero_vs_Monsters.main;

import java.lang.*;

/**
 * Hero.java - Represents a Hero.
 * @Author Simon Jon Pedersen.
 * @Author Kristoffer Broch Møller.
 * @Version 1.0 05/02-2015
 * @see Character
 */
public class Hero extends Character {

    /** Represents the experience. */
    private int experience;

    /** Represents the max amount of experience. */
    private int maxExperience;

    /**
     * Constructor.
     * @param name - The name of the hero.
     * @param skills - Decides how many skills the hero has.
     */
    public Hero (String name, int skills) {

        super(name, skills);

    }

    /**
     * Sets the experience, and if the the experience is bigger/equal then/to max experience,
     * level up.
     * @param experience - The amount of experience.
     */
    public void setExperience(int experience) {

        if (experience >= maxExperience) {

            this.experience = experience - maxExperience;
            setLevel(getLevel() + 1);

        }
        else
            this.experience = experience;

    }

    /** Gets the experience */
    public int getExperience() {

      return experience;

    }

    /** Gets the max amount of experience. */
    public int getMaxExperience() {

        return maxExperience;

    }

    /**
     * Calls the setLevel function in Character and overrides the function to set the max amount of experience.
     * @param level - The level.
     */
    @Override
    public void setLevel(int level) {

        super.setLevel(level);
        maxExperience = 200 * level;

    }

}
