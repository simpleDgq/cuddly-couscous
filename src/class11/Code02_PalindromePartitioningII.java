package class11;

import java.util.ArrayList;
import java.util.List;

//本题测试链接 : https://leetcode.com/problems/palindrome-partitioning-ii/
public class Code02_PalindromePartitioningII {
    /*
     * 问题一：一个字符串至少要切几刀能让切出来的子串都是回文串
     * 问题二：返回问题一的其中一种划分结果
     * 问题三：返回问题一的所有划分结果
     * 
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文。
     * 
     * 返回符合要求的 最少分割次数 。
     * 
     * 示例 1：
     * 
     * 输入：s = "aab"
     * 输出：1
     * 解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
     * 示例 2：
     * 
     * 输入：s = "a"
     * 输出：0
     * 示例 3：
     * 
     * 输入：s = "ab"
     * 输出：1
     * 提示：
     * 
     * 1 <= s.length <= 2000
     * s 仅由小写英文字母组成
     */

    /**
     * 问题一：一个字符串至少要切几刀能让切出来的子串都是回文串
     * 
     * 从左往右尝试模型:
     * 一个字符串，每一个可能的前缀，如果是回文，作为第一部分，然后去搞剩下的部分，看能够最少能够切出多少个回文串。
     */

    /**
     * str从i位置出发，至少分成几个部分，每个部分都是回文
     * 时间复杂度:
     * 每一个i都要去考察i出发每一个可能的前缀，i到i，i~i+1， i~i+2...等位置，所以时间复杂度是O(N^2)
     * 但是每个i每次还要检查i出发的前缀是不是回文，时间复杂度是O(N)
     * 整体时间复杂度是O(N^3)
     * 
     * 有没有什么办法，降低时间复杂度??? --> 检查时间复杂度的操作，生成一张dp表，能够查出任意一个i到j位置是不是回文。
     * 那么判断是否是回文的操作就变成了O(1) --> 整体的时间复杂度就变成了O(N^2)
     */
//    public int process(char str[], int i) {
//        if(i == str.length) { // 来到了最后的位置，没有字符了. 必搞不出回文
//            return 0;
//        }
//        // i出发每一个可能的前缀，如果是回文，递归搞剩下的串
//        // i ~ i, i ~ i + 1 等等
//        int next = 0;
//        int ans = 0;
//        for(int end = i; end <= str.length - 1; end++) {
//            if(检查str[i~end] 是回文吗) { // str[i~end]如果是回文，从end + 1开始搞剩下的，因为i~end已经搞定了
//                next = Math.min(next, process(str, end + 1)); // 剩下的字符串，选出最小能够分成的份数
//            }
//        }
//        ans = next + 1; // 加上i~end已经切出的一份就是答案
//        return ans;
//    }

    /**
     * 动态规划
     * 问题一：一个字符串至少要切几刀能让切出来的子串都是回文串
     * 
     * 看上面的递归，就是一个一维dp问题 : int dp[N + 1]
     * dp[i]的含义就是，从i出发至少能搞出几个回文串.
     * 那么从0出发至少能够搞出的回文数是dp[0], 减去1，就是至少需要切的刀数。
     */
    public static int minCut(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        int N = s.length();
        char str[] = s.toCharArray();
        int dp[] = new int[N + 1];
        dp[N] = 0; // base case N出发越界了，不可能搞出回文串
        // 生成checkMap
        boolean checkMap[][] = generateCheckMap(str, N);

        // 从右往左推
        for (int i = N - 1; i >= 0; i--) {
            int next = Integer.MAX_VALUE; // 每次来到一个新的i位置出发，next都必须重新变成系统最大!!!
            // 每次来到i位置，都选出一个最小值
            for (int end = i; end <= str.length - 1; end++) {
                if (checkMap[i][end]) { // str[i~end]如果是回文
                    next = Math.min(next, dp[end + 1]); // end + 1位置出发，选出能够搞出的最少回文份数
                }
            }
            dp[i] = next + 1; // 加上i~end已经切出的一份,就是答案
        }
        return dp[0] - 1; // 从0出发至少能够搞出的回文数是dp[0], 减去1，就是至少需要切的刀数。
    }

