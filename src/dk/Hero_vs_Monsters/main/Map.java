package dk.Hero_vs_Monsters.main;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Map.java - Used for representing the map as a string of characters.
 * @author Simon Jon Pedersen
 * @author Kristoffer Broch Møller
 * @version 1.0 05/02-2015.
 */
public class Map {

    /** Symbols/Characters representing the textures that are to be used in the map file. */
    private final char wallSymbol = 'W', floorSymbol = ' ', heroSymbol = 'H', monsterSymbol = 'M';

    /** Three pieces that make up the texture representing a wall. */
    public final String[] wallTexture = { "#####", "#####", "#####" };

    /** Three pieces that make up the texture representing the floor. */
    public final String[] floorTexture = { "     ", "     ", "     " };

    /** Three pieces that make up the texture representing a hero. */
    public final String[] heroTexture = { "  o/ ", " /|  ", " / \\ " };

    /** Three pieces that make up the texture representing a monster. */
    public final String[] monsterTexture = { "|O-O|", ">-X-<", "_/ \\_" };

    /** Three pieces that make up the texture representing a fight. */
    public final String[] fightTexture = { "\\-|-/", "FIGHT", "/-|-\\" };

    /** This is the directory from where the map files is to be found. */
    private String mapDirectory;
    
    private String mapFileName;

    /** This is a representation of the map textures and there location */
    private String[][][] map;

    /**
     * Constructor.
     * As default, mapDirectory will be set to be equal to a subfolder called maps in the user working directory.
     */
    public Map() {

        mapDirectory = System.getProperty("user.dir") + "\\maps\\";

    }

    /**
     * Constructor.
     * @param mapDirectory - This is where the map files is to be located.
     */
    public Map(String mapDirectory) {

        this.mapDirectory = mapDirectory;

    }

    /** Return string that gives the maps directory. */
    public String getMapDirectory() {

        return mapDirectory;

    }
    
    public String getMapFileName() {
        
        return mapFileName;
        
    }

    /**
     * Fill the multidimensional map array with textures according to the specified file name.
     * @param mapFileName - This must be a file's name with its extension included.
     */
    public void setMap(String mapFileName) {

        this.mapFileName = mapFileName;
        
        ArrayList<String> mapFromFile = getLinesFromFile(mapDirectory + "\\" + mapFileName);

        if (!(mapFromFile.size() > 0))
            return;

        map = new String[mapFromFile.get(0).length()][mapFromFile.size()][wallTexture.length];

        for (int y = 0; y < mapFromFile.size(); y++) {

            for (int x = 0; x < mapFromFile.get(y).toCharArray().length; x++) {

                if (mapFromFile.get(y).toCharArray()[x] == wallSymbol)
                    map[x][y] = wallTexture;
                else if (mapFromFile.get(y).toCharArray()[x] == floorSymbol)
                    map[x][y] = floorTexture;
                else if (mapFromFile.get(y).toCharArray()[x] == heroSymbol)
                    map[x][y] = heroTexture;
                else if (mapFromFile.get(y).toCharArray()[x] == monsterSymbol)
                    map[x][y] = monsterTexture;

            }

        }

    }

    /** Return string that gives the list of maps in the maps directory. */
    public String getMaps() {

        String fileList = "No maps found!";

        File[] files = getMapFiles();

        if (files.length > 0)
            fileList = "";

        for (int i = 0; i < files.length; i++)
            fileList += "  " + String.valueOf(i + 1) + " - " + files[i].getName() + "\n";

        return fileList + "  ";

    }

    /** Return string that represents the map in the form of characters. */
    public String getMap() {

        String stringMap = "  ";

        for (int y = 0; y < map[0].length; y++) {

            for (int i = 0; i < map[0][y].length; i++) {

                for (int x = 0; x < map.length; x++) {

                    stringMap += map[x][y][i];

                }

                stringMap += "\n  ";

            }

        }

        return stringMap;

    }

    /**
     * Return string that gives the file name at a given index.
     * @param index - There must be an existing file at this index, otherwise an exception is thrown.
     */
    public String getMapFileName(int index) {

        File[] files = getMapFiles();

        if (index - 1 > files.length -1)
            throw new ArrayIndexOutOfBoundsException("Amount of files: " + files.length + ". Index: " + index + " does not exist.");

        return files[index - 1].getName();

    }

    /** Return a file array of map files that are in the maps directory. */
    public File[] getMapFiles() {

        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {

                if (name.endsWith(".map"))
                    return true;
                else
                    return false;
            }
        };

        return new File(mapDirectory).listFiles(filenameFilter);

    }

    /**
     * Return all the occurrences of the specified texture.
     * @param texture - This is what kind of texture that are to be located.
     */
    public ArrayList<Point> getTextureLocations(String[] texture) {

        ArrayList<Point> points = new ArrayList<Point>();

        for (int y = 0; y < map[0].length; y++)
            for (int x = 0; x < map.length; x++)
                if (map[x][y] == texture)
                    points.add(new Point(x, y));

        return points;

    }

    /** Sets the given texture at the given location on the map */
    public void setTextureLocation(String[] texture, Point point) {

        map[point.x][point.y] = texture;

    }

    /**
     * Move a texture from a given point to another point on the map.
     * This will only work if no wall is in the way or fight is at the fromPoint.
     * A result in the form of string will also be returned.
     * @param fromPoint - This is from where the texture should be taken.
     * @param toPoint - This is to where the texture should be moved to.
     *
     */
    public String moveTextureLocation(Point fromPoint, Point toPoint) {

        String result = "Failure: Wall";

        if (map[toPoint.x][toPoint.y] == floorTexture) {

            if (map[fromPoint.x][fromPoint.y] == fightTexture)
                result = "Failure: Fight";
            else {

            map[toPoint.x][toPoint.y] = map[fromPoint.x][fromPoint.y];

            map[fromPoint.x][fromPoint.y] = floorTexture;

            result = "Success: Floor";

            }

        }
        else if (map[fromPoint.x][fromPoint.y] == heroTexture && map[toPoint.x][toPoint.y] == monsterTexture) {

            map[toPoint.x][toPoint.y] = fightTexture;

            map[fromPoint.x][fromPoint.y] = floorTexture;

            result = "Success: Monster";
        }
        else if (map[fromPoint.x][fromPoint.y] == monsterTexture && map[toPoint.x][toPoint.y] == heroTexture) {

            map[toPoint.x][toPoint.y] = fightTexture;

            map[fromPoint.x][fromPoint.y] = floorTexture;

            result = "Success: Hero";

        }

        return result;

    }

    /**
     * Return the lines from a specified file's path.
     * @param filePath - This must be a file's path and not a directories path.
     */
    private ArrayList<String> getLinesFromFile(String filePath) {

        File file = new File(filePath);

        ArrayList<String> arrayListOfLines = new ArrayList<String>();

        if (!file.exists() || !file.canRead()) {

            System.out.println("Can't read " + file);

            return arrayListOfLines;

        }

        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;

            while ((line = bufferedReader.readLine()) != null)
                arrayListOfLines.add(line);

        } catch ( IOException e ) {

            System.out.println("Error reading the file!");

        }

        return arrayListOfLines;

    }

}
