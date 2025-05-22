package combat;

import combat.engine.GameEngine;
import combat.domain.Troop;
import combat.domain.TroopNode;
import combat.ui.ConsoleUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GameEngine functionality.
 */
// NOTE: AI-generated—please rephrase header and messages.
class GameEngineTest {

    private static final String SCORE_FILE = "scores.txt";
    private GameEngine engine;

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(Path.of(SCORE_FILE));
        engine = new GameEngine(new ConsoleUI());
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(Path.of(SCORE_FILE));
    }

    @Test
    void buildTroopHistoryViaReflection() throws Exception {
        Method m = GameEngine.class.getDeclaredMethod("buildTroopHistory", List.class);
        m.setAccessible(true);
        Troop t1 = new Troop("A", 5, 2, 1);
        Troop t2 = new Troop("B", 6, 3, 1);
        TroopNode head = (TroopNode) m.invoke(engine, List.of(t1, t2));
        assertEquals(t2, head.getData(), "First in chain should be t2");
        assertEquals(t1, head.getNext().getData(), "Second should be t1");
    }

    @Test
    void loadScoresEmptyWhenNoFile() throws Exception {
        Field f = GameEngine.class.getDeclaredField("pastScores");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> scores = (List<Integer>) f.get(engine);
        assertTrue(scores.isEmpty(), "pastScores should be empty without file");
    }

    @Test
    void saveAndLoadScores() throws Exception {
        Field f = GameEngine.class.getDeclaredField("pastScores");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> scores = (List<Integer>) f.get(engine);
        scores.addAll(List.of(10, 20, 30));

        Method save = GameEngine.class.getDeclaredMethod("saveScores");
        save.setAccessible(true);
        save.invoke(engine);

        GameEngine fresh = new GameEngine(new ConsoleUI());
        Field f2 = GameEngine.class.getDeclaredField("pastScores");
        f2.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> loaded = (List<Integer>) f2.get(fresh);

        assertEquals(List.of(10, 20, 30), loaded, "Loaded scores must match saved ones");
    }
}
