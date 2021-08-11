package com.algorithm.hackerrank.bomberman;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Result {

    /*
     * Complete the 'bomberMan' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING_ARRAY grid
     *
     *
     *
            .......
            ...O.O.
            ....O..
            ..O....
            OO...OO
            OO.O...

            OOO.O.O
            OO.....
            OO....O
            .......
            .......
            .......

            .......
            ...O.O.
            ...OO..
            ..OOOO.
            OOOOOOO
            OOOOOOO
     *
     *
     */
    private static List<Integer> getIndex(String str) {
        List<Integer> result = new ArrayList<>();
        if(str.contains("O")){
            int i = str.indexOf("O");
            result.add(i);
            while(i >= 0) {
                i = str.indexOf("O", i+1);
                if(i > 0){
                    result.add(i);
                }
            }
        }
        return result;
    }

    public static String replaceValue(String str, int idx) {
        String[] tempArr = str.split("");
        tempArr[idx] = ".";
        return String.join("",tempArr);
    }

    public static List<String> fillBomber(List<String> grid){
        return grid.stream().map(a -> a.replace(".", "O")).collect(Collectors.toList());
    }

    public static List<String> setIndex(List<String> grid, int column , int row) {
        // 폭탄 위치값 조회 및 설정
        Map<Integer, List<Integer>> indexMap = new HashMap<>();
        for(int idx = 0; idx < grid.size(); idx++){
            indexMap.put(idx, getIndex(grid.get(idx)));
        }

        // 폭탄 폭발
        List<String> convertGrid = new ArrayList<>(fillBomber(grid));
        for(Integer key : indexMap.keySet()){
            if(!indexMap.get(key).isEmpty()){

                for(Integer val : indexMap.get(key)){
                    //4방향 폭탄
                    // up validation & set
                    if(key != 0){
                        convertGrid.set(key - 1, replaceValue(convertGrid.get(key - 1), val));
                    }
                    // down validation & set
                    if(key != row - 1){
                        convertGrid.set(key + 1, replaceValue(convertGrid.get(key + 1), val));
                    }
                    // left validation & set
                    if(val != 0){
                        convertGrid.set(key, replaceValue(convertGrid.get(key), val - 1));
                    }
                    // right validation & set
                    if(val != column - 1){
                        convertGrid.set(key, replaceValue(convertGrid.get(key), val + 1));
                    }
                    // center set
                    convertGrid.set(key, replaceValue(convertGrid.get(key), val));
                }
            }
        }

        for(String line : convertGrid){
            System.out.println(line);
        }

        return convertGrid;
    }

    public static List<String> bomberMan(int time, int column, int row, List<String> grid) {
        // Write your code here
        List<String> result;
        if(time % 2 == 0){
            result = fillBomber(grid);
        }else{
            // 1   5   9    13    17
            //   3   7   11    15
            if(time == 1){
                result = grid;
            }else {
                // 나머지값이 3인경우는 폭탄이 1번 터진경우 ( 즉 폭탄이 초기랑 다르게 터진모양인 경우 )
                result = setIndex(grid, column, row);
                System.out.println();
                if(time % 4 == 1){
                    // 나머지값이 1인경우는 폭탄이 2번 터진경우
                    result = setIndex(result, column, row);
                }
            }

        }
        return result;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        String path = "/Users/junmyung/IdeaProjects/Algorithm/src/com/algorithm/hackerrank/bomberman/test_case.txt";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r = Integer.parseInt(firstMultipleInput[0]);    // row

        int c = Integer.parseInt(firstMultipleInput[1]);    // column

        int n = Integer.parseInt(firstMultipleInput[2]);    // time

        List<String> grid = IntStream.range(0, r).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        List<String> result = Result.bomberMan(n,c,r, grid);

        /*bufferedWriter.write(
            result.stream()
                .collect(joining("\n"))
            + "\n"
        );*/

        bufferedReader.close();
        //bufferedWriter.close();
    }
}
