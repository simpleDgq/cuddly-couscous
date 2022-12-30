package class22;

//本题测试链接 : https://leetcode.cn/problems/maximum-sum-of-3-non-overlapping-subarrays/
public class Code01_MaximumSumof3NonOverlappingSubarrays {
    /**
     * 给定数组 nums 由正整数组成，找到三个互不重叠的子数组的最大和。
        每个子数组的长度为k，我们要使这3* k个项的和最大化。
        
        返回每个区间起始索引的列表（索引从 0 开始）。如果有多个结果，返回字典序最小的一个。
        
        示例:
        
        输入: [1,2,1,2,6,7,5,1], 2
        输出: [0, 3, 5]
        解释: 子数组 [1, 2], [2, 6], [7, 5] 对应的起始索引为 [0, 3, 5]。
        我们也可以取 [2, 1], 但是结果 [1, 3, 5] 在字典序上更大。
        注意:
        
        nums.length的范围在[1, 20000]之间。
        nums[i]的范围在[1, 65535]之间。
        k的范围在[1, floor(nums.length / 3)]之间。
     */
    
    
    /**
     * 思路:
     * 这题还讲了两个基本问题
     * 1)dp[i]在0~i范围上，随意选一个子数组，累加和最大是多少？
     *    先求help数组: help[i]: 子数组必须以i位置数结尾情况下, 累加和怎么最好？ 然后利用help数组求dp
     *    help[i]怎么求? 
     *      1)必须以i位置结尾, 不往左边扩, 只有arr[i]
     *      2)i向左扩，取决于i-1位置的最大累加和: help[i] = help[i-1] + arr[i]
     *    两种情况取最大值。
     *    
     *    求好了help[i], 怎么搞出dp[i]?
     *    可能性: 1) 必须以i位置结尾，累加和最大值 help[i]
     *           2) 不以i位置结尾的情况下，0~i-1位置的数任意选择，dp[i - 1]
     *    两种情况取最大值。
     *    
     * 2)在0~i范围上，随意选一个子数组，长度必须为k，累加和最大是多少?
     *   先求help数组: help[i]: 子数组必须以i位置数结尾情况下, 子数组长度必须为k, 累加和怎么最好？ 然后利用help数组求dp
     *   help[i]怎么求?
     *     因为长度必须要求是k。所以0~k-2的元素不够k个，累加起来
     *     从k-1个元素开始，才是有效的
     *     每一个位置，前面出去一个，后面进来一个就能得到必须以该位置结尾，长度为k，累加和是多少。
     *   
     *   求好了help[i], 怎么搞出dp[i]?
     *    可能性: 1) 必须以i位置结尾，累加和最大值 help[i]
     *           2) 不以i位置结尾的情况下，0~i-1位置的数任意选择，dp[i - 1]
     *    两种情况取最大值。
     *    
     * 解决原题:
     *   整个数组从左往右遍历生成dp数组: 0~i范围上, 如果只选一个子数组, 长度为k, 累加和怎么最大?
     *   再从右往左生成dp'[]数组: i~N-1范围上, 如果只选一个子数组, 长度为k, 累加和怎么最大?
     *   
     *   搞两个指针L和R，指向中间一段长度为k的范围，如果中间的数组去这个范围的数组，那么问题就变成了，怎么求这段范围的两边，长度为k的子数组
     *   累加和最大 -->  也就变成了求0~i 范围上，选择一个长度为k的子数组，累加和怎么最大，以及 i~N-1范围选择一个长度为k的子数组，累加和怎么最大
     *   可以通过上面的两个dp搞出来。
     * 
     *   L和R一直往右移动，求得所有的范围上，三部分累加和的最大值就是答案。 --> 原题求的是开始下标。 直接看代码
     */
    // 在0~i范围上，随意选一个子数组，累加和最大是多少？
    public int[] maxSumArray1(int arr[]) {
        int N = arr.length;
        int help[] = new int[N];
        // help[i] 子数组必须以i位置结尾的情况下，累加和最大是多少？
        help[0] = arr[0];
        for(int i = 1; i <= N - 1; i++) {
            int p1 = arr[i];
            int p2 = help[i - 1] + arr[i];
            help[i] = Math.max(p1, p2);
        }
        // dp[i]在0~i范围上，随意选一个子数组，累加和最大是多少？
        int dp[] = new int[N];
        dp[0] = help[0]; // 0~0任意选(不能为空)
        for(int i = 1; i <= N - 1; i++) {
            int p1 = help[i];
            int p2 = dp[i - 1];
            dp[i] = Math.max(p1, p2);
        }
        return dp;
    }
    
    //在0~i范围上，随意选一个子数组，长度必须为k，累加和最大是多少?
    public int[] maxSumArray2(int arr[], int k) {
        int N = arr.length;
        // help[i]: 必须以i位置结尾的情况下，在0~i范围上，随意选择子数组长度是k，最大累加和是多少？
        int help[] = new int[N];
        int sum = 0;
        // 0 ~ k-2的数累加
        for(int i = 0; i <= k - 2; i++) {
           sum += arr[i]; 
        }
        // 从k-1开始，求每个位置，长度为k，必须以该位置结尾，累加和最大是多少(其实就是求累加和，进来一个出去一个，因为长度
        // 为k这个条件限制死了)。
        for(int i = k - 1; i <= N - 1; i++) {
            sum += arr[i];
            help[i] = sum;
            // i++之前，sum需要减去i- k + 1位置的数。 arr前面出去一个，后面累加一个
            sum -= arr[i - k + 1];
        }
        // dp[i]在0~i范围上，随意选一个子数组，长度为k，累加和最大是多少？
        int dp[] = new int[N];
        dp[0] = help[0]; // 0~0任意选(不能为空)
        for(int i = 1; i <= N - 1; i++) {
            int p1 = help[i];
            int p2 = dp[i - 1];
            dp[i] = Math.max(p1, p2);
        }
        return dp;
    }
    
