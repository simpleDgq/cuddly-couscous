package class32;

//152. 乘积最大子数组
public class Problem_0152_MaximumProductSubarray {
    /**
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），
     * 并返回该子数组所对应的乘积。
     * 测试用例的答案是一个 32-位 整数。
     * 子数组: 是数组的连续子序列。
     */
    
    /**
     * 思路:
     * 子数组问题，以某某位置开头，以某某位置结尾，都搞出一个答案
     * 
     * 这题: 以i位置结尾，能搞出最大的乘积是多少，每一个位置都求，然后取最大值
     * 
     * dp[i]: 以i位置结尾的情况下，最大乘积是多少
     * 
     * i位置可能性分析:
     * 1. 最大乘积只有nums[i]位置自己。例如i位置是正数，dp[i - 1]是负数
     * 2. 最大乘积是nums[i] * dp[i - 1]。例如nums[i]是正数，dp[i - 1]是正数
     * 3. 最大乘积是nums[i] * 前面i-1位置的最小乘积。例如nums[i]是负数，要想得到最大的乘积，需要i-1位置
     *    是最小乘积，比如也是负数，乘上才越大
     *    发现这种情况还需要求每个位置的最小乘积 --> 两个dp表，最大乘积和最小乘积
     *    
     * 最小乘积可能性分析:
     * 1. 最小乘积只有nums[i]位置自己。例如i位置是正数，dp[i - 1]是正数
     * 2. 最小乘积是nums[i] * 前面i - 1位置的最大乘积。例如nums[i]是负数，dp[i - 1]是正数
     * 3. 最小乘积是nums[i] * 前面i - 1位置的最小乘积。例如nums[i]是正数，dp[i - 1]是负数
     */
    public int maxProduct(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        // 以i位置结尾的情况下，最大乘积是多少
        int dpMax[] = new int[N];
        // 以i位置结尾的情况下，最小乘积是多少
        int dpMin[] = new int[N];
        // 以0位置结尾的情况下，只有一个数，最大最小乘积就是nums[0]
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];
        int ans = nums[0];
        // 从前往后推
        for(int i = 1; i <= N - 1; i++) {
            int p1 = nums[i];
            int p2 = nums[i] * dpMax[i - 1];
            int p3 = nums[i] * dpMin[i - 1];
            
            dpMax[i] = Math.max(p1, Math.max(p2, p3));
            dpMin[i] = Math.min(p1, Math.min(p2, p3));
            
            ans = Math.max(dpMax[i], ans);
        }
        return ans;
    }
    
    // 不开辟数组，用有限的几个变量，滚动更新
    public int maxProduct2(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int dpMax = nums[0];
        int dpMin = nums[0];
        int ans = nums[0];
        // 从前往后推
        for(int i = 1; i <= N - 1; i++) {
            int p1 = nums[i];
            int p2 = nums[i] * dpMax;
            int p3 = nums[i] * dpMin;
            
            dpMax = Math.max(p1, Math.max(p2, p3));
            dpMin = Math.min(p1, Math.min(p2, p3));
            
            ans = Math.max(dpMax, ans);
        }
        return ans;
    }
}
