package com.algorithm.leetcode.longestincreasing;

import java.util.Arrays;

public class Solution {

    public int lengthOfLIS(int[] nums) {
        int[] dpArr = new int[nums.length];
        for(int i = 1; i < nums.length; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    //dpArr[i] = dpArr[j] + 1;
                    dpArr[i] = Math.max(dpArr[i], dpArr[j]  + 1);
                }
                //System.out.print(nums[j] + " ");
            }

            for(int d : dpArr) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
        return Arrays.stream(dpArr).max().getAsInt() + 1;
    }


    public int lengthOfLIS2(int[] nums) {
        //Util.print(nums);

        int loopCount = nums.length;
        int beforeNum = Integer.MAX_VALUE;  // 최대값 할당
        int result = Integer.MIN_VALUE;

        for(int i = 0; i < loopCount; i++){
            int longestCount = 1; // 자기 자신 포함

            // 현재 값이 이전값보다 작아야만 수행하자
            if(nums[i] < beforeNum) {
                int lastIndex = 0;
                int diffValue = nums[i];
                for(int j = i + 1; j < loopCount; j++) {
                    if(diffValue < nums[j]) {
                        System.out.println("\t " + diffValue + " " + nums[j]);
                        longestCount++;
                        diffValue = nums[j];
                        lastIndex = j;
                    }
                }

                System.out.println(nums[i] + " longest value " + longestCount);
                if(longestCount > result) {
                    result = longestCount;
                }

                //loopCount = lastIndex + 1;
                System.out.println("Last Index " + loopCount);

                beforeNum = nums[i];    //이전값 설정

            }else {
                System.out.println("수행하지 않는다 " + nums[i]);
            }


            //loopCount
        }

        return result;
    }

    public int lengthOfLIS3(int[] nums) {
        int result = 1;
        for(int i = 0 ; i < nums.length - 1; i++) {
            if(nums[i + 1] - nums[i] > 0) {
                result++;
            }
        }
        System.out.println(result);
        return result;
    }

}
