package class28;

import java.util.ArrayList;
import java.util.List;

// 22. 括号生成
public class Problem_0022_GenerateParentheses {
    /**
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * 
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     */
    
    /**
     * 思路: 递归
     * 搞一个path，长度是2n，每个位置存放的是已经做过的决定，有可能是左括号或者右括号
     */ 
    public List<String> generateParenthesis(int n) {
        ArrayList<String> ans = new ArrayList<String>();
        if(n <= 0) {
            return ans;
        }
        char path[] = new char[n << 1];
        process(path, 0, 0, n, ans);
        return ans;
    }
    
    // 已经做过的决定存放在path中，0~index-1位置都填好了，现在来到了index位置，做决定
    // leftMinusRight和leftRest是为了便于剪枝
    // leftMinusRight: 已经做过的决定中左扩号的数量 - 右括号的数量
    // leftRest: 剩下的可用的左括号数量，对于给定的n，左括号和右括号可用的数量都是n
    // ans用于存放最终的答案
    public void process(char path[], int index, int leftMinusRight, int leftRest, List<String> ans) {
        if(index == path.length) { // path填完了，答案直接加到ans中。为什么敢确定一定是有效的答案？因为剪枝做的好
           ans.add(String.valueOf(path));
        } else {
            // index位置有两种决定
            // 1. 填左括号。如果要填左括号，必须有剩余的左扩号可以使用，也就是leftRest > 0
            if(leftRest > 0) {
                path[index] = '(';
                // 搞下一个位置
                process(path, index + 1, leftMinusRight + 1, leftRest - 1, ans);
            }
            // 2. 填右括号。如果要填右括号，必须已经填的左括号减去右括号大于0，才能保证填右括号之后，是有效的
            if(leftMinusRight > 0) {
                path[index] = ')';
                process(path, index + 1, leftMinusRight - 1, leftRest, ans);
            }
        }
    }
}
