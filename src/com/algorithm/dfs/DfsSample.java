package com.algorithm.dfs;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class DfsSample {
    // Adjacency List Representation
    private Map<String, List<String>> adjacentMap = new LinkedHashMap<>();
    private List<String> visitedList = new LinkedList<>();

    // Function to add an edge into the graph
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

    // A function used by DFS
    void dfs(String key) {
        // Mark the current node as visited and print it
        visitedList.add(key);
        System.out.print(key + " ");

        //다음 키값
        for (String nKey : adjacentMap.get(key)) {
            if (!visitedList.contains(nKey)) {
                dfs(nKey);
            }
        }
    }

    public static void main(String args[]) {
        DfsSample dfsSample = new DfsSample();     // 노드 개수
        dfsSample.addEdge("A", "F");
        dfsSample.addEdge("A", "E");
        dfsSample.addEdge("A", "D");
        dfsSample.addEdge("A", "C");
        dfsSample.addEdge("A", "B");

        dfsSample.addEdge("B", "B");
        dfsSample.addEdge("C", "B");
        dfsSample.addEdge("D", "D");
        dfsSample.addEdge("E", "E");
        dfsSample.addEdge("F", "E");
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
        dfsSample.dfs("A");
    }
}
// This code is contributed by Aakash Hasija