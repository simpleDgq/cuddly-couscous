package class16;

import java.util.HashSet;

public class Code01_IsSum {
    
    /**
     * 数组的子集能否累加出K
     * 
     * 给定一个有正、有负、有0的数组arr，
        给定一个整数k，
        返回arr的子集是否能累加出k
        1）正常怎么做？
        2）如果arr中的数值很大，但是arr的长度不大，怎么做？
     */
    
    /**
     * 思路:
     * 第一问: 背包 动态规划  ==> 这个解法的重点在于，要求的dp[i][j] 实际上是要求dpi[][j - min], 因为平移了，就像例子中的第一个元素-7，要求
     * dp[0][-7] -> 平移到了dp[0][0]
     * 
     * 子集就是子序列
     *   每一个位置的数要跟不要展开生成的子序列就对应一个子集
     *   标准的从左往右的尝试模型
     * 
     * dp[i][j]: arr 0~i范围的数自由选择能不能累加出j这个累加和来, 是一张true, false表, 分情况讨论:
     *   1)完全不用i位置的数,i-1位置能够搞定j: dp[i-1][j]
     *   2)一定要含有i位置的数, 需要i-1位置能够搞定j-arr[i]: dp[i-1][j-arr[i]] 
     * 
     * 难点: 列的准备，如果K=10
     *  如果arr里只有正数, 列值只需要准备0~10就够了;
     *  难点在于有正、有负、有0, 只定义0~K是不够的, 有负数列的大小不能够按照K的大小来决定
     * ==> 
     *  把所有负数累加到一起, 所有正数累加到一起, 这样累加和可能范围就定下来了
     * ==> 
     *  如果累加之后得到的范围是-10 ~ 20，但是矩阵没有-10的下标, 做平移, 比如-10~20, 平移后coding里对应0~30就可以了
     *  
     *  这么一来，想象的dp[i][j] 要拿dp[i][j-min]
     *  例如dp[0][-4] 平移之后对应的事dp[0][0] ==> 看笔记例子
     * 
     */
    public static boolean isSum(int arr[], int sum) {
        if(sum == 0) {
            return true; // 数组中一个数也不取，就能搞定0
        }
        if(arr == null || arr.length == 0) {
            return false; // 一个数也没有，搞个毛
        }
       
        int min = 0;
        int max = 0;
        int N = arr.length;
        for(int i = 0; i <= N - 1; i++) {
            min += arr[i] < 0 ? arr[i] : 0; // 如果是负数就累加
            max += arr[i] > 0 ? arr[i] : 0; // 如果是正数数就累加
        }
        if(min > sum || max < sum) {
            return false;
        }
        boolean dp[][] = new boolean[N][max - min + 1];
        //  0   1   2   3  4    5   6    7 (实际的对应)
        // -7  -6  -5  -4  -3   -2  -1   0（想象中）
        //                              想象中的dp[0][0]
        //                              实际上是dp[0][7]
        // 第0行
        // 如果全是正数，本来要填dp[0][0] --> 一个数也不选的时候，能搞出0，是true
        // 如果全是正数，那么直接dp[0][0] = true没有问题
        // 但是dp[0][0] 对应的实际上是dp[0][-min]  --> 也就是dp[0][7]
        dp[0][-min]= true;
        // dp[0][arr[0]] // 0位置是 arr[0]，一定能搞出累加和arr[0]
        // 想象中是dp[0][arr[0]]， 时间上是dp[0][arr[0] - min]
        dp[0][arr[0] - min] = true;
        // 从上往下，从左往右填表
        for(int i = 1; i <= N - 1; i++) {
            for(int j = min; j <= max; j++) {
                // 可能性1 如果全是正数的情况想，本来要求  dp[i][j] = dp[i - 1][j]
                // 实际上是dp[i][j - min] =  dp[i - 1][j - min]
                dp[i][j - min] =  dp[i - 1][j - min];
                // 可能性2 如果全是正数的情况想，本来要求  dp[i][j] = dp[i - 1][j - arr[i]]
                // 实际上是dp[i][j - min] =  dp[i - 1][j - min - arr[i]]
                int next = j - min - arr[i];
                if(next >= 0 && next <= max - min) { // 只有不越界的情况下，才求。 本来是<= max， 但是实际上的范围是<= max - min
                    dp[i][j - min] |= dp[i - 1][next];
                }
            }
        }
        // 本来要返回dp[N - 1][sum] --> 实际对应到dp[N - 1][sum - min]
        return dp[N - 1][sum - min];
    }
    
