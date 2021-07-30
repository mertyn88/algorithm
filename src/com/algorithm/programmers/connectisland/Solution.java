package com.algorithm.programmers.connectisland;

import java.util.Arrays;
import java.util.Comparator;

public class Solution {

    /*
        크루스칼 알고리즘

        그래프 내의 모든 정점을 포함하고 사이클이 없는 연결 선을 그렸을 때, 가중치의 합이 최소가 되는 상황을 구하고 싶을 때 크루스칼 알고리즘을 사용
        즉, 최소 신장 트리를 구하기 위한 알고리즘입
        실제 크루스칼 알고리즘은 배열 초기값을 해당 노드의 값으로 하였는데 그부분을 안보고 구현하다보니 초기값을 -1로 지정
     */

    public int solution(int n, int[][] costs) {
        //정렬
        int[][] sortCosts = Arrays.stream(costs).sorted(Comparator.comparing(cost -> cost[2])).toArray(int[][]::new);
        // 방문 벡터 배열 선언 및 -1 값으로 초기화
        int[] visitArr = new int[n];
        Arrays.fill(visitArr, -1);

        int answer = 0;
        for (int[] cost : sortCosts) {
            // 시작섬, 끝섬 각각 최상위 부모를 찾는다.
            // cost의 0번째 값이 visitArr에서 -1값이 아니면 그값을 탐색한다.
            int startRoot = findRoot(cost[0], visitArr);
            int endRoot = findRoot(cost[1], visitArr);

            // 양쪽 부모가 같으면 사이클이 형성되므로 제외한다.
            if(startRoot == endRoot){
                continue;
            }else{
                // 순서가 상관없으므로 두 root값을 모두 비교해야한다.
                // 큰값이 방문배열 Index, 작은값이 방문배열 Value
                if(startRoot > endRoot){
                    visitArr[startRoot] = endRoot;
                }else{
                    visitArr[endRoot] = startRoot;
                }
                // 양쪽 부모가 같지 않으므로 연결이 가능하므로 연산한다.
                answer += cost[2];
            }
        }
        return answer;
    }

   private int findRoot(int child, int[] visitArr) {
       if(child == visitArr[child]){
           return child;
       }else if (visitArr[child] == -1) {
           return child; // 기존 부모값 사용
       } else {
           return findRoot(visitArr[child], visitArr);
       }
   }
}
