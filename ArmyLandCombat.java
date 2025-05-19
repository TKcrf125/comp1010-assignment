public class ArmyLandCombat {

    public static void main(String[] args) {
        showMenu();
    }

    /**
     * Main menu loop
     */
    public static void showMenu() {
        while (true) {
            // ASCII art
            System.out.println("             \\ | //");
            System.out.println("              \\|//");
            System.out.println("           ---  *  ---");
            System.out.println("              //|\\");
            System.out.println("             // | \\");
            System.out.println();

            // Main options
            System.out.println("=== Army Land Combat ===");
            System.out.println("1. Play");
            System.out.println("2. Tutorial");
            System.out.println("3. Past Scores");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    playGame();
                    break;
                case 2:
                    showTutorial();
                    break;
                case 3:
                    showPastScores();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice; please enter 0–3.");
            }
        }
    }

    /** Reads an entire line and parses it as integer */
    private static int readInt() {
        StringBuilder sb = new StringBuilder();
        try {
            int c;
            while ((c = System.in.read()) != -1 && c != '\n' && c != '\r') {
                sb.append((char) c);
            }
        } catch (Exception e) {
            // ignore
        }
        try { return Integer.parseInt(sb.toString().trim()); }
        catch (Exception e) { return -1; }
    }

    /** Wait for Enter key */
    private static void waitForEnter() {
        try {
            int c;
            while ((c = System.in.read()) != -1 && c != '\n') { }
        } catch (Exception e) {}
    }

    /** Troop selection submenu */
    public static void playGame() {
        while (true) {
            System.out.println();
            System.out.println("--- Troop Selection ---");

            // list corps
            CorpsType[] corps = CorpsType.values();
            for (int i = 0; i < corps.length; i++) {
                System.out.printf("%d. %s%n", i + 1, corps[i]);
            }
            System.out.println("0. Back");
            System.out.print("Choose a corps: ");

            int cChoice = readInt();
            if (cChoice == 0) return;
            if (cChoice < 1 || cChoice > corps.length) {
                System.out.println("Invalid selection.");
                continue;
            }
            CorpsType selected = corps[cChoice - 1];
            java.util.List<Troop> list = Troops.getTroopsByCorps(selected);
            if (list.isEmpty()) {
                System.out.println("No units in " + selected + ".");
                System.out.println("Press Enter to return..."); waitForEnter();
                continue;
            }

            // list troops
            while (true) {
                System.out.println();
                System.out.println("--- " + selected + " Units ---");
                for (int j = 0; j < list.size(); j++) {
                    Troop t = list.get(j);
                    System.out.printf(
                        "%d. %s (FP:%d AR:%d CM:%d RG:%d CMD:%d CP:%d)%n",
                        j+1, t.getRole(), t.getFirepower(), t.getArmour(),
                        t.getCamouflage(), t.getRange(), t.getCommand(),
                        t.getCommandPoints()
                    );
                }
                System.out.println("0. Back");
                System.out.print("Choose a unit: ");

                int tChoice = readInt();
                if (tChoice == 0) break;
                if (tChoice < 1 || tChoice > list.size()) {
                    System.out.println("Invalid selection.");
                    continue;
                }
                Troop sel = list.get(tChoice-1);
                System.out.println();
                System.out.println("Selected: " + sel.getRole() + " [" + sel.getCorps() + "]");
                System.out.println("Firepower: " + sel.getFirepower());
                System.out.println("Armour:    " + sel.getArmour());
                System.out.println("Camouflage:" + sel.getCamouflage());
                System.out.println("Range:     " + sel.getRange());
                System.out.println("Command:   " + sel.getCommand());
                System.out.println("CmdPoints: " + sel.getCommandPoints());
                System.out.println("Abilities: " + sel.getSpecialAbilities());
                System.out.println();
                System.out.println("Press Enter to return to main menu..."); waitForEnter();
                return;
            }
        }
    }

    /** Tutorial */
    public static void showTutorial() {
        System.out.println();
        System.out.println("[TUTORIAL]");
        System.out.println("Use WASD to move, SPACE to fire.");
        System.out.println("Press Enter to return..."); waitForEnter();
    }

    /** Past scores */
    public static void showPastScores() {
        System.out.println();
        System.out.println("[PAST SCORES]");
        System.out.println("Alice:12340\nBob:8920\nCarol:5750");
        System.out.println("Press Enter to return..."); waitForEnter();
    }
}

// Corps categories
enum CorpsType { INFANTRY, ARTILLERY, ARMOURED, ENGINEERS, MEDICAL, TRANSPORT, AVIATION }

// Soldier roles
enum Role {
    INFANTRY, MARKSMAN, GUNNER, GRENADIER, FIRE_TEAM_LEADER,
    ASLAV, BOXER, M1A2_TANK,
    MORTAR, HOWITZER, MLRS,
    SAPPER, BREACHER, BRIDGER,
    MED_TECH, NURSE, DOCTOR,
    GWAGON, UNIMOG,
    BLACKHAWK, CHINOOK, TIGER
}

