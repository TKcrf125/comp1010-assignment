package majorAssignmentTopic1.src.test.java.combat;

import combat.util.InputUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for InputUtil.
 */
class InputUtilTest {

    private final InputStream originalIn = System.in;

    @AfterEach
    void restoreInput() {
        System.setIn(originalIn);
    }

    @Test
    void readIntSkipsInvalid() {
        String simulated = "foo\n-1\n5\n";
        System.setIn(new ByteArrayInputStream(simulated.getBytes()));
        int result = InputUtil.readInt("Prompt: ", 1, 10);
        assertEquals(5, result, "Should return 5 after skipping invalid entries");
    }

    @Test
    void waitForEnterDoesNotThrow() {
        System.setIn(new ByteArrayInputStream("\n".getBytes()));
        assertDoesNotThrow(InputUtil::waitForEnter, "waitForEnter should not throw");
    }
}
