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
        for (int[] temp : costs) {
            for (int b : temp) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
        System.out.println();

        // 방문 벡터 배열
        int[] visitArr = new int[n];
        Arrays.fill(visitArr, -1);
        // 자식노드 부모로 변경?



        int answer = 0;
        for (int[] cost : costs) {
            // 최상위 부모를 찾는다.
            // cost의 0번째 값이 visitArr에서 -1값이 아니면 그값을 탐색한다.
            // -1 값이발견되면 그값은 아직 할당되지 않았으므로 작성가
            int root = findParent(cost[0], visitArr);
            visitArr[cost[1]] = root;
            //root값이 기존값과 동일한데 이미 자식노드에 값이 있는경우?
            if(cost[0] == root && visitArr[cost[1]] != -1){
                answer += cost[2];
            }
        }

        System.out.println(">> " + answer);


        //System.out.println("------");
        return answer;
    }

   private int findParent(int child, int[] visitArr) {
       if (visitArr[child] == -1) {
           return child; // 기존 부모값 사용
       } else {
           return findParent(visitArr[child], visitArr);
       }
   }
}
