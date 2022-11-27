package class15;

// https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii
public class Code02_BestTimeToBuyAndSellStockII {
    /**
     * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
        在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
        返回 你能获得的 最大 利润 。
     */
    
    /**
     * 思路: 买入和卖出次数不限制
     * 想象整个股市就相当于一个波峰波谷图。既然他无限制交易，你把每一个爬坡算一个钱，全累加起来就是答案。
     *   所有爬坡的钱全累加, 相当于抓住了每一次行情。
     *   
     *   从1开始，减去前面一个数，如果大于0就累加，如果小于0就，不累加。
     */
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }
        int ans = 0;
        int N = prices.length;
        for(int i = 1; i <= N - 1; i++) {
            ans += Math.max(0, prices[i] - prices[i - 1]);
        }
        return ans;
    }
}
