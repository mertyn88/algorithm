package com.algorithm.programmers.convertword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        hit[hot]
        hot[dot, lot]
        dot[hot, dog, lot]
        dog[dot, log, cog]
        lot[hot, dot, log]
        log[dog, lot, cog]
        cog[dog, log]
     */

    private List<String> visitList;
    private List<String> wordList;
    private int matchSize = 0;
    private String target;
    private boolean check = false;

    public int recursion(String begin, int count){
        // 방문 리스트 추가
        visitList.add(begin);
        // 시작단어의 char array 변환
        List<String> beginList = Arrays.asList(begin.split(""));
        // 전체 단어리스트중, matchSize에 해당하는 리스트 만들기
        List<String> containList = wordList.stream().map(s -> {
            if(matchSize == getMatchSize(Arrays.asList(s.split("")), beginList)){
                if(!visitList.contains(s)){
                    return s;
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        if(containList.isEmpty()){
            // 발견하지 못한 상황, 현재 경로에는 원하는 결과가 없으므로 다른경로 탐색 필요
            visitList.remove(begin); // 방문자 리스트 삭제 ( 전의 상황으로 돌아가야 한다. )
            return count - 1;
        }else if(containList.contains(target)){
            // 발견된 상황, target이 포함되어있으므로 해당 상황이 최적의 길이다.
            check = true;   // 스위치 ON
            return count;
        }else{
            // matchSize에 해당하는 리스트는 존재하지만, target값은 없는 경우, 재귀 처리
            for(String word : containList){
                if(!check){ // 처리중, target이 포함된 경우를 발견이 되면 더이상의 재귀 loop는 의미 없으므로 통과한다.
                    return recursion(word, count + 1);
                }
            }
        }
        return 0;
    }

    /**
     * 단어의 idx 번째 값이 일치하는 개수
     * 초기 contains를 사용하였다가 같은값이 있는 경우 처리가 안되므로 변경
     */
    public long getMatchSize(List<String> list1, List<String> list2){
        return IntStream.range(0, Math.min(list1.size(), list2.size()))
                .filter(idx -> list1.get(idx).equals(list2.get(idx))).count();
    }

    public int solution(String begin, String target, String[] words) {
        /* Data set */
        visitList = new ArrayList<>();      // 방문 리스트
        wordList = Arrays.asList(words);    // 배열문자열 리스트 변경
        matchSize = begin.length() - 1;     // 알파벳 한글자만을 제외하고 일치해야하므로
        this.target = target;               // 매칭되어야할 최종단어

        // 체크가 false면 매칭되는 단어가 없으므로 0값으로
        int answer = recursion(begin, 1);
        return check ? answer : 0 ;
    }
}
