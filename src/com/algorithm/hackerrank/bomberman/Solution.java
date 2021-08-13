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
        1   5   9    13    17
          3   7   11    15
     *
     *
     */

    // Variable
    private static int COLUMN_SIZE = 0;
    private static int ROW_SIZE = 0;
    private static final String SET_BOMB = "O";
    private static final String UNSET_BOMB = ".";

    private static String getUnsetBomb(String str, int idx) {
        String[] tempArr = str.split("");
        tempArr[idx] = UNSET_BOMB;
        return String.join("",tempArr);
    }

    private static void setBomb(int key, int val, List<String> explodeGrid){
        // up validation & set
        if(key != 0){
            explodeGrid.set(key - 1, getUnsetBomb(explodeGrid.get(key - 1), val));
        }
        // down validation & set
        if(key != ROW_SIZE - 1){
            explodeGrid.set(key + 1, getUnsetBomb(explodeGrid.get(key + 1), val));
        }
        // left validation & set
        if(val != 0){
            explodeGrid.set(key, getUnsetBomb(explodeGrid.get(key), val - 1));
        }
        // right validation & set
        if(val != COLUMN_SIZE - 1){
            explodeGrid.set(key, getUnsetBomb(explodeGrid.get(key), val + 1));
        }
        // center set
        explodeGrid.set(key, getUnsetBomb(explodeGrid.get(key), val));
    }

    private static List<Integer> getAddBomb(String str) {
        List<Integer> result = new ArrayList<>();
        int index = str.indexOf(SET_BOMB);
        do{
            result.add(index);
            // Find other bomb
            index = str.indexOf(SET_BOMB, index + 1);
        }while (index > 0);
        return result;
    }

    private static List<Integer> getFindBomb(String str) {
        if(str.contains(SET_BOMB)){
            // Add bomb list
            return getAddBomb(str);
        }
        return new ArrayList<>();
    }

    private static Map<Integer, List<Integer>> getCurrentBombIndex(List<String> grid) {
        Map<Integer, List<Integer>> indexMap = new HashMap<>();
        for(int idx = 0; idx < grid.size(); idx++){
            indexMap.put(idx, getFindBomb(grid.get(idx)));
        }
        return indexMap;
    }

    private static void setExplodeBomb(int key, List<Integer> indexList, List<String> explodeGrid) {
        if(!indexList.isEmpty()){
            for(int index : indexList){
                setBomb(key, index, explodeGrid);
            }
        }
    }

    private static List<String> setFillBomb(List<String> grid){
        return grid.stream().map(line -> line.replace(UNSET_BOMB, SET_BOMB)).collect(Collectors.toList());
    }

    private static List<String> setBombProcess(List<String> grid) {
        // Bomb index map
        Map<Integer, List<Integer>> indexMap = getCurrentBombIndex(grid);

        // Set explode grid
        List<String> explodeGrid = new ArrayList<>(setFillBomb(grid));
        for(Integer key : indexMap.keySet()){
            setExplodeBomb(key, indexMap.get(key), explodeGrid);
        }

        return explodeGrid;
    }

    private static void setSize(int column, int row) {
        // Set Global Variable
        COLUMN_SIZE = column;
        ROW_SIZE = row;
    }

    public static List<String> bomberMan(int time, int column, int row, List<String> grid) {
        // Write your code here
        setSize(column, row);

        // No change grid
        if(time == 1){
            return grid;
        }
        // Only Fill bomb
        if(time % 2 == 0){
            return setFillBomb(grid);
        }
        // Bomb explodes once
        List<String> result = setBombProcess(grid);
        // Bomb explodes twice
        if(time % 4 == 1){
            // Bomb explodes twice
            result = setBombProcess(result);
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
