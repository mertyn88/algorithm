package com.algorithm.programmers.sortboxer;

import com.algorithm.util.Util;

public class Main {

      /*
        정렬 조건
        1. 전체 승률 순서
            - WIN의 개수
        2. 자기자신의 몸무게보다 높은 WIN의 개수
        3. 자기자신의 몸무게가 높은 순
        4. 인덱스가 낮은
     */

    public static void main(String[] args) {
        Solution solution = new Solution();
        Util.print(solution.solution(new int[]{50,82,75,120}, new String[]{"NLWL","WNLL","LWNW","WWLN"}));
        //solution.solution(new int[]{145,92,86}, new String[]{"NLW","WNL","LWN"});
        //solution.solution(new int[]{60,70,60}, new String[]{"NNN","NNN","NNN"});
    }
}
