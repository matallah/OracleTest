package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread3 {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG136",
                "UG137",
                "UG141",
                "UG142",
                "UG143",
                "UG144",
                "UG145",
                "UG146",
                "UG147",
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
                "UG161",
                "UG162",
                "UG163");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo( allKp,3).start();
        }
    }
}
