package class15;

//https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/submissions/
public class Code01_BestTimeToBuyAndSellStock {
    /**
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     *   你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     *   返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     */
    
    /**
     *  思路:
     * 只能做一次交易, 
        0时刻是这一次交易的卖出时机, 我能挣多少钱
        1时刻是这一次交易的卖出时机, 我能挣多少钱
        2时刻是这一次交易的卖出时机, 我能挣多少钱
        i时刻是卖出时机, 我能挣多少钱
        max就是答案
        
        任意的一个i时刻的卖出能赚的钱怎么求? 
        i位置前面找一个最小的数min，arr[i] - min 和 0 PK就是答案
        搞一个min变量记录前面的最小值。
     */
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) {
            return 0;
        }
        int N = prices.length;
        int min = prices[0]; // 刚开始最小值就是0
        int ans = 0; // 0位置的答案是0，因为前面没有数，只能0位置买入，0位置卖出
        for(int i= 1; i <= N - 1; i++) {
            ans = Math.max(ans, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return ans;
    }
    
}
