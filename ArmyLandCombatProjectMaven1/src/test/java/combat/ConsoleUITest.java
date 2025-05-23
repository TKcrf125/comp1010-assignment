package combat;

import combat.ui.ConsoleUI;
import combat.domain.Troop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ConsoleUI printing.
 */
// NOTE: AI-generated—please reword header and test messages.
class ConsoleUITest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private ConsoleUI ui;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ui = new ConsoleUI();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void showMainMenuPrintsOptions() {
        ui.showMainMenu();
        String text = outContent.toString();
        assertTrue(text.contains("=== Army Land Combat ==="));
        assertTrue(text.contains("1. Play"));
        assertTrue(text.contains("0. Exit"));
    }

    @Test
    void displayTutorialListsSteps() {
        ui.showTutorialMenu(List.of("Step1", "Step2"));
        String text = outContent.toString();
        assertTrue(text.contains("Step1"));
        assertTrue(text.contains("Step2"));
    }

    @Test
    void displayScoresShowsCorrect() {
        ui.displayScores(List.of());
        assertTrue(outContent.toString().contains("No scores recorded."));
        outContent.reset();

        ui.displayScores(List.of(42, 7));
        String text2 = outContent.toString();
        assertTrue(text2.contains("Score: 42"));
        assertTrue(text2.contains("Score: 7"));
    }

    @Test
    void showBattleStatusAndWinner() {
        Troop p = new Troop("X", 10, 1, 1);
        Troop e = new Troop("Y", 8, 2, 1);
        ui.showBattleStatus(p, e);
        assertTrue(outContent.toString().contains("You:"), "Should display battle status");
        outContent.reset();

        ui.showWinner("VictoryTest");
        assertTrue(outContent.toString().contains("VictoryTest"), "Should display winner message");
    }
}

