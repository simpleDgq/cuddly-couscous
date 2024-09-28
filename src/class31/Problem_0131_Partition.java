package class31;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/palindrome-partitioning
// 131. 分割回文串
public class Problem_0131_Partition {
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<List<String>>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        List<String> path = new ArrayList<String>();
        int N = s.length();
        // 生成好dp表，快速判断是否是回文
        boolean map[][] = generateCheckMap(s.toCharArray(), N);
        process(0, s, path, ans, map);
        return ans;
    }

    /**
     * 当前来到i位置，i位置之前的回文结果都存在path中，你给我返回i及其往后能够
     * 搞定的回文串，放到ans中
     * 
     * 对于每一个i位置，到它后面的每一个位置(end)都枚举一下，看是不是回文，是就加入到path中
     * 然后从新的end+1位置开始搞
     */
    public void process(int i, String s, List<String> path, List<List<String>> ans, boolean map[][]) {
        if (i == s.length()) {
            // 如果i来到了str结尾的位置，path中就是答案，加入
            ans.add(new ArrayList<>(path));
            return;
        }
        // i到它后面的位置都尝试一遍。看是不是回文，是的话就加入path，然后从新的位置开始搞
        for (int end = i; end <= s.length() - 1; end++) {
            // i开始到end是回文
            if (map[i][end]) {
                // 加入path
                path.add(s.substring(i, end + 1));
                // 从end+1出发去尝试后面没有个位置是否能成回文
                process(end + 1, s, path, ans, map);
                // 尝试完了，end要++了，恢复现场
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * 生成checkMap，检查i到j位置是不是回文:
     * dp[N][N] 的表，记录任意i到j是不是回文。
     * 对角线位置，只有一个字符，所以都是true，
     * 第二条对角线位置，比如0到1,1到2...
     * 只有两个字符，如果str[i] == str[j]就是true，否则是false
     * 左下半区不用
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

    // 判断str中i到j，是不是回文
    // 优化，使用dp表来判断是否是回文，优化时间复杂度
    // public boolean isPalindrome(int i, int j, String s) {
    //     char[] str = s.toCharArray();
    //     int L = i;
    //     int R = j;
    //     while (L <= R) {
    //         if (str[L] != str[R]) {
    //             return false;
    //         }
    //         L++;
    //         R--;
    //     }
    //     return true;
    // }

}
