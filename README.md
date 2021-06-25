참여인원 4  
> 잡부 : https://github.com/lordchiwoo/algo_programmers  
> 산당 : https://github.com/JhonverKing/AlgoStudy  
> 가니 : https://github.com/kwan1989/algorithm_Programmers  

---
#### 단어변환 (2021. 06. 20) BFS/DFS
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/43163)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/convertword/Solution.java)  

>두 개의 단어 begin, target과 단어의 집합 words가 있습니다. 아래와 같은 규칙을 이용하여 begin에서 target으로 변환하는 가장 짧은 변환 과정을 찾으려고 합니다.
> 1. 한 번에 한 개의 알파벳만 바꿀 수 있습니다.
> 2. words에 있는 단어로만 변환할 수 있습니다.

|begin|target|words|return|
|-----|------|-----|:------:|
|"hit"|"cog"|["hot", "dot", "dog", "lot", "log", "cog"]|4|
|"hit"|"cog"|["hot", "dot", "dog", "lot", "log"]|0|
|"hit"|"hhh"|["hhh","hht"]|2|

|테스트 번호|통과여부|메모리 및 시간|
|---------|:-------:|------------|
|테스트 1|통과|(5.77ms, 53MB)
|테스트 2|통과|(7.49ms, 53.2MB)
|테스트 3|통과|(31.36ms, 53.2MB)
|테스트 4|통과|(4.45ms, 54.2MB)
|테스트 5|통과|(3.61ms, 52.3MB)

해당문제를 해결하는데 가장 중요한 포인트는 2가지 였던것 같다.
1. 다음 노드의 집합을 찾을 수 있는가
2. 찾은 노드의 집합군을 어떻게 처리할 것인가
    * target의 정확한 위치값은 필요하지 않다. `해당 집합군에 포함되어 있는가` 이다.
    * 집합군이 없을 경우, 카운트의 횟수와 `집합군을 롤백`해야 한다. 

오랜시간이 걸려서 원하는 결과를 얻었지만, 실제 테스트 3 케이스에서 계속 실패를 하였는데, 찾아보니 다음과 같은 상황이 발생했다.
```bash
hit -> ["hht"] 인경우
```
람다로 두 리스트를 contains하여 개수를 반환하였는데 위의 경우는 값이 2가 나와야 하는데 실제로는 3의 값이 나온다. 그 이유는
* [h]ht 인경우 hit.contains true
* h[h]t 인경우 hit.contains true
* hh[t] 인경우 hit.contains true

코드를 변경하였고 이후 실제 테스트3의 케이스도 통과하였다.

---
#### 네트워크 (2021. 06. 09) BFS/DFS
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/43162)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/network/Solution.java)  

>컴퓨터 A와 컴퓨터 B가 직접적으로 연결되어있고, 컴퓨터 B와 컴퓨터 C가 직접적으로 연결되어 있을 때 컴퓨터 A와 컴퓨터 C도 간접적으로 연결되어 정보를 교환할 수 있습니다.  
>따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.

|n|computers|return|
|---|------|------|
|3|[[1, 1, 0], [1, 1, 0], [0, 0, 1]]|2|
|3|[[1, 1, 0], [1, 1, 1], [0, 1, 1]]|1|

해당 문제에 대해서는...  나의 실력으로 해결한 문제가 아니므로 결과를 기술하지 않는다.  
테스트케이스에 대해서 해결을 하지 못하였고, 시간이 너무 지나서 결국 정답을 살짝 보았지만  
결국 코드가 해답과 비슷해져 버렸다.

---
#### 타겟넘버 (2021. 06. 07) BFS/DFS
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/43165)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/targetnumber/Solution.java)  

>-1+1+1+1+1 = 3  
>+1-1+1+1+1 = 3  
>+1+1-1+1+1 = 3  
>+1+1+1-1+1 = 3  
>+1+1+1+1-1 = 3

|numbers|target|return|
|-------|------|------|
|[1, 1, 1, 1, 1]|3|5|

|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---------|:-------:|------------|---------|:-------:|------------|
|테스트 1|통과|(167.02ms, 102MB)|테스트 5|통과|(16.06ms, 53.5MB)|
|테스트 2|통과|(194.38ms, 99.3MB)|테스트 6|통과|(4.00ms, 53MB)|
|테스트 3|통과|(3.40ms, 52.5MB)|테스트 7|통과|(3.28ms, 52.1MB)|
|테스트 4|통과|(7.61ms, 52.5MB)|테스트 8|통과|(7.75ms, 53.6MB)|

연습한 DFS를 사용하고 싶었지만, 또다시 테스트케이스에서 지속적인 예외가 발생하였다.  
또다시 편법을 사용해야 했으며, 모든 케이스에 대해 전체 계산이 이루어진 후, 필요 번호를 stream api로  
추출하는 방식을 취했다. 그러다보니 테스트 케이스에서 엄청난 시간과 메모리양이 소요되었다.

---
#### 프린터 (2021. 05. 30)  Stack/Queue
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/42587)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/printer/Solution.java)  

> 1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
> 2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
> 3. 그렇지 않으면 J를 인쇄합니다.

|priorities |location|return
|-----------|--------|------|  
|[2, 1, 3, 2]|2|1|
|[1, 1, 9, 1, 1, 1]|0|5|

|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---------|:-------:|------------|---------|:-------:|------------|
|테스트 1|통과|(37.06ms, 52.7MB)|테스트 11|통과|(45.89ms, 54.1MB)|
|테스트 2|통과|(89.72ms, 54.9MB)|테스트 12|통과|(33.65ms, 52.8MB)|
|테스트 3|통과|(57.63ms, 54.4MB)|테스트 13|통과|(55.14ms, 54.9MB)|
|테스트 4|통과|(31.50ms, 52.9MB)|테스트 14|통과|(11.64ms, 53.5MB)|
|테스트 5|통과|(10.50ms, 54MB)|테스트 15|통과|(27.03ms, 52.7MB)|
|테스트 6|통과|(38.47ms, 52.8MB)|테스트 16|통과|(19.79ms, 53.7MB)|
|테스트 7|통과|(42.19ms, 53.3MB)|테스트 17|통과|(75.95ms, 53.3MB)|
|테스트 8|통과|(78.59ms, 58.6MB)|테스트 18|통과|(15.52ms, 52.7MB)|
|테스트 9|통과|(12.61ms, 52.5MB)|테스트 19|통과|(61.24ms, 53.9MB)|
테스트 10|통과|(34.05ms, 53.4MB)|테스트 20|통과|(50.05ms, 53.2MB)|


재귀함수를 이용해서 해결. 단, 아쉬운점은 동적으로 위치값을 변하게 하고 싶었으나  
테스트 케이스에서의 지속적인 에러로 String값을 마킹하는 편법을 사용했다.  
그러므로써 int형의 계산을 String -> Integer변환 및 replace를 해줘야하는 상황이 발생하였다.
