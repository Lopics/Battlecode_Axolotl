package Axolotl;

import battlecode.common.*;

public class Movement extends General {

    public static boolean tryMoveInDirection(Direction dir) throws GameActionException {
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }
        Direction left = dir.rotateLeftDegrees(45);
        if (rc.canMove(left)) {
            rc.move(left);
            return true;
        }
        Direction right = dir.rotateRightDegrees(45);
        if (rc.canMove(right)) {
            rc.move(right);
            return true;
        }
        Direction leftLeft = left.rotateLeftDegrees(90);
        if (rc.canMove(leftLeft)) {
            rc.move(leftLeft);
            return true;
        }
        Direction rightRight = right.rotateRightDegrees(90);
        if (rc.canMove(rightRight)) {
            rc.move(rightRight);
            return true;
        }
        return false;
    }

    public static boolean goToDirect(MapLocation dest) throws GameActionException {
        if (myLocation.equals(dest)) {
            return false;
        }

        Direction forward = myLocation.directionTo(dest);
        MapLocation forwardLoc = myLocation.add(forward);

        if (rc.canMove(forward)) {
            System.out.println(myLocation.distanceTo(dest));
            rc.move(forward);
            return true;
        }

        if (rc.isLocationOccupiedByRobot(forwardLoc)) {
            RobotInfo U = rc.senseRobotAtLocation(forwardLoc);
            if (U.getTeam() == EnemyTeam) {
                if (rc.canStrike() && myLocation.distanceTo(U.getLocation()) - myBodyRadius - U.getRadius() <= 1) {
                    rc.strike();
                }
            } else if (U.getTeam() == myTeam) {
                Direction[] dirs;
                if (preferLeft(dest)) {
                    dirs = new Direction[]{ forward.rotateLeftDegrees(90), forward.rotateRightDegrees(90),
                            forward.rotateLeftDegrees(90).rotateLeftDegrees(90), forward.rotateRightDegrees(90).rotateRightDegrees(90)};
                } else {
                    dirs = new Direction[]{ forward.rotateRightDegrees(90), forward.rotateLeftDegrees(90),
                            forward.rotateRightDegrees(90).rotateRightDegrees(90), forward.rotateLeftDegrees(90).rotateLeftDegrees(90)};
                }

                for (Direction dir : dirs) {
                    if (rc.canMove(dir)) {
                        rc.move(dir);
                        return true;
                    }
                }

            }

        }
        System.out.println("RETURN FALSE HERE");
        return false;
    }

    private static boolean preferLeft(MapLocation dest) {
        Direction toDest = myLocation.directionTo(dest);
        MapLocation leftLoc = myLocation.add(toDest.rotateLeftDegrees(90));
        MapLocation rightLoc = myLocation.add(toDest.rotateRightDegrees(90));
        return (dest.distanceTo(leftLoc) < dest.distanceTo(rightLoc));
    }

}
