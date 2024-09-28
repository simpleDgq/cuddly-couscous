package class29;

// 62. 不同路径
public class Problem_0062_UniquePaths {
    /**
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     */

    /**
     * 动态规划
     * dp[i][j]: 来到i,j位置，有多少条路径
     * 可能性：dp[i][j] = dp[i][j - 1] + dp[i - 1][j]
     * 1. 从i，j位置的左边来，dp[i][j - 1]
     * 2. 从i，j位置的上面来，dp[i - 1][j]
     */
    public int uniquePaths1(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        int dp[][] = new int[m][n];
        // 填第0行，第0行右边的位置，只能从右边来，只有一条路径
        for (int j = 0; j <= n - 1; j++) {
            dp[0][j] = 1;
        }
        // 填第0列，第0列的位置，只能从上面来，只有一条路径
        for (int i = 0; i <= m - 1; i++) {
            dp[i][0] = 1;
        }
        // 从左往右，从上往下，填
        for (int i = 1; i <= m - 1; i++) {
            for (int j = 1; j <= n - 1; j++) {
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    // 空间优化 i,j位置只依赖于它左边、上面的位置，可以一行行往下推
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        int dp[] = new int[n];
        // 填第0行，第0行右边的位置，只能从右边来，只有一条路径
        for (int j = 0; j <= n - 1; j++) {
            dp[j] = 1;
        }
        // 从左往右，从上往下，填
        for (int i = 1; i <= m - 1; i++) {
            for (int j = 1; j <= n - 1; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    /**
     * 排列组合:
     * https://zhuanlan.zhihu.com/p/41855459
     * 
     * 思路:
     * 这题其实是排列组合问题，不管你怎么走，往左都只能走 n - 1步
     * 往下只能走m - 1步。一共m + n - 2步
     * 
     * 也就是要求Cm+n-2 ^ m -1
     * 
     * 例子: 矩阵规模是10 * 5， 那么往左需要走9步，往下需要走4步，总共14步
     * 总共有多少种走法？ 也就是14里面选9步，有多少种选法? --> C14^9
     * 或者往下C14^4， C14^9和C14^4是相等的
     * 
     * Cm+n-2 ^ m -1 =
     * 
     * (m+n−2)(m+n−3)⋯n
     * ​-------------------
     *      (m−1)!
     */
    // 背
    public int uniquePaths3(int m, int n) {
        long ans = 1;
        for (int x = n, y = 1; y <= m - 1; ++x, ++y) {
            ans = ans * x / y;
        }
        return (int) ans;
    }

    // m 行
    // n 列
    // 下：m-1
    // 右：n-1
    public int uniquePaths2(int m, int n) {
        // 往右能走多少步
        int right = n - 1;
        // 总共走多少步
        int all = m + n - 2;
        // 求组合数的时候的分子和分母
        long o1 = 1; // 分子
        long o2 = 1; // 分母
        /*
         * 例子:
         * right = 6
         * all = 10
         * 要求的是C10^6, 根据排列组合的公式
         * (10!) / (6! * 4!)
         * 
         * 化简得:
         * 7 * 8 * 9 * 10 分子
         * ---------------- 表示除法
         * 1 * 2 * 3 * 4 分母
         * 
         * 剩下的分子、分母长度一定是相等的
         * 
         * o1和o2都从1开始，每次分别乘上分子分母上的一个元素，然后求最大公约数，
         * 约到最大公约数之后，继续搞后面的元素
         * 
         * 最终返回o1, 分母都被约完了
         * 
         */
        // 分子从 right + 1开始
        // 分母从1开始
        // o1乘进去的个数 一定等于 o2乘进去的个数, 可以省掉下面的这种写法 -> //
        // 因为分子分母长度是相等的，如果分子到到了末尾，分母必然也到了末尾；反之亦然
        // for (int i = right + 1, j = 1; i <= all || j <= all - right ; i++)
        for (int i = right + 1, j = 1; i <= all; i++, j++) {
            // 分子分母分别乘上当前的元素，然后求最大公约数
            o1 *= i;
            o2 *= j;
            long gcd = gcd(o1, o2);
            // 分子分母分别约掉最大公约数
            o1 /= gcd;
            o2 /= gcd;
        }
        return (int) o1;
    }

    // 背，求最大公约数
    public long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }
}
