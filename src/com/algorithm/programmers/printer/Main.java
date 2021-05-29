package com.algorithm.programmers.printer;

public class Main {
    /*
        https://programmers.co.kr/learn/courses/30/lessons/42587
        1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
        2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
        3. 그렇지 않으면 J를 인쇄합니다.

        A, B, C, D  2 1 3 2
        1. A를 꺼낸다.
        2. A(2) 보다 중요도가 높은 C(3)이 발견되었으므로 맨 뒤로 이동한다.
            - B, C, D, A 1 3 2 2
        3. B(1) 보다 중요도가 높은 C(3)이 발견되었으므로 맨 뒤로 이동한다.
            - C, D, A, B 3 2 2 1
        4. C(3) 보다 중요도가 높은 문서가 없으므로 C(3)를 출력한다.
            -  D, A, B 2 2 1
        5. 반복한다.



        --- 먼저 들어온게 처리가 되는것이 주된 목적이므로 Queue이다.
     */

    public static void main(String[] args){
        Solution solution = new Solution();
        // [2, 1, 3, 2] 2
        // [1, 1, 9, 1, 1, 1] 5
        // [2,1,2,1,2]
        //System.out.println(solution.solution(new int[]{2, 1, 3, 2}, 2));
        //System.out.println(solution.solution(new int[]{1, 1, 9, 1, 1, 1}, 0));
        //System.out.println(solution.solution(new int[]{2, 1, 2, 1, 2}, 4));
        System.out.println(solution.solution(new int[]{1, 7, 5, 2, 5}, 3));
    }

}
