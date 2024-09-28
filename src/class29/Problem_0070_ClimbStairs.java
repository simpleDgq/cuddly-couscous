package class29;

// https://leetcode.cn/problems/climbing-stairs
// 70. 爬楼梯
public class Problem_0070_ClimbStairs {
    /**
     * 设跳上 n 级台阶有 f(n) 种跳法。在所有跳法中，青蛙的最后一步只有两种情况： 跳上 1 级或 2 级台阶。
     * 
     * 当为 1 级台阶： 剩 n−1 个台阶，此情况共有 f(n−1) 种跳法。
     * 当为 2 级台阶： 剩 n−2 个台阶，此情况共有 f(n−2) 种跳法。
     * 即 f(n) 为以上两种情况之和，即 f(n)=f(n−1)+f(n−2) ，以上递推性质为斐波那契数列。
     * 因此，本题可转化为 求斐波那契数列的第 n 项，区别仅在于初始值不同：
     * 
     * 青蛙跳台阶问题： f(0)=1 , f(1)=1 , f(2)=2 。
     * 斐波那契数列问题： f(0)=0 , f(1)=1 , f(2)=1 。
     * 
     * 动态规划：dp[i]: 跳上第i阶台阶的方法数。根据前面的分析可得dp[i] = dp[i - 1] + dp[i - 2]
     * 初始状态dp[0] = 1 dp[1] = 1
     * 返回dp[n]
     * 
     * 时间复杂度：O(n) 空间复杂度O(n)
     */
    public int climbStairs(int n) {
        int dp[] = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // 优化上面的写法，状态压缩，使用有限个变量
    // 时间复杂度：O(n) 空间复杂度O(1)
    public int climbStairs2(int n) {
        int sum = 0;
        int a = 1; // f(i - 2)
        int b = 1; // f(i - 1)
        for (int i = 0; i <= n - 2; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }
        return b;
    }

}
