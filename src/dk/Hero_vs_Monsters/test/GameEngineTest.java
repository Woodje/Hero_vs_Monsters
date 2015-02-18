package dk.Hero_vs_Monsters.test;

import dk.Hero_vs_Monsters.main.*;
import dk.Hero_vs_Monsters.main.Character;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameEngineTest {

    @Test
    public void testInitializeGame() throws Exception {

        GameEngine gameEngine = new GameEngine();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(byteArrayOutputStream));

        InputStream inputStream = new ByteArrayInputStream("3".getBytes("UTF-8"));

        System.setIn(inputStream);

        gameEngine.initializeGame();

        String[] output = new String(byteArrayOutputStream.toByteArray()).split(System.getProperty("line.separator"));

        assertEquals("Should return: \"Thank you for playing...\" if option 3 is entered.", "Thank you for playing...", output[output.length - 1]);

    }

    @Test
    public void testGetStats() throws Exception {

        GameEngine gameEngine = new GameEngine();

        Field privateField = GameEngine.class.getDeclaredField("characters");

        privateField.setAccessible(true);

        Hero hero = new Hero("UnitTestHeroName", 0);

        ((ArrayList<Character>) privateField.get(gameEngine)).add(hero);

        assertNotEquals("Should not return an empty string if a character is added", "", gameEngine.getStats());

    }
}