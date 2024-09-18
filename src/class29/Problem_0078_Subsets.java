package class29;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/subsets/
// 78. 子集
public class Problem_0078_Subsets {
    /**
     * 时间复杂度
     * 在任意一层递归中，我们都有 2 种选择（包含或不包含当前元素），这种选择会一直持续到数组末尾。因此，对于长度为 n 的数组，
     * 总的决策路径（即不同的子集组合）是 2 的 n 次方（2^n）。这是因为每个元素都有两种可能性（在或不在子集中），并且这些决策是独立的。
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) {
            return ans;
        }
        List<Integer> path = new ArrayList<Integer>();
        process(0, nums, path, ans);
        return ans;
    }

    // 当前来到了i位置，i位置的数可以要或者不要，以前已经做过的决定放在了path中
    // 你给我返回所有可能得ans
    public void process(int i, int nums[], List<Integer> path, List<List<Integer>> ans) {
        // 如果i来到了nums数组的结尾位置，说明搞完了，path里面是一个答案
        if (i == nums.length) {
            ans.add(new ArrayList<>(path)); // path得new一份，因为后面会改变
            return;
        }
        // 不要i位置的数，直接去搞i+1位置
        process(i + 1, nums, path, ans);
        // 要i位置的数
        path.add(nums[i]);
        process(i + 1, nums, path, ans);
        // 恢复现场
        path.remove(path.size() - 1); // 将上次添加的nums[i]从path拿掉，去加新的i进来
        /*
         * 在path.add(nums[i]);之后，我们进行了另一次递归调用process(i + 1, nums, path, ans);。这次调用结束后，
         * 我们需要将path中的nums[i]移除，因为接下来的递归调用（或递归调用之后的操作）可能不再希望nums[i]在path中。
         */
    }
}
