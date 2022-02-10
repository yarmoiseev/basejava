package com.yarmoiseev.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;


public class MainStreams {
    static int[] a1 = new int[]{1, 2, 3, 3, 2, 3};
    static int[] a2 = new int[]{9, 8};
    static int[] a3 = new int[]{0, 1, 2, 3, 9, 5, 6, 7, 4, 8, 5, 8, 6, 7, 7};

    static List<Integer> even = Arrays.asList(1, 2, 3, 3, 2, 3);
    static List<Integer> odd = Arrays.asList(9, 8);

    public static void main(String[] args) {
        System.out.println("------------ Task 1 ------------");
        System.out.println(minValue(a1));
        System.out.println(minValue(a2));
        System.out.println(minValue(a3));
        System.out.println("------------ Task 2 ------------");
        System.out.println(oddOrEven(even));
        System.out.println(oddOrEven(odd));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(partitioningBy(x -> x % 2 == 0, toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
