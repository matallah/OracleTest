package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread4 {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG164",
                "UG165",
                "UG166",
                "UG167",
                "UG168",
                "UG169",
                "UG170",
                "UG171",
                "UG172",
                "UG173",
                "UG174",
                "UG175",
                "UG176",
                "UG178",
                "UG179",
                "UG180",
                "UG181",
                "UG182",
                "UG184",
                "UG185",
                "UG186",
                "UG187",
                "UG188",
                "UG189");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo( allKp,4).start();
        }
    }
}
