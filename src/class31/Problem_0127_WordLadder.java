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
     * beginWord 经过几次转换能够变成endWord，每次只能变一个字符，每次转换得到的串都要在wordlist中，求最小的转换次数。
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
     * 
     * 时间复杂度:
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
    
    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> dict = new ArrayList<String>(Arrays.asList("hot","dot","dog","lot","log"));
        ladderLength(beginWord, endWord, dict);
    }
}
