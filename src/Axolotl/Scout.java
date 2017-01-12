package Axolotl;

import battlecode.common.*;

import static Axolotl.RobotPlayer.randomDirection;
import static Axolotl.RobotPlayer.tryMove;

public class Scout extends General {
    public static void loop() throws GameActionException {
        while (true) {
            // Move randomly
            tryMove(randomDirection());
            trySenseNeutralTrees();
            Clock.yield();
        }

    }

    private static void trySenseNeutralTrees() throws GameActionException {
        TreeInfo[] Trees = rc.senseNearbyTrees(mySightRadius+myBodyRadius, Team.NEUTRAL);
        MapLocation[] Local = readNTree();
        if (Trees.length > 0) {
            for (int x = 0; x < Local.length; x++) {
                for (int y = 0; y < Trees.length; y++) {
                    if (Trees[y].getLocation() != Local[x]) {
                        writeNTree(Trees[y].getLocation());
                    }
                }

            }

        }
        return;
    }

    private static MapLocation[] readNTree() throws GameActionException {
        if (rc.readBroadcast(5) > 0) {
            MapLocation[] treeLocations = new MapLocation[rc.readBroadcast(5)];
            int count = 0;
            for (int x = 0; x < rc.readBroadcast(5); x += 2) {
                if (rc.readBroadcast(100 + x) != 0) {
                    treeLocations[count] = new MapLocation(rc.readBroadcast(100 + x), rc.readBroadcast(101 + x));
                }
            }
            return treeLocations;
        }
        return null;
    }

    private static void writeNTree(MapLocation m) throws GameActionException {
        int x = 0;
        while (rc.readBroadcast(100 + x) != 0) {
            x += 2;
        }
        rc.broadcast(100 + x, (int) m.x);
        rc.broadcast(101 + x, (int) m.y);
    }
}
