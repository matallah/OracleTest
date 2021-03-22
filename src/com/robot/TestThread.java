package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG163",
                "UG164",
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
                "UG180",
                "UG178",
                "UG179",
                "UG181",
                "UG182",
                "UG186",
                "UG184",
                "UG185",
                "UG187",
                "UG188",
                "UG189",
                "UG29",
                "UG30");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo( allKp).start();
        }
    }
}