    //在i~N-1范围上，随意选一个子数组，长度必须为k，累加和最大是多少?
    public int[] maxSumArray3(int arr[], int k) {
        int N = arr.length;
        // help[i]: 必须以i位置开始的情况下，在i~N-1范围上，随意选择子数组长度是k，最大累加和是多少？
        int help[] = new int[N];
        int sum = 0;
        // N - k + 1 ~ N - 1 中间的数，k - 1个，不够k个，累加
        for(int i = N - k + 1; i <= N - 1; i--) {
           sum += arr[i]; 
        }
        // 从N - k开始，求每个位置，长度为k，必须以该位置开始，累加和最大是多少(其实就是求累加和，进来一个出去一个，因为长度
        // 为k这个条件限制死了)。
        for(int i = N - k; i >= 0; i--) {
            sum += arr[i];
            help[i] = sum;
            // i++之前，sum需要减去i + k - 1 位置的数。 arr前面进来一个，后面出去一个
            sum -= arr[i + k - 1];
        }
        // dp[i]在i~N-1范围上，随意选一个子数组，长度为k，累加和最大是多少？
        // 可能性1: 必须以i开始，也就是help[i]
        // 可能性2: 不以i开始，i+1到N-1位置上，选k个，也就是dp[i+1]
        int dp[] = new int[N];
        dp[0] = help[0]; // 0~0任意选(不能为空)
        for(int i = 1; i <= N - 1; i++) {
            int p1 = help[i];
            int p2 = dp[i + 1];
            dp[i] = Math.max(p1, p2);
        }
        return dp;
    }
 
    
    /**
     * 原题
     */
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int N = nums.length;
        // range[i]： 必须以i位置开始，长度为k的子数组，累加和是多少
        int[] range = new int[N];
        // left[i]: 0~i位置，任意选，长度为k的子数组，累加和最大的子数组的开始位置的下标是多少？
        int[] left = new int[N];
        int sum = 0;
        // 前k个数累加
        for(int i = 0; i <= k - 1; i++) {
            sum += nums[i];
        }
        left[k - 1] = 0; // 0~k-1位置，任意选，长度为k的子数组，累加和最大的子数组的开始位置的下标是0？填好
        range[0] = sum; // 必须以0位置开始，长度为k的子数组，累加和是多少，就是sum
        int max = sum;
        for(int i = k; i <= N - 1; i++) {
            // 后面进来一个，前面出去一个
            sum = sum + nums[i] - nums[i - k]; // 第k个数进来的时候，第0个出去，所有是i-k
            range[i - k + 1] = sum; // 必须以i-k+1开始，长度为k的子数组，累加和是多少
            left[i] = left[i - 1]; // 0~i位置的子数组最大累加和是由0~i-1位置得到的，所以累加和最大子数组，开始位置的下标就是left[i - 1]
            if(sum > max) { // 如果新的范围更新了最大值，需要记录新的子数组累加和的开始下标i-k+1
                max = sum;
                left[i] = i - k + 1;
            }
        }
        
        // i ~ N - 1位置，任意选，长度为k的子数组，累加和最大的子数组的开始位置的下标是多少？
        int[] right = new int[N];
        sum = 0;
        // 后k个数累加
        for(int i = N - k; i <= N - 1; i++) {
            sum += nums[i];
        }
        right[N - k] = N -k; // 必须以N-k位置开始，N-K ~ N - 1任意选，长度为k的子数组，累加和最大的子数组的开始位置的下标是N - k. 填好
        max = sum;
        for(int i = N - k - 1; i >= 0; i--) {
            // 前面进来一个，后面出去一个
            sum = sum + nums[i] - nums[i + k];
            right[i] = right[i + 1]; // 子数组最大累加和是由i+1~N-1位置得到的，所以开始位置的下标就是right[i+1]
            if(sum >= max) { // 如果新的范围更新了最大值，需要记录新的子数组累加和的开始下标
                             // 这里为什么是等于? 因为题目要求的是返回字典序最小的下标。所以当
                             // sum == max的时候，当前的累加和子数组的开始下标更小，需要更新到right[i]
                max = sum;
                right[i] = i;
            }
        }
        /**
         * 大流程
         * 上面求得了，0~i位置，任意选，长度为k的子数组，累加和最大的子数组的开始位置的下标是多少？
         * 以及i ~ N - 1位置，任意选，长度为k的子数组，累加和最大的子数组的开始位置的下标是多少？
         * 搞一个中间位置L~R范围, 求出这块累加和，然后怼上0~L-1范围和R+1范围的累加和最大值，就得到了一个答案
         * L和R同时往右移动，搞出所有的答案，求max，同时更新下标，就是最终的答案
         */
        int L = k; // 中间一块的起始点 (0...k-1)选不了
        int R = L + k - 1;
        max = 0;
        int a = 0;
        int b = 0;
        int c = 0;
        while(L <= N - 2*k) { // 必须保证有3块范围，L不能超过N - 2*k, 否则后面的部分就凑不出2块了
            int part1 = range[L]; // 以L位置开始，长度为k的子数组的累加和
            int part2 = range[left[L - 1]]; // 0~L-1范围上任意选择，累加和最大值
            int part3 = range[right[R + 1]]; // R+1~N-1范围上任意选择，累加和最大值
            if(part1 + part2 + part3 > max) {
                // 更新max已经下标
                max = part1 + part2 + part3;
                b = L;
                a = left[L - 1];
                c = right[R + 1];
            }
            L++;
            R++;
        }
        return new int[] {a, b , c};
    }
    
    
}
