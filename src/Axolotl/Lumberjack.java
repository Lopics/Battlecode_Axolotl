package Axolotl;

import battlecode.common.*;



public class Lumberjack extends General {

    public static void loop() throws GameActionException {
        while (true) {
            try {
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Lumber Exception");
                e.printStackTrace();
            }

        }

    }




}
