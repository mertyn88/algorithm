package com.algorithm.leetcode.longestincreasing;

import java.util.Arrays;

public class Solution {

    public int lengthOfLIS(int[] nums) {
        int[] dpArr = new int[nums.length];
        for(int i = 1; i < nums.length; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    dpArr[i] = Math.max(dpArr[i], dpArr[j]  + 1);
                }
            }
        }
        return Arrays.stream(dpArr).max().getAsInt() + 1;
    }
}
