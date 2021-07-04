참여인원 4  
> 잡부 : https://github.com/lordchiwoo/algo_programmers  
> 산당 : https://github.com/JhonverKing/AlgoStudy  
> 가니 : https://github.com/kwan1989/algorithm_Programmers  
---
## 순위 (2021. 07. 04) Graph (BFS)
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/49191?language=java)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/setrank/Solution.java)

설명 작성 요망



---
## 가장 먼 노드 (2021. 06. 28) Graph (BFS)
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/49189)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/longnode/Solution.java)

### 설명
> n개의 노드가 있는 그래프가 있습니다. 각 노드는 1부터 n까지 번호가 적혀있습니다.
> 1번 노드에서 가장 멀리 떨어진 노드의 갯수를 구하려고 합니다. 가장 멀리 떨어진 노드란 최단경로로 이동했을 때 간선의 개수가 가장 많은 노드들을 의미합니다.
> 노드의 개수 n, 간선에 대한 정보가 담긴 2차원 배열 vertex가 매개변수로 주어질 때, 1번 노드로부터 가장 멀리 떨어진 노드가 몇 개인지를 return 하도록 solution 함수를 작성해주세요.

> 제한사항
> * 노드의 개수 n은 2 이상 20,000 이하입니다.
> * 간선은 양방향이며 총 1개 이상 50,000개 이하의 간선이 있습니다.
> * vertex 배열 각 행 [a, b]는 a번 노드와 b번 노드 사이에 간선이 있다는 의미입니다.

