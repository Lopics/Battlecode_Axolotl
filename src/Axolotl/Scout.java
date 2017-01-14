package Axolotl;

import battlecode.common.*;

import static Axolotl.RobotPlayer.randomDirection;
import static Axolotl.RobotPlayer.tryMove;

public class Scout extends General {
    public static void loop() throws GameActionException {
        while (true) {
            try {
                // Move randomly
                tryMove(randomDirection());
                if (rc.readBroadcast(100) == 0) {
                    trySenseNeutralTrees();
                }
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Scout Exception");
                e.printStackTrace();
            }
        }

    }

    private static void trySenseNeutralTrees() throws GameActionException {
        TreeInfo[] Trees = rc.senseNearbyTrees(mySightRadius, Team.NEUTRAL);
        if (Trees.length > 0) {
            writeNTree(Trees[0].getLocation());
        }
        return;
    }


    private static void writeNTree(MapLocation m) throws GameActionException {
        rc.broadcast(100 , encode((int)m.x,(int)m.y));
    }
}
