참여인원 4  
> 잡부 : https://github.com/lordchiwoo/algo_programmers  
> 산당 : https://github.com/JhonverKing/AlgoStudy  
> 가니 : https://github.com/kwan1989/algorithm_Programmers  
---

## Programmers - Sort Boxer (2021. 09. 27)
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/85002)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/sortboxer/Solution.java)

### 문제 설명
>복서 선수들의 몸무게 _**weights**_와, 복서 선수들의 전적을 나타내는 _**head2head**_가 매개변수로 주어집니다. 복서 선수들의 번호를 다음과 같은 순서로 정렬한 후 _**return**_ 하도록 _**solution**_ 함수를 완성해주세요.

* 전체 승률이 높은 복서의 번호가 앞쪽으로 갑니다. 아직 다른 복서랑 붙어본 적이 없는 복서의 승률은 0%로 취급합니다.
* 승률이 동일한 복서의 번호들 중에서는 자신보다 몸무게가 무거운 복서를 이긴 횟수가 많은 복서의 번호가 앞쪽으로 갑니다.
* 자신보다 무거운 복서를 이긴 횟수까지 동일한 복서의 번호들 중에서는 자기 몸무게가 무거운 복서의 번호가 앞쪽으로 갑니다.
* 자기 몸무게까지 동일한 복서의 번호들 중에서는 작은 번호가 앞쪽으로 갑니다.

제한사항
* weights의 길이는 2 이상 1,000 이하입니다.
  * weights의 모든 값은 45 이상 150 이하의 정수입니다.
  * weights[i] 는 i+1번 복서의 몸무게(kg)를 의미합니다.
* head2head의 길이는 weights의 길이와 같습니다.
  * head2head의 모든 문자열은 길이가 weights의 길이와 동일하며, 'N', 'W', 'L'로 이루어진 문자열입니다.
  * head2head[i] 는 i+1번 복서의 전적을 의미하며, head2head[i][j]는 i+1번 복서와 j+1번 복서의 매치 결과를 의미합니다.
  * 임의의 i에 대해서 head2head[i][i] 는 항상 'N'입니다. 자기 자신과 싸울 수는 없기 때문입니다.
  * 임의의 i, j에 대해서 head2head[i][j] = 'W' 이면, head2head[j][i] = 'L'입니다.
  * 임의의 i, j에 대해서 head2head[i][j] = 'L' 이면, head2head[j][i] = 'W'입니다.
  * 임의의 i, j에 대해서 head2head[i][j] = 'N' 이면, head2head[j][i] = 'N'입니다.

입출력 예

|weights|head2head|result|
|---|---|---|
|[50,82,75,120]|["NLWL","WNLL","LWNW","WWLN"]|[3,4,1,2]
|[145,92,86]|["NLW","WNL","LWN"]|[2,3,1]|
|[60,70,60]|["NNN","NNN","NNN"]|[2,1,3]|

입출력 예 설명
입출력 예 #1

다음은 선수들의 정보를 나타낸 표입니다.

|선수 번호|vs 1번|vs 2번|vs 3번|vs 4번|승률|자기보다 무거운 복서를 이긴 횟수|몸무게|
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
|1번|-|패배|승리|패배|33.33%|1회|50kg|
|2번|승리|-|패배|패배|33.33%|0회|82kg|
|3번|패배|승리|-|승리|66.66%|2회|75kg|
|4번|승리|승리|패배|-|66.66%|0회|120kg|

본문에 서술된 우선순위를 따라 [3,4,1,2] 를 _**return**_ 합니다.

<details>
 <summary>풀이 보기</summary>
 <div markdown="14">

### 문제 해결
특정 알고리즘을 사용하기 보다는 _**멀티정렬**_을 얼마나 잘 활용하는가에 대한 문제로 생각이 되었고 잘해결이 되었다. 무언가 설명이 복잡하지만 문제 설명에 어떠한 순서로 정렬이 이루어 져야하는지 정리하면 다음과 같다
```
1. 정렬의 첫번째 조건은 승률이 높은 순서 (DESC)  
2. 정렬의 두번째 조건은 자신보다 몸무게가 무거운 복서를 이긴 횟수가 높은 순서 (DESC)  
3. 정렬의 세번째 조건은 자신의 몸무게가 무서운 순서 (DESC)  
4. 정렬의 네번째 조건은 복서의 인덱스가 낮은 순서 (ASC)
```

#### 정보를 객체화 하여 컨트롤 하기
주어진 정보에 대해서 복서에 대한 정보를 연산하여 저장할 때, 독립적인 변수에 저장하지 않고 객체화 하여 내부클래스를 만들어서 저장한다
```java
/*
    public 변수이므로 getter값이 필요 없어야 하지만 정렬 객체 생성시에는 메소드값으로 값을 가지고 있어야 사용할수 있으므로 선언한다
    (정확한 원인은 모르지만 정렬 파라미터의 타입이 Function이므로 메소드가 와야하지 않을까 싶다)
*/
class Info {
    public float winnerRate;    // 승률 (이긴횟수)
    public int moreWeightCnt;   // 자기몸무게보다 높은 사람 횟수
    public int weight;          // 몸무게
    public int index;           // 순서

    public float getWinnerRate() {
        return winnerRate;
    }
    public int getMoreWeightCnt() {
        return moreWeightCnt;
    }
    public int getWeight() {
        return weight;
    }
    public int getIndex() {
        return index;
    }
}
```

#### 이긴 횟수 구하기
해당 복서가 몇번을 이겼는지 횟수를 구하여 객체에 set한다
```java
/*
    주어진 문자열에 대해 W값의(승리) position을 담고 있는 List를 반환한다
*/
private List<Integer> getWinnerList(String target) {
    List<Integer> result = new ArrayList<>();
    if(target.contains("W")){
        int i = target.indexOf("W");
        result.add(i);
        while(i >= 0) {
            i = target.indexOf("W", i+1);
            if(i > 0){
                result.add(i);
            }
        }
    }
    return result;
}
```

#### 승률 구하기
이긴 횟수를 통해서 복서의 승률을 계산한다. 단, 여기서 N값인 경우에는 승률연산에서 제외한다.
```java
private float getWinnerRate(List<Integer> list, String target) {
    int totalCount = target.replace("N", "").length();
    if(totalCount == 0) {
        return 0.0f;
    }
    return list.size() / (float)totalCount * 100;
}
```

#### 자신보다 무거운 복서를 이긴 횟수 구하기
이전에 이긴 횟수의 Position값을 가지고 있는 List변수를 파라미터로 받아 해당 위치의 복서 몸무게값을 조회하여 횟수를 구한다
```java
private int getMoreWeightWinnerCount(List<Integer> list, int[] weights, int weight) {
    int result = 0;
    for(int idx : list) {
        if(weight < weights[idx]) {
            result++;
        }
    }
    return result;
}
```

#### 여러 필드 정렬하기
정렬 사용시 사용한 인터페이스 클래스는 다음과 같다

```java
Comparator<T>
    comparing
    thenComparing
```
정렬값을 적용하고 정렬된 객체의 값 중 index값을 배열화 하여 return 한다
```java
Comparator<Info> compare = Comparator.comparing(Info::getWinnerRate).reversed()
                            .thenComparing(Info::getMoreWeightCnt, Comparator.reverseOrder())
                            .thenComparing(Info::getWeight, Comparator.reverseOrder())
                            .thenComparing(Info::getIndex)
                            
return Arrays.stream(infoArr).sorted(compare).mapToInt(info -> info.index).toArray();
```

### 테스트 결과
|정확성|테스트|시간 및 메모리|정확성|테스트|시간 및 메모리|
|---|---|---|---|---|---|
|테스트 1|통과|(6.95ms, 77.7MB)|테스트 7|통과|(74.75ms, 102MB)|
|테스트 2|통과|(7.25ms, 75.8MB)|테스트 8|통과|(60.19ms, 101MB)|
|테스트 3|통과|(9.61ms, 77.6MB)|테스트 9|통과|(37.03ms, 81.3MB)|
|테스트 4|통과|(6.85ms, 72.9MB)|테스트 10|통과|(30.01ms, 90.4MB)|
|테스트 5|통과|(8.40ms, 82.6MB)|테스트 11|통과|(59.20ms, 93.7MB)|
|테스트 6|통과|(60.94ms, 109MB)|테스트 12|통과|(35.65ms, 109MB)|


### 후기
이번 문제는 알고리즘이라기 보다는 정렬클래스를 얼마나 잘 다루는지에 대한 문제인듯 하다. 나는 Comparator를 사용했지만 클래스 내부에서 compare를 override하거나 메소드에서 override하여 직접 명시할 수 도 있을듯 하다. 나의 경우 stream을 사용하였는데 코드는 간결하나 속도면에서는 느린것 같다. 명확하게 이용 할 수 있는 이런 문제는 언제나 환영인데..

</div>
</details>

---

## LeetCode - Best Time to Buy and Sell Stock II (2021. 09. 13)
### 문제 및 풀이 주소
[LeetCode](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/leetcode/buyandcellstock/Solution.java)

### 문제 설명 - 영문
>You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.
Find and return the maximum profit you can achieve.

