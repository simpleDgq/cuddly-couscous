package class01;

import java.util.HashMap;

public class Code07_TargetSum {
	/**
	 * 494. 数组中添加加减运算符得到指定值的所有方法 [M]
	 * 给定一个数组arr，你可以在每个数字之前决定+或者-, 但是必须所有数字都参与, 再给定一个数target，请问最后算出target的方法数是多少？
	 */
	
	// 暴力递归
	public int findTargetSumWays1(int[] nums, int target) {
		if(nums == null || nums.length == 0) {
			return 0;
		}
		return process1(nums, 0, target);
	}
	/*
	 * 当前来到index位置，这个位置的数可以加+号，也可以加-号，
	 * 你给我返回有多少种方法凑出rest这个数字
	 */
	public static int process1(int arr[], int index, int rest) {
		if(index == arr.length) {
			return rest == 0 ? 1 : 0; // 用完了所有的元素，如果rest是0，则是一种方法，否则凑不出rest，返回0
		}
		// 可以加+ 也可以加-，所有方法数累加
		return process1(arr, index + 1, rest - arr[index]) + process1(arr, index + 1, rest + arr[index]);
	}
	
	// 傻缓存
	public int findTargetSumWays2(int[] nums, int target) {
		if(nums == null || nums.length == 0) {
			return 0;
		}
		return process2(nums, 0, target, new HashMap<Integer, HashMap<Integer, Integer>>());
	}

	public static int process2(int arr[], int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
		if(dp.containsKey(index) && dp.get(index).containsKey(rest)) {
			return dp.get(index).get(rest);
		}
		int ans = 0;
		if(index == arr.length) {
			ans = rest == 0 ? 1 : 0;
		} else {
			ans = process2(arr, index + 1, rest - arr[index], dp) + process2(arr, index + 1, rest + arr[index], dp);
		}
		if(!dp.containsKey(index)) {
			dp.put(index, new HashMap<Integer, Integer>());
		}
		dp.get(index).put(rest, ans);
		return ans;
	}
	
	// 优化点一 :
	// 你可以认为arr中都是非负数
	// 因为即便是arr中有负数，比如[3,-4,2]
	// 因为你能在每个数前面用+或者-号
	// 所以[3,-4,2]其实和[3,4,2]达成一样的效果
	// 那么我们就全把arr变成非负数，不会影响结果的
	
	// 优化点二 :
	// 如果arr都是非负数，并且所有数的累加和是sum
	// 那么如果target<sum，很明显没有任何方法可以达到target，可以直接返回0
	
	// 优化点三 :
	// arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
	// 所以，如果所有数的累加和是sum，
	// 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
	
	// 优化点四 :
	// 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
	// 其中一个方案是 : +1 -2 +3 -4 +5 = 3
	// 该方案中取了正的集合为P = {1，3，5}
	// 该方案中取了负的集合为N = {2，4}
	// 所以任何一种方案，都一定有 sum(P) - sum(N) = target
	// 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
	// sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
	// 2 * sum(P) = target + 数组所有数的累加和
	// sum(P) = (target + 数组所有数的累加和) / 2
	// 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
	// 那么就一定对应一种target的方式
	// 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
	// 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
	
	// 优化点五 :
	// 二维动态规划的空间压缩技巧
	
	/*
	 * 
	 * 暴力递归
	 *	index: 0 ~ N
	 *	rest: 0 ~ sum
	 *	dp[N + 1][sum + 1]
	 */
	public static int process(int arr[], int index, int rest) {
		if(index == arr.length) {
			return rest == 0 ? 1 : 0;
		}
		int p1 = process(arr, index + 1, rest);
		int p2 = 0;
		if(rest - arr[index] >= 0) {
			p2 = process(arr, index + 1, rest - arr[index]);
		}
		return p1 + p2;
	}
	
	/*
	 * dp[i][j]  0~i位置的数自由选择，能够搞出j出来的方法数
	 * 	 
	 */
	public static int findTargetSumWays3(int[] nums, int target) {
		if(nums == null || nums.length == 0) {
			return 0;
		}
		int sum = 0;
		for(int i : nums) {
			sum += i;
		}
		return (sum < target || ((sum & 1) ^ (target & 1)) != 0) ? 0 : subset(nums, (target + sum) >> 1);
	}
	/**
	 * 动态规划
	 */
	public static int subset(int nums[], int sum) {
		if(sum < 0) {
			return 0;
		}
		int N = nums.length;
		int M = sum + 1;
		int[][] dp = new int[N + 1][M];
		dp[N][0] = 1;
		
		// 从下往上，从左往右
		for(int i = N - 1; i >= 0; i--) {
			for(int j = 0; j <= M - 1; j++) {
				int p1 = dp[i + 1][j];
				int p2 = 0;
				if(j - nums[i] >= 0) {
					p2 = dp[i + 1][j - nums[i]];
				}
				dp[i][j] = p1 + p2;
			}
		}
		return dp[0][sum];
	}
	
	/**
	 * 空间压缩
	 * 每一行只依赖它下面的一行，所以可以用一维数组代替二维数组
	 * 
	 * // 核心就是for循环里面的：for (int i = s; i >= n; i--) {
	// 为啥不枚举所有可能的累加和？只枚举 n...s 这些累加和？
	// 因为如果 i - n < 0，dp[i]怎么更新？和上一步的dp[i]一样！所以不用更新
	// 如果 i - n >= 0，dp[i]怎么更新？上一步的dp[i] + 上一步dp[i - n]的值，这才需要更新
	 */
	public static int subset2(int nums[], int sum) {
		if(sum < 0) {
			return 0;
		}
		int M = sum + 1;
		int[] dp = new int[M];
		dp[0] = 1;
		
		for(int n : nums) {
			for(int i = sum; i >= n; i--) {
				dp[i] += dp[i - n];
			}
		}
		return dp[sum];
	}
}
