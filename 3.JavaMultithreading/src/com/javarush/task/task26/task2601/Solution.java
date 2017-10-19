package com.javarush.task.task26.task2601;

import javax.print.attribute.IntegerSyntax;
import java.lang.reflect.Array;
import java.util.*;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {13, 8, 15, 5, 17, 10};
        arr = sort(arr);
    }

    public static Integer[] sort(Integer[] array) {
        Double mediana;
        Arrays.sort(array);
        if (array.length % 2 == 1) {
            mediana = new Double(array[array.length / 2]);
        } else {
            mediana = (new Double(array[array.length / 2]) + new Double(array[array.length / 2 - 1])) / 2;
        }
        Comparator<Integer> medianaComp = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return (int)(Math.abs(o1 - mediana) - Math.abs(o2 - mediana));
            }
        };
        List<Integer> arrList = Arrays.asList(array);
        Collections.sort(arrList, medianaComp);
        array = arrList.toArray(new Integer[arrList.size()]);
        return array;
    }
}
