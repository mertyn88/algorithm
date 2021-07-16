package com.algorithm.kakao.lock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Solution {

    /*
        공식적용해보자
        N * (N - 0) - (N - 1)|N * (N - 1) - (N - 1)|N * (N - 2) - (N - 1)
        N * (N - 0) - (N - 2)|N * (N - 1) - (N - 2)|N * (N - 2) - (N - 2)
        N * (N - 0) - (N - 3)|N * (N - 1) - (N - 3)|N * (N - 2) - (N - 3)

        2중 배열을 flat하게 펼친다?
        나와야하는 결과 모양을 찾는다?
        loop?

        lock의 빈 홈의 모양이 같은 구조로 되어있는지? 돌렸을때
        패턴 찾기 인데.. 음
        차장야 하는 패턴을 다시 새로운 사각형을 만들어서 ???


        1. 자물쇠의 빈공간으로 만들어질수 있는 배열 개수가 lock의 배열개수가 가능한지 여부 , 아니면 바로 return false
        2.
     */

    public boolean solution(int[][] key, int[][] lock) {
        // Validation
/*        if(Arrays.stream(key).flatMapToInt(Arrays::stream).filter(a -> a == 1).count() == 0){
            return false;
        }else if(Arrays.stream(lock).flatMapToInt(Arrays::stream).filter(a -> a == 0).count() == 0){
            return true;
        }*/

        int idx = 0;
        int[][] keyClone = key.clone();
        int[] lockPattern = patternArray(lock, false);
        do{
            // key  [[0, 0, 0], [1, 0, 0], [0, 1, 1]]
            // lock [[1, 1, 1], [1, 1, 0], [1, 0, 1]]
            int[] keyPattern = patternArray(keyClone, true);

            // Validation
            // 자물쇠 구멍이 하나면 뭐가 와도 되는거 아냐?
            if(lockPattern.length == 1 && keyPattern.length > 0){
                return true;
            }else if(lockPattern.length == 0){
                return true;
            }else if(lockPattern.length > 0 && keyPattern.length == 0){
                return false;
            }

            int[] isVisit = new int[keyPattern.length];
            for(int lockNo = 0; lockNo < lockPattern.length; lockNo++){
                for(int keyNo = 0; keyNo < keyPattern.length; keyNo++){
                    if(isVisit[keyNo] == 0 && lockPattern[lockNo] == keyPattern[keyNo]){
                        isVisit[keyNo] = 1;
                        break;
                    }
                }
                if(Arrays.stream(isVisit).filter(i -> i == 1).count() == 0){
                    break;
                }
            }

            if(Arrays.stream(isVisit).filter(i -> i == 1).count() >= lockPattern.length){
                return true;
            }else{
                keyClone = rotation(keyClone);
            }
        }while(idx++ < 3);

        return false;
    }

    public int[][] rotation(int[][] arr){
        // 1차원으로 펼쳐놓은것
        int[] flatArr = Arrays.stream(arr).flatMapToInt(Arrays::stream).toArray();

        int size = arr.length;
        int[] tempArr = new int[size * size];
        int column = 0;
        int row = 0;

        for(int idx = 0; idx < flatArr.length; idx++){
            // 로테이션 위치값
            int locationValue = size * (size - row) - (size - column);
            tempArr[idx] = flatArr[locationValue];
            if( row == (size - 1)){
                row = 0;
                column++;
                //System.out.println();
            }else{
                row++;
            }
        }

        int[][] result = new int[size][size];
        row = 0;
        column = 0;
        for(int i = 0; i < tempArr.length; i++){
            result[column][row] = tempArr[i];
            if((size - 1) == row){
                row = 0;
                column++;
            }else{
                row++;
            }
        }

        /*for(int[] a : result){
            for(int b : a){
                System.out.println(b);
            }
        }*/

        return result;
    }

    public int[] patternArray(int[][] arr, boolean isSwitch) {
        int combination = isSwitch ? 1 : 0;

        // N값
        int size = arr.length;
        //결과
        List<Integer> targetList = new ArrayList<>();

        int count = 0;
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                if(arr[i][j] == combination){
                    // 삽입
                    targetList.add(count);
                    //System.out.print("("+count+")");
                }else{
                    //System.out.print(count);
                }
                count++;
            }
            //System.out.println();
        }

        //System.out.println(targetList);
        if(targetList.isEmpty()){
            return new int[0];
        }

        System.out.println(targetList);

        //공식처리
        // resultList가 1개면 필요없음 2개 이여야함
        // X = 다음값 - 이전값 - N
        // X = 8 - 6 - 3 = -1
        //-----------------
        // X = 13 - 9 - 5 = -1
        int[] result = new int[targetList.size()-1];
        IntStream.range(1, targetList.size()).forEach( idx ->
                result[idx-1] = targetList.get(idx) - targetList.get(idx-1) - size
        );

        return result;
    }
}
