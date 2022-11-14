package class12;


//本题测试链接 : https://leetcode.com/problems/permutation-in-string/
public class Code01_ContainAllCharExactly {
    /*
     * 给定长度为m的字符串aim，以及一个长度为n的字符串str
        问能否在str中找到一个长度为m的连续子串，
        使得这个子串刚好由aim的m个字符组成，顺序无所谓，
        返回任意满足条件的一个子串的起始位置，未找到返回-1
     */
    
    /**
     * 滑动窗口 + 欠账表
     * 
     * 搞一张欠账表，统计aim中每个字符的个数，同时搞一个变量all，记录aim所有的字符数。
     * 
     * 搞一个窗口，刚开始一直增长，增长的过程中，对应的欠账表中的字符个数减减，all也减减，增长到aim的长度的时候，停止增长。
     * 
     * 如果all是0，说明找到了，直接返回。
     * 
     * 接下来的过程就是，右边进一个，左边吐出一个；进的时候，如果欠账表里面对应的字符的个数是大于0的，all--；如果是负数，说明
     * 以前进的时候，已经减过了，窗口内对应的字符的个数，已经超过了aim中对应的字符，all不能再减减。
     * 吐的时候，如果欠账表里面对应的字符的个数是大于等于0的，all++；如果是负数，说明以前减多了，现在吐出去，欠账表里面对应的字符的个数正好补起来，
     * all不用动。
     * 
     * 时间复杂度： O(N) 窗口不回退
     */
    public static int containExactly(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return -1;
        }
        char str2[] = s2.toCharArray();
        int M = s2.length();
        int count[] = new int [256]; // ascii 字符都能包括
        // 统计aim s2 中的字符数
        for(int i = 0; i <= M - 1; i++) {
            count[str2[i]]++;
        }
        // 在s1上玩滑动窗口，
        // 滑动窗口右边界
        int R = 0;
        int all = M;
        char str1[] = s1.toCharArray();
        // 形成初始规模的窗口
        for(; R < M; R++) {
            // 字符进窗口的过程中，更新欠账表
            if(--count[str1[R]] >= 0) { // 如果欠账表对应的字符数，--之后是大于0的，说明是一次有效的更新，all--
                all--;
            }
        }
        // 形成的初始规模的窗口，没有判断是不是有效的，直接在这里判断
        // 左出右进的过程，同时判断形成的滑动窗口是不是答案
        for(; R < s1.length(); R++) { 
            if(all == 0) { // 上一次形成的窗口，判断是否是有效的
                return R - M; // 返回有效范围的开始位置
            }
            // 左出, R - M位置出
            if(count[str1[R - M]]++ >= 0) {
                all++;
            }
            // 右进
            if(--count[str1[R]] >= 0) { // --之后大于0，说明是有效的还款，all--
                all--;
            }
        }
        // 最后的一个滑动窗口范围，上面的过程都没有判断，这里返回之前判断
        return all == 0 ? R - M : -1;
    }
}
