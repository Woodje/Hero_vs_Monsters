package dk.Hero_vs_Monsters.test;

import dk.Hero_vs_Monsters.main.Map;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MapTest {

    @Test
    public void testGetMapDirectory() throws Exception {

        Map map = new Map("c:\\");

        assertEquals("Must be the same path as provided to the constructor.", "c:\\", map.getMapDirectory());

    }

    @Test
    public void testSetMap() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        Field privateField = Map.class.getDeclaredField("map");

        privateField.setAccessible(true);

        String[][][] mapArray = (String[][][]) privateField.get(map);

        assertNotNull("Should not be null.", mapArray);

    }

    @Test
    public void testGetMapsByMapsFound() throws Exception {

        Map map = new Map();

        assertNotEquals("Should not return: \"No maps found!  \" if a map is indeed found.", "No maps found!  ", map.getMaps());

    }

    @Test
    public void testGetMapsByNoMapsFound() throws Exception {

        Map map = new Map("c:\\");

        assertEquals("Should return: \"No maps found!  \" if no maps is found.", "No maps found!  ", map.getMaps());

    }

    @Test
    public void testGetMap() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        assertNotNull("Should not be null.", map.getMap());

    }

    @Test (expected = NullPointerException.class)
    public void testGetMapWithNoMapSelected() throws Exception {

        Map map = new Map();

        map.getMap();

    }

    @Test
    public void testGetMapFileName() throws Exception {

        Map map = new Map();

        assertEquals("Should be equal to Map1.map.", "Map1.map", map.getMapFileName(1));

    }

    @Test
    public void testGetMapFiles() throws Exception {

        Map map = new Map();

        assertTrue("Should be greater than zero if a valid path is provided.", map.getMapFiles().length > 0);

    }

    @Test
    public void testGetTextureLocations() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        assertTrue("Should be greater than zero if a texture is located.", map.getTextureLocations(map.monsterTexture).size() > 0);

    }

    @Test
    public void testSetTextureLocation() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        map.setTextureLocation(map.monsterTexture, new Point(0, 0));

        Field privateField = Map.class.getDeclaredField("map");

        privateField.setAccessible(true);

        String[][][] mapArray = (String[][][]) privateField.get(map);

        assertSame("Must be referencing the same object.", map.monsterTexture[0], mapArray[0][0][0]);

    }

    @Test
    public void testMoveTextureLocationByHittingWall() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        map.setTextureLocation(map.monsterTexture, new Point(0, 0));

        assertEquals("Should return: \"Failure: Wall\" if a wall is hit.", "Failure: Wall", map.moveTextureLocation(new Point(0, 0), new Point(0, 1)));

    }

    @Test
     public void testMoveTextureLocationByMovingFromFight() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        map.setTextureLocation(map.fightTexture, new Point(0, 0));

        assertEquals("Should return: \"Failure: Fight\" if a fight going on.", "Failure: Fight", map.moveTextureLocation(new Point(0, 0), new Point(1, 1)));

    }

    @Test
     public void testMoveTextureLocationByHittingFloor() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        map.setTextureLocation(map.monsterTexture, new Point(0, 0));

        assertEquals("Should return: \"Success: Floor\" if a floor is hit.", "Success: Floor", map.moveTextureLocation(new Point(0, 0), new Point(1, 1)));

    }

    @Test
    public void testMoveTextureLocationByHeroHittingMonster() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        map.setTextureLocation(map.heroTexture, new Point(0, 0));

        map.setTextureLocation(map.monsterTexture, new Point(0, 1));

        assertEquals("Should return: \"Success: Monster\" if a hero hits a monster.", "Success: Monster", map.moveTextureLocation(new Point(0, 0), new Point(0, 1)));

    }

    @Test
    public void testMoveTextureLocationByMonsterHittingHero() throws Exception {

        Map map = new Map();

        map.setMap("Map1.map");

        map.setTextureLocation(map.monsterTexture, new Point(0, 0));

        map.setTextureLocation(map.heroTexture, new Point(0, 1));

        assertEquals("Should return: \"Success: Hero\" if a monster hits a hero.", "Success: Hero", map.moveTextureLocation(new Point(0, 0), new Point(0, 1)));

    }

    @Test
    public void testGetLinesFromFile() throws Exception {

        Map map = new Map();

        Method privateMethod = Map.class.getDeclaredMethod("getLinesFromFile", String.class);

        privateMethod.setAccessible(true);

        ArrayList<String> arrayListOfLines = (ArrayList<String>) privateMethod.invoke(map, map.getMapDirectory() + "\\Map1.map");

        assertTrue("Should be greater than zero if a valid file is provided.", arrayListOfLines.size() > 0);

    }

}