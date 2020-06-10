package com.algorithm.queue.configuration;

/**
 * 링 버퍼
 * front : 맨 처음 요소의 인덱스
 * rear : 맨 끝 요소의 하나 뒤의 인덱스 (다음 요소를 인큐할 위치를 미리 지정)
 * front와 rear는 논리적인 데이터 순서를 말한다. (배열의 물리적인 순서가 아님)
 *
 * enqueue : 삽입
 * dequeue : 제외?
 *
 */
public class IntQueue {
    private int max;    // 큐 용량
    private int front;    // 첫 번째 요소 커서
    private int rear;    // 마지막 요소 커서
    private int num;     //현재 데이터 수
    private int[] queue;  // 큐 본체

    //실행시 예외 : 큐가 비어있음
    public class EmptyIntQueueException extends RuntimeException{
        public EmptyIntQueueException(){}
    }

    //실행시 예외 : 큐가 가득 참
    public class OverflowIntQueueException extends RuntimeException{
        public OverflowIntQueueException(){}
    }

    //생성자
    public IntQueue(int capacity){
        num = 0;
        front = 0;
        rear = 0;
        max = capacity;

        try {
            queue = new int[max];       //큐 본체 배열 생성
        } catch(OutOfMemoryError e){
            max = 0;
        }
    }

    //큐에 데이터 인큐
    public int enqueue(int x) throws OverflowIntQueueException {
        if (num >= max){
            throw new OverflowIntQueueException();  //큐가 가득참
        }
        queue[rear++] = x;
        num++;
        if(rear == max){
            rear = 0;
        }
        return x;
    }

    //큐에 데이터를 디큐
    public int dequeue() throws EmptyIntQueueException{
        if (num <= 0){
            throw new EmptyIntQueueException();
        }

        int x = queue[front++];
        num--;
        if(front == max){
            front = 0;
        }
        return x;
    }

    //큐에 데이터를 피크
    public int peek() throws EmptyIntQueueException{
        if (num <= 0){
            throw new EmptyIntQueueException(); //큐가 비어있음
        }
        return queue[front];
    }

    //큐에서 x검색
    public int indexOf(int x){
        for(int i = 0; i < num; i++){
            int idx = (i + front) % max;
            if(queue[idx] == x){
                return idx;
            }
        }
        return -1;
    }

    //큐를 비움
    public void clear(){
        num = 0;
        front = 0;
        rear = 0;
    }

    //큐의 용량 반환
    public int capacity(){
        return max;
    }

    //큐에 쌓여있는 데이터 수 반환
    public int size(){
        return num;
    }

    //큐가 비어있는지 여부
    public boolean isEmpty(){
        return  num <= 0;
    }

    //큐가 가득찼는지 여부
    public boolean ifFull(){
        return num >= max;
    }

    //큐 안의 모든 데이터 프린트 -> 리어 순으로 출력
    public void dump(){
        if(num <= 0){
            System.out.println("큐가 비었습니다.");
        }else {
            for(int i = 0; i < num; i++){
                System.out.println(queue[(i + front) % max] + " ");
            }
            System.out.println();
        }
    }
}
