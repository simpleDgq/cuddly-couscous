package class16;

import java.util.Arrays;

public class Code02_SmallestUnformedSum {
    /**
     * 
     * 数组的子集不能累加出的最小正数
     * 
     * 给定一个正数数组arr，
     *   返回arr的子集不能累加出的最小正数
     *   1）正常怎么做？
     *   2）如果arr中肯定有1这个值，怎么做？
     * 
     * 原题: arr是正数, 要求子集不能是空集, 最小子集累加和到最大子集累加和的范围中哪个数是最小不可组成的 --> 代码里面记录一个最小值，
     * 最后查的时候就不是从1开始了，而是从min开始。
     */
    
    /**
     * 第1问:
     * 思路: 同上一题
     *   arr所有值的累加和从一个负数到一个整数做出一张表, 然后看最后一行
     *  arr 0~N-1所有的值能不能搞定1, 2, 3..., 哪一个最早不行的, 返回就是答案
     *  
     *  这题更加简单，它都是正数。
     *  
     *  dp[i][j]: 0到i的数，能不能搞出累加和j
     *  可能性1: 不用i位置的数，dp[i - 1][j]
     *  可能性2: 用i位置的数，dp[i - 1][j - arr[i]]
     */
    public static int unformedSum(int arr[]) {
        if(arr == null || arr.length == 0) {
            return 1;
        }
        int N = arr.length;
        // 正数累加
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i <= N - 1; i++) {
            sum += arr[i];
            min = Math.min(min, arr[i]);
        }
        boolean dp[][] = new boolean[N][sum + 1];
        // 第0行
        dp[0][0] = true; // 0位置的数能不能搞定0？ 能，一个数也不选
        dp[0][arr[0]] = true; //0位置的数是arr[0], 一定能搞定arr[0]
        // 每个位置只依赖它上一行的位置，所以从上往下，从左往右一行一行填
        for(int i = 1; i <= N - 1; i++) {
            for(int j = 0; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if(j - arr[i] >= 0) {
                    dp[i][j] |=  dp[i - 1][j - arr[i]];
                }
            }
        }
        // 遍历最后一行，看不能搞定的是哪个正数
//        for (int j = 1; j <= sum; j++) { // 解决本题
        for (int j = min; j <= sum; j++) { // 解决原题
            if(!dp[N - 1][j]) {
                return j;
            }
        }
        // 如果最后一行没有退出，说明1~sum都能搞定，那么不能搞定的第一个正数就是sum+1
        return sum + 1;
    }
    
    /**
     * 第2问: 如果arr中肯定有1这个值，怎么做？
     * 1)先把array排序, 正数数组排完序, 左边0位置肯定是1
     * 2) 定义变量range =1, 表示从1~1范围上的正数都能累加出来
     *   range=k, 代表1~k上的所有正数都能搞出来
     * 3) 当arr 0位置是1的情况下, range=1, 代表1~1范围的正数都可以搞出来
     *   如果1位置也是1, range变成2, 代表1~2范围的正数都可以搞出来
     * 4) 如果2位置也是2, range变成4, 代表1~4范围的正数都可以搞出来
     * 5) 如果0~i-1能搞定的数是1~100, 此时i位置是101, 那么能搞定的是1~201, 直接加
     * 6) 注意一点: 如果0~i-1能搞定的数是1~100, 此时i位置是102, 那么101不可以搞定 --> 就是答案
     * 
     * 抽象化:
     * 如果0~i-1搞定1~a, 如果i位置上是b:
     * 1)如果b<= a+1, 能扩充, 范围到1~a+b
     * 2)如果b> a+1, a+1就是搞定不了的最小正整数
     */
    public static int unformedSum2(int arr[]) {
        if(arr == null || arr.length == 0) {
            return 1;
        }
        int N = arr.length;
        Arrays.sort(arr); // O(N*logN)
        
        if(arr[0] != 1) {
            return 1;
        }
        int range = 1; // 1~1一定能搞定
        for(int i = 1; i <= N - 1; i++) {
            if(arr[i] <= range + 1) {
                range += arr[i];
            } else {
                return range + 1;
            }
        }
        // 如果前面没有return, 说明1~range都能搞定，返回range + 1就是答案
        return range + 1;
    }
}
