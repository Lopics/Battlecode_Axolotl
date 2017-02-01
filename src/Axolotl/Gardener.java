package Axolotl;

import battlecode.common.*;

import static Axolotl.Broadcasts.*;
import static Axolotl.Functions.*;
import static Axolotl.Movement.*;

public class Gardener extends General {
    public static void loop() {
        while (true) {
            try {
                update();
                updateBullets();
                updateRobotInfos();
                updateTreeInfos();
                turn();
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
        }
    }

    private static void turn() throws GameActionException {
        dead();
        if (rc.getTeamBullets() > 500) {
            rc.donate(rc.getVictoryPointCost());
        }

        if (rc.getTeamBullets() > 1000) {
            rc.donate(500);
        }
        firstRounds();
        if (isCircleBuilt == true && meLeader == false) {
            buildCircle(7);
            waterCircle();
            shakeCircle();
        }
        if (meLeader) {
            avoidEnemyRobots();
            avoidBullets();
            leaderJob();
        }
        if (visibleEnemies.length > 0) {
            sendMapLocation(BROADCAST_ARCHON_NEED_HELP, visibleEnemies[0].getLocation());
        }
    }

    private static void firstRounds() throws GameActionException {
        if (isCircleBuilt == false) {
            if (rc.readBroadcast(BROADCAST_NUMBER_OF_GARDENER_L) == 0) {
                rc.broadcast(BROADCAST_NUMBER_OF_GARDENER_L, rc.getID());
                meLeader = true;
            }
            if (meLeader == false) {

                /*int fuckme = 0;
                for (int x = 0; x < visibleAllies.length; x++) {
                    if (myLocation.distanceTo(visibleAllies[x].getLocation()) > 6) {
                        fuckme++;
                    }
                }
                if (fuckme == visibleAllies.length) {
                    isCircleBuilt = true;
                }*/

                if (myLocation.distanceTo(visibleAllies[0].getLocation()) > 6){
                    isCircleBuilt = true;
                }else{
                    if (!rc.hasMoved()) {
                        tryMove(myLocation.directionTo(visibleAllies[0].getLocation()).opposite());
                    }
                }
            }
        }
    }

    private static void shakeCircle() throws GameActionException {
        for (TreeInfo T : rc.senseNearbyTrees((float) 2.5, myTeam)) {
            if (rc.canShake()) {
                if (T.getContainedBullets() > 10) {
                    rc.shake(T.getID());
                    break;
                }
            } else {
                break;
            }

        }
    }

    private static void waterCircle() throws GameActionException {
        for (TreeInfo T : rc.senseNearbyTrees((float) 2.5, myTeam)) {
            if (rc.canWater()) {
                if (T.getHealth() < 45) {
                    rc.water(T.getID());
                    break;
                }
            } else {
                break;
            }

        }
    }

    private static void buildCircle(int max) throws GameActionException {
        for (int x = 1; x < max; x++) {
            Direction dir = new Direction((float) 1.0472 * x);
            if (rc.canPlantTree(dir)) {
                rc.plantTree(dir);
            }
        }
    }

    private static void leaderJob() throws GameActionException {
        for (RobotInfo R : visibleAllies) {
            if (myLocation.distanceTo(R.getLocation()) < 7) {
                if (!rc.hasMoved()) {
                    tryMove(new Direction(myLocation, R.getLocation()).opposite());
                }
            }
        }


        if (rc.readBroadcast(BROADCAST_NUMBER_OF_SOLDIER) < 3) {
            tryToSpawn(RobotType.SOLDIER, BROADCAST_NUMBER_OF_SOLDIER);
        }

        if (rc.readBroadcast(BROADCAST_NUMBER_OF_LUMBERJACK) < 5) {
            tryToSpawn(RobotType.LUMBERJACK, BROADCAST_NUMBER_OF_LUMBERJACK);

        }
        if (rc.readBroadcast(BROADCAST_NUMBER_OF_TANK) < 1) {
            tryToSpawn(RobotType.TANK, BROADCAST_NUMBER_OF_TANK);

        }

        if (rc.readBroadcast(BROADCAST_NUMBER_OF_LUMBERJACK) < 10) {
            tryToSpawn(RobotType.LUMBERJACK, BROADCAST_NUMBER_OF_LUMBERJACK);

        }
        if (rc.readBroadcast(BROADCAST_NUMBER_OF_TANK) < 10) {
            tryToSpawn(RobotType.TANK, BROADCAST_NUMBER_OF_TANK);

        }


    }

    private static void dead() throws GameActionException {
        if (myHealth < 20 && deadmSend == false && meLeader == true) {
            rc.broadcast(BROADCAST_NUMBER_OF_GARDENER_L, 0);
            rc.broadcast(BROADCAST_NUMBER_OF_GARDENER, rc.readBroadcast(BROADCAST_NUMBER_OF_GARDENER) - 1);
            deadmSend = true;
        } else if (myHealth < 20 && deadmSend == false && meLeader == false) {
            rc.broadcast(BROADCAST_NUMBER_OF_GARDENER, rc.readBroadcast(BROADCAST_NUMBER_OF_GARDENER) - 1);
            deadmSend = true;
        }
    }
}
