package class24;

public class Code05_MinWindowLength {
    /**
     * 给定两个字符串str1和str2
        在str1中寻找一个最短子串，能包含str2的所有字符
        字符顺序无所谓，str1的这个最短子串也可以包含多余的字符
        返回这个最短包含子串
        
        【举例】
        str1="abcde"，str2="ac"
        因为"abc"包含 str2 所有的字符，并且在满足这一条件的str1的所有子串中，"abc"是 最短的，返回3。
        str1="12345"，str2="344" 最小包含子串不存在，返回0。
     */
    
    /**
     * 思路:
     * 滑动窗口 + 欠账表模型
     * 
     * 为什么想到滑动窗口? 因为有单调性，str1范围变大, 能搞定str2的字符数量一定只会变多不会变少。
     * 
     * 例子:
     * str1: bbcabccab  str2:bcca
     * 
     * 建一张欠账表: 统计str2中每个字符的个数
     * b:1
     * c:2
     * a:1
     * 
     * 总共的欠账all=4
     * 
     * 搞一个滑动窗口，刚开始L和R都是0，是左闭又开的[L,R), L 到 R这个范围，但是不包含R位置的数
     * 
     * str1上，R++，进来第一个b，欠账表中，b的数量1--，--之后是0，是大于等于0的，说明是一次有效的还款，all--;
     * all=3，不等于0，说明还有账要还，
     * R继续++，进来一个b，欠账表中，b的数量-1--，--之后是-2，是小于0的，说明是一次无效的还款，all不变;
     * all=3, 不等于0，说明还有账要还，
     * R继续++，进来一个c，欠账表中，c的数量2--，--之后是1，是大于等于0的，说明是一次有效的还款，all--;
     * all=2, 不等于0，说明还有账要还，
     * R继续++，进来一个a，欠账表中，a的数量1--，--之后是0，是大于等于0的，说明是一次有效的还款，all--;
     * all=1, 不等于0，说明还有账要还，
     * R继续++，进来一个b，欠账表中，b的数量-2--，--之后是-3，是小于0的，说明是一次无效的还款，all不变;
     * all=1, 不等于0，说明还有账要还，
     * R继续++，进来一个c，欠账表中，c的数量1--，--之后是0，是大于等于0的，说明是一次有效的还款，all--;
     * all=0, 没有账要还了，
     * 说明L~R位置，不包含R位置，因为是左闭右开的区间，这一段区间是一个答案，记录下来, min = R - L + 1;
     * 
     * all=0之后，L++，滑动窗口吐出一个b，欠账表中b的数量是-3，++之后是-2，是小于等于0的，说明不是一次有效的放回，all不变；
     * all还是0，继续收集一次答案，PK出最小值。 min = R - L + 1;
     * L继续++，滑动窗口又吐出一个b，欠账表中b的数量是-2，++之后是-1，是小于等于0的，说明不是一次有效的放回，all不变
     * all还是0，继续收集一次答案，PK出最小值。 min = R - L + 1;
     * L继续++，滑动窗口又吐出一个c，欠账表中c的数量是0，++之后是1，是大于0的，说明是一次有效的放回，all++
     * all变成1，不收集答案。
     * 
     * 接下来R继续++往右，重复上面的过程。。。。
     * 
     * 时间复杂度: L和R不回退，O(N)
     * 
     */
    // 这个解法返回的是最短字符串的长度
    // leet要求的事最短字符串是什么
    public static int minLength(String s, String t) {
        if(s == null || s.length() == 0 || t== null || t.length() == 0
                || s.length() < t.length()) {
            return 0;
        }
        // 建立欠账表。用数组，节约空间，ascii字符最多256个
        int map[] = new int[256];
        int M = t.length();
        char tStr[] = t.toCharArray();
        // t字符串中，每个字符出现了多少次，放入map中
        for(int i = 0; i <= M - 1; i++) {
            map[tStr[i]]++;
        }
        int L = 0;
        int R = 0;
        int N = s.length();
        char sStr[] = s.toCharArray();
        int all = M; // 欠账表总共多少个字符
        int ans = Integer.MAX_VALUE;
        while(R != N) {
            // 取R位置的字符，然后再R++, 例如R刚开始等于0，++之后就是1，所以要先取出0位置的字符，还款到欠账表，再++
            // 取出R位置的字符数量，--之后，如果该字符的数量是大于等于0的，说明是一次有效的还款，all要--
            //否则是一次无效的还款，all保持不变，R继续++
            if(--map[sStr[R]] >= 0) {
                all--;
            }
            // 每次R位置的字符还款之后，都要检查all是不是等于0，是的话，就要收集答案
            if(all == 0) {
                //收集一次答案
                ans = Math.min(ans, R - L + 1);
                // 将L位置的字符放回map，滑动窗口吐出一个字符，L++，如果放回存款表后，该字符的数量是小于等于0的，说明总的欠账all不变，还是0，需要收集答案
                while(++map[sStr[L++]] <= 0) {
                    ans = Math.min(ans, R - L + 1);
                }
                // 如果L位置的字符加入到欠账表之后，该位置的字符数大于0了，all要++，接下来R++
                all++;
            }
            R++;
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
    
    //测试链接 : https://leetcode.com/problems/minimum-window-substring/
    public String minWindow(String s, String t) {
        if(s == null || s.length() == 0 || t== null || t.length() == 0
                || s.length() < t.length()) {
            return "";
        }
        int N = s.length();
        int M = t.length();
        char s1[] = s.toCharArray();
        char s2[] = t.toCharArray();
        int map[] = new int[256]; // 这题如果用hashMap的话，得用两个。还有点复杂，没搞明白，为什么一个数组也行
        for(int i = 0; i <= M - 1; i++) {
            map[s2[i]]++;
        }
        
        int L = 0;
        int R = 0;
        int minLen = Integer.MAX_VALUE;
        int all = M;
        // 两个标记记录子串的开始和结束位置
        int ansL = -1;
        int ansR = -1;
        while(R != N) {
            if(--map[s1[R]] >= 0) {
                all--;
            }
            // 每次R位置的字符还款之后，都要检查all是不是等于0，是的话，就要收集答案
            if(all == 0) {
                if(minLen > R - L + 1) {
                    ansL = L;
                    ansR = R;
                    minLen = R - L + 1;
                }
                // L++一直往右
                while(++map[s1[L++]] <= 0) {
                    if(minLen > R - L + 1) {
                        ansL = L;
                        ansR = R;
                        minLen = R - L + 1;
                    }
                }
                all++;
            }
            R++;
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(ansL, ansR + 1);
    }
}
