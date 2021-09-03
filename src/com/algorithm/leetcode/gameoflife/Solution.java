package com.algorithm.leetcode.gameoflife;

public class Solution {
    /*
    가운데 셀 기준 이웃의 조건
    x-1,y-1    x-1,y   x-1,y+1
    x,y-1       x,y     x,y+1
    x+1,y-1     x+1,y   x+1,y+1
     */

    int sizeX = 0;
    int sizeY = 0;

    public void gameOfLife(int[][] board) {
        sizeX = board.length;
        sizeY = board[0].length;

        int[][] result = new int[sizeX][sizeY];

        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                if(isRangeCheck(x, y)){

                }
            }
        }



        for(int[] a : result){
            for(int b : a){
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

    private boolean isRangeCheck(int x, int y){
        // x,y 값이 최대길이를 벗어날경우
        if(x > sizeX || y > sizeY) {
            return false;
        }
        return x >= 0 && y >= 0;
    }

    private void setCell(int[][] result, int x, int y){
        // 8방향 정의
        int[][] direction = new int[][]{
            {-1,-1},
            {-1, 0},
            {-1, 1},
            {0, -1},
            {0,  1},
            {1, -1},
            {1,  0},
            {1,  1}
        };

        for(int[] dir : direction) {
            //TODO 주의를 순회하며 1의 카운팅을 찾고 규칙을 적용
            if(isRangeCheck(x + dir[0], y + dir[1])){
            }
        }
    }
}
