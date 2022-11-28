package class15;

// https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {
    /**
     * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
     *   你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     *   返回获得利润的最大值。    
     *   注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
     */
    /**
     * 无限次交易，没有冷却期。
     * 和题目5一样
     * buy[i]: 0~i位置，无限次交易，最后一个动作是买的情况下，能获得的最好收入 减去一个最优的买入价格，再减去一个fee
     * see[i]: 0~i位置，无限次交易，最后一个动作是卖的情况下，能够获得的最好收益
     * 
     * 搞定i位置，去推i+1位置
     */
    public static int maxProfit(int[] arr, int fee) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        // 0..0   0 -[0] - fee
        // 0 ~ 0位置上，最后一个动作是买的情况下，能获得的最好收入是0， 减去一个最优的买入价格arr[0]， 交易的费用要提前减fee
        int bestbuy = -arr[0] - fee; // 买的时候就扣费，提前扣费和卖出的时候扣费没有任何区别
        // 0..0  卖  0
        // 0~0位置，无限次交易，最后一个动作是卖的情况下，能够获得的最好收益是0
        int bestsell = 0;
        for (int i = 1; i < N; i++) {
            // 来到i位置了！
            // 如果在i必须买: 0到i-1位置获得的最好收入 - 批发价 - fee， 也就是bestsell - arr[i] - fee
            int curbuy = bestsell - arr[i] - fee;
            // 如果在i必须卖:  （0到i-1位置获得的最好收入 - 良好批发价 - fee）这个整体的最优 + 卖出价格，也就是bestbuy + arr[i]
            int cursell = bestbuy + arr[i];
            // 也可以不在i位置买，curbuy就和之前i-1的的bestby PK，得出当前的bestbuy
            bestbuy = Math.max(bestbuy, curbuy);
            // 也可以不在i位置卖，bestsell就和之前i-1的的bestsell PK，得出当前的bestsell
            bestsell = Math.max(bestsell, cursell);
        }
        return bestsell;
    }
}
