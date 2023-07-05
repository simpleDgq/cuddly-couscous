package class30;

// 88. 合并两个有序数组
public class Problem_0088_MergeSortedArray {
    /**
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，
     * 其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     * 
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -109 <= nums1[i], nums2[j] <= 109
     */
    
    /**
     * 思路: 从右往左，两个指针分变指向最后一个有效元素，谁大就先拷贝谁到长数组里面的最后
     * 相等的时候，先拷贝长数组的元素到后面，--> 提前释放更多的空间 (实际上相等的时候，拷贝谁都一样，leetcode都能过)
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = nums1.length - 1;
        while(m > 0 && n > 0) {
            // 谁大拷贝谁，相等先拷贝长数组的元素
            if(nums1[m - 1] >= nums2[n - 1]) {
                nums1[index--] = nums1[--m];
            } else {
                nums1[index--] = nums2[--n];
            }
        }
        // 谁还剩下，将剩下的元素直接拷贝到nums1
        while(m > 0) {
            nums1[index--] = nums1[--m];
        }
        while(n > 0) {
            nums1[index--] = nums2[--n];
        }
    }
}
