package com.algorithm.programmers.printer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    private Queue<String> queueStr = new LinkedList<>();      // 큐 생성
    public int solution(int[] priorities, int location) {
        String indexValue = ""; // 마지막 List의 위치값을 가져올 변수
        for(int idx = 0; idx < priorities.length; idx++){
            if(idx == location){
                indexValue = priorities[idx]+"#mark";
                queueStr.add(indexValue);   // 로테이션 값 마킹
            }else{
                queueStr.add(priorities[idx]+"");
            }
        }

        return new ArrayList<String>(){{
            addAll(recursion(new ArrayList<>(), priorities.length - 1));    // 정렬값
            addAll(queueStr);   // 정렬되지 않는 마지막 큐값 설정
        }}.indexOf(indexValue) + 1; // 결과값은 1부터 시작이므로 +1
    }

    public List<String> recursion(List<String> resultList, int loopCount){
        if(loopCount != 0) {
            int nowValue = Integer.parseInt(queueStr.element().replace("#mark",""));    // 현재 값
            int maxValue = queueStr.stream().map(a -> Integer.parseInt(a.replace("#mark","")))  // 큐의 값중 가장 큰값
                .max(Integer::compareTo).get();
            if(nowValue < maxValue){        // 비교, 큰값이 있으면 삭제 후, 마지막 위치로 이동 / 없으면 결과값에 추가
                queueStr.add(queueStr.remove());
                return recursion(resultList, loopCount); // 재귀, 큐의 위치만 변경되므로 loopCount의 값은 변경 없음
            } else{
                resultList.add(queueStr.remove());
                return recursion(resultList, --loopCount); // 재귀, 큐가 실행되었으므로 loopCount의 값은 -1
            }
        }
        return resultList;
    }
}