package com.algorithm.programmers.entryaudit;

import java.util.Arrays;

public class Solution {

    public long solution(int n, int[] times) {
        long divide = (long) Math.ceil((double) n / times.length);
        //정렬
        Arrays.sort(times);
        // 최소값
        long min = times[0] * divide;
        // 최대값
        long max = times[times.length - 1] * divide ;

        return binarySearch(n, min, max, times);
    }

    long binarySearch(int key, long low, long high, int[] times) {
        long result = 0;
        long mid = 0;

        while(low <= high) {
            mid = (low + high) / 2;

            // 심사관 루프
            long sum = 0;
            for(int time : times){
                sum += mid / time;
            }

            //System.out.println(key + " " + sum + " " + mid + " " + low + " " + high);
            if(sum < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
                result = mid;
            }
        }

        return result;
    }
}