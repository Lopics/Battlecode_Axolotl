package Axolotl;

import battlecode.common.*;

public strictfp class RobotPlayer  extends General{

    @SuppressWarnings("unused")
    public static void run(RobotController rc)  throws GameActionException {

        General.init(rc);
        switch (rc.getType()) {
            case ARCHON:
                Archon.loop();
                break;
            case GARDENER:
                Gardener.loop();
                break;
            case SOLDIER:
                Soldier.loop();
                break;
            case LUMBERJACK:
                Lumberjack.loop();
                break;
            case TANK:
                Tank.loop();
                break;
            case SCOUT:
                Scout.loop();
                break;
        }
    }


}
