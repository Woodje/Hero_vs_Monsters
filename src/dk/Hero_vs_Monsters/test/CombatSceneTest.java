package dk.Hero_vs_Monsters.test;

import dk.Hero_vs_Monsters.main.*;
import dk.Hero_vs_Monsters.main.Character;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class CombatSceneTest {

    @Test
    public void testGetCombatSceneByZeroSkills() throws Exception {

        Character   character1 = new Character("Character1", 0),
                    character2 = new Character("Character2", 0);

        CombatScene combatScene = new CombatScene(character1, character2);

        String characters = combatScene.getCombatScene();

        assertEquals("Amount of characters should be 312 with zero skills added.", 312, characters.length());

    }

    @Test
    public void testGetCombatSceneByOneSkill() throws Exception {

        Character   character1 = new Character("Character1", 1),
                    character2 = new Character("Character2", 0);

        character1.setSkillArray(new Skill("Skill1", 1, 10), 0);

        CombatScene combatScene = new CombatScene(character1, character2);

        String characters = combatScene.getCombatScene();

        assertEquals("Amount of characters should be 390 with only one skills added.", 390, characters.length());

    }

    @Test
    public void testGetCombatSceneByTwoSkills() throws Exception {

        Character   character1 = new Character("Character1", 2),
                    character2 = new Character("Character2", 0);

        character1.setSkillArray(new Skill("Skill1", 1, 10), 0);

        character1.setSkillArray(new Skill("Skill2", 1, 10), 1);

        CombatScene combatScene = new CombatScene(character1, character2);

        String characters = combatScene.getCombatScene();

        assertEquals("Amount of characters should be 468 with two skills added.", 468, characters.length());

    }

    @Test
    public void addEmptySpace() throws Exception {

        Character   character1 = new Character("Character1", 0),
                    character2 = new Character("Character2", 0);

        CombatScene combatScene = new CombatScene(character1, character2);

        Method privateMethod = CombatScene.class.getDeclaredMethod("addEmptySpace", int.class);

        privateMethod.setAccessible(true);

        String stringOfEmptySpace = (String) privateMethod.invoke(combatScene, 2);

        assertEquals("Must be equal to the amount of spaces specified.", "  ", stringOfEmptySpace);

    }

    @Test
    public void testAttackWithSkill() throws Exception {

        Character   character1 = new Character("Character1", 1),
                    character2 = new Character("Character2", 0);

        character1.setSkillArray(new Skill("Skill1", 1, 10), 0);

        CombatScene combatScene = new CombatScene(character1, character2);

        String result = combatScene.attackWithSkill("1", true);

        assertNotEquals("Should not return: \"  No skill entered!\" if a valid skill is entered", "  No skill entered!\n", result);

    }

    @Test
    public void testAttackWithSkillByNoInput() throws Exception {

        Character   character1 = new Character("Character1", 1),
                    character2 = new Character("Character2", 1);

        character1.setSkillArray(new Skill("Skill1", 1, 10), 0);

        character2.setSkillArray(new Skill("Skill1", 1, 10), 0);

        CombatScene combatScene = new CombatScene(character1, character2);

        String result = combatScene.attackWithSkill("", true);

        assertEquals("Should return: \"  No skill entered!\" if no valid skill is entered.", "  No skill entered!\n", result);

    }

    @Test
    public void calculateCharacterDamage() throws Exception {

        Character   character1 = new Character("Character1", 1),
                    character2 = new Character("Character2", 0);

        character1.setSkillArray(new Skill("Skill1", 1, 10), 0);

        character1.setLevel(1);

        CombatScene combatScene = new CombatScene(character1, character2);

        Method privateMethod = CombatScene.class.getDeclaredMethod("calculateCharacterDamage", Character.class, int.class);

        privateMethod.setAccessible(true);

        int damage = (Integer) privateMethod.invoke(combatScene, character1, 0);

        assertTrue("Must be greater than zero.", damage > 0);

    }

    @Test
    public void testGetWinner() throws Exception {

        Character   character1 = new Character("Character1", 1),
                    character2 = new Character("Character2", 0);

        character1.setSkillArray(new Skill("Skill1", 100, 1000), 0);

        character1.setLevel(1);

        CombatScene combatScene = new CombatScene(character1, character2);

        combatScene.attackWithSkill("1", true);

        assertSame("Must be referencing the same object. (character1 should be the winner)", character1, combatScene.getWinner());

    }

    @Test
    public void testGetLoser() throws Exception {

        Character   character1 = new Character("Character1", 1),
                    character2 = new Character("Character2", 0);

        character1.setSkillArray(new Skill("Skill1", 100, 1000), 0);

        character1.setLevel(1);

        CombatScene combatScene = new CombatScene(character1, character2);

        String result = combatScene.attackWithSkill("1", true);

        assertSame("Must be referencing the same object. (character2 should be the loser)", character2, combatScene.getLoser());

    }

    @Test
     public void testGetTextures() throws Exception {

        Character   character1 = new Character("Character1", 0),
                    character2 = new Character("Character2", 0);

        CombatScene combatScene = new CombatScene(character1, character2);

        assertEquals("Amount of characters should be equal to 1560.", 1560, combatScene.getTextures(1).length());

    }

}