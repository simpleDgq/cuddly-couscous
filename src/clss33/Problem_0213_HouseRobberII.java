package clss33;

// 213. 打家劫舍 II
public class Problem_0213_HouseRobberII {
    /**
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，
     * 这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻
     * 的房屋在同一晚上被小偷闯入，系统会自动报警 。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
     * 
     * 示例 1：
     * 输入：nums = [2,3,2]
     * 输出：3
     * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     * 
     * 示例 2：
     * 输入：nums = [1,2,3,1]
     * 输出：4
     * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * 
     * 示例 3：
     * 输入：nums = [1,2,3]
     * 输出：3
     */
    
    /**
     * 思路: 打家劫舍 I的变种
     * 
     * 打家劫舍I中，首尾不相连，也就是给定一个数组，子序列的最大累加和
     * 
     * 动态规划: dp[i] : 0 ~ i范围上的最大累加和
     * 
     * 可能性: 
     * 1. 只和i位置有关，dp[i] = arr[i]. 例如，前面都是负数，arr[i]位置是正数
     * 2. 不要i位置的数，那么可能性来自于0~i-1位置，dp[i] = dp[i - 1]
     * 3. 要i位置的数，那么之前要在0~i-2位置上做选择，因为不能选相邻的元素，选了i，就不能选i-1了。 dp[i] = dp[i - 2] + arr[i]
     * 三种可能性取最大值
     */
    public int rob(int[] nums) {
        int length = nums.length;
        int dp[] = new int[length];
        // 0位置没有选择，只有一个数
        dp[0] = nums[0];
        // 1位置，因为不能选相邻的数，所以就是0位置和1位置，谁大取谁
        dp[1] = Math.max(nums[0], nums[1]);
        // 从前往后填
        for(int i = 2; i <= length - 1; i++) {
            int p1 = nums[i];
            int p2 = dp[i - 1];
            int p3 = dp[i - 2] + nums[i];
            dp[i] = Math.max(p1, Math.max(p2, p3));
        }
        return dp[length - 1];
    }
    
    /**
     * 打家劫舍II
     * 
     * 两种情况:
     * 1. 选0位置，那么N-1位置就不能选 --> 问题变成0~N-2范围上，子序列最大累加和
     * 2. 不选0位置，那么N-1位置就可以选 --> 问题变成1~N-1范围上，子序列最大累加和
     * 两种情况下，都搞动态规划，最终就是两者取最大值
     */
    public int rob2(int[] nums) {
        int length = nums.length;
        if(length == 1) {
            return nums[0];
        }
        if(length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        
        // 要0位置, 0 ~ N - 2范围上子序列，最大累加和
        int dp1[] = new int[length]; // 0 ~ N - 2范围上, length - 1位置不用
        dp1[0] = nums[0];
        dp1[1] = Math.max(nums[0], nums[1]);
        for(int i = 2; i <= length - 2; i++) {
            int p1 = nums[i];
            int p2 = dp1[i - 1];
            int p3 = dp1[i - 2] + nums[i];
            dp1[i] = Math.max(p1, Math.max(p2, p3));
        }
        // 不要0位置, 1 ~ N - 1范围上子序列，最大累加和
        int dp2[] = new int[length]; // 1 ~ N - 1范围上, 0位置不用
        dp2[1] = nums[1];
        dp2[2] = Math.max(nums[1], nums[2]);
        for(int i = 3; i <= length - 1; i++) {
            int p1 = nums[i];
            int p2 = dp2[i - 1];
            int p3 = dp2[i - 2] + nums[i];
            dp2[i] = Math.max(p1, Math.max(p2, p3));
        }
        
        return Math.max(dp1[length - 2], dp2[length - 1]);
    }
}
