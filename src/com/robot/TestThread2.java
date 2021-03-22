package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread2 {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG31",
                "UG32",
                "UG33",
                "UG43",
                "UG41",
                "UG45",
                "UG47",
                "UG58",
                "UG56",
                "UG61",
                "UG63",
                "UG64",
                "UG65",
                "UG66",
                "UG68",
                "UG69",
                "UG71",
                "UG72",
                "UG74",
                "UG76",
                "UG108",
                "UG84");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo( allKp).start();
        }
    }
}
