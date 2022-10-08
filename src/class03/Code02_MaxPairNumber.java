package class03;

import java.util.Arrays;

public class Code02_MaxPairNumber {
	/**
	// 给定一个数组arr，代表每个人的能力值。再给定一个非负数k。
	// 如果两个人能力差值正好为k，那么可以凑在一起比赛，一局比赛只有两个人
	// 返回最多可以同时有多少场比赛
	 * 
	 * 思路:
	 * 贪心加窗口
	 * 
	 * 
	 * 1. 先将数组排序
	 * 2. 贪心： 每次卡着小的能力去配对,就不会错过最多的配对数。 对数器和暴力解去验证
	 * 
	 * 流程:
	 * 例子:
	 * [1,1,3,3,5,7]
	 * 1. 先把所有东西排序, 用窗口, 一开始L, R都在1位置,L这个人跟R这个人能不能凑在一起比赛? 不行, 一个人, R++
	 * 2. L这个人跟R这个人能不能凑在一起比赛? 不行, 差值不是2, 差值小就让R动, 差值大就让L动, 差值等于K就可以比赛
	 * 3. R++, L这个人跟R这个人能不能凑在一起比赛? 可以, 差值正好是2
	 * 4. 3用过了, 打个X, 然后同时跳下动, 能比赛同时跳, 因为同时用过了
	 */
	// 时间复杂度O(N*logN) ==>虽然指针不回退，时间复杂度是O(N),但是Arrays.sort使用的事快排和归并排序，它们的时间复杂度都是O(N*logN)
	public static int maxPairNum2(int ability[], int K) {
		if(ability == null || ability.length <= 1 || K < 0) {
			return 0;
		}
		int L = 0;
		int R = 0;
		int N = ability.length;
		boolean used[] = new boolean[N];
		int ans = 0;
		//先排序
		Arrays.sort(ability);
		
		while(L < N && R < N) {
			if(used[L]) { // L来到了已经使用过的位置,直接跳
				L++;
			} else if(L == R) { // 同一个位置，R++
				R++;
			} else {
				int distance = ability[R] - ability[L];
				if(distance == K) {
					ans++;
					used[R] = true;
					L++;
					R++;
				} else if(distance < K) {
					R++;
				} else {
					L++;
				}
			}
		}
		return ans;
	}
}