    /**
     * 如果arr中的数值很大，但是arr的长度不大，怎么做？
     * 数值很大，导致j的范围很大，会使复杂度超高。
     * 
     * 数组长度很小，就可以使用分治的方式
     * 
     * 假设arr 长度40, 如果不切成左右两半2^40, 程序是跑不完的
        分左右两半, 每边20个数跑暴力每个数要跟不要都展开(2^20), 收集所有累加和
        左边20个数暴力的方式跑出来100万个累加和, 
        右边20个数暴力的方式跑出来100万个累加和, 
        问arr 40个数, 任意选你能不能搞定某个累加和 17 怎么算？
        如果单独, 左边, 右边可以搞定17就返回T, 否则
        左右两侧凑, 想一个整合逻辑
        遍历左边所有可能的累加和一百万个。但是当我一旦确定一个累加和的时候，
        我在右边找他所伴随的累加和是非常快的。
        比如左边3, 右边凑14
        O(1)
     */
    public static boolean isSum1(int arr[], int sum) {
        if(sum == 0) {
            return true; // 数组中一个数也不取，就能搞定0
        }
        if(arr == null || arr.length == 0) {
            return false; // 一个数也没有，搞个毛
        }
        if (arr.length == 1) { // 如果数组只有一个元素
            return arr[0] == sum;
        }
        HashSet<Integer> leftSum = new HashSet<Integer>(); // 左边所有的累加和
        HashSet<Integer> rightSum = new HashSet<Integer>(); // 右边所有的累加和
        
        int N = arr.length;
        int mid = (N >> 1);
        // 求左半部分所有累加和
        // 0 到 mid - 1  递归的定义是不包含end位置的
        process(0, mid, 0, leftSum, arr);
        // 求右半部分所有累加和
        // mid 到 N - 1
        process(mid, N, 0, rightSum, arr);
        // 左右两部分整合
        // 1)单独查看，只使用左部分，能不能搞出sum
        // 2) 单独查看，只使用右部分，能不能搞出sum
        // 3) 左+右，联合能不能搞出sum
        // 本来是要考虑上面3种情况
        // 但是因为左部分搞出所有累加和的时候，一定是包含左部分一个数也没有，这种情况的，leftsum表里，一定会有0，这时候相当于判断单独查看右部分能不能搞出sum
        // 同理，右部分搞出所有累加和的时候，一定是包含右部分一个数也没有(递归的时候每个位置都是可以要可以不要，都不要的情况就是右部分一个数也没有)，
        // 这种情况的，rightSum表里，一定会有0，这时候相当于判断单独查看左部分能不能搞出sum
        for(Integer l : leftSum) {
            if(rightSum.contains(sum - l)) {
                return true;
            }
        }
        return false;
    }
    // 0~i-1范围的累加和都存在pre中了，当前来到了i位置，i到end - 1位置的数随便选择，end
    // 是终止位置
    // 所有可能的累加和都加到ans中取
    public static void process(int i, int end, int pre, HashSet<Integer> ans, int arr[]) {
        if(i == end) { // 来到了终止位置，已经没数了，前面得到的累加和加入到ans中
            ans.add(pre);
        } else {
            // 不要i位置的数
            process(i + 1, end, pre, ans, arr);
            // 要i位置的数
            process(i + 1, end, pre + arr[i], ans, arr);
        }
    }
}
