package class33;

// 279. 完全平方数
public class Problem_0279_PerfectSquares {
    /**
     * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
     * 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     * 
     * 示例 1：
     * 输入：n = 12
     * 输出：3 
     * 解释：12 = 4 + 4 + 4
     * 
     * 示例 2：
     * 输入：n = 13
     * 输出：2
     * 解释：13 = 4 + 9
     */
    
    /**
     * 思路: 
     * 
     * 题意: 一个数最少可以用几个数的平方累加得到 --> N它最少能比几个数的平方拆出来
     * 
     * 这题直接背吧，没什么意思, 数学定理
     * 
     * 1.四平方和定理: 任何一个数，你拆平方和的项数，不会超过4项。
     * 2.任何数消去4的因子之后，剩下rest，rest % 8 == 7，一定是4个
     * 
     */
    public static int numSquares3(int n) {
        while (n % 4 == 0) { // 消掉4的因子
            n /= 4;
        }
        if (n % 8 == 7) { // 剩下的n模8之后，如果是7，一对是4个
            return 4;
        }
        // a是第一部分
        // b是第二部分
        for (int a = 0; a * a <= n; ++a) {
            // a * a +  b * b = n  
            int b = (int) Math.sqrt(n - a * a); // n - a * a 之后开平方，求第二部分b
            if (a * a + b * b == n) { // 如果a的平方加b的平方是n。找到答案
                return (a > 0 && b > 0) ? 2 : 1; // a 和 b都大于0说明有两个，任何一个为0，说明只有一个
            }
        }
        // 不超过4个，1， 2 ，4 都讨论过，剩下的就是3，直接返回
        return 3;
    }
}