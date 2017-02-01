package Axolotl;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.List;

import static Axolotl.Movement.randomDirection;
import static Axolotl.Movement.tryMove;
import static Axolotl.Movement.willCollideWithMe;

public class Functions extends General {
    public static void tryToSpawn(RobotType T, int BR) throws GameActionException {
        Direction dir = new Direction(0);
        int count = 0;
        while (!rc.canBuildRobot(T, dir)) {
            dir = new Direction((float) 0.785398 * count);
            count++;
            if (count > 7) {
                break;
            }
        }
        if (rc.canBuildRobot(T, dir)) {
            rc.buildRobot(T, dir);
            rc.broadcast(BR, rc.readBroadcast(BR) + 1);
            return;
        }
    }

    public static void avoidEnemyRobots() throws GameActionException {
        if (visibleEnemies.length > 0) {
            switch (myType) {
                case SCOUT:
                    moveAwayDistance(9);
                    break;
                default:
                    moveAwayDistance(7);
                    break;
            }
        }
    }

    private static void moveAwayDistance(int stayBackDistance) throws GameActionException {
        RobotInfo R = visibleEnemies[0];
        if (R.getType() == RobotType.TANK || R.getType() == RobotType.SOLDIER || R.getType() == RobotType.LUMBERJACK) {
            if (myLocation.distanceTo(R.getLocation()) <= stayBackDistance) {
                Direction oppositeDirection = myLocation.directionTo(visibleEnemies[0].getLocation()).opposite();
                if (rc.canMove(oppositeDirection)) {
                    tryMove(oppositeDirection);
                }
            }
        }
    }

    public static RobotInfo findSpecificEnemy(RobotType T) {
        if (visibleEnemies.length > 0) {
            for (RobotInfo R : visibleEnemies) {
                if (R.getType() == T) {
                    return R;
                }
            }
        }
        return null;
    }

    public static void avoidBullets() throws GameActionException {
        if (visibleBullets.length > 0) {
            if (willCollideWithMe(visibleBullets, myLocation)) {
                tryMove(randomDirection());
            }
        }
    }

    public static List<RobotInfo> returnGardeners(){
        List<RobotInfo> temp = new ArrayList<>();
        for (RobotInfo R: visibleAllies){
            if (R.getType() == RobotType.GARDENER || R.getType() == RobotType.ARCHON) {
               temp.add(R);
            }
        }
        return temp;
    }
}
