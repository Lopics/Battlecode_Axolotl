package Axolotl;

import battlecode.common.*;

public class Movement extends General {

    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // Axolotl, try intended direction
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        int currentCheck = 1;

        while (currentCheck <= checksPerSide) {
            // Try the offset of the left side
            Direction leftSide = dir.rotateLeftDegrees(degreeOffset * currentCheck);
            if (rc.canMove(leftSide)) {
                if (!willCollideWithMe(visibleBullets, myLocation.add(leftSide))) {
                    rc.move(leftSide);
                    return true;

                }
            }
            // Try the offset on the right side
            Direction rightSide = dir.rotateRightDegrees(degreeOffset * currentCheck);
            if (rc.canMove(rightSide)) {
                if (!willCollideWithMe(visibleBullets, myLocation.add(rightSide))) {
                    rc.move(rightSide);
                    return true;
                }
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }

    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir, 45, 3);
    }

    static Direction randomDirection() {
        return new Direction((float) Math.random() * 2 * (float) Math.PI);
    }

    static boolean willCollideWithMe(BulletInfo[] bullets, MapLocation location) {

        for (BulletInfo B : bullets) {
            // Get relevant bullet information
            Direction propagationDirection = B.dir;
            MapLocation bulletLocation = B.location;

            // Calculate bullet relations to this robot
            Direction directionToRobot = bulletLocation.directionTo(location);
            float distToRobot = bulletLocation.distanceTo(location);
            float theta = propagationDirection.radiansBetween(directionToRobot);

            if ((float) Math.abs(distToRobot * Math.sin(theta)) <= myBodyRadius) {
                return true;
            }
        }
        return false;
    }
}
