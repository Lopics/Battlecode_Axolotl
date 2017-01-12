package Axolotl;

import battlecode.common.*;

import static Axolotl.RobotPlayer.randomDirection;
import static Axolotl.RobotPlayer.rc;
import static Axolotl.RobotPlayer.tryMove;

public class Gardener {
    public static void loop() {
        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

                /*// Listen for home archon's location
                int xPos = rc.readBroadcast(0);
                int yPos = rc.readBroadcast(1);
                MapLocation archonLoc = new MapLocation(xPos, yPos);*/

                if(rc.isBuildReady()) {
                    tryToSpawn(RobotType.LUMBERJACK);
                }
                // Move randomly
                tryMove(randomDirection());

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
            Clock.yield();
        }
    }

    private static void tryToSpawn(RobotType T) throws GameActionException {
        Direction dir = randomDirection();
        int count = 0;
        while (!rc.canBuildRobot(T,dir)) {
            dir.rotateRightDegrees(90);
            count++;
            if (count > 3) {
                break;
            }
        }
        if (rc.canBuildRobot(T,dir)) {
            rc.buildRobot(T,dir);
            rc.broadcast(1, rc.readBroadcast(1) + 1);
            return;
        }
    }
}
