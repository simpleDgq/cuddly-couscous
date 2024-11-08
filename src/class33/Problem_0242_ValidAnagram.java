package class33;

// 242. 有效的字母异位词
public class Problem_0242_ValidAnagram {
    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * 
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     */
    
    /**
     * 
     * 思路：
     * 1.如果两个字符串长度不相等，那么必然不是字母异位词
     * 2.搞一个数组count记录s中每个字符出现的次数, 遍历s填好
     * 3.遍历t，对count中对应的字符数量进行--，--之后如果出现了次数小于0的情况，那么一定不是字母异位词
     * 
     * 也可以用hashmap
     * 或者排序之后，看是否相等，Arrays.equals()
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false; // 如果长度不相等
        }
        // 如果长度相等
        char s1[] = s.toCharArray();
        char t1[] = t.toCharArray();

        int count[] = new int[256];
        for (char ch : s1) {
            count[ch]++; // 字符串每种字符都++
        }
        // 字符串2，每种字符都--，所有字符如果--都没有小于0的时刻
        // 而且两者长度相等，说明两个字符串长度相等，字符数量相同
        for (char ch : t1) {
            count[ch]--;
            if (count[ch] < 0) {
                return false; // 有小于0的时刻，说明某个字符，数量不相等
            }
        }
        return true;
    }
}
