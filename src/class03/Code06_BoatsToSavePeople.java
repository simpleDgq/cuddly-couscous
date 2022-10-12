package class03;

import java.util.Arrays;

public class Code06_BoatsToSavePeople {
	/**
	 * // 给定一个正数数组arr，代表若干人的体重
		// 再给定一个正数limit，表示所有船共同拥有的载重量
		// 每艘船最多坐两人，且不能超过载重
		// 想让所有的人同时过河，并且用最好的分配方法让船尽量少
		// 返回最少的船数
		// 测试链接 : https://leetcode.cn/problems/boats-to-save-people/
	 */
	
	/*
	 * 首尾指针解法
	 * 1. 首先将数组排序
	 * 2. L 指向0位置，R指向最后的位置，看加起来是否超过limit，如果超过，r--, 最大的数需要单独运送一次，加入ans，每次都想要拿一个最小的去消掉一个最大的
	 *    如果不超过，两个人运输一次，加入ans，l++，r--
	 */
	public int numRescueBoats(int[] people, int limit) {
		if(people == null || people.length == 0 || limit <= 0) {
			return 0;
		}
		// N*logN
		Arrays.sort(people);
		int l = 0;
		int r = people.length - 1;
		int ans = 0;
		int sum = 0;
		while(l <= r) {
			sum = l == r ? people[l] : people[l] + people[r];
			if(sum > limit) {
				r--;
			} else {
				l++;
				r--;
			}
			ans++;
		}
		return ans;
	}
	
	/*
	 * 指针从limit/2的位置往两边跑的解法
	 * 优化了常数时间(LeetCode跑的时间和上面的解法差不多) ==> 代码难写，直接跳过，用上面的方法
	 */
	public static int numRescueBoats1(int[] arr, int limit) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		Arrays.sort(arr);
		if (arr[N - 1] > limit) {
			return -1;
		}
		int lessR = -1;
		for (int i = N - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return N;
		}
		int L = lessR;
		int R = lessR + 1;
		int noUsed = 0;
		while (L >= 0) {
			int solved = 0;
			while (R < N && arr[L] + arr[R] <= limit) {
				R++;
				solved++;
			}
			if (solved == 0) {
				noUsed++;
				L--;
			} else {
				L = Math.max(-1, L - solved);
			}
		}
		int all = lessR + 1;
		int used = all - noUsed;
		int moreUnsolved = (N - all) - used;
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}
}
