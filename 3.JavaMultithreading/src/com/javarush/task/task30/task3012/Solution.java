package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
        //solution.createExpression(1234);
    }

    public void createExpression(int number) {
        List<String> opers = new ArrayList<>();
        int rest = number;
        while (rest >= 3) {
            int a = rest % 3;
            int b = rest / 3;
            //System.out.println(rest + " " + a + " " + b);
            switch (a) {
                case (2):
                    opers.add(" - ");
                    b++;
                    break;
                case (1):
                    opers.add(" + ");
                    break;
                case (0):
                    opers.add("");
                    break;
            }
            if (b < 3) {
                switch (b) {
                    case (2):
                        opers.add(" - ");
                        opers.add(" + ");
                        break;
                    case (1):
                        opers.add(" + ");
                        break;
                    case (0):
                        opers.add("");
                        break;
                }
            }
            rest = b;
        }
        StringBuffer sb = new StringBuffer(Integer.toString(number) + " =");
        for (int i = 0; i < opers.size(); i++) {
            if (!opers.get(i).equals("")) {
                sb.append(opers.get(i) + Integer.toString((int) Math.pow(3, i)));
            }
        }
        System.out.println(sb);
    }
}
