package Axolotl;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class General {
    private static final int MAX = 1000;
    private static final int MIN = 0;

    public static RobotController rc;
    public static MapLocation myLocation;
    public static Team myTeam;
    public static Team EnemyTeam;
    public static int myID;
    public static RobotType myType;
    public static float mySightRadius;
    public static float myBodyRadius;
    public static MapLocation[] myInitialArchonLocations;
    public static MapLocation[] EnemyInitialArchonLocations;
    public static int numberOfInitialArchon;
    public static int roundNum;
    public static RobotInfo[] visibleEnemies = null;
    public static TreeInfo[] visibleNeutralTrees = null;
    public static TreeInfo[]  visisbleAllyTrees = null;
    public static RobotInfo[] visibleAllies = null;
    public static BulletInfo[] visibleBullets = null;
    public static TreeInfo[] visibleEnemyTrees = null;
    public static boolean foundFirstEnemy = false;
    public static int enemyVictoryPoints = 0;
    public static int myVictoryPoints =0;
    public static boolean isCircleBuilt = false;
    public static boolean deadmSend = false;
    public static boolean meLeader = false;
    public static int myJob = 0;
    public static boolean initialized = false;
    public static int myChannel = 0;
    public static float myHealth;
    public static ArrayList<MapLocation> myMovementMemory = new ArrayList<MapLocation>();
    public static boolean leaderArchon = false;
    public static int gardnerCount= 0;

    public static void init(RobotController RC) {
        rc = RC;
        myID = rc.getID();
        myTeam = rc.getTeam();
        EnemyTeam = myTeam.opponent();
        myID = rc.getID();
        myType = rc.getType();
        mySightRadius = myType.sensorRadius;
        myBodyRadius = myType.bodyRadius;
        myInitialArchonLocations = rc.getInitialArchonLocations(myTeam);
        EnemyInitialArchonLocations = rc.getInitialArchonLocations(EnemyTeam);
        numberOfInitialArchon = myInitialArchonLocations.length;
        myVictoryPoints = rc.getTeamVictoryPoints();
        enemyVictoryPoints = rc.getOpponentVictoryPoints();
    }

    public static void update() {
        myHealth = rc.getHealth();
        myLocation = rc.getLocation();
        roundNum = rc.getRoundNum();
    }
    public  static void updateBullets(){
        visibleBullets = rc.senseNearbyBullets();
    }
    public static void updateRobotInfos() {
        visibleEnemies =  rc.senseNearbyRobots(mySightRadius, EnemyTeam);
        visibleAllies = rc.senseNearbyRobots(mySightRadius, myTeam);
    }
    public  static void updateVictoryPoints(){
        myVictoryPoints = rc.getTeamVictoryPoints();
        enemyVictoryPoints = rc.getOpponentVictoryPoints();
    }


    public static void updateTreeInfos(){
        visisbleAllyTrees = rc.senseNearbyTrees(mySightRadius,myTeam);
        visibleNeutralTrees = rc.senseNearbyTrees(mySightRadius,Team.NEUTRAL);
        visibleEnemyTrees = rc.senseNearbyTrees(mySightRadius, EnemyTeam);
    }
}
