package com.algorithm.stack.main;

import com.algorithm.stack.configuration.IntStack;

import java.util.Scanner;

public class StackMain {

    public static void main(String[] args) {
        Scanner stdIn =  new Scanner(System.in);
        IntStack intStack = new IntStack(64);

        while (true){
            System.out.println("현제 데이터 수 " + intStack.size() + " / " + intStack.capacity());
            System.out.println("1. 푸쉬 2. 팝 3. 피크 4. 덤프 0. 종료");

            int menu = stdIn.nextInt();
            if(menu == 0){
                break;
            }

            int x;
            switch (menu){
                case 1: //푸쉬
                    System.out.print("데이터 :");
                    x = stdIn.nextInt();
                    try {
                        intStack.push(x);
                    } catch (IntStack.OverflowIntStackException e){
                        System.out.println("스택이 가득 참");
                    }
                    break;
                case 2: //팝
                    try {
                        x = intStack.pop();
                        System.out.println("팝 데이터는 " + x + "이다.");
                    } catch (IntStack.EmptyIntStackException e){
                        System.out.println("스택이 비어 있음");
                    }
                    break;
                case 3: //피크
                    try {
                        x = intStack.peek();
                        System.out.println("피크한 데이터는 " + x + "이다");
                    } catch (IntStack.EmptyIntStackException e){
                        System.out.println("스택이 비어 있음");
                    }
                    break;
                case 4: //덤프
                    intStack.dump();
                    break;
            }
        }
    }
}
