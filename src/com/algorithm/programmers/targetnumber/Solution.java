package com.algorithm.programmers.targetnumber;

import java.util.Stack;

class Solution {

    /*
        https://programmers.co.kr/learn/courses/30/lessons/43165?language=java
        최단거리가 아니라 경우의 수를 구하는것이므로 DFS를 활용한다.
        제공되는 배열은 정수뿐이므로 하나의 숫자를 +와 -값이 있다고 생각해야한다.
        한 depth에 만들어진 리스트를 -를 포함한 값으로 *2 만큼의 개수를 생성한다
        최초 실행은 0으로 시작해서 제공되는 배열의 첫글자도 +, -가 존재하게끔 한다..?
        깊이의 값이 유니크하지 않네... 다른 처리가 필요



        loop당 스택이 비워지면 하나의 뎁스처리가 종료된것으로 간주한다.
            - 다 비우지 않고 계산할 때에 뒤죽박죽...;;
        하나의 연산 stack의 결과마다 +와 -의 값을 연산한값을 넣는다.
            - 최종 깊이의 연산값은 2의 n승
        전체 연산 결과중, target 숫자와 같은 값의 개수를 stream연산으로 return 한다.
     */
        /*
                                         [0](Z)                               // 1    / 2의 0승
                  +1(A)                                     -1(H)             // 2    / 2의 1승
            +1(B)      -1(C)                        +1(I)       -1(J)         // 4    / 2의 2승
          +1(D)  -1(E)  +1(F)   -1(G)         +1(K)  -1(L)   +1(M)  -1(N)     // 8    / 2의 3승

          제공받은 numbers의 개수 = n
          2의 n승
          1 1 1 1 1 = 32 개수

          1 뎁스 / 0 = 1, -1
            [0,1][0,-1]
          2 뎁스 / 1 = 1, -1 | -1 = 1, -1
            [1,1][1,-1][-1,1][-1,-1]
          3 뎁스 / 1 = 1, -1 | -1
            [1,1][1,-1][-1,+1][-1,-1][1,1][1,-1][-1,1][-1,-1]
*/

    private Stack<Integer> stack = new Stack<>();
    public int solution(int[] numbers, int target) {
        // 최초값 삽입 (stack loop가 수행될수 있도록)
        stack.push(0);
        // 깊이별 loop
        for (int number : numbers) {
            // 임시 stack loop마다 초기화가 이루어져야 한다.
            Stack<Integer> tempStack = new Stack<>();
            while (!stack.empty()) {
                // 현재까지 연산된 stack 값 pop
                int value = stack.pop();
                // +값, -값을 각각 stack push
                tempStack.push(number + value);
                tempStack.push((number * -1) + value);
            }
            // Empty된 stack에 다시 loop할 수 있도록 임시 stack의 값 할당
            stack.addAll(tempStack);
        }
        // 최종깊이의 연산값에 대해 target 숫자와 일치하는 개수를 반환
        return (int) stack.stream().filter(num -> num == target).count();
    }
}
