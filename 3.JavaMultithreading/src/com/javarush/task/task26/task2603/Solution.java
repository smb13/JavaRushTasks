package com.javarush.task.task26.task2603;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/*
Убежденному убеждать других не трудно
*/
public class Solution {
    //public static CustomizedComparator customizedComparator;

    public static class CustomizedComparator<T> implements Comparator<T> {
        private Comparator<T>[] comparators;

        public CustomizedComparator(Comparator... vararg) {
            this.comparators = vararg;
        }

        public int compare(T o1, T o2) {
            int result = 0;
            for (Comparator<T> comp: comparators) {
                result = comp.compare(o1, o2);
                if (result !=0) return result;
            }
            return result;
        }
    }

    public static void main(String[] args) {

    }
}
