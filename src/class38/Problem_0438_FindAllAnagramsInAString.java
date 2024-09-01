package class38;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 438. 找到字符串中所有字母异位词
// https://leetcode.cn/problems/find-all-anagrams-in-a-string/description/
public class Problem_0438_FindAllAnagramsInAString {
    /**
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
     * 
     * 示例 1:
     * 输入: s = "cbaebabacd", p = "abc"
     * 输出: [0,6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
     */

    /**
     * 思路：滑动窗口 + 欠账表
     * 搞一个欠账表map，记录每个字符还有多少个还没有在窗口内出现过。
     * 搞一个滑动窗口，从左往右，从每一个位置出发，看当前窗口内有没有将
     * 所有的欠账都搞定，搞定的话，总的欠账all就是0，搜集当前窗口的起始位置就是答案。
     * 每一个位置都搞一遍，最后就得到了所有的答案。
     * 
     * all 表示当前窗口内离搞定p还差的字符个数
     * 例子: abcabbdee p: abc
     * 欠账表就是: a: 1 b: 1 c: 1 总共欠的就是3
     */

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<Integer>();
        if (s == null || s.length() == 0 || 
                p == null || p.length() == 0 || s.length() < p.length()) {
            return result;
        }
        // 生成欠账表
        
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        char pchars[] = p.toCharArray();
        char sChars[] = s.toCharArray();
        int M = pchars.length;
        int N = sChars.length;
        int all = M;
        for (char c : pchars) {
            if (!map.containsKey(c)) { // 不存在就加一个
                map.put(c, 1);
            } else { // 存在，数量加1
                map.put(c, map.get(c) + 1);
            }
        }
        // 如果p的长度是5，那么滑动窗口里面字符的数量，也必须是5，不能超过5
        // 先生成4个长度的滑动窗口，然后后面加一个，判断一下当前窗口是不是符合条件
        // 然后吐出一个，再加入一个。。。继续往下
        // aac abc
        for (int end = 0; end < M - 1; end++) {
            char c = sChars[end];
            // 如果欠账表里面存在这个字符，则更新欠账表
            if (map.containsKey(c)) {
                // 欠账表里面是大于0的，则是一次有效的还款，all--
                if (map.get(c) > 0) {
                    all--;
                } // 如果已经小于或者等于0了，不是有效的还款。而all表示的是总的欠款，所以不变
                map.put(c, map.get(c) - 1); // 欠账表--
            }
            // 不存在不更新
        }
        // 加一个，吐一个，判断是否满足条件
        for (int end = M - 1, start = 0; end <= N - 1; start++, end++) {
            char c = sChars[end];
            // 加一个字符到滑动窗口里面
            // 如果滑动窗口里面有这个字符
            if (map.containsKey(c)) {
                // 欠账表里面是大于0的，则是一次有效的还款，all--
                if (map.get(c) > 0) {
                    all--;
                }
                map.put(c, map.get(c) - 1); // 欠账表--
            }
            // 验证是否符合
            if (all == 0) {
                result.add(start);
            }
            // 吐出最左边的
            // 如果欠账表里面存在吐出的字符
            char left = sChars[start];
            if (map.containsKey(left)) {
                // 而且欠账表里面该字符的次数是大于等于0的。
                // 说明是一次有效的借款，吐出之后，总的欠款数需要++
                if(map.get(left) >= 0) {
                    all++;
                }
                // 欠账表更新
                map.put(left, map.get(left) + 1);
            }
        }
        return result;
    }
    
    public static void main(String args[]) {
        String s = "cbaebabacd";
        String p = "abc";
        System.out.print(p.length());
        findAnagrams(s, p);
             
    }
}
