package class23;

public class Code01_MaxABSBetweenLeftAndRight {
    /**
     * 给定一个数组arr长度为N(N>1)，你可以把任意长度大于0且小于N的前缀作为左部分，剩下的 作为右部分。
    *  但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的， 左部分最大值减去右部分最大值的绝对值。
    */
    
    /**
     * 思路:
     * 1)暴力方法
     * 每一种划分，分别求左部分和右部分的max值相减，取最大值就是结果.
     * 时间复杂度:O(N^2).划分方法N-1, 每一种划分都要遍历左右两部分求最大值，所以是O(N^2)
     * 2)辅助数组
     * left[i]数组: 记录0~i范围上的最大值。 --> 生成方法: 当前数跟前一个位置的数, 谁大拷贝谁
     * right[i]数组: 从右往左生成,i+1~N-1范围上的最大值
     * 
     * 有了两个辅助数组, 每一种划分情况取求左右部分max值的时候，直接从辅助数组取。
     *   0~i范围上的最大值直接从left数组里取, i+1~N-1范围上的最大值直接从right数组里拿
     *   把O(N^2)的暴力解优化成O(N)
     *   
     * 3) arr 遍历一遍找到全局最大值, 然后0位置数跟N-1位置数, 谁小减谁就是答案
     * 
     * 
     * 找到的max可能性罗列:
     * 1) 全局max被划分到了左部分
     *    max - 右边的最大值就是答案，让右max尽量小，
     *    右部分一定要有数, 一定会包含N-1位置的数
     *    右部分只包含最右边一个数是右边max最小的时候。
     *    
     * 理解:假设最大值划分到了左部分，那么就是要在右部分中找一个最小的最大值，这样才能使的这两个数的差的绝对值最大;
     *   不管怎么切，右部分一定是包含arr[length-1]的，如果切的时候，右部分只有数组最后一个元素，那么这种情况下，答案就是max-arr[length-1]
     *   切入点继续往左移动，包含arr[length-2]，只会使右部分的最大值变大
     *   1.切入点往左移动，如果arr[length-1] > arr[length-2], 那么右部分的足最大值还是arr[length-1]，最终的答案不变, 还是max-arr[length-1]；
     *   2.如果arr[length-1]<arr[length-2], 最大值变大了，使得左部分max-右部分max值arr[length-2]，变小了，这种切法不可取
     *   所以还是只将数组的最后一个数当成右部分是最好的答案
     *    
     * 2) 全局max被划分到了右部分
     * 同理: 左部分只包含最右边一个数是左边max最小的时候。
     */
    public static int maxABS2(int[] arr) {
        int[] lArr = new int[arr.length];
        int[] rArr = new int[arr.length];
        lArr[0] = arr[0];
        rArr[arr.length - 1] = arr[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            lArr[i] = Math.max(lArr[i - 1], arr[i]);
        }
        for (int i = arr.length - 2; i > -1; i--) {
            rArr[i] = Math.max(rArr[i + 1], arr[i]);
        }
        int max = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            max = Math.max(max, Math.abs(lArr[i] - rArr[i + 1]));
        }
        return max;
    }
    
    // 最优解
    public static int maxABS3(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        return max - Math.min(arr[0], arr[arr.length - 1]);
    }

}
