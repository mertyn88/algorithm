package com.algorithm.leetcode.wordbreak;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(
            solution.wordBreak("ddadddbdddadd", List.of("dd","ad","da","b"))
        );

        System.out.println(
             //   solution.wordBreak("cars", List.of("car","ar", "ca","rs"))
        );
    }
}
