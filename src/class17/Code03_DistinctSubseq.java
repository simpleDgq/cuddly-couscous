package class17;

// https://leetcode-cn.com/problems/21dk04/
public class Code03_DistinctSubseq {
    /**
     * 剑指 Offer II 097. 子序列的数目
     * 
     * 给定一个字符串 s 和一个字符串 t ，计算在 s 的子序列中 t 出现的个数。
     *  字符串的一个 子序列 是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。
     *  （例如，"ACE" 是 "ABCDE" 的一个子序列，而 "AEC" 不是）
     *  题目数据保证答案符合 32 位带符号整数范围。
     *  ====
     *   给定两个字符串S和T
     *   返回S的所有子序列中
     *   有多少个子序列的字面值等于T
     *
     */
    
    /**
     * 思路: 样本对应模型
     * dp[i][j]:
     *       s 0..i 这个范围要搞子序列, i往后范围不可使用, 有多少个子序列的字面值等于字符串t从0到j这个前缀字符串
     *       最右下角就是答案
     *       
     * 样本对应模型可能性根据结尾位置分
     * 1) 子序列不考虑i位置的字符: dp[i][j] = dp[i - 1][j]
     * 2) 子序列一定要含有i位置的字符, [i]位置字符跟j位置字符想等, i-1 搞定j-1 : dp[i][j] = dp[i - 1][j - 1]
     * 
     * 
     * 第0行: 
     * 0行0列:
     * 0~0位置的字符搞定0~0位置的字符，如果s[0] == t[0]，则是1种方法。其它位置，都是0，因为0~0只有一个字符，不可能搞定t的2个及以上字符
     * 第0列:
     * 1行0列位置，0~1位置的字符任意选择，能不能搞定t[0]字符。--> 当前格子字符跟你相等上一个+1，不想等, 上一个抄下来
     */
    public int numDistinct(String s, String t) {
        if(s == null || s.length() == 0 || t == null || t.length() == 0) {
            return 0;
        }
        int N = s.length();
        int M = t.length();
        char str[] = s.toCharArray();
        char tr[] = t.toCharArray();
        
        int dp[][] = new int[N][M];
        // 0行0列
        dp[0][0] = str[0] == tr[0] ? 1 : 0;
        // 第一列
        for(int i = 1; i <= N - 1; i++) {
            //当前格子字符跟你相等上一个+1，不想等, 上一个抄下来
            dp[i][0] = str[i] == tr[0] ? dp[i - 1][0] + 1 : dp[i - 1][0];
        }
        // 从上往下，从左往右填
        for(int i = 1; i <= N - 1; i++) {
            for(int j = 1; j <= M - 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if(str[i] == tr[j]) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[N - 1][M - 1];
    }
}
