package class03;

//本题测试链接 : https://leetcode.cn/problems/longest-substring-without-repeating-characters/
public class Code03_LongestSubstringWithoutRepeatingCharacters {

	/**
	 * 求一个字符串中，最长无重复字符子串长度
	 * 
	 * 思路:
	 * 这就是所谓的你看到任何一个关于什么子串问题，还是子数组问题
		先这么想，这个答案你甭管它问的是啥，这道题是最长无重复子串，是最长无重复这个事儿，
		下回一个别的问题叫S问题，只要是子串子数组的问题，你就想0结尾的时候这个s答案是啥？
		1结尾的时候这个s答案是啥，就这么想，这是我们的一个重要思维传统
		
		1. 必须以0结尾的情况下左侧推多远能不重复求答案，
		子串必须以1结尾的情况下左侧推多远能不重复求个答案，
		子串必须与二结尾的时候左侧最多能推多远能不重复求答案，
		如果我们每个位置结尾的时候往左侧往左侧能推多远不重复的答案都求出来，
		所有答案中的最大值就是我要的
		2. 答案是从左往右求的当我来到i位置的时候，i-1位置的答案你求过了, i-2位置的答案你求过了,
		i-3位置的答案你求过了，那么我就想我i结尾时的答案能不能由我之前求出答案帮我加速得到，
		为啥要把求解流程定位每个位置结尾或每个位置开头，我就是想用动态规划
		3. i号位置，假如是a字符，它能往前推多远，取决于两个因素：
		 1) a上次出现的位置
		 2) i-1号位置能够往前推到的位置
		 两者取最小值
		4. 不需要整张动态规划表
		因为i位置只需要i-1位置的答案, 并不需要左边所有的那些答案
		在推i位置的时候, 只需要i-1位置的答案, 你用有限几个变量滚动跟下去就可以了，
		你没有必要准备整个dp数组
	 */
	
	
    public static int lengthOfLongestSubstring(String s) {
    	if(s == null || s.length() == 0) {
    		return 0;
    	}
    	if(s.length() == 1) {
    		return 1;
    	}
    	
    	int map[] = new int[256]; // 记录某个字符上次出现的下标位置.都是acsii字符，只需要表示0~255
    	char arr[] = s.toCharArray();
    	int N = arr.length;
    	for(int i = 0; i <= N - 1; i++) {
    		map[arr[i]] = -1; // 注意
    	}
    	map[arr[0]] = 0; //注意
    	
    	int pre = 1; // 当前字符的前一个位置，向左推了多长
    	int ans = 1;// 一个字符，最少也有1
    	
    	for(int i = 1; i <= N - 1; i++) {
    		int p1 = i - map[arr[i]]; // 第一种可能性，上次出现的位置
    		int p2 = pre + 1; //第二种可能性， i-1位置能推的最大距离 + 1
    		int cur = Math.min(p1, p2); // 取最小值
    		ans = Math.max(cur, ans);
    		pre = cur;// i++之前记录
    		map[arr[i]] = i;// i++之前记录
    	}
    	return ans;
    }
}