![](https://images.velog.io/images/mertyn88/post/78916a7e-c778-4cca-bf8b-5e71cc09aea2/image.png)

|n|vertex|return|
|---|---|---|
|6|[[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]|3|

### 문제 해결

#### 이슈 케이스
해당 문제를 해결하기 위해서 처음에는 DFS를 사용했었다. 하지만 DFS를 사용함과 동시에 처리해야할 for loop가 점점 많아지고, 원하는 결과는 나오지 않고, 간신히 예제를 통과했더니 모든 테스트 케이스에서 런타임 에러 및 예외가 발생하였다.

1차적인 문제의 원인은 다음과 같았다.
* 해당 문제는 BFS가 효과적이다. DFS의 경우 모든 이동 경로를 탐색하므로 뎁스의 구별이 없다.
* 가장 먼 노드는 가장 뎁스의 깊이 가 긴 노드들 이다.

처음에 주어진 사진의 예제 그림으로는 DFS로 5번노드와 6번 노드를 구할수 있었는데 4번의 경우 양방향으로 이어진 2번과 3번 노드들이 있기에 많은 고민과 삽질을 하였다. ( 코딩시간에만 6시간 이상 할애 한듯 ) 하지만 스터디 인원과의 대화를 통해서 예제 그래프를 살짝만 돌려보면, 4번, 5번, 6번 노드가 3뎁스의 위치에 존재하여 3개의 노드가 가장먼 노드가 된다는 사실을 깨달았다.

#### BFS를 위한 인접 리스트 Set 만들기
> 해당 문제는 단순 BFS가 아니고 Graph 이므로 인접노드셋이 필요하다. (보통 알고리즘 BFS 예제에서 보던 처음에 주어지는 노드셋이다. ) 해당 노드셋을 만들기 위해 다음과 같은 setData() 메소드를 추가한다.
```java
// 만들어진 인접노드 리스트
{1=[2, 3], 2=[1, 3, 4, 5], 3=[1, 2, 4, 6], 4=[2, 3], 5=[2], 6=[3]}
```

```java
//인접노드리스트
public void setData(int n, int[][] edge){
    for(int idx = 1; idx <= n; idx++){
        for (int[] arr : edge) {
            if (idx == arr[0]) {
                addEdge(idx, arr[1]);
            } else if (idx == arr[1]) {
                addEdge(idx, arr[0]);
            }
        }
    }
}

//키값이 존재하면 기존의 값에 append, 존재 하지 않으면 새로 put
//Map.merge를 이용하면 속도가 좀더 느려져서 변경하였다.
void addEdge(int key, int adjacent) {
    if(adjacentMap.containsKey(key)){
        adjacentMap.get(key).add(adjacent);
    }else{
        adjacentMap.put(key, new HashSet<>(){{
            add(adjacent);
        }});
    }
}
```


#### BFS 처리 중 Depth 구별
>과거 BFS/DFS의 예제를 정리한것이 있어서 해당 방식을 적용하였는데, 예제에서는 탐색의 순서를 정해줄 뿐, 언제 어떠한 노드가 깊이의 마지막인지를 처리하는 로직은 없다. 해서 코드를 분석 결과, 하나의 Queue가 비어졌을 때 한번의 깊이가 종료된다고 생각하였고, 해당 방식을 처리하기 위해 Switch Queue를 이용하여 처리하였다.

```java
// adjacent list loop
for (int loopKey : adjacentMap.get(queueKey)) {
    if (!visitedList.contains(loopKey)) {
        visitedList.add(loopKey);
        tempQueue.add(loopKey);
    }
}

if(queue.size() == 0){
    //뎁스별 노드 삽입
    if(tempQueue.size() != 0){
        answer = tempQueue.size();  // 마지막 tempQueue 사이즈가 마지막 뎁스의 노드 개수
        queue.addAll(tempQueue);
        tempQueue.clear();
    }
}
```

#### 시간초과 이슈 발생 (Critical)
> 개발 이후 실제 테스트케이스를 돌려보니 테스트케이스 7,8,9번에서 엄청난 속도가 걸렸고, 테스트8번같은 경우는 시간초과가 발생하였다.

|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|---|---|---|
|테스트 1|통과|(0.55ms, 53MB)|테스트 6|통과|(96.47ms, 57.3MB)
|테스트 2|통과|(0.57ms, 52.2MB)|테스트 7|통과|(3816.47ms, 76.7MB)
|테스트 3|통과|(1.00ms, 53.2MB)|테스트 8|실패|(시간 초과)
|테스트 4|통과|(8.14ms, 56.1MB)|테스트 9|통과|(7040.84ms, 81.1MB)
|테스트 5|통과|(51.55ms, 58.2MB)|

시간초과가 발생한 원인을 스터디원과 함께 분석한 결과 다음과 같았다.
##### 인접노드리스트를 만드는 Loop
> 인접노드 리스트를 만들때, n개의 노드를 Loop하며 edge[][]를 다시 Loop하는데 이러면 n * edge 만큼의 Loop가 발생하므로 속도에서의 차이가 발생한다.
해서 다음과 같이 코드를 변경하였다.

```java
// 시간초과 해결한 코드
public void setData(int n, int[][] edge){
    for (int[] arr : edge) {
        addEdge(arr[0], arr[1]);
        addEdge(arr[1], arr[0]);
    }
    visitArray = new boolean[n];
}
```
하지만 해당 구조를 변경해도 테스트 케이스8번을 통과할수 없었다...

##### 방문리스트를 체크하는 데이터 구조
> 실무에서 사용하는 데이터구조를 이용하기로 마음먹었기 때문에 배열을 사용하지 않고 List를 자주 이용하는데 List의 contains로 체크하는 부분도 생각보다 속도에 영향을 준다고 하였다. 실제로 코드내에서 방문리스트는 전체 탐색을 통하는데 반해 ( 발견하면 그때는 멈추기는 하겠지...) 방문 배열은 해당 인덱스를 명시하므로 속도에서 크게 차이가 날것으로 생각된다.
해서 다음과 같이 데이터 구조가 변경되었다.

```java
//방문배열 선언, 초기화는 setData에서 n개 만큼 이루어진다.
private boolean[] visitArray = null;
...
//노드는 1번부터 시작이므로 실제 방문리스트 체크는 노드 - 1 을 한다.
if (!visitArray[loopKey - 1]) {
    visitArray[loopKey - 1] = true;
    tempQueue.add(loopKey);
}
```

### 코드 개선 이후 결과

|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|---|---|---|
|테스트 1|통과|(0.40ms, 52.3MB)|테스트 6|통과|(9.54ms, 58.6MB)
|테스트 2|통과|(0.46ms, 52.9MB)|테스트 7|통과|(101.81ms, 77.4MB)
|테스트 3|통과|(0.73ms, 54.5MB)|테스트 8|통과|(92.72ms, 75.5MB)
|테스트 4|통과|(1.66ms, 53.6MB)|테스트 9|통과|(86.50ms, 77.9MB)
|테스트 5|통과|(6.90ms, 53.7MB)


### 후기
알고리즘을 풀면서 항상 생각한게, `"속도는 조금 걸리더라도 실무에서 많이 사용하는 방식으로 처리하자"` 였다. 
그래서 일부러 배열도 리스트로 변환하여 처리하고 그랬는데, 이번처럼 애초에 테스트 케이스 자체를 통과 할 수 없는 문제에 와버리니 데이터 구조를 배열로 가는 방법도 다시 한번 생각해 봐야할듯 하다.
이번 문제를 해결하면서 굉장히 많은 삽질과 ( 매번하지만,, ) 시간이 소요되었다. 
이럴때마다 정말 현타가 온다 아오...


---
## 단어변환 (2021. 06. 20) BFS/DFS
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
## 네트워크 (2021. 06. 09) BFS/DFS
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
## 타겟넘버 (2021. 06. 07) BFS/DFS
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
## 프린터 (2021. 05. 30)  Stack/Queue
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
