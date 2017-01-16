package Axolotl;

import battlecode.common.*;

import static Axolotl.Movement.*;

public class Gardener extends General {
    public static void loop() {
        while (true) {
            try {

                Clock.yield();

            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
            Clock.yield();
        }
    }

    private static void tryToSpawn(RobotType T, int BR) throws GameActionException {
        Direction dir = randomDirection();
        int count = 0;
        while (!rc.canBuildRobot(T, dir)) {
            dir.rotateRightDegrees(90);
            count++;
            if (count > 3) {
                break;
            }
        }
        if (rc.canBuildRobot(T, dir)) {
            rc.buildRobot(T, dir);
            rc.broadcast(BR, rc.readBroadcast(BR) + 1);
            return;
        }
    }
}
