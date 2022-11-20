package class14;

//本题测试链接 : https://leetcode.com/problems/longest-valid-parentheses/
public class Code01_Parentheses {
    // 32. 最长有效括号
    /**
     * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
        示例 1：
        
        输入：s = "(()"
        输出：2
        解释：最长有效括号子串是 "()"
        示例 2：
        
        输入：s = ")()())"
        输出：4
        解释：最长有效括号子串是 "()()"
        示例 3：
        
        输入：s = ""
        输出：0
        提示：
        0 <= s.length <= 3 * 10^4
        s[i] 为 '(' 或 ')'
     */
    
    /**
     * 思路:
     * 子串问题 -> 以每一个位置结尾的字符串，最长是多少，所有的答案取最大值，就是最终的答案。
     * 
     * 1) 当来到i位置的时候, 如果是个左括号就是0
     *   如果是个右括号, 要看i-1位置往左能推多长, 假设到了K位置,
     *   如果K位置也是右括号, 则当前i位置无效
     * 2) 如果 K位置是左括号, 那么i位置的答案起码是A+2长度
     *     还要看K-1位置前面的有效答案怼上去
     *     不需要多步跳, 只需要看k-1位置的答案即可
     */
    public int longestValidParentheses(String s) {
        if(s == null || s.length() < 2) {
            return 0;
        }
        char str[] = s.toCharArray();
        int N = str.length;
        // dp[i]含义 : 以i位置结尾的子串，最长括号子串长度是多少
        int dp[] = new int [N];
        // dp[0] = 0 0位置结尾，左边没有了，不可能形成有效括号子串，所以是0
        int pre = 0;
        int ans = 0;
        for(int i = 1; i <= N - 1; i++) {
            if(str[i] == ')') { // 如果i位置是右括号才考虑，如果是左括号，答案就是0，dp默认就是0，不用管
                pre = i - dp[i - 1] - 1; // 与str[i]配对的括号的位置
                if(pre >= 0 && str[pre] == '(') { // pre需要 >= 0, 如果与str[i]配对的括号的位置越界了，说明i是无效位置
                    // 如果没有越界，而且与str[i]配对的括号的位置是左括号，那么至少加一层皮
                    // 如果pre-1位置有效，还需要怼上pre-1位置的答案
                    dp[i] = dp[i - 1] + 2 + (pre - 1 >= 0 ? dp[pre - 1] : 0);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

}
