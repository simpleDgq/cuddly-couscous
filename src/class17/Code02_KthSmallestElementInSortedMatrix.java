package class17;

// https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/
public class Code02_KthSmallestElementInSortedMatrix {
    /**
     * 给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     *   请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
     *   你必须找到一个内存复杂度优于 O(n2) 的解决方案。
     */
    
    /**
     * 1. 先解决这个问题: 给定一个数num，求矩阵中<=num的数有多少个，同时求出这些小于等于num的数中，最接近num的是谁？
     * 搞两个变量: 分别记录矩阵中<=num的数有多少个， 最接近num的是谁
     * 也是从右上角出发，如果当前数是小于等于num的，那么它左边的所有的数包含它自己都是小于等于num的，累加计数
     * 同时当前数和已经记录的最接近num的数，求最大值，最大的就是当前最接近num的; 搜集完之后，往下走，继续去找
     * 如果当前数是大于num的，则需要去左边继续找。
     * 
     * 2. 解决原问题 --> 二分
     *   整个数组中最小的是谁？左上角的数, 假设是1
     *   那整个数组中，最大的数是谁？右下角的数，假设是1000
     *   第100小的数一定在1到1000之间, 看看<=500的数有几个?
     *   
     *   
     *   1) 1~1000二分，中点位置500. 如果矩阵中<=500有200个, 说明目标定大了，要去中点位置的左边继续找，因为我们要求的是第100小的数
     *   2) 继续在1~500之间，二分, 取中点的数250, 看矩阵中<=250的数有多少个，如果有79个，说明我们的目标定小了，要去中点位置的右边继续找
     *   3) 继续在250~500之间，二分, 取中点的数375, 看矩阵中小于等于375的数有多少个
     *   这样一直二分下去一定能够找到第100小的数。
     *   
     *   4) 有可能最后得到<=785的数有100个, 但是数组中没有这个数, 应该取<=785并离它最近
     *   的数。
     *   所以我每次让你过的时候求俩信息，
     *   第一小于等于某一个值个数有几个，
     *   第二，最接近它的是谁？
     *   
     *   
     *   时间复杂度: 每次都拿一个数去查，都要从矩阵的右上角开始走起，这个时间复杂度是O(n + m)
     *   查几次: 最小值到最大值二分几次就是查几次 log(max - min)
     *   所以总的复杂度: O(n + m) * log(max - min)
     */
     public int kthSmallest(int[][] matrix, int k) {
         int N = matrix.length;
         int M = matrix[0].length;
         
         // 矩阵的最大最小值
         int left = matrix[0][0];
         int right = matrix[N - 1][M - 1];
         int ans = 0;
         while(left <= right) {
             int mid = left + ((right - left) >> 1);
             // 求矩阵中小于等于mid的数有多少个，而且求出矩阵中真实存在的，最接近mid的是谁
             Info info = nearMore(mid, matrix);
             /*   ========
             *   1 ~ 1000  第100小的数:
             *   
             *   如果矩阵中小于等于500的数只有17个，其中最接近500的数，肯定不是答案，需要在大于500的数中继续找。500到1000，继续二分
             *   如果矩阵中小于等于500的数有220个，说明答案在0~500中，其中最接近500的数是490，
             *   那么需要记录一下490, 这个490有可能就是答案，0到500上，继续二分， 看小于等于250的数有多少个，最接近250的数是不是答案
             *   也就是看看能不能继续找到更小的答案。
             */
             // 如果目标定小了，则继续去右边找
             if(info.count < k) { // 如果是小于规定的k的，最接近mid的值，一定不是答案,继续去右边找
                 left = mid + 1;
             } else {  // 如果大于等于规定的k的，最接近mid的值可能是答案，记一下，继续去左边找，有没有更小的答案
                 right = mid - 1;
                 ans = info.near;
             }
         }
         return ans;
     }
     public class Info {
         int near; // 最接近num的数
         int count; // 小于num的数，最接近它的是谁 
         public Info(int near, int count) {
           this.near = near;
           this.count = count;
        }
     }
     // 求出小于等于num的数有多少个，其中最接近num的是谁
     public Info nearMore(int num, int matrix[][]) {
        int N = matrix.length;
        int M = matrix[0].length;
        int row = 0;
        int col = M - 1;
        
        int count = 0;
        int near = Integer.MIN_VALUE;
        int cur = 0;
        while(row <= N - 1 && col >= 0) {
           cur = matrix[row][col];
           if(cur <= num) {
               count += col + 1;
               near = Math.max(near, cur);
               row++;
           } else {
               col--;
           }
        }
        return new Info(near, count);
     }

}
