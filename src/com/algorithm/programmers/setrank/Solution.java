package com.algorithm.programmers.setrank;

import java.util.*;
import java.util.stream.IntStream;

class Solution {

    /*
        https://programmers.co.kr/learn/courses/30/lessons/49191?language=java
        5	[[4, 3], [4, 2], [3, 2], [1, 2], [2, 5]]	2

        [1차] 노드별 승리 데이터 셋
        1 = [2]
        2 = [5]
        3 = [2]
        4 = [3,2]
        5 = []

        [1차] 노드별 패배 데이터 셋
        1 = []
        2 = [4,3,1]
        3 = [4]
        4 = []
        5 = [2]

        [2차] 노드별 승리 데이터셋을 기준으로 가상 승리 노드셋 계산 ( 자기 자신은 항상 포함, 괄호는 가상으로 만들어진 노드셋 )
        1 = [1, 2, (5)]
        2 = [2, 5]
        3 = [2, (3), (5)]
        4 = [(2), (3), 4, 5]
        5 = [5]

        [2차] 노드별 패배 데이터셋을 기준으로 가상 패배 노드셋 계산 ( 자기 자신은 항상 포함, 괄호는 가상으로 만들어진 노드셋 )
        1 = [1]
        2 = [1, 2, 3, 4]
        3 = [3, 4]
        4 = [4]
        5 = [(1), 2, (3), (4), 5]

        [3차] 승리 데이터 셋과 패배 데이터 셋을 합쳤을 경우 노드셋 계산
        1 = [1, 2, (5)]
        2 = [1, 2, 3, 4, 5]
        3 = [2, (3), 4, (5)]
        4 = [(2), (3), 4, 5]
        5 = [(1), 2, (3), (4), 5]

        모든 경치를 치룬 ( 가상 노드 결과 포함 ) 노드
        2, 5 -> 2
     */
    private Map<Integer, Set<Integer>> winnerMap = new HashMap<>();     // 승리데이터셋
    private Map<Integer, Set<Integer>> loserMap = new HashMap<>();      // 패배데이터셋
    private Map<Integer, Set<Integer>> mergeMap = new HashMap<>();      // 승패 데이터 셋
    private int nodeCount = 0;                                          // 노드 개수

    public void bfs(Integer node, Map<Integer, Set<Integer>> scoreMap){
        Set<Integer> tempSet = new HashSet<>();                     // BFS 내에서 사용되는 임시 셋
        boolean[] isVisit = new boolean[nodeCount];                 // 방문 배열 초기화 (예전 속도 이슈로 방문리스트 -> 방문 배열로 변경)
        Queue<Integer> queue = new LinkedList<>(){{add(node);}};    // BFS 큐

        //Queue loop
        while(!queue.isEmpty()){
            int queueNode = queue.poll();
            // 노드 추가
            tempSet.add(queueNode);
            for(int iNode : scoreMap.get(queueNode)){
                // 방문 체크
                if(!isVisit[iNode - 1]){
                    isVisit[iNode - 1] = true; //방문 추가
                    queue.add(iNode);
                }
            }
        }
        scoreMap.put(node, tempSet);            // 승리 또는 패배 데이터 셋 신규 데이터 추가
    }

    public int solution(int n, int[][] results) {
        // 노드 개수 할당
        nodeCount = n;
        // 맵 초기화 (주어진 데이터셋을 모두 가지기 위함)
        IntStream.rangeClosed(1, nodeCount).forEach(node -> {
            winnerMap.put(node, new HashSet<>());
            loserMap.put(node, new HashSet<>());
        });

        // [1차] 승리, 패배 데이터 셋 구축
        for(int[] arr : results){
            //승리 arr[0] 패매 arr[1]
            winnerMap.merge(arr[0], new HashSet<>(){{add(arr[1]);}}, (e, i) -> new HashSet<>(){{addAll(e);addAll(i);}});
            loserMap.merge(arr[1], new HashSet<>(){{add(arr[0]);}}, (e, i) -> new HashSet<>(){{addAll(e);addAll(i);}});
        }

        // [2차] 승리, 패배 가상 데이터 셋 구축 ( 각자 for 문에서 리팩토링 )
        for(Map scoreMap : new Map[]{winnerMap, loserMap}){
           for(Object  key : scoreMap.keySet()){
               bfs((Integer)key, scoreMap);
           }
        }

        // [3차] 승리데이터 와 패배 데이터를 머지
        IntStream.rangeClosed(1, nodeCount).forEach(node ->
                mergeMap.put(node, new HashSet<>(){{addAll(winnerMap.get(node));addAll(loserMap.get(node));}}));

        // 승리데이터셋과 패배데이터셋을 합쳤을때, 모든 노드들이 표현되는 노드 개수 반환
        return (int)mergeMap.values().stream().filter(value -> value.size() == n).count();
    }
}