package class32;

// https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array
// 153. 寻找旋转排序数组中的最小值
public class Problem_0153_FindMin {
    /**
     * 思路：思路：看哪部分有序
     * nums[L] 和 nums[mid]比较，如果小于，则说明左半部分有序，
     * nums[L]就是左半部分的最小值，记录下来；然后去右半部分找
     * 如果nums[L] > nums[mid] 说明右半部分有序，记录下来，然后去左半部分找
     */
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }
        int N = nums.length;
        int L = 0;
        int R = N - 1;
        int ans = Integer.MAX_VALUE;

        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            // 看哪部分是有序的
            if (nums[L] <= nums[mid]) {
                ans = Math.min(ans, nums[L]);
                L = mid + 1;
            } else {
                ans = Math.min(ans, nums[mid]);
                R = mid - 1;
            }
        }
        return ans;
    }
}
