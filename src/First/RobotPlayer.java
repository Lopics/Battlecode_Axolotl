package First;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public strictfp class RobotPlayer {
    static RobotController rc;

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        First.RobotPlayer.rc = rc;


        switch (rc.getType()) {
            case ARCHON:
                Archon();
                break;
            case GARDENER:
                Gardener();
                break;
            case SOLDIER:
                Soldier();
                break;
            case LUMBERJACK:
                Lumberjack();
                break;
        }
    }
