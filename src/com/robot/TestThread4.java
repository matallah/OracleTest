package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread4 {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG137",
                "UG135",
                "UG136",
                "UG142",
                "UG143",
                "UG144",
                "UG145",
                "UG146",
                "UG147",
                "UG162",
                "UG149",
                "UG150",
                "UG151",
                "UG152",
                "UG153",
                "UG154",
                "UG155",
                "UG156",
                "UG157",
                "UG158",
                "UG160",
                "UG161");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo( allKp).start();
        }
    }
}
