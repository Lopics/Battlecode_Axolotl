package Axolotl;

import battlecode.common.*;

import static Axolotl.Broadcasts.*;
import static Axolotl.Movement.*;


public class Soldier extends General {
    public static void loop() throws GameActionException {
        while (true) {
            try {
                update();
                updateBullets();
                updateRobotInfos();
                turn();
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
        }

    }

    private static void turn() throws GameActionException {
        dead();
        if (visibleEnemies.length > 0) {
            for (RobotInfo R : visibleEnemies) {
                tryMove(myLocation.directionTo(R.getLocation()));
                if (rc.canFirePentadShot()) {
                    rc.firePentadShot(myLocation.directionTo(R.getLocation()));
                }
                if (rc.canFireSingleShot()) {
                    rc.fireSingleShot(myLocation.directionTo(R.getLocation()));
                }
            }
        } else {
            if (rc.readBroadcast(BROADCAST_ARCHON_NEED_HELP) > 0) {
                tryMove(myLocation.directionTo(mapLocationFromInt(rc.readBroadcast(BROADCAST_ARCHON_NEED_HELP))));
            } else {
                if (myLocation.distanceTo(visibleAllies[0].getLocation()) <= 7) {
                    tryMove(myLocation.directionTo(visibleAllies[0].getLocation()).opposite());
                }
            }
        }
    }

    private static void dead() throws GameActionException {
        if (myHealth < 25 && deadmSend == false) {
            rc.broadcast(BROADCAST_NUMBER_OF_SOLDIER, rc.readBroadcast(BROADCAST_NUMBER_OF_SOLDIER) - 1);
            deadmSend = true;
        }
    }
}

