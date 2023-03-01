package class23;

// https://leetcode-cn.com/problems/minimum-cost-to-merge-stones/
public class Code05_MinimumCostToMergeStones {
    /**
     * 
     * 有 N 堆石头排成一排，第 i 堆中有 stones[i] 块石头。
        每次移动（move）需要将连续的 K 堆石头合并为一堆，而这个移动的成本为这 K 堆石头的总数。
        找出把所有石头合并成一堆的最低成本。如果不可能，返回 -1 。
        
        示例 1：
        
        输入：stones = [3,2,4,1], K = 2
        输出：20
        解释：
        从 [3, 2, 4, 1] 开始。
        合并 [3, 2]，成本为 5，剩下 [5, 4, 1]。
        合并 [4, 1]，成本为 5，剩下 [5, 5]。
        合并 [5, 5]，成本为 10，剩下 [10]。
        总成本 20，这是可能的最小值。
        示例 2：
        
        输入：stones = [3,2,4,1], K = 3
        输出：-1
        解释：任何合并操作后，都会剩下 2 堆，我们无法再进行合并。所以这项任务是不可能完成的。.
        示例 3：
        
        输入：stones = [3,5,1,2,6], K = 3
        输出：25
        解释：
        从 [3, 5, 1, 2, 6] 开始。
        合并 [5, 1, 2]，成本为 8，剩下 [3, 8, 6]。
        合并 [3, 8, 6]，成本为 17，剩下 [17]。
        总成本 25，这是可能的最小值。
        
        提示：
        
        1 <= stones.length <= 30
        2 <= K <= 30
        1 <= stones[i] <= 100
     */
    
    /**
     * 题意: 给你K, 代表相邻的数能合并，但是你可以选择先合并谁，后合并谁，最终一定要合成一个数字，让你返回最小代价
     *  思路:
     *      动态规划: 范围尝试+业务限制
     *  定义f函数
            L...R一定变为p份, 最少代价是啥, 返回
            L...R: 范围上的尝试, p份:业务限制
         例如: 数组下标是0~100，那么要求的就是，0~100这个数组，如果变成一份，最小代价是多少？ ->f(0, 100, 1) 
     * 
     * 
     * 例子: arr[0....7]  K = 3
     * 切分成p份:
     * 
     * 1.f(0,7,1): 当p=1的时候，也就是0~7要合并成一份，只有一条路f(0,7,3), 因为K=3，一次只能K个相邻合并，
     * 0~7必须先搞出3份，最后加上最后三份合并的代价就是总代价
     * 2. 一个普遍的p怎么做?
     *   枚举最左边的一份是什么范围
     *   1)0~0范围上给我搞出一份，1~7范围上给我整出两份，去调用递归函数
     *   2)0~1范围上给我搞出一份，2~7范围上给我整出两份，去调用递归函数
     *   3)0~2范围上给我搞出一份，3~7范围上给我整出两份，去调用递归函数
     *   4)0~3范围上给我搞出一份，4~7范围上给我整出两份，去调用递归函数
     *   ...
     *   最左侧的一份是从哪到哪儿，就这样划分所有可能性
     * 
     */
    public int mergeStones1(int[] stones, int k) {
        if(stones == null || stones.length == 0) {
            return -1;
        }
        int N = stones.length;
        if((N - 1) % (k - 1) > 0) { // n个数到底能不能k个相邻的数合并，最终变成1个数
            return -1;
        }
        // 求前缀和
        int preSum[] = new int[N + 1];
        for (int i = 0; i < N; i++) { // 0位置不用，避免后面用preSum的时候越界
            preSum[i + 1] = preSum[i] + stones[i];
        }
        return process(0, N - 1, 1, preSum, k);
    }
    
