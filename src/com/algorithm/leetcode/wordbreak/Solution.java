package com.algorithm.leetcode.wordbreak;

import java.util.List;

public class Solution {
        public boolean wordBreak(String s, List<String> wordDict) {
        int size = s.length();
        boolean[] dpArr = new boolean[size + 1];
        dpArr[0] = true;

        for (int subIndex = 1; subIndex <= size; subIndex++) {
            String sub = s.substring(0, subIndex);
            for (String word : wordDict) {
                if (sub.endsWith(word)) {
                    //System.out.println(sub + " " + word + " : " + subIndex + " / " + (subIndex - word.length()));
                    // subIndex - word.length 값이 이전값을 가지고 있다는 것
                    dpArr[subIndex] = dpArr[subIndex] | dpArr[subIndex - word.length()];
                }
                if (dpArr[subIndex]) {
                    break;
                }
            }
        }
        return dpArr[size];
    }
}
