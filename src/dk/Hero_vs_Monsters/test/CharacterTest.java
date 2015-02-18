package dk.Hero_vs_Monsters.test;

import dk.Hero_vs_Monsters.main.Character;
import dk.Hero_vs_Monsters.main.Skill;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class CharacterTest {

    @Test
    public void testGetName() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        assertEquals("Must be the same name as provided to the constructor.", "UnitTestCharacterName", character.getName());

    }

    @Test
    public void testSetLevel() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLevel(1);

        Field privateField = Character.class.getDeclaredField("level");

        privateField.setAccessible(true);

        int level = (Integer) privateField.get(character);

        assertEquals("Must be the same as provided to the setter.", 1, level);
    }

    @Test
    public void testGetLevel() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLevel(1);

        assertEquals("Must be the same as provided to the setter.", 1, character.getLevel());

    }

    @Test
    public void testSetHealth() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLevel(1);

        character.setHealth(50);

        Field privateField = Character.class.getDeclaredField("health");

        privateField.setAccessible(true);

        int health = (Integer) privateField.get(character);

        assertEquals("Must be the same as provided to the setter.", 50, health);

    }

    @Test
    public void testSetHealthByMoreThanMaxHealth() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLevel(1);

        character.setHealth(200);

        Field privateField = Character.class.getDeclaredField("health");

        privateField.setAccessible(true);

        int health = (Integer) privateField.get(character);

        assertEquals("Must be the same as the max health.", 100, health);

    }

    @Test
    public void testGetHealth() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLevel(1);

        assertEquals("Must be the same as the max health for level 1.", 100, character.getHealth());

    }

    @Test
    public void testGetMaxHealth() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLevel(1);

        assertEquals("Must be equal to (level * 100).", 100, character.getMaxHealth());

    }

    @Test
    public void testGetDamage() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLevel(1);

        assertEquals("Must be the same as the level number.", 1, character.getDamage());

    }

    @Test
    public void testSetLocation() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLocation(new Point(1, 1));

        Field privateField = Character.class.getDeclaredField("location");

        privateField.setAccessible(true);

        Point location = (Point) privateField.get(character);

        assertEquals("Must be the same as provided to the setter.", new Point(1, 1), location);

    }

    @Test
    public void testGetLocation() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLocation(new Point(1,1));

        assertEquals("Must be the same as provided to the setter.", new Point(1, 1), character.getLocation());

    }

    @Test
    public void testGetPreviousLocation() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setLocation(new Point(1, 1));

        character.setLocation(new Point(0, 0));

        assertEquals("Must be the same as it's previous value.", new Point(1, 1), character.getPreviousLocation());

    }

    @Test
    public void testSetTexture() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setTexture(new String[] {"TEST"});

        Field privateField = Character.class.getDeclaredField("texture");

        privateField.setAccessible(true);

        String[] texture = (String[]) privateField.get(character);

        assertEquals("Must be the same as provided to the setter.", "TEST", texture[0]);

    }

    @Test
    public void testGetTexture() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setTexture(new String[] {"TEST"});

        assertEquals("Must be the same as provided to the setter.", "TEST", character.getTexture()[0]);

    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testSetSkillArrayByIndexOutOfBounds() throws Exception {

        Character character = new Character("UnitTestCharacterName", 0);

        character.setSkillArray(new Skill("UnitTestSkillName", 1, 2), 0);

    }

    @Test
    public void testSetSkillArrayByName() throws Exception {

        Character character = new Character("UnitTestCharacterName", 1);

        character.setSkillArray(new Skill("UnitTestSkillName", 1, 2), 0);

        Field privateField = Character.class.getDeclaredField("skillArray");

        privateField.setAccessible(true);

        Skill[] skillArray = (Skill[]) privateField.get(character);

        assertEquals("Must be the same name as provided to the constructor.", "UnitTestSkillName", skillArray[0].getName());

    }

    @Test
    public void testSetSkillArrayByMinDamage() throws Exception {

        Character character = new Character("UnitTestCharacterName", 1);

        character.setSkillArray(new Skill("UnitTestSkillName", 1, 2), 0);

        Field privateField = Character.class.getDeclaredField("skillArray");

        privateField.setAccessible(true);

        Skill[] skillArray = (Skill[]) privateField.get(character);

        assertEquals("Must be the same value as provided to the constructor.", 1, skillArray[0].getMinDamage());

    }

    @Test
    public void testSetSkillArrayByMaxDamage() throws Exception {

        Character character = new Character("UnitTestCharacterName", 1);

        character.setSkillArray(new Skill("UnitTestSkillName", 1, 2), 0);

        Field privateField = Character.class.getDeclaredField("skillArray");

        privateField.setAccessible(true);

        Skill[] skillArray = (Skill[]) privateField.get(character);

        assertEquals("Must be the same value as provided to the constructor.", 2, skillArray[0].getMaxDamage());

    }

    @Test
    public void testGetSkillArrayByName() throws Exception {

        Character character = new Character("UnitTestCharacterName", 1);

        character.setSkillArray(new Skill("UnitTestSkillName", 1, 2), 0);

        assertEquals("Must be the same name as provided to the constructor.", "UnitTestSkillName", character.getSkillArray()[0].getName());

    }

    @Test
    public void testGetSkillArrayByMinDamage() throws Exception {

        Character character = new Character("UnitTestCharacterName", 1);

        character.setSkillArray(new Skill("UnitTestSkillName", 1, 2), 0);

        assertEquals("Must be the same value as provided to the constructor.", 1, character.getSkillArray()[0].getMinDamage());

    }

    @Test
    public void testGetSkillArrayByMaxDamage() throws Exception {

        Character character = new Character("UnitTestCharacterName", 1);

        character.setSkillArray(new Skill("UnitTestSkillName", 1, 2), 0);

        assertEquals("Must be the same value as provided to the constructor.", 2, character.getSkillArray()[0].getMaxDamage());

    }

}