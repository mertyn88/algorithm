package com.algorithm.programmers.convertword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Solution {

            /*
            https://programmers.co.kr/learn/courses/30/lessons/43163
            제약 1 - 한번에 한개의 알파벳을 변환해야 하는데 그때 word 배열에 포함된 단어로만 변경 가능

            DFS 써야함

            hit
            |
            hot
            |      \
            dot    lot
            |   \     \
            dog  lot  log
            |   \    \
            cog  log  log

            사전 구축
            begin 단어에서 가장 가까운 집합들
            hit -> hot
            hot -> dot, lot
            dot -> dog
            dog -> cog, log // stop!

            hit[hot] <-- 시작
            hot[dot, lot]
            dot[hot, dog, lot]
            dog[dot, log, cog]
            lot[hot, dot, log]
            log[dog, lot, cog]
            cog[dog, log]
         */

    public int getMatchSize(List<String> list1, List<String> list2){
        int count = 0;
        for(int idx = 0; idx < Math.min(list1.size(),list2.size()); idx++){
            if(list1.get(idx).equals(list2.get(idx))){
                count += 1;
            }
        }
        return count;
    }

    private List<String> visitList = new ArrayList<>();
    private boolean check = false;
    public int recursion(String begin, String target, List<String> words, int matchSize, int count){

        // 방문 리스트
        visitList.add(begin);

        //for(String word : words){
        List<String> beginList = Arrays.asList(begin.split(""));
        // 리스트 중에서 begin -1 과 같은 개수 루프
        List<String> containList = words.stream().map(s -> {
            if(matchSize == getMatchSize(Arrays.asList(s.split("")), beginList)){
                if(!visitList.contains(s)){
                    return s;
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        if(containList.isEmpty()){
            visitList.remove(begin); // 전으로 돌아가고
            return count-1;
        }else if(containList.contains(target)){
            check = true;
            return count;
        }else{
            for(String word : containList){
                if(!check){
                    return recursion(word, target, words, matchSize, count + 1);
                }

            }
        }
        return 0;
    }

    public int solution(String begin, String target, String[] words) {
        int answer = recursion(begin, target, Arrays.asList(words), begin.length()-1, 1);
        if(check){
            return answer;
        }else{
            return 0;
        }
    }
}
