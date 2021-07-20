package com.algorithm.kakao.lock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    /*
        공식적용해보자
        N * (N - 0) - (N - 1)|N * (N - 1) - (N - 1)|N * (N - 2) - (N - 1)
        N * (N - 0) - (N - 2)|N * (N - 1) - (N - 2)|N * (N - 2) - (N - 2)
        N * (N - 0) - (N - 3)|N * (N - 1) - (N - 3)|N * (N - 2) - (N - 3)
     */

    private List<Integer> virtualRealLockList = null;
    private int[][] virtualArr = null;
    private int lockLength = 0;
    private int keyLength = 0;

    public boolean solution(int[][] key, int[][] lock) {
        // Set virtual extends lock arr
        setVirtualLock(lock);

        // 배열 크기 설정
        lockLength = virtualArr.length;
        keyLength = key.length;

        int idx = 0;                    // rotation 순서 idx
        int[][] keyClone = key.clone(); // key는 loop마다 변화하므로 초기값 clone

        // 가상 자물쇠 배열중, 값이 0(홈)인 배열의 인덱스 패턴 List 추출
        List<Integer> lockPattern = patternArray(virtualArr, false);
        // 키 배열중, 값이 1(돌기)인 배열의 인덱스 패턴 List 추출
        // 최초는 rotation을 하지 않고 체크해야 하므로 do-while사용
        List<Integer> keyPattern = patternArray(keyClone, true);

        // 특정 조건 validation
        if(lockPattern.size() == 0){
            // 애초에 비어있는 홈이 없기때문에 의미 없다.
            return true;
        }else if(keyPattern.size() == 0){
            // 채울 키가 없기때문에 의미없다.
            return false;
        }else if(lockPattern.size() > keyPattern.size()){
            // 다 채울수 없기 때문에 의미 없다...
            return false;
        }

        do{
            //key pattern 체크
            if(!checkPattern(keyPattern, lockPattern, key.length)){
                keyClone = rotation(keyClone);
                keyPattern = patternArray(keyClone, true);
            }else{
                return true;
            }
        }while(idx++ < 3);

        return false;
    }

    public void setVirtualLock(int[][] lock){
        // 자물쇠의 크기를 확장하는데 자물쇠의 크기와 키의 크기가 같을 경우, 앞뒤로 키의 배열이 올 수 있으므로 총 3배
        // 실제 크기
        int length = lock.length;
        // 관련 변수 초기화
        virtualArr = new int[length*3][length*3];
        virtualRealLockList = new ArrayList<>();

        // 가상 배열 적용 loop
        int count = 0;
        for(int i = 0; i < virtualArr.length; i++){
            for(int j = 0; j < virtualArr.length; j++){
                // 가상 배열 범위중, 실제 lock 배열의 값을 가운데에다가 설정
                // 조건은 (0,0) ~ (length-1, length-1)이 만들어지는 실제 범위 이므로 (length, length) ~ (length * 2 -1, length * 2 -1)로 변환한다.
                if(i >= length && j >= length && i <= (length * 2) -1 && j <= (length * 2) -1){
                    virtualArr[i][j] = lock[i-length][j-length];
                    virtualRealLockList.add(count);
                }else{
                    virtualArr[i][j] = 1;
                }
                count++;
            }
        }
    }

    public int[][] rotation(int[][] arr){
        // 2차원 배열을 1차원 배열로 변환한다.
        int[] flatArr = Arrays.stream(arr).flatMapToInt(Arrays::stream).toArray();

        // loop 관련 변수
        int size = arr.length;
        int column = 0;
        int row = 0;

        // 임시 1차원 배열
        int[] tempArr = new int[size * size];
        for(int idx = 0; idx < flatArr.length; idx++){
            // 로테이션 위치값 - 공식에 의한 인덱스값을 찾는다.
            int locationValue = size * (size - row) - (size - column);
            // 인덱스값을 1차원으로 펼친 flatArr에서 값을 임시 1차원 배열에 할당한다.
            tempArr[idx] = flatArr[locationValue];
            if( row == (size - 1)){
                row = 0;
                column++;
            }else{
                row++;
            }
        }

        // 1차원 temp 배열을 2차원 result 배열로 변환
        int[][] result = new int[size][size];
        row = 0;
        column = 0;
        for (int value : tempArr) {
            result[column][row] = value;
            if ((size - 1) == row) {
                row = 0;
                column++;
            } else {
                row++;
            }
        }
        return result;
    }

    public List<Integer> patternArray(int[][] arr, boolean isFind) {
        List<Integer> targetList = new ArrayList<>();
        int basePoint = isFind ? 1 : 0; // 자물쇠는 돌기인 0, 키는 홈인 1값을 찾아야 하므로 구분
        int count = 0;
        for (int[] ints : arr) {    // loop를 통해서 필요한 패턴의 인덱스 값을 찾는다.
            for (int anInt : ints) {
                if (anInt == basePoint) {
                    targetList.add(count);
                }
                count++;
            }
        }
        return targetList;
    }

    private boolean diffList(List<Integer> keyList, List<Integer> lockList){
        // 실제 범위이외의 데이터는 필터링 처리 되었기 때문에 두 리스트의 사이즈는 같으며 포함되어야 한다.
        // 단순 contains 사용시, 패턴에서 자물쇠와 키가 둘다 1인 케이스가 있어서 테스트 케이스 3개가 실패한다.
        // 참고) 실제 정답에서는 XOR처리라고 되어있다.
        if(keyList.size() == lockList.size()){
            return lockList.containsAll(keyList);
        }
        return false;
    }

    private boolean checkPattern(List<Integer> keyList, List<Integer> lockList, int keySize){
        // 모든 패턴 일치 탐색
        // 하나의 lock 패턴에 각각의 key 패턴들을 기준점으로 삼아서 loop 하면서  비교한다.
        for(int i = 0; i < lockList.size(); i++){
            for(int idx = 0; idx < keyList.size(); idx++){
                if(diffList(findPattern(keyList, lockList.get(i), idx), lockList)){
                    return true;
                }
            }
        }
        return false;
    }

    private List<Integer> findPattern(List<Integer> keyList, int standardLockNo, int idx){
        List<Integer> resultList = new ArrayList<>();
        // lock 패턴의 기준점 계산, key 패턴이 loop하면서 해당 값이 loop 패턴의 값이 되도록 하기 위해
        int standardPoint = standardLockNo - keyList.get(idx);
        // key 패턴의 값이 key 배열의 몇번째 row에 있는지 계산하기 위해
        int standardRow = keyList.get(idx) / keyLength;
        // key 패턴 loop
        for(int keyNo : keyList){
            // 기본값, 각 기준에 해당하는 key 패턴은 해당 lock 패턴의 값과 일치하게 나와야 한다.
            int basePoint = keyNo + standardPoint;
            // lockLength - keyLength -> 배열 크기가 다른 lock과 key배열의 차이
            // (keyNo / keyLength) - standardRow -> key 패턴의 값이 실제 어느 열에 존재하는지
            // lock 패턴과 key 패턴의 크기가 다를때, 같은 패턴의 모양을 만들어주기 위해 크기가 차이나는 만큼 인덱스 값을 더해서 만든다.
            int plusPoint = (lockLength - keyLength) * ((keyNo / keyLength) - standardRow);
            // basePoint와 plusPoint의 합으로 최종적인 virtual lock pattern의 위치 값을 찾는다.
            int newPoint = basePoint + plusPoint;

            // 필터링 - 확장되었지만 실제로는 확정 전의 lock 패턴에 값이 존재하는 key의 값만이 대상이 되므로
            // 해당 범위가 아닌 point값은 lock의 실제범위를 벗어나는 값이다.
            if(virtualRealLockList.contains(newPoint)){
                resultList.add(newPoint);
            }
        }
        return resultList;
    }
}
