package dk.Hero_vs_Monsters.main;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Woodje on 18-02-2015.
 */
public class GameDatabase {

    Connection databaseConnection;
    Statement databaseStatement;
    boolean heroExists = false,
            mapExists = false;
    
    private String ConnectToDB() {
        
        try {
            
            Class.forName("org.sqlite.JDBC");
            
            databaseConnection = DriverManager.getConnection("jdbc:sqlite:GameDatabase.db");
            
        }
        catch (Exception e) {
            
            return e.getClass().getName() + ": " + e.getMessage();
            
        }
        
        return "Connection established";
        
    }

    public String CreateTables() {
        
        try {
            
            ConnectToDB();

            databaseStatement = databaseConnection.createStatement();

            String sql = "CREATE TABLE heros(name TEXT, level INT, health INT, locationX INT, locationY INT, previousLocationX INT, previousLocationY INT, skills INT, experience INT);";

            databaseStatement.executeUpdate(sql);

            sql = "CREATE TABLE herotextures(heroname TEXT, toppiece TEXT, middlepiece TEXT, bottumpiece TEXT);";

            databaseStatement.executeUpdate(sql);

            sql = "CREATE TABLE heroskills(heroname TEXT, name TEXT, mindamage INT, maxdamage INT);";

            databaseStatement.executeUpdate(sql);


            sql = "CREATE TABLE mappaths(heroname TEXT, mapdirectory TEXT, mapfilename TEXT);";

            databaseStatement.executeUpdate(sql);
            
            databaseStatement.close();
            
            databaseConnection.close();
            
        }
        catch (Exception e) {

            return e.getClass().getName() + ": " + e.getMessage();
            
        }

        return "Table created";
        
    }

    public Map getMap(String heroName) {

        Map map = null;
        
        try {

            ConnectToDB();

            databaseStatement = databaseConnection.createStatement();

            String sql = "SELECT * FROM mappaths WHERE heroname = '" + heroName + "';";

            ResultSet queryResult = databaseStatement.executeQuery(sql);

            while (queryResult.next()) {

                map = new Map(queryResult.getString("mapdirectory"));
                
                map.setMap(queryResult.getString("mapfilename"));
                
                mapExists = true;
                
            }

            queryResult.close();

            databaseStatement.close();

            databaseConnection.close();

        }
        catch (Exception e) {

            map = null;

        }

        return map;

    }

    public String setMap(String mapDirectory, String mapFileName, String heroName) {

        try {

            ConnectToDB();

            databaseStatement = databaseConnection.createStatement();

            String sql;

            if (mapExists) {

                sql =   "UPDATE mappaths SET mapdirectory = '" + mapDirectory +
                        "',' mapfilename = " + mapFileName +
                        "' WHERE heroname = '" + heroName + "';";

            }
            else {

                sql =   "INSERT INTO mappaths(mapdirectory, mapfilename, heroname) VALUES('" + mapDirectory + "','" + mapFileName + "','" + heroName + "'); ";
                        
            }

            databaseStatement.executeUpdate(sql);

            databaseStatement.close();

            databaseConnection.close();

        }
        catch (Exception e) {

            return e.getClass().getName() + ": " + e.getMessage();

        }

        return "Map saved";

    }




