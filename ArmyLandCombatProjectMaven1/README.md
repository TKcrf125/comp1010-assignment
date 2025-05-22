# Army Land Combat

## 1. Problem Statement
Simulate a turn-based land combat game between player-controlled and opposing military units.

## 2. Project Structure
- `combat/ArmyLandCombat.java` – entry point  
- `combat/engine/GameEngine.java` – core logic, menu, I/O  
- `combat/ui/ConsoleUI.java` – all user interactions  
- `combat/util/InputUtil.java` – input helpers  
- `combat/domain/…` – domain classes (`Troop`, `CombatTeam`, `EnemyTeam`, `TroopNode`)  
- `src/test/java/combat/…` – JUnit 5 tests

## 3. How to Run
1. Clone the repo  
2. Build:  
   ```bash
   mvn clean compile
   ```  
3. Test:  
   ```bash
   mvn test
   ```  
4. Run:  
   ```bash
   mvn exec:java
   ```

## 4. Task Allocation
| Team Member | Tasks                                   |
|-------------|-----------------------------------------|
| Alice       | Core game loop & I/O                   |
| Bob         | Domain models & recursive history      |
| Carol       | UI menus & input util                  |
| Dave        | README, testing, file I/O, refactoring |

## 5. UML Class Diagram
(Refer to `docs/ArmyLandCombat_UML.pdf` for full diagram of class relationships)

## 6. Method Analysis
### `GameEngine.playGame()`
- **What I did:** Orchestrated combat turns, recursive history, file I/O.  
- **Alternative:** Event-driven listener pattern for turns—more decoupled but higher complexity.  
- **Time/Space:** O(n) per turn, uses O(t) space for history.

### `Troop.attack(Troop other)`
- **What I did:** Simple damage = `attackPower – other.defencePower`.  
- **Alternative:** Probability‐based hit/miss (adds realism).  
- **Efficiency:** O(1), minimal memory overhead.
