package class09;

import java.util.ArrayList;
import java.util.List;

public class Code03_RemoveInvalidParentheses {
    /*
    // 测试链接 : https://leetcode.com/problems/remove-invalid-parentheses/
     * 删除无效的括号(这题递归很难理解，直接背住，需要看视频的例子去理解记忆)
     */
 // 来自leetcode投票第一的答案，实现非常好，我们来赏析一下
    public static List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        remove(s, ans, 0, 0, new char[] { '(', ')' });
        return ans;
    }
    // modifyIndex <= checkIndex
    // 只查s[checkIndex....]的部分，因为之前的一定已经调整对了
    // 但是之前的部分是怎么调整对的，调整到了哪？就是modifyIndex
    // 比如：
    // ( ( ) ( ) ) ) ...
    // 0 1 2 3 4 5 6
    // 一开始当然checkIndex = 0，modifyIndex = 0
    // 当查到6的时候，发现不对了，
    // 然后可以去掉2位置、4位置的 )，都可以
    // 如果去掉2位置的 ), 那么下一步就是
    // ( ( ( ) ) ) ...
    // 0 1 2 3 4 5 6
    // checkIndex = 6 ，modifyIndex = 2
    // 如果去掉4位置的 ), 那么下一步就是
    // ( ( ) ( ) ) ...
    // 0 1 2 3 4 5 6
    // checkIndex = 6 ，modifyIndex = 4
    // 也就是说，
    // checkIndex和modifyIndex，分别表示查的开始 和 调的开始，之前的都不用管了  par  (  )
    public static void remove(String s, List<String> ans, int checkIndex, int deleteIndex, char[] par) {
        for (int count = 0, i = checkIndex; i < s.length(); i++) {
            if (s.charAt(i) == par[0]) {
                count++;
            }
            if (s.charAt(i) == par[1]) {
                count--;
            }
            // i check计数<0的第一个位置
            if (count < 0) {
                for (int j = deleteIndex; j <= i; ++j) { // j从deleteIndex开始，不超过i位置
                    // j位置是右括号，而且j是deleteIndex或者j-1位置不是右括号(不能同时删两个连续的右括号)
                    if (s.charAt(j) == par[1] && (j == deleteIndex || s.charAt(j - 1) != par[1])) {
                        // 删除j位置，从i开始，继续递归去
                        remove(s.substring(0, j) + s.substring(j + 1, s.length()),ans, i, j, par);
                    }
                }
                // 直接返回，i不变
                return;
            }
        }
        // 反转一次
        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') { // 左括号搞完了，去搞右括号
            remove(reversed, ans, 0, 0, new char[] { ')', '(' });
        } else { // 右括号也搞完了，搜集答案
            ans.add(reversed);
        }
    }


}
