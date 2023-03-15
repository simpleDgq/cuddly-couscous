package class26;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

//本题测试链接 : https://leetcode.cn/problems/word-search-ii/
public class Code02_WordSearchII {
    /**
     *  给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words，找出所有同时在二维网格和字典中出现的单词。

        单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
        同一个单元格内的字母在一个单词中不允许被重复使用。
        
        示例 1：
        
        
        输入：board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]],
             words = ["oath","pea","eat","rain"]
        输出：["eat","oath"]
        示例 2：
        
        
        输入：board = [["a","b"],["c","d"]], words = ["abcb"]
        输出：[]
        
        提示：
        
        m == board.length
        n == board[i].length
        1 <= m, n <= 12
        board[i][j] 是一个小写英文字母
        1 <= words.length <= 3 * 10^4
        1 <= words[i].length <= 10
        words[i] 由小写英文字母组成
        words 中的所有字符串互不相同
     */
    
    /**
     * 思路: 递归 + 前缀树
     * 
     * 大思路: 从board的每一个点出发，收集单词。 每个点，上下左右都搞一变，能够生成的单词都搞出来，看在不在words中，在的话就收集
     * 
     * 优化1: 所有的wrods先建成前缀树，来带一个位置(i,j), 可以往上下左右四个方向走，但是有没有必要走，可以用前缀树来指导。 --> 来到一个位置，前缀树
     * 上有没有这个节点，决定你能不能登上去，能登上去才继续。
     * 
     * 优化2: 每个节点有一个pass变量，如果某个word被搞定了，沿途经过的节点的pass都--
     * 如果要登上的节点的pass等于0，说明答案已经收集过，直接返回。
     * 
     * 例子: abc,abe,abk 三个word，建成前缀树
     * 
     * board是:
     * 
     * a b e      0位置的a出发，收集到了abc和abe，前缀树上沿途的节点的pass--, 
     * x c
     * a b c      下一次来到了这一行的a，发现a走向b的路pass是0了，就没必要再走了，直接返回。
     */
    
    // 前缀树节点
    public static class TrieNode {
        public TrieNode nexts[]; // 前缀树边，当前节点有没有到达下一个节点的路，边是字符，index相当于是边
        public boolean end;
        public int pass;
        
        public TrieNode() {
            nexts = new TrieNode[26];
            end = false;
            pass = 0; // 记录节点通过次数
        }
    }
    
    // 根据word，生成前缀树
    public static void fillWord(String word, TrieNode head) {
        head.pass++; // 没一个单词都会经过一次头节点
        char chs[] = word.toCharArray();
        // 每一个字符，如果没有走向它的路，新建，并且走向它
        // 如果有走向它的路，直接走向
        TrieNode cur = head;
        int index = 0;
        for(int i = 0; i <= chs.length - 1; i++) {
            index = chs[i] - 'a'; // 走向chs[i]字符的边
            if(cur.nexts[index] == null) { // 没有走向chs[i]字符的路
                cur.nexts[index] = new TrieNode();
            }
            // 走向字符的节点
            cur = cur.nexts[index];
            cur.pass++;
        }
        cur.end = true; // word字符串的结尾，标记
    }
    
    // 根据words生成好前缀树，然后board中的每一个点，都去搞
    public static List<String> findWords(char[][] board, String[] words) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0
                || words == null || words.length == 0) {
            return null;
        }
        // 生成前缀树
        TrieNode head = new TrieNode();
        HashSet<String> set = new HashSet<String>(); // 记录已经生成过前缀树的word，生成过的不在生成
        for(String word : words) {
            if(!set.contains(word)) {
                fillWord(word, head);
                set.add(word);
            }
        }
        
        // 之前的路径上，走过的字符，记录在path里
        LinkedList<Character> path = new LinkedList<Character>();
        // 如果找到words中的某个str，就记录在 res里
        List<String> res = new ArrayList<String>();
        // 每一个i,j位置，都去搞一遍
        int N = board.length;
        int M = board[0].length;
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= M - 1; j++) {
                process(board, i, j, path, head, res);// 每一个i,j位置都从前缀树的head开始搞
            }
        }
        return res;
    }
    
    // 从board[row][col]位置的字符出发，
    // 之前的路径上，走过的字符，记录在path里
    // cur还没有登上，有待检查能不能登上去的前缀树的节点
    // 如果找到words中的某个str，就记录在 res里
    // 返回值，从row,col 出发，一共找到了多少个str
    public static int process(
            char[][] board, int row, int col, 
            LinkedList<Character> path, TrieNode cur,
            List<String> res) {
        char cha = board[row][col];
        if(cha == '0') {// 已经走过的字符不能再走
            return 0;
        }
        // (row,col) 不是回头路 cha 有效
        int index = cha - 'a';
        // 如果前缀树上没有走向cha的路
        if(cur.nexts[index] == null) { // 优化点1
            return 0;
        }
        // 这条路上最终的字符串之前加入过结果里
        if(cur.nexts[index].pass == 0) { // 优化点2
            return 0;
        }
        // 没有走回头路且能登上去
        cur = cur.nexts[index];
        // 当前位置的字符加到路径里去
        path.addLast(cha);
        // 从row和col位置出发，后续一共搞定了多少答案
        int fix = 0;
        // 当我来到row col位置，如果决定不往后走了。当前登上的cur.end = ture, 是不是已经搞定了某个字符串了
        if(cur.end) {
            cur.end = false;
            // 收集答案
            res.add(generatePath(path)); // 这里得自己写函数转。toString输出的事"[a, a, b, a]"
            fix++; // 搞定的字符串数量++
        }
        // 当前字符走过，不能走回头路，标记为0
        board[row][col] = '0';
        // 往上、下、左、右，四个方向尝试
        if (row > 0) {
            fix += process(board, row - 1, col, path, cur, res);
        }
        if (row < board.length - 1) {
            fix += process(board, row + 1, col, path, cur, res);
        }
        if (col > 0) {
            fix += process(board, row, col - 1, path, cur, res);
        }
        if (col < board[0].length - 1) {
            fix += process(board, row, col + 1, path, cur, res);
        }
        // 下一个字符去搞它的上下左右之前，要恢复现场
        board[row][col] = cha;
        path.pollLast();
        // 修正pass，搞定一个减一个
        cur.pass -= fix;
        
        return fix;
    }
    
    public static String generatePath(LinkedList<Character> path) {
        char[] str = new char[path.size()];
        int index = 0;
        for (Character cha : path) {
            str[index++] = cha;
        }
        return String.valueOf(str);
    }
    
    public static void main(String args[]) {
       char board[][] =  { {'o','a','a','n'}, {'e','t','a','e'}, {'i','h','k','r'}, {'i','f','l','v'} };
       String words[] = {"oath","pea","eat","rain" };
       
       findWords(board, words);
    }
}
