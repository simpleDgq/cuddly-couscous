package class04;

public class Code02_SubArrMaxSum {
    /*
     * https://leetcode.cn/problems/maximum-subarray/
     * 子数组最大累加和
     * 
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。子数组 是数组中的一个连续部分。
     * 
     * 看到子数组子串想想每个位置结尾是答案是什么
        如果子数组必须以0结尾, 它往左扩到什么程度，能让累加和最大
        如果子数组必须以1位置结尾, 它往左扩到什么程度，能让累加和最大
     */
    /*
     * 必须以i位置结尾的情况下的累加和：
     * i完全不向左扩，只有i位置，dp[i] = arr[i]
     * i向左扩，取决于i-1位置的最大累加和dp[i] = dp[i-1] + arr[i]
     * 两者取最大值
     * 
     * 最终，dp中的最大值就是答案
     */
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int dp[] = new int[N];
        dp[0] = nums[0]; // 必须以0位置结尾的情况下，最大值，只有0位置，不能向左扩
        
        int max = dp[0]; // max从第一个元素开始
        for(int i = 1; i <= N - 1; i++) {
            int p1 = dp[i-1] + nums[i];
            int p2 = nums[i];
            dp[i] = Math.max(p1, p2);
            max = Math.max(dp[i], max);
        }
        
        return max;
    }
    
    
    // 可以更加优化，没必要申请数组
    public int maxSubArray2(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int pre = nums[0]; // 必须以0位置结尾的情况下，最大值，只有0位置，不能向左扩
        int max = nums[0]; // max从第一个元素开始
        
        for(int i = 1; i <= N - 1; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            max = Math.max(pre, max);
        }
        return max;
    }
    
}
