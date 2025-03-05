package com.thedev.solarisefixes.utils;

import java.util.Random;

public class ChanceUtil {

    public static boolean doChance(int chance) {
        Random random = new Random();

        return (chance >= random.nextInt(101));
    }
}