    /**
     * 生成checkMap，检查i到j位置是不是回文:
     * dp[N][N] 的表，记录任意i到j是不是回文。
     * 对角线位置，只有一个字符，所以都是true，
     * 第二条对角线位置，只有两个字符，如果str[i] == str[j]就是true，否则是false
     * 
     * 普遍的[i][j]位置，需要str[i] == str[j] 且[i+1][j-1]是回文 -> dp[i][j] = str[i] == str[j]
     * && dp[i+1][j-1]
     * 只依赖它左下的位置，从下往上，从左往右填。
     */
    public static boolean[][] generateCheckMap(char str[], int N) {
        boolean dp[][] = new boolean[N][N];
        // 一次填两条对角线: 填主对角线和第二条对角线。最后填[N-1][N-1]
        for (int i = N - 2; i >= 0; i--) {
            dp[i][i] = true; // 主对角线
            dp[i][i + 1] = str[i] == str[i + 1]; // 第二条对角线
        }
        dp[N - 1][N - 1] = true;// 最后填[N-1][N-1]
        // 普遍位置，只依赖它左下的位置，从下往上，从左往右填
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j <= N - 1; j++) { // 为什么是i+2， 第3行对角线的最右下的格子(i,j)是（N-3，N-1） --> j = i + 2
                dp[i][j] = str[i] == str[j] && dp[i + 1][j - 1];
            }
        }
        return dp;
    }

    /**
     * 动态规划
     * 问题二：返回问题一的其中一种划分结果
     * 
     * 根据动态规划表进行回溯。
     * 
     * 例子: abacfck 对应的dp是[3,4,3,2,3,2,1,0]
     *      0123456           0 1 2 3 4 5 6 7
     * 1）看第一份是不是0~0，str[0~0]是回文，但是dp[0] != dp[1] + 1，说明0~0不是单独的一份。 dp[1]的1就是代码的j -> j++，变成2
     * 2）看第一份是不是0~1，str[0~1]不是回文，说明0~1不是单独的一份。-> j++，变成3
     * 3）看第一份是不是0~2，str[0~2]是回文，而且dp[0] = dp[3] + 1，说明0~2是单独的第一份，搜集答案。 i变成j，等于3，j++，变成4
     * 4）继续从3位置出发，看3~3是不是单独的一份，str[3~3]是回文，但dp[3] != dp[4] + 1，说明3~3不是单独的一份
     * 5）看3~4是不是单独的一份，str[3~4]不是回文，说明3~4不是单独的一份
     * 6）看3~5是不是单独的一份，str[3~5]是回文，而且dp[3] = dp[6] + 1,说明3~5是单独的一份，搜集答案
     * 7）继续从6位置出发，str[6~6]是回文，而且dp[6] = dp[7] + 1,说明6~6是单独的一份，搜集答案
     * 8）继续从7位置出发，没有元素了，说明搜集完了。
     * 
     */
    public static  List<String> minCutOneWay(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null || s.length() < 2) {
            ans.add(s);
            return ans;
        }
        
        int N = s.length();
        char str[] = s.toCharArray();
        int dp[] = new int[N + 1];
        dp[N] = 0; // base case N出发越界了，不可能搞出回文串
        // 生成checkMap
        boolean checkMap[][] = generateCheckMap(str, N);

        // 从右往左推，生成dp表
        for (int i = N - 1; i >= 0; i--) {
            int next = Integer.MAX_VALUE; // 每次来到一个新的i位置出发，next都必须重新变成系统最大!!!
            // 每次来到i位置，都选出一个最小值
            for (int end = i; end <= str.length - 1; end++) {
                if (checkMap[i][end]) { // str[i~end]如果是回文
                    next = Math.min(next, dp[end + 1]); // end + 1位置出发，选出能够搞出的最少回文份数
                }
            }
            dp[i] = next + 1; // 加上i~end已经切出的一份,就是答案
        }
        
        // 回溯抓答案
        for (int i = 0, j = 1; j <= N; j++) {
            // i~j-1是回文，而且dp[i] == dp[j] + 1, 说明i~j-1是单独的一部分
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                ans.add(s.substring(i, j));// i~j-1是单独的一部分 加入答案
                i = j; // i从j位置重新出发
            }
        }
        return ans;
    }
    
    /**
     * 动态规划
     * 问题三：返回问题一的所有划分结果
     */ 
    public static List<List<String>> minCutAllWays(String s) {
        List<List<String>> ans = new ArrayList<List<String>>();
        
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
            return ans;
        }
       
        List<String> path = new ArrayList<String>();
        int N = s.length();
        char str[] = s.toCharArray();
        int dp[] = new int[N + 1];
        dp[N] = 0; // base case N出发越界了，不可能搞出回文串
        // 生成checkMap
        boolean checkMap[][] = generateCheckMap(str, N);

        // 从右往左推，生成dp表
        for (int i = N - 1; i >= 0; i--) {
            int next = Integer.MAX_VALUE; // 每次来到一个新的i位置出发，next都必须重新变成系统最大!!!
            // 每次来到i位置，都选出一个最小值
            for (int end = i; end <= str.length - 1; end++) {
                if (checkMap[i][end]) { // str[i~end]如果是回文
                    next = Math.min(next, dp[end + 1]); // end + 1位置出发，选出能够搞出的最少回文份数
                }
            }
            dp[i] = next + 1; // 加上i~end已经切出的一份,就是答案
        }
        
        // 回溯抓答案
        process(s, 0, 1, checkMap, dp, path, ans);
        return ans;
    }
    // s[0~i - 1]划分出来的结果都存到path里面去了
    // 现在考察的是s[i~j-1]是不是分出来的第一份
    public static void process(String s, int i, int j, boolean[][] checkMap, int[] dp, 
            List<String> path,
            List<List<String>> ans) {
        if(j == s.length()) { // 来到了结尾位置
            if(checkMap[i][j - 1] && dp[i] == dp[j] + 1) { // i~j-1是回文，而且dp[i] == dp[j] + 1，说明i~j-1是单独的一份
                path.add(s.substring(i, j));
                // 没有了，将所有的path加入到ans中
                ans.add(copyStringList(path)); // 必须copy，否则存放的内存地址，后面递归的时候会直接改
                // 恢复现场
                path.remove(path.size() - 1); // 删除加入的最后一个元素
            }
        } else { // 普遍的i~j-1
            if(checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                // 加入到path之后，递归去搞剩下的，i变成j，j++
                process(s, j, j + 1, checkMap, dp, path, ans);
                // 恢复现场
                path.remove(path.size() - 1);
            }
            // 对于不满足checkMap[i][j - 1] && dp[i] == dp[j] + 1的位置，只需要j++，i不变
            process(s, i, j + 1, checkMap, dp, path, ans);
        }
    }

    public static List<String> copyStringList(List<String> list) {
        List<String> ans = new ArrayList<>();
        for (String str : list) {
            ans.add(str);
        }
        return ans;
    }
}
