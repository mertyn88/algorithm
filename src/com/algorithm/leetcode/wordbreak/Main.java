package com.algorithm.leetcode.wordbreak;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(
          //  solution.wordBreak2("ddadddbdddadd", List.of("dd","ad","da","b"))
        );

        System.out.println(
                solution.wordBreak2("cars", List.of("car","ar", "ca","rs"))
        );
    }
}
