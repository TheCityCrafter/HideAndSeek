package de.thecitycrafter.hideAndSeek.utils;

import java.util.ArrayList;
import java.util.List;

public class secondsToTimer {

    public static List<Integer> secondsToTimer(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        List<Integer> value = new ArrayList<>();
        value.add(hours);
        value.add(minutes);
        value.add(seconds);
        return value;
    }
}
