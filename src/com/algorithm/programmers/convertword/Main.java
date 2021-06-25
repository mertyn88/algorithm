package com.algorithm.programmers.convertword;

public class Main {
    public static void main(String[] args){
        Solution solution = new Solution();
        // "hit",  "cog",  ["hot", "dot", "dog", "lot", "log", "cog"]
        //System.out.println(solution.solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}));
        // [cog, log, lot, dog, dot, hot]
        //System.out.println(solution.solution("hit", "cog", new String[]{"cog", "log", "lot", "dog", "dot", "hot", "hot"}));
        //  "hit", "cog", ["hot", "dot", "dog", "lot", "log"]  0
        //System.out.println(solution.solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log"}));
        //  "hit", "hhh", ["hhh","hht"] 2
        System.out.println(solution.solution("hit", "hhh", new String[]{"hhh", "hht"}));
    }
}