    // part >= 1
    // arr[L..R] 一定要弄出p份，返回最低代价
    // arr、K、presum（前缀累加和数组，求i..j的累加和，就是O(1)了）
    public int process(int L, int R, int P, int preSum[], int K) {
        if(L == R) { // 如果L==R，只有一个数，那么如果要合并成的份数就是1分，代价就是0；否则就合并不了，返回-1
            return P == 1 ? 0 : -1;
        }
        if(L > R) {
            return -1;
        }
        if(P == 1) { // 要合并成的份数是1
            // 先搞出K份，然后K份一次进行合并
           int next = process(L, R, K, preSum, K);
           if(next != -1) { // 如果能搞定
               return next + preSum[R + 1] - preSum[L];
           } else {
               return -1;
           }
        } else { // 要合并成的份数不是1份
            // 左部分L~mid分别搞出第一块，其它的搞出p-1块
            int ans = Integer.MAX_VALUE;
            // mid 不能等于R，因为右边必须还有数，才能搞出剩下的P-1
            // mid 每次加K-1个数，保证左边必能合成1份(这里，如果直接写mid++，leetcode会出错)
            // 不知道为什么
            for (int mid = L; mid < R; mid += K - 1) {
                int leftPart = process(L, mid, 1, preSum, K);// 左边部分搞出第1块
                int rightPart = process(mid + 1, R, P - 1, preSum, K);// 右边部分搞出剩下的P-1块
                // 只有当左右部分都能搞定的时候，求最小值
                if(leftPart != - 1 && rightPart != -1) {
                    ans = Math.min(ans, leftPart + rightPart);
                } else {
                    return -1;
                }
            }
            return ans;
        }
    }
    
    // 挂傻缓存
    public int mergeStones2(int[] stones, int k) {
        if(stones == null || stones.length == 0) {
            return -1;
        }
        int N = stones.length;
        if((N - 1) % (k - 1) > 0) { // n个数到底能不能k个相邻的数合并，最终变成1个数
            return -1;
        }
        // 求前缀和
        int preSum[] = new int[N + 1];
        for (int i = 0; i < N; i++) { // 0位置不用，避免后面用preSum的时候越界
            preSum[i + 1] = preSum[i] + stones[i];
        }
        // 傻缓存，三个拷贝参数, L(0~N-1) R(0~N-1) P(1 ~ K) ->
        int dp[][][] = new int[N][N][k + 1]; // 为什么是K+1, 0位置不用
        return process2(0, N - 1, 1, preSum, k, dp);
    }
    
    // part >= 1
    // arr[L..R] 一定要弄出p份，返回最低代价
    // arr、K、presum（前缀累加和数组，求i..j的累加和，就是O(1)了）
    public int process2(int L, int R, int P, int preSum[], int K, int dp[][][]) {
        if(dp[L][R][P] != 0) { // -1或者其它的数，-1表示搞不定
            return dp[L][R][P];
        }
        if(L == R) { // 如果L==R，只有一个数，那么如果要合并成的份数就是1分，代价就是0；否则就合并不了，返回-1
            dp[L][R][P] = P == 1 ? 0 : -1;
            return dp[L][R][P];
        }
        if(L > R) {
            return -1;
        }
        if(P == 1) { // 要合并成的份数是1
            // 先搞出K份，然后K份一次进行合并
           int next = process2(L, R, K, preSum, K, dp);
           if(next != -1) { // 如果能搞定
               dp[L][R][P] = next + preSum[R + 1] - preSum[L];
           } else {
               dp[L][R][P] = -1;
           }
           return dp[L][R][P];
        } else { // 要合并成的份数不是1份
            // 左部分L~mid分别搞出第一块，其它的搞出p-1块
            int ans = Integer.MAX_VALUE;
            // mid 不能等于R，因为右边必须还有数，才能搞出剩下的P-1
            // mid 每次加K-1个数，保证左边必能合成1份(这里，如果直接写mid++，leetcode会出错)
            // 不知道为什么
            for (int mid = L; mid < R; mid += K - 1) {
                int leftPart = process2(L, mid, 1, preSum, K, dp);// 左边部分搞出第1块
                int rightPart = process2(mid + 1, R, P - 1, preSum, K, dp);// 右边部分搞出剩下的P-1块
                // 只有当左右部分都能搞定的时候，求最小值
                if(leftPart != - 1 && rightPart != -1) {
                    ans = Math.min(ans, leftPart + rightPart);
                } else {
                   ans = -1;
                }
            }
            dp[L][R][P] = ans;
            return dp[L][R][P];
        }
    }
    
}
