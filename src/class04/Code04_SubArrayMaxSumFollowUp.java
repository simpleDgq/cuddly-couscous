package class04;

public class Code04_SubArrayMaxSumFollowUp {
 // 在线测试链接 : https://leetcode.com/problems/house-robber/
    /*  198. 打家劫舍  ==> 其实就是求数组的最大累加和，但是要求元素不相邻 （不能相邻的最大子序列累加和） ==> 返回一个数组中，
     * 选择的数字不能相邻的情况下，最大子序列累加和
     * 
     * 原题描述:
     *   你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
        给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     */
    /**
    子序列问题，不连续，不是子数组问题，不能用某个位置结尾来讨论
    这题是从左往右的尝试模型。每个位置都求一遍

    dp[i]: i位置能够获得的最大值？
    3种可能性：
    1. 只要i位置，p1 = nums[i]
    2. 要i位置，左边相邻的位置不要，但是间隔的位置要，p2 = nums[i] + dp[i - 2]
    3. i位置不要，取决于左边的位置能够取得的最大值 p3 = dp[i - 1]
    */
    public int rob(int[] nums) {

         if(nums == null || nums.length == 0) {
            return Integer.MAX_VALUE;
         }
         if(nums.length == 1) {
            return nums[0];
         }
         int N = nums.length;
         int dp[] = new int[N];
         dp[0] = nums[0];// 0位置只有一种可能性，没有左边
         dp[1] = Math.max(nums[0], nums[1]); // 1位置就是nums[0] 和nums[1]谁大取谁
         int max = dp[1];
         for(int i = 2; i <= N - 1; i++) {
            int p1 = nums[i];
            int p2 = nums[i] + dp[i - 2];
            int p3 = dp[i - 1];
            dp[i] = Math.max(p1, Math.max(p2, p3));
            max = Math.max(max, dp[i]);
         }
         return max;
    }
}
