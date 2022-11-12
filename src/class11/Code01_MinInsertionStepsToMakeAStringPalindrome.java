package class11;

import java.util.ArrayList;
import java.util.List;

//本题测试链接 : https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
public class Code01_MinInsertionStepsToMakeAStringPalindrome {
    /*
    问题一：一个字符串至少需要添加多少个字符能整体变成回文串
    问题二：返回问题一的其中一种添加结果
    问题三：返回问题一的所有添加结果

    给你一个字符串 s ，每一次操作你都可以在字符串的任意位置插入任意字符。

    请你返回让 s 成为回文串的 最少操作次数 。

    「回文串」是正读和反读都相同的字符串。

    示例 1：

    输入：s = "zzazz"
    输出：0
    解释：字符串 "zzazz" 已经是回文串了，所以不需要做任何插入操作。
    示例 2：

    输入：s = "mbadm"
    输出：2
    解释：字符串可变为 "mbdadbm" 或者 "mdbabdm" 。
    示例 3：

    输入：s = "leetcode"
    输出：5
    解释：插入 5 个字符后字符串变为 "leetcodocteel" 。
    示例 4：

    输入：s = "g"
    输出：0
    示例 5：

    输入：s = "no"
    输出：1
    提示：

    1 <= s.length <= 500
    s 中所有字符都是小写字母。
    */
    
    /**
     * 范围尝试模型 : L-R范围上，至少添几个字符，使整体变成回文串。
     * dp[i][j]  --> i~j 范围上，至少添几个字符，使整体变成回文串。
     * 
     * 问题一：一个字符串至少需要添加多少个字符能整体变成回文串
     * 可能性:
     * 1. 保留j位置的字符，i到j-1去变成回文串至少需要几个，然后再i位置的前面添加一个j位置相同的字符 --> dp[i][j] = dp[i][j-1] + 1  (左边)
     * 2. 保留i位置的字符，i+1到j去变成回文串至少需要几个，然后再j位置的后面添加一个i位置相同的字符 --> dp[i][j] = dp[i+1][j] + 1  (下边)
     * 3. i位置字符等于j位置字符，则i+1到j-1变成回文串至少需要几个,及时答案 -> str[i] == str[j] -> dp[i][j] = dp[i+1][j-1]     (左下)
     * 
     * 三种可能性取最小值。
     * 
     * 填表:
     * 1）左下班区，L > R, 不合法，所以不用管
     * 2）对角线位置，一个字符，是回文串，不需要添加，所以都是0
     * 3）第二条对角线，只有两个字符，如果相等，填0，不相等，填1.
     * 4）普遍位置，三种可能性取最小值。
     */
    public int minInsertions(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        
        int N = s.length();
        int dp[][] = new int[N][N];
        char str[] = s.toCharArray();
        // 填第二条对角线
        for(int L = 0; L <= N -2; L++) {
            dp[L][L+1] = str[L] == str[L + 1] ? 0 : 1;
        }
        // 普遍位置，从下往上，从左往右填
        for(int L = N - 3; L >= 0; L--) {
            for(int R = L + 2; R <= N - 1; R++) {
                // 可能性1和2 PK
                dp[L][R] = Math.min(dp[L][R-1] + 1, dp[L + 1][R] + 1);
                if(str[L] == str[R]) {// 可能性3
                    dp[L][R] = Math.min(dp[L + 1][R - 1], dp[L][R]);
                }
            }
        }
        return dp[0][N-1];
    }
    
    /**
     *  问题二：返回问题一的其中一种添加结果
     *  
     *  根据动态规划表，进行回溯
     *  
     */
 // 本题第二问，返回其中一种结果
    public static String minInsertionsOneWay(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        // 先生成动态规划表
        int N = s.length();
        int dp[][] = new int[N][N];
        char str[] = s.toCharArray();
        // 填第二条对角线
        for(int L = 0; L <= N -2; L++) {
            dp[L][L+1] = str[L] == str[L + 1] ? 0 : 1;
        }
        // 普遍位置，从下往上，从左往右填
        for(int L = N - 3; L >= 0; L--) {
            for(int R = L + 2; R <= N - 1; R++) {
                // 可能性1和2 PK
                dp[L][R] = Math.min(dp[L][R-1] + 1, dp[L + 1][R] + 1);
                if(str[L] == str[R]) {// 可能性3
                    dp[L][R] = Math.min(dp[L + 1][R - 1], dp[L][R]);
                }
            }
        }
        
        // dp[0][N-1] 就是至少要添加的字符，则可以知道最终的回文串的长度就是dp[0][N-1] + N
        char ans[] = new char[N + dp[0][N-1]];
        int L = 0;
        int R = N - 1;
        int ansL = 0;
        int ansR = ans.length - 1;
        // 从dp[0][N-1]开始回溯, 当L >= R的时候就搜集完答案
        while(L < R) {
            // 如果dp[L][R] - 1之后，等于它左边的格子，说明dp[L][R]来自于可能性1
            if(dp[L][R] - 1 == dp[L][R - 1]) {
                // 将R位置的字符，填在ansl和ansR位置
                ans[ansL++] = str[R];
                ans[ansR--] = str[R--]; // R--,去搞下一个范围。例如现在搞得是0-4这个范围，0-4的答案来自左边，下一步要搞0-3，R需要--
            } else if (dp[L][R] - 1 == dp[L + 1][R]) { // 如果dp[L][R] - 1之后，等于它下边的格子，说明dp[L][R]来自于可能性2
                // L位置的字符填好
                ans[ansL++] = str[L];
                ans[ansR--] = str[L++]; //L++，搞下一个范围 
            } else { // 如果dp[L][R] - 1之后，等于它左下的格子，说明dp[L][R]来自于可能性3
                // str[L] == str[R], L++，R--，从左下角出发去搞剩下的
                ans[ansL++] = str[L++]; 
                ans[ansR--] = str[R--];
            }
        }
        // L=R
        if (L == R) { // aba这种情况。当L++，R--之后，只剩一个字符了，最后一个字符也要填进去
            ans[ansL] = str[L];
        }
        // L > R ，错过去了，说明都填好了
        return String.valueOf(ans);
    }
    
