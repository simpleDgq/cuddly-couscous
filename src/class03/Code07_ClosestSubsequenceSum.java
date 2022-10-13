package class03;

import java.util.Iterator;
import java.util.TreeSet;

public class Code07_ClosestSubsequenceSum {
	/**
	 * 1755. 最接近目标值的子序列和 - https://leetcode.cn/problems/closest-subsequence-sum/
	 * 
	 * 给你一个整数数组 nums 和一个目标值 goal 。
	 * 你需要从 nums 中选出一个子序列，使子序列元素总和最接近 goal 。也就是说，如果子序列元素和为 sum ，你需要 最小化绝对差 abs(sum - goal) 。
	 * 返回 abs(sum - goal) 可能的 最小值 。
	 * 注意，数组的子序列是通过移除原始数组中的某些元素（可能全部或无）而形成的数组。
	 * 
	 * 数据范围:
	 * 1 <= nums.length <= 40
	 *	-10^7 <= nums[i] <= 10^7
	 *	-10^9 <= goal <= 10^9
	 */
	
	/**
	 * 把所有的可能的sum都搞出来，看最接近goal的是哪个
	 * 每一个数要和不要都有两种情况，时间复杂度是2^40次方。差不多10^12，超过10^8次方。
	 * 所以想到分治: 
	 * 1.左部分, 右部分每个数要跟不要全部展开, 2^20, 百万级别.
	 * 2. 最接近目标的，有可能是只用左侧的这20 个数搞出来的，也有可能是只用右侧的这20 个数搞出来的或者
		左侧给我一个累加和我看看右侧选哪个累加和加完之后离它最近。
	   3. 左侧搞出来了，最多100万个累加和右侧最多搞定了100万个的累加和，侧的所有累加和你都去找右侧哪个跟它加完之后最接近，
	     这个过程不就是相当于100万乘以log100万这样一个复杂度吗	
	   复杂度:  
	     O(2^20) + O(2^20) + O(2^20*log_2 2^{20})
	 */
	public static int minAbsDifference(int[] nums, int goal) {
		if (nums == null || nums.length == 0) {
			return goal;
		}
		TreeSet<Integer> lSums = new TreeSet<Integer>(); // 左边
		TreeSet<Integer> rSums = new TreeSet<Integer>(); // 右边
		
		int N = nums.length;
		int mid = nums.length >> 1;
		// 收集数组左半部分，所有可能的累加和
		process(nums, 0, mid, 0, lSums); // 这里是nums.length >> 1，没搞明白
		// 收集数组右半部分，所有可能的累加和
		process(nums, mid + 1, N - 1, 0, rSums);
		
		// 因为左右部分的累加和中肯定包含了0的情况，也就包括了只取右部分或左部分的数组成累加和的情况了
		Iterator<Integer> iterator = lSums.iterator();
		int ans = Integer.MAX_VALUE;
		while (iterator.hasNext()) {// 左边任意一个数，去右边匹配一个最接近goal - l的数
			int rest = goal - iterator.next();
			
			// 使用有序表的floor和ceiling，快速找到大于等于rest最小的数和小于等于rest最大的数，然后来看这两个数哪个和左部分累加和组合能让abs(sum - goal)最小（时间复杂度是LogN）
            Integer r1 = rSums.floor(rest);
            Integer r2 = rSums.ceiling(rest);
            
            if (r1 != null) {
            	ans = Math.min(ans, Math.abs(rest - r1));
            }
 
            if (r2 != null) {
            	ans = Math.min(ans, Math.abs(rest - r2));
            }
		}
		return ans;
	}
	
	// nums[0..index-1]已经选了一些数字，组成了累加和sum
	// 当前来到nums[index....end)这个范围，所有可能的累加和.
	// 填到arr中去
	public static void process(int[] nums, int index, int end, int sum, TreeSet<Integer> arr) {
		if (index == end + 1) {  // 没有数了。将找到的当前情况的累加和加入到set中
			arr.add(sum);
		} else {
			process(nums, index + 1, end, sum + nums[index], arr);
			process(nums, index + 1, end, sum, arr);
		}
	}
}
