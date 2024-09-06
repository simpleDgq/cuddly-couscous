package class03;

import java.util.ArrayList;
import java.util.HashMap;

public class Code08_FreedomTrail {
	
	// 本题测试链接 : https://leetcode.cn/problems/freedom-trail/
	/**
	 * 给定一个字符串 ring ，表示刻在外环上的编码；给定另一个字符串 key ，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数。
	 */
	
	/*
	 * 暴力递归+傻缓存
	 */
    public static int findRotateSteps(String ring, String key) {
    	if(ring == null || ring.length() == 0 || 
    			key == null || key.length() == 0) {
    		return 0;
    	}
    	// 生成ring每一个字符在环上的那些位置出现
    	int N = ring.length();
    	int ans = 0;
    	char[] rings = ring.toCharArray();
    	char[] keys = key.toCharArray();
    	HashMap<Character, ArrayList<Integer>> map = new HashMap<Character, ArrayList<Integer>>();
    	for(int i = 0; i <= N - 1; i++) {
    		char cur = rings[i];
    		if(!map.containsKey(cur)) {
    			ArrayList<Integer> list = new ArrayList<Integer>();
    			list.add(i);
    			map.put(cur, list);
    		} else {
    			map.get(cur).add(i);
    		}
    	}
    	
    	// set遍历使用迭代器
    	// map 遍历，forEach， entrySet
//    	for(Entry entry: map.entrySet()) {
//    		System.out.println(entry.getKey() + "=" + entry.getValue());
//    	}
    	
    	ans = process(0, 0, keys, N, map);
    	return ans;
    }
	/*
	 * preButton： 指针指着的上一个按键的位置
	 * index：当前要搞定的字符的index位置
	 * str: 要搞定的字符串
	 * N: 整个环的大小，ring的长度
	 * map: 存储哪些位置含有当前字符
	 * 
	 * 返回搞定当前字符的最小代价
	 */
	public static int process(int preButton, int index, char str[], int N, HashMap<Character, ArrayList<Integer>> map) {
		if(index == str.length) {// 没有字符了，代价为0
			return 0;
		}
		// 取出要搞定的字符的下一个可能的所有位置
		ArrayList<Integer> positions = map.get(str[index]);
		// 每个位置都去尝试
		int ans = Integer.MAX_VALUE; // 这里需要是最大值，否则取70行最小值的时候，可能会将答案变成0
		for(Integer next : positions) { // 所有可能的位置都去使，取最小值
			int cost = dial(preButton, next, N) + process(next, index + 1, str, N, map);
			ans = Math.min(ans, cost);
		}
		return ans;
	}
	
	/*
	 * 从i拨到j位置，最小代价给我返回
	 */
	public static int dial(int i, int j, int size) {
		// 两种情况，向左拨或者向右拨，取最小值
		int ans = 1; // 不管拨到哪，都要按一次确认
		ans += Math.min(Math.abs(j - i), size - Math.abs(j - i));
		return ans;
	}

	
	/*
	 * 傻缓存
	 */
	 public static int findRotateSteps2(String ring, String key) {
	    	if(ring == null || ring.length() == 0 || 
	    			key == null || key.length() == 0) {
	    		return 0;
	    	}
	    	// 生成ring每一个字符在环上的那些位置出现
	    	int N = ring.length();
	    	int M = key.length();
	    	int ans = 0;
	    	char[] rings = ring.toCharArray();
	    	char[] keys = key.toCharArray();
	    	HashMap<Character, ArrayList<Integer>> map = new HashMap<Character, ArrayList<Integer>>();
	    	for(int i = 0; i <= N - 1; i++) {
	    		char cur = rings[i];
	    		if(!map.containsKey(cur)) {
	    			ArrayList<Integer> list = new ArrayList<Integer>();
	    			list.add(i);
	    			map.put(cur, list);
	    		} else {
	    			map.get(cur).add(i);
	    		}
	    	}
	    	
	    	int dp[][] = new int[N][M + 1];
	    	for(int i = 0; i <= N - 1; i++) { // 全部初始化为-1，表示没有计算过
	    		for(int j = 0; j <= M; j++) { // 全部初始化为-1，表示没有计算过
	    			dp[i][j] = -1;
		    	}
	    	}
	    	
	    	ans = process2(0, 0, keys, N, map, dp);
	    	return ans;
	 }
	 
	 public static int process2(int preButton, int index, char str[], int N, HashMap<Character, ArrayList<Integer>> map, int dp[][]) {
		if(dp[preButton][index] != -1) {
			return dp[preButton][index];
		}
		int ans = Integer.MAX_VALUE; // 这里需要是最大值，否则取70行最小值的时候，可能会将答案变成0
		if(index == str.length) {// 没有字符了，代价为0
			ans = 0;
		} else {
			
			// 取出要搞定的字符的下一个可能的所有位置
			ArrayList<Integer> positions = map.get(str[index]);
			// 每个位置都去尝试
			for(Integer next : positions) { // 所有可能的位置都去使，取最小值
				int cost = dial(preButton, next, N) + process2(next, index + 1, str, N, map, dp);
				ans = Math.min(ans, cost);
			}
		}
		dp[preButton][index] = ans;
		return ans;
	}
	
	
	public static void main(String[] args) {
		String rString = "godding";
		int size = rString.length();
		String key = "gd";
		//System.out.println(dial(5, 0, size));
		
		int ans = findRotateSteps(rString, key);
		System.out.println(ans);
	}
}
