package class32;

// 189. 轮转数组
public class Problem_0189_RotateArray {
    /**
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * 示例 1:
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     */

    /**
     * 思路:
     * 天火三旋变
     * 
     * 旋转3次
     * 1.N - K ~ N - 1位置，数组旋转(颠倒过来)    翻转后K个
     * 2.0 ~ N - k - 1位置，数组旋转(颠倒过来)    翻转前面的N-K个
     * 3.整个数组旋转
     * 
     * 例子:
     * nums = [1,2,3,4,5,6,7], N = 7, k = 3
     * 4 ~ 6位置旋转: [4,3,2,1,7,6,5]
     * 0 ~ 3位置旋转: [4,3,2,1,5,6,7]
     * 整体旋转: [5,6,7,1,2,3,4]
     * 
     * 当数组长度没有k大的时候，例如[1,2,3] k = 4
     * 向右轮转 1 步: [3, 1, 2]
     * 向右轮转 2 步: [2, 3, 1]
     * 向右轮转 3 步: [1, 2, 3]
     * 向右轮转 4 步: [3, 1, 2]
     * => 当k比N大的时候，只需要轮转k % N轮就行
     * 所以代码里面写了k %= N; // 有可能数组长度还没有K大，比如数组长度是3，k是4.
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int N = nums.length;
        k %= N; // 有可能数组长度还没有K大，比如数组长度是3，k是4.
        reverse(nums, N - k, N - 1);
        reverse(nums, 0, N - k - 1);
        reverse(nums, 0, nums.length - 1);
    }

    // L到R范围上，翻转
    public void reverse(int nums[], int L, int R) {
        while (L < R) {
            int temp = nums[L];
            nums[L] = nums[R];
            nums[R] = temp;
            L++;
            R--;
        }
    }
}
