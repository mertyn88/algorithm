package com.algorithm.programmers.connectisland;

import java.util.Arrays;

public class Solution {


    public int solution(int n, int[][] costs) {
        //정렬
        Arrays.sort(costs, (o1, o2) -> {
            if (o1[2] == o2[2])
                return Integer.compare(o1[1], o2[1]);
            else
                return Integer.compare(o1[2], o2[2]);
        });
        // 방문 벡터 배열
        int[] visitArr = new int[n];
        Arrays.fill(visitArr, -1);
        // 자식노드 부모로 변경?

        int answer = 0;
        for (int[] cost : costs) {
            // 최상위 부모를 찾는다.
            // cost의 0번째 값이 visitArr에서 -1값이 아니면 그값을 탐색한다.
            // -1 값이발견되면 그값은 아직 할당되지 않았으므로 작성가

            /*
                ** 각 시작, 끝 별로 찾아간 root의 값이 같은지 틀린지를 비교하고 서로 값이 틀리면
                * 두 값을 비교 하여 큰값일때는 큰값의 위치에 작은값을 설정하고, 작은값일때는 작은값의 위치에
                *
                * 크리스컬 알고리즘
             */


            int startRoot = findRoot(cost[0], visitArr);
            int endRoot = findRoot(cost[1], visitArr);

            if(startRoot == endRoot){
                continue;
            }else{
                // 큰값을 작은값으로 치환
                if(startRoot > endRoot){
                    visitArr[startRoot] = endRoot;
                }else{
                    visitArr[endRoot] = startRoot;
                }
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
