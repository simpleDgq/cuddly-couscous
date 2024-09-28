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
     * 子数组问题
     * 以i位置结尾的数组最大乘积是多少？
     * 当前位置如果是一个负数的话，那么我们希望以它前一个位置结尾的某个段的积也是个负数，
     * 这样就可以负负得正，并且我们希望这个积尽可能「负得更多」，即尽可能小。
     * 如果当前位置是一个正数的话，我们更希望以它前一个位置结尾的某个段的积也是个正数，
     * 并且希望它尽可能地大。
     * 
     * 最大乘积分析可能性：
     * 1. 只与i位置有关，dpMax[i] = nums[i]
     * 2. nums[i]是负数，那么乘上前面i-1位置的最小乘积，就是最大 dpMax[i] = nums[i] * dpMin[i - 1]
     * 3. nums[i]是正数，那么乘上前面i-1位置的最大乘积，就是最大 dpMax[i] = nums[i] * dpMax[i - 1]
     * 维护两张表，dpMax dpMin 分别表示以i位置结尾的数组最大，最小乘积是多少？
     * 
     * 最小乘积分析可能性：
     * 1. 只与i位置有关，dpMin[i] = nums[i]
     * 2. nums[i]是负数，那么乘上前面i-1位置的最大乘积，就是最小 dpMin[i] = nums[i] * dpMax[i - 1]
     * 3. nums[i]是正数，那么乘上前面i-1位置的最小乘积，就是最小 dpMin[i] = nums[i] * dpMin[i - 1]
     * 
     空间优化：
     没必要用数组，直接搞两个变量滚动更细就完事了！！！
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
