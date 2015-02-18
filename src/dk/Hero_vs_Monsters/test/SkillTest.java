package dk.Hero_vs_Monsters.test;

import dk.Hero_vs_Monsters.main.Skill;
import org.junit.Test;

import static org.junit.Assert.*;

public class SkillTest {

    @Test
    public void testGetName() throws Exception {

        Skill skill = new Skill("UnitTestSkillName", 1, 2);

        assertEquals("Must be the same name as provided to the constructor.", "UnitTestSkillName", skill.getName());

    }

    @Test
    public void testGetMinDamage() throws Exception {

        Skill skill = new Skill("UnitTestSkillName", 1, 2);

        assertEquals("Must be the same value as provided to the constructor.", 1, skill.getMinDamage());

    }

    @Test
    public void testGetMaxDamage() throws Exception {

        Skill skill = new Skill("UnitTestSkillName", 1, 2);

        assertEquals("Must be the same value as provided to the constructor.", 2, skill.getMaxDamage());

    }
}