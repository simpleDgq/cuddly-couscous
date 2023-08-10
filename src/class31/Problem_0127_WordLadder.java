package class31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//127. 单词接龙
public class Problem_0127_WordLadder {
    /**
     * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个
     * 按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：
     *  1.每一对相邻的单词只差一个字母。
     *  2.对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意， beginWord 不需要在 wordList 中。
     *  3.sk == endWord
     *  
     *  给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从 beginWord 
     *  到 endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0 。
     *  
     *  示例 1：
     *  输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     *  输出：5
     *  解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
     */
    
    /**
     * beginWord 经过几次转换能够变成endWord，每次只能变一个字符，每次转换得到的串都要在wordlist中，求最短转换序列的长度。
     * 
     * 思路: 
     * 1. 将beginWord加入到wordlist中，然后对于其中的每一个单词，先生成邻居表
     *    邻居: 只有一个字符不同，且在wordlist存在。放到list中
     * 
     * 如何生成邻居表？
     *  例如: abc
     *  可以尝试abc的每一个位置，26个字符都变一次，如果变出来的字符串在wordlist中，就加入邻居表中
     * 
     * 2. 从beginWord开始，取出它所有可能的邻居，走一个宽度优先遍历，如果找到了endWord就结束，
     * 如果没有找到，就加入队列中，一层一层遍历，直到找到endWord
     * 
     * 优化: 从两头往中间搞，优化常数时间
     * 永远从小的一段开始搞，比如，左边的字符串的邻居表大小是150个，右边的字符串的邻居表大小是70，
     * 那么就从右边开始；后面每一次都是从小的开始
     * 
     * 时间复杂度:
     * 最差情况，整个单词表每个单词都有走一遍，O(N) --> 最差情况，所有的单词都要搞一遍宽度优先遍历
     * 每个单词平均长度为K，要变25次，发散复杂度是25 * K，变换之后，要在hashSet中查看存不存在，时间
     * 复杂度是O(K) --> O(25 * K^2)
     * --> 整体时间复杂度: O(25 * N * K^2)
     *
     * 为什么hash表时间复杂度是O(K)，而不是O(1)？
     * 因为是字符串，在hash表中查询的时候，生成hash值的时候，每个字符都得遍历一遍，所以是O(K)
     * 对于整型，引用类型而言，不需要遍历，时间复杂度是O(1)
     * 
     */
    
    // 这种解法会超时
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 注意生成邻居表之前，要将beginWord加入到list中
        wordList.add(beginWord);
        HashMap<String, List<String>> nexts = getNexts(wordList);
        
        // 宽度优先遍历
        Queue<String> queue = new LinkedList<String>();
        queue.add(beginWord);
        // 在set中出现过的，不在遍历，不走回头路
        HashSet<String> set = new HashSet<String>();
        set.add(beginWord);
        // 记录当前遍历到的字符串到beginWord的距离
        // 比如初始是abc
        // 遍历到第2层是bbc的时候，距离是2 --> key:bbc value: 2
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put(beginWord, 1); // 初始是1
        
        while(!queue.isEmpty()) {
            String cur = queue.poll();
            // 遍历当前字符串的所有邻居
            // 如果到达了end，直接返回
            // 没有到达end，加入队列中，变成下一层，后面继续宽度优先遍历
            int distance = map.get(cur);
            for(String str : nexts.get(cur)) {
                if(str.equals(endWord)) {
                    return distance + 1;
                }
                // 如果没有到达endword，而且没有遍历过
                if(!set.contains(str)) {
                    set.add(str);
                    queue.add(str);
                    // 距离加1
                    map.put(str, distance + 1);
                }
            }
        }
        // 最终都没有搞定，说明搞不定
        return 0;
    }
    
    // 生成wordList中每个单词的邻居表
    public static HashMap<String, List<String>> getNexts(List<String> wordList) {
        HashMap<String, List<String>> nexts = new HashMap<String, List<String>>();
        for(String str : wordList) {
            List<String> next = new ArrayList<String>();
            char chars[] = str.toCharArray();
            // str的每一个字符都尝试去变
            for(int i = 0; i <= chars.length - 1; i++) {
                char temp = chars[i];
                for(char c = 'a'; c <= 'z'; c++) { // 题目告诉我们都是小写字母
                    if(c != temp) {
                        chars[i] = c;
                        // 如果在wordlist中出现过，加入邻居表
                        if(wordList.contains(String.valueOf(chars))) {
                            next.add(String.valueOf(chars));
                        }
                    }
                }
                chars[i] = temp;
            }
            // 一个字符串的邻居表生成好了，加入nexts
            nexts.put(str, next);
        }
        return nexts;
    }
    
    /**
     * 优化解法，从两头往中间搞
     * 需要掌握
     * 
     * 两端分别建立一个hashSet，存储两端的邻居表
     * 
     * 从start端开始宽度优先遍历，遍历的过程中，生成每个单词的邻居表，存放在next邻居表中，
     * 然后比较next和另一端的邻居表进行比较，谁小，下一次就从哪段开始
     */
    public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        // 将list变成hashSet，可以去重
        HashSet<String> dict = new HashSet<String>(wordList);
        if(!dict.contains(endWord)) {
            return 0;
        }
        // startSet是较小的，endSet是较大的
        // 开始端的邻居表，代表某一层的所有字符串
        HashSet<String> startSet = new HashSet<String>();
        startSet.add(beginWord); // 刚开始都只有一个字符串
        // 结束端的邻居表，代表某一层的所有字符串
        HashSet<String> endSet = new HashSet<String>();
        endSet.add(endWord);
        // 不走回头路，每一层访问过的字符串，不在访问
        HashSet<String> visitSet = new HashSet<String>();
        // len表示距离，如果beginWord变1次就能到endWord，距离就是2
        for(int len = 2; !startSet.isEmpty(); len++) {
            // 宽度优先遍历，对于startSet中的每一个单词
            // 都变换，生成邻居表，作为下一层
            HashSet<String> nextSet = new HashSet<String>();
            for(String str : startSet) {
                char chars[] = str.toCharArray();
                for(int i = 0; i <= chars.length - 1; i++) {
                    char temp = chars[i];
                    for(char c = 'a'; c <= 'z'; c++) {
                        if(c != temp) {
                            chars[i] = c;
                            String next = String.valueOf(chars);
                            // 这么写为什么不对？
//                          if(next.equals(endWord)) {
//                              return len;
//                          }
                            // 如果两头夹逼的过程中，endSet中已经撞上了，说明两边已经对接上了，直接返回
                            if(endSet.contains(next)) {
                                return len;
                            }
                            // 如果以前没有访问过(保证不走回头路)，而且在dict中存在，放入next层
                            if(!visitSet.contains(next) && dict.contains(next)) {
                                nextSet.add(next);
                                visitSet.add(next);
                            }
                        }
                    }
                    chars[i] = temp;
                }
            }
            // endSet, nextSet进行比较，谁小，谁就作为下一次的startSet
            // 较大的作为endSet
            startSet = (endSet.size() > nextSet.size()) ? nextSet : endSet;
            endSet = (startSet == endSet) ? nextSet : endSet;
        }
        return 0;  
    }
    
    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> dict = new ArrayList<String>(Arrays.asList("hot","dot","dog","lot","log","cog"));
        System.out.println(ladderLength2(beginWord, endWord, dict));
    }
}