    public Hero getHero(String name) {

        Hero hero = null;
        
        try {

            ConnectToDB();

            databaseStatement = databaseConnection.createStatement();

            String sql = "SELECT * FROM heros WHERE name = '" + name + "';";

            ResultSet queryResult = databaseStatement.executeQuery(sql);
            
            while (queryResult.next()) {

                hero = new Hero(queryResult.getString("name"), queryResult.getInt("skills"));
                
                hero.setLevel(queryResult.getInt("level"));
                
                hero.setHealth(queryResult.getInt("health"));
                
                hero.setLocation(new Point(queryResult.getInt("previousLocationX"), queryResult.getInt("previousLocationY")));

                hero.setLocation(new Point(queryResult.getInt("locationX"), queryResult.getInt("locationY")));
                
                hero.setExperience(queryResult.getInt("experience"));

                heroExists = true;
                
            }

            if (heroExists) {

                queryResult.close();
                
                sql = "SELECT * FROM herotextures WHERE heroname = '" + name + "';";

                queryResult = databaseStatement.executeQuery(sql);

                while (queryResult.next())
                    hero.setTexture(new String[] {  queryResult.getString("toppiece"),
                                                    queryResult.getString("middlepiece"),
                                                    queryResult.getString("bottumpiece")});

                queryResult.close();

                sql = "SELECT * FROM heroskills WHERE heroname = '" + name + "';";

                queryResult = databaseStatement.executeQuery(sql);

                for (int i = 0; i < hero.getSkillArray().length; i++) {

                    queryResult.next();
                    
                    hero.setSkillArray(new Skill(queryResult.getString("name"), queryResult.getInt("mindamage"), queryResult.getInt("maxdamage")), i);
                    
                }

            }
            
            queryResult.close();
            
            databaseStatement.close();
            
            databaseConnection.close();

        }
        catch (Exception e) {
            
            hero = null;

        }

        return hero;

    }
    
    public String setHero(Hero hero) {
        
        try {
            
            ConnectToDB();

            databaseStatement = databaseConnection.createStatement();

            String sql;
            
            if (heroExists) {

                sql =   "UPDATE heros SET level = " + hero.getLevel() +
                        ", health = " + hero.getHealth() +
                        ", locationX = " + hero.getLocation().x +
                        ", locationY = " + hero.getLocation().y +
                        ", previousLocationX = " + hero.getPreviousLocation().x +
                        ", previousLocationY = " + hero.getPreviousLocation().y +
                        ", skills = " + hero.getSkillArray().length +
                        ", experience = " + hero.getExperience() +
                        " WHERE name = '" + hero.getName() + "';";
                
                sql +=  " UPDATE herotextures SET toppiece = '" + hero.getTexture()[0] +
                        "', middlepiece = '" + hero.getTexture()[1] +
                        "', bottumpiece = '" + hero.getTexture()[2] +
                        "' WHERE heroname = '" + hero.getName() + "';";

                for (int i = 0; i < hero.getSkillArray().length; i++)
                    sql +=  " UPDATE heroskills SET" + 
                            " mindamage = " + hero.getSkillArray()[i].getMinDamage() +
                            ", maxdamage = " + hero.getSkillArray()[i].getMaxDamage() +
                            " WHERE heroname = '" + hero.getName() + "' AND name = '" + hero.getSkillArray()[i].getName() + "';";
                
            }
            else {
                
                sql =   "INSERT INTO heros(name, level, health, locationX, locationY, previousLocationX, previousLocationY, skills, experience) VALUES('" +
                        hero.getName() + "', " +
                        hero.getLevel() + ", " +
                        hero.getHealth() + ", " +
                        hero.getLocation().x + ", " +
                        hero.getLocation().y + ", " +
                        hero.getPreviousLocation().x + ", " +
                        hero.getPreviousLocation().y + ", " +
                        hero.getSkillArray().length + ", " +
                        hero.getExperience() + ");";
                
                sql +=   " INSERT INTO herotextures(heroname, toppiece, middlepiece, bottumpiece) VALUES('" +
                        hero.getName() + "','" +
                        hero.getTexture()[0] + "','" +
                        hero.getTexture()[1] + "','" +
                        hero.getTexture()[2] + "');";
                
                for (int i = 0; i < hero.getSkillArray().length; i++) {
                    sql +=  "INSERT INTO heroskills(heroname, name, mindamage, maxdamage) VALUES('" +
                            hero.getName() + "','" +
                            hero.getSkillArray()[i].getName() + "'," +
                            hero.getSkillArray()[i].getMinDamage() + "," +
                            hero.getSkillArray()[i].getMaxDamage() + ");";
                    
                }
                
            }

            databaseStatement.executeUpdate(sql);

            databaseStatement.close();
            
            databaseConnection.close();
            
        }
        catch (Exception e) {

            return e.getClass().getName() + ": " + e.getMessage();
            
        }

        return "Hero saved to the database";
        
    }
    
}
