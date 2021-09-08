package com.algorithm.leetcode.buyandcellstock;

import java.util.stream.IntStream;

public class Solution {
    public int maxProfit(int[] prices) {
        return IntStream.range(1, prices.length).filter(idx -> prices[idx] - prices[idx-1] > 0).map(idx -> prices[idx] - prices[idx-1]).sum();
    }
}
