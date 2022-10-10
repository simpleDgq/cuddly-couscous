package class03;

public class Code05_Largest1BorderedSquare {
	/*
	 * https://leetcode.cn/problems/largest-1-bordered-square/
	 * 1139. 最大的以 1 为边界的正方形.
	 * 给你一个由若干 0 和 1 组成的二维网格 grid，请你找出边界全部由 1 组成的最大 正方形 子网格，并返回该子网格中的元素数量。如果不存在，则返回 0。
	 */
	/**
	 * 思路:
	 * N*N中正方形数量: 任意一个点出发，边长从1到N都可以构成一个正方形。所以时间复杂度为:O(N^3)
	 * 
	 * 1.遍历举证的每一个点，边长从1到能够取到的最大值(边长的枚举，一定是横着、和竖着能达到的边界哪个小就是哪个)
	 * 求有多少个正方形
	 * 2. 三层for循环一套
	 * 3. 你就要想如何通过O(1)判断正方形边框是否全是1
	 * 
	 * 想要O(1)方式完成这件事:
	 * 对于任何一个点(i,j) 位置，边长为k，如果知道(i,j)包括它自己，右边有多少个连续的1；下方有多少个连续的1；判断是不是大于等于k；
	 * 就能很方便的判断当前边长下的正方形边框是不是全部是1.
	 * 
	 * 所以你得有数组存储矩阵的每一个点，右边有多少个连续的1，下边有多少个连续1.
	 */
	public static int largest1BorderedSquare(int[][] m) {
		if(m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		
		int N = m.length;
		int M = m[0].length;
		
		int right[][] = new int[N][M];
		int down[][] = new int[N][M];
		// 生成每个节点的下信息和右信息
		setBorderMap(m, right, down);
		
		int ans = 0;
		for(int i = 0; i <= N - 1; i++) {
			for(int j = 0; j <= M - 1; j++) {
				// 任意来到一个点，边长要取min值
				for(int border = 0; border <= Math.min(N - i - 1, M - j - 1); border++) {
					boolean res =hasSizeOfBorder(border, i, j, right, down);
					if(res) {
						ans = Math.max(ans,(border + 1) * (border + 1));
					}
				}
			}
		}
		return ans;
	}
	
	// 生成下信息和右信息
	public static void setBorderMap(int m[][], int right[][], int down[][]) {
		int N = m.length;
		int M = m[0].length;
		
		for(int i = N - 1; i >= 0; i--) {
			for(int j = M - 1; j >= 0; j--) {
				
				if(i == N - 1) {
					down[i][j] = m[i][j] == 1 ? 1 : 0;
				} else {
					down[i][j] = m[i][j] == 1 ? 1 + down[i + 1][j] : 0;
				}
				
				if(j == M - 1) {
					right[i][j] = m[i][j] == 1 ? 1 : 0;
				} else {
					right[i][j] = m[i][j] == 1 ? 1 + right[i][j + 1] : 0;
				}	
			}
		}
	}
	
	// 从(i,j)出发，边长为border的正方形，边上是否全为1
	public static boolean hasSizeOfBorder(int border, int i, int j, int right[][], int down[][]) {
		return right[i][j] >= border + 1 && down[i][j] >= border + 1 
				&& right[i + border][j] >= border + 1
				&& down[i][j + border] >= border + 1;
	}
	
	
	public static void main(String args[]) {
//		int m[][] = {{1,1,1,1}, 
//				     {1,1,0,1},
//				     {1,1,1,0},
//				     {1,1,1,1}};
		
		int m[][] = {{1,1,0,0}};
		
		int N = m.length;
		int M = m[0].length;
		
		int right[][] = new int[N][M];
		int down[][] = new int[N][M];
		setBorderMap(m, right, down);
		
//		for(int i = 0; i <= N - 1; i++) {
//			for(int j = 0; j <= M - 1; j++) {
////				System.out.print(right[i][j] + " ");
//				System.out.print(down[i][j] + " ");
//			}
//			System.out.println();
//		}
//		
		int res = largest1BorderedSquare(m);
		System.out.println(res);
	}

}
