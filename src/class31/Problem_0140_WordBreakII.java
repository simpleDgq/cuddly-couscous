package class31;

import java.util.ArrayList;
import java.util.List;

// 140. 单词拆分 II
public class Problem_0140_WordBreakII {
    /**
     * 给定一个字符串 s 和一个字符串字典 wordDict ，在字符串 s 中
     * 增加空格来构建一个句子，使得句子中所有的单词都在词典中。以任意顺序 返回所有这些可能的句子。
     * 注意：词典中的同一个单词可能在分段中被重复使用多次。
     * 
     * 输入:s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
     * 输出:["cats and dog","cat sand dog"]
     */
    
    /**
     * 思路:
     * 递归，根据dp表生成答案
     * 
     * abccckf
     * 
     * a做前缀，看在前缀树种是否有，没有的话
     * ab做前缀，前缀树上如果有，还要判断从c出发的字符串是否能被分解，通过dp表判断
     * ...
     * 如果从c出发的字符串能够被分解，将ab加入path中，
     * 然后从c出发继续递归，搜集所有可能的path
     * 
     * 在递归的过程中，两个剪枝，前缀树上是否有，后一个位置出发的字符串能否被分解，dp表判断
     */  
    public class Node {
        public String path;
        public boolean end;
        public Node[] nexts;

        public Node() {
            path = null;
            end = false;
            nexts = new Node[26];
        }
    }
    
    public List<String> wordBreak(String s, List<String> wordDict) {
        Node root = getTrie(wordDict);
        boolean[] dp = getDP(s, wordDict);
        char str[] = s.toCharArray();
        ArrayList<String> path = new ArrayList<String>();
        List<String> ans = new ArrayList<String>();
        process(str, 0, root, dp, path, ans);
        return ans;
    }
    //  str[index.....] 是要搞定的字符串
    //当前来到index位置，index之前的位置已经做过决定了，结果存放在了path中，
    //给我返回所有可能的答案，存放在ans中
    
    // root 单词表所有单词生成的前缀树头节点
    // dp[0...N-1] 0位置出发及其往后的所有字符，能否被分解， 1位置出发，能否被分解. 2... N-1... 在dp里
    public void process(char[] str, int index, Node root, boolean[] dp, ArrayList<String> path,
            List<String> ans) {
        // 如果到达了结尾位置，说明搞定了，搜集答案
        if(index == str.length) {
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i <= path.size() - 2; i++) {
                builder.append(path.get(i) + " ");
            }
            // 单独加最后一个为了不加空格
            builder.append(path.get(path.size() - 1));
            // 加入ans
            ans.add(builder.toString());
        } else {
            Node cur = root;
            // 尝试i到end作为前缀的每一种情况
            for(int end = index; end < str.length; end++) {
                // 如果没有走向当前字符的路，说明没有这个前缀
                cur = cur.nexts[str[end] - 'a'];
                if(cur == null) {
                    break;
                }
                // 有走向当前的路，而且是结尾字符，而且end+1往后的字符能够被分解
                // 搜集path，去搞下一个end+1位置
                if(cur.end && dp[end + 1]) {
                    // [i...end] 前缀串
                    // 也可以用 str.subString(i,end+1)  [i..end]
                    // 当前到达了end，path就是单词表中的单词
                    path.add(cur.path); // 这里的path在建立前缀树的时候，单词表的每一个单词都会存放在path中
                    // 从end+1位置出发，搜集所有的答案
                    process(str, end + 1, root, dp, path, ans);
                    //要去搞新的前缀了，恢复现场
                    path.remove(path.size() - 1);
                }
            }
        }
    }
    
    // 给定单词表，生成前缀树
    public Node getTrie(List<String> wordDict) {
        Node root = new Node();
        for(String s : wordDict) {
            char chars[] = s.toCharArray();
            Node cur = root;
            for(int i = 0; i <= chars.length - 1; i++) {
                int index = chars[i] - 'a';
                // 有没有走向当前字符的路，没有就新建，有就直接走向
                 if(cur.nexts[index] == null) {
                     cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.path = s; // 单词表的每一个单词存放在end节点上
            cur.end = true;
        }
        return root;
    }
    
    // dp[i] 以i位置开始的字符串能够被单词表分解?
    public boolean[] getDP(String s, List<String> wordDict) {
        // 生成前缀树
        Node root = getTrie(wordDict);
        int N = s.length();
        boolean dp[] = new boolean[N + 1];
        dp[N] = true;
        char chars[] = s.toCharArray();
        for(int i = N - 1; i >=0; i--) {
            // i到end作为前缀，去尝试
            Node cur = root;
            for(int end = i; end < N; end++) {
                int index = chars[end] - 'a';
                cur = cur.nexts[index];
                // 单词表中没有这个前缀，直接break，搞i+1位置
                if(cur == null) {
                    break;
                }
                // 有这个前缀，而且是字符串的结尾, 而且end+1往后的字符串也能够被分解
                // 说明以i位置开头的字符串能够被分解dp[i] = true;
                if(cur.end && dp[end + 1]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp;
    }
}
