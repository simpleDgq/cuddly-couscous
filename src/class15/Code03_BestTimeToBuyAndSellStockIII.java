package class15;

// https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/
public class Code03_BestTimeToBuyAndSellStockIII {
    /**
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
        设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
        注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    
    /**
     * 思路:
     * 通过股票问题4来求解，4求得是交易次数不超过K次的最好收益。
     * 这题就是K=2.
     */
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }
        return maxProfitIV(2, prices);
    }
    
    public int maxProfitIV(int k, int[] prices) {
        if(prices == null || prices.length == 0 || k < 1) {
            return 0;
        }
        // 大的过滤
        int N = prices.length;
        if(k >= N / 2) {
            return maxProfitII(prices);
        }
        // 动态规划
        int dp[][] = new int[N][k + 1];
        // 一列一列填
        for(int j = 1; j <= k; j++) {
            // 先填好dp[1][j], 然后用dp[1][j]去替代dp[2][j]
            // 0到1位置，不超过j次，能获得的最大收益?
            // 1) 1位置完全不参与, 等价于0到0位置，不超过j次
            int p1 = dp[0][j];
            // 2) 1买入，1卖出, dp[1][j - 1] + prices[1] - prices[1]
            // 3) 0买入，1卖出,dp[0][j - 1] + prices[1] - prices[0]
            int best = Math.max(dp[1][j - 1] - prices[1], dp[0][j - 1] - prices[0]); // 搞定best，可以用来继续求dp[2][j]的值
            // best + prices[1] 和 dp[0][j] PK，最大值就是dp[1][j]
            dp[1][j] = Math.max(best + prices[1], p1);
            
            for(int i = 2; i <= N - 1; i++) {
                // i位置完全不参与, 等价于0到i位置，不超过j次
                p1 = dp[i - 1][j];
                // i买i卖，dp[i][j-1] + prices[i] - prices[i]  -> 这次的，是枚举里面多出的，需要和前面求出来的best PK
                best = Math.max(best, dp[i][j-1] - prices[i]);
                dp[i][j] = Math.max(best + prices[i], p1);
            }
        }
        return dp[N - 1][k];
    }
    
    public int maxProfitII(int[] prices) {
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
