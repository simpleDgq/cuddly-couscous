package class15;

// https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/
public class Code05_BestTimeToBuyAndSellStockWithCooldown {
    /**
     * 给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。​
     *   设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     *   卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     *   注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    
    /**
     * 思路:
     * 定义新指标 buy[22]
     *  0~22无限次交易获得的最右收入减去 一个比较优良的买入时机, 综合起来的最优叫buy[22]
     *  这个综合起来的最优收益最后再加上100就是0~23范围上获得无限次交易的最优收益.
     *  
     *  例子:
     *  [1 X X X X 100]
     *   0 1 2 3 4 5
     *  0位置是1, buy[0]=-1，buy[0]表示最后一次交易买入时机在0位置
     *  0到0无限次交易获得的收入减去0位置的买入代价，只有0位置，最优的买入就是1，0 - prices[0] = 0 - 1 = -1
     * ====> 上面的例子: 
     *   0) 最后一次交易必须在0位置买入, 0~0范围上进行无限次交易获得的钱减去0位置的买入价, 最后加上5位置的卖出价
     *   1) 最后一次交易必须在1位置买入, 0~1范围上进行无限次交易获得的钱减去1位置的买入价, 最后加上5位置的卖出价
     *   2) 最后一次交易必须在2位置买入, 0~2范围上进行无限次交易获得的钱减去2位置的买入价, 最后加上5位置的卖出价
     *   3) 最后一次交易必须在3位置买入, 0~3范围上进行无限次交易获得的钱减去3位置的买入价, 最后加上5位置的卖出价
     *   整体最优就是不加5位置的之前的部分, 所有都考虑哪个最优，那个最max，就谁加5位置的卖出价
     *   最后一次操作一定是买动作
     * 
     * ========重点=====
     * 1. buy[i]: 指的是在 0 到i的范围上，最后一次操作一定是买的动作，而且发生在i位置。0到i进行无限次交易获得的钱，减去i的买入代价。
     *   buy[i] 怎么求?
     *   1) i不参与, 不在i位置买入: by[i - 1]
     *   2) i参与，就在i上买, 因为cooldown, 所以之前是0~i-2范围上无限次交易获得的钱: sell[i - 2] - prices[i]
     *   
     * 2. sell[i]: 0~i做无限次交易, 最后一个动作必须是卖, 获得的最好收入
     *    sell[i] 怎么求?
     *    1) i不参与, 不在i位置卖: sell[i - 1]
     *    2) i参与, i是最后的卖出时刻.
     *       就是0~i-1范围上考虑综合的无限次收入，以及一个最优的买入价，再加上你此时i位置的卖出价:  buy[i - 1] + prices[i] 
     */
    
    /**
     * 心路历程:
     * 可能一开始，想的是i位置如果要卖掉，最多能挣多少钱?
     * 1) 最后一次是在0位置买的，0位置之前能获得的最好收入是0，减去0位置的买入价格arr[0]，加上i位置的卖出价格arr[i],就是0位置买入，i卖出的情况下的收入
     *    0 - arr[0] + arr[i]
     * 2) 最后一次是1位置买的，1位置之前能获得的最好收入是0(因为有冷却期，1之前的0位置根本不能进行交易)，减去1位置的买入价格arr[1]，加上i位置的卖出价格arr[i],就是1位置买入，
     *    i卖出的情况下的收入  
     *    0 - arr[1] + arr[i]
     * 3) 最后一次是2位置买的，2位置之前能获得的最好收入是0~0(因为有冷却期，1位置不能进行交易)范围能获得的最好收入，减去2位置的买入价格arr[2]，加上i位置的卖出价格arr[i],就是2位置买入，
     *    i卖出的情况下的收入
     *    {0~0} - arr[2] + arr[i]
     * 4) 最后一次是3位置买的，3位置之前能获得的最好收入是0~1(因为有冷却期，2位置不能进行交易)范围能获得的最好收入，减去3位置的买入价格arr[3]，加上i位置的卖出价格arr[i],就是3位置买入，
     *    i卖出的情况下的收入
     *    {0~1} - arr[2] + arr[i]
     * 5) 最后一次是4位置买的，4位置之前能获得的最好收入是0~2(因为有冷却期，3位置不能进行交易)范围能获得的最好收入，减去4位置的买入价格arr[4]，加上i位置的卖出价格arr[i],就是4位置买入，
     *    i卖出的情况下的收入
     *    {0~2} - arr[2] + arr[i]
     * ....
     * 依次类推，求max，就是答案。
     * 
     * 发现有枚举行为. 需要优化，这些0 - arr[0]，  0 - arr[1] ， {0~0} - arr[2] ， {0~1} - arr[2]，  {0~2} - arr[2]的最优
     * 可以用一个指标进行替代。就是buy[i]  ==> 优化枚举行为，画格子去推，也能推出上面的解法。
     */
    
    
    // 最优尝试如下：
    // buy[i] : 在0...i范围上，最后一次操作是buy动作，
    // 这最后一次操作有可能发生在i位置，也可能发生在i之前
    // buy[i]值的含义是：max{ 所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格] }
    // 比如：arr[0...i]假设为[1,3,4,6,2,7,1...i之后的数字不用管]
    // 什么叫，所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格]？
    // 比如其中一种可能性：
    // 假设最后一次buy动作发生在2这个数的时候，那么之前的交易只能在[1,3,4]上结束，因为6要cooldown的，
    // 此时最大收益是多少呢？是4-1==3。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 3 - 2 = 1
    // 另一种可能性：
    // 再比如最后一次buy动作发生在最后的1这个数的时候，那么之前的交易只能在[1,3,4,6,2]上发生，因为7要cooldown的，
    // 此时最大收益是多少呢？是6-1==5。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 5 - 1 = 4
    // 除了上面两种可能性之外，还有很多可能性，你可以假设每个数字都是最后buy动作的时候，
    // 所有可能性中，(之前交易获得的最大收益 - 最后buy动作的收购价格)的最大值，就是buy[i]的含义
    // 为啥buy[i]要算之前的最大收益 - 最后一次收购价格？尤其是最后为什么要减这么一下？
    // 因为这样一来，当你之后以X价格做成一笔交易的时候，当前最好的总收益直接就是 X + buy[i]了
    //
    // sell[i] :0...i范围上，最后一次操作是sell动作，这最后一次操作有可能发生在i位置，也可能发生在之前
    // sell[i]值的含义：0...i范围上，最后一次动作是sell的情况下，最好的收益
    //
    // 于是通过分析，能得到以下的转移方程：
    // buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i])
    
