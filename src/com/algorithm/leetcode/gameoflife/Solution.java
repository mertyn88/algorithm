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
                setCell(board, result, x, y);
            }
        }

        //print(result);

        for(int i = 0; i < sizeX; i++){
            board[i] = result[i].clone();
        }
        print(board);
    }

    private boolean isRangeCheck(int x, int y){
        // x,y 값이 최대길이를 벗어날경우
        if(x > sizeX - 1 || y > sizeY - 1) {
            return false;
        }
        return x >= 0 && y >= 0;
    }

    private void setCell(int[][] board, int[][] result, int x, int y){
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

        int count = 0;
        for(int[] dir : direction) {
            if(isRangeCheck(x + dir[0], y + dir[1])){
               if(board[x + dir[0]][y + dir[1]] == 1){
                   count++;
               }
            }
        }

        // 살아있는 세포라면 이웃카운트가 2 ~ 3개만 허용
        if(board[x][y] == 1 && (count == 2 || count == 3)){
            result[x][y] = 1;
        }else if(board[x][y] == 0 && count == 3){
            // 죽어 있는 세포라면 이웃카운트가 3개일 경우에만 살아남
            result[x][y] = 1;
        }
    }

    private void print(int[][] result) {
        for(int[] a : result){
            for(int b : a){
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }
}
