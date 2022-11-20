package class14;

import java.util.TreeSet;

public class Code02_MaxSubArraySumLessOrEqualK {
   // 小于等于K的最大子数组累加和
  /**
   * 请返回arr中，求个子数组的累加和，是<=K的，并且是最大的。
   *  返回这个最大的累加和
   */
    
    /**
     * 思路:
     * 子数组问题，求以i位置结尾的子数组的答案，所有的位置的答案的最大值，就是最终的答案
     * 
     * 如果知道了i位置结尾的数组的累加和是sum，
     * 来到i位置， 求以它结尾的子数组的累加和是<=k的，并且是最大的，其实就是求前面累加和是>=sum-k的，并且是最接近的
     * sum 减去前面的这部分就是答案
     * 
     * 例子:
     * 假设0~57整体累加和1000, 求<=300的累加和怎么最大
     * 其实就是求之前有哪个前缀和是>=700且最接近.
     */
    public static int getMaxLessOrEqualK(int[] arr, int K) {
       TreeSet<Integer> set = new TreeSet<Integer>();// 记录i之前的，前缀和，按照有序表组织
       set.add(0); // 一个数也没有的时候，就已经有一个前缀和是0了
       int sum = 0;
       int ans = Integer.MIN_VALUE;
       // 每一步的i，都求子数组必须以i结尾的情况下，求个子数组的累加和，是<=K的，并且是最大的
       for(int i = 0; i <= arr.length - 1; i++) {
           sum += arr[i];
           // 每一个i位置，都去求它前面最接近sum - k的累加和. 然后用sum - 它前面最接近sum - k的累加和 就是答案
           if(set.ceiling(sum - K) != null) { // 存在才去求
               ans = Math.max(ans, sum - set.ceiling(sum - K));
           }
           set.add(sum);
       }
       return ans;
    }
}