### 문제 설명 - 번역
>당신은 정수 배열을 지정해되어 온 특정 주식의 가격입니다 날.pricesprices[i]ith
매일 주식을 사고팔기로 결정할 수 있습니다. 귀하는 한 번에 주식의 최대 1 주만 보유할 수 있습니다 . 그러나, 당신은 즉시 다음을 구입에 판매 할 수 같은 날 .
달성할 수 있는 최대 이익을 찾아 반환 합니다 .


예 1:
>Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: 2일째에 매수(가격 = 1)하고 3일째에 매도(가격 = 5), 이익 = 5-1 = 4.
그런 다음 4일차(가격=3)에 매수하고 5일차(가격=6)에 매도, 이익=6-3=3.
총 이익은 4 + 3 = 7입니다.

예 2:
>Input: prices = [1,2,3,4,5]
Output: 4
Explanation: 1일차에 매수(가격 = 1)하고 5일차에 매도(가격 = 5), 이익 = 5-1 = 4. 
총 이익 4입니다.

예 3:
>Input: prices = [7,6,4,3,1]
Output: 0
Explanation: 양의 이익을 얻을 수 있는 방법이 없으므로 최대 이익 0을 달성하기 위해 주식을 사지 않습니다.


제한사항
 * 1 <= prices.length <= 3 * 104
 * 0 <= prices[i] <= 104
 
 <details>
 <summary>풀이 보기</summary>
 <div markdown="13">

### 문제 해결
해당 문제는 어떻게 해결해야 하나 하고 난감해 하고 있었는데 같은 스터디 멤버인 `가니`님의 해석 방식을 보고 깨닫게 되었다. 
문제에서 가장 중요한 문구는 다음과 같다  
>귀하는 한 번에 주식의 최대 1 주만 보유할 수 있습니다

