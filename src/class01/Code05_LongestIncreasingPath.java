package class01;

public class Code05_LongestIncreasingPath {
	
	/**
	 * 329.二维数组最大递增链的长度 [H]
	 * 
	 * 给定一个二维数组matrix，可以从任何位置出发，每一步可以走向上、下、左、右，四个方向。返回最大递增链的长度。
		例子：
		matrix =
		5 4 3
		3 1 2
		2 1 3
		从最中心的1出发，是可以走出1 2 3 4 5的链的，而且这是最长的递增链。所以返回长度5
	 */
	
    public int longestIncreasingPath(int[][] matrix) {
    	if(matrix == null || matrix.length == 0) {
    		return 0;
    	}
    	int N = matrix.length;
    	int M = matrix[0].length;
    	int ans = 0;
    	for(int i = 0; i <= N - 1; i++) { // 每个i,j都去尝试一遍
    		for(int j = 0; j <= M - 1; j++) {
    			ans = Math.max(ans, process(matrix, i, j));
        	}
    	}
    	return ans;
    }
	
	// 暴力递归
	// 从i,j位置出发，你给我返回能走出的最大递增链长度！
	public static int process(int arr[][], int i, int j) {
//		if(i < 0 || i > arr.length - 1 || j < 0 || j > arr[0].length - 1) { // 如果越界了，直接返回0
//			return 0;
//		} // 因为下面的代码，在调用子过程process之前已经判断了是否越界，所以这段代码可以直接删掉
		// 有4个方向可以选择，分别算出最大值，然后取最大
		int up = i > 0 && (arr[i - 1][j] > arr[i][j])? process(arr, i - 1, j) : 0; // 往上走. 上面有元素，而且上面的元素比当前元素大，才往上走
		int down = i < (arr.length - 1) && (arr[i + 1][j] > arr[i][j])? process(arr, i + 1, j) : 0; // 往下走. 下面有元素，而且下面的元素比当前元素大，才往下走
		int left = j > 0 && (arr[i][j - 1] > arr[i][j])? process(arr, i, j - 1) : 0; // 往左走. 左边有元素，而且左边的元素比当前元素大，才往左走
		int right = j < (arr[0].length - 1) && (arr[i][j + 1] > arr[i][j])? process(arr, i, j + 1) : 0; // 往右走. 右边有元素，而且右边的元素比当前元素大，才往右走
		return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
	}
	

	// 傻缓存
    public int longestIncreasingPath2(int[][] matrix) {
    	if(matrix == null || matrix.length == 0) {
    		return 0;
    	}
    	int N = matrix.length;
    	int M = matrix[0].length;
    	int ans = 0;
    	int dp[][] = new int[N][M]; // 每个位置，至少最大递增链长度是1，如果是0则表示没有计算过，直接计算
    	for(int i = 0; i <= N - 1; i++) { // 每个i,j都去尝试一遍
    		for(int j = 0; j <= M - 1; j++) {
    			ans = Math.max(ans, process2(matrix, i, j, dp));
        	}
    	}
    	return ans;
    }

	public static int process2(int arr[][], int i, int j, int dp[][]) {
		if(dp[i][j] != 0) {
			return dp[i][j];
		}
		int up = i > 0 && (arr[i - 1][j] > arr[i][j])? process2(arr, i - 1, j, dp) : 0;
		int down = i < (arr.length - 1) && (arr[i + 1][j] > arr[i][j])? process2(arr, i + 1, j, dp) : 0;
		int left = j > 0 && (arr[i][j - 1] > arr[i][j])? process2(arr, i, j - 1, dp) : 0;
		int right = j < (arr[0].length - 1) && (arr[i][j + 1] > arr[i][j])? process2(arr, i, j + 1, dp) : 0;
		int ans = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
		dp[i][j] = ans;
		return ans;
	}
	
	// 每个位置依赖四个位置，而且不固定，分析严格依赖比较麻烦，不做了，傻缓存已经是最优解

}
