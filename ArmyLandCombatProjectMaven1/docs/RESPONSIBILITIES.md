<Tom Kilburn> (60%)•
 Role: lead game designer  
 Tasks: Define core gameplay mechanics

. Establish turn-based sequence (attack/defend choices each round)
. Specify win/loss conditions (all enemy units defeated vs. player squad wiped out)
. Design units & stats
. Choose unit types (Infantry, Armored Platoon, Artillery, etc.)
. Set health, attackPower, defencePower for each
. Balance strengths/weaknesses so no one unit dominates
. Craft the tutorial flow
. Write the step-by-step “Tutorial” menu content
. Decide what tips/hints to show and in what order
. Lay out the UI
. write menu structure and prompts
. write the scoring system
. Choose formula for “score” (e.g. sum of remaining health + stats)
. Decide how and when to record scores to scores.txt
. Create the recursive history feature
. Decide what events to record in the TroopNode chain (e.g. each action taken)
. Determine how deep/value of history for post-game analysis
. design and implement files format
. Define the plain-text format of scores.txt (one number per line)
. Make the UML class diagram
. Map game concepts (engine, UI, domain) to classes & relationships
. Choose packages and package names
. Develop method-analysis write-up
. creating the game loop 
. Work with the Test Designer to ensure those scenarios are covered

 Signed: <Tom Kilburn>

<Asmi Jambhale> (40%)• Role: Editor and Test Designer 
Tasks: 
• Refactor and reorganise code into packages (engine, ui, domain, util)
• Standardise indentation (4-spaces) and brace style across all .java files
• Rename variables and methods for clarity 
• Extract helper methods to improve delegation 
• Add and update Javadoc on every public class and method
• Ensure all imports are correct 
• Verify Maven pom.xml is configured properly 
• Merge all branches and resolve conflicts, commit final code structure
Domain classes 
Engine features (GameEngine.buildTroopHistory(), file I/O, win/lose paths)
• Write and review test cases for normal and out of pocket scenarios (empty lists, invalid input loops, all-dead teams)
• Implement test classes: EnemyTeamTest, TroopNodeTest, InputUtilTest, ConsoleUITest, GameEngineTest
• Configure Maven Surefire plugin to run tests automatically with mvn test
• Ensure tests pass offline
• Measure basic coverage and add any missing critical paths
• Document in README the existence of unit tests and how to run them


Signed: <Member Name 2>
