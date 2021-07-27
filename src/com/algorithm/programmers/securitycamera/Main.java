package com.algorithm.programmers.securitycamera;

public class Main {
    public static void main(String[] args){
        Solution solution = new Solution();
        //[[-20,15], [-14,-5], [-18,-13], [-5,-3]]
/*        System.out.println(
            solution.solution(new int[][]{{-20,15}, {-14,-5}, {-18,-13}, {-5,-3}})
        );*/

        //[[-100,100],[50,170],[150,200],[-50,-10],[10,20],[30,40]]
        System.out.println(
            solution.solution(new int[][]{{-100,100}, {50,170}, {150,200},{-50,-10}, {10,20}, {30,40}})
        );
    }
}
