package Axolotl;

import battlecode.common.*;

import static Axolotl.Broadcasts.*;
import static Axolotl.Functions.avoidBullets;
import static Axolotl.Functions.avoidEnemyRobots;
import static Axolotl.Movement.tryMove;


public class Scout extends General {
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
                System.out.println("Scout Exception");
                e.printStackTrace();
            }
        }

    }

    private static void turn() throws GameActionException {
        dead();
        avoidEnemyRobots();
        avoidBullets();
        mainJob();
    }

    private static void mainJob() throws GameActionException {
        if ( foundFirstEnemy == false) {
            if (visibleEnemies.length > 0) {
                for (RobotInfo R: visibleEnemies){
                    if (R.getType() == RobotType.ARCHON){
                        foundFirstEnemy = true;
                        sendMapLocation(BROADCAST_ENEMY_ARCHONS_LOCATION, R.getLocation());
                        break;
                    }
                }
            }
            tryMove(myLocation.directionTo(EnemyInitialArchonLocations[0]));
        }

        for (RobotInfo R: visibleEnemies){
            if (R.getType() == RobotType.ARCHON){
                if (myLocation.distanceTo(R.getLocation()) < 10 ){
                    tryMove(myLocation.directionTo(R.getLocation()).opposite());
                } else if (myLocation.distanceTo(R.getLocation()) > 10){
                    tryMove(myLocation.directionTo(R.getLocation()));
                }
                if (R.getHealth() < 10){
                    rc.broadcast(BROADCAST_ENEMY_ARCHONS_DEAD, 1);
                }
                sendMapLocation(BROADCAST_ENEMY_ARCHONS_LOCATION, R.getLocation());
                break;
            }
        }

        if (visibleNeutralTrees.length > 0 ){
            sendMapLocation(BROADCAST_NEUTRAL_TREES,visibleNeutralTrees[0].getLocation());
        }

    }

    private static void dead() throws GameActionException {
        if (myHealth < 20 && deadmSend == false) {
            rc.broadcast(BROADCAST_NUMBER_OF_SCOUT, 1);
            deadmSend = true;
        }
    }

}
