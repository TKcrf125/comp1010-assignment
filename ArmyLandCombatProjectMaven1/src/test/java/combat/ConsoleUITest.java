package majorAssignmentTopic1.src.test.java.combat;

import combat.ui.ConsoleUI;
import combat.domain.Troop;
import combat.domain.Ability;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ConsoleUI printing methods.
 */
class ConsoleUITest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream buffer;
    private ConsoleUI ui;

    @BeforeEach
    void setUp() {
        buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        ui = new ConsoleUI();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void showBattleOptionsIncludesAbilityWhenReady() {
        ui.showBattleOptions(true);
        String output = buffer.toString();
        assertTrue(output.contains("1. Attack"));
        assertTrue(output.contains("3. Use Special Ability"));
    }

    @Test
    void showBattleOptionsExcludesAbilityWhenNotReady() {
        ui.showBattleOptions(false);
        String output = buffer.toString();
        assertFalse(output.contains("Use Special Ability"));
    }

    @Test
    void displayScoresShowsNoneOrValues() {
        ui.displayScores(List.of());
        assertTrue(buffer.toString().contains("No scores recorded."));
        buffer.reset();
        ui.displayScores(List.of(10, 20));
        String out = buffer.toString();
        assertTrue(out.contains("Score: 10"));
        assertTrue(out.contains("Score: 20"));
    }

    @Test
    void showBattleStatusPrintsStatuses() {
        Troop p = new Troop("P", 10, 1, 1, 0.0, 1.0,
            new Ability("None","",0));
        Troop e = new Troop("E", 8, 1, 1, 0.0, 1.0,
            new Ability("None","",0));
        ui.showBattleStatus(p, e);
        String out = buffer.toString();
        assertTrue(out.contains("You:"));
        assertTrue(out.contains("Enemy:"));
    }
}

