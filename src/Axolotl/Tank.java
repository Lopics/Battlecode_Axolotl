package Axolotl;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

import static Axolotl.Broadcasts.*;
import static Axolotl.Movement.randomDirection;
import static Axolotl.Movement.tanktryMove;
import static Axolotl.Movement.tryMove;

public class Tank extends General {
    public static void loop() throws GameActionException {
        while (true) {
            update();
            updateRobotInfos();
            turn();
            Clock.yield();
        }
    }

    private static void turn() throws GameActionException {

        if (foundFirstEnemy == false) {
            tanktryMove(myLocation.directionTo(EnemyInitialArchonLocations[0]));
            if (visibleEnemies.length > 0) {
                foundFirstEnemy = true;
            }
        } else {
            if (visibleEnemies.length > 0) {
                tanktryMove(myLocation.directionTo(visibleEnemies[0].getLocation()));
                for (RobotInfo R: visibleEnemies){
                    if (R.getType() == RobotType.ARCHON){
                        sendMapLocation(BROADCAST_ENEMY_ARCHONS_LOCATION,R.getLocation());
                        if (R.getHealth() < 20){
                            rc.broadcast(BROADCAST_ENEMY_ARCHONS_DEAD,1);
                            rc.broadcast(BROADCAST_ENEMY_ARCHONS_LOCATION, 0);
                        }
                    }
                }

                if (rc.canFirePentadShot()) {
                    rc.firePentadShot(myLocation.directionTo(visibleEnemies[0].getLocation()));
                }
                if (rc.canFireSingleShot()) {
                    rc.fireSingleShot(myLocation.directionTo(visibleEnemies[0].getLocation()));
                }

            } else {
                if (rc.readBroadcast(BROADCAST_ENEMY_ARCHONS_LOCATION) > 0){
                    tanktryMove(myLocation.directionTo(mapLocationFromInt(rc.readBroadcast(BROADCAST_ENEMY_ARCHONS_LOCATION))));
                }
                if (rc.readBroadcast(BROADCAST_ENEMY_ARCHONS_DEAD) == 1) {
                    tanktryMove(randomDirection());
                } else {
                    tanktryMove(myLocation.directionTo(EnemyInitialArchonLocations[0]));
                }
            }
        }

    }
}
