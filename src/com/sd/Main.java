package com.sd;

import java.util.LinkedHashMap;
import java.util.Timer;

public class Main {

    public static void main(String[] args) throws Exception {
        new Timer().scheduleAtFixedRate(new SnmpManagerScheduler(new SnmpManager(new LinkedHashMap<>())), 0, 5000);
    }
}