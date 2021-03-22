package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread3 {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG86",
                "UG88",
                "UG90",
                "UG93",
                "UG97",
                "UG141",
                "UG100",
                "UG101",
                "UG102",
                "UG103",
                "UG105",
                "UG109",
                "UG111",
                "UG113",
                "UG114",
                "UG115",
                "UG116",
                "UG122",
                "UG127",
                "UG129",
                "UG133",
                "UG131");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo( allKp).start();
        }
    }
}
