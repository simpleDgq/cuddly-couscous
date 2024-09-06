package class27;

import java.util.HashMap;

// https://leetcode.cn/problems/two-sum/
public class Problem_0001_TwoSum {
    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。

        你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

        你可以按任意顺序返回答案。
        
        

        示例 1：
        
        输入：nums = [2,7,11,15], target = 9
        输出：[0,1]
        解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。

     */
    
    /**
     * 思路:
     * 搞一个map，key是出现过的值，value是这个值所在的数组的位置
     * 来到一个位置i，查看target - nums[i] 在数组中有没有出现过，出现过就找到了答案，直接返回
     */
    
    public int[] twoSum(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return null;
        }
        // key 某个之前的数   value 这个数出现的位置
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int N = nums.length;
        /**
         * 两个元素x，y必然是一前一后出现的，如果存在符合条件的解，
         * 在遍历到x时，哈希表里没有符合的y，此时把x加入到了哈希表里，
         * 当遍历到y时，就可以在哈希表里找到对应的x了，所以只需要一次遍历，
         */
        for(int i = 0; i <= N - 1; i++) {
            if(map.containsKey(target - nums[i])) {
                return new int[] {i, map.get(target - nums[i])};
            }
            // 没有出现过，将当前数放入map
            map.put(nums[i], i);
        }
        // 最后都没有找到，返回[-1,-1]
        return new int[] {-1, -1};
    }
}
