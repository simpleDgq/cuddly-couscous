package class28;

// https://leetcode.cn/problems/next-permutation
// 31. 下一个排列
public class Problem_0031_NextPermutation {
    /**
     * 注意到下一个排列总是比当前排列要大，除非该排列已经是最大的排列。我们希望找到一种方法，
     * 能够找到一个大于当前序列的新序列，且变大的幅度尽可能小。具体地：
     * 我们需要将一个左边的「较小数」与一个右边的「较大数」交换，以能够让当前排列变大，从而得到下一个排列。
     * 同时我们要让这个「较小数」尽量靠右，而「较大数」尽可能小。当交换完成后，「较大数」右边的数需要按照
     * 升序重新排列。这样可以在保证新排列大于原来排列的情况下，使变大的幅度尽可能小。
     */

    /**
     * 步骤：
     * 第一步，倒序遍历查找到第一个降序的元素的位置
     * 第二步，第二次倒序遍历找到第一个大于降序元素的元素的位置，两两交换
     * 第三步，翻转第一个降序元素之后的所有元素
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int N = nums.length;
        int firstLess = -1;
        for (int i = N - 2; i >= 0; i--) {// 从右往左找到第一个降序的位置
            if (nums[i] < nums[i + 1]) {
                firstLess = i;
                break;
            }
        }
        // 如果firstLess是-1，说明没有降序的位置，这个排列从左往右是升序的，也就是最大的排列
        if (firstLess == -1) {
            // 根据题意需要翻转，得到最小排列
            reverse(nums, 0, N - 1);
        } else {
            int rightClosetMore = -1;
            // 找最靠右的、同时比nums[firstLess]大的数，位置在哪
            // 这里其实也可以用二分优化，但是这种优化无关紧要了
            for (int i = N - 1; i >= 0; i--) {
                if (nums[i] > nums[firstLess]) {
                    rightClosetMore = i;
                    break;
                }
            }
            // 交换
            swap(nums, firstLess, rightClosetMore);
            // 翻转
            reverse(nums, firstLess + 1, N - 1);
        }
    }

    // L...R范围上进行翻转
    public void reverse(int nums[], int L, int R) {
        while (L < R) {
            swap(nums, L, R);
            L++;
            R--;
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