    // 如果i位置没有发生buy行为，说明有没有i位置都一样，那么buy[i] = buy[i-1]，这显而易见
    // 如果i位置发生了buy行为, 那么buy[i] = sell[i - 2] - prices[i]，
    // 因为你想在i位置买的话，你必须保证之前交易行为发生在0...i-2上，
    // 因为如果i-1位置有可能参与交易的话，i位置就要cooldown了，
    // 而且之前交易行为必须以sell结束，你才能buy，而且要保证之前交易尽可能得到最好的利润，
    // 这正好是sell[i - 2]所代表的含义，并且根据buy[i]的定义，最后一定要 - prices[i]
    //
    // sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i])
    // 如果i位置没有发生sell行为，那么sell[i] = sell[i-1]，这显而易见
    // 如果i位置发生了sell行为，那么我们一定要找到 {之前获得尽可能好的收益 - 最后一次的收购价格尽可能低}，
    // 而这正好是buy[i - 1]的含义！之前所有的"尽可能"中，最好的一个！
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length < 2) { // 只有一个元素，不可能获得大于0的收入，买了就得卖出
            return 0;
        }
        int N = prices.length;
        int buy[] = new int[N];
        int sell[] = new int[N];
        // buy[1] 怎么求？
        // 如果在0位置买入，0~0能获得的最优收益是0， 减去0位置的买入价就是-prices[0]
        // 如果在1位置买入，0~1能获得的最优收益是0，因为有冷却期，不能在0位置进行交易，交易之后，要冷却一天，1就不能买了，
        // 所以只能在1位置无限次交易，能获得的最优收益也是0，减去1位置的买入价就是-prices[1]
        buy[1] = Math.max(-prices[0], -prices[1]);
        // sell[1] 怎么求？0~1位置做无限次交易，最后一次是卖出，能获得的最好收入
        // 0买0卖，收入0
        // 1买1卖，收入0
        // 0买1卖，收入prices[1] - prices[0]
        sell[1] = Math.max(0, prices[1] - prices[0]);
        for(int i = 2; i <= N - 1; i++) {
            // buy[0] 不用设置，因为for循环里面，i从2开始，buy[i - 1] 不依赖buy[0]位置
            // sell[0] 就是0，0~0能够获得的最好收入，本身就不用设置
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[N - 1];
    }
    
    // 空间压缩(可以省略，没搞明白)
    public int maxProfit2(int[] prices) {
        if(prices == null || prices.length < 2) { // 只有一个元素，不可能获得大于0的收入，买了就得卖出
            return 0;
        }
        int N = prices.length;
        int buy1 = Math.max(-prices[0], -prices[1]);
        int sell1 = Math.max(0, prices[1] - prices[0]);
        int sell2 = 0;
        for(int i = 2; i <= N - 1; i++) {
//            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
//            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
            // i = 2, buy[2] = buy[1]  sell[0] 
            //        sell[2] = sell[1] buy[1]
            // i = 3  buy[3] = buy[2]  sell[1]
            //        sell[3] = sell[2] buy[2]
            int tmp = sell1;
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy1 = Math.max(buy1, sell2 - prices[i]);
            sell2 = tmp;
        }
        return sell1;
    }
    
    
}