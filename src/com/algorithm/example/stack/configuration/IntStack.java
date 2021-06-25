package com.algorithm.example.stack.configuration;

public class IntStack {
    private int max;    //스택 용량
    private int ptr;    //스택 포인터
    private int[] stack;    //스택 본체

    //실행 시 예외 : 스택이 비어있음
    public class EmptyIntStackException extends RuntimeException {
        public EmptyIntStackException() {}
    }

    //실행 시 예외 : 스택이 가득 참
    public class OverflowIntStackException extends RuntimeException {
        public OverflowIntStackException() {}
    }

    //생성자
    public IntStack(int capacity){
        ptr = 0;
        max = capacity;
        try{
            stack = new int[max];       //스택 본체용 배열
        } catch (OutOfMemoryError e){
            max = 0;
        }
    }

    //스택에 x를 푸쉬
    public int push(int x) throws OverflowIntStackException{
        if (ptr >= max){
            throw new OverflowIntStackException();
        }
        return stack[ptr++] = x;    //전달받은 데이터 X를 배열 요소 stack[ptr]에 저장하고 스택포인터를 증가, 반환값은 푸쉬한 값
    }

    //스택에서 데이터를 팝
    public int pop() throws EmptyIntStackException {
        if (ptr <= 0) {
            throw new EmptyIntStackException();
        }
        return stack[--ptr];
    }

    //스택에서 데이터를 피크(정상에 있는 데이터를 들여봄 , 아마 제일 아래있는게 정상)
    public int peek() throws EmptyIntStackException {
        if (ptr <= 0){
            throw new EmptyIntStackException();
        }
        return stack[ptr - 1];
    }

    //스택에서 x를 찾아 인덱스(없으면-1)를 반환
    public int indexOf(int x){
        for(int i = ptr - 1; i >= 0; i--){
            if(stack[i] == x){
                return i;       //검색성공
            }
        }
        return -1;  //검색실패
    }

    //스택을 비움
    public void clear(){
        ptr = 0;
    }

    //스택의 용량을 반환
    public int capacity() {
        return max;
    }

    //스택에 쌓여있는 데이터 수를 반환
    public int size() {
        return ptr;
    }

    //스택이 비어있는지 여부
    public boolean isEmpty() {
        return ptr <= 0;
    }

    //스택이 가득 찼는지 여부
    public boolean isFull() {
        return ptr >= max;
    }

    //스택 안의 모든 데이터를 바닥 -> 꼭대기 순으로 출력
    public void dump() {
        if(ptr <= 0){
            System.out.println("스택이 비었습니다.");
        }else{
            for(int i = 0; i < ptr; i++){
                System.out.println(stack[i] + " ");
            }
            System.out.println();
        }
    }
}
