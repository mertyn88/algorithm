package com.algorithm.programmers.connectisland;

public class Main {
    public static void main(String[] args){
        Solution solution = new Solution();


        /*

        5 [[0, 1, 5], [1, 2, 3], [2, 3, 3], [3, 1, 2], [3, 0, 4], [2, 4, 6], [4, 0, 7]] 15

        4 [[0, 1, 5], [1, 2, 3], [2, 3, 3], [1, 3, 2], [0, 3, 4]] 9
        5 [[0, 1, 1], [3, 1, 1], [0, 2, 2], [0, 3, 2], [0, 4, 100]] 104
        6 [[0, 1, 5], [0, 3, 2], [0, 4, 3], [1, 4, 1], [3, 4, 10], [1, 2, 2], [2, 5, 3], [4, 5, 4]] 11
        5 [[0, 1, 1], [2, 3, 1], [3, 4, 2], [1, 2, 2], [0, 4, 100]] 6

        5 [[0, 1, 1], [0, 2, 2], [0, 3, 3], [0, 4, 4], [1, 3, 1]] 8

        */
        // 5 [[0, 1, 1], [2, 3, 1], [3, 4, 2], [1, 2, 2], [0, 4, 100]] 6

        //System.out.println(solution.solution(5, new int[][]{{0,1,1},{2,3,1},{3,4,2},{1,2,2},{0,4,100}}));

        // 4 [[0, 1, 5], [1, 2, 3], [2, 3, 3], [1, 3, 2], [0, 3, 4]] 9
        System.out.println(solution.solution(4, new int[][]{{0,1,5},{1,2,3},{2,3,3},{1,3,2},{0,3,4}}));
        //5 [[0, 1, 5], [1, 2, 3], [2, 3, 3], [3, 1, 2], [3, 0, 4], [2, 4, 6], [4, 0, 7]] 15
        System.out.println(solution.solution(5, new int[][]{{0,1,5},{1,2,3},{2,3,3},{3,1,2},{3,0,4}, {2,4,6},{4,0,7}}));
        //5 [[0, 1, 1], [3, 1, 1], [0, 2, 2], [0, 3, 2], [0, 4, 100]] 104
        System.out.println(solution.solution(5, new int[][]{{0,1,1},{3,1,1},{0,2,2},{0,3,2},{0,4,100}}));
        //6 [[0, 1, 5], [0, 3, 2], [0, 4, 3], [1, 4, 1], [3, 4, 10], [1, 2, 2], [2, 5, 3], [4, 5, 4]] 11
        System.out.println(solution.solution(6, new int[][]{{0,1,5},{0,3,2},{0,4,3},{1,4,1},{3,4,10}, {1,2,2}, {2,5,3}, {4,5,4}}));
        //4 [[0,1,1],[0,2,2],[1,2,5],[1,3,1],[2,3,8]] 4
        System.out.println(solution.solution(4, new int[][]{{0,1,1},{0,2,2},{1,2,5},{1,3,1},{2,3,8}}));
        //5 [[0, 1, 1], [3, 4, 1], [1, 2, 2], [2, 3, 4]] 8
        System.out.println(solution.solution(5, new int[][]{{0,1,1},{3,4,1},{1,2,2},{2,3,4}}));
        //5 [[0, 1, 1], [0, 4, 5], [2, 4, 1], [2, 3, 1], [3, 4, 1]] 8
        System.out.println(solution.solution(5, new int[][]{{0,1,1},{0, 4, 5},{2,4,1},{2,3,1},{3,4,1}}));
        // [[0,1,1],[0,2,2],[1,2,5],[1,3,3],[2,3,8],[3,4,1]] 7
        System.out.println(solution.solution(5, new int[][]{{0,1,1},{0,2,2},{1,2,5},{1,3,3},{2,3,8},{3,4,1}}));
        //{{0, 1, 1}, {2, 3, 1}, {1, 2, 5}} 7
        System.out.println(solution.solution(4, new int[][]{{0,1,1},{2,3,1},{1,2,5}}));
        // {{1, 0, 1}, {2, 3, 1}, {1, 2, 5}}; // 7;
        System.out.println(solution.solution(4, new int[][]{{1,0,1},{2,3,1},{1,2,5}}));
    }
}
