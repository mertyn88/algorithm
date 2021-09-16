package com.algorithm.leetcode.predictthewinner;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public boolean PredictTheWinner(int[] nums) {

        int left = 0;
        int right = nums.length - 1;

        List<Integer> player1 = new ArrayList<>();
        List<Integer> player2 = new ArrayList<>();

        //int player1 = 0;
        //int player2 = 0;


        boolean isSwitch = true;
        while(left != right) {

            //System.out.println("left : " + left + " " + "right : " + right);

            int leftVal = nums[left] - nums[left + 1];
            int rightVal = nums[right] - nums[right - 1];

            if(leftVal >= rightVal){
                //System.out.println("left");
                if(isSwitch){
                    // p1
                    player1.add(nums[left]);
                    //player1 += nums[left];
                }else{
                    // p2
                    player2.add(nums[left]);
                    //player2 += nums[left];
                }
                left++;
            }else{
                //System.out.println("right");
                if(isSwitch){
                    // p1
                    player1.add(nums[right]);
                    //player1 += nums[right];
                }else{
                    // p2
                    player2.add(nums[right]);
                    //player2 += nums[right];
                }
                right--;
            }

            isSwitch = !isSwitch;
        }

        if(isSwitch){
            player1.add(nums[right]);
            //player1 += nums[right];
        }else{
            player2.add(nums[right]);
            //player2 += nums[right];
        }

        //System.out.println(player1);
        //System.out.println(player2);

        System.out.println(player1);
        System.out.println(player2);

        //return player1 >= player2;

        return true;
    }
}
