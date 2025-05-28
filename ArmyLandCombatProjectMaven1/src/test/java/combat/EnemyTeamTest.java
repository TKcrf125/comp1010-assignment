package combat;

import combat.domain.EnemyTeam;
import combat.domain.Troop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for EnemyTeam behavior.
*/
class EnemyTeamTest {

    private EnemyTeam enemies;

    @BeforeEach
    void setUp() {
        enemies = new EnemyTeam();
    }

    @Test
    void allUnitsAliveInitially() {
        assertFalse(enemies.isDefeated(), "Team should not be defeated on init");
    }

    @Test
    void defeatAllUnits() {
        try {
            Field field = EnemyTeam.class.getDeclaredField("troops");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<Troop> list = (List<Troop>) field.get(enemies);
            for (Troop t : list) {
                while (t.isAlive()) {
                    t.takeDamage(1);
                }
            }
            assertTrue(enemies.isDefeated(), "Team should be defeated after all troops die");
        } catch (Exception e) {
            fail("Reflection failure: " + e.getMessage());
        }
    }

    @Test
    void nextActiveSkipsDead() {
        try {
            Field field = EnemyTeam.class.getDeclaredField("troops");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<Troop> list = (List<Troop>) field.get(enemies);
            Troop first = list.get(0);
            while (first.isAlive()) {
                first.takeDamage(1);
            }
            Troop next = enemies.nextActive();
            assertNotEquals(first, next, "nextActive should skip dead troop");
        } catch (Exception e) {
            fail("Reflection failure: " + e.getMessage());
        }
    }
}

