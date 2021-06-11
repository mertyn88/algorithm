package com.algorithm.programmers.network;

class Solution {

    /*
        A Node = [(1), 1, 0]
        B Node = [1, (1), 0]
        C Node = [0, 0, (0)]

        visit Array = [A:false, B:false, C:false]
        1. A Node 미방문
        2. A Node 방문 체크
            visit Array = [A: true, B:false, C:false]
        3. A Node Loop 시작
        4. A Node 방문은 true 이므로 패스, B Node 미방문 및 값이 1이므로 재귀
        5. B Node 방문 체크
            visit Array = [A: true, B:true, C:false]
        6. B Node Loop 시작
        7. A Node 방문은 true 이므로 패스, B Node 방문은 true 이므로 패스, C Node 미방문 및 값이 0 이므로 종료
        8. B Node Loop 종료
        9. C Node 미방문 및 값이 0이므로 종료
        10. A Node Loop 종료
        11. answer + 1
        12. B Node 방문체크, true 이므로 재귀 하지 않고 종료
        13. C Node 방문체크
            visit Array = [A: true, B:true, C:true]
        14. C Node Loop 시작
        15. A Node 방문은 true 이므로 패스, B Node 방문은 true 이므로 패스, C Node 방문은 true 이므로 패스
        16. answer + 1
        17. return answer = 2
     */

    public void recursion(int idx, int[][] computers, boolean[] visit) {
        visit[idx] = true;
        for(int j = 0; j < computers[idx].length; j++){
            if(visit[j] == false && computers[idx][j] == 1){
                recursion(j, computers, visit);
            }
        }
    }

    public int solution(int n, int[][] computers) {
        boolean[] visit = new boolean[n];
        int answer = 0;
        for(int i = 0; i < n; i++){
            if(visit[i] == false){
                recursion(i, computers, visit);
                answer++;
            }
        }
        return answer;
    }

}
