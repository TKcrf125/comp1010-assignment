// ArmyLandCombat.java
public class ArmyLandCombat {
    public static void main(String[] args) {
        showMenu();
    }

    /** Main menu loop */
    public static void showMenu() {
        while (true) {
            System.out.println("=== Army Land Combat ===");
            System.out.println("1. Play");
            System.out.println("0. Exit");
            int choice;
            do {
                System.out.print("Enter choice (0-1): ");
                choice = readInt();
            } while (choice < 0 || choice > 1);
            if (choice == 1) playGame();
            else return;
        }
    }

    /** Read integer without imports */
    public static int readInt() {
        StringBuilder sb = new StringBuilder();
        try { int c;
            while ((c = System.in.read()) != -1 && c != '\n' && c != '\r') sb.append((char)c);
        } catch (Exception e) {}
        try { return Integer.parseInt(sb.toString().trim()); }
        catch (Exception e) { return -1; }
    }

    /** Wait for Enter */
    private static void waitForEnter() {
        try { while (System.in.read() != '\n'); } catch (Exception e) {}
    }

    /** Main gameplay */
    public static void playGame() {
        System.out.println("Contact front! Range 100 meters... enemy section moving on us!");
        System.out.println("Press Enter to engage..."); waitForEnter();

        CombatTeam player = new CombatTeam();
        EnemyTeam enemy = new EnemyTeam();
        int distance = 0;

        while (player.hasAlive() && enemy.hasAlive()) {
            // Player actions
            System.out.println("\n--- PLAYER TURN ---");
            for (Troop t : player.getAlive()) {
                if (!enemy.hasAlive()) break;
                int actions = 2;
                while (actions-- > 0 && player.hasAlive() && enemy.hasAlive()) {
                    System.out.println();
                    System.out.println(t.getRole() + " ready (AR=" + t.getArmour() + "). Distance: " + distance);
                    System.out.println("Actions: 1=Shoot 2=Move 3=Communicate 4=Medicate 5=Status");
                    int a;
                    do { System.out.print("Select action (1-5): "); a = readInt(); } while (a < 1 || a > 5);
                    switch (a) {
                        case 1: distance = player.shoot(t, enemy, distance); break;
                        case 2: distance = player.move(t, distance); break;
                        case 3: player.communicate(t, enemy, distance); actions++; break;
                        case 4: player.medicate(t); break;
                        case 5: player.showStatus(); actions++; break;
                    }
                }
            }
            // Reinforcement phase
            player.reinforce();
            if (!enemy.hasAlive()) break;

            // Enemy actions
            System.out.println("\n--- ENEMY TURN ---");
            for (Troop et : enemy.getAlive()) {
                if (!player.hasAlive()) break;
                int acts = 2;
                while (acts-- > 0 && player.hasAlive())
                    enemy.randomAction(et, player, distance);
            }
            enemy.reinforce();
        }

        // Outcome
        System.out.println();
        if (player.hasAlive()) System.out.println("Victory! Enemy neutralized.");
        else System.out.println("Defeat. All your troops are down.");
    }
}

// CombatTeam.java
class CombatTeam {
    private java.util.List<Troop> troops = new java.util.ArrayList<>();
    private int commandPool;

    public CombatTeam() {
        for (TroopType tt : TroopType.values()) {
            troops.add(new Troop(tt));
        }
        recalcPool();
    }
    private void recalcPool() {
        commandPool = 0;
        for (Troop t : troops) if (t.isAlive()) commandPool += t.getCommand();
    }
    public java.util.List<Troop> getAlive() {
        java.util.List<Troop> a = new java.util.ArrayList<>();
        for (Troop t : troops) if (t.isAlive()) a.add(t);
        return a;
    }
    public boolean hasAlive() { return !getAlive().isEmpty(); }

    public void showStatus() {
        System.out.println("-- Player Team --");
        for (Troop t : getAlive())
            System.out.println(t.getRole() + ": AR=" + t.getArmour() + ", FP=" + t.getFirepower());
        recalcPool();
        System.out.println("Command Pool: " + commandPool);
    }

    // Delegators
    public int shoot(Troop s, EnemyTeam e, int dist) { return s.shootSelf(e, dist); }
    public int move(Troop m, int d) { return m.moveSelf(d); }
    public void communicate(Troop c, EnemyTeam e, int d) { c.communicateSelf(e, d); }
    public void medicate(Troop m) { m.medicateSelf(); }

    // Purchase reinforcements
    public void reinforce() {
        recalcPool();
        System.out.println("-- Reinforcement Phase (CP=" + commandPool + ") --");
        int choice;
        do {
            // list types
            int i=1;
            for (TroopType tt : TroopType.values())
                System.out.println((i++) + ": " + tt.name() + " (Cost=" + tt.cp + ")");
            System.out.println("0: End purchases");
            System.out.print("Select unit to purchase: ");
            choice = ArmyLandCombat.readInt();
            if (choice > 0 && choice <= TroopType.values().length) {
                TroopType sel = TroopType.values()[choice-1];
                if (sel.cp <= commandPool) {
                    commandPool -= sel.cp;
                    troops.add(new Troop(sel));
                    System.out.println("Purchased " + sel.name() + "; CP left=" + commandPool);
                } else System.out.println("Not enough CP.");
            }
        } while (choice != 0);
    }

