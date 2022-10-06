package class02;

public class Code02_MinLengthForSort {
	
	/**
	 * 本题测试链接 : https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
	 * 
	 * 无序数组需要排序的最短子数组长度 - 时间复杂度 O(N)额外空间复杂度O(1)
	 * 
	 * 思路:
	 * 1）先从左往右遍历,
	 *		max左: 划过部分的最大值
	 *		左Max>当前数字: 打叉， 证明这个位置元素处在一个不正确的位置(因为它前面有个比它大的数)
	 *		左Max<=当前数字: 打勾
	 * 记录最右打叉的位置
	 * 2）然后从右往左遍历,
	 *		min右: 划过部分的最小值
	 *		右Min<当前数字: 打叉
	 *		右Min>=当前数字: 打勾
	 * 记录最左打叉的位置
	 * 
	 * 两个打叉的位置，表示的范围就是需要排序的数组。
	 */
	public static int findUnsortedSubarray(int nums[]) {
		if(nums == null || nums.length < 2) { // 数组长度是1，不需要排序
			return 0;
		}
		
		int leftMax = nums[0];
		int rightPoint = 0;
		for(int i = 1; i <= nums.length - 1; i++) {
			if(leftMax > nums[i]) {
				rightPoint = i;
			} else {
				leftMax = nums[i];
			}
		}
		
		int rightMin = nums[nums.length - 1];
		int leftPoint = nums.length - 1;
		for(int i = nums.length - 2; i >= 0; i--) {
			if(rightMin < nums[i]) {
				leftPoint = i;
			} else {
				rightMin = nums[i];
			}
		}
		
		return Math.max(0, rightPoint - leftPoint + 1); // 本来就有序的数组，rightPoint - leftPoint + 1会得到负数，本来不需要排序，答案是0，所以和0比较，取最大值
	}

}
