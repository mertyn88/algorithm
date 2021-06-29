package com.algorithm.programmers.longnode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Solution {


    //공통 인접 노드 정보를 담는 맵
    private Map<Integer, Set<Integer>> adjacentMap = new HashMap<>();
    //공통 방문 리스트 - 시간 이슈 발생
    @Deprecated
    private List<Integer> visitedList = new LinkedList<>();
    // 공통 방문 리스트 - 시간 이슈 해결
    private boolean[] visitArray = null;
    //bfs queue
    private Queue<Integer> queue = new LinkedList<>();

    // 시간초과 이슈 있었던 코드
    @Deprecated
    public void tempSetData(int n, int[][] edge){
        for(int idx = 1; idx <= n; idx++){
            for (int[] arr : edge) {
                if (idx == arr[0]) {
                    addEdge(idx, arr[1]);
                } else if (idx == arr[1]) {
                    addEdge(idx, arr[0]);
                }
            }
        }
    }
    // 시간초과 해결한 코드
    public void setData(int n, int[][] edge){
        for (int[] arr : edge) {
            addEdge(arr[0], arr[1]);
            addEdge(arr[1], arr[0]);
        }
        visitArray = new boolean[n];
    }

    void addEdge(int key, int adjacent) {
        //키값이 존재하면 기존의 값에 append, 존재 하지 않으면 새로 put
        if(adjacentMap.containsKey(key)){
            adjacentMap.get(key).add(adjacent);
        }else{
            adjacentMap.put(key, new HashSet<>(){{
                add(adjacent);
            }});
        }
    }

    public int bfs(){
        int answer = 0;

        // Switching Queue variable
        Queue<Integer> tempQueue = new LinkedList<>();
        // queue loop
        while(queue.size() != 0){
            int queueKey = queue.poll();

            // adjacent list loop
            for (int loopKey : adjacentMap.get(queueKey)) {
               /*
                시간 이슈 코드
                if (!visitedList.contains(loopKey)) {
                    visitedList.add(loopKey);
                    tempQueue.add(loopKey);
                }
                */
                // 시간 이슈 해결 코드
                if (!visitArray[loopKey - 1]) {
                    visitArray[loopKey - 1] = true;
                    tempQueue.add(loopKey);
                }
            }

            if(queue.size() == 0){
                //뎁스별 노드 삽입
                if(tempQueue.size() != 0){
                    answer = tempQueue.size();  // 마지막 tempQueue 사이즈가 마지막 뎁스의 노드 개수

                    queue.addAll(tempQueue);
                    tempQueue.clear();
                }
            }
        }

        return answer;
    }

    public int solution(int n, int[][] edge) {
        ////6   [[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]
        // 시간초과 이슈
        //tempSetData(n, edge);
        setData(n, edge);

        //visitedList.add(1);
        visitArray[0] = true;
        queue.add(1);         // 큐 추가
        return bfs();
    }
}
