package class03;

import java.util.HashSet;

public class Code04_HowManyTypes {
	
	/*
	 * 只由小写字母（a~z）组成的一批字符串，都放在字符类型的数组String[] arr中，
	 * 如果其中某两个字符串，所含有的字符种类完全一样，就将两个字符串算作一类 比如：baacba和bac就算作一类
	 * 虽然长度不一样，但是所含字符的种类完全一样（a、b、c） 返回arr中有多少类？
	 * 
	 */
	
	/**
	 * 思路:
	 * 1. a~z总共右26个，一个整数有32位，从0~25位可以表示a~z
	 * 2. 遍历每一个字符串的每一位字符，将整数的对应的位置标成1
	 * 3. 最后有几个不同的整数，就有几个不同种类的字符串
	 */
	
	// 使用整数标志字符串的每一位
	public static int types1(String[] arr) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		if(arr.length == 1) {
			return 1;
		}
		
		HashSet<Integer> types = new HashSet<>();
		for (int i = 0; i <= arr.length - 1; i++) {
			String cur = arr[i];
			char arr2[] = cur.toCharArray();
			int type = 0;
			for(int j = 0; j <= arr2.length - 1; j++) {
				type |= 1 << (arr2[j] - 'a'); // 0位表示a, 需要左移
			}
			types.add(type);
		}
		return types.size();
	}
	
	// 使用boolean数组标志字符串的每一位
	public static int types2(String[] arr) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		if(arr.length == 1) {
			return 1;
		}
		
		HashSet<String> types = new HashSet<>();
		for (int i = 0; i <= arr.length - 1; i++) {
			String cur = arr[i];
			char arr2[] = cur.toCharArray();
			String type = "";
			boolean map[] = new boolean[26]; // 标记字符串的每一位是不是对应的字符
			for(int j = 0; j <= arr2.length - 1; j++) {
				map[arr2[j] - 'a'] = true;
			}
			// 根据map生成type
			for(int k = 0; k <= 25; k++) {
				type += map[k] ? k + 'a' : "";
			}
			types.add(type);
		}
		return types.size();
	}
	
	// for test
	public static String[] getRandomStringArray(int possibilities, int strMaxSize, int arrMaxSize) {
		String[] ans = new String[(int) (Math.random() * arrMaxSize) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = getRandomString(possibilities, strMaxSize);
		}
		return ans;
	}

	// for test
	public static String getRandomString(int possibilities, int strMaxSize) {
		char[] ans = new char[(int) (Math.random() * strMaxSize) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strMaxSize = 10;
		int arrMaxSize = 100;
		int testTimes = 500000;
		System.out.println("test begin, test time : " + testTimes);
		for (int i = 0; i < testTimes; i++) {
			String[] arr = getRandomStringArray(possibilities, strMaxSize, arrMaxSize);
			int ans1 = types1(arr);
			int ans2 = types2(arr);
			if (ans1 != ans2) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");

	}

}
