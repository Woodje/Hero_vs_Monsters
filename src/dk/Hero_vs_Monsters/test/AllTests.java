package dk.Hero_vs_Monsters.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Woodje on 17-02-2015.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({   CharacterTest.class,
                        HeroTest.class,
                        SkillTest.class,
                        UserInterfaceTest.class,
                        MapTest.class,
                        CombatSceneTest.class,
                        GameEngineTest.class })
public class AllTests {

    public static void main(String[] args) {

        ArrayList<Failure> results = new ArrayList<Failure>();

        Class[] testClasses = { CharacterTest.class,
                                HeroTest.class,
                                SkillTest.class,
                                UserInterfaceTest.class,
                                MapTest.class,
                                CombatSceneTest.class,
                                GameEngineTest.class };

        for (int i = 0; i < testClasses.length; i++) {

            Result result = JUnitCore.runClasses(testClasses[i]);

            for (Failure failure : result.getFailures())
                results.add(failure);

        }

        // Print to the correct output after making the tests, some of them changes the standard output.
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        for (Failure failure : results)
            System.out.println(failure.toString());

        if (results.size() == 0)
            System.out.println("All tests passed!");

    }

}
