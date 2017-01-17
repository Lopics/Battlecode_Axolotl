package Axolotl;

import battlecode.common.*;

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

    private static void turn() throws GameActionException {
        avoidEnemyRobots();
        avoidBullets();
        mainJob();
    }

    private static void mainJob() throws GameActionException{
        if (rc.readBroadcast(0) < 1) {
            tryToSpawn();
        }
        if(rc.getMoveCount() < 1){
            tryMove(randomDirection());
        }
    }

    private static void avoidEnemyRobots() throws GameActionException {
        if (visibleEnemies.length > 0) {
            if (myLocation.distanceTo(visibleEnemies[0].getLocation()) < 7) {
                Direction oppositeDirection = myLocation.directionTo(visibleEnemies[0].getLocation()).opposite();
                if (rc.canMove(oppositeDirection)) {
                    tryMove(oppositeDirection);
                }
            }
        }
    }

    private static void avoidBullets() throws GameActionException {
        if (willCollideWithMe(visibleBullets, myLocation)) {
            tryMove(randomDirection());
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
