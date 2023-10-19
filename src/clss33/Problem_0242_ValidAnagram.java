package clss33;

// 242. 有效的字母异位词
public class Problem_0242_ValidAnagram {
    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * 
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     */
    
    /**
     * 思路:
     * 
     * 如果长度不相等，一定不是异位词；
     * 如果长度相等，字符串1每种字符 ++的次数，
     * 对于字符串2每一个字符，都没有--到小于0的时刻，
     * 说明字符数一样，种类也一样
     */
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) {
            return false; // 如果长度不相等
        }
        // 如果长度相等
        char s1[] = s.toCharArray();
        char t1[] = t.toCharArray();
        
        int chars[] = new int[256];
        for(char ch: s1) {
            chars[ch]++; // 字符串每种字符都++
        }
        
        // 字符串2，每种字符都--，所有字符如果--都没有小于0的时刻
        // 而且两者长度相等，说明两个字符串长度相等，字符数量相同
        for(char ch: t1) {
            chars[ch]--;
            if(chars[ch] < 0) {
                return false; // 有小于0的时刻，说明某个字符，数量不相等
            }
        }
        return true;
    }
}
