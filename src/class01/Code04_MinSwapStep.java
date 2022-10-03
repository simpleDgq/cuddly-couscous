package class01;

public class Code04_MinSwapStep {
	
	/**
	 * 相邻字符的交换次数
	 * 一个数组中只有两种字符'G'和'B’，可以让所有的G都放在左侧，
	 * 所有的B都放在右侧, 或者可以让所有的G都放在右侧，所有的B都放在左侧,
	 *  但是只能在相邻字符之间进行交换操作，返回至少需要交换几次
	 */
	
	//双指针index和L
	// index: 判断index是不是字符G, 不是就往右飘
	// L: 记录要交换的位置
	// 遍历一遍数组搞定， O(N)
	public static int minStep1(String s) {
		if(s == null || s.length() == 0) {
			return 0;
		}
		char arr[] = s.toCharArray();
		int LG = 0;
		int ansG = 0;
		// G放左边
		for(int i = 0; i <= arr.length - 1; i++) { // 这里用i代替了index，省略了一个变量
			if(arr[i] == 'G') {
				ansG += i - LG;
				LG++;
			}
		}
		// B放左边
		int LB = 0;
		int ansB = 0;
		for(int i = 0; i <= arr.length - 1; i++) {
			if(arr[i] == 'B') {
				ansB += i - LB;
				LB++;
			}
		}
		return Math.min(ansG, ansB);
	}
	
	
	/**
	 * 合在一起
	 */
	public static int minStep2(String s) { // G放左边
		if(s == null || s.length() == 0) {
			return 0;
		}
		char arr[] = s.toCharArray();
		int stepsG = 0; // G放左边，交换次数
		int stepsB = 0;// B放左边，交换次数
		int LG = 0; // G放左边的时候，应该交换的元素的位置
		int LB = 0;// B放左边的时候，应该交换的元素的位置
		for(int i = 0; i <= arr.length - 1; i++) {
			if(arr[i] == 'G') {
				stepsG += i - LG;
				LG++;
			} else {
				stepsB += i - LB;
				LB++;
			}
		}
		return Math.min(stepsB, stepsG);
	}
	
	// 为了测试
	public static String randomString(int maxLen) {
		char[] str = new char[(int) (Math.random() * maxLen)];
		for (int i = 0; i < str.length; i++) {
			str[i] = Math.random() < 0.5 ? 'G' : 'B';
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int maxLen = 100;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			String str = randomString(maxLen);
			int ans1 = minStep1(str);
			int ans2 = minStep2(str);
			if (ans1 != ans2) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}
}
