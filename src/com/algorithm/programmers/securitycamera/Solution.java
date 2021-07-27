package com.algorithm.programmers.securitycamera;

import java.util.Arrays;

class Solution {
    public int solution(int[][] routes) {
        int answer = 0;
        // 정렬
        //int[][] sortArr = Arrays.stream(routes).sorted(Comparator.comparing(route -> route[1])).toArray(int[][]::new);

        Arrays.sort(routes, (o1, o2) -> {
            if(o1[1] == o2[1])
                return Integer.compare(o1[0], o2[0]);
            else
                return Integer.compare(o1[1], o2[1]);
        });



        // 방문 리스트
        boolean[] isVisit = new boolean[routes.length];
        for(int i = 0; i < routes.length; i++){
            if(!isVisit[i]){
                for(int j = 0; j < routes.length; j++){
                    // 방문하지 않았고 i 배열 기준으로 포함되어 있는것이 있으면 방문 체크
                    if(!isVisit[j] && isContains(routes[i], routes[j])){ // -13 < -14, -13 < -5
                        //System.out.println(sortArr[i][0] + " " + sortArr[i][1] + " / " + i + " " + sortArr[j][0] + " " + sortArr[j][1]);
                        isVisit[j] = true;
                    }
                }
                // 방문에 몇개가 들어가든 하나의 그룹으로 인정하기 때문에 +1만 한다.
                answer++;
            }
            // 방문 개수가 전부 1이면 종료
            if(isAllVisit(isVisit)){
                break;
            }
        }
        return answer;
    }

    private boolean isAllVisit(boolean[] isVisit) {
        for(boolean bool : isVisit){
            if(!bool){
                return false;
            }
        }
        return true;
    }

    private boolean isContains(int[] arr1, int[] arr2){
        if(arr2[0] <= arr1[0] && arr1[0] <= arr2[1]){
            // -20 <= -18 <= 15
            return true;
        }else if(arr2[0] <= arr1[1] && arr1[1] <= arr2[1]){
            // -20 <= -13 <= 15
            return true;
        }
        return false;
    }
}
