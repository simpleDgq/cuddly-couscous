package class04;

public class Code07_InterleavingString {
    // 本题测试链接 : https://leetcode.com/problems/interleaving-string/
    /* 
     * 97. 交错字符串
     * 给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
     * 
     */
    
    /*
     * 动态规划 - 样本对应模型 -> 题目输入参数给了个样本A，是个字符串或者数组，
      *          又给了个样本B，也是个字符串或者数组，
       *         就可以看看用样本对应模型能不能把它憋出来
       *         
       * 结尾字符做文章
     */
    /**
     * 直接定义一张dp表。dp[i][j] -> str1只拿前i个字符， 和str2只能前j个字符， 能否组成str总只拿前i+j的字符？
     *   都是前缀
     *  如果这张表我们能顺利填好，最右下角的答案就是我们要的
     *  
     *  1. 第0行，表示s1不要，s2拿出0个字符，1个字符...能不能搞出s3  -->  填好第一行
     *  2. 第0列，表示s2不要，s1拿出0个字符，1个字符...能不能搞出s3  -->  填好第一列
     *  3. dp[i][j]怎么决策？
     *     dp[i][j]对应： 
     *          s1: 0 ~ i - 1下标
     *          s2: 0 ~ j - 1下标
     *          s3: 0 ~ i + j - 1下标
     *     两种情况:
     *      1) s3的最后一个字符来自于s1,  那就需要s1剩余的i-1个字符和s2剩余的j个字符，能搞定剩下的s3，也就是dp[i - 1][j]要是true;
     *          ==> s1[i - 1] == s3[i + j - 1] && dp[i - 1][j]
     *      2) s3的最后一个字符来自于s2,  那就需要s2剩余的j-1个字符和s1剩余的i个字符，能搞定剩下的s3，也就是dp[i][j - 1]要是true; 
     *          ==> s2[i - 1] == s3[i + j - 1] && dp[i][j - 1]
     *  
     */
    
    public static boolean isInterleave(String s1, String s2, String s3) {
        if(s1 == null || s2 == null || s3 == null) {
            return false;
        }
        int N = s1.length();
        int M = s2.length();
        boolean dp[][] = new boolean[N + 1][M + 1];
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();
        
        // 长度不等
        if(N + M != s3.length()) {
            return false;
        }
        
        dp[0][0] = true; // 一个都不拿，s3能搞定0个，true
        // 填好第0行
        for(int j = 1; j <= M; j++) {
            dp[0][j] = dp[0][j - 1] && (str2[j - 1] == str3[j - 1]);
        }
        // 填好第0列
        for(int i = 1; i <= N; i++) {
            dp[i][0] = dp[i - 1][0] && (str1[i - 1] == str3[i - 1]);
        }
        // 从上往下，从左往右，填好整张表
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                if((str1[i - 1] == str3[i + j - 1] && dp[i - 1][j]) ||
                        (str2[j - 1] == str3[i + j - 1] && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        
        return dp[N][M]; // 用完s1和s2所有的字符，就是答案
    }
    
    public static void main(String[] args) {
        String s1 = "aabcc";

        String s2 ="dbbca";
        String s3 ="aadbbcbcac";
        
        System.out.println(isInterleave(s1, s2, s3));
    }
   
    
}
