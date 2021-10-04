package com.algorithm.leetcode.wordbreak;

import java.util.List;

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
       /* for(String word : wordDict) {
            if(s.contains(word)) {
                s = s.replace(word, "");
            }
        }*/

        for (int i = 0; i < wordDict.size(); i++) {
            String temp = s;
            // i 루프의 값이 포함될 경우 하위 시작
            if (temp.contains(wordDict.get(i))) {
                temp = temp.replace(wordDict.get(i), "#");
                for (int j = 0; j < wordDict.size(); j++) {
                    // i와 j의 값이 다를때만,
                    if (!wordDict.get(i).equals(wordDict.get(j))) {
                        if (temp.contains(wordDict.get(j))) {
                            temp = temp.replace(wordDict.get(j), "#");
                        }
                    }
                }
            }

            System.out.println(temp);

            if (temp.replace("#", "").equals("")) {
                return true;
            }
        }
        return false;
    }


    public boolean wordBreak2(String s, List<String> wordDict) {
        int size = s.length();
        boolean[] dpArr = new boolean[size + 1];
        dpArr[0] = true;

        for (int subIndex = 1; subIndex <= size; subIndex++) {
            String sub = s.substring(0, subIndex);
            for (String word : wordDict) {
                if (sub.endsWith(word)) {
                    System.out.println(sub + " " + word + " : " + subIndex + " / " + (subIndex - word.length()));
                    // subIndex - word.length 값이 이전값을 가지고 있다는 것
                    dpArr[subIndex] = dpArr[subIndex] | dpArr[subIndex - word.length()];
                }
                if (dpArr[subIndex]) {
                    break;
                }
            }

            for(boolean b : dpArr) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
        return dpArr[size];
    }
}
