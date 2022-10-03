package class01;

import java.util.Arrays;

public class Code01_CordCoverMaxPoint {
	/**
	 * 给定一个有序数组arr，代表坐落在X轴上的点，给定一个正数K，代表绳子的长度，返回绳子最多压中几个点？
	 *	即使绳子边缘处盖住点也算盖住
	 */
	
	/**
	 * 如果绳子长度是100，数组中的元素是983，则以983结尾能够覆盖的最多的点，往左边推，最大就是883.
	 * 也就是求983的左边有多少个数是大于等于883的。==> 数组中的每一个元素num为结尾，它的左边有多少个数是大于等于num - K的。
	 * 二分法--> N*logN
	 */
	public static int maxPoint1(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		int ans = -1;
		for(int i = 0; i <= arr.length - 1; i++) { // 对于数组中的每一个数都去二分
			int nearestIndex = nearestIndex(arr, i, arr[i] - K);
			ans = Math.max(ans, i - nearestIndex + 1);
		}
		return ans;
	}
	
	public static int nearestIndex(int arr[], int R, int value) {
		int L = 0;
		int ans = -1;
		while(L <= R) {
			int mid = L + ((R - L) >> 1);
			if(arr[mid] >= value) {
				ans = mid;
				R = mid - 1;
			} else {
				L = mid + 1;
			}
		}
		return ans;
	}
	
	
	/**
	 * 滑动窗口 O(N)
	 * 
	 * 准备两个指针L和R，求以数组中的每一个元素打头，最多能往右推几个点，
	 * 就是此时以该元素开始绳子能够覆盖的最大点数
	 */
	public static int maxPoint2(int arr[], int K) {
		if(arr == null || arr.length == 0) {
			return -1;
		}
		int L = 0;
		int R = 0;
		int N = arr.length;
		int max = -1;
		while(L < N) {
			while(R < N && arr[R] - arr[L] <= K) { // L固定的情况下，R往右到不能往右
				R++;
			}
			max = Math.max(max, R - L);
			L++; // 求下一个点打头的答案
		}
		return max;
	}
	// for test
	public static int[] generateArray(int len, int max) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * max);
		}
		Arrays.sort(ans);
		return ans;
	}

	public static void main(String[] args) {
		int len = 100;
		int max = 10000;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int L = (int) (Math.random() * max);
			int[] arr = generateArray(len, max);
			int ans1 = maxPoint1(arr, L);
			int ans2 = maxPoint2(arr, L);
			if (ans1 != ans2 ) {
				System.out.println("oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}
}
