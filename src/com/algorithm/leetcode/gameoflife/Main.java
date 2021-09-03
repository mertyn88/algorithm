package com.algorithm.leetcode.gameoflife;

public class Main {
    public static void main(String[] args){
        Solution solution = new Solution();
        solution.gameOfLife(new int[][]{{0,1,0},{0,0,1},{1,1,1},{0,0,0}});
    }
}
