package class31;

import java.util.ArrayList;
import java.util.List;

//139. 单词拆分
public class Problem_0139_WordBreak {
    /**
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     *
     * 输入: s = "leetcode", wordDict = ["leet", "code"]
     * 输出: true
     * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
     */
    
    /**
     * 思路: 从左往右的尝试模型
     * 
     * 从i出发及其往后的所有单词能不能被字典里的分解掉
     * 
     * 怎么枚举: 例如 abccde， 当前来到0位置
     * 1) i自己能不能做为一个单词
     * 列表中没有这个a，你就宣告了前缀为a的时候不能这么分解
     * 2) ab做为第一个单词, 因为列表中有这个单词, 所以可以, 接下来调用f(i+2)
     * 3) 前缀abc
     * 4) 前缀abcc, 然后调用f(i+4)
     * 某一个前缀它在单词表里面，你就去试。有一个返回 true 就返回true, 都返回false，你才返回false。
     * 
     * 优化:
     * 使用前缀树，快速的判断前缀是否在字典中 --> 将字典的所有单词，建立成前缀树，然后从前缀树查当前的前缀是否在前缀树上
     * 
     * 求解顺序:
     * f(N - 1): 从N - 1位置出发，及其往后的所有单词，能不能被搞定
     * 要搞定f(N - 1) ，取决于f(N)，从N出发及其往后的所有单词，能不能被搞定，空串，能被搞定
     * 
     * 任何一个f(i)都是依赖后面的, 一维动态规划，从dp[N]一直求到dp[0]
     * 
     */
    // 前缀树节点
    public static class Node {
        public boolean end; // 记录是否是单词的结尾
        public Node[] nexts;

        public Node() {
            end = false;
            nexts = new Node[26];
        }
    }
    public static boolean wordBreak(String s, List<String> wordDict) {
        if(s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
            return false;
        }
        // 建立前缀树
        Node root = new Node();
        for(String word : wordDict) {
            char words[] = word.toCharArray();
            Node head = root;
            for(int i = 0; i<= words.length - 1; i++) {
                // 如果没有走到当前节点的路，新建
                int index = words[i] - 'a';
                if(head.nexts[index] == null) {
                    head.nexts[index] = new Node();
                } 
                //建好了往下走
                head = head.nexts[index];
            }
            // 记录单词的结尾
            head.end = true;
        }

        char chars[] = s.toCharArray();
        int N = chars.length;
        boolean dp[] = new boolean[N + 1];
        // N位置出发，都是空串，字典一定能搞定
        dp[N] = true;
        // 搞一维动态规划，从后往前填
        for(int i = N - 1; i >= 0; i--) {
            // 从i位置出发，尝试不同的前缀，从i位置出发到end这一段，如果在前缀树上没有，则搞下一个位置
            // 没有，直接返回false
            // i ... i 作为第一块
            // i ... i + 1 作为第一块
            // ...
            // i ... N - 1 作为第一块
            // 依次尝试
            Node cur = root;
            for(int end = i; end <= N - 1; end++) {
               // cur往下走
               cur = cur.nexts[chars[end] - 'a']; 
               // 前缀树上有没有到当前字符的路，如果没有，直接break，从下一个i+1位置出发去搞
               if (cur == null) {
                   break;
               }
               // 如果有路，而且是结尾字符，表示能i到end能作为第一块，end+1出发往后的字符能不能被分解
               if(cur.end) {
                   dp[i] |= dp[end + 1]; // dp[i]取决于后面end+1开始能不能被搞定 
               }
               // 如果从i位置出发到某个end作为前缀的情况下，能被分解，说明i位置出发找到了一种分解方案，
               // 直接去搞下一个位置
               if(dp[i]) {
                   break;
               }
            }
        }
        for(int i = 0; i <= N; i++) {
            System.out.println(dp[i]);
        }
        return dp[0];
    }
    
    public static void main(String[] args) {
        String s =
                "leetcode";
        List<String> wordDict = new ArrayList<String>();
        wordDict.add("leet");
        wordDict.add("code");
        wordBreak(s, wordDict);
    }
    
    /**
     * 扩展: 求有多少种分解方法
     * 能不能被分解, 有一个为true就结束
     * 方法数是要把所有可能性累加
     * 
     * dp[i]: word从i出发及其往后的所有被分解的方法数是多少?
     */   
    public static int wordBreak2(String s, List<String> wordDict) {
         if(s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
             return 0;
         }
         // 建立前缀树
         Node root = new Node();
         for(String word : wordDict) {
             char words[] = word.toCharArray();
             Node head = root;
             for(int i = 0; i<= words.length - 1; i++) {
                 // 如果没有走到当前节点的路，新建
                 int index = words[i] - 'a';
                 if(head.nexts[index] == null) {
                     head.nexts[index] = new Node();
                 } 
                 //建好了往下走
                 head = head.nexts[index];
             }
             // 记录单词的结尾
             head.end = true;
         }

         char chars[] = s.toCharArray();
         int N = chars.length;
         int dp[] = new int[N + 1];
         // N位置出发，都是空串，字典一定能搞定
         dp[N] = 1;
         // 搞一维动态规划，从后往前填
         for(int i = N - 1; i >= 0; i--) {
             // 从i位置出发，尝试不同的前缀，从i位置出发到end这一段，如果在前缀树上没有，则搞下一个位置
             // 没有，直接返回false
             // i ... i 作为第一块
             // i ... i + 1 作为第一块
             // ...
             // i ... N - 1 作为第一块
             // 依次尝试
             Node cur = root;
             for(int end = i; end <= N - 1; end++) {
                // cur往下走
                cur = cur.nexts[chars[end] - 'a']; 
                // 前缀树上有没有到当前字符的路，如果没有，直接break，从下一个i+1位置出发去搞
                if (cur == null) {
                    break;
                }
                // 如果有路，而且是结尾字符，表示能i到end能作为第一块，end+1出发往后的字符能不能被分解
                if(cur.end) {
                    dp[i] += dp[end + 1];
                }
                // 求所有可能的前缀，所有的方法数加起来，所以这里不像上面那题break
             }
         }
         return dp[0];
    }
}
