package class25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.cn/problems/3sum/
public class Code02_3Sum {
    /**
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j !=
     * k ，
     * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请 你返回所有和为 0 且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。 字面值要都不一样。
     */

    /**
     * 思路:
     * 先解决二元组的问题，在通过二元组搞出三元组
     * 
     * 二元组问题: 在一个数组中，有多少个二元组，相加等于target这个数，全返回。
     * 
     * 例子: [-3, -2, -1, 0, 1, 2, 4] K =2 求这个数组中，求和等于2的所有的二元组
     * 
     * 数组先排好序，然后用双指针解决、
     * L指向0位置，R指向N-1位置
     * 1. 如果两个位置的值相加，比K小，那么L++
     * 2. 如果两个位置的值相加，比K大，那么R--
     * 3. 如果两个位置的值相加，等于K，收集答案，L++或者R--或者同时变都可以（代码中使用的是L++）
     * 
     * 如果数组中有重复的数字怎么办？
     * 去重： 1) 收集所有二元组,后 遍历一遍，过滤，删掉重复的
     * 2) 通过好的流程设计规避重复 --> 收集答案之前，判断L指向的数和它前一个是不是一样，不一样菜收集答案
     * 
     * 例子: : [-3, -3, -3, -2, -2, -1, -1, 0, 1, 1, 2, 2, 4, 4] K = 1
     * L
     * 这里判断arr[1] == arr[0], 不收集答案，直接跳下一个
     * 
     * 
     * 
     * 三元组问题怎么搞？
     * 例如[-5, -5, -5, -4.....] 求三元组，和为0
     * 第一个数字是0位置的-5的时候，剩下的就是求从1位置到最后，target为5的二元组问题
     * 生成好二元组，然后将-5加到二元组的前面。
     * 
     * 1.你只要保证第1个数字不一样后面就是二元组的问题
     * 
     * 
     * 2.避免arrayList插到开头的代价有点高, 所以从右往左生成所有的三元组, 把一个数塞在最后
     */
    public List<List<Integer>> threeSum(int nums[]) {
        Arrays.sort(nums); // 注意一定要先拍序
        int N = nums.length;
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        // 从最后一个数开始，避免头插，代价高
        // 必须剩两个数，组成二元组，所以i>1
        for (int i = N - 1; i > 1; i--) {
            if (i == N - 1 || nums[i] != nums[i + 1]) {// 三元组，同样，如果当前数等于它前一个数，也不用求了，上一个数都求过
                List<List<Integer>> twoSum = twoSum(nums, i - 1, -nums[i]);
                for (List<Integer> next : twoSum) {
                    next.add(nums[i]);
                    ans.add(next);
                }
            }

        }
        return ans;
    }

    // nums[0...end]这个范围上，有多少个不同二元组，相加==target，全返回
    // 返回值
    // 多个二元组list
    // {-1,5} K = 4
    // {1, 3}
    public List<List<Integer>> twoSum(int nums[], int end, int target) {
        int L = 0;
        int R = end;
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        while (L < R) { // L到R去搞吧
            int sum = nums[L] + nums[R];
            if (sum < target) {// 两数的和比target小
                L++;
            } else if (sum > target) {
                R--;
            } else {
                // L==0是为了L-1不越界  
                //相等，收集答案. 当前数不等于前一个数 
                if (L == 0 || nums[L] != nums[L - 1]) {
                    List<Integer> cur = new ArrayList<Integer>();
                    cur.add(nums[L]);
                    cur.add(nums[R]);
                    // 收集好了一对放入ans
                    ans.add(cur);
                }
                L++; // 相等的时候，L++
            }
        }
        return ans;
    }
}
