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
/*        for (int[] temp : costs) {
            for (int b : temp) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
        System.out.println();*/

        // 첫번째 시작값은 끝나는지점이 없으니가 종료시점만 넣는다
        int answer = 0;
        boolean[] startVisit = new boolean[n];
        boolean[] endVisit = new boolean[n];
        for (int[] cost : costs) {
            // 시작에 target 앞뒤가 들어오면 안됨
            // 종료에 target 앞뒤가 들어오면 안됨
            // 시작 첫번째, 종료 두번째에 target이 들어오면 안됨
            // 시작 두번째, 종료 첫번째에 target이 들어오면 안됨

            //최초 실행하는것만 시작부랑 연결되는것이 없으니 그것만 넣는다?

            if (startVisit[cost[0]] && endVisit[cost[0]]) {
                //들어오지마
            } else if (startVisit[cost[1]] && endVisit[cost[1]]) {
                //들어오지마
            }else if(endVisit[cost[0]] && endVisit[cost[1]]){
                //한쪽면에 두값이 있는 경우
                //들어오지마
            }else if(startVisit[cost[0]] && startVisit[cost[1]]){
            //들어오지마
            }else{
                // 들어와
                startVisit[cost[0]] = true;
                endVisit[cost[1]] = true;
                //System.out.println(cost[0] + " " + cost[1] + " 계산할값 > " + cost[2]);
                answer += cost[2];
            }
        }
        //System.out.println("------");

        System.out.println(answer);

        return answer;
    }
}
