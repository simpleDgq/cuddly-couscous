package class05;

public class Code03_EditCost {
    // https://leetcode.cn/problems/edit-distance/
    /*
     * 编辑距离
     * 
     * str1编辑成str2的最小代价
     * 常见的4种编辑动作:
     * 1) 保留
     * 2) 删除
     * 3) 添加
     * 4) 替换
     * 一般认为保留代价是0
     * 删除, 添加, 替换代价会给你参数值
     */

    /**
     * 样本对应模型
     * 
     * dp[i][j]:
     * str1前缀取前i个字符, 编辑成str2前缀前j个字符最少代价是多少
     * 如果这张表我们能够顺利求完, 最右下角返回就是答案（用完str1所有字符，搞出str2所有字符）
     * 
     *
     * rc -> 替换代价
     * ac -> 添加代价
     * dc -> 删除代价
     *
     * 1） 第一行： str1前0个字符，搞定str2 0个字符，代价0， str1取0个字符，搞定str2
     * 1个字符，代价ac(str1添加一个str2的字符),str1取0个字符，搞定str2 2个字符，代价2*ac(str1添加两个str2的字符)。。。
     * 2） 第一列： str1取0个字符，搞定str2 0个字符，代价0， str1取1个字符，搞定str2
     * 0个字符，代价dc(str1删除一个字符); str1取2个字符，搞定str2 0个字符，代价2*dc(str1删除2个字符)。。。
     * 3）从左往右，从小往上填剩余的位置:
     * dp[i][j] 位置，是str1的0 ~ i个字符，搞定str2的0~ j个字符，最小代价是多少？
     * 可能性:
     * 
     * 可能性1:
     * str1的前i-1个字符变成str2的前j个字符, 即删掉str1的最后一个字符: dp[i - 1][j] + dc
     * 可能性2:
     * str1的整体先变成str2的前j-1个字符, 最后str1再加上str2的最后一个字符: dp[i][j - 1] + ac
     * 可能性3:
     * str1, str2两个字符串最后一个字符串相等,
     * str1前面i-1个字符变成str2前面j-1个字符, 最后一个字符保留: dp[i - 1][j - 1]
     * 可能性4:
     * 两个字符串最后一个字符串不相等,
     * str1前面i-1个字符变成str2前面j-1个字符, str1最后一个字符串替换成str2最好一个字符: dp[i - 1][j - 1] + rc
     *
     */
    public static int editCost(String s1, String s2, int rc, int ac, int dc) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        char str1[] = s1.toCharArray();
        char str2[] = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;

        int dp[][] = new int[N + 1][M + 1];
        // 第0行
        for (int j = 0; j <= M; j++) { // str1 0个字符搞定str2 0 个，1个...M-1个，需要在str1添加0个,1个...M-1个str2对应的字符
            dp[0][j] = j * ac;
        }
        // 第0列
        for (int i = 0; i <= N; i++) { // str1 0个字符，1个字符...N-1个字符，搞定str2 0个字符，选删除str1 0个字符，1个字符...N-1个字符
            dp[i][0] = i * dc;
        }

        // 普遍位置
        // str1的前i-1个字符变成str2的前j个字符, 即删掉str1的最后一个字符: dp[i - 1][j] + dc
        // str1的整体先变成str2的前j-1个字符, 最后str1再加上str2的最后一个字符: dp[i][j - 1] + ac
        // str1, str2两个字符串最后一个字符串相等, str1前面i-1个字符变成str2前面j-1个字符, 最后一个字符保留: dp[i - 1][j - 1]
        // 两个字符串最后一个字符串不相等, str1前面i-1个字符变成str2前面j-1个字符, str1最后一个字符串替换成str2最好一个字符: dp[i - 1][j - 1] + rc
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                int p1 = dp[i - 1][j] + dc;
                int p2 = dp[i][j - 1] + ac;
                int p3 = Integer.MAX_VALUE;
                int p4 = Integer.MAX_VALUE;
                if (str1[i - 1] != str2[j - 1]) {
                    p3 = dp[i - 1][j - 1] + rc;
                } else {
                    p4 = dp[i - 1][j - 1];
                }
                dp[i][j] = Math.min(p1, Math.min(p2, Math.min(p3, p4)));
            }
        }

        /*
         * 简化写法
         */
//        for (int i = 1; i <= N; i++) {
//            for (int j = 1; j <= M; j++) {
//                dp[i][j] = Math.min(dp[i - 1][j] + dc, dp[i][j - 1] + ac);
//                if(str1[i - 1] != str2[j - 1]) {
//                    dp[i][j] = Math.min(dp[i - 1][j - 1] + rc, dp[i][j]);
//                } else {
//                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i][j]);
//                }
//            }
//        }
        return dp[N][M];
    }

    public static void main(String[] args) {
        String str1 = "a";
        String str2 = "ab";
        System.out.println(editCost(str1, str2, 1, 1, 1));
//        System.out.println(minCost2(str1, str2, 5, 3, 2));

    }

}
