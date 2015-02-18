package dk.Hero_vs_Monsters.test;

import dk.Hero_vs_Monsters.main.UserInterface;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import dk.Hero_vs_Monsters.main.UserInterface.menu;

import static org.junit.Assert.*;

public class UserInterfaceTest {

    @Test
    public void testDrawToScreenByLines() throws Exception {

        UserInterface userInterface = new UserInterface();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(byteArrayOutputStream));

        userInterface.drawToScreen("TEST");

        String[] output = new String(byteArrayOutputStream.toByteArray()).split(System.getProperty("line.separator"));

        assertEquals("Must be the same string as provided to the \"drawToScreen-method\".", "TEST", output[output.length - 1]);

    }

    @Test
    public void testLoadMenu() throws Exception {

        UserInterface userInterface = new UserInterface();

        System.setOut(new PrintStream(new ByteArrayOutputStream()));

        InputStream inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));

        System.setIn(inputStream);

        String input = userInterface.loadMenu(menu.FIRST, "");

        assertEquals("Must be the same value as the users input.", "1", input);

    }

    @Test
    public void testGetInput() throws Exception {

        UserInterface userInterface = new UserInterface();

        System.setOut(new PrintStream(new ByteArrayOutputStream()));

        InputStream inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));

        System.setIn(inputStream);

        String input = userInterface.getInput("TEST");

        assertEquals("Must be the same value as the users input.", "1", input);

    }
}