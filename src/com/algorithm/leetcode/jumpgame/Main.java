package com.algorithm.leetcode.jumpgame;

public class Main {
    public static void main(String[] args){
        Solution solution = new Solution();

        System.out.println(solution.canJump(new int[]{2,3,1,1,4}));
        System.out.println();
        System.out.println(solution.canJump(new int[]{3,2,1,0,4}));
        System.out.println();
        System.out.println(solution.canJump(new int[]{1}));
        System.out.println();
        System.out.println(solution.canJump(new int[]{0}));
        System.out.println();
        System.out.println(solution.canJump(new int[]{0,2,3}));
        System.out.println();
        System.out.println(solution.canJump(new int[]{1,1,2,2,0,1,1}));
        System.out.println();
    }
}
