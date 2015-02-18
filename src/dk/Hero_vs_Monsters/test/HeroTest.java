package dk.Hero_vs_Monsters.test;

import dk.Hero_vs_Monsters.main.*;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class HeroTest {

    @Test
    public void testSetExperience() throws Exception {

        Hero hero = new Hero("UnitTestHeroName", 0);

        hero.setExperience(50);

        Field privateField = Hero.class.getDeclaredField("experience");

        privateField.setAccessible(true);

        int experience = (Integer) privateField.get(hero);

        assertEquals("Must be the same as provided to the setter.", 50, experience);

    }

    @Test
    public void testGetExperience() throws Exception {

        Hero hero = new Hero("UnitTestHeroName", 0);

        hero.setExperience(50);

        assertEquals("Must be the same as provided to the setter.", 50, hero.getExperience());

    }

    @Test
    public void testGetMaxExperience() throws Exception {

        Hero hero = new Hero("UnitTestHeroName", 0);

        hero.setLevel(1);

        assertEquals("Must be equal to (level * 200).", 200, hero.getMaxExperience());

    }

    @Test
    public void testSetLevel() throws Exception {

        Hero hero = new Hero("UnitTestHeroName", 0);

        hero.setLevel(1);

        Field privateField = dk.Hero_vs_Monsters.main.Character.class.getDeclaredField("level");

        privateField.setAccessible(true);

        int level = (Integer) privateField.get(hero);

        assertEquals("Must be the same as provided to the setter.", 1, level);

    }

}