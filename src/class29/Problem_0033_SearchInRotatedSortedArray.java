package class29;

// 33. 搜索旋转排序数组
public class Problem_0033_SearchInRotatedSortedArray {
    /**
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
     * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ...,
     * nums[k-1]]（下标 从 0 开始 计数）。
     * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
     */

    /**
     * 思路: 二分法
     * logN的时间复杂度，第一想到的肯定是二分。
     * 只不过这题的二分范围的确定，比较特殊。
     */

    public int search(int[] nums, int target) {
        /**
         * 分段讨论
         * 
         * 当数组从中点分开成为两部分的时候，一定有一部分是有序的。
         * 怎么判断哪部分是有序的？
         * 看当前区间的最左边的元素L和中间mid元素的大小，如果nums[L] <= nums[mid], 那么左半部分有序
         * 否则右半部分有序.
         * 在有序部分找的时候，看target是否在有序的部分范围内，在的话就在里面找，不在的话跳到另一部分找.
         * 
         * 官方题解是用的nums[0] <= nums[mid]判断哪部分有序：
         * 很多人在讨论为什么判断是否有序的时候，用nums[0] <= nums[mid]可以？
         * 主要是因为，比较的目的是为了判断哪部分数组是有序的，如果nums[0] <= nums[mid]， 题目给出了数组是按升序排列的，
         * 那么0到mid这一段一定是有序而且是升序的，L一定是在0和mid间的某个位置上(因为mid是L和R的中点，L一定在mid的左边)
         * 所以从nums[0] <= nums[mid] 一定可以推出 nums[L] <= nums[mid]
         * 如果如果nums[0] > nums[mid]，那就是在mid之前一定出现过反转点（当然这个反转点不一定在本次二分查找区间内），
         * 那nums[mid]之后一定就是有序的。
         * 
         * 6 7 0 1 2 4 5 ，中点是1， 左边不是有序，右边有序
         */
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return target == nums[0] ? 0 : -1;
        }
        int N = nums.length;
        int L = 0;
        int R = N - 1;

        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            // 中点元素是，直接返回
            if (nums[mid] == target) {
                return mid;
            }
            // 看哪部分是有序的
            if (nums[L] <= nums[mid]) { // 第L个元素比中点元素小，左半部分有序
                // 如果target是在左半部分的范围之内，那么就去这部分找，否则去另外部分找
                if (target >= nums[L] && target < nums[mid]) {
                    R = mid - 1; // 去左半有序的部分找
                } else {
                    L = mid + 1;// 去右半有序的部分找
                }
            } else { // 右半部分有序
                // 如果target在右半部分的范围之类
                if (target > nums[mid] && target <= nums[R]) {
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }
        }
        return -1;
    }

}
