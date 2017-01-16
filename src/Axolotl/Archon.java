package Axolotl;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;

import static Axolotl.Movement.*;

public class Archon extends General {
    public static void loop() {
        while (true) {
            try {
                update();
                updateBullets();
                updateRobotInfos();
                turn();
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }

        }
    }

    private static void turn() throws GameActionException{

        if (rc.readBroadcast(0) < 1) {
            tryToSpawn();
        }
        Direction forward = myLocation.directionTo(EnemyInitialArchonLocations[0]);
        tryMove(forward);
    }

    private static void tryToSpawn() throws GameActionException {
        Direction dir = randomDirection();
        int count = 0;
        while (!rc.canHireGardener(dir)) {
            dir.rotateRightDegrees(90);
            count++;
            if (count > 3) {
                break;
            }
        }
        if (rc.canHireGardener(dir)) {
            rc.hireGardener(dir);
            rc.broadcast(0, rc.readBroadcast(0) + 1);
            return;
        }
    }
}
