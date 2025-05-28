package combat;

import combat.engine.GameEngine;
import combat.domain.Ability;
import combat.domain.Troop;
import combat.domain.TroopNode;
import combat.ui.ConsoleUI;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GameEngine core logic.
 */
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
    void buildTroopHistoryCreatesLinkedList() throws Exception {
        Method m = GameEngine.class.getDeclaredMethod("buildTroopHistory", List.class);
        m.setAccessible(true);
        Troop t1 = new Troop("A", 5, 2, 1, 0, 1.0, new Ability("None","",0));
        Troop t2 = new Troop("B", 6, 3, 1, 0, 1.0, new Ability("None","",0));
        TroopNode head = (TroopNode) m.invoke(engine, List.of(t1, t2));
        assertEquals(t2, head.getData());
        assertEquals(t1, head.getNext().getData());
    }

    @Test
    void loadScoresStartsEmptyWhenNoFile() throws Exception {
        Field f = GameEngine.class.getDeclaredField("pastScores");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> scores = (List<Integer>) f.get(engine);
        assertTrue(scores.isEmpty());
    }

    @Test
    void saveAndLoadScoresPersistData() throws Exception {
        Field f = GameEngine.class.getDeclaredField("pastScores");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> scores = (List<Integer>) f.get(engine);
        scores.addAll(List.of(7, 8, 9));

        Method save = GameEngine.class.getDeclaredMethod("saveScores");
        save.setAccessible(true);
        save.invoke(engine);

        GameEngine fresh = new GameEngine(new ConsoleUI());
        Field f2 = GameEngine.class.getDeclaredField("pastScores");
        f2.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> loaded = (List<Integer>) f2.get(fresh);

        assertEquals(List.of(7, 8, 9), loaded);
    }
}