처음에 이것을 가지고 계속 루프하여 찾아가야 했는데 `가니`님이 엑셀에 장표를 그리고 알려준 결과 다음과 같은 규칙이 존재하였다
![](https://images.velog.io/images/mertyn88/post/708dc946-8c9a-4389-b7b3-4f416b977610/image.png)

단순히 _**현재값과 다음값의 차이값이 양수 일 경우 매수 매도 시 이익이 발생한다**_ 라고 판단을 할 수 있었다(이것이 알고리즘...).

즉 다음과 같은 조건값이 결정된다
> Array[i] - Array[i-1] > 0 을 충족하면, 이익이 발생한다.


#### 이슈케이스
이번에는 문제를 해결할 때에, 풀이 방식이 먼저 공유가 되어서, 특별한 삽질을 하지 않았다.


#### 전체코드

* prices 배열의 길이만큼 IntStream을 수행 그때의 idx는 배열의 값이 아니라 배열의 인덱스
* 배열의 인덱스 값(Value)을 연산(arr[idx] - arr[idx-1])한 값이 0보다 큰 경우에만 대상으로 filter
* filter된 연산값으로만 새로운 배열(.map) 처리
* 새로운 배열의 합(.sum)을 return 
```java
public int maxProfit(int[] prices) {
    return IntStream.range(1, prices.length).filter(idx -> prices[idx] - prices[idx-1] > 0).map(idx -> prices[idx] - prices[idx-1]).sum();
}
```

### 테스트 결과
![](https://images.velog.io/images/mertyn88/post/45464230-7b78-4956-8ebb-9f16399767d4/image.png)


### 후기
리팩토링을 하다보니 한줄로 끝낼 수 있었다. 내가 생각한 알고리즘이 아니라 이용만 한것이기에 해결했다는 느낌이 들지 않는다. 나도 고민하다보면 이러한 규칙을 찾을수 있었을까

</div>
</details>

---

## LeetCode - Jump Game (2021. 08. 29)
### 문제 및 풀이 주소
[LeetCode](https://leetcode.com/problems/jump-game/)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/leetcode/jumpgame/Solution.java)

### 문제 설명 - 영문
>You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.
Return true if you can reach the last index, or false otherwise.

### 문제 설명 - 번역
>정수 배열이 제공됩니다 nums. 처음에는 배열의 첫 번째 인덱스에 위치하며 배열의 각 요소는 해당 위치에서 최대 점프 길이를 나타냅니다.
true마지막 인덱스에 도달할 수 있으면 반환 하고 false그렇지 않으면 .


예 1:
>Input: nums = [2,3,1,1,4]
Output: true
Explanation: 인덱스 0에서 1로 1단계 이동한 다음 마지막 인덱스로 3단계 이동합니다.

예 2:
>Input: nums = [3,2,1,0,4]
Output: false
Explanation: 무슨 일이 있어도 항상 인덱스 3에 도달합니다. 최대 점프 길이는 0이므로 마지막 인덱스에 도달할 수 없습니다.


제한사항
1 <= nums.length <= 104
0 <= nums[i] <= 105

<details>
<summary>풀이 보기</summary>
<div markdown="12">

### 문제 해결
대상 배열인덱스에서 모든 탐색이 되어야 하므로 bfs나 dfs가 필수적이라고 생각되었다. 하지만 점점 산으로 간다. 커뮤니티 멤버인 산당님에게 열심히 설명을 들었는데 나참,, 한방에 해결되네 
중요한 부분은 다음과 같다.
> #### 찾아가는 방식은 i + nums[i] 이다.
다음 인덱스 값을 구할때는 `현재 위치 인덱스값 + 해당 위치 인덱스의 Value값`

> #### 굳이 해당값을 -- 하며 찾아갈 필요가 없다.
각 인덱스를 Loop하면서 값이 없을 경우 --를 해야하는 경우 때문에 bfs나 재귀를 돌려서 찾아야 하는데 그럴필요가 없다... Loop 당 i + nums[i]의 값이 최대값일 것이고, 해당 최대값이( 매번 변경된 최대값) 그 순간의 i값( 위치값 ) 에 도달하는가 못하는가만 확인하면 된다.


#### 이슈케이스
bfs로 탐색을 하려니까 해당 부분에 값이 0이 발생할 경우, 한단계 -- 값을 하여 처리하고, 여러 뎁스를 처리하면서 지속적으로 0이 발생하면 또다시 -- 하여 재귀처리가 되어야 하는데, 너무 많은 뎁스에 대해서 처리가 불가능해졌다. 


#### 전체코드

폭탄의 위치값을 찾는 메소드

```java
    public boolean canJump(int[] nums) {
        // nums가 한글자인 경우 0이든 0초과이든 무조건 true
        if(nums.length == 1){
            return true;
        }

        int maxIdx = Integer.MIN_VALUE;        // 인덱스당 최대값 설정
        int lastIdx = nums.length - 1;         // 목표 인덱스 값 설정
        for(int i = 0; i < nums.length; i++){
            maxIdx = Math.max(maxIdx, i + nums[i]); // idx + nums[idx]가 점프해야하는 위치값
            if(maxIdx <= i){    // maxIdx의 값이 대상 위치 인덱스보다 작을 경우 더이상 진행이 불가능하다.
                return false;
            }else if(maxIdx >= lastIdx) {   // maxIdx의 값이 루프가 돌기전에 이미 마지막 값을 넘겼다면 통과
                return true;
            }
        }
        return false;
    }
```

### 테스트 결과

![](https://images.velog.io/images/mertyn88/post/8820a5b4-d2de-4b31-9073-3c1a41aeedd2/image.png)

### 후기
도대체 이런 알고리즘적 생각은 어떻게 해야 잘 할수 있을까,, 이런 문제는 레벨이 낮아도 이런 방식을 모르면 엄청나게 삽질하던지(근데 나는 잘 짜지도 못해서 실패겠지), 시간초과가 뜨던지 할텐데 거참..
후.. 하면 할수록 어려운 알고리즘

</div>
</details>

---
## The Bomberman Game (2021. 08. 11)
### 문제 및 풀이 주소
[HackerRank](https://www.hackerrank.com/challenges/bomber-man/problem)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/hackerrank/bomberman/Solution.java)

### 문제 설명 - 영문
>Bomberman lives in a rectangular grid. Each cell in the grid either contains a bomb or nothing at all.
Each bomb can be planted in any cell of the grid but once planted, it will detonate after exactly 3 seconds. Once a bomb detonates, it's destroyed — along with anything in its four neighboring cells. This means that if a bomb detonates in cell , any valid cells (i±1,j)  and (i,j±1) are cleared. If there is a bomb in a neighboring cell, the neighboring bomb is destroyed without detonating, so there's no chain reaction.
Bomberman is immune to bombs, so he can move freely throughout the grid. Here's what he does:
Initially, Bomberman arbitrarily plants bombs in some of the cells, the initial state.
After one second, Bomberman does nothing.
After one more second, Bomberman plants bombs in all cells without bombs, thus filling the whole grid with bombs. No bombs detonate at this point.
After one more second, any bombs planted exactly three seconds ago will detonate. Here, Bomberman stands back and observes.
Bomberman then repeats steps 3 and 4 indefinitely.
Note that during every second Bomberman plants bombs, the bombs are planted simultaneously (i.e., at the exact same moment), and any bombs planted at the same time will detonate at the same time.
Given the initial configuration of the grid with the locations of Bomberman's first batch of planted bombs, determine the state of the grid after  seconds.
For example, if the initial grid looks like:

### 문제 설명 - 번역
>Bomberman 은 직사각형 격자에 살고 있습니다. 그리드의 각 셀에는 폭탄이 있거나 전혀 포함되어 있지 않습니다.
각 폭탄은 그리드의 모든 셀에 설치할 수 있지만 일단 설치하면 정확히 3초 후에 폭발 합니다. 폭탄이 터지면 4개의 인접한 셀에 있는 모든 것과 함께 파괴됩니다. 즉, 폭탄이 세포에서 폭발하면, 모든 유효한 셀  (i±1,j) 그리고 (i,j±1) 지워집니다. 이웃 셀에 폭탄이 있으면 이웃 폭탄은 폭발 하지 않고 파괴 되므로 연쇄 반응이 없습니다.
Bomberman은 폭탄에 면역이므로 그리드 전체를 자유롭게 이동할 수 있습니다. 그가 하는 일은 다음과 같습니다.
초기에 Bomberman은 초기 상태인 일부 세포에 임의로 폭탄을 설치합니다.
* 1초 후 Bomberman은 아무 것도 하지 않습니다.
* 1초 후에 Bomberman은 폭탄 없이 모든 셀에 폭탄을 설치하여 전체 그리드를 폭탄으로 채웁니다. 이 시점에서 폭탄이 터지지 않습니다.
* 1초가 더 지나면 정확히 3초 전에 설치한 폭탄이 폭발합니다. 여기에서 Bomberman은 뒤로 물러서서 관찰합니다.
* 그런 다음 Bomberman은 3단계와 4단계를 무기한 반복합니다.  

>매초 Bomberman이 폭탄을 설치하는 동안 폭탄은 동시에(즉, 정확히 같은 순간에 ) 설치되며, 동시에 설치된 폭탄은 동시에 폭발합니다.
Bomberman의 첫 번째 배치 폭탄 위치와 함께 그리드의 초기 구성이 주어지면  초.

제한사항
![](https://images.velog.io/images/mertyn88/post/abbb1518-916f-400a-93ab-c629a0abd88f/image.png)

<details>
<summary>풀이 보기</summary>
<div markdown="11">

### 문제 해결
영어라서 문제를 이해하는데 시간이 가장 오래걸린것 같다. 문제의 케이스를 나열해보니 시간에 따른 폭탄의 변화인데 모양이 변하는것이 규칙적으로 변하기 때문에 굳이 모든 시간을 다 계산할 필요가 없다고 생각하였다. 매번 변하는 모양을 어떻게 할당해서 처리할까 하다가 `List 컬렉션에서 set메소드`(업데이트)가 있어서 유용하게 사용해보았다. (실무에서도 안써본걸 이렇게 적용해본다)

#### 이슈케이스

계산을 3X3의 형태로 해보았을 때, 결국 설명만 어렵지 초기버전과 폭탄이 터진 버전, 전체가 폭탄으로 채워진 버전 총 3가지만 있으면 될것으로 보였다. 2의 배수 시간에서는 항상 채워진 폭탄이고 원복버전인지 폭탄이 터진버진인지는 다음과 같이 생각하였다.
```bash
1   5   9    13    17 # 최초 폭탄버전
  3   7   11    15    # 1차폭발이후 폭탄버전
```
보아하니 4의 나머지값이 1인 경우와 3인경우만 판별하면 되겠다 싶었다. 그런데 테스트케이스에서 실패가 떨어졌는데 초기버전모양이 아니였다.(테스트를 3x3으로 해서..)
잘못된 테스트 케이스는 다음과 같다
```bash
# 초기 버전
.......
...O.O.
....O..
..O....
OO...OO
OO.O...
# 1차 폭발 버전
OOO.O.O
OO.....
OO....O
.......
.......
.......
# 2차 폭발 버전
.......
...O.O.
...OO..
..OOOO.
OOOOOOO
OOOOOOO
```
보이는가,,, 초기버전과 2차 폭발 버전은 너무나도 다르게 나왔다. 이문제를 모르고 나는 내가 문제를 잘못 이해한줄알고 시간을 또다시 소비하게 되었다.

#### 문제 해결

폭탄의 위치값을 찾는 메소드

```java
    private static List<Integer> getIndex(String str) {
        List<Integer> result = new ArrayList<>();
        if(str.contains("O")){
            int i = str.indexOf("O");
            result.add(i);
            while(i >= 0) {
                i = str.indexOf("O", i+1);
                if(i > 0){
                    result.add(i);
                }
            }
        }
        return result;
    }
```

모든 영역을 폭탄으로 채우는 메소드

```java
    public static List<String> fillBomber(List<String> grid){
        return grid.stream().map(a -> a.replace(".", "O")).collect(Collectors.toList());
    }
```
폭탄이 터졌을때의 대상의 값을 변환시키는 메소드
```java
    public static String replaceValue(String str, int idx) {
        String[] tempArr = str.split("");
        tempArr[idx] = ".";
        return String.join("",tempArr);
    }
```
폭탄이 터졌을때의 본인을 포함한 상하좌우 Validation 및 값 설정 메소드
* 범위를 벗어날 경우에는 처리하지 않는다.
* 상하의 관계에서는 Row의 값만 변경되는것이므로 Key 값을 ±1 한다.
* 좌우의 관계에서는 Column의 값만 변경되는것이므로 Value 값을 ±1 한다.

```java
    // up validation & set
    if(key != 0){
        convertGrid.set(key - 1, replaceValue(convertGrid.get(key - 1), val));
    }
    // down validation & set
    if(key != row - 1){
        convertGrid.set(key + 1, replaceValue(convertGrid.get(key + 1), val));
    }
    // left validation & set
    if(val != 0){
        convertGrid.set(key, replaceValue(convertGrid.get(key), val - 1));
    }
    // right validation & set
    if(val != column - 1){
        convertGrid.set(key, replaceValue(convertGrid.get(key), val + 1));
    }
    // center set
    convertGrid.set(key, replaceValue(convertGrid.get(key), val));
```


### 테스트 결과

![](https://images.velog.io/images/mertyn88/post/b7f85dfb-0ecd-4821-87b6-2b1080d5b50f/image.png)

### 후기
문제를 이해하고 나서는 상하좌우 계산 때문에 시간이 걸리긴 했지만 난이도 자체는 낮았다고 생각된다.(내가 한번에 풀었으니까...;;;) 단, 테스트 케이스에서 저런 예외를 알려주지 않고 Hidden으로 처리되었다면 끔찍한 일이 벌어졌을지도...

</div>
</details>

---
## 섬연결 (2021. 07. 30) Connect Island - Greedy, Kruskal
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/42861)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/connectisland/Solution.java)

### 문제 설명
>n개의 섬 사이에 다리를 건설하는 비용(costs)이 주어질 때, 최소의 비용으로 모든 섬이 서로 통행 가능하도록 만들 때 필요한 최소 비용을 return 하도록 solution을 완성하세요.
다리를 여러 번 건너더라도, 도달할 수만 있으면 통행 가능하다고 봅니다. 예를 들어 A 섬과 B 섬 사이에 다리가 있고, B 섬과 C 섬 사이에 다리가 있으면 A 섬과 C 섬은 서로 통행 가능합니다.

제한사항
> * 섬의 개수 n은 1 이상 100 이하입니다.
* costs의 길이는 ((n-1) * n) / 2이하입니다.
* 임의의 i에 대해, costs[i][0] 와 costs[i] [1]에는 다리가 연결되는 두 섬의 번호가 들어있고, costs[i] [2]에는 이 두 섬을 연결하는 다리를 건설할 때 드는 비용입니다.
* 같은 연결은 두 번 주어지지 않습니다. 또한 순서가 바뀌더라도 같은 연결로 봅니다. 즉 0과 1 사이를 연결하는 비용이 주어졌을 때, 1과 0의 비용이 주어지지 않습니다.
* 모든 섬 사이의 다리 건설 비용이 주어지지 않습니다. 이 경우, 두 섬 사이의 건설이 불가능한 것으로 봅니다.
* 연결할 수 없는 섬은 주어지지 않습니다.

입출력 예

|n|costs|return|
|---|---|---|
|4|[[0,1,1],[0,2,2],[1,2,5],[1,3,1],[2,3,8]]|4|

입출력 예 설명
> costs를 그림으로 표현하면 다음과 같으며, 이때 초록색 경로로 연결하는 것이 가장 적은 비용으로 모두를 통행할 수 있도록 만드는 방법입니다.

![](https://images.velog.io/images/mertyn88/post/bfb3b271-eaff-43ee-aec2-9d771dcc2ade/image.png)

<details>
<summary>풀이 보기</summary>
<div markdown="10">

### 문제 해결
해당 문제는 주제인 탐욕법을 포함해서 또한가지의 알고리즘이 필요했다.(엄청난 삽질 또해버린..) 필요한 알고리즘은 다음과 같다. 

> _**크루스칼 알고리즘**_
* 그래프 내의 모든 정점을 포함하고 사이클이 없는 연결 선을 그렸을 때, 가중치의 합이 최소가 되는 상황을 구하고 싶을 때 사용
* 최소 신장 트리를 구하기 위한 알고리즘
* 실제 크루스칼 알고리즘은 배열 초기값을 해당 노드의 값으로 하였는데 그부분을 안보고 구현하다보니 초기값을 -1로 지정
---
_**크루스칼 알고리즘 구현 방식**_
* 배열의 시작값과, 종료값에 대한 탐색(DFS)으로 최종 부모값을 찾는다.
* 구해진 시작값에 대한 부모값과, 종료값에 대한 부모값을 비교한다.
    * 비교값이 큰값이 방문배열의 인덱스 값이되고, 작은 값이 해당 방문배열의 값이 된다.
* 시작값에 대한 부모값과, 종료값에 대한 부모값이 같은 경우, 만들어지면 안되는 `사이클`의 형태가 되므로 (A->B<->C) 아무런 행위를 하지 않는다.


#### 이슈케이스 및 해결 방안 추측
초기에는 크루스칼 알고리즘을 사용하지 않고 내가 정의한 조건들을 지속적으로 추가해서 구하는 방식으로 하였다.(틀린방식이라고는 할 수 없지만 조건이 점점 산으로 간다.) 사이클이 만들어지지 않기위한 방식을 각각의 시작값 배열과 종료값 배열로 나누어서 처리하고, 그것에 대해서 상황별 사이클이 만들어지는 조건값을 추가하였는데, 엄청난 테스트 케이스를 추가하고 조건에 성립함에도 불구하고 실제 테스트 케이스에서 실패를 하니, 각각의 조건이 상황에 따라 다르게 있는것이 있어 해당 방식은 불가능한것을 깨달았다.


#### 이슈 케이스

#### 문제 해결
크루스칼 알고리즘을 사용하였다.

최상위 부모노드를 찾기 위한 코드는 다음과 같다
```java
// 재귀함수를 사용하여 -1값이 나올때까지
private int findRoot(int child, int[] visitArr) {
    if(child == visitArr[child]){
        return child;
    }else if (visitArr[child] == -1) {
        return child; // 기존 부모값 사용
    } else {
        return findRoot(visitArr[child], visitArr);
    }
}
```

방문배열에 값을 설정하기 위한 조건은 다음과 같다
```java
// 순서가 상관없으므로 두 root값을 모두 비교해야한다.
// 큰값이 방문배열 Index, 작은값이 방문배열 Value
if(startRoot > endRoot){
    visitArr[startRoot] = endRoot;
}else{
    visitArr[endRoot] = startRoot;
}
// 양쪽 부모가 같지 않으므로 연결이 가능하므로 연산한다.
answer += cost[2];
```


### 테스트 결과
|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|---|---|---|
|테스트 1|통과|(11.49ms, 53.5MB)|테스트 5|통과|(6.98ms, 52.8MB)|
|테스트 2|통과|(4.35ms, 52.3MB)|테스트 6|통과|(6.56ms, 52.4MB)|
|테스트 3|통과|(4.39ms, 53.3MB)|테스트 7|통과|(12.55ms, 52.1MB)|
|테스트 4|통과|(9.10ms, 53.3MB)|테스트 8|통과|(6.82ms, 52.1MB)|

### 후기
해당 문제는 분명 정해진 알고리즘(쿠르스칼)을 사용하지 않고 조건을 잘 설정하기만 해도 가능할 것으로 보인다. 문제에 해당하는 조건을 생각해 보았지만 구멍이 계속 존재하였고 원하는 결과가 나오지 않았다..

</div>
</details>

---
## 단속카메라 (2021. 07. 28) Security Camera / 탐욕법
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/42884)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/securitycamera/Solution.java)

### 문제 설명
>고속도로를 이동하는 모든 차량이 고속도로를 이용하면서 단속용 카메라를 한 번은 만나도록 카메라를 설치하려고 합니다.
고속도로를 이동하는 차량의 경로 routes가 매개변수로 주어질 때, 모든 차량이 한 번은 단속용 카메라를 만나도록 하려면 최소 몇 대의 카메라를 설치해야 하는지를 return 하도록 solution 함수를 완성하세요.

>제한사항
* 차량의 대수는 1대 이상 10,000대 이하입니다.
* routes에는 차량의 이동 경로가 포함되어 있으며 routes[i][0]에는 i번째 차량이 고속도로에 진입한 지점, routes[i][1]에는 i번째 차량이 고속도로에서 나간 지점이 적혀 있습니다.
* 차량의 진입/진출 지점에 카메라가 설치되어 있어도 카메라를 만난것으로 간주합니다.
* 차량의 진입 지점, 진출 지점은 -30,000 이상 30,000 이하입니다.

입출력 예

|routes|return|
|---|---|
|[[-20,15], [-14,-5], [-18,-13], [-5,-3]]|2|

> 입출력 예 설명
* -5 지점에 카메라를 설치하면 두 번째, 네 번째 차량이 카메라를 만납니다.
* -15 지점에 카메라를 설치하면 첫 번째, 세 번째 차량이 카메라를 만납니다.

<details>
<summary>풀이 보기</summary>
<div markdown="9">

### 문제 해결
해당 문제는 프로그래머스 탐욕법(Greedy)에 관한 문제이니, 탐욕법이 무엇인지 먼저 찾아보았다. 탐욕법이란,  
> * 현재 주어진 상황에서 최고의 조건을 찾는것
* 다음 상황에서의 최고의 조건을 순차적으로 찾는다.
* 주어진 문제는 대상이 되는 `컬럼을 원하는 형태의 정렬`이 되어야 한다.

인데, 주어진 문제는 컬럼들의 `도착시간`을 기준으로 정렬을 해야한다.

#### 해결 방안 추측
문제의 예시와는 상관없이, 정렬이 된 상태에서 순차적으로 Loop하며 대상 순서가 될때 마다 `시작시간 ~ 도착시간에 포함되는 것을 체크`하며, 모든 체크 배열이 완성되면 빠져나오는 것으로 하였다.


#### 이슈 케이스
사실 처음에는 다른문제와 마찬가지로 삽질의 늪에서 빠져나올 수 없었다. 예시의 저 결과는 어째서 저렇게 나온것인가.. 에서 이해하는데 한참을 헤매버렸다.

#### 문제 해결
> 각각의 풀이에 의한 설치되는 개수가 중요하다

나는 추측에 작성한대로 Loop하며 해당 시간 범위에 포함되는 모든 대상을 방문대상으로 하여 처리한다고 하였다. 즉 예제에서는 4개의 컬럼을 2개씩 잡아서 보여주었지만, 시간 범위에 들어올 수 있으면 1대의 카메라에 3개의 컬럼값이 올 수도 있는것이다. 즉, 실제 구현은 3개의 컬럼, 1개의 컬럼으로 묶었지만 결과는 설치하는 카메라의 개수만 반환하면 되므로 결과값은 동일하게 2이다.

가장 중요한 시간범위의 체크 코드는 다음과 같다
```java
// arr1의 값을 중심으로 비교하므로 정렬의 순서가 필수적으로 중요해졌다.
private boolean isContains(int[] arr1, int[] arr2){
    if(arr2[0] <= arr1[0] && arr1[0] <= arr2[1]){
        // -20 <= -18 <= 15
        return true;
    }else if(arr2[0] <= arr1[1] && arr1[1] <= arr2[1]){
        // -20 <= -13 <= 15
        return true;
    }
    return false;
}
```

#### 이슈 케이스
문제를 해결해도 효율성 체크에서 시간초과가 떠버렸다. 문제의 코드는 아마 stream api를 사용하거나 Collection을 사용하는 부분일 것이다. (항상 그래왔다..)

#### 문제 해결
최초 초기 정렬을 stream으로 표현했는데 다음과 같이 변경하였다.
```java
// 변경 전, 효율성 테스트 미통과
int[][] sortArr = Arrays.stream(routes).sorted(Comparator.comparing(route -> route[1])).toArray(int[][]::new);

// 변경 후, 효율성 테스트 통과
Arrays.sort(routes, (o1, o2) -> {
    if(o1[1] == o2[1])
        return Integer.compare(o1[0], o2[0]);
    else
        return Integer.compare(o1[1], o2[1]);
});
```
즉, 효율성을 같이 따지는 문제는 Stream과 Collection의 사용을 하면 안된다. (실무처럼 하기 위해 Stream을 많이사용 하고 있는데 배열만 사용해야한다니..)

### 테스트 결과 - 정확성
|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|
|테스트 1|통과|(0.81ms, 52MB)|
|테스트 2|통과|(0.88ms, 54.3MB)|
|테스트 3|통과|(1.00ms, 51.5MB)|
|테스트 4|통과|(1.02ms, 53MB)|
|테스트 5|통과|(1.05ms, 53.4MB)|

### 테스트 결과 - 효율성
|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|
|테스트 1|통과|(16.22ms, 52.8MB)|
|테스트 2|통과|(12.55ms, 52.8MB)|
|테스트 3|통과|(31.58ms, 56.1MB)|
|테스트 4|통과|(1.62ms, 52.5MB)|
|테스트 5|통과|(55.86ms, 56.6MB)|

### 후기
확실히 카카오를 하다가 다시 프로그래머스의 주제에 관련된 문제를 푸니, 먼저 해당 알고리즘을 공부하고 하니 훨씬 쉽게 접근을 할 수 있었다. 여러 알고리즘과, 여러 풀이법을 경험하고나서 카카오문제를 접하는것이 나을듯 하다..

</div>
</details>

---

## 자물쇠와 열쇠 (2021. 07. 20) Lock
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/60059)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/kakao/lock/Solution.java)

### 문제 설명
> 고고학자인 "튜브"는 고대 유적지에서 보물과 유적이 가득할 것으로 추정되는 비밀의 문을 발견하였습니다. 그런데 문을 열려고 살펴보니 특이한 형태의 자물쇠로 잠겨 있었고 문 앞에는 특이한 형태의 열쇠와 함께 자물쇠를 푸는 방법에 대해 다음과 같이 설명해 주는 종이가 발견되었습니다.
잠겨있는 자물쇠는 격자 한 칸의 크기가 1 x 1인 N x N 크기의 정사각 격자 형태이고 특이한 모양의 열쇠는 M x M 크기인 정사각 격자 형태로 되어 있습니다.
자물쇠에는 홈이 파여 있고 열쇠 또한 홈과 돌기 부분이 있습니다. 열쇠는 회전과 이동이 가능하며 열쇠의 돌기 부분을 자물쇠의 홈 부분에 딱 맞게 채우면 자물쇠가 열리게 되는 구조입니다. 자물쇠 영역을 벗어난 부분에 있는 열쇠의 홈과 돌기는 자물쇠를 여는 데 영향을 주지 않지만, 자물쇠 영역 내에서는 열쇠의 돌기 부분과 자물쇠의 홈 부분이 정확히 일치해야 하며 열쇠의 돌기와 자물쇠의 돌기가 만나서는 안됩니다. 또한 자물쇠의 모든 홈을 채워 비어있는 곳이 없어야 자물쇠를 열 수 있습니다.
열쇠를 나타내는 2차원 배열 key와 자물쇠를 나타내는 2차원 배열 lock이 매개변수로 주어질 때, 열쇠로 자물쇠를 열수 있으면 true를, 열 수 없으면 false를 return 하도록 solution 함수를 완성해주세요.

> 제한사항
* key는 M x M(3 ≤ M ≤ 20, M은 자연수)크기 2차원 배열입니다.
* lock은 N x N(3 ≤ N ≤ 20, N은 자연수)크기 2차원 배열입니다.
* M은 항상 N 이하입니다.
* key와 lock의 원소는 0 또는 1로 이루어져 있습니다.
* 0은 홈 부분, 1은 돌기 부분을 나타냅니다.

|key|lock|result|
|---|---|---|
|[[0, 0, 0], [1, 0, 0], [0, 1, 1]]|[[1, 1, 1], [1, 1, 0], [1, 0, 1]]|true|

<details>
<summary>풀이 보기</summary>
<div markdown="8">


### 문제 해결

#### 해결 방안 추측
문제를 보고 이해할때, `탐색`에 관련된 문제인것은 느낄 수 있었으나, _**'어라? 결국 맞는지 틀린지 true/false만 체크하면 되니까 패턴값으로 비교하면 더 편하지 않을까?**_ 했었다. 하지만 미친듯한 예외 상황과 테스트케이스의 실패, 결과값을 체크할때 true/false니까 명확하게 잘못된 테스트 케이스를 알수가 없었다. 구현 방식을 일반방식과 다르게 하려고도 하니 구글링의 도움을 받을 수 조차 없었다. 처음에 문제를 포기하려고 했으나, 스터디 모임의 _**잡부**_(감사합니다)님에게 가능할 것 같다는 의견을 듣고나서, 현재 문제를 해결하였다.

#### 이슈 케이스
> 배열을 인덱스화 하여 매칭을 하려고 했으나, 해당 사항을 체크하지 못하였다.

![](https://images.velog.io/images/mertyn88/post/91c44dae-8336-4835-8b85-461b60bfbf25/image.png)

그림을 보면 lock과 key의 패턴이 그림과 같을 때, `11 인덱스`를 기준으로 패턴을 만들 때, 계산 공식에 의해서 `14 인덱스`의 위치값이 변하게 된다.. 공식 자체에는 문제가 없었으나(알고보니 공식도 틀렸었다) 이런 케이스 에서 원하는 패턴값이 만들어 지지 않는 다는것을 깨달았다.

#### 문제 해결
> lock 배열의 크기를 가상 배열로 만들어서 이슈케이스처럼 값이 넘어가는 경우가 없도록 하자!

애초에 값이 넘어갈 수 없는 상황을 만들면 될 것 같았다.(나중에 답보니까 확장해서 한 사람들이 많았다)
그렇다면 어느 정도 크기까지 확장해야 할까? 하다가 다음과 같이 기준을 정하였다.
* key 배열의 최대 크기는 lock 배열의 크기니까 key 배열과 lock 배열이 같을 경우가 가장 큰 값이다.
* lock배열의 끝 자리 인덱스에서 패턴을 찾아야 할 경우가 있으므로 최대값은 3배로 한다.
* 3배인 경우에는 항상 lock 배열의 실제 값은 `가운데`에 위치해야 한다. ( 그래야 상하좌우 다 커버 가능)

다음과 같은 조건이 되면 해당 그림처럼 나오게 된다.
![](https://images.velog.io/images/mertyn88/post/3f94b33c-3d44-4b6c-8049-61772cf4e182/image.png)

끝자리에 매칭해야할 번호가 있어도, 절대로 패턴의 모양이 변하지 않는다.(가상 범위값을 N*3 -1 로 해도 될듯하다, 겹쳐지는 라인은 반드시 필요하니까)

해당 인덱스를 만드는 코드는 다음과 같다.
```java
/*
        virtualArr               virtualRealLockList
        1 1 1 1 1 1 1 1 1 
        1 1 1 1 1 1 1 1 1 
        1 1 1 1 1 1 1 1 1 
        1 1 1 1 1 1 1 1 1              30 31 32
        1 1 1 1 1 0 1 1 1              39 40 41
        1 1 1 1 0 1 1 1 1              48 49 50
        1 1 1 1 1 1 1 1 1 
        1 1 1 1 1 1 1 1 1 
        1 1 1 1 1 1 1 1 1
*/
public void setVirtualLock(int[][] lock){
    // 자물쇠의 크기를 확장하는데 자물쇠의 크기와 키의 크기가 같을 경우, 앞뒤로 키의 배열이 올 수 있으므로 총 3배
    // 실제 크기
    int length = lock.length;
    // 관련 변수 초기화
    virtualArr = new int[length*3][length*3];
    virtualRealLockList = new ArrayList<>();

    // 가상 배열 적용 loop
    int count = 0;
    for(int i = 0; i < virtualArr.length; i++){
        for(int j = 0; j < virtualArr.length; j++){
            // 가상 배열 범위중, 실제 lock 배열의 값을 가운데에다가 설정
            // 조건은 (0,0) ~ (length-1, length-1)이 만들어지는 실제 범위 이므로 (length, length) ~ (length * 2 -1, length * 2 -1)로 변환한다.
            if(i >= length && j >= length && i <= (length * 2) -1 && j <= (length * 2) -1){
                virtualArr[i][j] = lock[i-length][j-length];
                virtualRealLockList.add(count);
            }else{
                virtualArr[i][j] = 1;
            }
            count++;
        }
    }
}
```

#### 공식 만들기 - 로테이션
배열의 값을 회전하기 위해서 다음과 같은 공식을 만들어 적용하였다.
>_**N \* (N - 행번호) - (N - 열번호)**_  
int locationValue = size * (size - row) - (size - column);

먼저 2차원 배열을 1차원 배열로 flat하게 펼쳐놓은 다음, loop를 돌면서 `locationValue` 변수의 값을 flat배열에서 찾아서 새로이 [0][0]부터 채운다.

코드는 다음과 같다.
```java
public int[][] rotation(int[][] arr){
    // 2차원 배열을 1차원 배열로 변환한다.
    int[] flatArr = Arrays.stream(arr).flatMapToInt(Arrays::stream).toArray();

    // loop 관련 변수
    int size = arr.length;
    int column = 0;
    int row = 0;

    // 임시 1차원 배열
    int[] tempArr = new int[size * size];
    for(int idx = 0; idx < flatArr.length; idx++){
        // 로테이션 위치값 - 공식에 의한 인덱스값을 찾는다.
        //N * (N - 0) - (N - 1)|N * (N - 1) - (N - 1)|N * (N - 2) - (N - 1)
        //N * (N - 0) - (N - 2)|N * (N - 1) - (N - 2)|N * (N - 2) - (N - 2)
        //N * (N - 0) - (N - 3)|N * (N - 1) - (N - 3)|N * (N - 2) - (N - 3)
        int locationValue = size * (size - row) - (size - column);
        // 인덱스값을 1차원으로 펼친 flatArr에서 값을 임시 1차원 배열에 할당한다.
        tempArr[idx] = flatArr[locationValue];
        if( row == (size - 1)){
            row = 0;
            column++;
        }else{
            row++;
        }
    }

    // 1차원 temp 배열을 2차원 result 배열로 변환
    int[][] result = new int[size][size];
    row = 0;
    column = 0;
    for (int value : tempArr) {
        result[column][row] = value;
        if ((size - 1) == row) {
            row = 0;
            column++;
        } else {
            row++;
        }
    }
    return result;
}
```

#### 공식만들기 - 패턴값 찾기
패턴을 찾기 위해 다음과 같은 공식을 적용하였다.
> _**(lock기준점 - key기준점) + key value + (lock size - key size) \* ((key value / key size) - key기준열)**_  
굉장히 복잡하여 코드에서는 변수로 나누어서 적용하였다. 솔직히 나도 내가 이런 공식을 만들어 낼줄은 몰랐다.. 굉장히 오랜시간 엑셀을 끄적이면서 테스트 하고 좌절하였다. 물론 실제 코딩테스트를 본다면 나는 바로 떨어지겠지

코드는 다음과 같다(어려우므로 각 변수에 주석을 상세히 적었다.)
목표하는 바는 다음과 같다.
* key와 lock의 배열값이 달라져도 **패턴의 모양은 변하면 안된다**.
* 인덱스의 번호값이 완전히 일치해야 하므로 lock배열의 위치중 하나가 key배열의 위치중 하나와 일치시킨다음** 만들어진 패턴값을 비교**한다.
* 필터링을 거쳐 lock패턴과 key패턴이 사이즈도 같고 저장된 데이터가 같으면 true를 반환한다.

```java
/*
    lock의 41번 인덱스가 key의 3인것으로 가정하고 패턴을 만들었을 경우
    standardPoint  38 | 38 | 38
    basePoint      41 | 45 | 46
    plusPoint       0 |  6 |  6
    newPoint       41 | 51 | 52
*/
// 변환된 lock 배열의 특정 위치에서 key의 패턴을 만드는 메소드
private List<Integer> findPattern(List<Integer> keyList, int standardLockNo, int idx){
    List<Integer> resultList = new ArrayList<>();
    // lock 패턴의 기준점 계산, key 패턴이 loop하면서 해당 값이 loop 패턴의 값이 되도록 하기 위해
    int standardPoint = standardLockNo - keyList.get(idx);
    // key 패턴의 값이 key 배열의 몇번째 row에 있는지 계산하기 위해
    int standardRow = keyList.get(idx) / keyLength;
    // key 패턴 loop
    for(int keyNo : keyList){
        // 기본값, 각 기준에 해당하는 key 패턴은 해당 lock 패턴의 값과 일치하게 나와야 한다.
        int basePoint = keyNo + standardPoint;
        // lockLength - keyLength -> 배열 크기가 다른 lock과 key배열의 차이
        // (keyNo / keyLength) - standardRow -> key 패턴의 값이 실제 어느 열에 존재하는지
        // lock 패턴과 key 패턴의 크기가 다를때, 같은 패턴의 모양을 만들어주기 위해 크기가 차이나는 만큼 인덱스 값을 더해서 만든다.
        int plusPoint = (lockLength - keyLength) * ((keyNo / keyLength) - standardRow);
        // basePoint와 plusPoint의 합으로 최종적인 virtual lock pattern의 위치 값을 찾는다.
        int newPoint = basePoint + plusPoint;

        // 필터링 - 확장되었지만 실제로는 확정 전의 lock 패턴에 값이 존재하는 key의 값만이 대상이 되므로
        // 해당 범위가 아닌 point값은 lock의 실제범위를 벗어나는 값이다.
        if(virtualRealLockList.contains(newPoint)){
            resultList.add(newPoint);
        }
    }
    return resultList;
}
```

```java
// 최종적으로 필터링 이후 만들어진 두 패턴을 비교하는 메소드
private boolean diffList(List<Integer> keyList, List<Integer> lockList){
    // 실제 범위이외의 데이터는 필터링 처리 되었기 때문에 두 리스트의 사이즈는 같으며 포함되어야 한다.
    // 단순 contains 사용시, 패턴에서 자물쇠와 키가 둘다 1인 케이스가 있어서 테스트 케이스 3개가 실패한다.
    // 참고) 실제 정답에서는 XOR처리라고 되어있다.
    if(keyList.size() == lockList.size()){
        return lockList.containsAll(keyList);
    }
    return false;
}
```


### 테스트 결과

|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|---|---|---|
|테스트 1|통과|(3.27ms, 52.7MB)|테스트 20|통과|(116.46ms, 58.8MB)
|테스트 2|통과|(0.11ms, 52.1MB)|테스트 21|통과|(8.61ms, 54.3MB)
|테스트 3|통과|(24.22ms, 54.9MB)|테스트 22|통과|(90.99ms, 67.5MB)
|테스트 4|통과|(0.11ms, 54MB)|테스트 23|통과|(8.79ms, 52.8MB)
|테스트 5|통과|(42.27ms, 56.9MB)|테스트 24|통과|(15.76ms, 52.5MB)
|테스트 6|통과|(23.67ms, 52.5MB)|테스트 25|통과|(56.86ms, 56.6MB)
|테스트 7|통과|(2.50ms, 52.2MB)|테스트 26|통과|(279.80ms, 71.3MB)
|테스트 8|통과|(68.75ms, 56.8MB)|테스트 27|통과|(1932.11ms, 200MB)
|테스트 9|통과|(184.03ms, 71.7MB)|테스트 28|통과|(28.92ms, 55.1MB)
|테스트 10|통과|(758.60ms, 75.6MB)|테스트 29|통과|(10.13ms, 52.7MB)
|테스트 11|통과|(1442.06ms, 141MB)|테스트 30|통과|(60.21ms, 58.6MB)
|테스트 12|통과|(0.08ms, 52.6MB)|테스트 31|통과|(80.87ms, 60.1MB)
|테스트 13|통과|(42.50ms, 56MB)|테스트 32|통과|(481.54ms, 76.5MB)
|테스트 14|통과|(6.44ms, 51.7MB)|테스트 33|통과|(108.59ms, 70.8MB)
|테스트 15|통과|(0.66ms, 51.9MB)|테스트 34|통과|(0.49ms, 52.4MB)
|테스트 16|통과|(44.66ms, 56.5MB)|테스트 35|통과|(10.97ms, 52.6MB)
|테스트 17|통과|(14.17ms, 52.7MB)|테스트 36|통과|(17.22ms, 53MB)
|테스트 18|통과|(24.64ms, 52.8MB)|테스트 37|통과|(13.21ms, 52.6MB)
|테스트 19|통과|(4.12ms, 52.6MB)|테스트 38|통과|(10.40ms, 52.8MB)

#### 정통 풀이 방식
![](https://images.velog.io/images/mertyn88/post/33e506de-d1ee-46a5-87de-bc8ffdb8a7db/image.png)

lock의 배열과 key의 배열을 겹치게 만들어 lock배열의 모든 원소가 1인지를 체크만 하면 된다. 겹치는 부분은 그림과 같이 2X2를 잡지는 않고 겹치는 부분이 1X1로 왼쪽 위부터 1X1, 2X2.. 로 하나의 배열이 남은 하나의 배열을 탐색하면서 체크하면된다. 실제 풀이 방식을 보니 나의 방식이 얼마나 어려운 방식인지 세삼 알게되었다. (나는 공식을 만들었는데...)
또한 해당 방법에서는 lock과 key배열의 크기 차이를 알 필요가 없다. 그냥 탐색만 하면 되니까, 그리고 중요한게 lock배열과 key배열의 값이 둘다 1인경우인데 이럴때는 열쇠가 자물쇠를 통과할 수 없는 케이스가 되므로 0으로 치환하던지 return false를 해주면 된다.

### 후기

오랫동안 시간을 들였고, 많은 실패로 좌절했다. 다음부터는 문제를 보고 어떤식으로 푸는지 확인을 하고 구현을 해야겠다는 생각을 하였다. 예제로 주어지는 문제에서는 테스트케이스를 모두 체크할 수가 없으므로 실제 제대로 구현했다고 해도 테스트케이스의 실패가 너무 많았다. 지금은 공부용이라 오랜시간을 들여 풀긴 했지만, 실제로는 정해진 시간에 문제를 풀어야 하기 때문에 해당 방식처럼 혼자 다른방향으로 가는것은 하지 말야겠다. 

</div>
</details>


---

## 입국심사 (2021. 07. 12) Entry Audit (Binary Search)
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/43238)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/entryaudit/Solution.java)

### 문제 설명
> n명이 입국심사를 위해 줄을 서서 기다리고 있습니다. 각 입국심사대에 있는 심사관마다 심사하는데 걸리는 시간은 다릅니다.
처음에 모든 심사대는 비어있습니다. 한 심사대에서는 동시에 한 명만 심사를 할 수 있습니다. 가장 앞에 서 있는 사람은 비어 있는 심사대로 가서 심사를 받을 수 있습니다. 하지만 더 빨리 끝나는 심사대가 있으면 기다렸다가 그곳으로 가서 심사를 받을 수도 있습니다.
**모든 사람이 심사를 받는데 걸리는 시간을 최소로 하고 싶습니다.**
입국심사를 기다리는 사람 수 n, 각 심사관이 한 명을 심사하는데 걸리는 시간이 담긴 배열 times가 매개변수로 주어질 때, 모든 사람이 심사를 받는데 걸리는 시간의 최솟값을 return 하도록 solution 함수를 작성해주세요.

> 제한사항
* 입국심사를 기다리는 사람은 1명 이상 1,000,000,000명 이하입니다.
* 각 심사관이 한 명을 심사하는데 걸리는 시간은 1분 이상 1,000,000,000분 이하입니다.
* 심사관은 1명 이상 100,000명 이하입니다.

|n|times|return|
|---|---|---|
|6|[7, 10]|28|

<details>
<summary>풀이 보기</summary>
<div markdown="7">

### 입출력 설명
* 가장 첫 두 사람은 바로 심사를 받으러 갑니다.
* 7분이 되었을 때, 첫 번째 심사대가 비고 3번째 사람이 심사를 받습니다.
* 10분이 되었을 때, 두 번째 심사대가 비고 4번째 사람이 심사를 받습니다.
* 14분이 되었을 때, 첫 번째 심사대가 비고 5번째 사람이 심사를 받습니다.
* 20분이 되었을 때, 두 번째 심사대가 비지만 6번째 사람이 그곳에서 심사를 받지 않고 1분을 더 기다린 후에 첫 번째 심사대에서 심사를 받으면 28분에 모든 사람의 심사가 끝납니다.

### 문제 해결

#### 이슈 케이스
`해당 문제에서 이분탐색을 사용해야하는 부분이 어디인가?`에 대한곳을 찾을 수 없었다. 단순히 처음에는 Set으로 각각의 시간을 addAll하여 중복을 제거하고 마지막 n값을 get할 수 있으면 끝일줄 알았다. 하지만 역시나 테스트케이스 실패의 향연... 다시 처음으로 돌아와 이분진법을 사용해야 하는 부분을 찾기 시작했다.

> 문제의 주제가 이분탐색이므로, 어느 부분에는 반드시 이분탐색을 적용해야한다. 이분탐색에서 가장 중요한 요소는 _**정렬**_이 필수로 되어 있어야 한다. 주어지는 배열 times는 정렬이 되어 있지 않으므로 제외해야하고, 그렇다면 남은것은 시간의 _**범위**_이다. ( 실제로는 엄청난 삽질을 하였다 )

심사자가 받을 수 있는 가장 빠른 범위의 시간과, 가장 느린 범위의 시간을 구하는것으로 먼저 고민하였다. 심사관마다 시간이 주어지지만, 문제에서 중복이 안된다고 하지 않았으니까 최소의 시간은 times의 최소값, 최대의 시간은 times의 최대값으로 지정하면 될것 같고, times에서 모든 n값이 최소값과 최대값으로만 이루어진 시간을 구하기로 하였다.

```java
long divide = (long) Math.ceil((double) n / times.length);
//정렬
Arrays.sort(times);
// 최소값
long min = times[0] * divide;
// 최대값
long max = times[times.length - 1] * divide ;
```
스트림 람다를 사용하면, min과 max에 각각 적용해야 하므로, times를 Sort하는 방향으로 생각하였다. 

--- 
이후의 모든 시도는 많은 시간을 들였음에도 전부다 실패하였다... 특히나, 이분탐색을 하는 코드를 긁어와서 재귀의 방식과 while로 하는 방식 둘다 적용을 하였는데 실패했다.

#### 해결 케이스
어떻게 해결해야 할까? 하다가 결국 풀이하는 방식을 보기로 했는데, 특정 공식을 알수 없으면 문제를 해결할 수 없는것 같다.. 탐색하면서 매번 일일이 처음부터 n값이 될때까지 모든걸 계산해야 하나? 했더니 다음과 같은 공식이 있었다.
```java
for(int time : times){
    sum += int(((low+high) / 2) / time)
}
```
모든 사람을 계산하지 않고 해당 공식하나만으로 심사받는 사람의 수를 구할 수 있다... ( 너무 허무했다..) 
하지만 바로 성공할 수는 없었는데 이분탐색 예제를 그대로 적용하였더니 원하는 수가 나오지 않고 전체 탐색을 하면 다른값이 나오기 시작했다.

```java
if(sum == key){
    return mid
}else if(sum > key) {
    high = mid - 1;
} else {
    low = mid + 1;
}
```
key값이 심사받은 사람수 인데 구글링해온 이분탐색 코드는 원하는 값을 찾았을 경우, 바로 return 하는 코드였다. 해당 코드를 그대로 적용하였는데, 당연하게도 실패하였다. 실패원인은 다음과 같다.
> 특정값에서 `sum == key`의 조건이 이루어지지만, 그다음값도, 그다다음값도 `sum == key`의 값이 되는 조건들이 있다. 

여러 sum ==key의 조건 때문에 처음에는 times각각의 값에서 배수값이 필요하지 않을까 생각했는데, 실제 코드를 보니 그럴필요가 없다는것을 알았다. ( 애초에 저렇게 나누어서 저장하면 최소값이 배열값으로 되는것 같다. )

```java
if(sum < key) {
    low = mid + 1;
} else {
high = mid - 1;
    result = mid;
}
```
sum == key의 조건이 사라지고 부등호의 방향이 바뀌어서 처리되었다.
부등호의 방향은 상관없을 줄알았는데 해당 표시가 바뀜에 따라 값이 달라지게 되었다. 


### 테스트 결과

|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|---|---|---|
|테스트 1|통과|(0.72ms, 52MB)|테스트 6|통과|(109.47ms, 60MB)
|테스트 2|통과|(0.60ms, 53MB)|테스트 7|통과|(100.97ms, 60.5MB)
|테스트 3|통과|(2.64ms, 55.4MB)|테스트 8|통과|(138.49ms, 60.7MB)
|테스트 4|통과|(105.26ms, 59.6MB)|테스트 9|통과|(0.74ms, 52.4MB)
|테스트 5|통과|(100.82ms, 58.1MB)


### 후기
해당 문제는 좀... 사람수를 구하는 공식을 모르면 절대로 풀수 없는 문제 같았다. 시간도 많이 걸렸거니와 저 문제의 공식을 혼자서 스스로 도출한 것이 아니기 때문에 찝찝함이 많은 문제였다. 실제 정답코드는 저리 짧은데..

</div>
</details>

---

## 순위 (2021. 07. 04) Graph (BFS)
### 문제 및 풀이 주소
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/49191?language=java)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/setrank/Solution.java)

### 문제 설명
> n명의 권투선수가 권투 대회에 참여했고 각각 1번부터 n번까지 번호를 받았습니다. 권투 경기는 1대1 방식으로 진행이 되고, `만약 A 선수가 B 선수보다 실력이 좋다면 A 선수는 B 선수를 항상 이깁니다`. 심판은 주어진 경기 결과를 가지고 선수들의 순위를 매기려 합니다. 하지만 몇몇 경기 결과를 분실하여 정확하게 순위를 매길 수 없습니다.
선수의 수 n, 경기 결과를 담은 2차원 배열 results가 매개변수로 주어질 때 정확하게 순위를 매길 수 있는 선수의 수를 return 하도록 solution 함수를 작성해주세요.

> 제한사항
* 선수의 수는 1명 이상 100명 이하입니다.
* 경기 결과는 1개 이상 4,500개 이하입니다.
* results 배열 각 행 [A, B]는 A 선수가 B 선수를 이겼다는 의미입니다.
* 모든 경기 결과에는 모순이 없습니다.

|n|vertex|return|
|---|---|---|
|5|[[4, 3], [4, 2], [3, 2], [1, 2], [2, 5]]|2|

<details>
<summary>풀이 보기</summary>
<div markdown="6">

### 입출력 설명
* 2번 선수는 [1, 3, 4] 선수에게 패배했고 5번 선수에게 승리했기 때문에 4위입니다.
* 5번 선수는 4위인 2번 선수에게 패배했기 때문에 5위입니다.

### 문제 해결

#### 이슈 케이스
초반 문제 해결 설계시, 승자에 대한 리스트와 패배에 대한 리스트를 따로 저장을 해야할 것 같았다. 각각의 리스트를 만든 후 필터링을 하면 원하는 답을 구할 수 있을 것같았다. 초반 생각한 설계는 다음과 같다.
* 노드별 승리 데이터 셋이 없으면 마지막 순위 확정이다.
* 노드별 패배 데이터 셋이 없으면 1순위 확정이다.
* 1순위 또는 마지막 순위를 알 수가 없는 상황이면 어떠한 노드도 정확한 순위를 구할 수 없다고 판단되어서 0값을 반환한다.

단, 조건에 해당하는 **노드의 개수가 2개 이상이면 해당 노드들은 전부 순위를 알수 없으므로 제외**한다.

> 예제에서 주어진 데이터 셋에서 5번 노드가 승리셋이 없으므로 5위가 확정이며, 승리 데이터 셋에서 패배가 없는 1, 4번 노드들은 개수가 2개이므로 제외한다.
즉, `1, 4 노드는 제외 대상, 5번 노드는 확정 대상`이다.

이후 남은 노드인 2, 3노드에 대해서 확정 노드에서 탐색알고리즘을 통해서 원하는 결과를 찾으면 되리라 생각되었다.
하지만 여기서 문제점이 발견되었는데, 확정 5번 노드에 의해서 2번 노드의 순위가 4위인것을 알게 되었지만, 2번 노드와 3번 노드에 대해 단순히 연결값으로는 순위에 대한 부분을 알 수가 없게 되었다. 추가적인 조건이 필요한데 방식이 점점더 산으로 가게 되었다.

#### 해결 케이스
승리, 패배 데이터셋을 만드는 부분은 큰 문제가 없다고 판단되었는데, 문제에서 다음과 같은 지시문을 처리하지 않았음을 깨달았다.
> 만약 A 선수가 B 선수보다 실력이 좋다면 A 선수는 B 선수를 항상 이깁니다
* A > B가 성립하고, B > C가 성립한다면, A > C는 성립합니다.

해당 말이 무슨말인고 하니, 초기 만들었던 데이터 셋에서 주어진 vertex를 가지고만 데이터셋을 만드는것이 아닌, 위의 조건에 해당하는 노드들을 추가로 만들면 될까? 하는 생각이 들었다. 종이에 끄적이면서 실제 주어지는 데이터는 아니지만, A > C의 조건이 되는 노드들은 탐색을 통해서 내용이 포함되어있으면 성립함을 알게 되었다.
> _**[1, 2]**_, _**[2, 5]**_에 대해서
 * 1은 2에 승리한다.
 * 2는 5에 승리한다.
 * 즉, 1은 2와 5에 승리한다.
 
가상의 결과셋을 승리와 패배로 나뉘어 수행하고 비교를 해보았더니, 승리셋과 패배셋을 병합하였더니 순위 대상이 되는 노드는 모든 노드들이 포함되어 있었다. (Set으로 하였기 때문에 중복은 제거 된다.)

#### 코드 - 1차 승패 데이터
```java
/*
[1차] 노드별 승리 데이터 셋
1 = [2]
2 = [5]
3 = [2]
4 = [2, 3]
5 = []

[1차] 노드별 패배 데이터 셋
1 = []
2 = [1, 3, 4]
3 = [4]
4 = []
5 = [2]
*/

// 맵 초기화 (주어진 데이터셋을 모두 가지기 위함)
IntStream.rangeClosed(1, nodeCount).forEach(node -> {
    winnerMap.put(node, new HashSet<>());
    loserMap.put(node, new HashSet<>());
});

// [1차] 승리, 패배 데이터 셋 구축
for(int[] arr : results){
    //승리 arr[0] 패매 arr[1]
    winnerMap.merge(arr[0], new HashSet<>(){{add(arr[1]);}}, (e, i) -> new HashSet<>(){{addAll(e);addAll(i);}});
    loserMap.merge(arr[1], new HashSet<>(){{add(arr[0]);}}, (e, i) -> new HashSet<>(){{addAll(e);addAll(i);}});
}
```

#### 코드 - 2차 승패 데이터 (가상의 승패 노드 추가)
```java
/*
[2차] 노드별 승리 데이터셋을 기준으로 가상 승리 노드셋 계산 ( 자기 자신은 항상 포함, 괄호는 가상으로 만들어진 노드셋 )
1 = [1, 2, (5)]
2 = [2, 5]
3 = [2, 3, (5)]
4 = [2, 3, 4, (5)]
5 = [5]

[2차] 노드별 패배 데이터셋을 기준으로 가상 패배 노드셋 계산 ( 자기 자신은 항상 포함, 괄호는 가상으로 만들어진 노드셋 )
1 = [1]
2 = [1, 2, 3, 4]
3 = [3, 4]
4 = [4]
5 = [(1), 2, (3), (4), 5]
*/

// [2차] 승리, 패배 가상 데이터 셋 구축 ( 각자 for 문에서 리팩토링 )
for(Map scoreMap : new Map[]{winnerMap, loserMap}){
    for(Object  key : scoreMap.keySet()){
        bfs((Integer)key, scoreMap);
    }
}
```
```java
// BFS 메소드
public void bfs(Integer node, Map<Integer, Set<Integer>> scoreMap){
    Set<Integer> tempSet = new HashSet<>();                     // BFS 내에서 사용되는 임시 셋
    boolean[] isVisit = new boolean[nodeCount];                 // 방문 배열 초기화 (예전 속도 이슈로 방문리스트 -> 방문 배열로 변경)
    Queue<Integer> queue = new LinkedList<>(){{add(node);}};    // BFS 큐

    //Queue loop
    while(!queue.isEmpty()){
        int queueNode = queue.poll();
        // 노드 추가
        tempSet.add(queueNode);
        for(int iNode : scoreMap.get(queueNode)){
            // 방문 체크
            if(!isVisit[iNode - 1]){
                isVisit[iNode - 1] = true; //방문 추가
                queue.add(iNode);
            }
        }
    }
    scoreMap.put(node, tempSet);            // 승리 또는 패배 데이터 셋 신규 데이터 추가
}
```

#### 코드 - 승패 데이터 ( 데이터 병합 )
```java
/*
[3차] 승리 데이터 셋과 패배 데이터 셋을 합쳤을 경우 노드셋 계산
1 = [1, 2, (5)]
2 = [1, 2, 3, 4, 5]
3 = [2, 3, 4, (5)]
4 = [2, 3, 4, (5)]
5 = [(1), 2, (3), (4), 5]
*/

// [3차] 승리데이터 와 패배 데이터를 머지
IntStream.rangeClosed(1, nodeCount).forEach(node ->
mergeMap.put(node, new HashSet<>(){{addAll(winnerMap.get(node));addAll(loserMap.get(node));}}));
```

#### 코드 - 결과 데이터 반환
```java
// 승리데이터셋과 패배데이터셋을 합쳤을때, 모든 노드들이 표현되는 노드 개수 반환
return (int)mergeMap.values().stream().filter(value -> value.size() == n).count();
```


### 테스트 결과

|테스트 번호|통과여부|메모리 및 시간|테스트 번호|통과여부|메모리 및 시간|
|---|---|---|---|---|---|
|테스트 1|통과|(10.46ms, 54MB)|테스트 6|통과|(12.47ms, 54.7MB)
|테스트 2|통과|(12.18ms, 52.5MB)|테스트 7|통과|(48.39ms, 57.2MB)
|테스트 3|통과|(9.40ms, 52.9MB)|테스트 8|통과|(64.00ms, 64.3MB)
|테스트 4|통과|(10.26ms, 53.4MB)|테스트 9|통과|(100.88ms, 69.8MB)
|테스트 5|통과|(11.91ms, 53.4MB)|테스트 10|통과|(144.90ms, 83.6MB)

### 후기
나름 이번에는 바로 무작정 개발하지 않고, 설계를 오랫동안 하였는데, 결국 1차는 무난히 실패해버렸다. 혼자서 하는 생각의 설계는 여러가지 예외케이스를 찾아내지 못하였고, 오랜 개발시간을 들였는데 다시 롤백하게되었다. ( 이번에는 부분 롤백이였지, 전체 롤백이면... )
시간의 제한을 두지 않고 오랜시간 생각하고 테스트 하였기에 해결했지, 실제 시간제한 있는 코딩테스트를 보게되면
한문제나 맞출까 싶다... 아직 멀었다.

</div>
</details>

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

<details>
<summary>풀이 보기</summary>
<div markdown="5">

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

</div>
</details>

---
## 단어변환 (2021. 06. 20) BFS/DFS
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/43163)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/convertword/Solution.java)  

