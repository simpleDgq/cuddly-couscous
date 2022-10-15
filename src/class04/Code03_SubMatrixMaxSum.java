package class04;

public class Code03_SubMatrixMaxSum {
    /*
     * 子矩阵最大累加和
     * 
     * leetcode这题: 加深了难度，需要返回左上角和右下角的坐标
     * https://leetcode-cn.com/problems/max-submatrix-lcci/
     * 
     * 矩形必须包含第0行数据, 且只包含第0行的情况下, 最大累加和是多少?
        矩形必须包含0,1两行数据, 且只包含0,1两行的情况下, 最大累加和是多少?
        矩形必须包含0,1,2三行数据, 且只包含0,1,2三行的情况下, 最大累加和是多少?
        矩形必须包含0,1,2,3四行数据, 且只包含0,1,2,3四行的情况下, 最大累加和是多少?
        ....
        然后
        1行~1行
        1行~2行
        1行~3行
        1行~4行
        ...
        如果我们能够每一个都求出来答案一定在其中
        
        ===
        0行
        必须包含第0行数据，且只有第0行情况下画框, 最大累加和多少?
        就是子数组的最大累加和问题
        
        矩形必须包含0, 1两行数据, 且只包含0, 1两行的情况下, 最大累加和是多少?
        两行上下数据压在一起,形成一个新数组
        对这个数组求最大累加和就代表必须包含0, 1两行数据，且只包含01两行数据画框的最好答案是啥
        
        ==> 压缩数组，转变成了求子数组累加和最大的问题
     */
    public static int subMatrixMaxSum(int matrix[][]) {
        if(matrix == null || matrix.length == 0 || 
                matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int sum[] = new int[M];
        // 0 - 0 行， 0-1行。。。。0-N-1行
        // 1- 1行，1-2行，。。。。1-N-1行
        //...
        int ans = Integer.MIN_VALUE;
        for(int i = 0; i <= N - 1; i++) {
            for(int j = i; j <= N - 1; j++) {
                for(int k = 0; k <= M - 1; k++) {
                    sum[k] += matrix[j][k]; // 每次将下面的一行数据，累加到上面一行
                }
                ans = Math.max(maxSubArray(sum), ans); // 然后再累加和数组中求，累加和最大值
            } 
        }
        return ans;
    }
    
    /*
     * 求子数组最大累加和
     */
    public static int maxSubArray(int[] nums) {
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
    
    
    // 求左上角和右下角的下标
    // 当发现更大的答案时，抓一下  ==> 下面的解法不对
    public static int[] getMaxMatrix(int matrix[][]) {
        if(matrix == null || matrix.length == 0 || 
                matrix[0] == null || matrix[0].length == 0) {
            return new int[]{-1, -1, -1, -1};
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int sum[] = new int[M];
        // 0 - 0 行， 0-1行。。。。0-N-1行
        // 1- 1行，1-2行，。。。。1-N-1行
        //...
        int ans = Integer.MIN_VALUE;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for(int i = 0; i <= N - 1; i++) {
            for(int j = i; j <= N - 1; j++) {
                for(int k = 0; k <= M - 1; k++) {
                    sum[k] += matrix[j][k]; // 每次将下面的一行数据，累加到上面一行
                }
                
                int ans2[] = subArrMaxSum2(sum); // 返回最大值，以及取得最大值的begin和end下标
                if(ans2[0] > ans) {
                    a = i;
                    c = j;
                    b = ans2[1]; // begin
                    d = ans2[2]; // end
                }
                ans = Math.max(ans2[0], ans); // 然后再累加和数组中求，累加和最大值
            } 
        }
        return new int[]{a, b, c, d};
    }
    
    /*
     * 求子数组最大累加和, 返回最大值和边界
     */
    public static int[] subArrMaxSum2(int nums[]) {
        int N = nums.length;
        int dp[] = new int[N];
        dp[0] = nums[0]; // 必须以0位置结尾的情况下，最大值，只有0位置，不能向左扩
        
        int begin = 0;
        int end = 0;
        
        int max = dp[0]; // max从第一个元素开始
        for(int i = 1; i <= N - 1; i++) {
            int p1 = dp[i-1] + nums[i];
            int p2 = nums[i];
            dp[i] = Math.max(p1, p2);
            
            if(dp[i] == p2) { // 累加和最大的时候，只有i这一个位置，范围就是i~i
                begin = i;
                end = i;
            }
            if(dp[i] > max) { // i位置累加和取得了更大的值，end更新
                end = i; 
            }
            
            max = Math.max(dp[i], max);
        }
        return new int[] {max, begin, end};
    }
    
    
    public static void main(String[] args) {
       int matrix[][] =  new int[][]{{9,-8,1,3,-2}
                                    ,{-3,7,6,-2,4}
                                    ,{6,-4,-4,8,-7}};
                                    
       int ans[] = getMaxMatrix(matrix);
       
    }
}
