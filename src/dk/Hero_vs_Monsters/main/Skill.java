package dk.Hero_vs_Monsters.main;

/**
 * Skill.java - Represents a simple skill.
 * @Author Simon Jon Pedersen.
 * @Author Kristoffer Broch Møller.
 * @Version 1.0 05/02-2015
 */
public class Skill {

    /* The name of the skill. */
    private String name;

    /* The minimum damage the skill can deal. */
    private int minDamage;

    /* The maximum damage the skill can deal. */
    private int maxDamage;

    /**
     * Constructor.
     * @param name - The name of the skill.
     * @param minDamage - The minimum damage for the skill.
     * @param maxDamage - The maximum damage for the skill.
     */
    public Skill(String name, int minDamage, int maxDamage) {

        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;

    }

    /* Gets the name of the skill */
    public String getName() {

        return name;

    }

    /* Gets the minimum damage of the skill */
    public int getMinDamage() {

        return minDamage;

    }

    /* Gets the maximum damage of the skill */
    public int getMaxDamage() {

        return maxDamage;

    }

}
