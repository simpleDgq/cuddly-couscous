package class28;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/combination-sum
// 39. 组合总和
public class Problem_0039_CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (candidates == null || candidates.length == 0) {
            return ans;
        }
        List<Integer> path = new ArrayList<Integer>();
        process(0, target, candidates, path, ans);
        return ans;
    }

    /**
     * 当前来到i位置，之前i-1位置做过的决定都在path中，还剩rest这么多要搞
     * 你给我返回所有的答案放在ans中
     * 
     * 这题的模型是来到一个位置： 要或者不要
     * 
     * 回溯感觉有两种模型：
     * 一种是每一个数都要，然后每个数都去尝试一遍。例如全排列，电话号码字母组合这种题目
     * 一种是每一个位置可以要或者不要。例如本题和求子集的题目
     * 区分主要看答案里面是不是要包括给出的所有数字或字符。例如这题的答案里面就不一定要包括所有给出的数，所以是要或不要的模型
     * 全排列那题，答案里面要出现题目给出的所有的数，只不过顺序不同，所以是每个位置都要，然后都去尝试一遍的模型
     */
    public void process(int i, int rest, int[] candidates, List<Integer> path, List<List<Integer>> ans) {
        if (i == candidates.length && rest > 0) {
            // 来到了数组的最后，还没有搞定rest，则搞不定，直接返回
            return;
        }
        if (rest == 0) {
            // rest等于0，说明搞定了
            ans.add(new ArrayList<>(path));
            return;
        }
        // 不要i位置的数，去搞i+1位置
        process(i + 1, rest, candidates, path, ans);
        // 要i位置的数，只有当rest-candidates[i] >= 0的时候，才能要
        if (rest - candidates[i] >= 0) {
            path.add(candidates[i]);
            // 注意i位置是可以重复选的，所以这里还是从i开始
            process(i, rest - candidates[i], candidates, path, ans);
            // 恢复现场
            path.remove(path.size() - 1);
        }
    }
}
