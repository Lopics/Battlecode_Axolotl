package Axolotl;

import battlecode.common.*;

import static Axolotl.Broadcasts.*;
import static Axolotl.Functions.avoidBullets;
import static Axolotl.Functions.findSpecificEnemy;
import static Axolotl.Movement.tryMove;
import static battlecode.common.GameConstants.LUMBERJACK_STRIKE_RADIUS;


public class Lumberjack extends General {

    public static void loop() throws GameActionException {
        while (true) {
            try {
                update();
                updateBullets();
                updateRobotInfos();
                updateTreeInfos();
                turn();
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Lumber Exception");
                e.printStackTrace();
            }
        }
    }

    private static void turn() throws GameActionException {
        dead();
        firstRound();
        avoidBullets();
        normalJob();

    }

    private static void dead() throws GameActionException {
        if (myHealth < 15 && deadmSend == false && myJob != 1) {
            rc.broadcast(myChannel, 0);
            rc.broadcast(BROADCAST_NUMBER_OF_LUMBERJACK, rc.readBroadcast(BROADCAST_NUMBER_OF_LUMBERJACK) - 1);
            deadmSend = true;
        } else if (myHealth < 15 && deadmSend == false && myJob == 1) {
            rc.broadcast(BROADCAST_NUMBER_OF_LUMBERJACK, rc.readBroadcast(BROADCAST_NUMBER_OF_LUMBERJACK) - 1);
            deadmSend = true;
        }
    }

    private static void normalJob() throws GameActionException {
        if (visibleEnemies.length > 0) {
            tryMove(myLocation.directionTo(visibleEnemies[0].getLocation()));
            if (rc.canStrike()) {
                if (myLocation.isWithinDistance(visibleEnemies[0].getLocation(), LUMBERJACK_STRIKE_RADIUS + myBodyRadius)) {
                    rc.strike();
                }
            }
        }
        if (rc.readBroadcast(BROADCAST_ARCHON_NEED_HELP) > 0) {
            tryMove(myLocation.directionTo(mapLocationFromInt(rc.readBroadcast(BROADCAST_ARCHON_NEED_HELP))));
        }
        if (visibleNeutralTrees.length > 0) {
            TreeInfo main = visibleNeutralTrees[0];
            /*for (TreeInfo T: visibleNeutralTrees){
                if (T.getContainedRobot() != null){
                    main = T;
                    break;
                }
            }*/
            tryMove(myLocation.directionTo(main.getLocation()));
            if (rc.canInteractWithTree(main.getLocation())) {
                rc.chop(main.getLocation());

            }
        } else {
            if (rc.readBroadcast(BROADCAST_NEUTRAL_TREES) > 0) {
                tryMove(myLocation.directionTo(mapLocationFromInt(rc.readBroadcast(BROADCAST_NEUTRAL_TREES))));

            }
        }
        if (visibleAllies.length > 0) {
            if (myLocation.distanceTo(visibleAllies[0].getLocation()) <= 7) {
                Direction oppositeDirection = myLocation.directionTo(visibleAllies[0].getLocation()).opposite();
                if (rc.canMove(oppositeDirection)) {
                    tryMove(oppositeDirection);

                }
            }
        }
    }

    private static void firstRound() throws GameActionException {
        if (initialized == false) {
            rc.broadcast(BROADCAST_NUMBER_OF_LUMBERJACK, rc.readBroadcast(BROADCAST_NUMBER_OF_LUMBERJACK) + 1);
            initialized = true;
            myJob = 1;
            return;
        }
    }
}