// Troop unit
class Troop {
    private CorpsType corps; private Role role;
    private int firepower, armour, command, camouflage, range, commandPoints;
    private java.util.List<String> specialAbilities;
    public Troop(CorpsType corps, Role role, int fp,int ar,int cmd,int cam,int rg,int cp,java.util.List<String> sa) {
        this.corps=corps; this.role=role; firepower=fp; armour=ar;
        command=cmd; camouflage=cam; range=rg; commandPoints=cp; specialAbilities=sa;
    }
    public CorpsType getCorps(){return corps;}
    public Role getRole(){return role;}
    public int getFirepower(){return firepower;}
    public int getArmour(){return armour;}
    public int getCommand(){return command;}
    public int getCamouflage(){return camouflage;}
    public int getRange(){return range;}
    public int getCommandPoints(){return commandPoints;}
    public java.util.List<String> getSpecialAbilities(){return specialAbilities;}
}

// Troops DB
class Troops {
    private static java.util.List<Troop> troops = new java.util.ArrayList<>();
    static {
        // INFANTRY
        troops.add(new Troop(CorpsType.INFANTRY, Role.INFANTRY,50,30,40,60,70,5,java.util.List.of("Quick March")));
        troops.add(new Troop(CorpsType.INFANTRY, Role.MARKSMAN,80,20,30,50,90,4,java.util.List.of("Stealth Shot")));
        troops.add(new Troop(CorpsType.INFANTRY, Role.GUNNER,90,40,30,30,80,3,java.util.List.of("Suppressive Fire")));
        troops.add(new Troop(CorpsType.INFANTRY, Role.GRENADIER,85,35,30,40,60,4,java.util.List.of("Fragmentation")));
        troops.add(new Troop(CorpsType.INFANTRY, Role.FIRE_TEAM_LEADER,60,40,80,50,70,6,java.util.List.of("Inspire")));
        // ARMOURED
        troops.add(new Troop(CorpsType.ARMOURED, Role.ASLAV,75,50,60,20,70,3,java.util.List.of("Recon")));
        troops.add(new Troop(CorpsType.ARMOURED, Role.BOXER,70,55,55,25,65,3,java.util.List.of("Rapid Fire")));
        troops.add(new Troop(CorpsType.ARMOURED, Role.M1A2_TANK,95,80,70,15,60,4,java.util.List.of("Overwatch")));
        // ARTILLERY
        troops.add(new Troop(CorpsType.ARTILLERY, Role.MORTAR,85,30,50,20,80,4,java.util.List.of("Indirect Fire")));
        troops.add(new Troop(CorpsType.ARTILLERY, Role.HOWITZER,90,35,55,25,75,4,java.util.List.of("Heavy Shell")));
        troops.add(new Troop(CorpsType.ARTILLERY, Role.MLRS,100,40,50,10,70,3,java.util.List.of("Area Saturation")));
        // ENGINEERS
        troops.add(new Troop(CorpsType.ENGINEERS, Role.SAPPER,60,25,70,30,50,3,java.util.List.of("Demolitions")));
        troops.add(new Troop(CorpsType.ENGINEERS, Role.BREACHER,65,30,65,35,55,3,java.util.List.of("Breach")));
        troops.add(new Troop(CorpsType.ENGINEERS, Role.BRIDGER,55,30,75,40,60,3,java.util.List.of("Bridge Deployment")));
        // MEDICAL
        troops.add(new Troop(CorpsType.MEDICAL, Role.MED_TECH,30,20,80,40,50,5,java.util.List.of("First Aid")));
        troops.add(new Troop(CorpsType.MEDICAL, Role.NURSE,25,15,85,45,45,5,java.util.List.of("Heal Over Time")));
        troops.add(new Troop(CorpsType.MEDICAL, Role.DOCTOR,20,10,90,50,40,5,java.util.List.of("Surgery")));
        // TRANSPORT
        troops.add(new Troop(CorpsType.TRANSPORT, Role.GWAGON,10,30,60,20,50,4,java.util.List.of("Transport Troops")));
        troops.add(new Troop(CorpsType.TRANSPORT, Role.UNIMOG,15,35,55,25,55,4,java.util.List.of("Supply Delivery")));
        // AVIATION
        troops.add(new Troop(CorpsType.AVIATION, Role.BLACKHAWK,70,40,65,20,80,4,java.util.List.of("Air Mobility")));
        troops.add(new Troop(CorpsType.AVIATION, Role.CHINOOK,75,45,60,20,75,4,java.util.List.of("Heavy Lift")));
        troops.add(new Troop(CorpsType.AVIATION, Role.TIGER,85,50,55,15,85,4,java.util.List.of("Close Air Support")));
    }
    public static java.util.List<Troop> getTroopsByCorps(CorpsType c) {
        java.util.List<Troop> out = new java.util.ArrayList<>();
        for (Troop t:troops) if (t.getCorps()==c) out.add(t);
        return out;
    }
}
