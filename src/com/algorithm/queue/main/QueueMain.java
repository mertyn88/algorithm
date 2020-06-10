package com.algorithm.queue.main;

import com.algorithm.queue.configuration.IntQueue;

import java.util.Scanner;

public class QueueMain {

    public static void main(String[] args) {
        Scanner stdIn =  new Scanner(System.in);
        IntQueue intQueue = new IntQueue(64);

        while (true){
            System.out.println("현제 데이터 수 " + intQueue.size() + " / " + intQueue.capacity());
            System.out.println("1. 인큐 2. 디큐 3. 피크 4. 덤프 0. 종료");

            int menu = stdIn.nextInt();
            if(menu == 0){
                break;
            }

            int x;
            switch (menu){
                case 1: //인큐
                    System.out.print("데이터 :");
                    x = stdIn.nextInt();
                    try {
                        intQueue.enqueue(x);
                    } catch (IntQueue.OverflowIntQueueException e){
                        System.out.println("큐가 가득 참");
                    }
                    break;
                case 2: //디큐
                    try {
                        x = intQueue.dequeue();
                        System.out.println("디큐 데이터는 " + x + "이다.");
                    } catch (IntQueue.EmptyIntQueueException e){
                        System.out.println("큐가 비어 있음");
                    }
                    break;
                case 3: //피크
                    try {
                        x = intQueue.peek();
                        System.out.println("피크한 데이터는 " + x + "이다");
                    } catch (IntQueue.EmptyIntQueueException e){
                        System.out.println("큐가 비어 있음");
                    }
                    break;
                case 4: //덤프
                    intQueue.dump();
                    break;
            }
        }
    }
}
