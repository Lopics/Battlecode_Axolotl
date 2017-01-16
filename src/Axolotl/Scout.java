package Axolotl;

import battlecode.common.*;


public class Scout extends General {
    public static void loop() throws GameActionException {
        while (true) {
            try {
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Scout Exception");
                e.printStackTrace();
            }
        }

    }
}