    public void takeRandomDamage(Troop atk) {
        java.util.List<Troop> a = getAlive(); if (a.isEmpty()) return;
        Troop tar = a.get((int)(Math.random()*a.size()));
        atk.attackSelf(tar);
    }
}

// EnemyTeam.java
class EnemyTeam {
    private java.util.List<Troop> troops = new java.util.ArrayList<>();
    public EnemyTeam() {
        for (TroopType tt : TroopType.values()) {
            // 3x marksman, 1 each else
            if (tt == TroopType.MARKSMAN) for (int i=0;i<3;i++) troops.add(new Troop(tt));
            else troops.add(new Troop(tt));
        }
    }
    public java.util.List<Troop> getAlive() {
        java.util.List<Troop> a = new java.util.ArrayList<>();
        for (Troop t : troops) if (t.isAlive()) a.add(t);
        return a;
    }
    public boolean hasAlive() { return !getAlive().isEmpty(); }

    public void randomAction(Troop t, CombatTeam p, int d) {
        int c = (int)(Math.random()*3)+1;
        if (c==1) { System.out.println(t.getRole()+" [ENEMY] shoots."); t.attackSelf(p.getAlive().get(0)); }
        else if (c==2) System.out.println(t.getRole()+" [ENEMY] repositions.");
        else System.out.println(t.getRole()+" [ENEMY] holds position.");
    }

    public void reinforce() { System.out.println("Enemy reinforcements pending."); }
}

// Troop.java
class Troop {
    private Role role;
    private int firepower, armour, baseArmour, camouflage, range, command, cp;
    private boolean alive=true;

    public Troop(TroopType tt) {
        this.role=tt.role; this.firepower=tt.fp; this.armour=tt.ar; this.baseArmour=tt.ar;
        this.camouflage=tt.cam; this.range=tt.rg; this.command=tt.cmd; this.cp=tt.cp;
    }

    public Role getRole(){return role;} public int getFirepower(){return firepower;} public int getArmour(){return armour;}
    public int getCamouflage(){return camouflage;} public int getRange(){return range;} public int getCommand(){return command;}
    public boolean isAlive(){return alive;}

    public void takeDamage(int dmg){armour-=dmg; if(armour<=0){alive=false; armour=0;}}
    public void healArmor(int amt){armour=Math.min(baseArmour, armour+amt);}

    // Actions
    public int shootSelf(EnemyTeam enemy, int dist) {
        if (dist>range) { System.out.println(role+": Target out of range."); return dist; }
        java.util.List<Troop> tgt=enemy.getAlive(); if(tgt.isEmpty()) return dist;
        System.out.println(role+" engaging:");
        for(int i=0;i<tgt.size();i++) System.out.println((i+1)+": "+tgt.get(i).role+" AR="+tgt.get(i).armour);
        int sel; do{ System.out.print("Pick #: "); sel=ArmyLandCombat.readInt()-1;}while(sel<0||sel>=tgt.size());
        Troop e=tgt.get(sel);
        int roll=(int)(Math.random()*6)+1; int atk=roll*firepower; int def=roll*e.camouflage;
        System.out.println(role+" rolls "+roll+" ATK="+atk+" DEF="+def);
        if(atk>def){int dmg=atk-def; e.takeDamage(dmg); System.out.println(e.role+" takes "+dmg+" AR="+e.armour);
            if(!e.alive) System.out.println(e.role+" neutralized.");}
        else System.out.println(role+" shot failed.");
        return dist;
    }
    public int moveSelf(int d){int c; do{ System.out.print(role+" move (1=Close,2=Withdraw): "); c=ArmyLandCombat.readInt();}while(c!=1&&c!=2);
        if(c==1){d=Math.max(0,d-20);System.out.println(role+" moves to "+d);} else{d+=20;System.out.println(role+" to "+d);}return d;}
    public void communicateSelf(EnemyTeam e,int d){System.out.println(role+" reports: range="+d+" enemies="+e.getAlive().size());}
    public void medicateSelf(){int b=armour; healArmor(2); System.out.println(role+" AR "+b+"->"+armour);}    
    public void attackSelf(Troop t){int roll=(int)(Math.random()*6)+1;int atk=roll*firepower;int def=roll*t.camouflage;
        System.out.println(role+" rolls "+roll+" ATK="+atk+" DEF="+def);
        if(atk>def){int dmg=atk-def; t.takeDamage(dmg); System.out.println(t.role+" takes "+dmg+" AR="+t.armour); if(!t.alive) System.out.println(t.role+" killed.");}
        else System.out.println(role+" missed.");}
}

enum Role{MARKSMAN,GUNNER,GRENADIER,FIRE_TEAM_LEADER}

enum TroopType{
    MARKSMAN(Role.MARKSMAN,80,20,50,90,10,4),
    GUNNER(Role.GUNNER,90,40,30,80,15,5),
    GRENADIER(Role.GRENADIER,85,35,40,60,12,6),
    FIRE_LEADER(Role.FIRE_TEAM_LEADER,60,40,50,70,20,8);
    public final Role role; public final int fp,ar,cam,rg,cmd,cp;
    TroopType(Role r,int fp,int ar,int cam,int rg,int cmd,int cp){this.role=r;this.fp=fp;this.ar=ar;this.cam=cam;this.rg=rg;this.cmd=cmd;this.cp=cp;}
}