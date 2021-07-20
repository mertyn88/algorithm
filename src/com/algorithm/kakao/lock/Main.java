package com.algorithm.kakao.lock;


public class Main {
    public static void main(String[] args) {
       Solution solution = new Solution();
       // [[0, 0, 0], [1, 0, 0], [0, 1, 1]]	[[1, 1, 1], [1, 1, 0], [1, 0, 1]]	true
       System.out.println(
           solution.solution(
               new int[][]{{0,0,0}, {1,0,0}, {0,1,1}},
               new int[][]{{1,1,1}, {1,1,0}, {1,0,1}}
           )
       );

      /*  System.out.println(
            solution.solution(
                new int[][]{{0,0,0,1}, {0,1,0,1}, {0,0,0,0}, {0,0,0,1}},
                new int[][]{
                      {1,1,1,1,1}
                    , {1,1,1,1,1}
                    , {1,0,1,1,1}
                    , {1,0,1,1,1}
                    , {1,1,1,1,1}
                }
            )
        );*/

/*        System.out.println(
            solution.solution(
                new int[][]{{1,0,0}, {0,1,0}, {1,1,0}},
                new int[][]{
                      {1,1,0,1,1}
                    , {1,1,1,1,1}
                    , {1,1,1,1,1}
                    , {1,1,1,1,0}
                    , {1,1,0,1,0}
                }
            )
        );*/
    }
}
