package com.algorithm.programmers.setrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Solution {

    /*

        https://programmers.co.kr/learn/courses/30/lessons/49191?language=java
        5	[[4, 3], [4, 2], [3, 2], [1, 2], [2, 5]]	2

        노드별 승리 데이터 셋
        5 = []
        4 = [3,2]
        3 = [2]
        2 = [5]
        1 = [2]

        노드별 패배 데이터 셋
        5 = [2]
        4 = []
        3 = [4]
        2 = [4,3,1]
        1 = []

        노드별 진 횟수
        5 = 1
        4 = 0
        3 = 1
        2 = 3
        1 = 0

        노드별 이긴 횟수
        5 = 0
        4 = 2
        3 = 1
        2 = 1
        1 = 1

        1. 승리 데이터셋이 없으면 마지막 순위 확정이다.
            -> 단, 데이터셋이 없는것이 2개 이상이면 모두 나가리 이다.
        2. 패배 데이터셋이 없으면 1순위 확정이다.
            -> 단, 데이터셋이 없는것이 2개 이상이면 모두 나가리 이다.

        5위 확정
        1,4 노드 제외

        2, 3 노드 찾으면 됨
         -> 2, 3노드가 확정노드 와 연결되어있는지 체크
         -> 3번 노드는 확정 노드와 연결점이 없으니까 제외
         -> 2번 노드는 5번노드와 연결되어 있으므로 추가적인 순위 확정


     */
    Map<Integer, Set<Integer>> winnerMap = new HashMap<>();
    Map<Integer, Set<Integer>> loserMap = new HashMap<>();
    int[] rankArr = null;
    Set<Integer> excludeSet = new HashSet<>();

    public void setData(int n, int[][] results){
        rankArr = new int[n];
        isVisit = new boolean[n];

        for(int i = 1; i <= n ; i++){
            winnerMap.put(i, null);
            loserMap.put(i, null);
        }

        for(int[] arr : results){
            //승리 arr[0] 패매 arr[1]
            winnerMap.merge(arr[0], new HashSet<>(){{add(arr[1]);}}, (e, i) -> new HashSet<>(){{addAll(e);addAll(i);}});
            loserMap.merge(arr[1], new HashSet<>(){{add(arr[0]);}}, (e, i) -> new HashSet<>(){{addAll(e);addAll(i);}});
        }
    }

    public int solution(int n, int[][] results) {
        // 초기 데이터 셋
        setData(n,  results);

        System.out.println(winnerMap);
        System.out.println(loserMap);
        System.out.println("-------------------");

        // 마지막 순위 찾기
        List<Integer> loserList = winnerMap.entrySet().stream().filter(entry -> entry.getValue() == null).map(Map.Entry::getKey).collect(Collectors.toList());
        // 첫번째 순위 찾기
        List<Integer> winnerList = loserMap.entrySet().stream().filter(entry -> entry.getValue() == null).map(Map.Entry::getKey).collect(Collectors.toList());


        System.out.println(loserList);
        System.out.println(winnerList);
        System.out.println("-------------------");

        // 마지막 제외 또는 할당 처리
        if(loserList.size() == 1){
            //할당
            rankArr[n-1] = loserList.get(0);
        }else{
            //제외
            excludeSet.addAll(loserList);
        }
        // 첫번째 제외 또는 할당 처리
        if(winnerList.size() == 1){
            //할당
            rankArr[0] = winnerList.get(0);
        }else{
            //제외
            excludeSet.addAll(winnerList);
        }

        //둘다 없으면 찾을 수 없으므로 종료
        if(loserList.size() == 0 && winnerList.size() == 0){
            return 0;
        }

/*        IntStream.range(0, rankArr.length).forEach(a -> System.out.println(rankArr[a]));
        System.out.println(excludeSet);

        // winner Map , losser Map에 제외 데이터
        for(Integer exclude : excludeSet){
           winnerMap.remove(exclude);
           loserMap.remove(exclude);
        }*/


        // 확정처리된 rank 배열로 연결점 찾아서 배치하기
        //System.out.println(winnerMap);
        //System.out.println(loserMap);

        //제외 처리 로 배열 값 필터링
        //List<List<Integer>> newResult = new ArrayList<>();
        List<int[]> newResult = new ArrayList<>();
        for(int[] arr : results){
            if(excludeSet.contains(arr[0]) || excludeSet.contains(arr[1])){
            }else{
                //newResult.add(List.of(arr[0], arr[1]));
                //확정이 포함된 노드만
                newResult.add(arr);
                /*for(int rank : rankArr){
                    // 확정 랭크값 포함이 되어야만 한다.
                    if(rank != 0 && (rank == arr[0] || rank == arr[1])){

                    }
                }*/
            }
        }


        System.out.println("---------------");
        for(int[] i : newResult){
            System.out.println(i[0] + " " + i[1]);
        }



        //확정 순위로 찾기 시작
        for(int i = 0; i < rankArr.length; i++){
            if(rankArr[i] != 0){
                // 0값이 아니여야 확정 순위가 있는것..
                dfs(rankArr[i], i, newResult);
            }
        }

        //IntStream.range(0, rankArr.length).forEach(a -> System.out.println(rankArr[a]));

        return (int)Arrays.stream(rankArr).filter(a -> a != 0).count();
    }


    private boolean[] isVisit = null;

    public void dfs(Integer node, Integer rank , List<int[]> newResult){
        //방문 처리
        isVisit[node - 1] = true;

        for(int[] arr : newResult){
            if(arr[1] == node){
                if(!isVisit[arr[0]-1]){
                    // 현재 지정 node를 이긴것, 현재 노드 번호 앞에다가 부여
                    rankArr[rank - 1] = arr[0];
                    // 다른 연결 노드가 있는지 다시 찾기
                    dfs(arr[0], rank - 1, newResult);
                }
            }else if(arr[0] == node){
                if(!isVisit[arr[1]-1]){
                    // 현재 지정 node에게 진것, 현재 노드 번호 뒤에다가 부여
                    rankArr[rank + 1] = arr[1];
                    // 다른 연결 노드 찾기
                    dfs(arr[1], rank + 1, newResult);
                }
            }
        }
    }
}