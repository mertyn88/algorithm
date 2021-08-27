package com.algorithm.leetcode.jumpgame;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    /*
        BFS..?

        대상값으로 타고 가면서 마지막 값에 도달을 한번이라도 하면 return
        실패하면 해당값을 -1하면서 0이 될때까지 수행

        같은 방문일수 있으니 체크하는부분 필요할듯

     */

    private int[] nums;

    public boolean canJump(int[] nums) {
        this.nums = nums;

        //첫번째 값 설정
        int num = this.nums[0];
        // 시작값이 0일 경우 처리
        if(num == 0) {
            return this.nums.length == 1;
        }
        // 개수가 1일 경우 처리
        if(this.nums.length == 1){
            return true;
        }

        do{
            if(bfsSearch(num, 0, 0, 0)){
                return true;
            }
            num--;
            System.out.println(num);
        }while (num > 0);

        return false;
    }

    private boolean bfsSearch(int num, int idx, int sum, int prev) {
        System.out.println(num + " " + idx + " " + sum);


        Queue<Integer> queue = new LinkedList<>();
        queue.add(num);

        // 방문 배열
        boolean[] isVisit = new boolean[nums.length];
        //isVisit[idx] = true;

        //int sum = 0;
        //int prev = 0;

        while(queue.size() > 0) {
            int value = queue.poll();

            if(num < 0){
                return false;
            }

            //합
            sum += value;
            isVisit[idx] = true;

            if(value == 0) {
                return bfsSearch(num - 1, sum - prev, sum - prev, prev);
            }else if(sum >= nums.length - 1){
                return true;
            }else{
                if(!isVisit[sum]){
                    queue.add(nums[sum]);
                }
                prev = value;
                idx = sum;
            }
        }

        return false;
    }
}
