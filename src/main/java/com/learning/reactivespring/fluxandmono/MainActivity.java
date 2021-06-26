package com.learning.reactivespring.fluxandmono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainActivity {

    static List<String> data = Arrays.asList("Jon","Doe","Alan","Jon");
    public static void main(String[] args) {

        /** Method -1 **/
        System.out.println("M1 Data");
        List<String> m1data = new ArrayList<>();
        for(String s:data){
            m1data.add(s.toUpperCase());
        }
        System.out.println(m1data);

        /** Method -2 **/
        System.out.println("M2 Data");
        List<String> m2data = data.stream()
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(m2data);

        /** Method -3 **/
        System.out.println("M3 Data");
        Set<String> m3data = data.stream()
                .map(s -> s.toUpperCase())
                .collect(Collectors.toSet());

        System.out.println(m3data);
    }
}
