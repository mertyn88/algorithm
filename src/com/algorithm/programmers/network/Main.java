package com.algorithm.programmers.network;

public class Main {

    public static void main(String[] args){
        Solution solution = new Solution();
        //3	[[1, 1, 0], [1, 1, 0], [0, 0, 1]]
        System.out.println(solution.solution(3, new int[][]{{1,1,0},{1,1,0},{0,0,1}}));
        // 3 [[1, 1, 0], [1, 1, 1], [0, 1, 1]]
        //System.out.println(solution.solution(3, new int[][]{{1,1,0},{1,1,1},{0,1,1}}));
        //6, [[1, 0, 1, 1, 0, 0], [0, 1, 0, 0, 1, 1], [1, 0, 1, 1, 1, 1], [1, 0, 1, 1, 1, 1], [0, 1, 1, 1, 1, 1], [0, 1, 1, 1, 1, 1]]
        //System.out.println(solution.solution(6, new int[][]{{1, 0, 1, 1, 0, 0}, {0, 1, 0, 0, 1, 1}, {1, 0, 1, 1, 1, 1}, {1, 0, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}}));
        //3 [[1, 0, 0], [0, 1, 0], [0, 0, 1]]

        /*
            3, {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}                            3

            1, {{1}}                                                        1

            4, {{1,1,1,1}, {1,1,1,0}, {1,1,1,0}, {1,0,0,1}}                 1
         */

        //System.out.println(solution.solution(4, new int[][]{{1,1,1,1}, {1,1,1,0}, {1,1,1,0}, {1,0,0,1}}));
    }
}

