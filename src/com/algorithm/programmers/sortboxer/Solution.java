package com.algorithm.programmers.sortboxer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Solution {

    /**
     * 복서에 대한 정보 및 정렬에 필요한 값을 저장하고 있는 객체 클래스 선언
     *  - getter 메소드를 추가한 이유는 Comparator에 사용하기 위해
     */
    class Info {
        public float winnerRate;       // 승률 (이긴횟수)
        public int moreWeightCnt;   // 자기몸무게보다 높은 사람 횟수
        public int weight;          // 몸무게
        public int index;           // 순서

        public float getWinnerRate() {
            return winnerRate;
        }
        public int getMoreWeightCnt() {
            return moreWeightCnt;
        }
        public int getWeight() {
            return weight;
        }
        public int getIndex() {
            return index;
        }
    }

    /**
     * 승리 리스트 구하기
     * @param target
     * @return
     */
    private List<Integer> getWinnerList(String target) {
        List<Integer> result = new ArrayList<>();
        if(target.contains("W")){
            int i = target.indexOf("W");
            result.add(i);
            while(i >= 0) {
                i = target.indexOf("W", i+1);
                if(i > 0){
                    result.add(i);
                }
            }
        }
        return result;
    }

    /**
     * 승리 승률 구하기
     *  - N인 경우는 경기 승률을 구할때 제외한다.
     *  - N인 경우를 제외 하였을 경우, 전체 경기횟수가 0값이 되면 나누지 않고 0으로 고정한다
     * @param list
     * @param target
     * @return
     */
    private float getWinnerRate(List<Integer> list, String target) {
        int totalCount = target.replace("N", "").length();
        if(totalCount == 0) {
            return 0.0f;
        }
        return list.size() / (float)totalCount * 100;
    }

    /**
     * 승리 값 중에서 자신의 몸무게보다 큰 횟수 구하기
     * @param list
     * @param weights
     * @param weight
     * @return
     */
    private int getMoreWeightWinnerCount(List<Integer> list, int[] weights, int weight) {
        int result = 0;
        for(int idx : list) {
            if(weight < weights[idx]) {
                result++;
            }
        }
        return result;
    }

    public int[] solution(int[] weights, String[] head2head) {

        Info[] infoArr = new Info[weights.length];
        for(int idx = 0; idx < weights.length; idx++) {
            Info info = new Info();
            // 순서 설정 하기
            info.index = idx + 1;
            // 몸무게 설정 하기
            info.weight = weights[idx];
            // 이긴 횟수 구하기
            List<Integer> list = getWinnerList(head2head[idx]);
            // 승률 계산하여 설정 하기
            info.winnerRate = getWinnerRate(list, head2head[idx]);
            // 자기자신 몸무게보다 많은 복서를 이겼을 때의 횟수 설정 하기
            info.moreWeightCnt = getMoreWeightWinnerCount(list, weights, weights[idx]);
            infoArr[idx] = info;
        }
        // 정렬
        Comparator<Info> compare = Comparator.comparing(Info::getWinnerRate).reversed()
                                    .thenComparing(Info::getMoreWeightCnt, Comparator.reverseOrder())
                                    .thenComparing(Info::getWeight, Comparator.reverseOrder())
                                    .thenComparing(Info::getIndex)
        ;
        return Arrays.stream(infoArr).sorted(compare).mapToInt(info -> info.index).toArray();
    }
}