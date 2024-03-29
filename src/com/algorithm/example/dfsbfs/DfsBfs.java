package com.algorithm.example.dfsbfs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/*
    최단경로 찾을때는
        - BFS ( Breadth-First-Search)

    전체 탐색 일때는
        - DFS ( Depth - First Search)
 */
class DfsBfs<E> {

    //공통 인접 노드 정보를 담는 맵
    private Map<E, List<E>> adjacentMap = new LinkedHashMap<>();
    //공통 방문 리스트
    private List<E> visitedList = new LinkedList<>();
    //bfs queue
    private Queue<E> queue = new LinkedList<>();
    //dfs stack
    private Stack<E> stack = new Stack<>();
    //result List
    private List<E> result = new ArrayList<>();

    void addEdge(E key, E adjacent) {
        //키값이 존재하면 기존의 값에 append, 존재 하지 않으면 새로 put
        if(adjacentMap.containsKey(key)){
            adjacentMap.get(key).add(adjacent);
        }else{
            adjacentMap.put(key, new LinkedList<>(){{
               add(adjacent);
            }});
        }
    }

    // dfs function - recursion
    void dfsRecursion(E key) {
        // 방문 추가
        visitedList.add(key);
        // 결과리스트 추가
        result.add(key);

        // recursion loop
        for (E nKey : adjacentMap.get(key)) {
            if (!visitedList.contains(nKey)) {
                dfsRecursion(nKey);
            }
        }
    }
    // dfs function - stack
    void dfsStack(E key){
        stack.push(key);        // Stack 추가
        //visitedList.add(key);   // 방문 추가

        // stack loop
        while(!stack.empty()){
            E stackKey = stack.pop();
            // 결과 list 추가
            result.add(stackKey);

            // list loop
            for(E loopKey : adjacentMap.get(stackKey)){
                if(!visitedList.contains(loopKey)){
                    visitedList.add(loopKey);
                    stack.push(loopKey);
                }
            }
        }
    }


    // bfs function
    void bfs(E key){
        queue.add(key);         // 큐 추가
   //     visitedList.add(key);   // 방문 추가

        // queue loop
        while(queue.size() != 0){
            E queueKey = queue.poll();
            //결과 list 추가
            result.add(queueKey);

            // list loop
            for (E loopKey : adjacentMap.get(queueKey)) {
                if (!visitedList.contains(loopKey)) {
                    visitedList.add(loopKey);
                    queue.add(loopKey);
                }
            }
        }
    }

    void init(){
        visitedList.clear();
        result.clear();
    }

    List<E> getResult(){
        return this.result;
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
        DfsBfs<String> dfsBfs = new DfsBfs<>();
        // 노드 설정
        dfsBfs.addEdge("A", "F");
        dfsBfs.addEdge("A", "D");
        dfsBfs.addEdge("A", "C");
        dfsBfs.addEdge("B", "B");
        dfsBfs.addEdge("C", "B");
        dfsBfs.addEdge("D", "D");
        dfsBfs.addEdge("E", "E");
        dfsBfs.addEdge("F", "E");
        dfsBfs.dfsRecursion("A");

        // Run DFS - Recursion
        System.out.println(dfsBfs.getResult()); // [A, F, E, D, C, B]
        dfsBfs.init();

        // Run DFS - Stack
        dfsBfs.dfsStack("A");
        System.out.println(dfsBfs.getResult()); // [A, C, B, D, F, E]
        dfsBfs.init();

        // Run BFS - Queue
        dfsBfs.bfs("A");
        System.out.println(dfsBfs.getResult()); // [A, F, D, C, E, B]
    }
}