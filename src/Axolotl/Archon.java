package Axolotl;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;

import static Axolotl.RobotPlayer.randomDirection;
import static Axolotl.RobotPlayer.tryMove;

public class Archon extends General {
    public static void loop() {
        // The code you want your robot to perform every round should be in this loop
        while (true) {
            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                if (rc.readBroadcast(0) < 1) {
                    tryToSpawn();
                }
                // Move randomly
                tryMove(randomDirection());
                /*// Broadcast archon's location for other robots on the team to know
                MapLocation myLocation = rc.getLocation();
                rc.broadcast(0,(int)myLocation.x);
                rc.broadcast(1,(int)myLocation.y);*/

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }

        }
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
