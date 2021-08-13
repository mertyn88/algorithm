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

    private static void setBomb(int row, int col, List<String> explodeGrid){
        int[][] bombExplodeRange = {{row,col},{row,col+1},{row,col-1},{row+1,col},{row-1,col}};
		
		for(int i=0; i<bombExplodeRange.length; i++){
            int r = bombExplodeRange[i][0];
            int c = bombExplodeRange[i][1];
            unsetBomb(explodeGrid, r, c);
        }
    }
	public static void unsetBomb(List<String> explodeGrid, int r, int c){
        if(r>=0 && r<ROW_SIZE && c>=0 && c<COLUMN_SIZE){
			StringBuilder unsettedRow = new StringBuilder( explodeGrid.get(r) );
			unsettedRow.setCharAt(c, UNSET_BOMB);
			
			explodeGrid.set(r, unsettedRow.toString());
		}
    }
    private static Map<Integer, List<Integer>> getCurrentBombIndex(List<String> grid) {
        Map<Integer, List<Integer>> indexMap = new HashMap<>();
        for(int idx = 0; idx < grid.size(); idx++){
			String bombPlantedRow = grid.get(idx)
			List<Integer> indexes = IntStream.range(0, bombPlantedRow.length())
				.filter(i -> bombPlantedRow.charAt(i) == SET_BOMB).boxed()
				.collect(Collectors.toList());
				
            indexMap.put(idx, indexes);
        }
        return indexMap;
    }

    private static void setExplodeBomb(int key, List<Integer> indexList, List<String> explodeGrid) {
        for(int index : indexList){
			setBomb(key, index, explodeGrid);
		}
    }

    private static List<String> getFillBomb(List<String> grid){
        return grid.stream().map(line -> line.replace(UNSET_BOMB, SET_BOMB)).collect(Collectors.toList());
    }

    private static List<String> getBombProcess(List<String> grid) {
        // Bomb index map
        Map<Integer, List<Integer>> indexMap = getCurrentBombIndex(grid);

        // Set explode grid
        List<String> explodeGrid = new ArrayList<>(getFillBomb(grid));
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
            return getFillBomb(grid);
        }
        // Bomb explodes once
        List<String> result = getBombProcess(grid);
		
        // Bomb explodes twice
        if(time % 4 == 1){
            // Bomb explodes twice
            result = getBombProcess(result);
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
