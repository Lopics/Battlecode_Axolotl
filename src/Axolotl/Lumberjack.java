package Axolotl;

import battlecode.common.*;

import java.util.Map;

import static Axolotl.General.EnemyTeam;
import static Axolotl.RobotPlayer.randomDirection;
import static Axolotl.RobotPlayer.rc;
import static Axolotl.RobotPlayer.tryMove;

public class Lumberjack extends General {
    static MapLocation[] Local;
    public static void loop() throws GameActionException {

        // The code you want your robot to perform every round should be in this loop
        while (true) {
            // See if there are any enemy robots within striking range (distance 1 from lumberjack's radius)
            /*RobotInfo[] robots = rc.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius + GameConstants.LUMBERJACK_STRIKE_RADIUS, EnemyTeam);

            if (robots.length > 0 && !rc.hasAttacked()) {
                // Use strike() to hit all nearby robots!
                rc.strike();
            } else {
                // No close robots, so search for robots within sight radius
                robots = rc.senseNearbyRobots(-1, EnemyTeam);

                // If there is a robot, move towards it
                if (robots.length > 0) {
                    MapLocation myLocation = rc.getLocation();
                    MapLocation enemyLocation = robots[0].getLocation();
                    Direction toEnemy = myLocation.directionTo(enemyLocation);

                    tryMove(toEnemy);
                } else {
                    // Move Randomly
                    tryMove(randomDirection());
                }
            }*/

                MapLocation[] Local = readNTree();

            if(Local.length > 0){
                if(rc.canMove(Local[0])){
                    rc.move(Local[0]);
                }
            }
            // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
            Clock.yield();
        }
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
}
