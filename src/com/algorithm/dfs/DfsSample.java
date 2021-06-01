package com.algorithm.dfs;

import java.util.LinkedList;
import java.util.List;

public class DfsSample {
    //private Map<String, LinkedList<String>> nodeMap = new HashMap<>();
    public List<Node> nodeList = new LinkedList<>();

    /*
        대상노드(Target Node) 대상별 인접노드(Adjacent Node) 리스트 추가
     */
    void add(String tNodeName, Node aNode){
        //nodeMap.get(targetNode).add(adjacentNode);
        nodeList.add(new Node(){{
            targetNodeName = tNodeName;
            adjacentNode = aNode;
        }});
    }

    void dfs(){
        /* 방문 체크 리스트 */
        dfsSearch(nodeList.get(0));
    }

    void dfsSearch(Node node){
        //인접 노드 가져오기
        // ListIterator는 양방향으로의 이동이 가능, 단 List 관련 컬렉션일경우에만 가능

        if(!node.isVisit){
            //방문 표시
            node.isVisit = true;
            dfsSearch(node.adjacentNode);
        }
        System.out.println(node.targetNodeName);
    }

    public static void main(String[] args){
        DfsSample dfsSample = new DfsSample();

        dfsSample.nodeList.add()
    }
}

class Node{
    public boolean isVisit = false;
    public String targetNodeName;
    public Node adjacentNode;
}