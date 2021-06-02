package com.algorithm.dfs;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class DfsSample {
    private int V; // No. of vertices

    // Array  of lists for
    // Adjacency List Representation
    private Map<String, List<String>> adjacentList;
    private List<String> visitedMap;

    // Constructor
    DfsSample() {
        adjacentList = new LinkedHashMap<>();
        visitedMap = new LinkedList<>();
       // for (int i = 0; i < v; ++i)
        //    adjacentList[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    void addEdge(String key, String w) {
        if(adjacentList.containsKey(key)){
            adjacentList.get(key).add(w);
        }else{
            adjacentList.put(key, new LinkedList<>(){{
               add(w);
            }});
        }


        adjacentList.get(key).add(w); // Add w to key list.
        //adjacentList[v].add(w);
    }

    // A function used by DFS
    void DFSUtil(String key) {
        // Mark the current node as visited and print it
        visitedMap.add(key);
        //visited[v] = true;
        System.out.print(key + " ");

        // Recur for all the vertices adjacent to this
        // vertex
        Iterator<String> i = adjacentList.get(key).listIterator();
        //System.out.println(i);
        while (i.hasNext()) {
            String nKey = i.next();
          //  System.out.println(nKey);
            if(!visitedMap.contains(nKey)){
                DFSUtil(nKey);
            }
        }
    }

    // The function to do DFS traversal.
    // It uses recursive
    // DFSUtil()
    void DFS(String key) {
        // Mark all the vertices as
        // not visited(set as
        // false by default in java)


        // Call the recursive helper
        // function to print DFS
        // traversal
        DFSUtil(key);
    }

    // Driver Code
    public static void main(String args[]) {
        DfsSample g = new DfsSample();     // 노드 개수

        //g.addEdge("0", "1");
        //g.addEdge("0", "2");
        //g.addEdge("1", "2");
        //g.addEdge("2", "0");
        //g.addEdge("2", "3");
        //g.addEdge("3", "3");

        g.addEdge("A", "F");
        g.addEdge("A", "E");
        g.addEdge("A", "D");
        g.addEdge("A", "C");
        g.addEdge("A", "B");

        g.addEdge("B", "B");

        g.addEdge("C", "B");

        g.addEdge("D", "D");

        g.addEdge("E", "E");

        g.addEdge("F", "E");


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

        System.out.println(
            "Following is Depth First Traversal "
                + "(starting from vertex 2)");

        g.DFS("A");
    }
}
// This code is contributed by Aakash Hasija