package Axolotl;

import battlecode.common.*;

import static Axolotl.Broadcasts.*;
import static Axolotl.Functions.avoidBullets;
import static Axolotl.Functions.avoidEnemyRobots;
import static Axolotl.Functions.tryToSpawn;
import static Axolotl.Movement.*;

public class Archon extends General {
    public static void loop() {
        while (true) {
            try {
                update();
                updateBullets();
                updateRobotInfos();
                updateVictoryPoints();
                turn();
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }

        }
    }

    private static void turn() throws GameActionException {
        if (rc.readBroadcast(BROADCAST_LEADER_ARCHON) == 0) {
            rc.broadcast(BROADCAST_LEADER_ARCHON, myID);
            leaderArchon = true;
        }
        avoidEnemyRobots();
        avoidBullets();
        if (rc.readBroadcast(0) < 1) {
            tryToSpawn(RobotType.GARDENER, BROADCAST_NUMBER_OF_GARDENER);
        }
        if (leaderArchon) {
            leaderJob();
        }else{
            normalJob();
        }

        if (visibleEnemies.length > 0) {
            sendMapLocation(BROADCAST_ARCHON_NEED_HELP, visibleEnemies[0].getLocation());
        } else if (visibleEnemies.length == 0) {
            rc.broadcast(BROADCAST_ARCHON_NEED_HELP, 0);
        }

        if (visibleAllies.length > 0) {
            if (myLocation.distanceTo(visibleAllies[0].getLocation()) < 10) {
                tryMove(myLocation.directionTo(visibleAllies[0].getLocation()).opposite());
            }
        }
    }

    private static void normalJob() throws GameActionException {
        if (rc.readBroadcast(0) < 3 && roundNum > 50 && rc.readBroadcast(BROADCAST_NUMBER_OF_LUMBERJACK) > 2) {
            tryToSpawn(RobotType.GARDENER, BROADCAST_NUMBER_OF_GARDENER);
            gardnerCount++;
        }
    }

    private static void leaderJob() throws GameActionException {

        if (rc.readBroadcast(0) < 7 && roundNum > 50 && rc.readBroadcast(BROADCAST_NUMBER_OF_LUMBERJACK) > 2) {
            tryToSpawn(RobotType.GARDENER, BROADCAST_NUMBER_OF_GARDENER);
            gardnerCount++;
        }

        if (enemyVictoryPoints > myVictoryPoints || rc.getTeamBullets() > 500) {
            rc.donate(rc.getVictoryPointCost());
        }

        if (rc.getTeamBullets() > 1000) {
            rc.donate(500);
        }
    }

}
