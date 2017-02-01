package Axolotl;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
public class Broadcasts extends General {


    public static final int BROADCAST_NUMBER_OF_GARDENER = 0;
    public static final int BROADCAST_NUMBER_OF_LUMBERJACK = 1;
    public static final int BROADCAST_NUMBER_OF_SCOUT = 2;
    public static final int BROADCAST_NUMBER_OF_SOLDIER = 3;
    public static final int BROADCAST_NUMBER_OF_TANK = 4;
    public static final int BROADCAST_ATTACK_COMMAND = 5;
    public static final int BROADCAST_NUMBER_OF_GARDENER_L = 6;
    public static final int BROADCAST_LEADER_ARCHON = 7;
    public static final int[] BROADCAST_LUMBERJACK_ATTACK = {10,11};

    public static final int BROADCAST_ARCHON_NEED_HELP = 15;
    public static final int BROADCAST_NEUTRAL_TREES = 20;
    public static final int BROADCAST_ENEMY_ARCHONS_LOCATION = 30;
    public static final int BROADCAST_ENEMY_ARCHONS_DEAD = 29;

    public static int intFromMapLocation(MapLocation loc) {
        if (loc == null) return 0xfffff;
        return ((int)loc.x << 10) | ((int)loc.y);
    }

    public static MapLocation mapLocationFromInt(int data) {
        if ((data & 0xfffff) == 0xfffff) return null;
        int x = ((data & 0xffc00) >>> 10);
        int y = (data & 0x003ff);
        return new MapLocation(x, y);
    }

    public static void sendMapLocation(int channel, MapLocation loc) throws GameActionException {
        rc.broadcast(channel, intFromMapLocation(loc));
    }

    private static void sendInt(int channel, int data) throws GameActionException {
        rc.broadcast(channel, data );
    }

    public static void sendAttackCommand(MapLocation loc) throws GameActionException {
        sendMapLocation(BROADCAST_ATTACK_COMMAND, loc);
    }

    public static void sendNaturalTreesLocation(MapLocation loc) throws GameActionException {
        sendMapLocation(BROADCAST_NEUTRAL_TREES, loc);
    }
}
