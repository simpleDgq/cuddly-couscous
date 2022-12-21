package class17;

import java.util.HashMap;

//本题测试链接 : https://leetcode.com/problems/distinct-subsequences-ii/
public class Code04_DistinctSubseqValue {
    /** 940. 不同的子序列 II
     * 给定一个字符串s，求s中有多少个字面值不相同的子序列。

        给定一个字符串 S，计算 S 的不同非空子序列的个数。
        
        因为结果可能很大，所以返回答案模 10^9 + 7.
        
        示例 1：
        
        输入："abc"
        输出：7
        解释：7 个不同的子序列分别是 "a", "b", "c", "ab", "ac", "bc", 以及 "abc"。
        示例 2：
        
        输入："aba"
        输出：6
        解释：6 个不同的子序列分别是 "a", "b", "ab", "ba", "aa" 以及 "aba"。
        示例 3：
        
        输入："aaa"
        输出：3
        解释：3 个不同的子序列分别是 "a", "aa" 以及 "aaa"。
        提示：
        
        S 只包含小写字母。
        1 <= S.length <= 2000
     */
    
    /**
     * 思路:
     * 1. 没有重复字符的时候
     * 例子: 123
     * 刚开始有一个空集，all = 1
     * 1来的时候，之前所有的集合结尾加一个1，组成新的集合，{1} , 加上老的空集 ==> {},{1}两个集合
     * 2来的时候，上一步所有的集合结尾加一个2，组成新的集合，{2} {1,2} ,  加上上一步老的集合{},{1} ==> {},{1} {2} {1,2}4个集合
     * 3来的时候，上一步所有的集合结尾加一个3，组成新的集合，{3} {1,3} {2,3} {1,2,3} ,加上上一步老的集合{},{1} {2} {1,2} ==> {},{1} {2} {1,2} {3} {1,3} {2,3} {1,2,3} 
     * 8个集合
     * 2. 有重复字符的时候
     * 例子: 121
     * 刚开始有一个空集{}，all = 1
     * 1来的时候，之前所有的集合结尾加一个1，组成新的集合，{1} , 加上老的空集 ==> {},{1}两个集合
     * 2来的时候，上一步所有的集合结尾加一个2，组成新的集合，{2} {1,2} ,  加上上一步老的集合{},{1} ==> {},{1} {2} {1,2}4个集合
     * 1来的时候，上一步所有的集合结尾加一个1，组成新的集合，{1} {1,1} {2,1} {1,2,1} 加上上一步老的集合{},{1} {2} {1,2}
     * {},{1} {2} {1,2} {1} {1,1} {2,1} {1,2,1}  ==> 有重复集合，{1} 需要减去
     * 发现集合数是上一步的all + 每一个字符加到结尾，新形成的集合newAdd - 一个修正  => 这个修正就是上一次以当前字符出现的时候，以它结尾的集合的个数
     * 例子中就是第一次1出现的时候，所有形成的集合中，以1结尾的集合的个数，就是1.
     * 
     */
    public static int distinctSubseqII(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int m = 1000000007;
        int all = 1; // 一个元素也没有的时候，空集算1个
        char[] str = s.toCharArray();
        // 记录上次以某个字符结尾的集合数
        // key: 上次出现的字符 value: 上次以该字符结尾的集合数
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for(char ch : str) {
//            int newAdd = all; // 上一次的集合每一个结尾加一个字符，就是新增的
//            // 当前的all等于all + 新增的newAdd - 上次以该字符结尾的集合数
//            int curAll = all + newAdd - (map.containsKey(ch) ? map.get(ch) : 0);
//            all = curAll;
//            // 记录以当前字符结尾的集合数，就是newAdd
//            map.put(ch, newAdd);
            
            // leetcode需要模一个数，否则会越界
            int newAdd = all;
            int curAll = (all + newAdd) % m;
            // 加m，保证curAll - (map.containsKey(ch) ? map.get(ch) : 0)是负数的情况下，加上m之后，再模m，也落在0~m区间内，保证结果是对的
            curAll = (curAll - (map.containsKey(ch) ? map.get(ch) : 0) + m) % m;
            all = curAll;
            map.put(ch, newAdd);
        }
        return all - 1; // leetcode不算空集，要减掉
    }
}
