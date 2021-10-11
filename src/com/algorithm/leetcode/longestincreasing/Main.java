package com.algorithm.leetcode.longestincreasing;

public class Main {
    public static void main(String[] args) {

        // 1000 이라고 기준점을 정하면?
        // 10,9,2,5,3,7,101,18
        // [990], [991], 998, [995], [997], 993, 899, 982
        //      1      7    -3     2     -4    -4   ..

        Solution solution = new Solution();
        //solution.lengthOfLIS(new int[]{10,9,2,5,3,7,101,18});
        //System.out.println("---");
        //solution.lengthOfLIS(new int[]{0,1,0,3,2,3});
        //System.out.println("---");
        //solution.lengthOfLIS(new int[]{4,10,4,3,8,9}); // 6, 0, [-1, 4, 5] | -6, -7, -2, -1
        //System.out.println("---");
        //solution.lengthOfLIS(new int[]{4,10,4,9,8,3});
        solution.lengthOfLIS(new int[]{1,3,6,7,9,4,10,5,6});
    }
}
