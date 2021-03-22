package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG29",
                "UG30",
                "UG31",
                "UG32",
                "UG33",
                "UG41",
                "UG43",
                "UG45",
                "UG47",
                "UG56",
                "UG58",
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
                "UG84");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo(allKp,1).start();
        }
    }
}
