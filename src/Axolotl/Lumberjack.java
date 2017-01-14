package Axolotl;

import battlecode.common.*;

import java.util.Map;

import static Axolotl.General.EnemyTeam;
import static Axolotl.Movement.goToDirect;
import static Axolotl.Movement.tryMoveInDirection;
import static Axolotl.RobotPlayer.randomDirection;
import static Axolotl.RobotPlayer.rc;
import static Axolotl.RobotPlayer.tryMove;

public class Lumberjack extends General {

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
            try {
                update();

                TreeInfo[] Trees = rc.senseNearbyTrees(mySightRadius, Team.NEUTRAL);
                if (Trees.length > 0) {
                    if (myLocation.distanceTo(Trees[0].getLocation())-myBodyRadius- Trees[0].getRadius()> 0) {
                        goToDirect(Trees[0].getLocation());
                    }
                    if (rc.canChop(Trees[0].getLocation())) {
                        rc.chop(Trees[0].getLocation());
                        if (!rc.canSenseTree(Trees[0].getID())) {
                            rc.broadcast(100, 0);
                        }
                    }

                } else {
                    if (rc.readBroadcast(100) != 0) {
                        int[] coord = decode(rc.readBroadcast(100));
                        int x = coord[0];
                        int y = coord[1];
                        tryMoveInDirection(myLocation.directionTo(new MapLocation(x, y)));
                    }
                }
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Lumber Exception");
                e.printStackTrace();
            }
            // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again

        }

    }


}
