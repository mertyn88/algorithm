package com.algorithm.programmers.network;

class Solution {

    public void recursion(int idx, int[][] computers, boolean[] visit) {
        visit[idx] = true;
        for(int j = 0; j < computers[idx].length; j++){
            if(visit[j] == false && computers[idx][j] == 1){
                recursion(j, computers, visit);
            }
        }
    }

    ////3	[[1, 1, 0], [1, 1, 0], [0, 0, 1]]
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
