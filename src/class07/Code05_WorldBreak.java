package class07;

import java.util.HashSet;

public class Code05_WorldBreak {
    /*
     * 使用单词表拼接长字符串的方法数
     * 
     * 假设所有字符都是小写字母. 长字符串是str
     *   arr是去重的单词表, 每个单词都不是空字符串且可以使用任意次
     *   使用arr中的单词有多少种拼接str的方式，返回方法数.
     * 
     */
    
    /*
     * 思路:
     * 看str的每一个可能的前缀，看set中的贴纸能不能搞定，
     * 能搞定，就继续去搞出去前缀之后剩下的字符串
     */
    public static int ways(String str, String arr[]) {
        if(str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        
        HashSet<String> set = new HashSet<String>();
        for(String item : arr) {
            set.add(item);
        }
        return process(str, 0, set);
    }
    
    // 时间复杂度分析: 字符串的每一个前缀都得去试一遍，总共O(N^2)个前缀
    // 在尝试的过程中，还得判断前缀是不是在set中，O(N) --> O(^3) --> 动态规划也是一样
    // 可以用的贴纸都放在了set中
    // 当前来到了i位置，你给我返回能够搞出i...str.length 字符串的方法数
    public static int process(String str, int i, HashSet<String> set) {
        if(i == str.length()) { // str没有字符了，全部搞定，一种方法
            return 1;
        }
        int ways = 0;
        // str的每一个前缀，看set能不能搞定，如果能搞定，继续去搞定下一个位置开始的剩下的字符串
        for(int end = i; end <= str.length() - 1; end++) {
            String prefix = str.substring(i, end + 1); // 也就是例子中的a，aa，aaa等
            if(set.contains(prefix)) { // set中的贴纸能够搞定当前前缀，process去搞定end + 1开始的剩下的字符串
                ways += process(str, end + 1, set);
            } 
        }
        return ways;
    }
    
    /*
     * 动态规划
     * 
     * 可变参数i
     * 范围: 0 ~ N  --> dp[N + 1]
     * 分析依赖，每一个位置，都只依赖它后面的位置
     * 从右往左，去填表
     */
    public static int ways1(String str, String arr[]) {
        if(str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        
        HashSet<String> set = new HashSet<String>();
        for(String item : arr) {
            set.add(item);
        }
        
        int N = str.length();
        int dp[] = new int[N + 1];
        // base case 
        dp[N] = 1;
        
        for(int i = N - 1; i >= 0; i--) {
            for(int end = i; end <= N - 1; end++) {
                String prefix = str.substring(i, end + 1); // 也就是例子中的a，aa，aaa等
                if(set.contains(prefix)) { // set中的贴纸能够搞定当前前缀，process去搞定end + 1开始的剩下的字符串
                    dp[i] += dp[end + 1];
                } 
            }
        }
        return dp[0];
    }
    
    
    /*
     * 优化，上面的过程中，每次都得判断前缀是不是在set中，有一个O(N)的时间复杂度
     * 
     * 可以使用前缀树，来判断字符串的每一个前缀是不是在set中，将时间复杂度降低到O(1)
     * 
     * set中的元素，建前缀树O(M)
     * 总体的时间复杂度降为O(M) + O(N^2)
     */
    public static int ways2(String str, String arr[]) {
        if(str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        // 建好前缀树
        Node rootNode = constructTrieTree(arr);
        return process2(str.toCharArray(), 0, rootNode);
    }
    public static class Node {
        public Node nexts[]; // 走向字符的路
        public boolean end; // 是不是前缀字符的结尾
        
        public Node() {
            nexts = new Node[26];
            end = false;
        }
    }
    
    // 建前缀树
    public static Node constructTrieTree(String arr[]) {
        if(arr == null || arr.length == 0) {
            return null;
        }
        Node head = new Node(); // 建好头节点
        for(String str: arr) { // arr中的每一个字符串，放到前缀树上
            char strs[] = str.toCharArray();
            Node cur = head;
            for(int i = 0; i <= strs.length - 1; i++) {
                int next = strs[i] - 'a';
                if(cur.nexts[next] == null) { // 没有走向next的路，建好
                    cur.nexts[next] = new Node();
                } // 这里不能else，因为，要往下走。例如，abc，走向a没有路，新建了，下一个字符b，不能从头开始，而要从a建出来的节点开始
                cur = cur.nexts[next]; // 走向下一个节点
            }
            cur.end = true; // 一个字符串遍历完，最后执向的节点就是该字符串的结尾字符
        }
        return head;
    }
    
    public static int process2(char str[], int i, Node root) {
        if(i == str.length) { // str没有字符了，全部搞定，一种方法
            return 1;
        }
        int ways = 0;
        Node cur = root;
        // 从i位置开始的，str的每一个前缀，看set能不能搞定，如果能搞定，继续去搞定下一个位置开始的剩下的字符串
        for(int end = i; end <= str.length - 1; end++) {
            // 值得注意的一点是，判断一个前缀是不是存在set中的时候，cur每次在前缀树上只是移动了一个字符
            int next = str[end] - 'a'; // 当前来到str的i位置的字符，看有没有向下走的路，如果有，则往下走
            // 如果已经没有往下走的路了，其它剩下前缀不用继续判断了，不可能搞出来，直接break
            if (cur.nexts[next] == null) {
                break;
            }
            // 有路，往下走
            cur = cur.nexts[next];
            if(cur.end) { // 如果是前缀的结尾，说明当前前缀字符串在set中 搞剩下的前缀去吧
               ways += process2(str, end + 1, root); // 新的i位置开始的字符串前缀，前缀树要从root开始
            }
        }
        return ways;
    }
    
    
    /*
     * 上面的方法改动态规划
     * 可变参数i
     * 范围: 0~N -> int dp[N + 1]
     * 分析依赖: 每一个位置只依赖它后面的位置
     * 从右往左填表
     */
    public static int ways3(String str, String arr[]) {
        if(str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        int N = str.length();
        int dp[] = new int[N + 1];
        // base case
        dp[N] = 1;
        // 建好前缀树
        Node rootNode = constructTrieTree(arr);
        char strs[] = str.toCharArray();
        
        for(int i = N - 1; i >= 0; i--) {
            Node cur = rootNode;
            // 从i位置开始的，str的每一个前缀，看set能不能搞定，如果能搞定，继续去搞定下一个位置开始的剩下的字符串
            for(int end = i; end <= strs.length - 1; end++) {
                // 值得注意的一点是，判断一个前缀是不是存在set中的时候，cur每次在前缀树上只是移动了一个字符
                int next = strs[end] - 'a'; // 当前来到str的i位置的字符，看有没有向下走的路，如果有，则往下走
                // 如果已经没有往下走的路了，其它剩下前缀不用继续判断了，不可能搞出来，直接break
                if (cur.nexts[next] == null) {
                    break;
                }
                // 有路，往下走
                cur = cur.nexts[next];
                if(cur.end) { // 如果是前缀的结尾，说明当前前缀字符串在set中 搞剩下的前缀去吧
                   dp[i] += dp[end + 1]; // 新的i位置开始的字符串前缀，前缀树要从root开始
                }
            }
        }
        return dp[0];
    }
}
