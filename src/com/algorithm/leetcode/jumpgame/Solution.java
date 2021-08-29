package com.algorithm.leetcode.jumpgame;

public class Solution {

    /*
        문제 주요 확인 점
         - i + nums[i]의 값이 다음 체크해야할 인덱스 값이다.
         - 어떤 짓거리를 해도 nums의 인덱스를 순차적으로 돌면서 그 값의 최대값으로만 비교한다.
            - 숫자가 변경되어서 인덱스를 차감하면서 진행해도 그 범위는 결국 nums의 범위 이므로
     */


    public boolean canJump(int[] nums) {
        // nums가 한글자인 경우 0이든 0초과이든 무조건 true
        if(nums.length == 1){
            return true;
        }

        int maxIdx = Integer.MIN_VALUE;        // 인덱스당 최대값 설정
        int lastIdx = nums.length - 1;         // 목표 인덱스 값 설정
        for(int i = 0; i < nums.length; i++){
            maxIdx = Math.max(maxIdx, i + nums[i]); // idx + nums[idx]가 점프해야하는 위치값
            if(maxIdx <= i){    // maxIdx의 값이 대상 위치 인덱스보다 작을 경우 더이상 진행이 불가능하다.
                return false;
            }else if(maxIdx >= lastIdx) {   // maxIdx의 값이 루프가 돌기전에 이미 마지막 값을 넘겼다면 통과
                return true;
            }
        }
        return false;
    }
}
