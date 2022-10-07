package class02;

public class Code06_Drive {
	/**
	 * 现有司机N * 2人，调度中心会将所有司机平分给A、B两个区域
	 *	第 i 个司机去A可得收入为income[i][0]，
	 *	第 i 个司机去B可得收入为income[i][1]，
	 *	返回所有调度方案中能使所有司机总收入最高的方案，是多少钱
	 */
	// 暴力递归
	public static int maxMoney(int income[][]) {
		if(income == null || income.length == 0 || (income.length & 1) != 0) {
			return 0;
		}
		int N = income.length;
		int M = N >> 1;
		return process(income, 0, M);
	}
	// index及其往后所有的司机，往A和B区域分配！
	// A区域还有rest个名额!
	// 返回把index及其往后所有的司机，分配完，并且最终A和B区域同样多的情况下，index...这些司机，整体收入最大是多少！
	public static int process(int income[][], int index, int rest) {
		if(index == income.length) { // 越界了，收入为0
			return 0;
		}
		// A区域没有位置了，index及其往后的司机全部去B区域
		if(rest == 0) {
			return income[index][1] + process(income, index + 1, rest);
		}
		// 剩下的司机数正好是A区域剩下位置，index及其往后的司机全部去A区域
		if(income.length - index == rest) {
			return income[index][0] + process(income, index + 1, rest - 1);
		}
		// 普通情况，可以去A，也可以去B，取最大值
		int p1 = income[index][1] + process(income, index + 1, rest);
		int p2 = income[index][0] + process(income, index + 1, rest - 1);
		return Math.max(p1, p2);
	}
	
	
	/**
	 * 动态规划
	 * index: 0 ~ N
	 * rest: 0 ~ M
	 * 时间复杂度: O(N*M) --> M = N / 2 -> 所以O(N^2)
	 * dp[N + 1][M + 1]
	 * index,rest 位置的值只由左下（index + 1,rest - 1） 和下方（index + 1,rest）位置决定
	 * 从下往上，从左往右，填好整张表格
	 */
	public static int maxMoney2(int income[][]) {
		if(income == null || income.length == 0 || (income.length & 1) != 0) { // 不是奇数也返回，因为司机要平分
			return 0;
		}
		int N = income.length;
		int M = N >> 1;
		int dp[][] = new int[N + 1][M + 1];
		// 由base case可以知道，最后一行全是0
		// 所以从N - 1行开始
		for(int index = N - 1; index >= 0; index--) {
			for(int rest = 0; rest <= M; rest++) {
				if(rest == 0) { // A区域没有位置了，index及其往后的司机全部去B区域
					dp[index][rest] = income[index][1] + dp[index + 1][rest];
					// 不加else if，这里就需要break，因为递归会直接return
				} else if(income.length - index == rest) { // 剩下的司机数正好是A区域剩下位置，index及其往后的司机全部去A区域
					dp[index][rest] = income[index][0] + dp[index + 1][rest - 1];
				} else {
					// 普通情况，可以去A，也可以去B，取最大值
					int p1 = income[index][1] + dp[index + 1][rest];
					int p2 = income[index][0] + dp[index + 1][rest - 1];
					dp[index][rest] = Math.max(p1, p2);
				}
			}
		}
		return dp[0][M];
	}
	
	
	// 返回随机len*2大小的正数矩阵
	// 值在0~value-1之间
	public static int[][] randomMatrix(int len, int value) {
		int[][] ans = new int[len << 1][2];
		for (int i = 0; i < ans.length; i++) {
			ans[i][0] = (int) (Math.random() * value);
			ans[i][1] = (int) (Math.random() * value);
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 10;
		int value = 100;
		int testTime = 500;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[][] matrix = randomMatrix(len, value);
			int ans1 = maxMoney(matrix);
			int ans2 = maxMoney2(matrix);

			if (ans1 != ans2 ) {
				System.out.println(ans1);
				System.out.println(ans2);

				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

}