>두 개의 단어 begin, target과 단어의 집합 words가 있습니다. 아래와 같은 규칙을 이용하여 begin에서 target으로 변환하는 가장 짧은 변환 과정을 찾으려고 합니다.
> 1. 한 번에 한 개의 알파벳만 바꿀 수 있습니다.
> 2. words에 있는 단어로만 변환할 수 있습니다.

<details>
<summary>풀이 보기</summary>
<div markdown="4">

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

</div>
</details>

---
## 네트워크 (2021. 06. 09) BFS/DFS
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/43162)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/network/Solution.java)  

>컴퓨터 A와 컴퓨터 B가 직접적으로 연결되어있고, 컴퓨터 B와 컴퓨터 C가 직접적으로 연결되어 있을 때 컴퓨터 A와 컴퓨터 C도 간접적으로 연결되어 정보를 교환할 수 있습니다.  
>따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.

<details>
<summary>풀이 보기</summary>
<div markdown="3">

|n|computers|return|
|---|------|------|
|3|[[1, 1, 0], [1, 1, 0], [0, 0, 1]]|2|
|3|[[1, 1, 0], [1, 1, 1], [0, 1, 1]]|1|

해당 문제에 대해서는...  나의 실력으로 해결한 문제가 아니므로 결과를 기술하지 않는다.  
테스트케이스에 대해서 해결을 하지 못하였고, 시간이 너무 지나서 결국 정답을 살짝 보았지만  
결국 코드가 해답과 비슷해져 버렸다.

</div>
</details>

---
## 타겟넘버 (2021. 06. 07) BFS/DFS
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/43165)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/targetnumber/Solution.java)  

<details>
<summary>풀이 보기</summary>
<div markdown="2">

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

</div>
</details>

---
## 프린터 (2021. 05. 30)  Stack/Queue
[Programmers](https://programmers.co.kr/learn/courses/30/lessons/42587)  
[Git Solution](https://github.com/mertyn88/algorithm/blob/master/src/com/algorithm/programmers/printer/Solution.java)  

> 1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
> 2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
> 3. 그렇지 않으면 J를 인쇄합니다.

<details>
<summary>풀이 보기</summary>
<div markdown="1">

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

</div>
</details>
