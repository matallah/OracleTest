package com.robot;

import java.util.Arrays;
import java.util.List;

public class TestThread5 {
    public static void main(String args[]) {
       Mainkp main = new Mainkp();
//        List<String> allKps = main.findAllKps();
        List<String> allKps = Arrays.asList("UG178","UG142");
        System.out.println(allKps);
        for (String allKp : allKps) {
           new RunnableDemo( allKp).start();
        }
    }
}
