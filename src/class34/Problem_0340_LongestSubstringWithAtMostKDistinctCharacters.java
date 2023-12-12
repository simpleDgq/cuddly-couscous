package class34;

import java.util.HashMap;
// 340.至多包含k个字符的最长子串

public class Problem_0340_LongestSubstringWithAtMostKDistinctCharacters {
    /**
     * 给定一个字符串，从中找子串，如果这个子串的字符种类超过k个，就是不达标
     * 如果没有超过k个，就是达标串，所有最长达标串里面谁最长，把那个长度返回
     */
    
    /**
     * 思路:
     * 
     * 滑动窗口 + 记账表(Map， key是字符，value是字符出现的次数)
     * 
     * 有单调性，所以想到滑动窗口: 一个窗口，如果我的窗口范围扩大了，它含有的字符种类
     * 只可能变多或者不变，不可能变少；也就是说窗口的大小和它所含的字符种类之间是有单调性的。
     * 
     * 每次来到一个开始位置，该扩扩，然后搜集一个答案，该缩缩，来到一个新的位置，每个位置出发都
     * 求一个答案，所有答案中求最大值就行
     * 
     * 
     * 例子:
     * 
     * k = 3 str = aabccd
     * 
     * 第0位置的字符是a, 在map中记录a出现一次，这时候map的size是1(map的size就是字符种类数)，没有超过3，可以继续往下扩
     * 
     * 第1位置的字符是a, 在map中a出现的次数+1，只是增加了词频，这时候map的size还是1，没有超过3，可以继续往下扩
     * 
     * 第2位置的字符是b, 在map中b出现的次数是1，这时候map的size2是2，没有超过3，可以继续往下扩
     * 
     * 第3位置的字符是c, 在map中c出现的次数是1，这时候map的size增加，等于3，没有超过3，继续往下扩
     * 
     * 第4位置的字符是c, 在map中c出现的次数+1，这时候map的size还是3，等于3，没有超过3，继续往下扩
     * 
     * 第5位置的字符是d, 不能加入map中，因为加入之后，size就超过3了，这时候从0到4位置，就是从0出发，能够扩出来的最长子串，
     * 这时候L到R上其实就是一个答案，搜集； --> 0开头的答案是5长度
     * 
     * 然后L++，吐出一个字符，0位置的字符a吐出去，map中a字符次数减1，不从map中删掉a，因为a的词频是1，还有a
     * 如果字符次数是0，该字符就可以从map中删除
     * 
     * 5位置的字符能够加入到窗口中去吗？ 不能，因为5位置的字符是d，加入进去之后，size就超过3了， 说明从1位置出发能够扩出来的
     * 最长子串是1到4为止，搜集答案 --> 1为止开头的答案是4
     * 
     * 接下来再缩窗口
     * 
     * 
     * 
     * 整体算法流程:
     * 1.初始化左右指针 left 和 right 为 0。
     * 2.将 right 指针右移，一直往右扩，直到窗口中包含超过 k 个不同字符。
     * 3.如果滑动窗口中的字符种类超过了k，说明不能往右扩了，缩小窗口
     * 4.更新最长子串的长度。
     */
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if(s == null || s.length() == 0 || k <= 0) {
            return 0;
        }
        // 记账表，key: 字符 value: 字符出现的次数
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int L = 0;
        int R = 0;
        char chars[] = s.toCharArray();
        int N = chars.length;
        int ans = 0;
        
        while(R < N) {
            char cur = chars[R];
            // 如果map中有当前字符，当前字符的次数加1
            if(map.containsKey(cur)) {
               map.put(cur, map.get(cur) + 1); 
            } else {
               // 如果map中没有当前字符，直接加入当前字符
                map.put(cur, 1); 
            }
            // 窗口往右扩
            R++;
            
            // 一直往右扩，如果map的size等于k+1，说明从L位置出发，不能继续往右扩了
            // 需要缩小窗口，L++缩小窗口。map中吐出这个字符，如果词频变成0，从map中删除这个字符
            while(map.size() == k + 1) {
                char curL = chars[L];
                // map吐出L上的字符。 如果吐出后，次数变成0，则从map中删除这个字符
                if(map.get(curL) - 1 == 0) {
                    map.remove(curL);
                } else {
                    // 次数减减
                    map.put(curL, map.get(curL) - 1);
                }
                // L来到下一个位置
                L++;
            }
            // 每次扩完或者缩完都搜集一个答案
            // 取最大值
            ans = Math.max(ans, R - L); 
        }
        return ans;
    }
   
    /**
     * 这题可以用数组代替hashMap
     * 数组的下标是字符，值是词频
     * 同时搞一个变量记录数组中的字符种类
     */
}
