package com.algorithm.dfsbfs;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class DfsBfs {
    //공통 인접 노드 정보를 담는 맵
    private Map<String, List<String>> adjacentMap = new LinkedHashMap<>();
    //공통 방문 리스트
    private List<String> visitedList = new LinkedList<>();
    //bfs queue
    private Queue<String> queue = new LinkedList<>();

    void addEdge(String key, String adjacent) {
        //키값이 존재하면 기존의 값에 append, 존재 하지 않으면 새로 put
        if(adjacentMap.containsKey(key)){
            adjacentMap.get(key).add(adjacent);
        }else{
            adjacentMap.put(key, new LinkedList<>(){{
               add(adjacent);
            }});
        }
    }

    // dfs function
    void dfs(String key) {
        // 방문 추가
        visitedList.add(key);
        // 출력
        System.out.print(key + " ");

        // recursion loop
        for (String nKey : adjacentMap.get(key)) {
            if (!visitedList.contains(nKey)) {
                dfs(nKey);
            }
        }
    }

    // bfs function
    void bfs(String key){
        queue.add(key);         // 큐 추가
        visitedList.add(key);   // 방문 추가

        // queue loop
        while(queue.size() != 0){
            String queueKey = queue.poll();
            System.out.print(queueKey + " ");

            // list loop
            for (String iterKey : adjacentMap.get(queueKey)) {
                if (!visitedList.contains(iterKey)) {
                    visitedList.add(iterKey);
                    queue.add(iterKey);
                }
            }
        }
    }

    void init(){
        visitedList.clear();
    }

    public static void main(String args[]) {
        /*
         *
         * A
         * | \ \
         * F D C
         * |   |
         * E   B
         *
         *  A = [F E], [D], [C, B]
         *  B = X
         *  C = [B]
         *  D = X
         *  E = X
         *  F = [E]
         */
        DfsBfs dfsBfs = new DfsBfs();
        // 노드 설정
        dfsBfs.addEdge("A", "F");
        dfsBfs.addEdge("A", "D");
        dfsBfs.addEdge("A", "C");
        dfsBfs.addEdge("B", "B");
        dfsBfs.addEdge("C", "B");
        dfsBfs.addEdge("D", "D");
        dfsBfs.addEdge("E", "E");
        dfsBfs.addEdge("F", "E");
        dfsBfs.dfs("A");
        dfsBfs.init();

        System.out.println();
        dfsBfs.bfs("A");
    }
}