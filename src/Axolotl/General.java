package Axolotl;

import battlecode.common.*;

public class General {
    private static final int MAX = 1000;
    private static final int MIN = 0;

    public static final int BROADCAST_NUMBER_OF_GARDENER = 0;
    public static final int BROADCAST_NUMBER_OF_LUMBERJACK = 1;
    public static final int BROADCAST_NUMBER_OF_SCOUT = 2;
    public static final int BROADCAST_NUMBER_OF_SOLDIER = 3;
    public static final int BROADCAST_NUMBER_OF_TANK = 4;

    public static RobotController rc;
    public static MapLocation myLocation;
    public static Team myTeam;
    public static Team EnemyTeam;
    public static int myID;
    public static RobotType myType;
    public static float mySightRadius;
    public static float myBodyRadius;
    public static MapLocation[] ourInitialArchonLocations;
    public static MapLocation[] theirInitialArchonLocations;
    public static int numberOfInitialArchon;
    public static int roundNum;
    public static float mySenseRadius;
    public static RobotInfo[] visibleEnemies = null;
    public static TreeInfo[] visibleNeutralTrees = null;

    public static void init(RobotController RC) {
        rc = RC;
        myTeam = rc.getTeam();
        EnemyTeam = myTeam.opponent();
        myID = rc.getID();
        myType = rc.getType();
        mySightRadius = myType.sensorRadius;
        myBodyRadius = myType.bodyRadius;
        ourInitialArchonLocations = rc.getInitialArchonLocations(myTeam);
        theirInitialArchonLocations = rc.getInitialArchonLocations(EnemyTeam);
        numberOfInitialArchon = ourInitialArchonLocations.length;
    }

    public static void update() {
        myLocation = rc.getLocation();
        roundNum = rc.getRoundNum();
    }

    public static void updateRobotInfos() {
        visibleEnemies =  rc.senseNearbyRobots(mySenseRadius, EnemyTeam);
    }

    public static void updateTreeInfos(){
        visibleNeutralTrees = rc.senseNearbyTrees(mySenseRadius,Team.NEUTRAL);
    }

    public static int encode(int x, int y) {
        return (x * (MAX - MIN + 1)) + y;
    }

    public static int[] decode(int z) {
        return new int[]{(MIN + (z / (MAX - MIN + 1))), (MIN + (z % (MAX - MIN + 1)))};
    }
}
