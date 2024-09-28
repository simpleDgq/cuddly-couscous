package class37;

// 416. 分割等和子集
// https://leetcode.cn/problems/partition-equal-subset-sum/description/
public class Problem_0416_canPartition {
    /**
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * 
     * 示例 1：
     * 输入：nums = [1,5,11,5]
     * 输出：true
     * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
     * 
     * 示例 2：
     * 输入：nums = [1,2,3,5]
     * 输出：false
     * 解释：数组不能分割成两个元素和相等的子集。
     */
    
    /**
     * 能够被划分为两个和相等的子集，那么子集的和一定是数组和的一半
     * 该题可以转换为求，一个数组里面，子集能不能搞出数组累加和的一半target，
     * 如果能搞定，剩下的数的累加和就是数组和的另一半
     * 
     * ==> 数组能不能搞出累加和的一半target
     * 背包问题
     * 
     * 动态规划: dp[i][j]: 0~i位置随便选，能不能搞出累加和j
     * 
     * i 取值范围: 0~ N - 1 
     * j 取值范围: 0~ sum
     * 
     * dp[N][sum + 1]
     * 
     * 普遍位置：dp[i][j] 
     * 1.i位置不选，取决于0到i-1位置随便选，能不能搞出j
     * 2.选i位置，取决于0到i-1位置随便选，能不能搞出j-nums[i]
     */
    
    public boolean canPartition(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        int N = nums.length;
        int sum = 0;
        // 求累加和
        for(int i = 0; i <= N - 1; i++) {
            sum += nums[i];
        }
        // 如果累加和是奇数，则整个数组不可能被分为两半，直接返回false
        if(sum % 2 != 0) {
            return false;
        }
        // 右移一位，得到targetSum
        sum >>= 1;
        
        // 填好第一列， 0 ~ N - 1位置随便选(key选或者不选)，能不能搞出累加和0
        boolean dp[][] = new boolean[N][sum + 1];
        for(int i = 0; i <= N - 1; i++) {
           dp[i][0] = true; // 不选的情况下，一定能搞出累加和0
        }
        // 填好第一行， 0位置可以选或者不选，能不能搞出累加和j
        for(int j = 1; j <= sum; j++) {
             // 0位置不选的话，肯定搞不成j，因为j>=1；0位置选的话，如果nums[0]正好等于j，那么就能搞出
            if(nums[0] == j) {
                dp[0][j] = true;
            }
        }
        // 从上往下，从左往右填表
        for(int i = 1; i <= N - 1; i++) {
            for(int j = 1; j <= sum; j++) {
                // 如果不选i位置，那么0~i位置能不能搞出j，取决于0~i-1位置随便选能不能搞出j
                dp[i][j] = dp[i - 1][j];
                // 如果选i位置，那么0~i位置能不能搞出j，取决于0~i-1位置随便选能不能搞出j - nums[i]
                // j - nums[i]要有效(j相当于背包大小，nums[i]相当于要装的东西，背包要装的下，才继续装)
                if(j - nums[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[N - 1][sum]; // 0~N-1随便选，能不能搞出累加和sum
    }
}
