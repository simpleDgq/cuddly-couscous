package class07;

import java.util.HashSet;

public class Code04_Power2Diffs {
    
    /*
     * 1. 给定一个有序数组arr，其中值可能为正、负、0。 返回arr中每个数都平方之后不同的结果有多少种？
     * 
     * 2. 给定一个数组arr，先递减然后递增，返回arr中有多少个绝对值不同的数字？ --> 也是同样的解法
     * 
     * 
     * 两题都是同一个解法
     */
    // 时间复杂度O(N)，额外空间复杂度O(N)
    public static int diff1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int cur : arr) {
            set.add(cur * cur);
        }
        return set.size();
    }
    
    // 首尾双指针 时间:O(N) 空间: O(1)
    public static int diff2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int L = 0;
        int R = N - 1;
        
        int leftAbs = 0;
        int rightAbs = 0;
        int ans = 0;
        while(L <= R) {
            leftAbs = Math.abs(arr[L]);
            rightAbs = Math.abs(arr[R]);
            if(leftAbs < rightAbs) { // 左边的绝对值小于右边的绝对值
                while(R >= 0 && Math.abs(arr[R]) == rightAbs) { // 如果右边绝对值一直等于Math.abs(arr[R])，一直往右撸
                    R--;
                }
            } else if(leftAbs > rightAbs) {
                while(L < N && Math.abs(arr[L]) == leftAbs) { // 如果左边绝对值一直等于Math.abs(arr[L])，一直往左撸
                    L++;
                }
            } else {
                // 相等的情况下，左右同时撸
                while(R >= 0 && Math.abs(arr[R]) == rightAbs) {
                    R--;
                }
                while(L < N && Math.abs(arr[L]) == leftAbs) {
                    L++;
                }
            }
            ans++;
        }
        return ans;
    }
}
