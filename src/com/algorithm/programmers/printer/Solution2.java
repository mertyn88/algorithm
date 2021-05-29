package com.algorithm.programmers.printer;

import java.util.LinkedList;
import java.util.Queue;

class Solution2 {
    private Queue<Integer> queue = null;
    private Integer prioritiesSize = null;
    private Integer printNodeCount = null;
    public int solution(int[] priorities, int location) {
        queue = new LinkedList<>();
        prioritiesSize = priorities.length - 1;
        printNodeCount = 0;
        for(int value : priorities){
            queue.add(value);
        }

        return recursion(location, prioritiesSize);
    }

    public int recursion(int location, int loopCount){
        if(loopCount == 0) {
            return location + 1;
        }
        //System.out.println(queue);

        int nowValue = queue.remove();
        int maxValue = queue.stream().max(Integer::compareTo).get();

        if(nowValue < maxValue){
            // 위치 변동
            queue.add(nowValue);
            // location 값 조절
            if(printNodeCount == location){
                location = prioritiesSize;
            }else{
                location--;
            }
            return recursion(location, loopCount); //다시 호출
        } else{
            printNodeCount++;
            // 로케이션 0일때는 return 추가
            // 혹시 앞단에서 출력된 노드가 있다면 최후에는 그 출력 노드수 만큼 더해줘야한다.
            return recursion(location, --loopCount); //다시 호출
        }
    }
}