    /**
     *   问题三：返回问题一的所有添加结果
     *   
     *   动态规划表进行回溯，只不过，没到一个新的格子的时候用递归去搞
     */
    public static List<String> minInsertionsAllWays(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            // 先生成动态规划表
            int N = s.length();
            int dp[][] = new int[N][N];
            char str[] = s.toCharArray();
            // 填第二条对角线
            for(int L = 0; L <= N -2; L++) {
                dp[L][L+1] = str[L] == str[L + 1] ? 0 : 1;
            }
            // 普遍位置，从下往上，从左往右填
            for(int L = N - 3; L >= 0; L--) {
                for(int R = L + 2; R <= N - 1; R++) {
                    // 可能性1和2 PK
                    dp[L][R] = Math.min(dp[L][R-1] + 1, dp[L + 1][R] + 1);
                    if(str[L] == str[R]) {// 可能性3
                        dp[L][R] = Math.min(dp[L + 1][R - 1], dp[L][R]);
                    }
                }
            }
            // 递归搜集答案去吧
            char path[] = new char[N + dp[0][N-1]];
            int M = path.length - 1;
            process(str, dp, 0, N - 1, path, 0, M, ans);
        }
        return ans;
    }
    // 当前来到的动态规划中的格子，(L,R)
    // path 中现在要填  [pl到pr]范围，pl左边和pr右边，已经填好了，不用管，
    // 填好的答案放到ans中返回
    public static void process(char[] str, int[][] dp, int L, int R, char[] path, int pl, int pr, List<String> ans) {
       if(L >= R) { // base case
          if(L == R) { // 只有一个字符了，填进path中，形成答案，再加入ans
               path[pl] = str[L];
          } 
          // L > R了，说明已经填完，直接加入答案
          ans.add(String.valueOf(path));
       } else {
           // 如果dp[L][R] - 1之后，等于它左边的格子，说明dp[L][R]来自于可能性1
           if(dp[L][R] - 1 == dp[L][R - 1]) {
               // 将R位置的字符，填在ansl和ansR位置
               path[pl] = str[R];
               path[pr] = str[R];
               // 递归去搞L~R--范围
               process(str, dp, L, --R, path, pl + 1, pr - 1, ans);
           } // 不能加else!!! 从右上角出发，可能的三个位置(左，左下，下)出发，都得去试一遍，求所有的答案，如果加了else，其实只求了一条路径
           if (dp[L][R] - 1 == dp[L + 1][R]) { // 如果dp[L][R] - 1之后，等于它下边的格子，说明dp[L][R]来自于可能性2
               // L位置的字符填好
               path[pl] = str[L];
               path[pr] = str[L]; 
               //L++，搞下一个范围 
               process(str, dp, ++L, R, path, pl + 1, pr - 1, ans);
           }
           // L == R - 1, 只有两个字符，aa这种情况，自消化
           // dp[L + 1][R - 1] == dp[L][R]. 三个及以上字符，abca这种. 例如dp[0][7]=3,说明0~3范围只需要填进3个字符，如果左下dp[1][6]也等于3，这时候就看你来自于可能性3
           if(str[L] == str[R] && (L == R - 1 || dp[L + 1][R - 1] == dp[L][R])) { // 如果dp[L][R] - 1之后，等于它左下的格子，说明dp[L][R]来自于可能性3
               // str[L] == str[R], L++，R--，从左下角出发去搞剩下的
               path[pl] = str[L]; 
               path[pr] = str[R];
               process(str, dp, ++L, --R, path, pl + 1, pr - 1, ans);
           }
       }
    }
    
    
    public static void main(String[] args) {
        String s = null;
        String ans2 = null;
        List<String> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "mbadm";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "leetcode";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "aabaa";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);
        System.out.println("本题第二问，返回其中一种结果测试结束");

        System.out.println();

        System.out.println("本题第三问，返回所有可能的结果测试开始");
        s = "mbadm";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "leetcode";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "aabaa";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能的结果测试结束");
    }
}
