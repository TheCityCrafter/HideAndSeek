package de.thecitycrafter.hideAndSeek.utils;

import java.util.ArrayList;
import java.util.List;

public class secondsToTimer {

    public static List<Integer> secondsToMin(int seconds){
        int endSec = seconds/60;
        int endMin = seconds%60;
        List<Integer> value = new ArrayList<>();
        value.add(endMin);
        value.add(endSec);
        return value;
    }
}
