package class28;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/permutations/
// 46. 全排列
public class Problem_0046_Permute {
    /**
     * 为了生成所有排列，算法需要尝试 nums 中每个元素在每个位置的可能性。
     * 这实际上是一个排列问题，其时间复杂度是 O(n!)，其中 n 是 nums 的长度。
     * 这是因为对于第一个位置，有 n 个选择；对于第二个位置，有 n-1 个选择（因为已经选择了一个元素）；
     * 依此类推，直到最后一个位置只有 1 个选择。因此，总的排列数是 n * (n-1) * (n-2) * ... * 2 * 1 = n!。
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) {
            return ans;
        }
        int N = nums.length;
        List<Integer> path = new ArrayList<Integer>();
        boolean[] onPath = new boolean[N];
        process(0, nums, ans, path, onPath);
        return ans;
    }

    // 当前来到了i位置，要决定i位置的数，以前已经选择的数存在了path中，onPath告诉你某个位置的数是否使用过，
    // 你给我返回ans
    public void process(int i, int[] nums, List<List<Integer>> ans, List<Integer> path, boolean[] onPath) {
        if (i == nums.length) { // i等于了数组的长度，说明path就是答案
            ans.add(new ArrayList<>(path));  // 这里得重新new，
            return;
        }
        // 依次考虑每一个数
        for (int j = 0; j <= nums.length - 1; j++) {
            // 如果i位置的数没有选择过
            if (!onPath[j]) {
                onPath[j] = true;
                // i位置的数使用nums[j]
                path.add(nums[j]);
                // 去搞下一个i+1位置
                process(i + 1, nums, ans, path, onPath);
                // 恢复现场
                onPath[j] = false;
                path.remove(path.size() - 1); // 要去选下一个j了，得从path里面将上次添加的nums[j]拿掉
            }
        }
        /*
         * 在path.add(nums[i]);之后，我们进行了另一次递归调用process(i + 1, nums, path, ans);。这次调用结束后，
         * 我们需要将path中的nums[i]移除，因为接下来的递归调用（或递归调用之后的操作）可能不再希望nums[i]在path中。
         */
    }
    
  
    
    
    
    
    
    
    
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if(nums == null || nums.length == 0) {
            return ans;
        }
        // 下面这行代码会存在类型转换的问题
        // process(Arrays.asList(nums), 0, ans);
        List<Integer> numsTmp = new ArrayList<Integer>();
        for(int num : nums) {
            numsTmp.add(num);
        }
        process2(numsTmp, 0, ans);
        return ans;
    }

    /**
     * 时间复杂度O(N!)
     * 由于我们是在生成所有可能的排列，所以总的操作数实际上是n的阶乘（n!），
     * 因为每个位置都可以放置n个元素中的任何一个，而下一个位置则只能放置剩下的n-1个元素中的任何一个，依此类推
     * 
     * 递归意义:
     * 给你nums以及当前应该考虑的index，
     * 和后面的数字交换，考虑谁可以做index位置，你给我返回所有可能的结果
     * 
     * nums里面的数字交换一次，相当于固定了前面的一小段，然后index+1去搜集后面的答案
     * 当答案搜集完之后，返回了，要恢复一下现场，因为index位置要继续和下一个新的i位置去交换了
     */
    public void process2(List<Integer> nums, int index, List<List<Integer>> ans) {
        if (index == nums.size()) { // 到最后了，搜集答案
            // 在将nums添加到ans中时，我使用了new ArrayList<>(nums)来创建一个新的列表副本，
            //而不是直接添加nums的引用。这是因为nums在递归过程中会被不断修改，如果直接添加引用，
            // 则结果集中的所有列表都会指向同一个列表对象，这会导致它们最终都包含相同的元素（即最后一次递归修改后的元素）
            // ans.add(nums); // 不能这样做，解释见上面
            ans.add(new ArrayList<>(nums)); // 添加当前排列的副本到结果集中
        } else {
            // 进行交换，index位置的值只能和它自己以及后面的数字交换
            for (int i = index; i < nums.size(); i++) {
                // 当前考虑的位置index，和后面的任意一个字符交换，得到一个答案
                swap(i, index, nums);
                // 继续从index + 1 位置开始
                process2(nums, index + 1, ans);
                // index要和新的i位置去交换了，index位置得先恢复过来
                swap(i, index, nums);
            }
        }
    }

    // 交换arrayList中的两个数
    public void swap(int i, int j, List<Integer> nums) {
        int temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }
}
