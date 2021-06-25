package com.algorithm.programmers.longnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Solution2 {

    //6 [[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]    3
    // 36 43 32 13 12 24 52

    private Stack<Integer> stack = new Stack<>();
    List<String> edgeList = new ArrayList<>(){{
        add("36");
        add("43");
        add("32");
        add("13");
        add("12");
        add("24");
        add("52");
    }};
    Map<String, int[]> edgeMap = new HashMap<>(){{
        put("36", new int[]{3,6});
        put("43", new int[]{4,3});
        put("32", new int[]{3,2});
        put("13", new int[]{1,3}); // 1
        put("12", new int[]{1,2});
        put("24", new int[]{2,4});
        put("52", new int[]{5,2});
    }};
    Map<Integer, Integer> result = new HashMap<>();




    void dfsStack(String key, int node, List<String> visitList, int count){

        Map<String, int[]> tempMap = edgeMap.entrySet().stream().filter( entry -> !visitList.contains(entry.getKey()) )  // 방문 리스트에 없고
            .filter( entry -> Arrays.stream(entry.getValue()).anyMatch(a -> a == node))  // value 중 node값이 있는거..
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println(key + " " + node + " " + tempMap);
        System.out.println(visitList);
        System.out.println("-----------");

        if(tempMap.isEmpty()){
            System.out.println("result Node > " + node + " " + count);
            result.merge(node, count, Math::min);
        }else{
            for(String k : tempMap.keySet()){
                for(int j : tempMap.get(k)){
                    if(j != node){
                        dfsStack(k, j, new ArrayList<>(){{addAll(visitList); add(k);}}, count + 1);
                    }
                }
            }
        }
    }


    public int solution(int n, int[][] edge) {
        List<String> visitList = new ArrayList<>();
        //visitList.add("13");
        int count = 1;
        // 1번으로 시작해서 이걸 찾은거
        dfsStack("", 1, visitList, count);
        //dfsStack("12");

        // {1=3, 5=3, 6=2}
        // {1=3, 5=2, 6=2}

        System.out.println(result);


        int answer = 0;
        return answer;
    }
}
